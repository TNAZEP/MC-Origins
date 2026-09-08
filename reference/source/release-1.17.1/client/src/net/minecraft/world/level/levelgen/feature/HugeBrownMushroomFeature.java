package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeBrownMushroomFeature extends AbstractHugeMushroomFeature {
   public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected void makeCap(LevelAccessor var1, Random var2, BlockPos var3, int var4, BlockPos.MutableBlockPos var5, HugeMushroomFeatureConfiguration var6) {
      int â˜ƒ = â˜ƒ.foliageRadius;

      for(int â˜ƒx = -â˜ƒ; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
         for(int â˜ƒxx = -â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
            boolean â˜ƒxxx = â˜ƒx == -â˜ƒ;
            boolean â˜ƒxxxx = â˜ƒx == â˜ƒ;
            boolean â˜ƒxxxxx = â˜ƒxx == -â˜ƒ;
            boolean â˜ƒxxxxxx = â˜ƒxx == â˜ƒ;
            boolean â˜ƒxxxxxxx = â˜ƒxxx || â˜ƒxxxx;
            boolean â˜ƒxxxxxxxx = â˜ƒxxxxx || â˜ƒxxxxxx;
            if (!â˜ƒxxxxxxx || !â˜ƒxxxxxxxx) {
               â˜ƒ.setWithOffset(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx);
               if (!â˜ƒ.getBlockState(â˜ƒ).isSolidRender(â˜ƒ, â˜ƒ)) {
                  boolean â˜ƒxxxxxxxxx = â˜ƒxxx || â˜ƒxxxxxxxx && â˜ƒx == 1 - â˜ƒ;
                  boolean â˜ƒxxxxxxxxxx = â˜ƒxxxx || â˜ƒxxxxxxxx && â˜ƒx == â˜ƒ - 1;
                  boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxxx || â˜ƒxxxxxxx && â˜ƒxx == 1 - â˜ƒ;
                  boolean â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxx || â˜ƒxxxxxxx && â˜ƒxx == â˜ƒ - 1;
                  BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒ.capProvider.getState(â˜ƒ, â˜ƒ);
                  if (â˜ƒxxxxxxxxxxxxx.hasProperty(HugeMushroomBlock.WEST)
                     && â˜ƒxxxxxxxxxxxxx.hasProperty(HugeMushroomBlock.EAST)
                     && â˜ƒxxxxxxxxxxxxx.hasProperty(HugeMushroomBlock.NORTH)
                     && â˜ƒxxxxxxxxxxxxx.hasProperty(HugeMushroomBlock.SOUTH)) {
                     â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.setValue(HugeMushroomBlock.WEST, Boolean.valueOf(â˜ƒxxxxxxxxx))
                        .setValue(HugeMushroomBlock.EAST, Boolean.valueOf(â˜ƒxxxxxxxxxx))
                        .setValue(HugeMushroomBlock.NORTH, Boolean.valueOf(â˜ƒxxxxxxxxxxx))
                        .setValue(HugeMushroomBlock.SOUTH, Boolean.valueOf(â˜ƒxxxxxxxxxxxx));
                  }

                  this.setBlock(â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxx);
               }
            }
         }
      }
   }

   @Override
   protected int getTreeRadiusForHeight(int var1, int var2, int var3, int var4) {
      return â˜ƒ <= 3 ? 0 : â˜ƒ;
   }
}
