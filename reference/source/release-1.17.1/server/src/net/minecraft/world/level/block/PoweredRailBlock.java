package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;

public class PoweredRailBlock extends BaseRailBlock {
   public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

   protected PoweredRailBlock(BlockBehaviour.Properties var1) {
      super(true, â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(SHAPE, RailShape.NORTH_SOUTH)
            .setValue(POWERED, Boolean.valueOf(false))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   protected boolean findPoweredRailSignal(Level var1, BlockPos var2, BlockState var3, boolean var4, int var5) {
      if (â˜ƒ >= 8) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.getX();
         int â˜ƒx = â˜ƒ.getY();
         int â˜ƒxx = â˜ƒ.getZ();
         boolean â˜ƒxxx = true;
         RailShape â˜ƒxxxx = â˜ƒ.getValue(SHAPE);
         switch(â˜ƒxxxx) {
            case NORTH_SOUTH:
               if (â˜ƒ) {
                  ++â˜ƒxx;
               } else {
                  --â˜ƒxx;
               }
               break;
            case EAST_WEST:
               if (â˜ƒ) {
                  --â˜ƒ;
               } else {
                  ++â˜ƒ;
               }
               break;
            case ASCENDING_EAST:
               if (â˜ƒ) {
                  --â˜ƒ;
               } else {
                  ++â˜ƒ;
                  ++â˜ƒx;
                  â˜ƒxxx = false;
               }

               â˜ƒxxxx = RailShape.EAST_WEST;
               break;
            case ASCENDING_WEST:
               if (â˜ƒ) {
                  --â˜ƒ;
                  ++â˜ƒx;
                  â˜ƒxxx = false;
               } else {
                  ++â˜ƒ;
               }

               â˜ƒxxxx = RailShape.EAST_WEST;
               break;
            case ASCENDING_NORTH:
               if (â˜ƒ) {
                  ++â˜ƒxx;
               } else {
                  --â˜ƒxx;
                  ++â˜ƒx;
                  â˜ƒxxx = false;
               }

               â˜ƒxxxx = RailShape.NORTH_SOUTH;
               break;
            case ASCENDING_SOUTH:
               if (â˜ƒ) {
                  ++â˜ƒxx;
                  ++â˜ƒx;
                  â˜ƒxxx = false;
               } else {
                  --â˜ƒxx;
               }

               â˜ƒxxxx = RailShape.NORTH_SOUTH;
         }

         if (this.isSameRailWithPower(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒx, â˜ƒxx), â˜ƒ, â˜ƒ, â˜ƒxxxx)) {
            return true;
         } else {
            return â˜ƒxxx && this.isSameRailWithPower(â˜ƒ, new BlockPos(â˜ƒ, â˜ƒx - 1, â˜ƒxx), â˜ƒ, â˜ƒ, â˜ƒxxxx);
         }
      }
   }

   protected boolean isSameRailWithPower(Level var1, BlockPos var2, boolean var3, int var4, RailShape var5) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!â˜ƒ.is(this)) {
         return false;
      } else {
         RailShape â˜ƒ = â˜ƒ.getValue(SHAPE);
         if (â˜ƒ != RailShape.EAST_WEST || â˜ƒ != RailShape.NORTH_SOUTH && â˜ƒ != RailShape.ASCENDING_NORTH && â˜ƒ != RailShape.ASCENDING_SOUTH) {
            if (â˜ƒ != RailShape.NORTH_SOUTH || â˜ƒ != RailShape.EAST_WEST && â˜ƒ != RailShape.ASCENDING_EAST && â˜ƒ != RailShape.ASCENDING_WEST) {
               if (!â˜ƒ.getValue(POWERED)) {
                  return false;
               } else {
                  return â˜ƒ.hasNeighborSignal(â˜ƒ) ? true : this.findPoweredRailSignal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ + 1);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   protected void updateState(BlockState var1, Level var2, BlockPos var3, Block var4) {
      boolean â˜ƒ = â˜ƒ.getValue(POWERED);
      boolean â˜ƒx = â˜ƒ.hasNeighborSignal(â˜ƒ) || this.findPoweredRailSignal(â˜ƒ, â˜ƒ, â˜ƒ, true, 0) || this.findPoweredRailSignal(â˜ƒ, â˜ƒ, â˜ƒ, false, 0);
      if (â˜ƒx != â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒx)), 3);
         â˜ƒ.updateNeighborsAt(â˜ƒ.below(), this);
         if (((RailShape)â˜ƒ.getValue(SHAPE)).isAscending()) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.above(), this);
         }
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
               case NORTH_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.EAST_WEST);
               case EAST_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_SOUTH);
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
            }
         case CLOCKWISE_90:
            switch((RailShape)â˜ƒ.getValue(SHAPE)) {
               case NORTH_SOUTH:
                  return â˜ƒ.setValue(SHAPE, RailShape.EAST_WEST);
               case EAST_WEST:
                  return â˜ƒ.setValue(SHAPE, RailShape.NORTH_SOUTH);
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
      â˜ƒ.add(SHAPE, POWERED, WATERLOGGED);
   }
}
