package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;

public class DripstoneClusterFeature extends Feature<DripstoneClusterConfiguration> {
   public DripstoneClusterFeature(Codec<DripstoneClusterConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<DripstoneClusterConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      DripstoneClusterConfiguration â˜ƒxx = â˜ƒ.config();
      Random â˜ƒxxx = â˜ƒ.random();
      if (!DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒx)) {
         return false;
      } else {
         int â˜ƒ = â˜ƒxx.height.sample(â˜ƒxxx);
         float â˜ƒx = â˜ƒxx.wetness.sample(â˜ƒxxx);
         float â˜ƒxx = â˜ƒxx.density.sample(â˜ƒxxx);
         int â˜ƒxxx = â˜ƒxx.radius.sample(â˜ƒxxx);
         int â˜ƒxxxx = â˜ƒxx.radius.sample(â˜ƒxxx);

         for(int â˜ƒxxxxx = -â˜ƒxxx; â˜ƒxxxxx <= â˜ƒxxx; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = -â˜ƒxxxx; â˜ƒxxxxxx <= â˜ƒxxxx; ++â˜ƒxxxxxx) {
               double â˜ƒxxxxxxx = this.getChanceOfStalagmiteOrStalactite(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxx);
               BlockPos â˜ƒxxxxxxxx = â˜ƒx.offset(â˜ƒxxxxx, 0, â˜ƒxxxxxx);
               this.placeColumn(â˜ƒ, â˜ƒxxx, â˜ƒxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒx, â˜ƒxxxxxxx, â˜ƒ, â˜ƒxx, â˜ƒxx);
            }
         }

