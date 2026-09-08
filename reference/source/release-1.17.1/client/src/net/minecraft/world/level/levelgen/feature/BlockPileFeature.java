package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

public class BlockPileFeature extends Feature<BlockPileConfiguration> {
   public BlockPileFeature(Codec<BlockPileConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<BlockPileConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();
      BlockPileConfiguration â˜ƒxxx = â˜ƒ.config();
      if (â˜ƒ.getY() < â˜ƒx.getMinBuildHeight() + 5) {
         return false;
      } else {
         int â˜ƒ = 2 + â˜ƒxx.nextInt(2);
         int â˜ƒx = 2 + â˜ƒxx.nextInt(2);

         for(BlockPos â˜ƒxx : BlockPos.betweenClosed(â˜ƒ.offset(-â˜ƒ, 0, -â˜ƒx), â˜ƒ.offset(â˜ƒ, 1, â˜ƒx))) {
            int â˜ƒxxx = â˜ƒ.getX() - â˜ƒxx.getX();
            int â˜ƒxxxx = â˜ƒ.getZ() - â˜ƒxx.getZ();
            if ((float)(â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx) <= â˜ƒxx.nextFloat() * 10.0F - â˜ƒxx.nextFloat() * 6.0F) {
               this.tryPlaceBlock(â˜ƒx, â˜ƒxx, â˜ƒxx, â˜ƒxxx);
            } else if ((double)â˜ƒxx.nextFloat() < 0.031) {
               this.tryPlaceBlock(â˜ƒx, â˜ƒxx, â˜ƒxx, â˜ƒxxx);
            }
         }

         return true;
      }
   }

   private boolean mayPlaceOn(LevelAccessor var1, BlockPos var2, Random var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒx.is(Blocks.DIRT_PATH) ? â˜ƒ.nextBoolean() : â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP);
   }

   private void tryPlaceBlock(LevelAccessor var1, BlockPos var2, Random var3, BlockPileConfiguration var4) {
      if (â˜ƒ.isEmptyBlock(â˜ƒ) && this.mayPlaceOn(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.stateProvider.getState(â˜ƒ, â˜ƒ), 4);
      }
   }
}
