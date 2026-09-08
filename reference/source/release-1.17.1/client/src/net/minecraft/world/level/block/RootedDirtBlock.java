package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RootedDirtBlock extends Block implements BonemealableBlock {
   public RootedDirtBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).isAir();
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      â˜ƒ.setBlockAndUpdate(â˜ƒ.below(), Blocks.HANGING_ROOTS.defaultBlockState());
   }
}
