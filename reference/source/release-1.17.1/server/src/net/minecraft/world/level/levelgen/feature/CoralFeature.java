package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class CoralFeature extends Feature<NoneFeatureConfiguration> {
   public CoralFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      Random â˜ƒ = â˜ƒ.random();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      BlockState â˜ƒxxx = BlockTags.CORAL_BLOCKS.getRandomElement(â˜ƒ).defaultBlockState();
      return this.placeFeature(â˜ƒx, â˜ƒ, â˜ƒxx, â˜ƒxxx);
   }

   protected abstract boolean placeFeature(LevelAccessor var1, Random var2, BlockPos var3, BlockState var4);

   protected boolean placeCoralBlock(LevelAccessor var1, Random var2, BlockPos var3, BlockState var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if ((â˜ƒx.is(Blocks.WATER) || â˜ƒx.is(BlockTags.CORALS)) && â˜ƒ.getBlockState(â˜ƒ).is(Blocks.WATER)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
         if (â˜ƒ.nextFloat() < 0.25F) {
            â˜ƒ.setBlock(â˜ƒ, BlockTags.CORALS.getRandomElement(â˜ƒ).defaultBlockState(), 2);
         } else if (â˜ƒ.nextFloat() < 0.05F) {
            â˜ƒ.setBlock(â˜ƒ, Blocks.SEA_PICKLE.defaultBlockState().setValue(SeaPickleBlock.PICKLES, Integer.valueOf(â˜ƒ.nextInt(4) + 1)), 2);
         }

         for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
            if (â˜ƒ.nextFloat() < 0.2F) {
               BlockPos â˜ƒxxx = â˜ƒ.relative(â˜ƒxx);
               if (â˜ƒ.getBlockState(â˜ƒxxx).is(Blocks.WATER)) {
                  BlockState â˜ƒxxxx = BlockTags.WALL_CORALS.getRandomElement(â˜ƒ).defaultBlockState();
                  if (â˜ƒxxxx.hasProperty(BaseCoralWallFanBlock.FACING)) {
                     â˜ƒxxxx = â˜ƒxxxx.setValue(BaseCoralWallFanBlock.FACING, â˜ƒxx);
                  }

                  â˜ƒ.setBlock(â˜ƒxxx, â˜ƒxxxx, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
