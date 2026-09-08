package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SugarCaneBlock extends Block {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   protected SugarCaneBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.isEmptyBlock(â˜ƒ.above())) {
         int â˜ƒ = 1;

         while(â˜ƒ.getBlockState(â˜ƒ.below(â˜ƒ)).is(this)) {
            ++â˜ƒ;
         }

         if (â˜ƒ < 3) {
            int â˜ƒx = â˜ƒ.getValue(AGE);
            if (â˜ƒx == 15) {
               â˜ƒ.setBlockAndUpdate(â˜ƒ.above(), this.defaultBlockState());
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(0)), 4);
            } else {
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒx + 1)), 4);
            }
         }
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      if (â˜ƒ.is(this)) {
         return true;
      } else {
         if (â˜ƒ.is(BlockTags.DIRT) || â˜ƒ.is(Blocks.SAND) || â˜ƒ.is(Blocks.RED_SAND)) {
            BlockPos â˜ƒ = â˜ƒ.below();

            for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
               BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒx));
               FluidState â˜ƒxxx = â˜ƒ.getFluidState(â˜ƒ.relative(â˜ƒx));
               if (â˜ƒxxx.is(FluidTags.WATER) || â˜ƒxx.is(Blocks.FROSTED_ICE)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }
}
