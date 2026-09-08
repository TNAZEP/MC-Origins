package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SmallDripleafBlock extends DoublePlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   public SmallDripleafBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH)
      );
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.is(BlockTags.SMALL_DRIPLEAF_PLACEABLE) || â˜ƒ.getFluidState(â˜ƒ.above()).isSourceOfType(Fluids.WATER) && super.mayPlaceOn(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = super.getStateForPlacement(â˜ƒ);
      return â˜ƒ != null ? copyWaterloggedFrom(â˜ƒ.getLevel(), â˜ƒ.getClickedPos(), â˜ƒ.setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite())) : null;
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (!â˜ƒ.isClientSide()) {
         BlockPos â˜ƒ = â˜ƒ.above();
         BlockState â˜ƒx = DoublePlantBlock.copyWaterloggedFrom(
            â˜ƒ, â˜ƒ, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, (Direction)â˜ƒ.getValue(FACING))
         );
         â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 3);
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      if (â˜ƒ.getValue(HALF) == DoubleBlockHalf.UPPER) {
         return super.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         BlockPos â˜ƒ = â˜ƒ.below();
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         return this.mayPlaceOn(â˜ƒx, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(HALF, WATERLOGGED, FACING);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      if (â˜ƒ.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
         BlockPos â˜ƒ = â˜ƒ.above();
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.getFluidState(â˜ƒ).createLegacyBlock(), 18);
         BigDripleafBlock.placeWithRandomHeight(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getValue(FACING));
      } else {
         BlockPos â˜ƒ = â˜ƒ.below();
         this.performBonemeal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ));
      }
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
   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.XYZ;
   }

   @Override
   public float getMaxVerticalOffset() {
      return 0.1F;
   }
}
