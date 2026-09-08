package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeRedMushroomFeature extends AbstractHugeMushroomFeature {
   public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected void makeCap(LevelAccessor var1, Random var2, BlockPos var3, int var4, BlockPos.MutableBlockPos var5, HugeMushroomFeatureConfiguration var6) {
      for(int â˜ƒ = â˜ƒ - 3; â˜ƒ <= â˜ƒ; ++â˜ƒ) {
         int â˜ƒx = â˜ƒ < â˜ƒ ? â˜ƒ.foliageRadius : â˜ƒ.foliageRadius - 1;
         int â˜ƒxx = â˜ƒ.foliageRadius - 2;

         for(int â˜ƒxxx = -â˜ƒx; â˜ƒxxx <= â˜ƒx; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = -â˜ƒx; â˜ƒxxxx <= â˜ƒx; ++â˜ƒxxxx) {
               boolean â˜ƒxxxxx = â˜ƒxxx == -â˜ƒx;
               boolean â˜ƒxxxxxx = â˜ƒxxx == â˜ƒx;
               boolean â˜ƒxxxxxxx = â˜ƒxxxx == -â˜ƒx;
               boolean â˜ƒxxxxxxxx = â˜ƒxxxx == â˜ƒx;
               boolean â˜ƒxxxxxxxxx = â˜ƒxxxxx || â˜ƒxxxxxx;
               boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx || â˜ƒxxxxxxxx;
               if (â˜ƒ >= â˜ƒ || â˜ƒxxxxxxxxx != â˜ƒxxxxxxxxxx) {
                  â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxxx, â˜ƒ, â˜ƒxxxx);
                  if (!â˜ƒ.getBlockState(â˜ƒ).isSolidRender(â˜ƒ, â˜ƒ)) {
                     BlockState â˜ƒxxxxxxxxxxx = â˜ƒ.capProvider.getState(â˜ƒ, â˜ƒ);
                     if (â˜ƒxxxxxxxxxxx.hasProperty(HugeMushroomBlock.WEST)
                        && â˜ƒxxxxxxxxxxx.hasProperty(HugeMushroomBlock.EAST)
                        && â˜ƒxxxxxxxxxxx.hasProperty(HugeMushroomBlock.NORTH)
                        && â˜ƒxxxxxxxxxxx.hasProperty(HugeMushroomBlock.SOUTH)
                        && â˜ƒxxxxxxxxxxx.hasProperty(HugeMushroomBlock.UP)) {
                        â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.setValue(HugeMushroomBlock.UP, Boolean.valueOf(â˜ƒ >= â˜ƒ - 1))
                           .setValue(HugeMushroomBlock.WEST, Boolean.valueOf(â˜ƒxxx < -â˜ƒxx))
                           .setValue(HugeMushroomBlock.EAST, Boolean.valueOf(â˜ƒxxx > â˜ƒxx))
                           .setValue(HugeMushroomBlock.NORTH, Boolean.valueOf(â˜ƒxxxx < -â˜ƒxx))
                           .setValue(HugeMushroomBlock.SOUTH, Boolean.valueOf(â˜ƒxxxx > â˜ƒxx));
                     }

                     this.setBlock(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxx);
                  }
               }
            }
         }
      }
   }

   @Override
   protected int getTreeRadiusForHeight(int var1, int var2, int var3, int var4) {
      int â˜ƒ = 0;
      if (â˜ƒ < â˜ƒ && â˜ƒ >= â˜ƒ - 3) {
         â˜ƒ = â˜ƒ;
      } else if (â˜ƒ == â˜ƒ) {
         â˜ƒ = â˜ƒ;
      }

      return â˜ƒ;
   }
}
