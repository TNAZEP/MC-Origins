package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelStorageSource;

public class ServerChunkCache extends ChunkSource {
   private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.getStatusList();
   private final DistanceManager distanceManager;
   private final ChunkGenerator generator;
   final ServerLevel level;
   final Thread mainThread;
   final ThreadedLevelLightEngine lightEngine;
   private final ServerChunkCache.MainThreadExecutor mainThreadProcessor;
   public final ChunkMap chunkMap;
   private final DimensionDataStorage dataStorage;
   private long lastInhabitedUpdate;
   private boolean spawnEnemies = true;
   private boolean spawnFriendlies = true;
   private static final int CACHE_SIZE = 4;
   private final long[] lastChunkPos = new long[4];
   private final ChunkStatus[] lastChunkStatus = new ChunkStatus[4];
   private final ChunkAccess[] lastChunk = new ChunkAccess[4];
   @Nullable
   @VisibleForDebug
   private NaturalSpawner.SpawnState lastSpawnState;

   public ServerChunkCache(
      ServerLevel var1,
      LevelStorageSource.LevelStorageAccess var2,
      DataFixer var3,
      StructureManager var4,
      Executor var5,
      ChunkGenerator var6,
      int var7,
      boolean var8,
      ChunkProgressListener var9,
      ChunkStatusUpdateListener var10,
      Supplier<DimensionDataStorage> var11
   ) {
      this.level = â˜ƒ;
      this.mainThreadProcessor = new ServerChunkCache.MainThreadExecutor(â˜ƒ);
      this.generator = â˜ƒ;
      this.mainThread = Thread.currentThread();
      File â˜ƒ = â˜ƒ.getDimensionPath(â˜ƒ.dimension());
      File â˜ƒx = new File(â˜ƒ, "data");
      â˜ƒx.mkdirs();
      this.dataStorage = new DimensionDataStorage(â˜ƒx, â˜ƒ);
      this.chunkMap = new ChunkMap(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.mainThreadProcessor, this, this.getGenerator(), â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.lightEngine = this.chunkMap.getLightEngine();
      this.distanceManager = this.chunkMap.getDistanceManager();
      this.clearCache();
   }

   public ThreadedLevelLightEngine getLightEngine() {
      return this.lightEngine;
   }

   @Nullable
   private ChunkHolder getVisibleChunkIfPresent(long var1) {
      return this.chunkMap.getVisibleChunkIfPresent(â˜ƒ);
   }

   public int getTickingGenerated() {
      return this.chunkMap.getTickingGenerated();
   }

   private void storeInCache(long var1, ChunkAccess var3, ChunkStatus var4) {
      for(int â˜ƒ = 3; â˜ƒ > 0; --â˜ƒ) {
         this.lastChunkPos[â˜ƒ] = this.lastChunkPos[â˜ƒ - 1];
         this.lastChunkStatus[â˜ƒ] = this.lastChunkStatus[â˜ƒ - 1];
         this.lastChunk[â˜ƒ] = this.lastChunk[â˜ƒ - 1];
      }

      this.lastChunkPos[0] = â˜ƒ;
      this.lastChunkStatus[0] = â˜ƒ;
      this.lastChunk[0] = â˜ƒ;
   }

   @Nullable
   @Override
   public ChunkAccess getChunk(int var1, int var2, ChunkStatus var3, boolean var4) {
      if (Thread.currentThread() != this.mainThread) {
         return (ChunkAccess)CompletableFuture.supplyAsync(() -> this.getChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), this.mainThreadProcessor).join();
      } else {
         ProfilerFiller â˜ƒ = this.level.getProfiler();
         â˜ƒ.incrementCounter("getChunk");
         long â˜ƒx = ChunkPos.asLong(â˜ƒ, â˜ƒ);

         for(int â˜ƒxx = 0; â˜ƒxx < 4; ++â˜ƒxx) {
            if (â˜ƒx == this.lastChunkPos[â˜ƒxx] && â˜ƒ == this.lastChunkStatus[â˜ƒxx]) {
               ChunkAccess â˜ƒxxx = this.lastChunk[â˜ƒxx];
               if (â˜ƒxxx != null || !â˜ƒ) {
                  return â˜ƒxxx;
               }
            }
         }

         â˜ƒ.incrementCounter("getChunkCacheMiss");
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒxx = this.getChunkFutureMainThread(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.mainThreadProcessor.managedBlock(â˜ƒxx::isDone);
         ChunkAccess â˜ƒxxx = ((Either)â˜ƒxx.join()).map(var0 -> var0, var1x -> {
            if (â˜ƒ) {
               throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("Chunk not there when requested: " + var1x));
            } else {
               return null;
            }
         });
         this.storeInCache(â˜ƒx, â˜ƒxxx, â˜ƒ);
         return â˜ƒxxx;
      }
   }

