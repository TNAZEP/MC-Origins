package net.minecraft.world.entity.ai.village.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.SectionTracker;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.storage.SectionStorage;

public class PoiManager extends SectionStorage<PoiSection> {
   public static final int MAX_VILLAGE_DISTANCE = 6;
   public static final int VILLAGE_SECTION_SIZE = 1;
   private final PoiManager.DistanceTracker distanceTracker;
   private final LongSet loadedChunks = new LongOpenHashSet();

   public PoiManager(File var1, DataFixer var2, boolean var3, LevelHeightAccessor var4) {
      super(â˜ƒ, PoiSection::codec, PoiSection::new, â˜ƒ, DataFixTypes.POI_CHUNK, â˜ƒ, â˜ƒ);
      this.distanceTracker = new PoiManager.DistanceTracker();
   }

   public void add(BlockPos var1, PoiType var2) {
      this.getOrCreate(SectionPos.asLong(â˜ƒ)).add(â˜ƒ, â˜ƒ);
   }

   public void remove(BlockPos var1) {
      this.getOrLoad(SectionPos.asLong(â˜ƒ)).ifPresent(var1x -> var1x.remove(â˜ƒ));
   }

   public long getCountInRange(Predicate<PoiType> var1, BlockPos var2, int var3, PoiManager.Occupancy var4) {
      return this.getInRange(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).count();
   }

   public boolean existsAtPosition(PoiType var1, BlockPos var2) {
      return this.exists(â˜ƒ, â˜ƒ::equals);
   }

   public Stream<PoiRecord> getInSquare(Predicate<PoiType> var1, BlockPos var2, int var3, PoiManager.Occupancy var4) {
      int â˜ƒ = Math.floorDiv(â˜ƒ, 16) + 1;
      return ChunkPos.rangeClosed(new ChunkPos(â˜ƒ), â˜ƒ).flatMap(var3x -> this.getInChunk(â˜ƒ, var3x, â˜ƒ)).filter(var2x -> {
         BlockPos â˜ƒ = var2x.getPos();
         return Math.abs(â˜ƒ.getX() - â˜ƒ.getX()) <= â˜ƒ && Math.abs(â˜ƒ.getZ() - â˜ƒ.getZ()) <= â˜ƒ;
      });
   }

   public Stream<PoiRecord> getInRange(Predicate<PoiType> var1, BlockPos var2, int var3, PoiManager.Occupancy var4) {
      int â˜ƒ = â˜ƒ * â˜ƒ;
      return this.getInSquare(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).filter(var2x -> var2x.getPos().distSqr(â˜ƒ) <= (double)â˜ƒ);
   }

   @VisibleForDebug
   public Stream<PoiRecord> getInChunk(Predicate<PoiType> var1, ChunkPos var2, PoiManager.Occupancy var3) {
      return IntStream.range(this.levelHeightAccessor.getMinSection(), this.levelHeightAccessor.getMaxSection())
         .boxed()
         .map(var2x -> this.getOrLoad(SectionPos.of(â˜ƒ, var2x).asLong()))
         .filter(Optional::isPresent)
         .flatMap(var2x -> ((PoiSection)var2x.get()).getRecords(â˜ƒ, â˜ƒ));
   }

   public Stream<BlockPos> findAll(Predicate<PoiType> var1, Predicate<BlockPos> var2, BlockPos var3, int var4, PoiManager.Occupancy var5) {
      return this.getInRange(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).map(PoiRecord::getPos).filter(â˜ƒ);
   }

   public Stream<BlockPos> findAllClosestFirst(Predicate<PoiType> var1, Predicate<BlockPos> var2, BlockPos var3, int var4, PoiManager.Occupancy var5) {
      return this.findAll(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).sorted(Comparator.comparingDouble(var1x -> var1x.distSqr(â˜ƒ)));
   }

   public Optional<BlockPos> find(Predicate<PoiType> var1, Predicate<BlockPos> var2, BlockPos var3, int var4, PoiManager.Occupancy var5) {
      return this.findAll(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).findFirst();
   }

   public Optional<BlockPos> findClosest(Predicate<PoiType> var1, BlockPos var2, int var3, PoiManager.Occupancy var4) {
      return this.getInRange(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).map(PoiRecord::getPos).min(Comparator.comparingDouble(var1x -> var1x.distSqr(â˜ƒ)));
   }

   public Optional<BlockPos> findClosest(Predicate<PoiType> var1, Predicate<BlockPos> var2, BlockPos var3, int var4, PoiManager.Occupancy var5) {
      return this.getInRange(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).map(PoiRecord::getPos).filter(â˜ƒ).min(Comparator.comparingDouble(var1x -> var1x.distSqr(â˜ƒ)));
   }

   public Optional<BlockPos> take(Predicate<PoiType> var1, Predicate<BlockPos> var2, BlockPos var3, int var4) {
      return this.getInRange(â˜ƒ, â˜ƒ, â˜ƒ, PoiManager.Occupancy.HAS_SPACE).filter(var1x -> â˜ƒ.test(var1x.getPos())).findFirst().map(var0 -> {
         var0.acquireTicket();
         return var0.getPos();
      });
   }

   public Optional<BlockPos> getRandom(Predicate<PoiType> var1, Predicate<BlockPos> var2, PoiManager.Occupancy var3, BlockPos var4, int var5, Random var6) {
      List<PoiRecord> â˜ƒ = (List)this.getInRange(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).collect(Collectors.toList());
      Collections.shuffle(â˜ƒ, â˜ƒ);
      return â˜ƒ.stream().filter(var1x -> â˜ƒ.test(var1x.getPos())).findFirst().map(PoiRecord::getPos);
   }

