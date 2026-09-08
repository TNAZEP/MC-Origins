package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class StandingSignBlock extends SignBlock {
   public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

   public StandingSignBlock(BlockBehaviour.Properties var1, WoodType var2) {
      super(â˜ƒ, â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, Integer.valueOf(0)).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).getMaterial().isSolid();
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      return this.defaultBlockState()
         .setValue(ROTATION, Integer.valueOf(Mth.floor((double)((180.0F + â˜ƒ.getRotation()) * 16.0F / 360.0F) + 0.5) & 15))
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.getType() == Fluids.WATER));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.DOWN && !this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(ROTATION, Integer.valueOf(â˜ƒ.rotate(â˜ƒ.getValue(ROTATION), 16)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.setValue(ROTATION, Integer.valueOf(â˜ƒ.mirror(â˜ƒ.getValue(ROTATION), 16)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(ROTATION, WATERLOGGED);
   }
}
