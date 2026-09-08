package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class InfestedRotatedPillarBlock extends InfestedBlock {
   public InfestedRotatedPillarBlock(Block var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ, â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y));
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return RotatedPillarBlock.rotatePillar(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(RotatedPillarBlock.AXIS);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(RotatedPillarBlock.AXIS, â˜ƒ.getClickedFace().getAxis());
   }
}
