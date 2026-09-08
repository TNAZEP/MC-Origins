package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TwistingVinesBlock extends GrowingPlantHeadBlock {
   public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

   public TwistingVinesBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, Direction.UP, SHAPE, false, 0.1);
   }

   @Override
   protected int getBlocksToGrowWhenBonemealed(Random var1) {
      return NetherVines.getBlocksToGrowWhenBonemealed(â˜ƒ);
   }

   @Override
   protected Block getBodyBlock() {
      return Blocks.TWISTING_VINES_PLANT;
   }

   @Override
   protected boolean canGrowInto(BlockState var1) {
      return NetherVines.isValidGrowthState(â˜ƒ);
   }
}
