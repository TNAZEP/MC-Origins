package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SmallDripstoneConfiguration;

public class SmallDripstoneFeature extends Feature<SmallDripstoneConfiguration> {
   public SmallDripstoneFeature(Codec<SmallDripstoneConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<SmallDripstoneConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      SmallDripstoneConfiguration â˜ƒxxx = â˜ƒ.config();
      if (!DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒx)) {
         return false;
      } else {
         int â˜ƒ = Mth.randomBetweenInclusive(â˜ƒxx, 1, â˜ƒxxx.maxPlacements);
         boolean â˜ƒx = false;

         for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
            BlockPos â˜ƒxxx = randomOffset(â˜ƒxx, â˜ƒx, â˜ƒxxx);
            if (searchAndTryToPlaceDripstone(â˜ƒ, â˜ƒxx, â˜ƒxxx, â˜ƒxxx)) {
               â˜ƒx = true;
            }
         }

         return â˜ƒx;
      }
   }

   private static boolean searchAndTryToPlaceDripstone(WorldGenLevel var0, Random var1, BlockPos var2, SmallDripstoneConfiguration var3) {
      Direction â˜ƒ = Direction.getRandom(â˜ƒ);
      Direction â˜ƒx = â˜ƒ.nextBoolean() ? Direction.UP : Direction.DOWN;
      BlockPos.MutableBlockPos â˜ƒxx = â˜ƒ.mutable();

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ.emptySpaceSearchRadius; ++â˜ƒxxx) {
         if (!DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒxx)) {
            return false;
         }

         if (tryToPlaceDripstone(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒ)) {
            return true;
         }

         if (tryToPlaceDripstone(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒx.getOpposite(), â˜ƒ)) {
            return true;
         }

         â˜ƒxx.move(â˜ƒ);
      }

      return false;
   }

   private static boolean tryToPlaceDripstone(WorldGenLevel var0, Random var1, BlockPos var2, Direction var3, SmallDripstoneConfiguration var4) {
      if (!DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.getOpposite());
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         if (!DripstoneUtils.isDripstoneBase(â˜ƒx)) {
            return false;
         } else {
            createPatchOfDripstoneBlocks(â˜ƒ, â˜ƒ, â˜ƒ);
            int â˜ƒ = â˜ƒ.nextFloat() < â˜ƒ.chanceOfTallerDripstone && DripstoneUtils.isEmptyOrWater(â˜ƒ, â˜ƒ.relative(â˜ƒ)) ? 2 : 1;
            DripstoneUtils.growPointedDripstone(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
            return true;
         }
      }
   }

   private static void createPatchOfDripstoneBlocks(WorldGenLevel var0, Random var1, BlockPos var2) {
      DripstoneUtils.placeDripstoneBlockIfPossible(â˜ƒ, â˜ƒ);

      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         if (!(â˜ƒ.nextFloat() < 0.3F)) {
            BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
            DripstoneUtils.placeDripstoneBlockIfPossible(â˜ƒ, â˜ƒx);
            if (!â˜ƒ.nextBoolean()) {
               BlockPos â˜ƒxx = â˜ƒx.relative(Direction.getRandom(â˜ƒ));
               DripstoneUtils.placeDripstoneBlockIfPossible(â˜ƒ, â˜ƒxx);
               if (!â˜ƒ.nextBoolean()) {
                  BlockPos â˜ƒxxx = â˜ƒxx.relative(Direction.getRandom(â˜ƒ));
                  DripstoneUtils.placeDripstoneBlockIfPossible(â˜ƒ, â˜ƒxxx);
               }
            }
         }
      }
   }

   private static BlockPos randomOffset(Random var0, BlockPos var1, SmallDripstoneConfiguration var2) {
      return â˜ƒ.offset(
         Mth.randomBetweenInclusive(â˜ƒ, -â˜ƒ.maxOffsetFromOrigin, â˜ƒ.maxOffsetFromOrigin),
         Mth.randomBetweenInclusive(â˜ƒ, -â˜ƒ.maxOffsetFromOrigin, â˜ƒ.maxOffsetFromOrigin),
         Mth.randomBetweenInclusive(â˜ƒ, -â˜ƒ.maxOffsetFromOrigin, â˜ƒ.maxOffsetFromOrigin)
      );
   }
}
