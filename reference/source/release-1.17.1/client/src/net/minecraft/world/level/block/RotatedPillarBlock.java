package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class RotatedPillarBlock extends Block {
   public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

   public RotatedPillarBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(AXIS, Direction.Axis.Y));
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return rotatePillar(â˜ƒ, â˜ƒ);
   }

   public static BlockState rotatePillar(BlockState var0, Rotation var1) {
      switch(â˜ƒ) {
         case COUNTERCLOCKWISE_90:
         case CLOCKWISE_90:
            switch((Direction.Axis)â˜ƒ.getValue(AXIS)) {
               case X:
                  return â˜ƒ.setValue(AXIS, Direction.Axis.Z);
               case Z:
                  return â˜ƒ.setValue(AXIS, Direction.Axis.X);
               default:
                  return â˜ƒ;
            }
         default:
            return â˜ƒ;
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AXIS);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(AXIS, â˜ƒ.getClickedFace().getAxis());
   }
}