   public boolean release(BlockPos var1) {
      return this.getOrLoad(SectionPos.asLong(â˜ƒ))
         .map(var1x -> var1x.release(â˜ƒ))
         .orElseThrow(() -> Util.pauseInIde(new IllegalStateException("POI never registered at " + â˜ƒ)));
   }

   public boolean exists(BlockPos var1, Predicate<PoiType> var2) {
      return this.getOrLoad(SectionPos.asLong(â˜ƒ)).map(var2x -> var2x.exists(â˜ƒ, â˜ƒ)).orElse(false);
   }

   public Optional<PoiType> getType(BlockPos var1) {
      return this.getOrLoad(SectionPos.asLong(â˜ƒ)).flatMap(var1x -> var1x.getType(â˜ƒ));
   }

   @Deprecated
   @VisibleForDebug
   public int getFreeTickets(BlockPos var1) {
      return this.getOrLoad(SectionPos.asLong(â˜ƒ)).map(var1x -> var1x.getFreeTickets(â˜ƒ)).orElse(0);
   }

   public int sectionsToVillage(SectionPos var1) {
      this.distanceTracker.runAllUpdates();
      return this.distanceTracker.getLevel(â˜ƒ.asLong());
   }

   boolean isVillageCenter(long var1) {
      Optional<PoiSection> â˜ƒ = this.get(â˜ƒ);
      return â˜ƒ == null ? false : â˜ƒ.map(var0 -> var0.getRecords(PoiType.ALL, PoiManager.Occupancy.IS_OCCUPIED).count() > 0L).orElse(false);
   }

   @Override
   public void tick(BooleanSupplier var1) {
      super.tick(â˜ƒ);
      this.distanceTracker.runAllUpdates();
   }

   @Override
   protected void setDirty(long var1) {
      super.setDirty(â˜ƒ);
      this.distanceTracker.update(â˜ƒ, this.distanceTracker.getLevelFromSource(â˜ƒ), false);
   }

   @Override
   protected void onSectionLoad(long var1) {
      this.distanceTracker.update(â˜ƒ, this.distanceTracker.getLevelFromSource(â˜ƒ), false);
   }

   public void checkConsistencyWithBlocks(ChunkPos var1, LevelChunkSection var2) {
      SectionPos â˜ƒ = SectionPos.of(â˜ƒ, SectionPos.blockToSectionCoord(â˜ƒ.bottomBlockY()));
      Util.ifElse(this.getOrLoad(â˜ƒ.asLong()), var3x -> var3x.refresh(var3xx -> {
            if (mayHavePoi(â˜ƒ)) {
               this.updateFromSection(â˜ƒ, â˜ƒ, var3xx);
            }
         }), () -> {
         if (mayHavePoi(â˜ƒ)) {
            PoiSection â˜ƒ = this.getOrCreate(â˜ƒ.asLong());
            this.updateFromSection(â˜ƒ, â˜ƒ, â˜ƒ::add);
         }
      });
   }

   private static boolean mayHavePoi(LevelChunkSection var0) {
      return â˜ƒ.maybeHas(PoiType.ALL_STATES::contains);
   }

   private void updateFromSection(LevelChunkSection var1, SectionPos var2, BiConsumer<BlockPos, PoiType> var3) {
      â˜ƒ.blocksInside()
         .forEach(
            var2x -> {
               BlockState â˜ƒ = â˜ƒ.getBlockState(
                  SectionPos.sectionRelative(var2x.getX()), SectionPos.sectionRelative(var2x.getY()), SectionPos.sectionRelative(var2x.getZ())
               );
               PoiType.forState(â˜ƒ).ifPresent(var2xx -> â˜ƒ.accept(var2x, var2xx));
            }
         );
   }

   public void ensureLoadedAndValid(LevelReader var1, BlockPos var2, int var3) {
      SectionPos.aroundChunk(new ChunkPos(â˜ƒ), Math.floorDiv(â˜ƒ, 16), this.levelHeightAccessor.getMinSection(), this.levelHeightAccessor.getMaxSection())
         .map(var1x -> Pair.of(var1x, this.getOrLoad(var1x.asLong())))
         .filter(var0 -> !((Optional)var0.getSecond()).map(PoiSection::isValid).orElse(false))
         .map(var0 -> ((SectionPos)var0.getFirst()).chunk())
         .filter(var1x -> this.loadedChunks.add(var1x.toLong()))
         .forEach(var1x -> â˜ƒ.getChunk(var1x.x, var1x.z, ChunkStatus.EMPTY));
   }

   final class DistanceTracker extends SectionTracker {
      private final Long2ByteMap levels = new Long2ByteOpenHashMap();

      protected DistanceTracker() {
         super(7, 16, 256);
         this.levels.defaultReturnValue((byte)7);
      }

      @Override
      protected int getLevelFromSource(long var1) {
         return PoiManager.this.isVillageCenter(â˜ƒ) ? 0 : 7;
      }

      @Override
      protected int getLevel(long var1) {
         return this.levels.get(â˜ƒ);
      }

      @Override
      protected void setLevel(long var1, int var3) {
         if (â˜ƒ > 6) {
            this.levels.remove(â˜ƒ);
         } else {
            this.levels.put(â˜ƒ, (byte)â˜ƒ);
         }
      }

      public void runAllUpdates() {
         super.runUpdates(Integer.MAX_VALUE);
      }
   }

   public static enum Occupancy {
      HAS_SPACE(PoiRecord::hasSpace),
      IS_OCCUPIED(PoiRecord::isOccupied),
      ANY(var0 -> true);

      private final Predicate<? super PoiRecord> test;

      private Occupancy(Predicate<? super PoiRecord> var3) {
         this.test = â˜ƒ;
      }

      public Predicate<? super PoiRecord> getTest() {
         return this.test;
      }
   }
}
