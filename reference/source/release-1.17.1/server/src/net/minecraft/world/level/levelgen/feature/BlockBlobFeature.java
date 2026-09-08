package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BlockBlobFeature extends Feature<BlockStateConfiguration> {
   public BlockBlobFeature(Codec<BlockStateConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<BlockStateConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();

      BlockStateConfiguration â˜ƒ;
      for(â˜ƒ = â˜ƒ.config(); â˜ƒ.getY() > â˜ƒx.getMinBuildHeight() + 3; â˜ƒ = â˜ƒ.below()) {
         if (!â˜ƒx.isEmptyBlock(â˜ƒ.below())) {
            BlockState â˜ƒxxx = â˜ƒx.getBlockState(â˜ƒ.below());
            if (isDirt(â˜ƒxxx) || isStone(â˜ƒxxx)) {
               break;
            }
         }
      }

      if (â˜ƒ.getY() <= â˜ƒx.getMinBuildHeight() + 3) {
         return false;
      } else {
         for(int â˜ƒxxx = 0; â˜ƒxxx < 3; ++â˜ƒxxx) {
            int â˜ƒxxxx = â˜ƒxx.nextInt(2);
            int â˜ƒxxxxx = â˜ƒxx.nextInt(2);
            int â˜ƒxxxxxx = â˜ƒxx.nextInt(2);
            float â˜ƒxxxxxxx = (float)(â˜ƒxxxx + â˜ƒxxxxx + â˜ƒxxxxxx) * 0.333F + 0.5F;

            for(BlockPos â˜ƒxxxxxxxx : BlockPos.betweenClosed(â˜ƒ.offset(-â˜ƒxxxx, -â˜ƒxxxxx, -â˜ƒxxxxxx), â˜ƒ.offset(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx))) {
               if (â˜ƒxxxxxxxx.distSqr(â˜ƒ) <= (double)(â˜ƒxxxxxxx * â˜ƒxxxxxxx)) {
                  â˜ƒx.setBlock(â˜ƒxxxxxxxx, â˜ƒ.state, 4);
               }
            }

            â˜ƒ = â˜ƒ.offset(-1 + â˜ƒxx.nextInt(2), -â˜ƒxx.nextInt(2), -1 + â˜ƒxx.nextInt(2));
         }

         return true;
      }
   }
}
