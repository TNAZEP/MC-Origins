package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GlowstoneFeature extends Feature<NoneFeatureConfiguration> {
   public GlowstoneFeature(Codec<NoneFeatureConfiguration> var1) {
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
         if (!â˜ƒ.is(Blocks.NETHERRACK) && !â˜ƒ.is(Blocks.BASALT) && !â˜ƒ.is(Blocks.BLACKSTONE)) {
            return false;
         } else {
            â˜ƒ.setBlock(â˜ƒx, Blocks.GLOWSTONE.defaultBlockState(), 2);

            for(int â˜ƒ = 0; â˜ƒ < 1500; ++â˜ƒ) {
               BlockPos â˜ƒx = â˜ƒx.offset(â˜ƒxx.nextInt(8) - â˜ƒxx.nextInt(8), -â˜ƒxx.nextInt(12), â˜ƒxx.nextInt(8) - â˜ƒxx.nextInt(8));
               if (â˜ƒ.getBlockState(â˜ƒx).isAir()) {
                  int â˜ƒxx = 0;

                  for(Direction â˜ƒxxx : Direction.values()) {
                     if (â˜ƒ.getBlockState(â˜ƒx.relative(â˜ƒxxx)).is(Blocks.GLOWSTONE)) {
                        ++â˜ƒxx;
                     }

                     if (â˜ƒxx > 1) {
                        break;
                     }
                  }

                  if (â˜ƒxx == 1) {
                     â˜ƒ.setBlock(â˜ƒx, Blocks.GLOWSTONE.defaultBlockState(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
