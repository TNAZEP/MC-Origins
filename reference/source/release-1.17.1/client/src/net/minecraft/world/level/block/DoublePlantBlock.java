package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class DoublePlantBlock extends BushBlock {
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

   public DoublePlantBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      DoubleBlockHalf â˜ƒ = â˜ƒ.getValue(HALF);
      if (â˜ƒ.getAxis() != Direction.Axis.Y || â˜ƒ == DoubleBlockHalf.LOWER != (â˜ƒ == Direction.UP) || â˜ƒ.is(this) && â˜ƒ.getValue(HALF) != â˜ƒ) {
         return â˜ƒ == DoubleBlockHalf.LOWER && â˜ƒ == Direction.DOWN && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      Level â˜ƒx = â˜ƒ.getLevel();
      return â˜ƒ.getY() < â˜ƒx.getMaxBuildHeight() - 1 && â˜ƒx.getBlockState(â˜ƒ.above()).canBeReplaced(â˜ƒ) ? super.getStateForPlacement(â˜ƒ) : null;
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      BlockPos â˜ƒ = â˜ƒ.above();
      â˜ƒ.setBlock(â˜ƒ, copyWaterloggedFrom(â˜ƒ, â˜ƒ, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER)), 3);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      if (â˜ƒ.getValue(HALF) != DoubleBlockHalf.UPPER) {
         return super.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
         return â˜ƒ.is(this) && â˜ƒ.getValue(HALF) == DoubleBlockHalf.LOWER;
      }
   }

   public static void placeAt(LevelAccessor var0, BlockState var1, BlockPos var2, int var3) {
      BlockPos â˜ƒ = â˜ƒ.above();
      â˜ƒ.setBlock(â˜ƒ, copyWaterloggedFrom(â˜ƒ, â˜ƒ, â˜ƒ.setValue(HALF, DoubleBlockHalf.LOWER)), â˜ƒ);
      â˜ƒ.setBlock(â˜ƒ, copyWaterloggedFrom(â˜ƒ, â˜ƒ, â˜ƒ.setValue(HALF, DoubleBlockHalf.UPPER)), â˜ƒ);
   }

   public static BlockState copyWaterloggedFrom(LevelReader var0, BlockPos var1, BlockState var2) {
      return â˜ƒ.hasProperty(BlockStateProperties.WATERLOGGED) ? â˜ƒ.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(â˜ƒ.isWaterAt(â˜ƒ))) : â˜ƒ;
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide) {
         if (â˜ƒ.isCreative()) {
            preventCreativeDropFromBottomPart(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            dropResources(â˜ƒ, â˜ƒ, â˜ƒ, null, â˜ƒ, â˜ƒ.getMainHandItem());
         }
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void playerDestroy(Level var1, Player var2, BlockPos var3, BlockState var4, @Nullable BlockEntity var5, ItemStack var6) {
      super.playerDestroy(â˜ƒ, â˜ƒ, â˜ƒ, Blocks.AIR.defaultBlockState(), â˜ƒ, â˜ƒ);
   }

   protected static void preventCreativeDropFromBottomPart(Level var0, BlockPos var1, BlockState var2, Player var3) {
      DoubleBlockHalf â˜ƒ = â˜ƒ.getValue(HALF);
      if (â˜ƒ == DoubleBlockHalf.UPPER) {
         BlockPos â˜ƒx = â˜ƒ.below();
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         if (â˜ƒxx.is(â˜ƒ.getBlock()) && â˜ƒxx.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState â˜ƒxxx = â˜ƒxx.hasProperty(BlockStateProperties.WATERLOGGED) && â˜ƒxx.getValue(BlockStateProperties.WATERLOGGED)
               ? Blocks.WATER.defaultBlockState()
               : Blocks.AIR.defaultBlockState();
            â˜ƒ.setBlock(â˜ƒx, â˜ƒxxx, 35);
            â˜ƒ.levelEvent(â˜ƒ, 2001, â˜ƒx, Block.getId(â˜ƒxx));
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(HALF);
   }

   @Override
   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.XZ;
   }

   @Override
   public long getSeed(BlockState var1, BlockPos var2) {
      return Mth.getSeed(â˜ƒ.getX(), â˜ƒ.below(â˜ƒ.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), â˜ƒ.getZ());
   }
}
