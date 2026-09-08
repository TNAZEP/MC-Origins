package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

public class NetherForestVegetationFeature extends Feature<BlockPileConfiguration> {
   public NetherForestVegetationFeature(Codec<BlockPileConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<BlockPileConfiguration> var1) {
      return place(â˜ƒ.level(), â˜ƒ.random(), â˜ƒ.origin(), â˜ƒ.config(), 8, 4);
   }

   public static boolean place(LevelAccessor var0, Random var1, BlockPos var2, BlockPileConfiguration var3, int var4, int var5) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      if (!â˜ƒ.is(BlockTags.NYLIUM)) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.getY();
         if (â˜ƒ >= â˜ƒ.getMinBuildHeight() + 1 && â˜ƒ + 1 < â˜ƒ.getMaxBuildHeight()) {
            int â˜ƒx = 0;

            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ * â˜ƒ; ++â˜ƒxx) {
               BlockPos â˜ƒxxx = â˜ƒ.offset(â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ), â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ), â˜ƒ.nextInt(â˜ƒ) - â˜ƒ.nextInt(â˜ƒ));
               BlockState â˜ƒxxxx = â˜ƒ.stateProvider.getState(â˜ƒ, â˜ƒxxx);
               if (â˜ƒ.isEmptyBlock(â˜ƒxxx) && â˜ƒxxx.getY() > â˜ƒ.getMinBuildHeight() && â˜ƒxxxx.canSurvive(â˜ƒ, â˜ƒxxx)) {
                  â˜ƒ.setBlock(â˜ƒxxx, â˜ƒxxxx, 2);
                  ++â˜ƒx;
               }
            }

            return â˜ƒx > 0;
         } else {
            return false;
         }
      }
   }
}
