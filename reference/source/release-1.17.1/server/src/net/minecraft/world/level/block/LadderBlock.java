package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LadderBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final float AABB_OFFSET = 3.0F;
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape WEST_AABB = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

   protected LadderBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch((Direction)â˜ƒ.getValue(FACING)) {
         case NORTH:
            return NORTH_AABB;
         case SOUTH:
            return SOUTH_AABB;
         case WEST:
            return WEST_AABB;
         case EAST:
         default:
            return EAST_AABB;
      }
   }

   private boolean canAttachTo(BlockGetter var1, BlockPos var2, Direction var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      return this.canAttachTo(â˜ƒ, â˜ƒ.relative(â˜ƒ.getOpposite()), â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getOpposite() == â˜ƒ.getValue(FACING) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         if (â˜ƒ.getValue(WATERLOGGED)) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      if (!â˜ƒ.replacingClickedOnBlock()) {
         BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().relative(â˜ƒ.getClickedFace().getOpposite()));
         if (â˜ƒ.is(this) && â˜ƒ.getValue(FACING) == â˜ƒ.getClickedFace()) {
            return null;
         }
      }

      BlockState â˜ƒ = this.defaultBlockState();
      LevelReader â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = â˜ƒ.getClickedPos();
      FluidState â˜ƒxxx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());

      for(Direction â˜ƒxxxx : â˜ƒ.getNearestLookingDirections()) {
         if (â˜ƒxxxx.getAxis().isHorizontal()) {
            â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒxxxx.getOpposite());
            if (â˜ƒ.canSurvive(â˜ƒx, â˜ƒxx)) {
               return â˜ƒ.setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxxx.getType() == Fluids.WATER));
            }
         }
      }

      return null;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, WATERLOGGED);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }
}
