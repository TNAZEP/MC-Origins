package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class SoulFireBlock extends BaseFireBlock {
   public SoulFireBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, 2.0F);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ) ? this.defaultBlockState() : Blocks.AIR.defaultBlockState();
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return canSurviveOnBlock(â˜ƒ.getBlockState(â˜ƒ.below()));
   }

   public static boolean canSurviveOnBlock(BlockState var0) {
      return â˜ƒ.is(BlockTags.SOUL_FIRE_BASE_BLOCKS);
   }

   @Override
   protected boolean canBurn(BlockState var1) {
      return true;
   }
}
