package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PointedDripstoneBlock extends Block implements Fallable, SimpleWaterloggedBlock {
   public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
   public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
   private static final int MAX_SEARCH_LENGTH_WHEN_LOOKING_FOR_TIP_OF_FALLING_STALACTITE = Integer.MAX_VALUE;
   private static final int DELAY_BEFORE_FALLING = 2;
   private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
   private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = 0.12F;
   private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;
   private static final float WATER_CAULDRON_FILL_PROBABILITY_PER_RANDOM_TICK = 0.17578125F;
   private static final float LAVA_CAULDRON_FILL_PROBABILITY_PER_RANDOM_TICK = 0.05859375F;
   private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6;
   private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
   private static final int STALACTITE_MAX_DAMAGE = 40;
   private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
   private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
   private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
   private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
   private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
   private static final int MAX_GROWTH_LENGTH = 7;
   private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
   private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;
   private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
   private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
   private static final float MAX_HORIZONTAL_OFFSET = 0.125F;

   public PointedDripstoneBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(TIP_DIRECTION, Direction.UP)
            .setValue(THICKNESS, DripstoneThickness.TIP)
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return isValidPointedDripstonePlacement(â˜ƒ, â˜ƒ, â˜ƒ.getValue(TIP_DIRECTION));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      if (â˜ƒ != Direction.UP && â˜ƒ != Direction.DOWN) {
         return â˜ƒ;
      } else {
         Direction â˜ƒ = â˜ƒ.getValue(TIP_DIRECTION);
         if (â˜ƒ == Direction.DOWN && â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
            return â˜ƒ;
         } else if (â˜ƒ == â˜ƒ.getOpposite() && !this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ)) {
            if (â˜ƒ == Direction.DOWN) {
               this.scheduleStalactiteFallTicks(â˜ƒ, â˜ƒ, â˜ƒ);
            } else {
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
            }

            return â˜ƒ;
         } else {
            boolean â˜ƒ = â˜ƒ.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
            DripstoneThickness â˜ƒx = calculateDripstoneThickness(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            return â˜ƒ.setValue(THICKNESS, â˜ƒx);
         }
      }
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      BlockPos â˜ƒ = â˜ƒ.getBlockPos();
      if (!â˜ƒ.isClientSide && â˜ƒ.mayInteract(â˜ƒ, â˜ƒ) && â˜ƒ instanceof ThrownTrident && â˜ƒ.getDeltaMovement().length() > 0.6) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      if (â˜ƒ.getValue(TIP_DIRECTION) == Direction.UP && â˜ƒ.getValue(THICKNESS) == DripstoneThickness.TIP) {
         â˜ƒ.causeFallDamage(â˜ƒ + 2.0F, 2.0F, DamageSource.STALAGMITE);
      } else {
         super.fallOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (canDrip(â˜ƒ)) {
         float â˜ƒ = â˜ƒ.nextFloat();
         if (!(â˜ƒ > 0.12F)) {
            getFluidAboveStalactite(â˜ƒ, â˜ƒ, â˜ƒ)
               .filter(var1x -> â˜ƒ < 0.02F || canFillCauldron(var1x))
               .ifPresent(var3x -> spawnDripParticle(â˜ƒ, â˜ƒ, â˜ƒ, var3x));
         }
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (isStalagmite(â˜ƒ) && !this.canSurvive(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      } else {
         spawnFallingStalactite(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      maybeFillCauldron(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.nextFloat());
      if (â˜ƒ.nextFloat() < 0.011377778F && isStalactiteStartPos(â˜ƒ, â˜ƒ, â˜ƒ)) {
         growStalactiteOrStalagmiteIfPossible(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @VisibleForTesting
   public static void maybeFillCauldron(BlockState var0, ServerLevel var1, BlockPos var2, float var3) {
      if (!(â˜ƒ > 0.17578125F) || !(â˜ƒ > 0.05859375F)) {
         if (isStalactiteStartPos(â˜ƒ, â˜ƒ, â˜ƒ)) {
            Fluid â˜ƒx = getCauldronFillFluidType(â˜ƒ, â˜ƒ);
            float â˜ƒ;
            if (â˜ƒx == Fluids.WATER) {
               â˜ƒ = 0.17578125F;
            } else {
               if (â˜ƒx != Fluids.LAVA) {
                  return;
               }

               â˜ƒ = 0.05859375F;
            }

            if (!(â˜ƒ >= â˜ƒ)) {
               BlockPos â˜ƒ = findTip(â˜ƒ, â˜ƒ, â˜ƒ, 11, false);
               if (â˜ƒ != null) {
                  BlockPos â˜ƒx = findFillableCauldronBelowStalactiteTip(â˜ƒ, â˜ƒ, â˜ƒx);
                  if (â˜ƒx != null) {
                     â˜ƒ.levelEvent(1504, â˜ƒ, 0);
                     int â˜ƒxx = â˜ƒ.getY() - â˜ƒx.getY();
                     int â˜ƒxxx = 50 + â˜ƒxx;
                     BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒx);
                     â˜ƒ.getBlockTicks().scheduleTick(â˜ƒx, â˜ƒxxxx.getBlock(), â˜ƒxxx);
                  }
               }
            }
         }
      }
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      LevelAccessor â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      Direction â˜ƒxx = â˜ƒ.getNearestLookingVerticalDirection().getOpposite();
      Direction â˜ƒxxx = calculateTipDirection(â˜ƒ, â˜ƒx, â˜ƒxx);
      if (â˜ƒxxx == null) {
         return null;
      } else {
         boolean â˜ƒ = !â˜ƒ.isSecondaryUseActive();
         DripstoneThickness â˜ƒx = calculateDripstoneThickness(â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒ);
         return â˜ƒx == null
            ? null
            : this.defaultBlockState()
               .setValue(TIP_DIRECTION, â˜ƒxxx)
               .setValue(THICKNESS, â˜ƒx)
               .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.getFluidState(â˜ƒx).getType() == Fluids.WATER));
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public VoxelShape getOcclusionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return Shapes.empty();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      DripstoneThickness â˜ƒx = â˜ƒ.getValue(THICKNESS);
      VoxelShape â˜ƒ;
      if (â˜ƒx == DripstoneThickness.TIP_MERGE) {
         â˜ƒ = TIP_MERGE_SHAPE;
      } else if (â˜ƒx == DripstoneThickness.TIP) {
         if (â˜ƒ.getValue(TIP_DIRECTION) == Direction.DOWN) {
            â˜ƒ = TIP_SHAPE_DOWN;
         } else {
            â˜ƒ = TIP_SHAPE_UP;
         }
      } else if (â˜ƒx == DripstoneThickness.FRUSTUM) {
         â˜ƒ = FRUSTUM_SHAPE;
      } else if (â˜ƒx == DripstoneThickness.MIDDLE) {
         â˜ƒ = MIDDLE_SHAPE;
      } else {
         â˜ƒ = BASE_SHAPE;
      }

      Vec3 â˜ƒ = â˜ƒ.getOffset(â˜ƒ, â˜ƒ);
      return â˜ƒ.move(â˜ƒ.x, 0.0, â˜ƒ.z);
   }

   @Override
   public boolean isCollisionShapeFullBlock(BlockState var1, BlockGetter var2, BlockPos var3) {
      return false;
   }

   @Override
   public BlockBehaviour.OffsetType getOffsetType() {
      return BlockBehaviour.OffsetType.XZ;
   }

   @Override
   public float getMaxHorizontalOffset() {
      return 0.125F;
   }

   @Override
   public void onBrokenAfterFall(Level var1, BlockPos var2, FallingBlockEntity var3) {
      if (!â˜ƒ.isSilent()) {
         â˜ƒ.levelEvent(1045, â˜ƒ, 0);
      }
   }

   @Override
   public DamageSource getFallDamageSource() {
      return DamageSource.FALLING_STALACTITE;
   }

   @Override
   public Predicate<Entity> getHurtsEntitySelector() {
      return EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
   }

   private void scheduleStalactiteFallTicks(BlockState var1, LevelAccessor var2, BlockPos var3) {
      BlockPos â˜ƒ = findTip(â˜ƒ, â˜ƒ, â˜ƒ, Integer.MAX_VALUE, true);
      if (â˜ƒ != null) {
         BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();

         while(isStalactite(â˜ƒ.getBlockState(â˜ƒx))) {
            â˜ƒ.getBlockTicks().scheduleTick(â˜ƒx, this, 2);
            â˜ƒx.move(Direction.UP);
         }
      }
   }

   private static int getStalactiteSizeFromTip(ServerLevel var0, BlockPos var1, int var2) {
      int â˜ƒ = 1;
      BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable().move(Direction.UP);

      while(â˜ƒ < â˜ƒ && isStalactite(â˜ƒ.getBlockState(â˜ƒx))) {
         ++â˜ƒ;
         â˜ƒx.move(Direction.UP);
      }

      return â˜ƒ;
   }

   private static void spawnFallingStalactite(BlockState var0, ServerLevel var1, BlockPos var2) {
      Vec3 â˜ƒ = Vec3.atBottomCenterOf(â˜ƒ);
      FallingBlockEntity â˜ƒx = new FallingBlockEntity(â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ);
      if (isTip(â˜ƒ, true)) {
         int â˜ƒxx = getStalactiteSizeFromTip(â˜ƒ, â˜ƒ, 6);
         float â˜ƒxxx = 1.0F * (float)â˜ƒxx;
         â˜ƒx.setHurtsEntities(â˜ƒxxx, 40);
      }

      â˜ƒ.addFreshEntity(â˜ƒx);
   }

   @VisibleForTesting
   public static void growStalactiteOrStalagmiteIfPossible(BlockState var0, ServerLevel var1, BlockPos var2, Random var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.above(1));
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.above(2));
      if (canGrow(â˜ƒ, â˜ƒx)) {
         BlockPos â˜ƒxx = findTip(â˜ƒ, â˜ƒ, â˜ƒ, 7, false);
         if (â˜ƒxx != null) {
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
            if (canDrip(â˜ƒxxx) && canTipGrow(â˜ƒxxx, â˜ƒ, â˜ƒxx)) {
               if (â˜ƒ.nextBoolean()) {
                  grow(â˜ƒ, â˜ƒxx, Direction.DOWN);
               } else {
                  growStalagmiteBelow(â˜ƒ, â˜ƒxx);
               }
            }
         }
      }
   }

   private static void growStalagmiteBelow(ServerLevel var0, BlockPos var1) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();

      for(int â˜ƒx = 0; â˜ƒx < 10; ++â˜ƒx) {
         â˜ƒ.move(Direction.DOWN);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
         if (!â˜ƒxx.getFluidState().isEmpty()) {
            return;
         }

         if (isUnmergedTipWithDirection(â˜ƒxx, Direction.UP) && canTipGrow(â˜ƒxx, â˜ƒ, â˜ƒ)) {
            grow(â˜ƒ, â˜ƒ, Direction.UP);
            return;
         }

         if (isValidPointedDripstonePlacement(â˜ƒ, â˜ƒ, Direction.UP) && !â˜ƒ.isWaterAt(â˜ƒ.below())) {
            grow(â˜ƒ, â˜ƒ.below(), Direction.UP);
            return;
         }
      }
   }

   private static void grow(ServerLevel var0, BlockPos var1, Direction var2) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (isUnmergedTipWithDirection(â˜ƒx, â˜ƒ.getOpposite())) {
         createMergedTips(â˜ƒx, â˜ƒ, â˜ƒ);
      } else if (â˜ƒx.isAir() || â˜ƒx.is(Blocks.WATER)) {
         createDripstone(â˜ƒ, â˜ƒ, â˜ƒ, DripstoneThickness.TIP);
      }
   }

   private static void createDripstone(LevelAccessor var0, BlockPos var1, Direction var2, DripstoneThickness var3) {
      BlockState â˜ƒ = Blocks.POINTED_DRIPSTONE
         .defaultBlockState()
         .setValue(TIP_DIRECTION, â˜ƒ)
         .setValue(THICKNESS, â˜ƒ)
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.getFluidState(â˜ƒ).getType() == Fluids.WATER));
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
   }

   private static void createMergedTips(BlockState var0, LevelAccessor var1, BlockPos var2) {
      BlockPos â˜ƒ;
      BlockPos â˜ƒx;
      if (â˜ƒ.getValue(TIP_DIRECTION) == Direction.UP) {
         â˜ƒx = â˜ƒ;
         â˜ƒ = â˜ƒ.above();
      } else {
         â˜ƒ = â˜ƒ;
         â˜ƒx = â˜ƒ.below();
      }

      createDripstone(â˜ƒ, â˜ƒ, Direction.DOWN, DripstoneThickness.TIP_MERGE);
      createDripstone(â˜ƒ, â˜ƒx, Direction.UP, DripstoneThickness.TIP_MERGE);
   }

   public static void spawnDripParticle(Level var0, BlockPos var1, BlockState var2) {
      getFluidAboveStalactite(â˜ƒ, â˜ƒ, â˜ƒ).ifPresent(var3 -> spawnDripParticle(â˜ƒ, â˜ƒ, â˜ƒ, var3));
   }

   private static void spawnDripParticle(Level var0, BlockPos var1, BlockState var2, Fluid var3) {
      Vec3 â˜ƒ = â˜ƒ.getOffset(â˜ƒ, â˜ƒ);
      double â˜ƒx = 0.0625;
      double â˜ƒxx = (double)â˜ƒ.getX() + 0.5 + â˜ƒ.x;
      double â˜ƒxxx = (double)((float)(â˜ƒ.getY() + 1) - 0.6875F) - 0.0625;
      double â˜ƒxxxx = (double)â˜ƒ.getZ() + 0.5 + â˜ƒ.z;
      Fluid â˜ƒxxxxx = getDripFluid(â˜ƒ, â˜ƒ);
      ParticleOptions â˜ƒxxxxxx = â˜ƒxxxxx.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
      â˜ƒ.addParticle(â˜ƒxxxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, 0.0, 0.0, 0.0);
   }

   @Nullable
   private static BlockPos findTip(BlockState var0, LevelAccessor var1, BlockPos var2, int var3, boolean var4) {
      if (isTip(â˜ƒ, â˜ƒ)) {
         return â˜ƒ;
      } else {
         Direction â˜ƒ = â˜ƒ.getValue(TIP_DIRECTION);
         Predicate<BlockState> â˜ƒx = var1x -> var1x.is(Blocks.POINTED_DRIPSTONE) && var1x.getValue(TIP_DIRECTION) == â˜ƒ;
         return (BlockPos)findBlockVertical(â˜ƒ, â˜ƒ, â˜ƒ.getAxisDirection(), â˜ƒx, var1x -> isTip(var1x, â˜ƒ), â˜ƒ).orElse(null);
      }
   }

   @Nullable
   private static Direction calculateTipDirection(LevelReader var0, BlockPos var1, Direction var2) {
      Direction â˜ƒ;
      if (isValidPointedDripstonePlacement(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ = â˜ƒ;
      } else {
         if (!isValidPointedDripstonePlacement(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite())) {
            return null;
         }

         â˜ƒ = â˜ƒ.getOpposite();
      }

      return â˜ƒ;
   }

   private static DripstoneThickness calculateDripstoneThickness(LevelReader var0, BlockPos var1, Direction var2, boolean var3) {
      Direction â˜ƒ = â˜ƒ.getOpposite();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ));
      if (isPointedDripstoneWithDirection(â˜ƒx, â˜ƒ)) {
         return !â˜ƒ && â˜ƒx.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
      } else if (!isPointedDripstoneWithDirection(â˜ƒx, â˜ƒ)) {
         return DripstoneThickness.TIP;
      } else {
         DripstoneThickness â˜ƒ = â˜ƒx.getValue(THICKNESS);
         if (â˜ƒ != DripstoneThickness.TIP && â˜ƒ != DripstoneThickness.TIP_MERGE) {
            BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ));
            return !isPointedDripstoneWithDirection(â˜ƒx, â˜ƒ) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
         } else {
            return DripstoneThickness.FRUSTUM;
         }
      }
   }

   public static boolean canDrip(BlockState var0) {
      return isStalactite(â˜ƒ) && â˜ƒ.getValue(THICKNESS) == DripstoneThickness.TIP && !â˜ƒ.getValue(WATERLOGGED);
   }

   private static boolean canTipGrow(BlockState var0, ServerLevel var1, BlockPos var2) {
      Direction â˜ƒ = â˜ƒ.getValue(TIP_DIRECTION);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (!â˜ƒxx.getFluidState().isEmpty()) {
         return false;
      } else {
         return â˜ƒxx.isAir() ? true : isUnmergedTipWithDirection(â˜ƒxx, â˜ƒ.getOpposite());
      }
   }

   private static Optional<BlockPos> findRootBlock(Level var0, BlockPos var1, BlockState var2, int var3) {
      Direction â˜ƒ = â˜ƒ.getValue(TIP_DIRECTION);
      Predicate<BlockState> â˜ƒx = var1x -> var1x.is(Blocks.POINTED_DRIPSTONE) && var1x.getValue(TIP_DIRECTION) == â˜ƒ;
      return findBlockVertical(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite().getAxisDirection(), â˜ƒx, var0x -> !var0x.is(Blocks.POINTED_DRIPSTONE), â˜ƒ);
   }

   private static boolean isValidPointedDripstonePlacement(LevelReader var0, BlockPos var1, Direction var2) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.getOpposite());
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ) || isPointedDripstoneWithDirection(â˜ƒx, â˜ƒ);
   }

   private static boolean isTip(BlockState var0, boolean var1) {
      if (!â˜ƒ.is(Blocks.POINTED_DRIPSTONE)) {
         return false;
      } else {
         DripstoneThickness â˜ƒ = â˜ƒ.getValue(THICKNESS);
         return â˜ƒ == DripstoneThickness.TIP || â˜ƒ && â˜ƒ == DripstoneThickness.TIP_MERGE;
      }
   }

   private static boolean isUnmergedTipWithDirection(BlockState var0, Direction var1) {
      return isTip(â˜ƒ, false) && â˜ƒ.getValue(TIP_DIRECTION) == â˜ƒ;
   }

   private static boolean isStalactite(BlockState var0) {
      return isPointedDripstoneWithDirection(â˜ƒ, Direction.DOWN);
   }

   private static boolean isStalagmite(BlockState var0) {
      return isPointedDripstoneWithDirection(â˜ƒ, Direction.UP);
   }

   private static boolean isStalactiteStartPos(BlockState var0, LevelReader var1, BlockPos var2) {
      return isStalactite(â˜ƒ) && !â˜ƒ.getBlockState(â˜ƒ.above()).is(Blocks.POINTED_DRIPSTONE);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   private static boolean isPointedDripstoneWithDirection(BlockState var0, Direction var1) {
      return â˜ƒ.is(Blocks.POINTED_DRIPSTONE) && â˜ƒ.getValue(TIP_DIRECTION) == â˜ƒ;
   }

   @Nullable
   private static BlockPos findFillableCauldronBelowStalactiteTip(Level var0, BlockPos var1, Fluid var2) {
      Predicate<BlockState> â˜ƒ = var1x -> var1x.getBlock() instanceof AbstractCauldronBlock
            && ((AbstractCauldronBlock)var1x.getBlock()).canReceiveStalactiteDrip(â˜ƒ);
      return (BlockPos)findBlockVertical(â˜ƒ, â˜ƒ, Direction.DOWN.getAxisDirection(), BlockBehaviour.BlockStateBase::isAir, â˜ƒ, 11).orElse(null);
   }

   @Nullable
   public static BlockPos findStalactiteTipAboveCauldron(Level var0, BlockPos var1) {
      return (BlockPos)findBlockVertical(â˜ƒ, â˜ƒ, Direction.UP.getAxisDirection(), BlockBehaviour.BlockStateBase::isAir, PointedDripstoneBlock::canDrip, 11)
         .orElse(null);
   }

   public static Fluid getCauldronFillFluidType(Level var0, BlockPos var1) {
      return (Fluid)getFluidAboveStalactite(â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ)).filter(PointedDripstoneBlock::canFillCauldron).orElse(Fluids.EMPTY);
   }

   private static Optional<Fluid> getFluidAboveStalactite(Level var0, BlockPos var1, BlockState var2) {
      return !isStalactite(â˜ƒ) ? Optional.empty() : findRootBlock(â˜ƒ, â˜ƒ, â˜ƒ, 11).map(var1x -> â˜ƒ.getFluidState(var1x.above()).getType());
   }

   private static boolean canFillCauldron(Fluid var0) {
      return â˜ƒ == Fluids.LAVA || â˜ƒ == Fluids.WATER;
   }

   private static boolean canGrow(BlockState var0, BlockState var1) {
      return â˜ƒ.is(Blocks.DRIPSTONE_BLOCK) && â˜ƒ.is(Blocks.WATER) && â˜ƒ.getFluidState().isSource();
   }

   private static Fluid getDripFluid(Level var0, Fluid var1) {
      if (â˜ƒ.isSame(Fluids.EMPTY)) {
         return â˜ƒ.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
      } else {
         return â˜ƒ;
      }
   }

   private static Optional<BlockPos> findBlockVertical(
      LevelAccessor var0, BlockPos var1, Direction.AxisDirection var2, Predicate<BlockState> var3, Predicate<BlockState> var4, int var5
   ) {
      Direction â˜ƒ = Direction.get(â˜ƒ, Direction.Axis.Y);
      BlockPos.MutableBlockPos â˜ƒx = â˜ƒ.mutable();

      for(int â˜ƒxx = 1; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         â˜ƒx.move(â˜ƒ);
         BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒx);
         if (â˜ƒ.test(â˜ƒxxx)) {
            return Optional.of(â˜ƒx.immutable());
         }

         if (â˜ƒ.isOutsideBuildHeight(â˜ƒx.getY()) || !â˜ƒ.test(â˜ƒxxx)) {
            return Optional.empty();
         }
      }

      return Optional.empty();
   }
}
