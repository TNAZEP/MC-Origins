package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public abstract class AbstractHugeMushroomFeature extends Feature<HugeMushroomFeatureConfiguration> {
   public AbstractHugeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   protected void placeTrunk(LevelAccessor var1, Random var2, BlockPos var3, HugeMushroomFeatureConfiguration var4, int var5, BlockPos.MutableBlockPos var6) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         â˜ƒ.set(â˜ƒ).move(Direction.UP, â˜ƒ);
         if (!â˜ƒ.getBlockState(â˜ƒ).isSolidRender(â˜ƒ, â˜ƒ)) {
            this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ.stemProvider.getState(â˜ƒ, â˜ƒ));
         }
      }
   }

   protected int getTreeHeight(Random var1) {
      int â˜ƒ = â˜ƒ.nextInt(3) + 4;
      if (â˜ƒ.nextInt(12) == 0) {
         â˜ƒ *= 2;
      }

      return â˜ƒ;
   }

   protected boolean isValidPosition(LevelAccessor var1, BlockPos var2, int var3, BlockPos.MutableBlockPos var4, HugeMushroomFeatureConfiguration var5) {
      int â˜ƒ = â˜ƒ.getY();
      if (â˜ƒ >= â˜ƒ.getMinBuildHeight() + 1 && â˜ƒ + â˜ƒ + 1 < â˜ƒ.getMaxBuildHeight()) {
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.below());
         if (!isDirt(â˜ƒx) && !â˜ƒx.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return false;
         } else {
            for(int â˜ƒx = 0; â˜ƒx <= â˜ƒ; ++â˜ƒx) {
               int â˜ƒxx = this.getTreeRadiusForHeight(-1, -1, â˜ƒ.foliageRadius, â˜ƒx);

               for(int â˜ƒxxx = -â˜ƒxx; â˜ƒxxx <= â˜ƒxx; ++â˜ƒxxx) {
                  for(int â˜ƒxxxx = -â˜ƒxx; â˜ƒxxxx <= â˜ƒxx; ++â˜ƒxxxx) {
                     BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxxx, â˜ƒx, â˜ƒxxxx));
                     if (!â˜ƒxxxxx.isAir() && !â˜ƒxxxxx.is(BlockTags.LEAVES)) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      HugeMushroomFeatureConfiguration â˜ƒxxx = â˜ƒ.config();
      int â˜ƒxxxx = this.getTreeHeight(â˜ƒxx);
      BlockPos.MutableBlockPos â˜ƒxxxxx = new BlockPos.MutableBlockPos();
      if (!this.isValidPosition(â˜ƒ, â˜ƒx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxx)) {
         return false;
      } else {
         this.makeCap(â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxx);
         this.placeTrunk(â˜ƒ, â˜ƒxx, â˜ƒx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
         return true;
      }
   }

   protected abstract int getTreeRadiusForHeight(int var1, int var2, int var3, int var4);

   protected abstract void makeCap(
      LevelAccessor var1, Random var2, BlockPos var3, int var4, BlockPos.MutableBlockPos var5, HugeMushroomFeatureConfiguration var6
   );
}
