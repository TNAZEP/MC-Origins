package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class SeagrassFeature extends Feature<ProbabilityFeatureConfiguration> {
   public SeagrassFeature(Codec<ProbabilityFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> var1) {
      boolean â˜ƒ = false;
      Random â˜ƒx = â˜ƒ.random();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      ProbabilityFeatureConfiguration â˜ƒxxxx = â˜ƒ.config();
      int â˜ƒxxxxx = â˜ƒx.nextInt(8) - â˜ƒx.nextInt(8);
      int â˜ƒxxxxxx = â˜ƒx.nextInt(8) - â˜ƒx.nextInt(8);
      int â˜ƒxxxxxxx = â˜ƒxx.getHeight(Heightmap.Types.OCEAN_FLOOR, â˜ƒxxx.getX() + â˜ƒxxxxx, â˜ƒxxx.getZ() + â˜ƒxxxxxx);
      BlockPos â˜ƒxxxxxxxx = new BlockPos(â˜ƒxxx.getX() + â˜ƒxxxxx, â˜ƒxxxxxxx, â˜ƒxxx.getZ() + â˜ƒxxxxxx);
      if (â˜ƒxx.getBlockState(â˜ƒxxxxxxxx).is(Blocks.WATER)) {
         boolean â˜ƒxxxxxxxxx = â˜ƒx.nextDouble() < (double)â˜ƒxxxx.probability;
         BlockState â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx ? Blocks.TALL_SEAGRASS.defaultBlockState() : Blocks.SEAGRASS.defaultBlockState();
         if (â˜ƒxxxxxxxxxx.canSurvive(â˜ƒxx, â˜ƒxxxxxxxx)) {
            if (â˜ƒxxxxxxxxx) {
               BlockState â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
               BlockPos â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxx.above();
               if (â˜ƒxx.getBlockState(â˜ƒxxxxxxxxxxxx).is(Blocks.WATER)) {
                  â˜ƒxx.setBlock(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, 2);
                  â˜ƒxx.setBlock(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxx, 2);
               }
            } else {
               â˜ƒxx.setBlock(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxx, 2);
            }

            â˜ƒ = true;
         }
      }

      return â˜ƒ;
   }
}
