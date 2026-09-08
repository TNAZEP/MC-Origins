package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;

public class RailBlock extends BaseRailBlock {
   public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE;

   protected RailBlock(BlockBehaviour.Properties var1) {
      super(false, â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   protected void updateState(BlockState var1, Level var2, BlockPos var3, Block var4) {
      if (â˜ƒ.defaultBlockState().isSignalSource() && new RailState(â˜ƒ, â˜ƒ, â˜ƒ).countPotentialConnections() == 3) {
         this.updateDir(â˜ƒ, â˜ƒ, â˜ƒ, false);
      }
   }

   @Override
   public Property<RailShape> getShapeProperty() {
      return SHAPE;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            switch((RailShape)â˜ƒ.getValue(SHAPE)) {
               case ASCENDING_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_WEST);
               case SOUTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_EAST);
               case NORTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_WEST);
            }
         case COUNTERCLOCKWISE_90:
            switch((RailShape)â˜ƒ.getValue(SHAPE)) {
               case ASCENDING_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_NORTH);
               case ASCENDING_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_NORTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_WEST);
               case ASCENDING_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_EAST);
               case SOUTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_WEST);
               case NORTH_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.EAST_WEST);
               case EAST_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_SOUTH);
            }
         case CLOCKWISE_90:
            switch((RailShape)â˜ƒ.getValue(SHAPE)) {
               case ASCENDING_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_NORTH);
               case ASCENDING_NORTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_EAST);
               case ASCENDING_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_WEST);
               case SOUTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.EAST_WEST);
               case EAST_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_SOUTH);
            }
         default:
            return â˜ƒ;
      }
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      RailShape â˜ƒ = â˜ƒ.getValue(SHAPE);
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            switch(â˜ƒ) {
               case ASCENDING_NORTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
               case ASCENDING_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_NORTH);
               case SOUTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_EAST);
               case SOUTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_WEST);
               case NORTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_WEST);
               case NORTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_EAST);
               default:
                  return super.mirror(â˜ƒ, â˜ƒ);
            }
         case FRONT_BACK:
            switch(â˜ƒ) {
               case ASCENDING_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_WEST);
               case ASCENDING_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.ASCENDING_EAST);
               case ASCENDING_NORTH:
               case ASCENDING_SOUTH:
               default:
                  break;
               case SOUTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_WEST);
               case SOUTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.SOUTH_EAST);
               case NORTH_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_EAST);
               case NORTH_EAST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_WEST);
            }
      }

      return super.mirror(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(SHAPE, WATERLOGGED);
   }
}
