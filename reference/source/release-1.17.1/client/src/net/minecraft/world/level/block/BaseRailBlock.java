package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BaseRailBlock extends Block implements SimpleWaterloggedBlock {
   protected static final VoxelShape FLAT_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   protected static final VoxelShape HALF_BLOCK_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private final boolean isStraight;

   public static boolean isRail(Level var0, BlockPos var1) {
      return isRail(â˜ƒ.getBlockState(â˜ƒ));
   }

   public static boolean isRail(BlockState var0) {
      return â˜ƒ.is(BlockTags.RAILS) && â˜ƒ.getBlock() instanceof BaseRailBlock;
   }

   protected BaseRailBlock(boolean var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.isStraight = â˜ƒ;
   }

   public boolean isStraight() {
      return this.isStraight;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      RailShape â˜ƒ = â˜ƒ.is(this) ? â˜ƒ.getValue(this.getShapeProperty()) : null;
      return â˜ƒ != null && â˜ƒ.isAscending() ? HALF_BLOCK_AABB : FLAT_AABB;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return canSupportRigidBlock(â˜ƒ, â˜ƒ.below());
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.updateState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected BlockState updateState(BlockState var1, Level var2, BlockPos var3, boolean var4) {
      â˜ƒ = this.updateDir(â˜ƒ, â˜ƒ, â˜ƒ, true);
      if (this.isStraight) {
         â˜ƒ.neighborChanged(â˜ƒ, â˜ƒ, this, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide && â˜ƒ.getBlockState(â˜ƒ).is(this)) {
         RailShape â˜ƒ = â˜ƒ.getValue(this.getShapeProperty());
         if (shouldBeRemoved(â˜ƒ, â˜ƒ, â˜ƒ)) {
            dropResources(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.removeBlock(â˜ƒ, â˜ƒ);
         } else {
            this.updateState(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private static boolean shouldBeRemoved(BlockPos var0, Level var1, RailShape var2) {
      if (!canSupportRigidBlock(â˜ƒ, â˜ƒ.below())) {
         return true;
      } else {
         switch(â˜ƒ) {
            case ASCENDING_EAST:
               return !canSupportRigidBlock(â˜ƒ, â˜ƒ.east());
            case ASCENDING_WEST:
               return !canSupportRigidBlock(â˜ƒ, â˜ƒ.west());
            case ASCENDING_NORTH:
               return !canSupportRigidBlock(â˜ƒ, â˜ƒ.north());
            case ASCENDING_SOUTH:
               return !canSupportRigidBlock(â˜ƒ, â˜ƒ.south());
            default:
               return false;
         }
      }
   }

   protected void updateState(BlockState var1, Level var2, BlockPos var3, Block var4) {
   }

   protected BlockState updateDir(Level var1, BlockPos var2, BlockState var3, boolean var4) {
      if (â˜ƒ.isClientSide) {
         return â˜ƒ;
      } else {
         RailShape â˜ƒ = â˜ƒ.getValue(this.getShapeProperty());
         return new RailState(â˜ƒ, â˜ƒ, â˜ƒ).place(â˜ƒ.hasNeighborSignal(â˜ƒ), â˜ƒ, â˜ƒ).getState();
      }
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.NORMAL;
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ) {
         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (((RailShape)â˜ƒ.getValue(this.getShapeProperty())).isAscending()) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.above(), this);
         }

         if (this.isStraight) {
            â˜ƒ.updateNeighborsAt(â˜ƒ, this);
            â˜ƒ.updateNeighborsAt(â˜ƒ.below(), this);
         }
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      boolean â˜ƒx = â˜ƒ.getType() == Fluids.WATER;
      BlockState â˜ƒxx = super.defaultBlockState();
      Direction â˜ƒxxx = â˜ƒ.getHorizontalDirection();
      boolean â˜ƒxxxx = â˜ƒxxx == Direction.EAST || â˜ƒxxx == Direction.WEST;
      return â˜ƒxx.setValue(this.getShapeProperty(), â˜ƒxxxx ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH).setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx));
   }

   public abstract Property<RailShape> getShapeProperty();

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }
}
