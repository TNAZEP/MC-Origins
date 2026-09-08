package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeaPickleBlock extends BushBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   public static final int MAX_PICKLES = 4;
   public static final IntegerProperty PICKLES = BlockStateProperties.PICKLES;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape ONE_AABB = Block.box(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
   protected static final VoxelShape TWO_AABB = Block.box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
   protected static final VoxelShape THREE_AABB = Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
   protected static final VoxelShape FOUR_AABB = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

   protected SeaPickleBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(PICKLES, Integer.valueOf(1)).setValue(WATERLOGGED, Boolean.valueOf(true)));
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos());
      if (â˜ƒ.is(this)) {
         return â˜ƒ.setValue(PICKLES, Integer.valueOf(Math.min(4, â˜ƒ.getValue(PICKLES) + 1)));
      } else {
         FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
         boolean â˜ƒx = â˜ƒ.getType() == Fluids.WATER;
         return super.getStateForPlacement(â˜ƒ).setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx));
      }
   }

   public static boolean isDead(BlockState var0) {
      return !â˜ƒ.getValue(WATERLOGGED);
   }

   @Override
   protected boolean mayPlaceOn(BlockState var1, BlockGetter var2, BlockPos var3) {
      return !â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ).getFaceShape(Direction.UP).isEmpty() || â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      return this.mayPlaceOn(â˜ƒ.getBlockState(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         if (â˜ƒ.getValue(WATERLOGGED)) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      return !â˜ƒ.isSecondaryUseActive() && â˜ƒ.getItemInHand().is(this.asItem()) && â˜ƒ.getValue(PICKLES) < 4 ? true : super.canBeReplaced(â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch(â˜ƒ.getValue(PICKLES)) {
         case 1:
         default:
            return ONE_AABB;
         case 2:
            return TWO_AABB;
         case 3:
            return THREE_AABB;
         case 4:
            return FOUR_AABB;
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(PICKLES, WATERLOGGED);
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
      if (!isDead(â˜ƒ) && â˜ƒ.getBlockState(â˜ƒ.below()).is(BlockTags.CORAL_BLOCKS)) {
         int â˜ƒ = 5;
         int â˜ƒx = 1;
         int â˜ƒxx = 2;
         int â˜ƒxxx = 0;
         int â˜ƒxxxx = â˜ƒ.getX() - 2;
         int â˜ƒxxxxx = 0;

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 5; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < â˜ƒx; ++â˜ƒxxxxxxx) {
               int â˜ƒxxxxxxxx = 2 + â˜ƒ.getY() - 1;

               for(int â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx - 2; â˜ƒxxxxxxxxx < â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxx) {
                  BlockPos â˜ƒxxxxxxxxxx = new BlockPos(â˜ƒxxxx + â˜ƒxxxxxx, â˜ƒxxxxxxxxx, â˜ƒ.getZ() - â˜ƒxxxxx + â˜ƒxxxxxxx);
                  if (â˜ƒxxxxxxxxxx != â˜ƒ && â˜ƒ.nextInt(6) == 0 && â˜ƒ.getBlockState(â˜ƒxxxxxxxxxx).is(Blocks.WATER)) {
                     BlockState â˜ƒxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxx.below());
                     if (â˜ƒxxxxxxxxxxx.is(BlockTags.CORAL_BLOCKS)) {
                        â˜ƒ.setBlock(â˜ƒxxxxxxxxxx, Blocks.SEA_PICKLE.defaultBlockState().setValue(PICKLES, Integer.valueOf(â˜ƒ.nextInt(4) + 1)), 3);
                     }
                  }
               }
            }

            if (â˜ƒxxx < 2) {
               â˜ƒx += 2;
               ++â˜ƒxxxxx;
            } else {
               â˜ƒx -= 2;
               --â˜ƒxxxxx;
            }

            ++â˜ƒxxx;
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(PICKLES, Integer.valueOf(4)), 2);
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
