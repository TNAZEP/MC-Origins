package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrapDoorBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final int AABB_THICKNESS = 3;
   protected static final VoxelShape EAST_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape WEST_OPEN_AABB = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape BOTTOM_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape TOP_AABB = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);

   protected TrapDoorBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(OPEN, Boolean.valueOf(false))
            .setValue(HALF, Half.BOTTOM)
            .setValue(POWERED, Boolean.valueOf(false))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (!â˜ƒ.getValue(OPEN)) {
         return â˜ƒ.getValue(HALF) == Half.TOP ? TOP_AABB : BOTTOM_AABB;
      } else {
         switch((Direction)â˜ƒ.getValue(FACING)) {
            case NORTH:
            default:
               return NORTH_OPEN_AABB;
            case SOUTH:
               return SOUTH_OPEN_AABB;
            case WEST:
               return WEST_OPEN_AABB;
            case EAST:
               return EAST_OPEN_AABB;
         }
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      switch(â˜ƒ) {
         case LAND:
            return â˜ƒ.getValue(OPEN);
         case WATER:
            return â˜ƒ.getValue(WATERLOGGED);
         case AIR:
            return â˜ƒ.getValue(OPEN);
         default:
            return false;
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (this.material == Material.METAL) {
         return InteractionResult.PASS;
      } else {
         â˜ƒ = â˜ƒ.cycle(OPEN);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
         if (â˜ƒ.getValue(WATERLOGGED)) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         this.playSound(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getValue(OPEN));
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   protected void playSound(@Nullable Player var1, Level var2, BlockPos var3, boolean var4) {
      if (â˜ƒ) {
         int â˜ƒ = this.material == Material.METAL ? 1037 : 1007;
         â˜ƒ.levelEvent(â˜ƒ, â˜ƒ, â˜ƒ, 0);
      } else {
         int â˜ƒ = this.material == Material.METAL ? 1036 : 1013;
         â˜ƒ.levelEvent(â˜ƒ, â˜ƒ, â˜ƒ, 0);
      }

      â˜ƒ.gameEvent(â˜ƒ, â˜ƒ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, â˜ƒ);
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide) {
         boolean â˜ƒ = â˜ƒ.hasNeighborSignal(â˜ƒ);
         if (â˜ƒ != â˜ƒ.getValue(POWERED)) {
            if (â˜ƒ.getValue(OPEN) != â˜ƒ) {
               â˜ƒ = â˜ƒ.setValue(OPEN, Boolean.valueOf(â˜ƒ));
               this.playSound(null, â˜ƒ, â˜ƒ, â˜ƒ);
            }

            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ)), 2);
            if (â˜ƒ.getValue(WATERLOGGED)) {
               â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
            }
         }
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.defaultBlockState();
      FluidState â˜ƒx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      Direction â˜ƒxx = â˜ƒ.getClickedFace();
      if (!â˜ƒ.replacingClickedOnBlock() && â˜ƒxx.getAxis().isHorizontal()) {
         â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒxx).setValue(HALF, â˜ƒ.getClickLocation().y - (double)â˜ƒ.getClickedPos().getY() > 0.5 ? Half.TOP : Half.BOTTOM);
      } else {
         â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite()).setValue(HALF, â˜ƒxx == Direction.UP ? Half.BOTTOM : Half.TOP);
      }

      if (â˜ƒ.getLevel().hasNeighborSignal(â˜ƒ.getClickedPos())) {
         â˜ƒ = â˜ƒ.setValue(OPEN, Boolean.valueOf(true)).setValue(POWERED, Boolean.valueOf(true));
      }

      return â˜ƒ.setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx.getType() == Fluids.WATER));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, OPEN, HALF, POWERED, WATERLOGGED);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
