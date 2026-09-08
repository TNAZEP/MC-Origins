package net.minecraft.server.level;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetChunkCacheCenterPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.network.ServerPlayerConnection;
import net.minecraft.util.CsvOutput;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.chunk.storage.ChunkStorage;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkMap extends ChunkStorage implements ChunkHolder.PlayerProvider {
   private static final byte CHUNK_TYPE_REPLACEABLE = -1;
   private static final byte CHUNK_TYPE_UNKNOWN = 0;
   private static final byte CHUNK_TYPE_FULL = 1;
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int CHUNK_SAVED_PER_TICK = 200;
   private static final int MIN_VIEW_DISTANCE = 3;
   public static final int MAX_VIEW_DISTANCE = 33;
   public static final int MAX_CHUNK_DISTANCE = 33 + ChunkStatus.maxDistance();
   public static final int FORCED_TICKET_LEVEL = 31;
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> updatingChunkMap = new Long2ObjectLinkedOpenHashMap<>();
   private volatile Long2ObjectLinkedOpenHashMap<ChunkHolder> visibleChunkMap = this.updatingChunkMap.clone();
   private final Long2ObjectLinkedOpenHashMap<ChunkHolder> pendingUnloads = new Long2ObjectLinkedOpenHashMap<>();
   private final LongSet entitiesInLevel = new LongOpenHashSet();
   final ServerLevel level;
   private final ThreadedLevelLightEngine lightEngine;
   private final BlockableEventLoop<Runnable> mainThreadExecutor;
   private final ChunkGenerator generator;
   private final Supplier<DimensionDataStorage> overworldDataStorage;
   private final PoiManager poiManager;
   final LongSet toDrop = new LongOpenHashSet();
   private boolean modified;
   private final ChunkTaskPriorityQueueSorter queueSorter;
   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> worldgenMailbox;
   private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> mainThreadMailbox;
   private final ChunkProgressListener progressListener;
   private final ChunkStatusUpdateListener chunkStatusListener;
   private final ChunkMap.DistanceManager distanceManager;
   private final AtomicInteger tickingGenerated = new AtomicInteger();
   private final StructureManager structureManager;
   private final String storageName;
   private final PlayerMap playerMap = new PlayerMap();
   private final Int2ObjectMap<ChunkMap.TrackedEntity> entityMap = new Int2ObjectOpenHashMap<>();
   private final Long2ByteMap chunkTypeCache = new Long2ByteOpenHashMap();
   private final Queue<Runnable> unloadQueue = Queues.newConcurrentLinkedQueue();
   int viewDistance;

   public ChunkMap(
      ServerLevel var1,
      LevelStorageSource.LevelStorageAccess var2,
      DataFixer var3,
      StructureManager var4,
      Executor var5,
      BlockableEventLoop<Runnable> var6,
      LightChunkGetter var7,
      ChunkGenerator var8,
      ChunkProgressListener var9,
      ChunkStatusUpdateListener var10,
      Supplier<DimensionDataStorage> var11,
      int var12,
      boolean var13
   ) {
      super(new File(â˜ƒ.getDimensionPath(â˜ƒ.dimension()), "region"), â˜ƒ, â˜ƒ);
      this.structureManager = â˜ƒ;
      File â˜ƒ = â˜ƒ.getDimensionPath(â˜ƒ.dimension());
      this.storageName = â˜ƒ.getName();
      this.level = â˜ƒ;
      this.generator = â˜ƒ;
      this.mainThreadExecutor = â˜ƒ;
      ProcessorMailbox<Runnable> â˜ƒx = ProcessorMailbox.create(â˜ƒ, "worldgen");
      ProcessorHandle<Runnable> â˜ƒxx = ProcessorHandle.of("main", â˜ƒ::tell);
      this.progressListener = â˜ƒ;
      this.chunkStatusListener = â˜ƒ;
      ProcessorMailbox<Runnable> â˜ƒxxx = ProcessorMailbox.create(â˜ƒ, "light");
      this.queueSorter = new ChunkTaskPriorityQueueSorter(ImmutableList.of(â˜ƒx, â˜ƒxx, â˜ƒxxx), â˜ƒ, Integer.MAX_VALUE);
      this.worldgenMailbox = this.queueSorter.getProcessor(â˜ƒx, false);
      this.mainThreadMailbox = this.queueSorter.getProcessor(â˜ƒxx, false);
      this.lightEngine = new ThreadedLevelLightEngine(â˜ƒ, this, this.level.dimensionType().hasSkyLight(), â˜ƒxxx, this.queueSorter.getProcessor(â˜ƒxxx, false));
      this.distanceManager = new ChunkMap.DistanceManager(â˜ƒ, â˜ƒ);
      this.overworldDataStorage = â˜ƒ;
      this.poiManager = new PoiManager(new File(â˜ƒ, "poi"), â˜ƒ, â˜ƒ, â˜ƒ);
      this.setViewDistance(â˜ƒ);
   }

   private static double euclideanDistanceSquared(ChunkPos var0, Entity var1) {
      double â˜ƒ = (double)SectionPos.sectionToBlockCoord(â˜ƒ.x, 8);
      double â˜ƒx = (double)SectionPos.sectionToBlockCoord(â˜ƒ.z, 8);
      double â˜ƒxx = â˜ƒ - â˜ƒ.getX();
      double â˜ƒxxx = â˜ƒx - â˜ƒ.getZ();
      return â˜ƒxx * â˜ƒxx + â˜ƒxxx * â˜ƒxxx;
   }

   private static int checkerboardDistance(ChunkPos var0, ServerPlayer var1, boolean var2) {
      int â˜ƒ;
      int â˜ƒx;
      if (â˜ƒ) {
         SectionPos â˜ƒxx = â˜ƒ.getLastSectionPos();
         â˜ƒ = â˜ƒxx.x();
         â˜ƒx = â˜ƒxx.z();
      } else {
         â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getBlockX());
         â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getBlockZ());
      }

      return checkerboardDistance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   private static int checkerboardDistance(ChunkPos var0, Entity var1) {
      return checkerboardDistance(â˜ƒ, SectionPos.blockToSectionCoord(â˜ƒ.getBlockX()), SectionPos.blockToSectionCoord(â˜ƒ.getBlockZ()));
   }

   private static int checkerboardDistance(ChunkPos var0, int var1, int var2) {
      int â˜ƒ = â˜ƒ.x - â˜ƒ;
      int â˜ƒx = â˜ƒ.z - â˜ƒ;
      return Math.max(Math.abs(â˜ƒ), Math.abs(â˜ƒx));
   }

   protected ThreadedLevelLightEngine getLightEngine() {
      return this.lightEngine;
   }

   @Nullable
   protected ChunkHolder getUpdatingChunkIfPresent(long var1) {
      return this.updatingChunkMap.get(â˜ƒ);
   }

   @Nullable
   protected ChunkHolder getVisibleChunkIfPresent(long var1) {
      return this.visibleChunkMap.get(â˜ƒ);
   }

   protected IntSupplier getChunkQueueLevel(long var1) {
      return () -> {
         ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(â˜ƒ);
         return â˜ƒ == null ? ChunkTaskPriorityQueue.PRIORITY_LEVEL_COUNT - 1 : Math.min(â˜ƒ.getQueueLevel(), ChunkTaskPriorityQueue.PRIORITY_LEVEL_COUNT - 1);
      };
   }

   public String getChunkDebugData(ChunkPos var1) {
      ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(â˜ƒ.toLong());
      if (â˜ƒ == null) {
         return "null";
      } else {
         String â˜ƒ = â˜ƒ.getTicketLevel() + "\n";
         ChunkStatus â˜ƒx = â˜ƒ.getLastAvailableStatus();
         ChunkAccess â˜ƒxx = â˜ƒ.getLastAvailable();
         if (â˜ƒx != null) {
            â˜ƒ = â˜ƒ + "St: \u00a7" + â˜ƒx.getIndex() + â˜ƒx + "\u00a7r\n";
         }

         if (â˜ƒxx != null) {
            â˜ƒ = â˜ƒ + "Ch: \u00a7" + â˜ƒxx.getStatus().getIndex() + â˜ƒxx.getStatus() + "\u00a7r\n";
         }

         ChunkHolder.FullChunkStatus â˜ƒ = â˜ƒ.getFullStatus();
         â˜ƒ = â˜ƒ + "\u00a7" + â˜ƒ.ordinal() + â˜ƒ;
         return â˜ƒ + "\u00a7r";
      }
   }

   private CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> getChunkRangeFuture(
      ChunkPos var1, int var2, IntFunction<ChunkStatus> var3
   ) {
      List<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> â˜ƒ = Lists.newArrayList();
      int â˜ƒx = â˜ƒ.x;
      int â˜ƒxx = â˜ƒ.z;

      for(int â˜ƒxxx = -â˜ƒ; â˜ƒxxx <= â˜ƒ; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -â˜ƒ; â˜ƒxxxx <= â˜ƒ; ++â˜ƒxxxx) {
            int â˜ƒxxxxx = Math.max(Math.abs(â˜ƒxxxx), Math.abs(â˜ƒxxx));
            final ChunkPos â˜ƒxxxxxx = new ChunkPos(â˜ƒx + â˜ƒxxxx, â˜ƒxx + â˜ƒxxx);
            long â˜ƒxxxxxxx = â˜ƒxxxxxx.toLong();
            ChunkHolder â˜ƒxxxxxxxx = this.getUpdatingChunkIfPresent(â˜ƒxxxxxxx);
            if (â˜ƒxxxxxxxx == null) {
               return CompletableFuture.completedFuture(Either.right(new ChunkHolder.ChunkLoadingFailure() {
                  public String toString() {
                     return "Unloaded " + â˜ƒ;
                  }
               }));
            }

            ChunkStatus â˜ƒxxxxx = (ChunkStatus)â˜ƒ.apply(â˜ƒxxxxx);
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒxxxxxx = â˜ƒxxxxxxxx.getOrScheduleFuture(â˜ƒxxxxx, this);
            â˜ƒ.add(â˜ƒxxxxxx);
         }
      }

      CompletableFuture<List<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> â˜ƒxxx = Util.sequence(â˜ƒ);
      return â˜ƒxxx.thenApply(var4x -> {
         List<ChunkAccess> â˜ƒ = Lists.<ChunkAccess>newArrayList();
         int â˜ƒx = 0;

         for(final Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> â˜ƒxx : var4x) {
            Optional<ChunkAccess> â˜ƒxxx = â˜ƒxx.left();
            if (!â˜ƒxxx.isPresent()) {
               final int â˜ƒxxxx = â˜ƒx;
               return Either.right(new ChunkHolder.ChunkLoadingFailure() {
                  public String toString() {
                     return "Unloaded " + new ChunkPos(â˜ƒ + â˜ƒ % (â˜ƒ * 2 + 1), â˜ƒ + â˜ƒ / (â˜ƒ * 2 + 1)) + " " + â˜ƒ.right().get();
                  }
               });
            }

            â˜ƒ.add((ChunkAccess)â˜ƒxxx.get());
            ++â˜ƒx;
         }

         return Either.left(â˜ƒ);
      });
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> prepareEntityTickingChunk(ChunkPos var1) {
      return this.getChunkRangeFuture(â˜ƒ, 2, var0 -> ChunkStatus.FULL)
         .thenApplyAsync(var0 -> var0.mapLeft(var0x -> (LevelChunk)var0x.get(var0x.size() / 2)), this.mainThreadExecutor);
   }

   @Nullable
   ChunkHolder updateChunkScheduling(long var1, int var3, @Nullable ChunkHolder var4, int var5) {
      if (â˜ƒ > MAX_CHUNK_DISTANCE && â˜ƒ > MAX_CHUNK_DISTANCE) {
         return â˜ƒ;
      } else {
         if (â˜ƒ != null) {
            â˜ƒ.setTicketLevel(â˜ƒ);
         }

         if (â˜ƒ != null) {
            if (â˜ƒ > MAX_CHUNK_DISTANCE) {
               this.toDrop.add(â˜ƒ);
            } else {
               this.toDrop.remove(â˜ƒ);
            }
         }

         if (â˜ƒ <= MAX_CHUNK_DISTANCE && â˜ƒ == null) {
            â˜ƒ = this.pendingUnloads.remove(â˜ƒ);
            if (â˜ƒ != null) {
               â˜ƒ.setTicketLevel(â˜ƒ);
            } else {
               â˜ƒ = new ChunkHolder(new ChunkPos(â˜ƒ), â˜ƒ, this.level, this.lightEngine, this.queueSorter, this);
            }

            this.updatingChunkMap.put(â˜ƒ, â˜ƒ);
            this.modified = true;
         }

         return â˜ƒ;
      }
   }

   @Override
   public void close() throws IOException {
      try {
         this.queueSorter.close();
         this.poiManager.close();
      } finally {
         super.close();
      }
   }

   protected void saveAllChunks(boolean var1) {
      if (â˜ƒ) {
         List<ChunkHolder> â˜ƒ = (List)this.visibleChunkMap
            .values()
            .stream()
            .filter(ChunkHolder::wasAccessibleSinceLastSave)
            .peek(ChunkHolder::refreshAccessibility)
            .collect(Collectors.toList());
         MutableBoolean â˜ƒx = new MutableBoolean();

         do {
            â˜ƒx.setFalse();
            â˜ƒ.stream().map(var1x -> {
               CompletableFuture<ChunkAccess> â˜ƒ;
               do {
                  â˜ƒ = var1x.getChunkToSave();
                  this.mainThreadExecutor.managedBlock(â˜ƒ::isDone);
               } while(â˜ƒ != var1x.getChunkToSave());

               return (ChunkAccess)â˜ƒ.join();
            }).filter(var0 -> var0 instanceof ImposterProtoChunk || var0 instanceof LevelChunk).filter(this::save).forEach(var1x -> â˜ƒ.setTrue());
         } while(â˜ƒx.isTrue());

         this.processUnloads(() -> true);
         this.flushWorker();
      } else {
         this.visibleChunkMap.values().stream().filter(ChunkHolder::wasAccessibleSinceLastSave).forEach(var1x -> {
            ChunkAccess â˜ƒ = (ChunkAccess)var1x.getChunkToSave().getNow(null);
            if (â˜ƒ instanceof ImposterProtoChunk || â˜ƒ instanceof LevelChunk) {
               this.save(â˜ƒ);
               var1x.refreshAccessibility();
            }
         });
      }
   }

   protected void tick(BooleanSupplier var1) {
      ProfilerFiller â˜ƒ = this.level.getProfiler();
      â˜ƒ.push("poi");
      this.poiManager.tick(â˜ƒ);
      â˜ƒ.popPush("chunk_unload");
      if (!this.level.noSave()) {
         this.processUnloads(â˜ƒ);
      }

      â˜ƒ.pop();
   }

   private void processUnloads(BooleanSupplier var1) {
      LongIterator â˜ƒ = this.toDrop.iterator();

      long â˜ƒ;
      for(int â˜ƒx = 0; â˜ƒ.hasNext() && (â˜ƒ.getAsBoolean() || â˜ƒx < 200 || this.toDrop.size() > 2000); â˜ƒ.remove()) {
         â˜ƒ = â˜ƒ.nextLong();
         ChunkHolder â˜ƒxx = this.updatingChunkMap.remove(â˜ƒ);
         if (â˜ƒxx != null) {
            this.pendingUnloads.put(â˜ƒ, â˜ƒxx);
            this.modified = true;
            ++â˜ƒx;
            this.scheduleUnload(â˜ƒ, â˜ƒxx);
         }
      }

      while((â˜ƒ.getAsBoolean() || this.unloadQueue.size() > 2000) && (â˜ƒ = (long)((Runnable)this.unloadQueue.poll())) != null) {
         â˜ƒ.run();
      }
   }

   private void scheduleUnload(long var1, ChunkHolder var3) {
      CompletableFuture<ChunkAccess> â˜ƒ = â˜ƒ.getChunkToSave();
      â˜ƒ.thenAcceptAsync(var5 -> {
         CompletableFuture<ChunkAccess> â˜ƒ = â˜ƒ.getChunkToSave();
         if (â˜ƒ != â˜ƒ) {
            this.scheduleUnload(â˜ƒ, â˜ƒ);
         } else {
            if (this.pendingUnloads.remove(â˜ƒ, â˜ƒ) && var5 != null) {
               if (var5 instanceof LevelChunk) {
                  ((LevelChunk)var5).setLoaded(false);
               }

               this.save(var5);
               if (this.entitiesInLevel.remove(â˜ƒ) && var5 instanceof LevelChunk â˜ƒ) {
                  this.level.unload(â˜ƒ);
               }

               this.lightEngine.updateChunkStatus(var5.getPos());
               this.lightEngine.tryScheduleUpdate();
               this.progressListener.onStatusChange(var5.getPos(), null);
            }
         }
      }, this.unloadQueue::add).whenComplete((var1x, var2) -> {
         if (var2 != null) {
            LOGGER.error("Failed to save chunk {}", â˜ƒ.getPos(), var2);
         }
      });
   }

   protected boolean promoteChunkMap() {
      if (!this.modified) {
         return false;
      } else {
         this.visibleChunkMap = this.updatingChunkMap.clone();
         this.modified = false;
         return true;
      }
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> schedule(ChunkHolder var1, ChunkStatus var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      if (â˜ƒ == ChunkStatus.EMPTY) {
         return this.scheduleChunkLoad(â˜ƒ);
      } else {
         if (â˜ƒ == ChunkStatus.LIGHT) {
            this.distanceManager.addTicket(TicketType.LIGHT, â˜ƒ, 33 + ChunkStatus.getDistance(ChunkStatus.LIGHT), â˜ƒ);
         }

         Optional<ChunkAccess> â˜ƒ = ((Either)â˜ƒ.getOrScheduleFuture(â˜ƒ.getParent(), this).getNow(ChunkHolder.UNLOADED_CHUNK)).left();
         if (â˜ƒ.isPresent() && ((ChunkAccess)â˜ƒ.get()).getStatus().isOrAfter(â˜ƒ)) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒx = â˜ƒ.load(
               this.level, this.structureManager, this.lightEngine, var2x -> this.protoChunkToFullChunk(â˜ƒ), (ChunkAccess)â˜ƒ.get()
            );
            this.progressListener.onStatusChange(â˜ƒ, â˜ƒ);
            return â˜ƒx;
         } else {
            return this.scheduleChunkGeneration(â˜ƒ, â˜ƒ);
         }
      }
   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> scheduleChunkLoad(ChunkPos var1) {
      return CompletableFuture.supplyAsync(() -> {
         try {
            this.level.getProfiler().incrementCounter("chunkLoad");
            CompoundTag â˜ƒ = this.readChunk(â˜ƒ);
            if (â˜ƒ != null) {
               boolean â˜ƒx = â˜ƒ.contains("Level", 10) && â˜ƒ.getCompound("Level").contains("Status", 8);
               if (â˜ƒx) {
                  ChunkAccess â˜ƒxx = ChunkSerializer.read(this.level, this.structureManager, this.poiManager, â˜ƒ, â˜ƒ);
                  this.markPosition(â˜ƒ, â˜ƒxx.getStatus().getChunkType());
                  return Either.left(â˜ƒxx);
               }

               LOGGER.error("Chunk file at {} is missing level data, skipping", â˜ƒ);
            }
         } catch (ReportedException var5) {
            Throwable â˜ƒ = var5.getCause();
            if (!(â˜ƒ instanceof IOException)) {
               this.markPositionReplaceable(â˜ƒ);
               throw var5;
            }

            LOGGER.error("Couldn't load chunk {}", â˜ƒ, â˜ƒ);
         } catch (Exception var6) {
            LOGGER.error("Couldn't load chunk {}", â˜ƒ, var6);
         }

         this.markPositionReplaceable(â˜ƒ);
         return Either.left(new ProtoChunk(â˜ƒ, UpgradeData.EMPTY, this.level));
      }, this.mainThreadExecutor);
   }

   private void markPositionReplaceable(ChunkPos var1) {
      this.chunkTypeCache.put(â˜ƒ.toLong(), (byte)-1);
   }

   private byte markPosition(ChunkPos var1, ChunkStatus.ChunkType var2) {
      return this.chunkTypeCache.put(â˜ƒ.toLong(), (byte)(â˜ƒ == ChunkStatus.ChunkType.PROTOCHUNK ? -1 : 1));
   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> scheduleChunkGeneration(ChunkHolder var1, ChunkStatus var2) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> â˜ƒx = this.getChunkRangeFuture(
         â˜ƒ, â˜ƒ.getRange(), var2x -> this.getDependencyStatus(â˜ƒ, var2x)
      );
      this.level.getProfiler().incrementCounter((Supplier<String>)(() -> "chunkGenerate " + â˜ƒ.getName()));
      Executor â˜ƒxx = var2x -> this.worldgenMailbox.tell(ChunkTaskPriorityQueueSorter.message(â˜ƒ, var2x));
      return â˜ƒx.thenComposeAsync(
         var5x -> var5x.map(
               var5xx -> {
                  try {
                     CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒ = â˜ƒ.generate(
                        â˜ƒ, this.level, this.generator, this.structureManager, this.lightEngine, var2x -> this.protoChunkToFullChunk(â˜ƒ), var5xx
                     );
                     this.progressListener.onStatusChange(â˜ƒ, â˜ƒ);
                     return â˜ƒ;
                  } catch (Exception var9) {
                     var9.getStackTrace();
                     CrashReport â˜ƒx = CrashReport.forThrowable(var9, "Exception generating new chunk");
                     CrashReportCategory â˜ƒxx = â˜ƒx.addCategory("Chunk to be generated");
                     â˜ƒxx.setDetail("Location", String.format("%d,%d", â˜ƒ.x, â˜ƒ.z));
                     â˜ƒxx.setDetail("Position hash", ChunkPos.asLong(â˜ƒ.x, â˜ƒ.z));
                     â˜ƒxx.setDetail("Generator", this.generator);
                     throw new ReportedException(â˜ƒx);
                  }
               },
               var2x -> {
                  this.releaseLightTicket(â˜ƒ);
                  return CompletableFuture.completedFuture(Either.right(var2x));
               }
            ),
         â˜ƒxx
      );
   }

   protected void releaseLightTicket(ChunkPos var1) {
      this.mainThreadExecutor
         .tell(
            Util.name(
               () -> this.distanceManager.removeTicket(TicketType.LIGHT, â˜ƒ, 33 + ChunkStatus.getDistance(ChunkStatus.LIGHT), â˜ƒ),
               () -> "release light ticket " + â˜ƒ
            )
         );
   }

   private ChunkStatus getDependencyStatus(ChunkStatus var1, int var2) {
      ChunkStatus â˜ƒ;
      if (â˜ƒ == 0) {
         â˜ƒ = â˜ƒ.getParent();
      } else {
         â˜ƒ = ChunkStatus.getStatusAroundFullChunk(ChunkStatus.getDistance(â˜ƒ) + â˜ƒ);
      }

      return â˜ƒ;
   }

   private static void postLoadProtoChunk(ServerLevel var0, List<CompoundTag> var1) {
      if (!â˜ƒ.isEmpty()) {
         â˜ƒ.addWorldGenChunkEntities(EntityType.loadEntitiesRecursive(â˜ƒ, â˜ƒ));
      }
   }

   private CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> protoChunkToFullChunk(ChunkHolder var1) {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒ = â˜ƒ.getFutureIfPresentUnchecked(ChunkStatus.FULL.getParent());
      return â˜ƒ.thenApplyAsync(var2x -> {
         ChunkStatus â˜ƒ = ChunkHolder.getStatus(â˜ƒ.getTicketLevel());
         return !â˜ƒ.isOrAfter(ChunkStatus.FULL) ? ChunkHolder.UNLOADED_CHUNK : var2x.mapLeft(var2xx -> {
            ChunkPos â˜ƒx = â˜ƒ.getPos();
            ProtoChunk â˜ƒxx = (ProtoChunk)var2xx;
            LevelChunk â˜ƒ;
            if (â˜ƒxx instanceof ImposterProtoChunk) {
               â˜ƒ = ((ImposterProtoChunk)â˜ƒxx).getWrapped();
            } else {
               â˜ƒ = new LevelChunk(this.level, â˜ƒxx, var2xxx -> postLoadProtoChunk(this.level, â˜ƒ.getEntities()));
               â˜ƒ.replaceProtoChunk(new ImposterProtoChunk(â˜ƒ));
            }

            â˜ƒ.setFullStatus(() -> ChunkHolder.getFullChunkStatus(â˜ƒ.getTicketLevel()));
            â˜ƒ.runPostLoad();
            if (this.entitiesInLevel.add(â˜ƒx.toLong())) {
               â˜ƒ.setLoaded(true);
               â˜ƒ.registerAllBlockEntitiesAfterLevelLoad();
            }

            return â˜ƒ;
         });
      }, var2x -> this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message(var2x, â˜ƒ.getPos().toLong(), â˜ƒ::getTicketLevel)));
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> prepareTickingChunk(ChunkHolder var1) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      CompletableFuture<Either<List<ChunkAccess>, ChunkHolder.ChunkLoadingFailure>> â˜ƒx = this.getChunkRangeFuture(â˜ƒ, 1, var0 -> ChunkStatus.FULL);
      CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> â˜ƒxx = â˜ƒx.thenApplyAsync(var0 -> var0.flatMap(var0x -> {
            LevelChunk â˜ƒ = (LevelChunk)var0x.get(var0x.size() / 2);
            â˜ƒ.postProcessGeneration();
            return Either.left(â˜ƒ);
         }), var2x -> this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message(â˜ƒ, var2x)));
      â˜ƒxx.thenAcceptAsync(var2x -> var2x.ifLeft(var2xx -> {
            this.tickingGenerated.getAndIncrement();
            Packet<?>[] â˜ƒ = new Packet[2];
            this.getPlayers(â˜ƒ, false).forEach(var3x -> this.playerLoadedChunk(var3x, â˜ƒ, var2xx));
         }), var2x -> this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message(â˜ƒ, var2x)));
      return â˜ƒxx;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> prepareAccessibleChunk(ChunkHolder var1) {
      return this.getChunkRangeFuture(â˜ƒ.getPos(), 1, ChunkStatus::getStatusAroundFullChunk).thenApplyAsync(var0 -> var0.mapLeft(var0x -> {
            LevelChunk â˜ƒ = (LevelChunk)var0x.get(var0x.size() / 2);
            â˜ƒ.unpackTicks();
            return â˜ƒ;
         }), var2 -> this.mainThreadMailbox.tell(ChunkTaskPriorityQueueSorter.message(â˜ƒ, var2)));
   }

   public int getTickingGenerated() {
      return this.tickingGenerated.get();
   }

   private boolean save(ChunkAccess var1) {
      this.poiManager.flush(â˜ƒ.getPos());
      if (!â˜ƒ.isUnsaved()) {
         return false;
      } else {
         â˜ƒ.setUnsaved(false);
         ChunkPos â˜ƒ = â˜ƒ.getPos();

         try {
            ChunkStatus â˜ƒx = â˜ƒ.getStatus();
            if (â˜ƒx.getChunkType() != ChunkStatus.ChunkType.LEVELCHUNK) {
               if (this.isExistingChunkFull(â˜ƒ)) {
                  return false;
               }

               if (â˜ƒx == ChunkStatus.EMPTY && â˜ƒ.getAllStarts().values().stream().noneMatch(StructureStart::isValid)) {
                  return false;
               }
            }

            this.level.getProfiler().incrementCounter("chunkSave");
            CompoundTag â˜ƒx = ChunkSerializer.write(this.level, â˜ƒ);
            this.write(â˜ƒ, â˜ƒx);
            this.markPosition(â˜ƒ, â˜ƒx.getChunkType());
            return true;
         } catch (Exception var5) {
            LOGGER.error("Failed to save chunk {},{}", â˜ƒ.x, â˜ƒ.z, var5);
            return false;
         }
      }
   }

   private boolean isExistingChunkFull(ChunkPos var1) {
      byte â˜ƒ = this.chunkTypeCache.get(â˜ƒ.toLong());
      if (â˜ƒ != 0) {
         return â˜ƒ == 1;
      } else {
         CompoundTag â˜ƒ;
         try {
            â˜ƒ = this.readChunk(â˜ƒ);
            if (â˜ƒ == null) {
               this.markPositionReplaceable(â˜ƒ);
               return false;
            }
         } catch (Exception var5) {
            LOGGER.error("Failed to read chunk {}", â˜ƒ, var5);
            this.markPositionReplaceable(â˜ƒ);
            return false;
         }

         ChunkStatus.ChunkType â˜ƒ = ChunkSerializer.getChunkTypeFromTag(â˜ƒ);
         return this.markPosition(â˜ƒ, â˜ƒ) == 1;
      }
   }

   protected void setViewDistance(int var1) {
      int â˜ƒ = Mth.clamp(â˜ƒ + 1, 3, 33);
      if (â˜ƒ != this.viewDistance) {
         int â˜ƒx = this.viewDistance;
         this.viewDistance = â˜ƒ;
         this.distanceManager.updatePlayerTickets(this.viewDistance);

         for(ChunkHolder â˜ƒxx : this.updatingChunkMap.values()) {
            ChunkPos â˜ƒxxx = â˜ƒxx.getPos();
            Packet<?>[] â˜ƒxxxx = new Packet[2];
            this.getPlayers(â˜ƒxxx, false).forEach(var4 -> {
               int â˜ƒ = checkerboardDistance(â˜ƒ, var4, true);
               boolean â˜ƒx = â˜ƒ <= â˜ƒ;
               boolean â˜ƒxx = â˜ƒ <= this.viewDistance;
               this.updateChunkTracking(var4, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
            });
         }
      }
   }

   protected void updateChunkTracking(ServerPlayer var1, ChunkPos var2, Packet<?>[] var3, boolean var4, boolean var5) {
      if (â˜ƒ.level == this.level) {
         if (â˜ƒ && !â˜ƒ) {
            ChunkHolder â˜ƒ = this.getVisibleChunkIfPresent(â˜ƒ.toLong());
            if (â˜ƒ != null) {
               LevelChunk â˜ƒx = â˜ƒ.getTickingChunk();
               if (â˜ƒx != null) {
                  this.playerLoadedChunk(â˜ƒ, â˜ƒ, â˜ƒx);
               }

               DebugPackets.sendPoiPacketsForChunk(this.level, â˜ƒ);
            }
         }

         if (!â˜ƒ && â˜ƒ) {
            â˜ƒ.untrackChunk(â˜ƒ);
         }
      }
   }

   public int size() {
      return this.visibleChunkMap.size();
   }

   protected net.minecraft.server.level.DistanceManager getDistanceManager() {
      return this.distanceManager;
   }

   protected Iterable<ChunkHolder> getChunks() {
      return Iterables.unmodifiableIterable(this.visibleChunkMap.values());
   }

   void dumpChunks(Writer var1) throws IOException {
      CsvOutput â˜ƒ = CsvOutput.builder()
         .addColumn("x")
         .addColumn("z")
         .addColumn("level")
         .addColumn("in_memory")
         .addColumn("status")
         .addColumn("full_status")
         .addColumn("accessible_ready")
         .addColumn("ticking_ready")
         .addColumn("entity_ticking_ready")
         .addColumn("ticket")
         .addColumn("spawning")
         .addColumn("block_entity_count")
         .build(â˜ƒ);

      for(Entry<ChunkHolder> â˜ƒx : this.visibleChunkMap.long2ObjectEntrySet()) {
         ChunkPos â˜ƒxx = new ChunkPos(â˜ƒx.getLongKey());
         ChunkHolder â˜ƒxxx = (ChunkHolder)â˜ƒx.getValue();
         Optional<ChunkAccess> â˜ƒxxxx = Optional.ofNullable(â˜ƒxxx.getLastAvailable());
         Optional<LevelChunk> â˜ƒxxxxx = â˜ƒxxxx.flatMap(var0 -> var0 instanceof LevelChunk ? Optional.of((LevelChunk)var0) : Optional.empty());
         â˜ƒ.writeRow(
            â˜ƒxx.x,
            â˜ƒxx.z,
            â˜ƒxxx.getTicketLevel(),
            â˜ƒxxxx.isPresent(),
            â˜ƒxxxx.map(ChunkAccess::getStatus).orElse(null),
            â˜ƒxxxxx.map(LevelChunk::getFullStatus).orElse(null),
            printFuture(â˜ƒxxx.getFullChunkFuture()),
            printFuture(â˜ƒxxx.getTickingChunkFuture()),
            printFuture(â˜ƒxxx.getEntityTickingChunkFuture()),
            this.distanceManager.getTicketDebugString(â˜ƒx.getLongKey()),
            !this.noPlayersCloseForSpawning(â˜ƒxx),
            â˜ƒxxxxx.map(var0 -> var0.getBlockEntities().size()).orElse(0)
         );
      }
   }

   private static String printFuture(CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> var0) {
      try {
         Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> â˜ƒ = (Either)â˜ƒ.getNow(null);
         return â˜ƒ != null ? â˜ƒ.map(var0x -> "done", var0x -> "unloaded") : "not completed";
      } catch (CompletionException var2) {
         return "failed " + var2.getCause().getMessage();
      } catch (CancellationException var3) {
         return "cancelled";
      }
   }

   @Nullable
   private CompoundTag readChunk(ChunkPos var1) throws IOException {
      CompoundTag â˜ƒ = this.read(â˜ƒ);
      return â˜ƒ == null ? null : this.upgradeChunkTag(this.level.dimension(), this.overworldDataStorage, â˜ƒ);
   }

   boolean noPlayersCloseForSpawning(ChunkPos var1) {
      long â˜ƒ = â˜ƒ.toLong();
      return !this.distanceManager.hasPlayersNearby(â˜ƒ)
         ? true
         : this.playerMap.getPlayers(â˜ƒ).noneMatch(var1x -> !var1x.isSpectator() && euclideanDistanceSquared(â˜ƒ, var1x) < 16384.0);
   }

   private boolean skipPlayer(ServerPlayer var1) {
      return â˜ƒ.isSpectator() && !this.level.getGameRules().getBoolean(GameRules.RULE_SPECTATORSGENERATECHUNKS);
   }

   void updatePlayerStatus(ServerPlayer var1, boolean var2) {
      boolean â˜ƒ = this.skipPlayer(â˜ƒ);
      boolean â˜ƒx = this.playerMap.ignoredOrUnknown(â˜ƒ);
      int â˜ƒxx = SectionPos.blockToSectionCoord(â˜ƒ.getBlockX());
      int â˜ƒxxx = SectionPos.blockToSectionCoord(â˜ƒ.getBlockZ());
      if (â˜ƒ) {
         this.playerMap.addPlayer(ChunkPos.asLong(â˜ƒxx, â˜ƒxxx), â˜ƒ, â˜ƒ);
         this.updatePlayerPos(â˜ƒ);
         if (!â˜ƒ) {
            this.distanceManager.addPlayer(SectionPos.of(â˜ƒ), â˜ƒ);
         }
      } else {
         SectionPos â˜ƒ = â˜ƒ.getLastSectionPos();
         this.playerMap.removePlayer(â˜ƒ.chunk().toLong(), â˜ƒ);
         if (!â˜ƒx) {
            this.distanceManager.removePlayer(â˜ƒ, â˜ƒ);
         }
      }

      for(int â˜ƒ = â˜ƒxx - this.viewDistance; â˜ƒ <= â˜ƒxx + this.viewDistance; ++â˜ƒ) {
         for(int â˜ƒx = â˜ƒxxx - this.viewDistance; â˜ƒx <= â˜ƒxxx + this.viewDistance; ++â˜ƒx) {
            ChunkPos â˜ƒxx = new ChunkPos(â˜ƒ, â˜ƒx);
            this.updateChunkTracking(â˜ƒ, â˜ƒxx, new Packet[2], !â˜ƒ, â˜ƒ);
         }
      }
   }

   private SectionPos updatePlayerPos(ServerPlayer var1) {
      SectionPos â˜ƒ = SectionPos.of(â˜ƒ);
      â˜ƒ.setLastSectionPos(â˜ƒ);
      â˜ƒ.connection.send(new ClientboundSetChunkCacheCenterPacket(â˜ƒ.x(), â˜ƒ.z()));
      return â˜ƒ;
   }

   public void move(ServerPlayer var1) {
      for(ChunkMap.TrackedEntity â˜ƒ : this.entityMap.values()) {
         if (â˜ƒ.entity == â˜ƒ) {
            â˜ƒ.updatePlayers(this.level.players());
         } else {
            â˜ƒ.updatePlayer(â˜ƒ);
         }
      }

      int â˜ƒ = SectionPos.blockToSectionCoord(â˜ƒ.getBlockX());
      int â˜ƒx = SectionPos.blockToSectionCoord(â˜ƒ.getBlockZ());
      SectionPos â˜ƒxx = â˜ƒ.getLastSectionPos();
      SectionPos â˜ƒxxx = SectionPos.of(â˜ƒ);
      long â˜ƒxxxx = â˜ƒxx.chunk().toLong();
      long â˜ƒxxxxx = â˜ƒxxx.chunk().toLong();
      boolean â˜ƒxxxxxx = this.playerMap.ignored(â˜ƒ);
      boolean â˜ƒxxxxxxx = this.skipPlayer(â˜ƒ);
      boolean â˜ƒxxxxxxxx = â˜ƒxx.asLong() != â˜ƒxxx.asLong();
      if (â˜ƒxxxxxxxx || â˜ƒxxxxxx != â˜ƒxxxxxxx) {
         this.updatePlayerPos(â˜ƒ);
         if (!â˜ƒxxxxxx) {
            this.distanceManager.removePlayer(â˜ƒxx, â˜ƒ);
         }

         if (!â˜ƒxxxxxxx) {
            this.distanceManager.addPlayer(â˜ƒxxx, â˜ƒ);
         }

         if (!â˜ƒxxxxxx && â˜ƒxxxxxxx) {
            this.playerMap.ignorePlayer(â˜ƒ);
         }

         if (â˜ƒxxxxxx && !â˜ƒxxxxxxx) {
            this.playerMap.unIgnorePlayer(â˜ƒ);
         }

         if (â˜ƒxxxx != â˜ƒxxxxx) {
            this.playerMap.updatePlayer(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒ);
         }
      }

      int â˜ƒ = â˜ƒxx.x();
      int â˜ƒx = â˜ƒxx.z();
      if (Math.abs(â˜ƒ - â˜ƒ) <= this.viewDistance * 2 && Math.abs(â˜ƒx - â˜ƒx) <= this.viewDistance * 2) {
         int â˜ƒxx = Math.min(â˜ƒ, â˜ƒ) - this.viewDistance;
         int â˜ƒxxx = Math.min(â˜ƒx, â˜ƒx) - this.viewDistance;
         int â˜ƒxxxx = Math.max(â˜ƒ, â˜ƒ) + this.viewDistance;
         int â˜ƒxxxxx = Math.max(â˜ƒx, â˜ƒx) + this.viewDistance;

         for(int â˜ƒxxxxxx = â˜ƒxx; â˜ƒxxxxxx <= â˜ƒxxxx; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = â˜ƒxxx; â˜ƒxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxx) {
               ChunkPos â˜ƒxxxxxxxx = new ChunkPos(â˜ƒxxxxxx, â˜ƒxxxxxxx);
               boolean â˜ƒxxxxxxxxx = checkerboardDistance(â˜ƒxxxxxxxx, â˜ƒ, â˜ƒx) <= this.viewDistance;
               boolean â˜ƒxxxxxxxxxx = checkerboardDistance(â˜ƒxxxxxxxx, â˜ƒ, â˜ƒx) <= this.viewDistance;
               this.updateChunkTracking(â˜ƒ, â˜ƒxxxxxxxx, new Packet[2], â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx);
            }
         }
      } else {
         for(int â˜ƒ = â˜ƒ - this.viewDistance; â˜ƒ <= â˜ƒ + this.viewDistance; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒx - this.viewDistance; â˜ƒx <= â˜ƒx + this.viewDistance; ++â˜ƒx) {
               ChunkPos â˜ƒxx = new ChunkPos(â˜ƒ, â˜ƒx);
               boolean â˜ƒxxx = true;
               boolean â˜ƒxxxx = false;
               this.updateChunkTracking(â˜ƒ, â˜ƒxx, new Packet[2], true, false);
            }
         }

         for(int â˜ƒ = â˜ƒ - this.viewDistance; â˜ƒ <= â˜ƒ + this.viewDistance; ++â˜ƒ) {
            for(int â˜ƒx = â˜ƒx - this.viewDistance; â˜ƒx <= â˜ƒx + this.viewDistance; ++â˜ƒx) {
               ChunkPos â˜ƒxx = new ChunkPos(â˜ƒ, â˜ƒx);
               boolean â˜ƒxxx = false;
               boolean â˜ƒxxxx = true;
               this.updateChunkTracking(â˜ƒ, â˜ƒxx, new Packet[2], false, true);
            }
         }
      }
   }

   @Override
   public Stream<ServerPlayer> getPlayers(ChunkPos var1, boolean var2) {
      return this.playerMap.getPlayers(â˜ƒ.toLong()).filter(var3 -> {
         int â˜ƒ = checkerboardDistance(â˜ƒ, var3, true);
         if (â˜ƒ > this.viewDistance) {
            return false;
         } else {
            return !â˜ƒ || â˜ƒ == this.viewDistance;
         }
      });
   }

   protected void addEntity(Entity var1) {
      if (!(â˜ƒ instanceof EnderDragonPart)) {
         EntityType<?> â˜ƒ = â˜ƒ.getType();
         int â˜ƒx = â˜ƒ.clientTrackingRange() * 16;
         if (â˜ƒx != 0) {
            int â˜ƒxx = â˜ƒ.updateInterval();
            if (this.entityMap.containsKey(â˜ƒ.getId())) {
               throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("Entity is already tracked!"));
            } else {
               ChunkMap.TrackedEntity â˜ƒxxx = new ChunkMap.TrackedEntity(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ.trackDeltas());
               this.entityMap.put(â˜ƒ.getId(), â˜ƒxxx);
               â˜ƒxxx.updatePlayers(this.level.players());
               if (â˜ƒ instanceof ServerPlayer â˜ƒxx) {
                  this.updatePlayerStatus(â˜ƒxx, true);

                  for(ChunkMap.TrackedEntity â˜ƒxxxx : this.entityMap.values()) {
                     if (â˜ƒxxxx.entity != â˜ƒxx) {
                        â˜ƒxxxx.updatePlayer(â˜ƒxx);
                     }
                  }
               }
            }
         }
      }
   }

   protected void removeEntity(Entity var1) {
      if (â˜ƒ instanceof ServerPlayer â˜ƒ) {
         this.updatePlayerStatus(â˜ƒ, false);

         for(ChunkMap.TrackedEntity â˜ƒx : this.entityMap.values()) {
            â˜ƒx.removePlayer(â˜ƒ);
         }
      }

      ChunkMap.TrackedEntity â˜ƒ = this.entityMap.remove(â˜ƒ.getId());
      if (â˜ƒ != null) {
         â˜ƒ.broadcastRemoved();
      }
   }

   protected void tick() {
      List<ServerPlayer> â˜ƒ = Lists.<ServerPlayer>newArrayList();
      List<ServerPlayer> â˜ƒx = this.level.players();

      for(ChunkMap.TrackedEntity â˜ƒxx : this.entityMap.values()) {
         SectionPos â˜ƒxxx = â˜ƒxx.lastSectionPos;
         SectionPos â˜ƒxxxx = SectionPos.of(â˜ƒxx.entity);
         if (!Objects.equals(â˜ƒxxx, â˜ƒxxxx)) {
            â˜ƒxx.updatePlayers(â˜ƒx);
            Entity â˜ƒxxxxx = â˜ƒxx.entity;
            if (â˜ƒxxxxx instanceof ServerPlayer) {
               â˜ƒ.add((ServerPlayer)â˜ƒxxxxx);
            }

            â˜ƒxx.lastSectionPos = â˜ƒxxxx;
         }

         â˜ƒxx.serverEntity.sendChanges();
      }

      if (!â˜ƒ.isEmpty()) {
         for(ChunkMap.TrackedEntity â˜ƒxx : this.entityMap.values()) {
            â˜ƒxx.updatePlayers(â˜ƒ);
         }
      }
   }

   public void broadcast(Entity var1, Packet<?> var2) {
      ChunkMap.TrackedEntity â˜ƒ = this.entityMap.get(â˜ƒ.getId());
      if (â˜ƒ != null) {
         â˜ƒ.broadcast(â˜ƒ);
      }
   }

   protected void broadcastAndSend(Entity var1, Packet<?> var2) {
      ChunkMap.TrackedEntity â˜ƒ = this.entityMap.get(â˜ƒ.getId());
      if (â˜ƒ != null) {
         â˜ƒ.broadcastAndSend(â˜ƒ);
      }
   }

   private void playerLoadedChunk(ServerPlayer var1, Packet<?>[] var2, LevelChunk var3) {
      if (â˜ƒ[0] == null) {
         â˜ƒ[0] = new ClientboundLevelChunkPacket(â˜ƒ);
         â˜ƒ[1] = new ClientboundLightUpdatePacket(â˜ƒ.getPos(), this.lightEngine, null, null, true);
      }

      â˜ƒ.trackChunk(â˜ƒ.getPos(), â˜ƒ[0], â˜ƒ[1]);
      DebugPackets.sendPoiPacketsForChunk(this.level, â˜ƒ.getPos());
      List<Entity> â˜ƒ = Lists.<Entity>newArrayList();
      List<Entity> â˜ƒx = Lists.<Entity>newArrayList();

      for(ChunkMap.TrackedEntity â˜ƒxx : this.entityMap.values()) {
         Entity â˜ƒxxx = â˜ƒxx.entity;
         if (â˜ƒxxx != â˜ƒ && â˜ƒxxx.chunkPosition().equals(â˜ƒ.getPos())) {
            â˜ƒxx.updatePlayer(â˜ƒ);
            if (â˜ƒxxx instanceof Mob && ((Mob)â˜ƒxxx).getLeashHolder() != null) {
               â˜ƒ.add(â˜ƒxxx);
            }

            if (!â˜ƒxxx.getPassengers().isEmpty()) {
               â˜ƒx.add(â˜ƒxxx);
            }
         }
      }

      if (!â˜ƒ.isEmpty()) {
         for(Entity â˜ƒxx : â˜ƒ) {
            â˜ƒ.connection.send(new ClientboundSetEntityLinkPacket(â˜ƒxx, ((Mob)â˜ƒxx).getLeashHolder()));
         }
      }

      if (!â˜ƒx.isEmpty()) {
         for(Entity â˜ƒxx : â˜ƒx) {
            â˜ƒ.connection.send(new ClientboundSetPassengersPacket(â˜ƒxx));
         }
      }
   }

   protected PoiManager getPoiManager() {
      return this.poiManager;
   }

   public String getStorageName() {
      return this.storageName;
   }

   public CompletableFuture<Void> packTicks(LevelChunk var1) {
      return this.mainThreadExecutor.submit((Runnable)(() -> â˜ƒ.packTicks(this.level)));
   }

   void onFullChunkStatusChange(ChunkPos var1, ChunkHolder.FullChunkStatus var2) {
      this.chunkStatusListener.onChunkStatusChange(â˜ƒ, â˜ƒ);
   }

   class DistanceManager extends net.minecraft.server.level.DistanceManager {
      protected DistanceManager(Executor var2, Executor var3) {
         super(â˜ƒ, â˜ƒ);
      }

      @Override
      protected boolean isChunkToRemove(long var1) {
         return ChunkMap.this.toDrop.contains(â˜ƒ);
      }

      @Nullable
      @Override
      protected ChunkHolder getChunk(long var1) {
         return ChunkMap.this.getUpdatingChunkIfPresent(â˜ƒ);
      }

      @Nullable
      @Override
      protected ChunkHolder updateChunkScheduling(long var1, int var3, @Nullable ChunkHolder var4, int var5) {
         return ChunkMap.this.updateChunkScheduling(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   class TrackedEntity {
      final ServerEntity serverEntity;
      final Entity entity;
      private final int range;
      SectionPos lastSectionPos;
      private final Set<ServerPlayerConnection> seenBy = Sets.newIdentityHashSet();

      public TrackedEntity(Entity var2, int var3, int var4, boolean var5) {
         this.serverEntity = new ServerEntity(ChunkMap.this.level, â˜ƒ, â˜ƒ, â˜ƒ, this::broadcast);
         this.entity = â˜ƒ;
         this.range = â˜ƒ;
         this.lastSectionPos = SectionPos.of(â˜ƒ);
      }

      public boolean equals(Object var1) {
         if (â˜ƒ instanceof ChunkMap.TrackedEntity) {
            return ((ChunkMap.TrackedEntity)â˜ƒ).entity.getId() == this.entity.getId();
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.entity.getId();
      }

      public void broadcast(Packet<?> var1) {
         for(ServerPlayerConnection â˜ƒ : this.seenBy) {
            â˜ƒ.send(â˜ƒ);
         }
      }

      public void broadcastAndSend(Packet<?> var1) {
         this.broadcast(â˜ƒ);
         if (this.entity instanceof ServerPlayer) {
            ((ServerPlayer)this.entity).connection.send(â˜ƒ);
         }
      }

      public void broadcastRemoved() {
         for(ServerPlayerConnection â˜ƒ : this.seenBy) {
            this.serverEntity.removePairing(â˜ƒ.getPlayer());
         }
      }

      public void removePlayer(ServerPlayer var1) {
         if (this.seenBy.remove(â˜ƒ.connection)) {
            this.serverEntity.removePairing(â˜ƒ);
         }
      }

      public void updatePlayer(ServerPlayer var1) {
         if (â˜ƒ != this.entity) {
            Vec3 â˜ƒ = â˜ƒ.position().subtract(this.serverEntity.sentPos());
            int â˜ƒx = Math.min(this.getEffectiveRange(), (ChunkMap.this.viewDistance - 1) * 16);
            boolean â˜ƒxx = â˜ƒ.x >= (double)(-â˜ƒx)
               && â˜ƒ.x <= (double)â˜ƒx
               && â˜ƒ.z >= (double)(-â˜ƒx)
               && â˜ƒ.z <= (double)â˜ƒx
               && this.entity.broadcastToPlayer(â˜ƒ);
            if (â˜ƒxx) {
               if (this.seenBy.add(â˜ƒ.connection)) {
                  this.serverEntity.addPairing(â˜ƒ);
               }
            } else if (this.seenBy.remove(â˜ƒ.connection)) {
               this.serverEntity.removePairing(â˜ƒ);
            }
         }
      }

      private int scaledRange(int var1) {
         return ChunkMap.this.level.getServer().getScaledTrackingDistance(â˜ƒ);
      }

      private int getEffectiveRange() {
         int â˜ƒ = this.range;

         for(Entity â˜ƒx : this.entity.getIndirectPassengers()) {
            int â˜ƒxx = â˜ƒx.getType().clientTrackingRange() * 16;
            if (â˜ƒxx > â˜ƒ) {
               â˜ƒ = â˜ƒxx;
            }
         }

         return this.scaledRange(â˜ƒ);
      }

      public void updatePlayers(List<ServerPlayer> var1) {
         for(ServerPlayer â˜ƒ : â˜ƒ) {
            this.updatePlayer(â˜ƒ);
         }
      }
   }
}
