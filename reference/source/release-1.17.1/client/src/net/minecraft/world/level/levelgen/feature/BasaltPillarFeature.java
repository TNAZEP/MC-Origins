package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BasaltPillarFeature extends Feature<NoneFeatureConfiguration> {
   public BasaltPillarFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();
      if (â˜ƒx.isEmptyBlock(â˜ƒ) && !â˜ƒx.isEmptyBlock(â˜ƒ.above())) {
         BlockPos.MutableBlockPos â˜ƒxxx = â˜ƒ.mutable();
         BlockPos.MutableBlockPos â˜ƒxxxx = â˜ƒ.mutable();
         boolean â˜ƒxxxxx = true;
         boolean â˜ƒxxxxxx = true;
         boolean â˜ƒxxxxxxx = true;
         boolean â˜ƒxxxxxxxx = true;

         while(â˜ƒx.isEmptyBlock(â˜ƒxxx)) {
            if (â˜ƒx.isOutsideBuildHeight(â˜ƒxxx)) {
               return true;
            }

            â˜ƒx.setBlock(â˜ƒxxx, Blocks.BASALT.defaultBlockState(), 2);
            â˜ƒxxxxx = â˜ƒxxxxx && this.placeHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.NORTH));
            â˜ƒxxxxxx = â˜ƒxxxxxx && this.placeHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.SOUTH));
            â˜ƒxxxxxxx = â˜ƒxxxxxxx && this.placeHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.WEST));
            â˜ƒxxxxxxxx = â˜ƒxxxxxxxx && this.placeHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.EAST));
            â˜ƒxxx.move(Direction.DOWN);
         }

         â˜ƒxxx.move(Direction.UP);
         this.placeBaseHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.NORTH));
         this.placeBaseHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.SOUTH));
         this.placeBaseHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.WEST));
         this.placeBaseHangOff(â˜ƒx, â˜ƒxx, â˜ƒxxxx.setWithOffset(â˜ƒxxx, Direction.EAST));
         â˜ƒxxx.move(Direction.DOWN);
         BlockPos.MutableBlockPos â˜ƒxxxxxxxxx = new BlockPos.MutableBlockPos();

         for(int â˜ƒxxxxxxxxxx = -3; â˜ƒxxxxxxxxxx < 4; ++â˜ƒxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxx = -3; â˜ƒxxxxxxxxxxx < 4; ++â˜ƒxxxxxxxxxxx) {
               int â˜ƒxxxxxxxxxxxx = Mth.abs(â˜ƒxxxxxxxxxx) * Mth.abs(â˜ƒxxxxxxxxxxx);
               if (â˜ƒxx.nextInt(10) < 10 - â˜ƒxxxxxxxxxxxx) {
                  â˜ƒxxxxxxxxx.set(â˜ƒxxx.offset(â˜ƒxxxxxxxxxx, 0, â˜ƒxxxxxxxxxxx));
                  int â˜ƒxxxxxxxxxxxxx = 3;

                  while(â˜ƒx.isEmptyBlock(â˜ƒxxxx.setWithOffset(â˜ƒxxxxxxxxx, Direction.DOWN))) {
                     â˜ƒxxxxxxxxx.move(Direction.DOWN);
                     if (--â˜ƒxxxxxxxxxxxxx <= 0) {
                        break;
                     }
                  }

                  if (!â˜ƒx.isEmptyBlock(â˜ƒxxxx.setWithOffset(â˜ƒxxxxxxxxx, Direction.DOWN))) {
                     â˜ƒx.setBlock(â˜ƒxxxxxxxxx, Blocks.BASALT.defaultBlockState(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void placeBaseHangOff(LevelAccessor var1, Random var2, BlockPos var3) {
      if (â˜ƒ.nextBoolean()) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.BASALT.defaultBlockState(), 2);
      }
   }

   private boolean placeHangOff(LevelAccessor var1, Random var2, BlockPos var3) {
      if (â˜ƒ.nextInt(10) != 0) {
         â˜ƒ.setBlock(â˜ƒ, Blocks.BASALT.defaultBlockState(), 2);
         return true;
      } else {
         return false;
      }
   }
}
