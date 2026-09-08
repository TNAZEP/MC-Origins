package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ChorusPlantFeature extends Feature<NoneFeatureConfiguration> {
   public ChorusPlantFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      if (â˜ƒ.isEmptyBlock(â˜ƒx) && â˜ƒ.getBlockState(â˜ƒx.below()).is(Blocks.END_STONE)) {
         ChorusFlowerBlock.generatePlant(â˜ƒ, â˜ƒx, â˜ƒxx, 8);
         return true;
      } else {
         return false;
      }
   }
}
