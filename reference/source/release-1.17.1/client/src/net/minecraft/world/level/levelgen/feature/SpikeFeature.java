package net.minecraft.world.level.levelgen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;
import net.minecraft.world.phys.AABB;

public class SpikeFeature extends Feature<SpikeConfiguration> {
   public static final int NUMBER_OF_SPIKES = 10;
   private static final int SPIKE_DISTANCE = 42;
   private static final LoadingCache<Long, List<SpikeFeature.EndSpike>> SPIKE_CACHE = CacheBuilder.newBuilder()
      .expireAfterWrite(5L, TimeUnit.MINUTES)
      .build(new SpikeFeature.SpikeCacheLoader());

   public SpikeFeature(Codec<SpikeConfiguration> var1) {
      super(â˜ƒ);
   }

   public static List<SpikeFeature.EndSpike> getSpikesForLevel(WorldGenLevel var0) {
      Random â˜ƒ = new Random(â˜ƒ.getSeed());
      long â˜ƒx = â˜ƒ.nextLong() & 65535L;
      return (List<SpikeFeature.EndSpike>)SPIKE_CACHE.getUnchecked(â˜ƒx);
   }

   @Override
   public boolean place(FeaturePlaceContext<SpikeConfiguration> var1) {
      SpikeConfiguration â˜ƒ = â˜ƒ.config();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      List<SpikeFeature.EndSpike> â˜ƒxxxx = â˜ƒ.getSpikes();
      if (â˜ƒxxxx.isEmpty()) {
         â˜ƒxxxx = getSpikesForLevel(â˜ƒx);
      }

      for(SpikeFeature.EndSpike â˜ƒ : â˜ƒxxxx) {
         if (â˜ƒ.isCenterWithinChunk(â˜ƒxxx)) {
            this.placeSpike(â˜ƒx, â˜ƒxx, â˜ƒ, â˜ƒ);
         }
      }

      return true;
   }