         return true;
      }
   }

   private void placeColumn(
      WorldGenLevel var1, Random var2, BlockPos var3, int var4, int var5, float var6, double var7, int var9, float var10, DripstoneClusterConfiguration var11
   ) {
      Optional<Column> â˜ƒ = Column.scan(â˜ƒ, â˜ƒ, â˜ƒ.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
      if (â˜ƒ.isPresent()) {
         OptionalInt â˜ƒx = ((Column)â˜ƒ.get()).getCeiling();
         OptionalInt â˜ƒxx = ((Column)â˜ƒ.get()).getFloor();
         if (â˜ƒx.isPresent() || â˜ƒxx.isPresent()) {
            boolean â˜ƒxxxx = â˜ƒ.nextFloat() < â˜ƒ;
            Column â˜ƒxxx;
            if (â˜ƒxxxx && â˜ƒxx.isPresent() && this.canPlacePool(â˜ƒ, â˜ƒ.atY(â˜ƒxx.getAsInt()))) {
               int â˜ƒxxxxx = â˜ƒxx.getAsInt();
               â˜ƒxxx = ((Column)â˜ƒ.get()).withFloor(OptionalInt.of(â˜ƒxxxxx - 1));
               â˜ƒ.setBlock(â˜ƒ.atY(â˜ƒxxxxx), Blocks.WATER.defaultBlockState(), 2);
            } else {
               â˜ƒxxx = (Column)â˜ƒ.get();
            }

            OptionalInt â˜ƒxxxx = â˜ƒxxx.getFloor();
            boolean â˜ƒxxxxx = â˜ƒ.nextDouble() < â˜ƒ;
            int â˜ƒxxx;
            if (â˜ƒx.isPresent() && â˜ƒxxxxx && !this.isLava(â˜ƒ, â˜ƒ.atY(â˜ƒx.getAsInt()))) {
               int â˜ƒxxxxxxx = â˜ƒ.dripstoneBlockLayerThickness.sample(â˜ƒ);
               this.replaceBlocksWithDripstoneBlocks(â˜ƒ, â˜ƒ.atY(â˜ƒx.getAsInt()), â˜ƒxxxxxxx, Direction.UP);
               int â˜ƒxxxxxx;
               if (â˜ƒxxxx.isPresent()) {
                  â˜ƒxxxxxx = Math.min(â˜ƒ, â˜ƒx.getAsInt() - â˜ƒxxxx.getAsInt());
               } else {
                  â˜ƒxxxxxx = â˜ƒ;
               }

               â˜ƒxxx = this.getDripstoneHeight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒ);
            } else {
               â˜ƒxxx = 0;
            }

            boolean â˜ƒxxxx = â˜ƒ.nextDouble() < â˜ƒ;
            int â˜ƒxxx;
            if (â˜ƒxxxx.isPresent() && â˜ƒxxxx && !this.isLava(â˜ƒ, â˜ƒ.atY(â˜ƒxxxx.getAsInt()))) {
               int â˜ƒxxxxx = â˜ƒ.dripstoneBlockLayerThickness.sample(â˜ƒ);
               this.replaceBlocksWithDripstoneBlocks(â˜ƒ, â˜ƒ.atY(â˜ƒxxxx.getAsInt()), â˜ƒxxxxx, Direction.DOWN);
               â˜ƒxxx = Math.max(0, â˜ƒxxx + Mth.randomBetweenInclusive(â˜ƒ, -â˜ƒ.maxStalagmiteStalactiteHeightDiff, â˜ƒ.maxStalagmiteStalactiteHeightDiff));
            } else {
               â˜ƒxxx = 0;
            }

            int â˜ƒxxx;
            int â˜ƒxxxx;
            if (â˜ƒx.isPresent() && â˜ƒxxxx.isPresent() && â˜ƒx.getAsInt() - â˜ƒxxx <= â˜ƒxxxx.getAsInt() + â˜ƒxxx) {
               int â˜ƒxxxxx = â˜ƒxxxx.getAsInt();
               int â˜ƒxxxxxx = â˜ƒx.getAsInt();
               int â˜ƒxxxxxxx = Math.max(â˜ƒxxxxxx - â˜ƒxxx, â˜ƒxxxxx + 1);
               int â˜ƒxxxxxxxx = Math.min(â˜ƒxxxxx + â˜ƒxxx, â˜ƒxxxxxx - 1);
               int â˜ƒxxxxxxxxx = Mth.randomBetweenInclusive(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx + 1);
               int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx - 1;
               â˜ƒxxxx = â˜ƒxxxxxx - â˜ƒxxxxxxxxx;
               â˜ƒxxx = â˜ƒxxxxxxxxxx - â˜ƒxxxxx;
            } else {
               â˜ƒxxxx = â˜ƒxxx;
               â˜ƒxxx = â˜ƒxxx;
            }

            boolean â˜ƒxxx = â˜ƒ.nextBoolean()
               && â˜ƒxxxx > 0
               && â˜ƒxxx > 0
               && â˜ƒxxx.getHeight().isPresent()
               && â˜ƒxxxx + â˜ƒxxx == â˜ƒxxx.getHeight().getAsInt();
            if (â˜ƒx.isPresent()) {
               DripstoneUtils.growPointedDripstone(â˜ƒ, â˜ƒ.atY(â˜ƒx.getAsInt() - 1), Direction.DOWN, â˜ƒxxxx, â˜ƒxxx);
            }

            if (â˜ƒxxxx.isPresent()) {
               DripstoneUtils.growPointedDripstone(â˜ƒ, â˜ƒ.atY(â˜ƒxxxx.getAsInt() + 1), Direction.UP, â˜ƒxxx, â˜ƒxxx);
            }
         }
      }
   }

   private boolean isLava(LevelReader var1, BlockPos var2) {
      return â˜ƒ.getBlockState(â˜ƒ).is(Blocks.LAVA);
   }

   private int getDripstoneHeight(Random var1, int var2, int var3, float var4, int var5, DripstoneClusterConfiguration var6) {
      if (â˜ƒ.nextFloat() > â˜ƒ) {
         return 0;
      } else {
         int â˜ƒ = Math.abs(â˜ƒ) + Math.abs(â˜ƒ);
         float â˜ƒx = (float)Mth.clampedMap((double)â˜ƒ, 0.0, (double)â˜ƒ.maxDistanceFromCenterAffectingHeightBias, (double)â˜ƒ / 2.0, 0.0);
         return (int)randomBetweenBiased(â˜ƒ, 0.0F, (float)â˜ƒ, â˜ƒx, (float)â˜ƒ.heightDeviation);
      }
   }

   private boolean canPlacePool(WorldGenLevel var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!â˜ƒ.is(Blocks.WATER) && !â˜ƒ.is(Blocks.DRIPSTONE_BLOCK) && !â˜ƒ.is(Blocks.POINTED_DRIPSTONE)) {
         for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
            if (!this.canBeAdjacentToWater(â˜ƒ, â˜ƒ.relative(â˜ƒx))) {
               return false;
            }
         }

         return this.canBeAdjacentToWater(â˜ƒ, â˜ƒ.below());
      } else {
         return false;
      }
   }

   private boolean canBeAdjacentToWater(LevelAccessor var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.is(BlockTags.BASE_STONE_OVERWORLD) || â˜ƒ.getFluidState().is(FluidTags.WATER);
   }

   private void replaceBlocksWithDripstoneBlocks(WorldGenLevel var1, BlockPos var2, int var3, Direction var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; ++â˜ƒx) {
         if (!DripstoneUtils.placeDripstoneBlockIfPossible(â˜ƒ, â˜ƒ)) {
            return;
         }

         â˜ƒ.move(â˜ƒ);
      }
   }

   private double getChanceOfStalagmiteOrStalactite(int var1, int var2, int var3, int var4, DripstoneClusterConfiguration var5) {
      int â˜ƒ = â˜ƒ - Math.abs(â˜ƒ);
      int â˜ƒx = â˜ƒ - Math.abs(â˜ƒ);
      int â˜ƒxx = Math.min(â˜ƒ, â˜ƒx);
      return Mth.clampedMap(
         (double)â˜ƒxx, 0.0, (double)â˜ƒ.maxDistanceFromEdgeAffectingChanceOfDripstoneColumn, (double)â˜ƒ.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0
      );
   }

   private static float randomBetweenBiased(Random var0, float var1, float var2, float var3, float var4) {
      return ClampedNormalFloat.sample(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
