package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallSeagrassBlock extends DoublePlantBlock implements LiquidBlockContainer {
   public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   public TallSeagrassBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP) && !â˜ƒ.is(Blocks.MAGMA_BLOCK);
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(Blocks.SEAGRASS);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = super.getStateForPlacement(â˜ƒ);
      if (â˜ƒ != null) {
         FluidState â˜ƒx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos().above());
         if (â˜ƒx.is(FluidTags.WATER) && â˜ƒx.getAmount() == 8) {
            return â˜ƒ;
         }
      }

      return null;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      if (â˜ƒ.getValue(HALF) == DoubleBlockHalf.UPPER) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
         return â˜ƒ.is(this) && â˜ƒ.getValue(HALF) == DoubleBlockHalf.LOWER;
      } else {
         FluidState â˜ƒ = â˜ƒ.getFluidState(â˜ƒ);
         return super.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ) && â˜ƒ.is(FluidTags.WATER) && â˜ƒ.getAmount() == 8;
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return Fluids.WATER.getSource(false);
   }

   @Override
   public boolean canPlaceLiquid(BlockGetter var1, BlockPos var2, BlockState var3, Fluid var4) {
      return false;
   }

   @Override
   public boolean placeLiquid(LevelAccessor var1, BlockPos var2, BlockState var3, FluidState var4) {
      return false;
   }
}
