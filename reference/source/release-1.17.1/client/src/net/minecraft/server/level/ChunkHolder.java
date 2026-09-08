package net.minecraft.server.level;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLightUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.util.DebugBuffer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

public class ChunkHolder {
   public static final Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> UNLOADED_CHUNK = Either.right(ChunkHolder.ChunkLoadingFailure.UNLOADED);
   public static final CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> UNLOADED_CHUNK_FUTURE = CompletableFuture.completedFuture(
      UNLOADED_CHUNK
   );
   public static final Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> UNLOADED_LEVEL_CHUNK = Either.right(ChunkHolder.ChunkLoadingFailure.UNLOADED);
   private static final CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> UNLOADED_LEVEL_CHUNK_FUTURE = CompletableFuture.completedFuture(
      UNLOADED_LEVEL_CHUNK
   );
   private static final List<ChunkStatus> CHUNK_STATUSES = ChunkStatus.getStatusList();
   private static final ChunkHolder.FullChunkStatus[] FULL_CHUNK_STATUSES = ChunkHolder.FullChunkStatus.values();
   private static final int BLOCKS_BEFORE_RESEND_FUDGE = 64;
   private final AtomicReferenceArray<CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> futures = new AtomicReferenceArray(
      CHUNK_STATUSES.size()
   );
   private final LevelHeightAccessor levelHeightAccessor;
   private volatile CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> fullChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
   private volatile CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> tickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
   private volatile CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> entityTickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
   private CompletableFuture<ChunkAccess> chunkToSave = CompletableFuture.completedFuture(null);
   @Nullable
   private final DebugBuffer<ChunkHolder.ChunkSaveDebug> chunkToSaveHistory = null;
   private int oldTicketLevel;
   private int ticketLevel;
   private int queueLevel;
   final ChunkPos pos;
   private boolean hasChangedSections;
   private final ShortSet[] changedBlocksPerSection;
   private final BitSet blockChangedLightSectionFilter = new BitSet();
   private final BitSet skyChangedLightSectionFilter = new BitSet();
   private final LevelLightEngine lightEngine;
   private final ChunkHolder.LevelChangeListener onLevelChange;
   private final ChunkHolder.PlayerProvider playerProvider;
   private boolean wasAccessibleSinceLastSave;
   private boolean resendLight;
   private CompletableFuture<Void> pendingFullStateConfirmation = CompletableFuture.completedFuture(null);

