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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AmethystClusterBlock extends AmethystBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   protected final VoxelShape northAabb;
   protected final VoxelShape southAabb;
   protected final VoxelShape eastAabb;
   protected final VoxelShape westAabb;
   protected final VoxelShape upAabb;
   protected final VoxelShape downAabb;

   public AmethystClusterBlock(int var1, int var2, BlockBehaviour.Properties var3) {
      super(â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.UP));
      this.upAabb = Block.box((double)â˜ƒ, 0.0, (double)â˜ƒ, (double)(16 - â˜ƒ), (double)â˜ƒ, (double)(16 - â˜ƒ));
      this.downAabb = Block.box((double)â˜ƒ, (double)(16 - â˜ƒ), (double)â˜ƒ, (double)(16 - â˜ƒ), 16.0, (double)(16 - â˜ƒ));
      this.northAabb = Block.box((double)â˜ƒ, (double)â˜ƒ, (double)(16 - â˜ƒ), (double)(16 - â˜ƒ), (double)(16 - â˜ƒ), 16.0);
      this.southAabb = Block.box((double)â˜ƒ, (double)â˜ƒ, 0.0, (double)(16 - â˜ƒ), (double)(16 - â˜ƒ), (double)â˜ƒ);
      this.eastAabb = Block.box(0.0, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, (double)(16 - â˜ƒ), (double)(16 - â˜ƒ));
      this.westAabb = Block.box((double)(16 - â˜ƒ), (double)â˜ƒ, (double)â˜ƒ, 16.0, (double)(16 - â˜ƒ), (double)(16 - â˜ƒ));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      switch(â˜ƒ) {
         case NORTH:
            return this.northAabb;
         case SOUTH:
            return this.southAabb;
         case EAST:
            return this.eastAabb;
         case WEST:
            return this.westAabb;
         case DOWN:
            return this.downAabb;
         case UP:
         default:
            return this.upAabb;
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ.getOpposite());
      return â˜ƒ.getBlockState(â˜ƒx).isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return â˜ƒ == ((Direction)â˜ƒ.getValue(FACING)).getOpposite() && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      LevelAccessor â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      return this.defaultBlockState()
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.getFluidState(â˜ƒx).getType() == Fluids.WATER))
         .setValue(FACING, â˜ƒ.getClickedFace());
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
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(WATERLOGGED, FACING);
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }
}
