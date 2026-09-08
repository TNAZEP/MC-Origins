package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WeepingVinesFeature extends Feature<NoneFeatureConfiguration> {
   private static final Direction[] DIRECTIONS = Direction.values();

   public WeepingVinesFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      if (!â˜ƒ.isEmptyBlock(â˜ƒx)) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒx.above());
         if (!â˜ƒ.is(Blocks.NETHERRACK) && !â˜ƒ.is(Blocks.NETHER_WART_BLOCK)) {
            return false;
         } else {
            this.placeRoofNetherWart(â˜ƒ, â˜ƒxx, â˜ƒx);
            this.placeRoofWeepingVines(â˜ƒ, â˜ƒxx, â˜ƒx);
            return true;
         }
      }
   }

   private void placeRoofNetherWart(LevelAccessor var1, Random var2, BlockPos var3) {
      â˜ƒ.setBlock(â˜ƒ, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 2);
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(int â˜ƒxx = 0; â˜ƒxx < 200; ++â˜ƒxx) {
         â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ.nextInt(6) - â˜ƒ.nextInt(6), â˜ƒ.nextInt(2) - â˜ƒ.nextInt(5), â˜ƒ.nextInt(6) - â˜ƒ.nextInt(6));
         if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
            int â˜ƒxxx = 0;

            for(Direction â˜ƒxxxx : DIRECTIONS) {
               BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxxxx));
               if (â˜ƒxxxxx.is(Blocks.NETHERRACK) || â˜ƒxxxxx.is(Blocks.NETHER_WART_BLOCK)) {
                  ++â˜ƒxxx;
               }

               if (â˜ƒxxx > 1) {
                  break;
               }
            }

            if (â˜ƒxxx == 1) {
               â˜ƒ.setBlock(â˜ƒ, Blocks.NETHER_WART_BLOCK.defaultBlockState(), 2);
            }
         }
      }
   }

   private void placeRoofWeepingVines(LevelAccessor var1, Random var2, BlockPos var3) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int â˜ƒx = 0; â˜ƒx < 100; ++â˜ƒx) {
         â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ.nextInt(8) - â˜ƒ.nextInt(8), â˜ƒ.nextInt(2) - â˜ƒ.nextInt(7), â˜ƒ.nextInt(8) - â˜ƒ.nextInt(8));
         if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.above());
            if (â˜ƒxx.is(Blocks.NETHERRACK) || â˜ƒxx.is(Blocks.NETHER_WART_BLOCK)) {
               int â˜ƒxxx = Mth.nextInt(â˜ƒ, 1, 8);
               if (â˜ƒ.nextInt(6) == 0) {
                  â˜ƒxxx *= 2;
               }

               if (â˜ƒ.nextInt(5) == 0) {
                  â˜ƒxxx = 1;
               }

               int â˜ƒxxx = 17;
               int â˜ƒxxxx = 25;
               placeWeepingVinesColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxxx, 17, 25);
            }
         }
      }
   }

   public static void placeWeepingVinesColumn(LevelAccessor var0, Random var1, BlockPos.MutableBlockPos var2, int var3, int var4, int var5) {
      for(int â˜ƒ = 0; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
            if (â˜ƒ == â˜ƒ || !â˜ƒ.isEmptyBlock(â˜ƒ.below())) {
               â˜ƒ.setBlock(â˜ƒ, Blocks.WEEPING_VINES.defaultBlockState().setValue(GrowingPlantHeadBlock.AGE, Integer.valueOf(Mth.nextInt(â˜ƒ, â˜ƒ, â˜ƒ))), 2);
               break;
            }

            â˜ƒ.setBlock(â˜ƒ, Blocks.WEEPING_VINES_PLANT.defaultBlockState(), 2);
         }

         â˜ƒ.move(Direction.DOWN);
      }
   }
}
