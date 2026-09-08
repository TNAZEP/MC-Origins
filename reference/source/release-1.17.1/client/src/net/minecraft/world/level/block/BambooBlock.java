package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BambooBlock extends Block implements BonemealableBlock {
   protected static final float SMALL_LEAVES_AABB_OFFSET = 3.0F;
   protected static final float LARGE_LEAVES_AABB_OFFSET = 5.0F;
   protected static final float COLLISION_AABB_OFFSET = 1.5F;
   protected static final VoxelShape SMALL_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   protected static final VoxelShape LARGE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   protected static final VoxelShape COLLISION_SHAPE = Block.box(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
   public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;
   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
   public static final int MAX_HEIGHT = 16;
   public static final int STAGE_GROWING = 0;
   public static final int STAGE_DONE_GROWING = 1;
   public static final int AGE_THIN_BAMBOO = 0;
   public static final int AGE_THICK_BAMBOO = 1;

   public BambooBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(LEAVES, BambooLeaves.NONE).setValue(STAGE, Integer.valueOf(0))
      );
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE, LEAVES, STAGE);
   }

   @Override
   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.XZ;
   }

   @Override
   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return true;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      VoxelShape â˜ƒ = â˜ƒ.getValue(LEAVES) == BambooLeaves.LARGE ? LARGE_SHAPE : SMALL_SHAPE;
      Vec3 â˜ƒx = â˜ƒ.getOffset(â˜ƒ, â˜ƒ);
      return â˜ƒ.move(â˜ƒx.x, â˜ƒx.y, â˜ƒx.z);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      Vec3 â˜ƒ = â˜ƒ.getOffset(â˜ƒ, â˜ƒ);
      return COLLISION_SHAPE.move(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   @Override
   public boolean isCollisionShapeFullBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
      return false;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      if (!â˜ƒ.isEmpty()) {
         return null;
      } else {
         BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().below());
         if (â˜ƒ.is(BlockTags.BAMBOO_PLANTABLE_ON)) {
            if (â˜ƒ.is(Blocks.BAMBOO_SAPLING)) {
               return this.defaultBlockState().setValue(AGE, Integer.valueOf(0));
            } else if (â˜ƒ.is(Blocks.BAMBOO)) {
               int â˜ƒx = â˜ƒ.getValue(AGE) > 0 ? 1 : 0;
               return this.defaultBlockState().setValue(AGE, Integer.valueOf(â˜ƒx));
            } else {
               BlockState â˜ƒx = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().above());
               return â˜ƒx.is(Blocks.BAMBOO) ? this.defaultBlockState().setValue(AGE, (Integer)â˜ƒx.getValue(AGE)) : Blocks.BAMBOO_SAPLING.defaultBlockState();
            }
         } else {
            return null;
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getValue(STAGE) == 0;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(STAGE) == 0) {
         if (â˜ƒ.nextInt(3) == 0 && â˜ƒ.isEmptyBlock(â˜ƒ.above()) && â˜ƒ.getRawBrightness(â˜ƒ.above(), 0) >= 9) {
            int â˜ƒ = this.getHeightBelowUpToMax(â˜ƒ, â˜ƒ) + 1;
            if (â˜ƒ < 16) {
               this.growBamboo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).is(BlockTags.BAMBOO_PLANTABLE_ON);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      if (â˜ƒ == Direction.UP && â˜ƒ.is(Blocks.BAMBOO) && â˜ƒ.getValue(AGE) > â˜ƒ.getValue(AGE)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.cycle(AGE), 2);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      int â˜ƒ = this.getHeightAboveUpToMax(â˜ƒ, â˜ƒ);
      int â˜ƒx = this.getHeightBelowUpToMax(â˜ƒ, â˜ƒ);
      return â˜ƒ + â˜ƒx + 1 < 16 && â˜ƒ.getBlockState(â˜ƒ.above(â˜ƒ)).getValue(STAGE) != 1;
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      int â˜ƒ = this.getHeightAboveUpToMax(â˜ƒ, â˜ƒ);
      int â˜ƒx = this.getHeightBelowUpToMax(â˜ƒ, â˜ƒ);
      int â˜ƒxx = â˜ƒ + â˜ƒx + 1;
      int â˜ƒxxx = 1 + â˜ƒ.nextInt(2);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx; ++â˜ƒxxxx) {
         BlockPos â˜ƒxxxxx = â˜ƒ.above(â˜ƒ);
         BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
         if (â˜ƒxx >= 16 || â˜ƒxxxxxx.getValue(STAGE) == 1 || !â˜ƒ.isEmptyBlock(â˜ƒxxxxx.above())) {
            return;
         }

         this.growBamboo(â˜ƒxxxxxx, â˜ƒ, â˜ƒxxxxx, â˜ƒ, â˜ƒxx);
         ++â˜ƒ;
         ++â˜ƒxx;
      }
   }

   @Override
   public float getDestroyProgress(BlockState var1, Player var2, BlockGetter var3, BlockPos var4) {
      return â˜ƒ.getMainHandItem().getItem() instanceof SwordItem ? 1.0F : super.getDestroyProgress(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected void growBamboo(BlockState var1, Level var2, BlockPos var3, Random var4, int var5) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      BlockPos â˜ƒx = â˜ƒ.below(2);
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      BambooLeaves â˜ƒxxx = BambooLeaves.NONE;
      if (â˜ƒ >= 1) {
         if (!â˜ƒ.is(Blocks.BAMBOO) || â˜ƒ.getValue(LEAVES) == BambooLeaves.NONE) {
            â˜ƒxxx = BambooLeaves.SMALL;
         } else if (â˜ƒ.is(Blocks.BAMBOO) && â˜ƒ.getValue(LEAVES) != BambooLeaves.NONE) {
            â˜ƒxxx = BambooLeaves.LARGE;
            if (â˜ƒxx.is(Blocks.BAMBOO)) {
               â˜ƒ.setBlock(â˜ƒ.below(), â˜ƒ.setValue(LEAVES, BambooLeaves.SMALL), 3);
               â˜ƒ.setBlock(â˜ƒx, â˜ƒxx.setValue(LEAVES, BambooLeaves.NONE), 3);
            }
         }
      }

      int â˜ƒ = â˜ƒ.getValue(AGE) != 1 && !â˜ƒxx.is(Blocks.BAMBOO) ? 0 : 1;
      int â˜ƒx = (â˜ƒ < 11 || !(â˜ƒ.nextFloat() < 0.25F)) && â˜ƒ != 15 ? 0 : 1;
      â˜ƒ.setBlock(â˜ƒ.above(), this.defaultBlockState().setValue(AGE, Integer.valueOf(â˜ƒ)).setValue(LEAVES, â˜ƒxxx).setValue(STAGE, Integer.valueOf(â˜ƒx)), 3);
   }

   protected int getHeightAboveUpToMax(BlockGetter var1, BlockPos var2) {
      int â˜ƒ = 0;

      while(â˜ƒ < 16 && â˜ƒ.getBlockState(â˜ƒ.above(â˜ƒ + 1)).is(Blocks.BAMBOO)) {
         ++â˜ƒ;
      }

      return â˜ƒ;
   }

   protected int getHeightBelowUpToMax(BlockGetter var1, BlockPos var2) {
      int â˜ƒ = 0;

      while(â˜ƒ < 16 && â˜ƒ.getBlockState(â˜ƒ.below(â˜ƒ + 1)).is(Blocks.BAMBOO)) {
         ++â˜ƒ;
      }

      return â˜ƒ;
   }
}