   public ChunkHolder(
      ChunkPos var1, int var2, LevelHeightAccessor var3, LevelLightEngine var4, ChunkHolder.LevelChangeListener var5, ChunkHolder.PlayerProvider var6
   ) {
      this.pos = â˜ƒ;
      this.levelHeightAccessor = â˜ƒ;
      this.lightEngine = â˜ƒ;
      this.onLevelChange = â˜ƒ;
      this.playerProvider = â˜ƒ;
      this.oldTicketLevel = ChunkMap.MAX_CHUNK_DISTANCE + 1;
      this.ticketLevel = this.oldTicketLevel;
      this.queueLevel = this.oldTicketLevel;
      this.setTicketLevel(â˜ƒ);
      this.changedBlocksPerSection = new ShortSet[â˜ƒ.getSectionsCount()];
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getFutureIfPresentUnchecked(ChunkStatus var1) {
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒ = (CompletableFuture)this.futures.get(â˜ƒ.getIndex());
      return â˜ƒ == null ? UNLOADED_CHUNK_FUTURE : â˜ƒ;
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getFutureIfPresent(ChunkStatus var1) {
      return getStatus(this.ticketLevel).isOrAfter(â˜ƒ) ? this.getFutureIfPresentUnchecked(â˜ƒ) : UNLOADED_CHUNK_FUTURE;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> getTickingChunkFuture() {
      return this.tickingChunkFuture;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> getEntityTickingChunkFuture() {
      return this.entityTickingChunkFuture;
   }

   public CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> getFullChunkFuture() {
      return this.fullChunkFuture;
   }

   @Nullable
   public LevelChunk getTickingChunk() {
      CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> â˜ƒ = this.getTickingChunkFuture();
      Either<LevelChunk, ChunkHolder.ChunkLoadingFailure> â˜ƒx = (Either)â˜ƒ.getNow(null);
      return â˜ƒx == null ? null : (LevelChunk)â˜ƒx.left().orElse(null);
   }

   @Nullable
   public ChunkStatus getLastAvailableStatus() {
      for(int â˜ƒ = CHUNK_STATUSES.size() - 1; â˜ƒ >= 0; --â˜ƒ) {
         ChunkStatus â˜ƒx = (ChunkStatus)CHUNK_STATUSES.get(â˜ƒ);
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒxx = this.getFutureIfPresentUnchecked(â˜ƒx);
         if (((Either)â˜ƒxx.getNow(UNLOADED_CHUNK)).left().isPresent()) {
            return â˜ƒx;
         }
      }

      return null;
   }

   @Nullable
   public ChunkAccess getLastAvailable() {
      for(int â˜ƒ = CHUNK_STATUSES.size() - 1; â˜ƒ >= 0; --â˜ƒ) {
         ChunkStatus â˜ƒx = (ChunkStatus)CHUNK_STATUSES.get(â˜ƒ);
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒxx = this.getFutureIfPresentUnchecked(â˜ƒx);
         if (!â˜ƒxx.isCompletedExceptionally()) {
            Optional<ChunkAccess> â˜ƒxxx = ((Either)â˜ƒxx.getNow(UNLOADED_CHUNK)).left();
            if (â˜ƒxxx.isPresent()) {
               return (ChunkAccess)â˜ƒxxx.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<ChunkAccess> getChunkToSave() {
      return this.chunkToSave;
   }

   public void blockChanged(BlockPos var1) {
      LevelChunk â˜ƒ = this.getTickingChunk();
      if (â˜ƒ != null) {
         int â˜ƒx = this.levelHeightAccessor.getSectionIndex(â˜ƒ.getY());
         if (this.changedBlocksPerSection[â˜ƒx] == null) {
            this.hasChangedSections = true;
            this.changedBlocksPerSection[â˜ƒx] = new ShortArraySet();
         }

         this.changedBlocksPerSection[â˜ƒx].add(SectionPos.sectionRelativePos(â˜ƒ));
      }
   }

   public void sectionLightChanged(LightLayer var1, int var2) {
      LevelChunk â˜ƒ = this.getTickingChunk();
      if (â˜ƒ != null) {
         â˜ƒ.setUnsaved(true);
         int â˜ƒx = this.lightEngine.getMinLightSection();
         int â˜ƒxx = this.lightEngine.getMaxLightSection();
         if (â˜ƒ >= â˜ƒx && â˜ƒ <= â˜ƒxx) {
            int â˜ƒxxx = â˜ƒ - â˜ƒx;
            if (â˜ƒ == LightLayer.SKY) {
               this.skyChangedLightSectionFilter.set(â˜ƒxxx);
            } else {
               this.blockChangedLightSectionFilter.set(â˜ƒxxx);
            }
         }
      }
   }

   public void broadcastChanges(LevelChunk var1) {
      if (this.hasChangedSections || !this.skyChangedLightSectionFilter.isEmpty() || !this.blockChangedLightSectionFilter.isEmpty()) {
         Level â˜ƒ = â˜ƒ.getLevel();
         int â˜ƒx = 0;

         for(int â˜ƒxx = 0; â˜ƒxx < this.changedBlocksPerSection.length; ++â˜ƒxx) {
            â˜ƒx += this.changedBlocksPerSection[â˜ƒxx] != null ? this.changedBlocksPerSection[â˜ƒxx].size() : 0;
         }

         this.resendLight |= â˜ƒx >= 64;
         if (!this.skyChangedLightSectionFilter.isEmpty() || !this.blockChangedLightSectionFilter.isEmpty()) {
            this.broadcast(
               new ClientboundLightUpdatePacket(â˜ƒ.getPos(), this.lightEngine, this.skyChangedLightSectionFilter, this.blockChangedLightSectionFilter, true),
               !this.resendLight
            );
            this.skyChangedLightSectionFilter.clear();
            this.blockChangedLightSectionFilter.clear();
         }

         for(int â˜ƒxx = 0; â˜ƒxx < this.changedBlocksPerSection.length; ++â˜ƒxx) {
            ShortSet â˜ƒxxx = this.changedBlocksPerSection[â˜ƒxx];
            if (â˜ƒxxx != null) {
               int â˜ƒxxxx = this.levelHeightAccessor.getSectionYFromSectionIndex(â˜ƒxx);
               SectionPos â˜ƒxxxxx = SectionPos.of(â˜ƒ.getPos(), â˜ƒxxxx);
               if (â˜ƒxxx.size() == 1) {
                  BlockPos â˜ƒxxxxxx = â˜ƒxxxxx.relativeToBlockPos(â˜ƒxxx.iterator().nextShort());
                  BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
                  this.broadcast(new ClientboundBlockUpdatePacket(â˜ƒxxxxxx, â˜ƒxxxxxxx), false);
                  this.broadcastBlockEntityIfNeeded(â˜ƒ, â˜ƒxxxxxx, â˜ƒxxxxxxx);
               } else {
                  LevelChunkSection â˜ƒxxxx = â˜ƒ.getSections()[â˜ƒxx];
                  ClientboundSectionBlocksUpdatePacket â˜ƒxxxxx = new ClientboundSectionBlocksUpdatePacket(â˜ƒxxxxx, â˜ƒxxx, â˜ƒxxxx, this.resendLight);
                  this.broadcast(â˜ƒxxxxx, false);
                  â˜ƒxxxxx.runUpdates((var2x, var3x) -> this.broadcastBlockEntityIfNeeded(â˜ƒ, var2x, var3x));
               }

               this.changedBlocksPerSection[â˜ƒxx] = null;
            }
         }

         this.hasChangedSections = false;
      }
   }

   private void broadcastBlockEntityIfNeeded(Level var1, BlockPos var2, BlockState var3) {
      if (â˜ƒ.hasBlockEntity()) {
         this.broadcastBlockEntity(â˜ƒ, â˜ƒ);
      }
   }

   private void broadcastBlockEntity(Level var1, BlockPos var2) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ != null) {
         ClientboundBlockEntityDataPacket â˜ƒx = â˜ƒ.getUpdatePacket();
         if (â˜ƒx != null) {
            this.broadcast(â˜ƒx, false);
         }
      }
   }

   private void broadcast(Packet<?> var1, boolean var2) {
      this.playerProvider.getPlayers(this.pos, â˜ƒ).forEach(var1x -> var1x.connection.send(â˜ƒ));
   }

   public CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> getOrScheduleFuture(ChunkStatus var1, ChunkMap var2) {
      int â˜ƒ = â˜ƒ.getIndex();
      CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒx = (CompletableFuture)this.futures.get(â˜ƒ);
      if (â˜ƒx != null) {
         Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> â˜ƒxx = (Either)â˜ƒx.getNow(null);
         boolean â˜ƒxxx = â˜ƒxx != null && â˜ƒxx.right().isPresent();
         if (!â˜ƒxxx) {
            return â˜ƒx;
         }
      }

      if (getStatus(this.ticketLevel).isOrAfter(â˜ƒ)) {
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒ = â˜ƒ.schedule(this, â˜ƒ);
         this.updateChunkToSave(â˜ƒ, "schedule " + â˜ƒ);
         this.futures.set(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      } else {
         return â˜ƒx == null ? UNLOADED_CHUNK_FUTURE : â˜ƒx;
      }
   }

   private void updateChunkToSave(CompletableFuture<? extends Either<? extends ChunkAccess, ChunkHolder.ChunkLoadingFailure>> var1, String var2) {
      if (this.chunkToSaveHistory != null) {
         this.chunkToSaveHistory.push(new ChunkHolder.ChunkSaveDebug(Thread.currentThread(), â˜ƒ, â˜ƒ));
      }

      this.chunkToSave = this.chunkToSave.thenCombine(â˜ƒ, (var0, var1x) -> var1x.map(var0x -> var0x, var1xx -> var0));
   }

   public ChunkHolder.FullChunkStatus getFullStatus() {
      return getFullChunkStatus(this.ticketLevel);
   }

   public ChunkPos getPos() {
      return this.pos;
   }

   public int getTicketLevel() {
      return this.ticketLevel;
   }

   public int getQueueLevel() {
      return this.queueLevel;
   }

   private void setQueueLevel(int var1) {
      this.queueLevel = â˜ƒ;
   }

   public void setTicketLevel(int var1) {
      this.ticketLevel = â˜ƒ;
   }

   private void scheduleFullChunkPromotion(
      ChunkMap var1, CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> var2, Executor var3, ChunkHolder.FullChunkStatus var4
   ) {
      this.pendingFullStateConfirmation.cancel(false);
      CompletableFuture<Void> â˜ƒ = new CompletableFuture();
      â˜ƒ.thenRunAsync(() -> â˜ƒ.onFullChunkStatusChange(this.pos, â˜ƒ), â˜ƒ);
      this.pendingFullStateConfirmation = â˜ƒ;
      â˜ƒ.thenAccept(var1x -> var1x.ifLeft(var1xx -> â˜ƒ.complete(null)));
   }

   private void demoteFullChunk(ChunkMap var1, ChunkHolder.FullChunkStatus var2) {
      this.pendingFullStateConfirmation.cancel(false);
      â˜ƒ.onFullChunkStatusChange(this.pos, â˜ƒ);
   }

   protected void updateFutures(ChunkMap var1, Executor var2) {
      ChunkStatus â˜ƒ = getStatus(this.oldTicketLevel);
      ChunkStatus â˜ƒx = getStatus(this.ticketLevel);
      boolean â˜ƒxx = this.oldTicketLevel <= ChunkMap.MAX_CHUNK_DISTANCE;
      boolean â˜ƒxxx = this.ticketLevel <= ChunkMap.MAX_CHUNK_DISTANCE;
      ChunkHolder.FullChunkStatus â˜ƒxxxx = getFullChunkStatus(this.oldTicketLevel);
      ChunkHolder.FullChunkStatus â˜ƒxxxxx = getFullChunkStatus(this.ticketLevel);
      if (â˜ƒxx) {
         Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure> â˜ƒxxxxxx = Either.right(new ChunkHolder.ChunkLoadingFailure() {
            public String toString() {
               return "Unloaded ticket level " + ChunkHolder.this.pos;
            }
         });

         for(int â˜ƒxxxxxxx = â˜ƒxxx ? â˜ƒx.getIndex() + 1 : 0; â˜ƒxxxxxxx <= â˜ƒ.getIndex(); ++â˜ƒxxxxxxx) {
            CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒxxxxxxxx = (CompletableFuture)this.futures.get(â˜ƒxxxxxxx);
            if (â˜ƒxxxxxxxx == null) {
               this.futures.set(â˜ƒxxxxxxx, CompletableFuture.completedFuture(â˜ƒxxxxxx));
            }
         }
      }

      boolean â˜ƒ = â˜ƒxxxx.isOrAfter(ChunkHolder.FullChunkStatus.BORDER);
      boolean â˜ƒx = â˜ƒxxxxx.isOrAfter(ChunkHolder.FullChunkStatus.BORDER);
      this.wasAccessibleSinceLastSave |= â˜ƒx;
      if (!â˜ƒ && â˜ƒx) {
         this.fullChunkFuture = â˜ƒ.prepareAccessibleChunk(this);
         this.scheduleFullChunkPromotion(â˜ƒ, this.fullChunkFuture, â˜ƒ, ChunkHolder.FullChunkStatus.BORDER);
         this.updateChunkToSave(this.fullChunkFuture, "full");
      }

      if (â˜ƒ && !â˜ƒx) {
         CompletableFuture<Either<LevelChunk, ChunkHolder.ChunkLoadingFailure>> â˜ƒ = this.fullChunkFuture;
         this.fullChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
         this.updateChunkToSave(â˜ƒ.thenApply(var1x -> var1x.ifLeft(â˜ƒ::packTicks)), "unfull");
      }

      boolean â˜ƒ = â˜ƒxxxx.isOrAfter(ChunkHolder.FullChunkStatus.TICKING);
      boolean â˜ƒx = â˜ƒxxxxx.isOrAfter(ChunkHolder.FullChunkStatus.TICKING);
      if (!â˜ƒ && â˜ƒx) {
         this.tickingChunkFuture = â˜ƒ.prepareTickingChunk(this);
         this.scheduleFullChunkPromotion(â˜ƒ, this.tickingChunkFuture, â˜ƒ, ChunkHolder.FullChunkStatus.TICKING);
         this.updateChunkToSave(this.tickingChunkFuture, "ticking");
      }

      if (â˜ƒ && !â˜ƒx) {
         this.tickingChunkFuture.complete(UNLOADED_LEVEL_CHUNK);
         this.tickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
      }

      boolean â˜ƒ = â˜ƒxxxx.isOrAfter(ChunkHolder.FullChunkStatus.ENTITY_TICKING);
      boolean â˜ƒx = â˜ƒxxxxx.isOrAfter(ChunkHolder.FullChunkStatus.ENTITY_TICKING);
      if (!â˜ƒ && â˜ƒx) {
         if (this.entityTickingChunkFuture != UNLOADED_LEVEL_CHUNK_FUTURE) {
            throw (IllegalStateException)Util.pauseInIde(new IllegalStateException());
         }

         this.entityTickingChunkFuture = â˜ƒ.prepareEntityTickingChunk(this.pos);
         this.scheduleFullChunkPromotion(â˜ƒ, this.entityTickingChunkFuture, â˜ƒ, ChunkHolder.FullChunkStatus.ENTITY_TICKING);
         this.updateChunkToSave(this.entityTickingChunkFuture, "entity ticking");
      }

      if (â˜ƒ && !â˜ƒx) {
         this.entityTickingChunkFuture.complete(UNLOADED_LEVEL_CHUNK);
         this.entityTickingChunkFuture = UNLOADED_LEVEL_CHUNK_FUTURE;
      }

      if (!â˜ƒxxxxx.isOrAfter(â˜ƒxxxx)) {
         this.demoteFullChunk(â˜ƒ, â˜ƒxxxxx);
      }

      this.onLevelChange.onLevelChange(this.pos, this::getQueueLevel, this.ticketLevel, this::setQueueLevel);
      this.oldTicketLevel = this.ticketLevel;
   }

   public static ChunkStatus getStatus(int var0) {
      return â˜ƒ < 33 ? ChunkStatus.FULL : ChunkStatus.getStatusAroundFullChunk(â˜ƒ - 33);
   }

   public static ChunkHolder.FullChunkStatus getFullChunkStatus(int var0) {
      return FULL_CHUNK_STATUSES[Mth.clamp(33 - â˜ƒ + 1, 0, FULL_CHUNK_STATUSES.length - 1)];
   }

   public boolean wasAccessibleSinceLastSave() {
      return this.wasAccessibleSinceLastSave;
   }

   public void refreshAccessibility() {
      this.wasAccessibleSinceLastSave = getFullChunkStatus(this.ticketLevel).isOrAfter(ChunkHolder.FullChunkStatus.BORDER);
   }

   public void replaceProtoChunk(ImposterProtoChunk var1) {
      for(int â˜ƒ = 0; â˜ƒ < this.futures.length(); ++â˜ƒ) {
         CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> â˜ƒx = (CompletableFuture)this.futures.get(â˜ƒ);
         if (â˜ƒx != null) {
            Optional<ChunkAccess> â˜ƒxx = ((Either)â˜ƒx.getNow(UNLOADED_CHUNK)).left();
            if (â˜ƒxx.isPresent() && â˜ƒxx.get() instanceof ProtoChunk) {
               this.futures.set(â˜ƒ, CompletableFuture.completedFuture(Either.left(â˜ƒ)));
            }
         }
      }

      this.updateChunkToSave(CompletableFuture.completedFuture(Either.left(â˜ƒ.getWrapped())), "replaceProto");
   }

   public interface ChunkLoadingFailure {
      ChunkHolder.ChunkLoadingFailure UNLOADED = new ChunkHolder.ChunkLoadingFailure() {
         public String toString() {
            return "UNLOADED";
         }
      };
   }

   static final class ChunkSaveDebug {
      private final Thread thread;
      private final CompletableFuture<? extends Either<? extends ChunkAccess, ChunkHolder.ChunkLoadingFailure>> future;
      private final String source;

      ChunkSaveDebug(Thread var1, CompletableFuture<? extends Either<? extends ChunkAccess, ChunkHolder.ChunkLoadingFailure>> var2, String var3) {
         this.thread = â˜ƒ;
         this.future = â˜ƒ;
         this.source = â˜ƒ;
      }
   }

   public static enum FullChunkStatus {
      INACCESSIBLE,
      BORDER,
      TICKING,
      ENTITY_TICKING;

      public boolean isOrAfter(ChunkHolder.FullChunkStatus var1) {
         return this.ordinal() >= â˜ƒ.ordinal();
      }
   }

   @FunctionalInterface
   public interface LevelChangeListener {
      void onLevelChange(ChunkPos var1, IntSupplier var2, int var3, IntConsumer var4);
   }

   public interface PlayerProvider {
      Stream<ServerPlayer> getPlayers(ChunkPos var1, boolean var2);
   }
}
