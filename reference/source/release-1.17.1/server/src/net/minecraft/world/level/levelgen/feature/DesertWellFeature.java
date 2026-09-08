package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DesertWellFeature extends Feature<NoneFeatureConfiguration> {
   private static final BlockStatePredicate IS_SAND = BlockStatePredicate.forBlock(Blocks.SAND);
   private final BlockState sandSlab = Blocks.SANDSTONE_SLAB.defaultBlockState();
   private final BlockState sandstone = Blocks.SANDSTONE.defaultBlockState();
   private final BlockState water = Blocks.WATER.defaultBlockState();

   public DesertWellFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      â˜ƒx = â˜ƒx.above();

      while(â˜ƒ.isEmptyBlock(â˜ƒx) && â˜ƒx.getY() > â˜ƒ.getMinBuildHeight() + 2) {
         â˜ƒx = â˜ƒx.below();
      }

      if (!IS_SAND.test(â˜ƒ.getBlockState(â˜ƒx))) {
         return false;
      } else {
         for(int â˜ƒxx = -2; â˜ƒxx <= 2; ++â˜ƒxx) {
            for(int â˜ƒxxx = -2; â˜ƒxxx <= 2; ++â˜ƒxxx) {
               if (â˜ƒ.isEmptyBlock(â˜ƒx.offset(â˜ƒxx, -1, â˜ƒxxx)) && â˜ƒ.isEmptyBlock(â˜ƒx.offset(â˜ƒxx, -2, â˜ƒxxx))) {
                  return false;
               }
            }
         }

         for(int â˜ƒxx = -1; â˜ƒxx <= 0; ++â˜ƒxx) {
            for(int â˜ƒxxx = -2; â˜ƒxxx <= 2; ++â˜ƒxxx) {
               for(int â˜ƒxxxx = -2; â˜ƒxxxx <= 2; ++â˜ƒxxxx) {
                  â˜ƒ.setBlock(â˜ƒx.offset(â˜ƒxxx, â˜ƒxx, â˜ƒxxxx), this.sandstone, 2);
               }
            }
         }

         â˜ƒ.setBlock(â˜ƒx, this.water, 2);

         for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
            â˜ƒ.setBlock(â˜ƒx.relative(â˜ƒxx), this.water, 2);
         }

         for(int â˜ƒxx = -2; â˜ƒxx <= 2; ++â˜ƒxx) {
            for(int â˜ƒxxx = -2; â˜ƒxxx <= 2; ++â˜ƒxxx) {
               if (â˜ƒxx == -2 || â˜ƒxx == 2 || â˜ƒxxx == -2 || â˜ƒxxx == 2) {
                  â˜ƒ.setBlock(â˜ƒx.offset(â˜ƒxx, 1, â˜ƒxxx), this.sandstone, 2);
               }
            }
         }

         â˜ƒ.setBlock(â˜ƒx.offset(2, 1, 0), this.sandSlab, 2);
         â˜ƒ.setBlock(â˜ƒx.offset(-2, 1, 0), this.sandSlab, 2);
         â˜ƒ.setBlock(â˜ƒx.offset(0, 1, 2), this.sandSlab, 2);
         â˜ƒ.setBlock(â˜ƒx.offset(0, 1, -2), this.sandSlab, 2);

         for(int â˜ƒxx = -1; â˜ƒxx <= 1; ++â˜ƒxx) {
            for(int â˜ƒxxx = -1; â˜ƒxxx <= 1; ++â˜ƒxxx) {
               if (â˜ƒxx == 0 && â˜ƒxxx == 0) {
                  â˜ƒ.setBlock(â˜ƒx.offset(â˜ƒxx, 4, â˜ƒxxx), this.sandstone, 2);
               } else {
                  â˜ƒ.setBlock(â˜ƒx.offset(â˜ƒxx, 4, â˜ƒxxx), this.sandSlab, 2);
               }
            }
         }

         for(int â˜ƒxx = 1; â˜ƒxx <= 3; ++â˜ƒxx) {
            â˜ƒ.setBlock(â˜ƒx.offset(-1, â˜ƒxx, -1), this.sandstone, 2);
            â˜ƒ.setBlock(â˜ƒx.offset(-1, â˜ƒxx, 1), this.sandstone, 2);
            â˜ƒ.setBlock(â˜ƒx.offset(1, â˜ƒxx, -1), this.sandstone, 2);
            â˜ƒ.setBlock(â˜ƒx.offset(1, â˜ƒxx, 1), this.sandstone, 2);
         }

         return true;
      }
   }
}
