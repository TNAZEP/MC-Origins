package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class GravelBlock extends FallingBlock {
   public GravelBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public int getDustColor(BlockState var1, BlockGetter var2, BlockPos var3) {
      return -8356741;
   }
}
