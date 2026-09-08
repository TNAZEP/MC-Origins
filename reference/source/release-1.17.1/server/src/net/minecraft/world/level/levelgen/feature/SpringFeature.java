package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;

public class SpringFeature extends Feature<SpringConfiguration> {
   public SpringFeature(Codec<SpringConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<SpringConfiguration> var1) {
      SpringConfiguration â˜ƒ = â˜ƒ.config();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      if (!â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.above()).getBlock())) {
         return false;
      } else if (â˜ƒ.requiresBlockBelow && !â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.below()).getBlock())) {
         return false;
      } else {
         BlockState â˜ƒ = â˜ƒx.getBlockState(â˜ƒxx);
         if (!â˜ƒ.isAir() && !â˜ƒ.validBlocks.contains(â˜ƒ.getBlock())) {
            return false;
         } else {
            int â˜ƒ = 0;
            int â˜ƒx = 0;
            if (â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.west()).getBlock())) {
               ++â˜ƒx;
            }

            if (â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.east()).getBlock())) {
               ++â˜ƒx;
            }

            if (â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.north()).getBlock())) {
               ++â˜ƒx;
            }

            if (â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.south()).getBlock())) {
               ++â˜ƒx;
            }

            if (â˜ƒ.validBlocks.contains(â˜ƒx.getBlockState(â˜ƒxx.below()).getBlock())) {
               ++â˜ƒx;
            }

            int â˜ƒ = 0;
            if (â˜ƒx.isEmptyBlock(â˜ƒxx.west())) {
               ++â˜ƒ;
            }

            if (â˜ƒx.isEmptyBlock(â˜ƒxx.east())) {
               ++â˜ƒ;
            }

            if (â˜ƒx.isEmptyBlock(â˜ƒxx.north())) {
               ++â˜ƒ;
            }

            if (â˜ƒx.isEmptyBlock(â˜ƒxx.south())) {
               ++â˜ƒ;
            }

            if (â˜ƒx.isEmptyBlock(â˜ƒxx.below())) {
               ++â˜ƒ;
            }

            if (â˜ƒx == â˜ƒ.rockCount && â˜ƒ == â˜ƒ.holeCount) {
               â˜ƒx.setBlock(â˜ƒxx, â˜ƒ.state.createLegacyBlock(), 2);
               â˜ƒx.getLiquidTicks().scheduleTick(â˜ƒxx, â˜ƒ.state.getType(), 0);
               ++â˜ƒ;
            }

            return â˜ƒ > 0;
         }
      }
   }
}
