package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class GrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {
   public GrassBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return â˜ƒ.getBlockState(â˜ƒ.above()).isAir();
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      BlockState â˜ƒx = Blocks.GRASS.defaultBlockState();

      label48:
      for(int â˜ƒxx = 0; â˜ƒxx < 128; ++â˜ƒxx) {
         BlockPos â˜ƒxxx = â˜ƒ;

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxx / 16; ++â˜ƒxxxx) {
            â˜ƒxxx = â˜ƒxxx.offset(â˜ƒ.nextInt(3) - 1, (â˜ƒ.nextInt(3) - 1) * â˜ƒ.nextInt(3) / 2, â˜ƒ.nextInt(3) - 1);
            if (!â˜ƒ.getBlockState(â˜ƒxxx.below()).is(this) || â˜ƒ.getBlockState(â˜ƒxxx).isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxx)) {
               continue label48;
            }
         }

         BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
         if (â˜ƒxxxx.is(â˜ƒx.getBlock()) && â˜ƒ.nextInt(10) == 0) {
            ((BonemealableBlock)â˜ƒx.getBlock()).performBonemeal(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx);
         }

         if (â˜ƒxxxx.isAir()) {
            BlockState â˜ƒxxxx;
            if (â˜ƒ.nextInt(8) == 0) {
               List<ConfiguredFeature<?, ?>> â˜ƒxxxxx = â˜ƒ.getBiome(â˜ƒxxx).getGenerationSettings().getFlowerFeatures();
               if (â˜ƒxxxxx.isEmpty()) {
                  continue;
               }

               â˜ƒxxxx = getBlockState(â˜ƒ, â˜ƒxxx, (ConfiguredFeature)â˜ƒxxxxx.get(0));
            } else {
               â˜ƒxxxx = â˜ƒx;
            }

            if (â˜ƒxxxx.canSurvive(â˜ƒ, â˜ƒxxx)) {
               â˜ƒ.setBlock(â˜ƒxxx, â˜ƒxxxx, 3);
            }
         }
      }
   }

   private static <U extends FeatureConfiguration> BlockState getBlockState(Random var0, BlockPos var1, ConfiguredFeature<U, ?> var2) {
      AbstractFlowerFeature<U> â˜ƒ = (AbstractFlowerFeature)â˜ƒ.feature;
      return â˜ƒ.getRandomFlower(â˜ƒ, â˜ƒ, â˜ƒ.config());
   }
}