   @Nullable
   @Override
   public LevelChunk getChunkNow(int var1, int var2) {
      if (Thread.currentThread() != this.mainThread) {
         return null;
      } else {
         this.level.getProfiler().incrementCounter("getChunkNow");
         long â˜ƒ = ChunkPos.asLong(â˜ƒ, â˜ƒ);

         for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
            if (â˜ƒ == this.lastChunkPos[â˜ƒx] && this.lastChunkStatus[â˜ƒx] == ChunkStatus.FULL) {
               ChunkAccess â˜ƒxx = this.lastChunk[â˜ƒx];
               return â˜ƒxx instanceof LevelChunk ? (LevelChunk)â˜ƒxx : null;
            }
         }

         ChunkHolder â˜ƒx = this.getVisibleChunkIfPresent(â˜ƒ);
         if (â˜ƒx == null) {
            return null;
         } else {
            Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> â˜ƒx = (Either)â˜ƒx.getFutureIfPresent(ChunkStatus.FULL).getNow(null);
            if (â˜ƒx == null) {
               return null;
            } else {
               ChunkAccess â˜ƒx = (ChunkAccess)â˜ƒx.left().orElse(null);
               if (â˜ƒx != null) {
                  this.storeInCache(â˜ƒ, â˜ƒx, ChunkStatus.FULL);
                  if (â˜ƒx instanceof LevelChunk) {
                     return (LevelChunk)â˜ƒx;
                  }
               }

               return null;
            }
         }
      }
   }

   private void clearCache() {
      Arrays.fill(this.lastChunkPos, ChunkPos.INVALID_CHUNK_POS);
      Arrays.fill(this.lastChunkStatus, null);
      Arrays.fill(this.lastChunk, null);
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getChunkFuture(int var1, int var2, ChunkStatus var3, boolean var4) {
      boolean â˜ƒx = Thread.currentThread() == this.mainThread;
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒ;
      if (â˜ƒx) {
         â˜ƒ = this.getChunkFutureMainThread(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.mainThreadProcessor.managedBlock(â˜ƒ::isDone);
      } else {
         â˜ƒ = CompletableFuture.supplyAsync(() -> this.getChunkFutureMainThread(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), this.mainThreadProcessor).thenCompose(var0 -> var0);
      }

      return â˜ƒ;
   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getChunkFutureMainThread(int var1, int var2, ChunkStatus var3, boolean var4) {
      ChunkPos â˜ƒ = new ChunkPos(â˜ƒ, â˜ƒ);
      long â˜ƒx = â˜ƒ.toLong();
      int â˜ƒxx = 33 + ChunkStatus.getDistance(â˜ƒ);
      ChunkHolder â˜ƒxxx = this.getVisibleChunkIfPresent(â˜ƒx);
      if (â˜ƒ) {
         this.distanceManager.addTicket(TicketType.UNKNOWN, â˜ƒ, â˜ƒxx, â˜ƒ);
         if (this.chunkAbsent(â˜ƒxxx, â˜ƒxx)) {
            ProfilerFiller â˜ƒxxxx = this.level.getProfiler();
            â˜ƒxxxx.push("chunkLoad");
            this.runDistanceManagerUpdates();
            â˜ƒxxx = this.getVisibleChunkIfPresent(â˜ƒx);
            â˜ƒxxxx.pop();
            if (this.chunkAbsent(â˜ƒxxx, â˜ƒxx)) {
               throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("No chunk holder after ticket has been added"));
            }
         }
      }

      return this.chunkAbsent(â˜ƒxxx, â˜ƒxx) ? ChunkHolder.UNLOADED_CHUNK_FUTURE : â˜ƒxxx.getOrScheduleFuture(â˜ƒ, this.chunkMap);
   }

   private boolean chunkAbsent(@Nullable ChunkHolder var1, int var2) {
      return â˜ƒ == null || â˜ƒ.getTicketLevel() > â˜ƒ;
   }

   @Override
   public boolean hasChunk(int var1, int var2) {
      ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(new ChunkPos(â˜ƒ, â˜ƒ).toLong());
      int â˜ƒx = 33 + ChunkStatus.getDistance(ChunkStatus.FULL);
      return !this.chunkAbsent(â˜ƒ, â˜ƒx);
   }

   @Override
   public BlockGetter getChunkForLighting(int var1, int var2) {
      long â˜ƒ = ChunkPos.asLong(â˜ƒ, â˜ƒ);
      ChunkHolder â˜ƒx = this.getVisibleChunkIfPresent(â˜ƒ);
      if (â˜ƒx == null) {
         return null;
      } else {
         int â˜ƒ = CHUNK_STATUSES.size() - 1;

         while(true) {
            ChunkStatus â˜ƒx = (ChunkStatus)CHUNK_STATUSES.get(â˜ƒ);
            Optional<ChunkAccess> â˜ƒxx = ((Either)â˜ƒx.getFutureIfPresentUnchecked(â˜ƒx).getNow(ChunkHolder.UNLOADED_CHUNK)).left();
            if (â˜ƒxx.isPresent()) {
               return (BlockGetter)â˜ƒxx.get();
            }

            if (â˜ƒx == ChunkStatus.LIGHT.getParent()) {
               return null;
            }

            --â˜ƒ;
         }
      }
   }

   public Level getLevel() {
      return this.level;
   }

   public boolean pollTask() {
      return this.mainThreadProcessor.pollTask();
   }

   boolean runDistanceManagerUpdates() {
      boolean â˜ƒ = this.distanceManager.runAllUpdates(this.chunkMap);
      boolean â˜ƒx = this.chunkMap.promoteChunkMap();
      if (!â˜ƒ && !â˜ƒx) {
         return false;
      } else {
         this.clearCache();
         return true;
      }
   }

   public boolean isPositionTicking(long var1) {
      return this.checkChunkFuture(â˜ƒ, ChunkHolder::getTickingChunkFuture);
   }

   private boolean checkChunkFuture(long var1, Function<ChunkHolder, CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>>> var3) {
      ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(â˜ƒ);
      if (â˜ƒ == null) {
         return false;
      } else {
         Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> â˜ƒ = (Either)((CompletableFuture)â˜ƒ.apply(â˜ƒ)).getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK);
         return â˜ƒ.left().isPresent();
      }
   }

   public void save(boolean var1) {
      this.runDistanceManagerUpdates();
      this.chunkMap.saveAllChunks(â˜ƒ);
   }

   @Override
   public void close() throws IOException {
      this.save(true);
      this.lightEngine.close();
      this.chunkMap.close();
   }

   @Override
   public void tick(BooleanSupplier var1) {
      this.level.getProfiler().push("purge");
      this.distanceManager.purgeStaleTickets();
      this.runDistanceManagerUpdates();
      this.level.getProfiler().popPush("chunks");
      this.tickChunks();
      this.level.getProfiler().popPush("unload");
      this.chunkMap.tick(â˜ƒ);
      this.level.getProfiler().pop();
      this.clearCache();
   }

   private void tickChunks() {
      long â˜ƒ = this.level.getGameTime();
      long â˜ƒx = â˜ƒ - this.lastInhabitedUpdate;
      this.lastInhabitedUpdate = â˜ƒ;
      LevelData â˜ƒxx = this.level.getLevelData();
      boolean â˜ƒxxx = this.level.isDebug();
      boolean â˜ƒxxxx = this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING);
      if (!â˜ƒxxx) {
         this.level.getProfiler().push("pollingChunks");
         int â˜ƒxxxxx = this.level.getGameRules().getInt(GameRules.RULE_RANDOMTICKING);
         boolean â˜ƒxxxxxx = â˜ƒxx.getGameTime() % 400L == 0L;
         this.level.getProfiler().push("naturalSpawnCount");
         int â˜ƒxxxxxxx = this.distanceManager.getNaturalSpawnChunkCount();
         NaturalSpawner.SpawnState â˜ƒxxxxxxxx = NaturalSpawner.createState(â˜ƒxxxxxxx, this.level.getAllEntities(), this::getFullChunk);
         this.lastSpawnState = â˜ƒxxxxxxxx;
         this.level.getProfiler().pop();
         List<ChunkHolder> â˜ƒxxxxxxxxx = Lists.<ChunkHolder>newArrayList(this.chunkMap.getChunks());
         Collections.shuffle(â˜ƒxxxxxxxxx);
         â˜ƒxxxxxxxxx.forEach(var7x -> {
            Optional<LevelChunk> â˜ƒ = ((Either)var7x.getTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK)).left();
            if (â˜ƒ.isPresent()) {
               LevelChunk â˜ƒx = (LevelChunk)â˜ƒ.get();
               ChunkPos â˜ƒxx = â˜ƒx.getPos();
               if (this.level.isPositionEntityTicking(â˜ƒxx) && !this.chunkMap.noPlayersCloseForSpawning(â˜ƒxx)) {
                  â˜ƒx.setInhabitedTime(â˜ƒx.getInhabitedTime() + â˜ƒ);
                  if (â˜ƒ && (this.spawnEnemies || this.spawnFriendlies) && this.level.getWorldBorder().isWithinBounds(â˜ƒxx)) {
                     NaturalSpawner.spawnForChunk(this.level, â˜ƒx, â˜ƒ, this.spawnFriendlies, this.spawnEnemies, â˜ƒ);
                  }

                  this.level.tickChunk(â˜ƒx, â˜ƒ);
               }
            }
         });
         this.level.getProfiler().push("customSpawners");
         if (â˜ƒxxxx) {
            this.level.tickCustomSpawners(this.spawnEnemies, this.spawnFriendlies);
         }

         this.level.getProfiler().popPush("broadcast");
         â˜ƒxxxxxxxxx.forEach(var0 -> ((Either)var0.getTickingChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK)).left().ifPresent(var0::broadcastChanges));
         this.level.getProfiler().pop();
         this.level.getProfiler().pop();
      }

      this.chunkMap.tick();
   }

   private void getFullChunk(long var1, Consumer<LevelChunk> var3) {
      ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(â˜ƒ);
      if (â˜ƒ != null) {
         ((Either)â˜ƒ.getFullChunkFuture().getNow(ChunkHolder.UNLOADED_LEVEL_CHUNK)).left().ifPresent(â˜ƒ);
      }
   }

   @Override
   public String gatherStats() {
      return Integer.toString(this.getLoadedChunksCount());
   }

   @VisibleForTesting
   public int getPendingTasksCount() {
      return this.mainThreadProcessor.getPendingTasksCount();
   }

   public ChunkGenerator getGenerator() {
      return this.generator;
   }

   @Override
   public int getLoadedChunksCount() {
      return this.chunkMap.size();
   }

   public void blockChanged(BlockPos var1) {
      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getX());
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getZ());
      ChunkHolder â˜ƒxx = this.getVisibleChunkIfPresent(ChunkPos.asLong(â˜ƒ, â˜ƒx));
      if (â˜ƒxx != null) {
         â˜ƒxx.blockChanged(â˜ƒ);
      }
   }

   @Override
   public void onLightUpdate(LightLayer var1, SectionPos var2) {
      this.mainThreadProcessor.execute(() -> {
         ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(â˜ƒ.chunk().toLong());
         if (â˜ƒ != null) {
            â˜ƒ.sectionLightChanged(â˜ƒ, â˜ƒ.y());
         }
      });
   }

   public <T> void addRegionTicket(TicketType<T> var1, ChunkPos var2, int var3, T var4) {
      this.distanceManager.addRegionTicket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public <T> void removeRegionTicket(TicketType<T> var1, ChunkPos var2, int var3, T var4) {
      this.distanceManager.removeRegionTicket(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void updateChunkForced(ChunkPos var1, boolean var2) {
      this.distanceManager.updateChunkForced(â˜ƒ, â˜ƒ);
   }

   public void move(ServerPlayer var1) {
      this.chunkMap.move(â˜ƒ);
   }

   public void removeEntity(Entity var1) {
      this.chunkMap.removeEntity(â˜ƒ);
   }

   public void addEntity(Entity var1) {
      this.chunkMap.addEntity(â˜ƒ);
   }

   public void broadcastAndSend(Entity var1, Packet<?> var2) {
      this.chunkMap.broadcastAndSend(â˜ƒ, â˜ƒ);
   }

   public void broadcast(Entity var1, Packet<?> var2) {
      this.chunkMap.broadcast(â˜ƒ, â˜ƒ);
   }

   public void setViewDistance(int var1) {
      this.chunkMap.setViewDistance(â˜ƒ);
   }

   @Override
   public void setSpawnSettings(boolean var1, boolean var2) {
      this.spawnEnemies = â˜ƒ;
      this.spawnFriendlies = â˜ƒ;
   }

   public String getChunkDebugData(ChunkPos var1) {
      return this.chunkMap.getChunkDebugData(â˜ƒ);
   }

   public DimensionDataStorage getDataStorage() {
      return this.dataStorage;
   }

   public PoiManager getPoiManager() {
      return this.chunkMap.getPoiManager();
   }

   @Nullable
   @VisibleForDebug
   public NaturalSpawner.SpawnState getLastSpawnState() {
      return this.lastSpawnState;
   }

   final class MainThreadExecutor extends BlockableEventLoop<Runnable> {
      MainThreadExecutor(Level var2) {
         super("Chunk source main thread executor for " + â˜ƒ.dimension().location());
      }

      @Override
      protected Runnable wrapRunnable(Runnable var1) {
         return â˜ƒ;
      }

      @Override
      protected boolean shouldRun(Runnable var1) {
         return true;
      }

      @Override
      protected boolean scheduleExecutables() {
         return true;
      }

      @Override
      protected Thread getRunningThread() {
         return ServerChunkCache.this.mainThread;
      }

      @Override
      protected void doRunTask(Runnable var1) {
         ServerChunkCache.this.level.getProfiler().incrementCounter("runTask");
         super.doRunTask(â˜ƒ);
      }

      @Override
      protected boolean pollTask() {
         if (ServerChunkCache.this.runDistanceManagerUpdates()) {
            return true;
         } else {
            ServerChunkCache.this.lightEngine.tryScheduleUpdate();
            return super.pollTask();
         }
      }
   }
}
