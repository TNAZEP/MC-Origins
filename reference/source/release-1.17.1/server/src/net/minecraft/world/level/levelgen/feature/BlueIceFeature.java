package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

public class BlueIceFeature extends Feature<NoneFeatureConfiguration> {
   public BlueIceFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();
      if (â˜ƒ.getY() > â˜ƒx.getSeaLevel() - 1) {
         return false;
      } else if (!â˜ƒx.getBlockState(â˜ƒ).is(Blocks.WATER) && !â˜ƒx.getBlockState(â˜ƒ.below()).is(Blocks.WATER)) {
         return false;
      } else {
         boolean â˜ƒ = false;

         for(Direction â˜ƒx : Direction.values()) {
            if (â˜ƒx != Direction.DOWN && â˜ƒx.getBlockState(â˜ƒ.relative(â˜ƒx)).is(Blocks.PACKED_ICE)) {
               â˜ƒ = true;
               break;
            }
         }

         if (!â˜ƒ) {
            return false;
         } else {
            â˜ƒx.setBlock(â˜ƒ, Blocks.BLUE_ICE.defaultBlockState(), 2);

            for(int â˜ƒx = 0; â˜ƒx < 200; ++â˜ƒx) {
               int â˜ƒxx = â˜ƒxx.nextInt(5) - â˜ƒxx.nextInt(6);
               int â˜ƒxxx = 3;
               if (â˜ƒxx < 2) {
                  â˜ƒxxx += â˜ƒxx / 2;
               }

               if (â˜ƒxxx >= 1) {
                  BlockPos â˜ƒxx = â˜ƒ.offset(â˜ƒxx.nextInt(â˜ƒxxx) - â˜ƒxx.nextInt(â˜ƒxxx), â˜ƒxx, â˜ƒxx.nextInt(â˜ƒxxx) - â˜ƒxx.nextInt(â˜ƒxxx));
                  BlockState â˜ƒxxx = â˜ƒx.getBlockState(â˜ƒxx);
                  if (â˜ƒxxx.getMaterial() == Material.AIR || â˜ƒxxx.is(Blocks.WATER) || â˜ƒxxx.is(Blocks.PACKED_ICE) || â˜ƒxxx.is(Blocks.ICE)) {
                     for(Direction â˜ƒxxxx : Direction.values()) {
                        BlockState â˜ƒxxxxx = â˜ƒx.getBlockState(â˜ƒxx.relative(â˜ƒxxxx));
                        if (â˜ƒxxxxx.is(Blocks.BLUE_ICE)) {
                           â˜ƒx.setBlock(â˜ƒxx, Blocks.BLUE_ICE.defaultBlockState(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
