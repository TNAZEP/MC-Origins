package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TwistingVinesFeature extends Feature<NoneFeatureConfiguration> {
   public TwistingVinesFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      return place(â˜ƒ.level(), â˜ƒ.random(), â˜ƒ.origin(), 8, 4, 8);
   }

   public static boolean place(LevelAccessor var0, Random var1, BlockPos var2, int var3, int var4, int var5) {
      if (isInvalidPlacementLocation(â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         placeTwistingVines(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return true;
      }
   }

   private static void placeTwistingVines(LevelAccessor var0, Random var1, BlockPos var2, int var3, int var4, int var5) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ * â˜ƒ; ++â˜ƒx) {
         â˜ƒ.set(â˜ƒ).move(Mth.nextInt(â˜ƒ, -â˜ƒ, â˜ƒ), Mth.nextInt(â˜ƒ, -â˜ƒ, â˜ƒ), Mth.nextInt(â˜ƒ, -â˜ƒ, â˜ƒ));
         if (findFirstAirBlockAboveGround(â˜ƒ, â˜ƒ) && !isInvalidPlacementLocation(â˜ƒ, â˜ƒ)) {
            int â˜ƒxx = Mth.nextInt(â˜ƒ, 1, â˜ƒ);
            if (â˜ƒ.nextInt(6) == 0) {
               â˜ƒxx *= 2;
            }

            if (â˜ƒ.nextInt(5) == 0) {
               â˜ƒxx = 1;
            }

            int â˜ƒxx = 17;
            int â˜ƒxxx = 25;
            placeWeepingVinesColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, 17, 25);
         }
      }
   }

   private static boolean findFirstAirBlockAboveGround(LevelAccessor var0, BlockPos.MutableBlockPos var1) {
      do {
         â˜ƒ.move(0, -1, 0);
         if (â˜ƒ.isOutsideBuildHeight(â˜ƒ)) {
            return false;
         }
      } while(â˜ƒ.getBlockState(â˜ƒ).isAir());

      â˜ƒ.move(0, 1, 0);
      return true;
   }

   public static void placeWeepingVinesColumn(LevelAccessor var0, Random var1, BlockPos.MutableBlockPos var2, int var3, int var4, int var5) {
      for(int â˜ƒ = 1; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
            if (â˜ƒ == â˜ƒ || !â˜ƒ.isEmptyBlock(â˜ƒ.above())) {
               â˜ƒ.setBlock(â˜ƒ, Blocks.TWISTING_VINES.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Integer.valueOf(Mth.nextInt(â˜ƒ, â˜ƒ, â˜ƒ))), 2);
               break;
            }

            â˜ƒ.setBlock(â˜ƒ, Blocks.TWISTING_VINES_PLANT.defaultBlockState(), 2);
         }

         â˜ƒ.move(Direction.UP);
      }
   }

   private static boolean isInvalidPlacementLocation(LevelAccessor var0, BlockPos var1) {
      if (!â˜ƒ.isEmptyBlock(â˜ƒ)) {
         return true;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
         return !â˜ƒ.is(Blocks.NETHERRACK) && !â˜ƒ.is(Blocks.WARPED_NYLIUM) && !â˜ƒ.is(Blocks.WARPED_WART_BLOCK);
      }
   }
}
