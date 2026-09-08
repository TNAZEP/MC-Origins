package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SlabBlock extends Block implements SimpleWaterloggedBlock {
   public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape BOTTOM_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape TOP_AABB = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

   public SlabBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return â˜ƒ.getValue(TYPE) != SlabType.DOUBLE;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(TYPE, WATERLOGGED);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      SlabType â˜ƒ = â˜ƒ.getValue(TYPE);
      switch(â˜ƒ) {
         case DOUBLE:
            return Shapes.block();
         case TOP:
            return TOP_AABB;
         default:
            return BOTTOM_AABB;
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      BlockState â˜ƒx = â˜ƒ.getLevel().getBlockState(â˜ƒ);
      if (â˜ƒx.is(this)) {
         return â˜ƒx.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
      } else {
         FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ);
         BlockState â˜ƒx = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.getType() == Fluids.WATER));
         Direction â˜ƒxx = â˜ƒ.getClickedFace();
         return â˜ƒxx != Direction.DOWN && (â˜ƒxx == Direction.UP || !(â˜ƒ.getClickLocation().y - (double)â˜ƒ.getY() > 0.5))
            ? â˜ƒx
            : â˜ƒx.setValue(TYPE, SlabType.TOP);
      }
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand();
      SlabType â˜ƒx = â˜ƒ.getValue(TYPE);
      if (â˜ƒx == SlabType.DOUBLE || !â˜ƒ.is(this.asItem())) {
         return false;
      } else if (â˜ƒ.replacingClickedOnBlock()) {
         boolean â˜ƒ = â˜ƒ.getClickLocation().y - (double)â˜ƒ.getClickedPos().getY() > 0.5;
         Direction â˜ƒx = â˜ƒ.getClickedFace();
         if (â˜ƒx == SlabType.BOTTOM) {
            return â˜ƒx == Direction.UP || â˜ƒ && â˜ƒx.getAxis().isHorizontal();
         } else {
            return â˜ƒx == Direction.DOWN || !â˜ƒ && â˜ƒx.getAxis().isHorizontal();
         }
      } else {
         return true;
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean placeLiquid(LevelAccessor var1, BlockPos var2, BlockState var3, FluidState var4) {
      return â˜ƒ.getValue(TYPE) != SlabType.DOUBLE ? SimpleWaterloggedBlock.super.placeLiquid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : false;
   }

   @Override
   public boolean canPlaceLiquid(BlockGetter var1, BlockPos var2, BlockState var3, Fluid var4) {
      return â˜ƒ.getValue(TYPE) != SlabType.DOUBLE ? SimpleWaterloggedBlock.super.canPlaceLiquid(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : false;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      switch(â˜ƒ) {
         case LAND:
            return false;
         case WATER:
            return â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER);
         case AIR:
            return false;
         default:
            return false;
      }
   }
}
