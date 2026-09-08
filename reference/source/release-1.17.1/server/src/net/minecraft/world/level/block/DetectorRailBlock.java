package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;

public class DetectorRailBlock extends BaseRailBlock {
   public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private static final int PRESSED_CHECK_PERIOD = 20;

   public DetectorRailBlock(BlockBehaviour.Properties var1) {
      super(true, â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(POWERED, Boolean.valueOf(false))
            .setValue(SHAPE, RailShape.NORTH_SOUTH)
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide) {
         if (!â˜ƒ.getValue(POWERED)) {
            this.checkPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(POWERED)) {
         this.checkPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      if (!â˜ƒ.getValue(POWERED)) {
         return 0;
      } else {
         return â˜ƒ == Direction.UP ? 15 : 0;
      }
   }

   private void checkPressed(Level var1, BlockPos var2, BlockState var3) {
      if (this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ)) {
         boolean â˜ƒ = â˜ƒ.getValue(POWERED);
         boolean â˜ƒx = false;
         List<AbstractMinecart> â˜ƒxx = this.getInteractingMinecartOfType(â˜ƒ, â˜ƒ, AbstractMinecart.class, var0 -> true);
         if (!â˜ƒxx.isEmpty()) {
            â˜ƒx = true;
         }

         if (â˜ƒx && !â˜ƒ) {
            BlockState â˜ƒ = â˜ƒ.setValue(POWERED, Boolean.valueOf(true));
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
            this.updatePowerToConnected(â˜ƒ, â˜ƒ, â˜ƒ, true);
            â˜ƒ.updateNeighborsAt(â˜ƒ, this);
            â˜ƒ.updateNeighborsAt(â˜ƒ.below(), this);
            â˜ƒ.setBlocksDirty(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         if (!â˜ƒx && â˜ƒ) {
            BlockState â˜ƒ = â˜ƒ.setValue(POWERED, Boolean.valueOf(false));
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
            this.updatePowerToConnected(â˜ƒ, â˜ƒ, â˜ƒ, false);
            â˜ƒ.updateNeighborsAt(â˜ƒ, this);
            â˜ƒ.updateNeighborsAt(â˜ƒ.below(), this);
            â˜ƒ.setBlocksDirty(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         if (â˜ƒx) {
            â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 20);
         }

         â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, this);
      }
   }

   protected void updatePowerToConnected(Level var1, BlockPos var2, BlockState var3, boolean var4) {
      RailState â˜ƒ = new RailState(â˜ƒ, â˜ƒ, â˜ƒ);

      for(BlockPos â˜ƒx : â˜ƒ.getConnections()) {
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         â˜ƒxx.neighborChanged(â˜ƒ, â˜ƒx, â˜ƒxx.getBlock(), â˜ƒ, false);
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         BlockState â˜ƒ = this.updateState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.checkPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public Property<RailShape> getShapeProperty() {
      return SHAPE;
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      if (â˜ƒ.getValue(POWERED)) {
         List<MinecartCommandBlock> â˜ƒ = this.getInteractingMinecartOfType(â˜ƒ, â˜ƒ, MinecartCommandBlock.class, var0 -> true);
         if (!â˜ƒ.isEmpty()) {
            return ((MinecartCommandBlock)â˜ƒ.get(0)).getCommandBlock().getSuccessCount();
         }

         List<AbstractMinecart> â˜ƒ = this.getInteractingMinecartOfType(â˜ƒ, â˜ƒ, AbstractMinecart.class, EntitySelector.CONTAINER_ENTITY_SELECTOR);
         if (!â˜ƒ.isEmpty()) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer((Container)â˜ƒ.get(0));
         }
      }

      return 0;
   }

   private <T extends AbstractMinecart> List<T> getInteractingMinecartOfType(Level var1, BlockPos var2, Class<T> var3, Predicate<Entity> var4) {
      return â˜ƒ.getEntitiesOfClass(â˜ƒ, this.getSearchBB(â˜ƒ), â˜ƒ);
   }

   private AABB getSearchBB(BlockPos var1) {
      double â˜ƒ = 0.2;
      return new AABB(
         (double)â˜ƒ.getX() + 0.2,
         (double)â˜ƒ.getY(),
         (double)â˜ƒ.getZ() + 0.2,
         (double)(â˜ƒ.getX() + 1) - 0.2,
         (double)(â˜ƒ.getY() + 1) - 0.2,
         (double)(â˜ƒ.getZ() + 1) - 0.2
      );
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
      â˜ƒ.add(SHAPE, POWERED, WATERLOGGED);
   }
}
