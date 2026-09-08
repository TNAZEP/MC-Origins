package net.minecraft.world.level.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigDripleafStemBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final int STEM_WIDTH = 6;
   protected static final VoxelShape NORTH_SHAPE = Block.box(5.0, 0.0, 9.0, 11.0, 16.0, 15.0);
   protected static final VoxelShape SOUTH_SHAPE = Block.box(5.0, 0.0, 1.0, 11.0, 16.0, 7.0);
   protected static final VoxelShape EAST_SHAPE = Block.box(1.0, 0.0, 5.0, 7.0, 16.0, 11.0);
   protected static final VoxelShape WEST_SHAPE = Block.box(9.0, 0.0, 5.0, 15.0, 16.0, 11.0);

   protected BigDripleafStemBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch((Direction)â˜ƒ.getValue(FACING)) {
         case SOUTH:
            return SOUTH_SHAPE;
         case NORTH:
         default:
            return NORTH_SHAPE;
         case WEST:
            return WEST_SHAPE;
         case EAST:
            return EAST_SHAPE;
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(WATERLOGGED, FACING);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.above());
      return (â˜ƒx.is(this) || â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP)) && (â˜ƒxx.is(this) || â˜ƒxx.is(Blocks.BIG_DRIPLEAF));
   }

   protected static boolean place(LevelAccessor var0, BlockPos var1, FluidState var2, Direction var3) {
      BlockState â˜ƒ = Blocks.BIG_DRIPLEAF_STEM
         .defaultBlockState()
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.isSourceOfType(Fluids.WATER)))
         .setValue(FACING, â˜ƒ);
      return â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if ((â˜ƒ == Direction.DOWN || â˜ƒ == Direction.UP) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      Optional<BlockPos> â˜ƒ = BlockUtil.getTopConnectedBlock(â˜ƒ, â˜ƒ, â˜ƒ.getBlock(), Direction.UP, Blocks.BIG_DRIPLEAF);
      if (!â˜ƒ.isPresent()) {
         return false;
      } else {
         BlockPos â˜ƒ = ((BlockPos)â˜ƒ.get()).above();
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
         return BigDripleafBlock.canPlaceAt(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      Optional<BlockPos> â˜ƒ = BlockUtil.getTopConnectedBlock(â˜ƒ, â˜ƒ, â˜ƒ.getBlock(), Direction.UP, Blocks.BIG_DRIPLEAF);
      if (â˜ƒ.isPresent()) {
         BlockPos â˜ƒx = (BlockPos)â˜ƒ.get();
         BlockPos â˜ƒxx = â˜ƒx.above();
         Direction â˜ƒxxx = â˜ƒ.getValue(FACING);
         place(â˜ƒ, â˜ƒx, â˜ƒ.getFluidState(â˜ƒx), â˜ƒxxx);
         BigDripleafBlock.place(â˜ƒ, â˜ƒxx, â˜ƒ.getFluidState(â˜ƒxx), â˜ƒxxx);
      }
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return new ItemStack(Blocks.BIG_DRIPLEAF);
   }
}
