package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CactusBlock extends Block {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
   public static final int MAX_AGE = 15;
   protected static final int AABB_OFFSET = 1;
   protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
   protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   protected CactusBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
         int â˜ƒx = 1;

         while(â˜ƒ.getBlockState(â˜ƒ.below(â˜ƒx)).is(this)) {
            ++â˜ƒx;
         }

         if (â˜ƒx < 3) {
            int â˜ƒxx = â˜ƒ.getValue(AGE);
            if (â˜ƒxx == 15) {
               â˜ƒ.setBlockAndUpdate(â˜ƒ, this.defaultBlockState());
               BlockState â˜ƒxxx = â˜ƒ.setValue(AGE, Integer.valueOf(0));
               â˜ƒ.setBlock(â˜ƒ, â˜ƒxxx, 4);
               â˜ƒxxx.neighborChanged(â˜ƒ, â˜ƒ, this, â˜ƒ, false);
            } else {
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒxx + 1)), 4);
            }
         }
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return COLLISION_SHAPE;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return OUTLINE_SHAPE;
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
      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ));
         Material â˜ƒxx = â˜ƒx.getMaterial();
         if (â˜ƒxx.isSolid() || â˜ƒ.getFluidState(â˜ƒ.relative(â˜ƒ)).is(FluidTags.LAVA)) {
            return false;
         }
      }

      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      return (â˜ƒ.is(Blocks.CACTUS) || â˜ƒ.is(Blocks.SAND) || â˜ƒ.is(Blocks.RED_SAND)) && !â˜ƒ.getBlockState(â˜ƒ.above()).getMaterial().isLiquid();
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      â˜ƒ.hurt(DamageSource.CACTUS, 1.0F);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
