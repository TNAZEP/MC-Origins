package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;

public class SimpleBlockFeature extends Feature<SimpleBlockConfiguration> {
   public SimpleBlockFeature(Codec<SimpleBlockConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<SimpleBlockConfiguration> var1) {
      SimpleBlockConfiguration â˜ƒ = â˜ƒ.config();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      if ((â˜ƒ.placeOn.isEmpty() || â˜ƒ.placeOn.contains(â˜ƒx.getBlockState(â˜ƒxx.below())))
         && (â˜ƒ.placeIn.isEmpty() || â˜ƒ.placeIn.contains(â˜ƒx.getBlockState(â˜ƒxx)))
         && (â˜ƒ.placeUnder.isEmpty() || â˜ƒ.placeUnder.contains(â˜ƒx.getBlockState(â˜ƒxx.above())))) {
         BlockState â˜ƒxxx = â˜ƒ.toPlace.getState(â˜ƒ.random(), â˜ƒxx);
         if (â˜ƒxxx.canSurvive(â˜ƒx, â˜ƒxx)) {
            if (â˜ƒxxx.getBlock() instanceof DoublePlantBlock) {
               if (!â˜ƒx.isEmptyBlock(â˜ƒxx.above())) {
                  return false;
               }

               DoublePlantBlock.placeAt(â˜ƒx, â˜ƒxxx, â˜ƒxx, 2);
            } else {
               â˜ƒx.setBlock(â˜ƒxx, â˜ƒxxx, 2);
            }

            return true;
         }
      }

      return false;
   }
}
