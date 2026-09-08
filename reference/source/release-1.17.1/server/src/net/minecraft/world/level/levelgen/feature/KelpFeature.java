package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class KelpFeature extends Feature<NoneFeatureConfiguration> {
   public KelpFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      int â˜ƒ = 0;
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      Random â˜ƒxxx = â˜ƒ.random();
      int â˜ƒxxxx = â˜ƒx.getHeight(Heightmap.Types.OCEAN_FLOOR, â˜ƒxx.getX(), â˜ƒxx.getZ());
      BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx.getX(), â˜ƒxxxx, â˜ƒxx.getZ());
      if (â˜ƒx.getBlockState(â˜ƒxxxxx).is(Blocks.WATER)) {
         BlockState â˜ƒxxxxxx = Blocks.KELP.defaultBlockState();
         BlockState â˜ƒxxxxxxx = Blocks.KELP_PLANT.defaultBlockState();
         int â˜ƒxxxxxxxx = 1 + â˜ƒxxx.nextInt(10);

         for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx <= â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxx) {
            if (â˜ƒx.getBlockState(â˜ƒxxxxx).is(Blocks.WATER) && â˜ƒx.getBlockState(â˜ƒxxxxx.above()).is(Blocks.WATER) && â˜ƒxxxxxxx.canSurvive(â˜ƒx, â˜ƒxxxxx)
               )
             {
               if (â˜ƒxxxxxxxxx == â˜ƒxxxxxxxx) {
                  â˜ƒx.setBlock(â˜ƒxxxxx, â˜ƒxxxxxx.setValue(KelpBlock.AGE, Integer.valueOf(â˜ƒxxx.nextInt(4) + 20)), 2);
                  ++â˜ƒ;
               } else {
                  â˜ƒx.setBlock(â˜ƒxxxxx, â˜ƒxxxxxxx, 2);
               }
            } else if (â˜ƒxxxxxxxxx > 0) {
               BlockPos â˜ƒxxxxxxxxxx = â˜ƒxxxxx.below();
               if (â˜ƒxxxxxx.canSurvive(â˜ƒx, â˜ƒxxxxxxxxxx) && !â˜ƒx.getBlockState(â˜ƒxxxxxxxxxx.below()).is(Blocks.KELP)) {
                  â˜ƒx.setBlock(â˜ƒxxxxxxxxxx, â˜ƒxxxxxx.setValue(KelpBlock.AGE, Integer.valueOf(â˜ƒxxx.nextInt(4) + 20)), 2);
                  ++â˜ƒ;
               }
               break;
            }

            â˜ƒxxxxx = â˜ƒxxxxx.above();
         }
      }

      return â˜ƒ > 0;
   }
}
