package net.minecraft.world.level.block;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class HugeMushroomBlock extends Block {
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   public static final BooleanProperty UP = PipeBlock.UP;
   public static final BooleanProperty DOWN = PipeBlock.DOWN;
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

   public HugeMushroomBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(NORTH, Boolean.valueOf(true))
            .setValue(EAST, Boolean.valueOf(true))
            .setValue(SOUTH, Boolean.valueOf(true))
            .setValue(WEST, Boolean.valueOf(true))
            .setValue(UP, Boolean.valueOf(true))
            .setValue(DOWN, Boolean.valueOf(true))
      );
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      return this.defaultBlockState()
         .setValue(DOWN, Boolean.valueOf(!â˜ƒ.getBlockState(â˜ƒx.below()).is(this)))
         .setValue(UP, Boolean.valueOf(!â˜ƒ.getBlockState(â˜ƒx.above()).is(this)))
         .setValue(NORTH, Boolean.valueOf(!â˜ƒ.getBlockState(â˜ƒx.north()).is(this)))
         .setValue(EAST, Boolean.valueOf(!â˜ƒ.getBlockState(â˜ƒx.east()).is(this)))
         .setValue(SOUTH, Boolean.valueOf(!â˜ƒ.getBlockState(â˜ƒx.south()).is(this)))
         .setValue(WEST, Boolean.valueOf(!â˜ƒ.getBlockState(â˜ƒx.west()).is(this)));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ.is(this) ? â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), Boolean.valueOf(false)) : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.rotate(Direction.NORTH)), (Boolean)â˜ƒ.getValue(NORTH))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.rotate(Direction.SOUTH)), (Boolean)â˜ƒ.getValue(SOUTH))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.rotate(Direction.EAST)), (Boolean)â˜ƒ.getValue(EAST))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.rotate(Direction.WEST)), (Boolean)â˜ƒ.getValue(WEST))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.rotate(Direction.UP)), (Boolean)â˜ƒ.getValue(UP))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.rotate(Direction.DOWN)), (Boolean)â˜ƒ.getValue(DOWN));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.mirror(Direction.NORTH)), (Boolean)â˜ƒ.getValue(NORTH))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.mirror(Direction.SOUTH)), (Boolean)â˜ƒ.getValue(SOUTH))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.mirror(Direction.EAST)), (Boolean)â˜ƒ.getValue(EAST))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.mirror(Direction.WEST)), (Boolean)â˜ƒ.getValue(WEST))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.mirror(Direction.UP)), (Boolean)â˜ƒ.getValue(UP))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.mirror(Direction.DOWN)), (Boolean)â˜ƒ.getValue(DOWN));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
   }
}