   private void placeSpike(ServerLevelAccessor var1, Random var2, SpikeConfiguration var3, SpikeFeature.EndSpike var4) {
      int â˜ƒ = â˜ƒ.getRadius();

      for(BlockPos â˜ƒx : BlockPos.betweenClosed(
         new BlockPos(â˜ƒ.getCenterX() - â˜ƒ, â˜ƒ.getMinBuildHeight(), â˜ƒ.getCenterZ() - â˜ƒ),
         new BlockPos(â˜ƒ.getCenterX() + â˜ƒ, â˜ƒ.getHeight() + 10, â˜ƒ.getCenterZ() + â˜ƒ)
      )) {
         if (â˜ƒx.distSqr((double)â˜ƒ.getCenterX(), (double)â˜ƒx.getY(), (double)â˜ƒ.getCenterZ(), false) <= (double)(â˜ƒ * â˜ƒ + 1)
            && â˜ƒx.getY() < â˜ƒ.getHeight()) {
            this.setBlock(â˜ƒ, â˜ƒx, Blocks.OBSIDIAN.defaultBlockState());
         } else if (â˜ƒx.getY() > 65) {
            this.setBlock(â˜ƒ, â˜ƒx, Blocks.AIR.defaultBlockState());
         }
      }

      if (â˜ƒ.isGuarded()) {
         int â˜ƒx = -2;
         int â˜ƒxx = 2;
         int â˜ƒxxx = 3;
         BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxxx = -2; â˜ƒxxxxx <= 2; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = -2; â˜ƒxxxxxx <= 2; ++â˜ƒxxxxxx) {
               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx <= 3; ++â˜ƒxxxxxxx) {
                  boolean â˜ƒxxxxxxxx = Mth.abs(â˜ƒxxxxx) == 2;
                  boolean â˜ƒxxxxxxxxx = Mth.abs(â˜ƒxxxxxx) == 2;
                  boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx == 3;
                  if (â˜ƒxxxxxxxx || â˜ƒxxxxxxxxx || â˜ƒxxxxxxxxxx) {
                     boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxxx == -2 || â˜ƒxxxxx == 2 || â˜ƒxxxxxxxxxx;
                     boolean â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx == -2 || â˜ƒxxxxxx == 2 || â˜ƒxxxxxxxxxx;
                     BlockState â˜ƒxxxxxxxxxxxxx = Blocks.IRON_BARS
                        .defaultBlockState()
                        .setValue(IronBarsBlock.NORTH, Boolean.valueOf(â˜ƒxxxxxxxxxxx && â˜ƒxxxxxx != -2))
                        .setValue(IronBarsBlock.SOUTH, Boolean.valueOf(â˜ƒxxxxxxxxxxx && â˜ƒxxxxxx != 2))
                        .setValue(IronBarsBlock.WEST, Boolean.valueOf(â˜ƒxxxxxxxxxxxx && â˜ƒxxxxx != -2))
                        .setValue(IronBarsBlock.EAST, Boolean.valueOf(â˜ƒxxxxxxxxxxxx && â˜ƒxxxxx != 2));
                     this.setBlock(â˜ƒ, â˜ƒxxxx.set(â˜ƒ.getCenterX() + â˜ƒxxxxx, â˜ƒ.getHeight() + â˜ƒxxxxxxx, â˜ƒ.getCenterZ() + â˜ƒxxxxxx), â˜ƒxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }

      EndCrystal â˜ƒx = EntityType.END_CRYSTAL.create(â˜ƒ.getLevel());
      â˜ƒx.setBeamTarget(â˜ƒ.getCrystalBeamTarget());
      â˜ƒx.setInvulnerable(â˜ƒ.isCrystalInvulnerable());
      â˜ƒx.moveTo((double)â˜ƒ.getCenterX() + 0.5, (double)(â˜ƒ.getHeight() + 1), (double)â˜ƒ.getCenterZ() + 0.5, â˜ƒ.nextFloat() * 360.0F, 0.0F);
      â˜ƒ.addFreshEntity(â˜ƒx);
      this.setBlock(â˜ƒ, new BlockPos(â˜ƒ.getCenterX(), â˜ƒ.getHeight(), â˜ƒ.getCenterZ()), Blocks.BEDROCK.defaultBlockState());
   }

   public static class EndSpike {
      public static final Codec<SpikeFeature.EndSpike> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("centerX").orElse(0).forGetter(var0x -> var0x.centerX),
                  Codec.INT.fieldOf("centerZ").orElse(0).forGetter(var0x -> var0x.centerZ),
                  Codec.INT.fieldOf("radius").orElse(0).forGetter(var0x -> var0x.radius),
                  Codec.INT.fieldOf("height").orElse(0).forGetter(var0x -> var0x.height),
                  Codec.BOOL.fieldOf("guarded").orElse(false).forGetter(var0x -> var0x.guarded)
               )
               .apply(var0, SpikeFeature.EndSpike::new)
      );
      private final int centerX;
      private final int centerZ;
      private final int radius;
      private final int height;
      private final boolean guarded;
      private final AABB topBoundingBox;

      public EndSpike(int var1, int var2, int var3, int var4, boolean var5) {
         this.centerX = â˜ƒ;
         this.centerZ = â˜ƒ;
         this.radius = â˜ƒ;
         this.height = â˜ƒ;
         this.guarded = â˜ƒ;
         this.topBoundingBox = new AABB(
            (double)(â˜ƒ - â˜ƒ), (double)DimensionType.MIN_Y, (double)(â˜ƒ - â˜ƒ), (double)(â˜ƒ + â˜ƒ), (double)DimensionType.MAX_Y, (double)(â˜ƒ + â˜ƒ)
         );
      }

      public boolean isCenterWithinChunk(BlockPos var1) {
         return SectionPos.blockToSectionCoord(â˜ƒ.getX()) == SectionPos.blockToSectionCoord(this.centerX)
            && SectionPos.blockToSectionCoord(â˜ƒ.getZ()) == SectionPos.blockToSectionCoord(this.centerZ);
      }

      public int getCenterX() {
         return this.centerX;
      }

      public int getCenterZ() {
         return this.centerZ;
      }

      public int getRadius() {
         return this.radius;
      }

      public int getHeight() {
         return this.height;
      }

      public boolean isGuarded() {
         return this.guarded;
      }

      public AABB getTopBoundingBox() {
         return this.topBoundingBox;
      }
   }

   static class SpikeCacheLoader extends CacheLoader<Long, List<SpikeFeature.EndSpike>> {
      public List<SpikeFeature.EndSpike> load(Long var1) {
         List<Integer> â˜ƒ = (List)IntStream.range(0, 10).boxed().collect(Collectors.toList());
         Collections.shuffle(â˜ƒ, new Random(â˜ƒ));
         List<SpikeFeature.EndSpike> â˜ƒx = Lists.<SpikeFeature.EndSpike>newArrayList();

         for(int â˜ƒxx = 0; â˜ƒxx < 10; ++â˜ƒxx) {
            int â˜ƒxxx = Mth.floor(42.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 10) * (double)â˜ƒxx)));
            int â˜ƒxxxx = Mth.floor(42.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 10) * (double)â˜ƒxx)));
            int â˜ƒxxxxx = â˜ƒ.get(â˜ƒxx);
            int â˜ƒxxxxxx = 2 + â˜ƒxxxxx / 3;
            int â˜ƒxxxxxxx = 76 + â˜ƒxxxxx * 3;
            boolean â˜ƒxxxxxxxx = â˜ƒxxxxx == 1 || â˜ƒxxxxx == 2;
            â˜ƒx.add(new SpikeFeature.EndSpike(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx));
         }

         return â˜ƒx;
      }
   }
}
