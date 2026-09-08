package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScaffoldingBlock extends Block implements SimpleWaterloggedBlock {
   private static final int TICK_DELAY = 1;
   private static final VoxelShape STABLE_SHAPE;
   private static final VoxelShape UNSTABLE_SHAPE;
   private static final VoxelShape UNSTABLE_SHAPE_BOTTOM = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   private static final VoxelShape BELOW_BLOCK = Shapes.block().move(0.0, -1.0, 0.0);
   public static final int STABILITY_MAX_DISTANCE = 7;
   public static final IntegerProperty DISTANCE = BlockStateProperties.STABILITY_DISTANCE;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;

   protected ScaffoldingBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(DISTANCE, Integer.valueOf(7))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
            .setValue(BOTTOM, Boolean.valueOf(false))
      );
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(DISTANCE, WATERLOGGED, BOTTOM);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (!â˜ƒ.isHoldingItem(â˜ƒ.getBlock().asItem())) {
         return â˜ƒ.getValue(BOTTOM) ? UNSTABLE_SHAPE : STABLE_SHAPE;
      } else {
         return Shapes.block();
      }
   }

   @Override
   public VoxelShape getInteractionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return Shapes.block();
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      return â˜ƒ.getItemInHand().is(this.asItem());
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      Level â˜ƒx = â˜ƒ.getLevel();
      int â˜ƒxx = getDistance(â˜ƒx, â˜ƒ);
      return this.defaultBlockState()
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx.getFluidState(â˜ƒ).getType() == Fluids.WATER))
         .setValue(DISTANCE, Integer.valueOf(â˜ƒxx))
         .setValue(BOTTOM, Boolean.valueOf(this.isBottom(â˜ƒx, â˜ƒ, â˜ƒxx)));
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.isClientSide) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      if (!â˜ƒ.isClientSide()) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      return â˜ƒ;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      int â˜ƒ = getDistance(â˜ƒ, â˜ƒ);
      BlockState â˜ƒx = â˜ƒ.setValue(DISTANCE, Integer.valueOf(â˜ƒ)).setValue(BOTTOM, Boolean.valueOf(this.isBottom(â˜ƒ, â˜ƒ, â˜ƒ)));
      if (â˜ƒx.getValue(DISTANCE) == 7) {
         if (â˜ƒ.getValue(DISTANCE) == 7) {
            â˜ƒ.addFreshEntity(
               new FallingBlockEntity(
                  â˜ƒ, (double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5, â˜ƒx.setValue(WATERLOGGED, Boolean.valueOf(false))
               )
            );
         } else {
            â˜ƒ.destroyBlock(â˜ƒ, true);
         }
      } else if (â˜ƒ != â˜ƒx) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 3);
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return getDistance(â˜ƒ, â˜ƒ) < 7;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ.isAbove(Shapes.block(), â˜ƒ, true) && !â˜ƒ.isDescending()) {
         return STABLE_SHAPE;
      } else {
         return â˜ƒ.getValue(DISTANCE) != 0 && â˜ƒ.getValue(BOTTOM) && â˜ƒ.isAbove(BELOW_BLOCK, â˜ƒ, true) ? UNSTABLE_SHAPE_BOTTOM : Shapes.empty();
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   private boolean isBottom(BlockGetter var1, BlockPos var2, int var3) {
      return â˜ƒ > 0 && !â˜ƒ.getBlockState(â˜ƒ.below()).is(this);
   }

   public static int getDistance(BlockGetter var0, BlockPos var1) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable().move(Direction.DOWN);
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      int â˜ƒxx = 7;
      if (â˜ƒx.is(Blocks.SCAFFOLDING)) {
         â˜ƒxx = â˜ƒx.getValue(DISTANCE);
      } else if (â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP)) {
         return 0;
      }

      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.setWithOffset(â˜ƒ, â˜ƒ));
         if (â˜ƒx.is(Blocks.SCAFFOLDING)) {
            â˜ƒxx = Math.min(â˜ƒxx, â˜ƒx.getValue(DISTANCE) + 1);
            if (â˜ƒxx == 1) {
               break;
            }
         }
      }

      return â˜ƒxx;
   }

   static {
      VoxelShape â˜ƒ = Block.box(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
      VoxelShape â˜ƒx = Block.box(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
      VoxelShape â˜ƒxx = Block.box(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
      VoxelShape â˜ƒxxx = Block.box(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
      VoxelShape â˜ƒxxxx = Block.box(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
      STABLE_SHAPE = Shapes.or(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      VoxelShape â˜ƒxxxxx = Block.box(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
      VoxelShape â˜ƒxxxxxx = Block.box(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
      VoxelShape â˜ƒxxxxxxx = Block.box(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
      VoxelShape â˜ƒxxxxxxxx = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
      UNSTABLE_SHAPE = Shapes.or(ScaffoldingBlock.UNSTABLE_SHAPE_BOTTOM, STABLE_SHAPE, â˜ƒxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
   }
}
