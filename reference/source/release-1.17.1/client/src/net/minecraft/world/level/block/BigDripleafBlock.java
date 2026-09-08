package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigDripleafBlock extends HorizontalDirectionalBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final EnumProperty<Tilt> TILT = BlockStateProperties.TILT;
   private static final int NO_TICK = -1;
   private static final Object2IntMap<Tilt> DELAY_UNTIL_NEXT_TILT_STATE = Util.make(new Object2IntArrayMap<>(), var0 -> {
      var0.defaultReturnValue(-1);
      var0.put(Tilt.UNSTABLE, 10);
      var0.put(Tilt.PARTIAL, 10);
      var0.put(Tilt.FULL, 100);
   });
   private static final int MAX_GEN_HEIGHT = 5;
   private static final int STEM_WIDTH = 6;
   private static final int ENTITY_DETECTION_MIN_Y = 11;
   private static final int LOWEST_LEAF_TOP = 13;
   private static final Map<Tilt, VoxelShape> LEAF_SHAPES = ImmutableMap.of(
      Tilt.NONE,
      Block.box(0.0, 11.0, 0.0, 16.0, 15.0, 16.0),
      Tilt.UNSTABLE,
      Block.box(0.0, 11.0, 0.0, 16.0, 15.0, 16.0),
      Tilt.PARTIAL,
      Block.box(0.0, 11.0, 0.0, 16.0, 13.0, 16.0),
      Tilt.FULL,
      Shapes.empty()
   );
   private static final VoxelShape STEM_SLICER = Block.box(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
   private static final Map<Direction, VoxelShape> STEM_SHAPES = ImmutableMap.of(
      Direction.NORTH,
      Shapes.joinUnoptimized(BigDripleafStemBlock.NORTH_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST),
      Direction.SOUTH,
      Shapes.joinUnoptimized(BigDripleafStemBlock.SOUTH_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST),
      Direction.EAST,
      Shapes.joinUnoptimized(BigDripleafStemBlock.EAST_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST),
      Direction.WEST,
      Shapes.joinUnoptimized(BigDripleafStemBlock.WEST_SHAPE, STEM_SLICER, BooleanOp.ONLY_FIRST)
   );
   private final Map<BlockState, VoxelShape> shapesCache;

   protected BigDripleafBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING, Direction.NORTH).setValue(TILT, Tilt.NONE)
      );
      this.shapesCache = this.getShapeForEachState(BigDripleafBlock::calculateShape);
   }

   private static VoxelShape calculateShape(BlockState var0) {
      return Shapes.or((VoxelShape)LEAF_SHAPES.get(â˜ƒ.getValue(TILT)), (VoxelShape)STEM_SHAPES.get(â˜ƒ.getValue(FACING)));
   }

   public static void placeWithRandomHeight(LevelAccessor var0, Random var1, BlockPos var2, Direction var3) {
      int â˜ƒ = Mth.nextInt(â˜ƒ, 2, 5);
      BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();
      int â˜ƒxx = 0;

      while(â˜ƒxx < â˜ƒ && canPlaceAt(â˜ƒ, â˜ƒx, â˜ƒ.getBlockState(â˜ƒx))) {
         ++â˜ƒxx;
         â˜ƒx.move(Direction.UP);
      }

      int â˜ƒxxx = â˜ƒ.getY() + â˜ƒxx - 1;
      â˜ƒx.setY(â˜ƒ.getY());

      while(â˜ƒx.getY() < â˜ƒxxx) {
         BigDripleafStemBlock.place(â˜ƒ, â˜ƒx, â˜ƒ.getFluidState(â˜ƒx), â˜ƒ);
         â˜ƒx.move(Direction.UP);
      }

      place(â˜ƒ, â˜ƒx, â˜ƒ.getFluidState(â˜ƒx), â˜ƒ);
   }

   private static boolean canReplace(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.WATER) || â˜ƒ.is(Blocks.SMALL_DRIPLEAF);
   }

   protected static boolean canPlaceAt(LevelHeightAccessor var0, BlockPos var1, BlockState var2) {
      return !â˜ƒ.isOutsideBuildHeight(â˜ƒ) && canReplace(â˜ƒ);
   }

   protected static boolean place(LevelAccessor var0, BlockPos var1, FluidState var2, Direction var3) {
      BlockState â˜ƒ = Blocks.BIG_DRIPLEAF.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.isSourceOfType(Fluids.WATER))).setValue(FACING, â˜ƒ);
      return â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      this.setTiltAndScheduleTick(â˜ƒ, â˜ƒ, â˜ƒ.getBlockPos(), Tilt.FULL, SoundEvents.BIG_DRIPLEAF_TILT_DOWN);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒx.is(Blocks.BIG_DRIPLEAF_STEM) || â˜ƒx.is(this) || â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == Direction.DOWN && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         if (â˜ƒ.getValue(WATERLOGGED)) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         return â˜ƒ == Direction.UP && â˜ƒ.is(this) ? Blocks.BIG_DRIPLEAF_STEM.withPropertiesOf(â˜ƒ) : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.above());
      return canReplace(â˜ƒ);
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      BlockPos â˜ƒ = â˜ƒ.above();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (canPlaceAt(â˜ƒ, â˜ƒ, â˜ƒx)) {
         Direction â˜ƒxx = â˜ƒ.getValue(FACING);
         BigDripleafStemBlock.place(â˜ƒ, â˜ƒ, â˜ƒ.getFluidState(), â˜ƒxx);
         place(â˜ƒ, â˜ƒ, â˜ƒx.getFluidState(), â˜ƒxx);
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide) {
         if (â˜ƒ.getValue(TILT) == Tilt.NONE && canEntityTilt(â˜ƒ, â˜ƒ) && !â˜ƒ.hasNeighborSignal(â˜ƒ)) {
            this.setTiltAndScheduleTick(â˜ƒ, â˜ƒ, â˜ƒ, Tilt.UNSTABLE, null);
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.hasNeighborSignal(â˜ƒ)) {
         resetTilt(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         Tilt â˜ƒ = â˜ƒ.getValue(TILT);
         if (â˜ƒ == Tilt.UNSTABLE) {
            this.setTiltAndScheduleTick(â˜ƒ, â˜ƒ, â˜ƒ, Tilt.PARTIAL, SoundEvents.BIG_DRIPLEAF_TILT_DOWN);
         } else if (â˜ƒ == Tilt.PARTIAL) {
            this.setTiltAndScheduleTick(â˜ƒ, â˜ƒ, â˜ƒ, Tilt.FULL, SoundEvents.BIG_DRIPLEAF_TILT_DOWN);
         } else if (â˜ƒ == Tilt.FULL) {
            resetTilt(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (â˜ƒ.hasNeighborSignal(â˜ƒ)) {
         resetTilt(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static void playTiltSound(Level var0, BlockPos var1, SoundEvent var2) {
      float â˜ƒ = Mth.randomBetween(â˜ƒ.random, 0.8F, 1.2F);
      â˜ƒ.playSound(null, â˜ƒ, â˜ƒ, SoundSource.BLOCKS, 1.0F, â˜ƒ);
   }

   private static boolean canEntityTilt(BlockPos var0, Entity var1) {
      return â˜ƒ.isOnGround() && â˜ƒ.position().y > (double)((float)â˜ƒ.getY() + 0.6875F);
   }

   private void setTiltAndScheduleTick(BlockState var1, Level var2, BlockPos var3, Tilt var4, @Nullable SoundEvent var5) {
      setTilt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ != null) {
         playTiltSound(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      int â˜ƒ = DELAY_UNTIL_NEXT_TILT_STATE.getInt(â˜ƒ);
      if (â˜ƒ != -1) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, â˜ƒ);
      }
   }

   private static void resetTilt(BlockState var0, Level var1, BlockPos var2) {
      setTilt(â˜ƒ, â˜ƒ, â˜ƒ, Tilt.NONE);
      if (â˜ƒ.getValue(TILT) != Tilt.NONE) {
         playTiltSound(â˜ƒ, â˜ƒ, SoundEvents.BIG_DRIPLEAF_TILT_UP);
      }
   }

   private static void setTilt(BlockState var0, Level var1, BlockPos var2, Tilt var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(TILT, â˜ƒ), 2);
      if (â˜ƒ.causesVibration()) {
         â˜ƒ.gameEvent(GameEvent.BLOCK_CHANGE, â˜ƒ);
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)LEAF_SHAPES.get(â˜ƒ.getValue(TILT));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)this.shapesCache.get(â˜ƒ);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().below());
      FluidState â˜ƒx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      boolean â˜ƒxx = â˜ƒ.is(Blocks.BIG_DRIPLEAF) || â˜ƒ.is(Blocks.BIG_DRIPLEAF_STEM);
      return this.defaultBlockState()
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx.isSourceOfType(Fluids.WATER)))
         .setValue(FACING, â˜ƒxx ? (Direction)â˜ƒ.getValue(FACING) : â˜ƒ.getHorizontalDirection().getOpposite());
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(WATERLOGGED, FACING, TILT);
   }
}
