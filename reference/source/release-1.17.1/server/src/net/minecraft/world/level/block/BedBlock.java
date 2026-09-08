package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;

public class BedBlock extends HorizontalDirectionalBlock implements EntityBlock {
   public static final EnumProperty<BedPart> PART = BlockStateProperties.BED_PART;
   public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;
   protected static final int HEIGHT = 9;
   protected static final VoxelShape BASE = Block.box(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
   private static final int LEG_WIDTH = 3;
   protected static final VoxelShape LEG_NORTH_WEST = Block.box(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
   protected static final VoxelShape LEG_SOUTH_WEST = Block.box(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
   protected static final VoxelShape LEG_NORTH_EAST = Block.box(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
   protected static final VoxelShape LEG_SOUTH_EAST = Block.box(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, LEG_NORTH_WEST, LEG_NORTH_EAST);
   protected static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, LEG_SOUTH_WEST, LEG_SOUTH_EAST);
   protected static final VoxelShape WEST_SHAPE = Shapes.or(BASE, LEG_NORTH_WEST, LEG_SOUTH_WEST);
   protected static final VoxelShape EAST_SHAPE = Shapes.or(BASE, LEG_NORTH_EAST, LEG_SOUTH_EAST);
   private final DyeColor color;

   public BedBlock(DyeColor var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.color = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(PART, BedPart.FOOT).setValue(OCCUPIED, Boolean.valueOf(false)));
   }

   @Nullable
   public static Direction getBedOrientation(BlockGetter var0, BlockPos var1) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.getBlock() instanceof BedBlock ? â˜ƒ.getValue(FACING) : null;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         return InteractionResult.CONSUME;
      } else {
         if (â˜ƒ.getValue(PART) != BedPart.HEAD) {
            â˜ƒ = â˜ƒ.relative(â˜ƒ.getValue(FACING));
            â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
            if (!â˜ƒ.is(this)) {
               return InteractionResult.CONSUME;
            }
         }

         if (!canSetSpawn(â˜ƒ)) {
            â˜ƒ.removeBlock(â˜ƒ, false);
            BlockPos â˜ƒ = â˜ƒ.relative(((Direction)â˜ƒ.getValue(FACING)).getOpposite());
            if (â˜ƒ.getBlockState(â˜ƒ).is(this)) {
               â˜ƒ.removeBlock(â˜ƒ, false);
            }

            â˜ƒ.explode(
               null,
               DamageSource.badRespawnPointExplosion(),
               null,
               (double)â˜ƒ.getX() + 0.5,
               (double)â˜ƒ.getY() + 0.5,
               (double)â˜ƒ.getZ() + 0.5,
               5.0F,
               true,
               Explosion.BlockInteraction.DESTROY
            );
            return InteractionResult.SUCCESS;
         } else if (â˜ƒ.getValue(OCCUPIED)) {
            if (!this.kickVillagerOutOfBed(â˜ƒ, â˜ƒ)) {
               â˜ƒ.displayClientMessage(new TranslatableComponent("block.minecraft.bed.occupied"), true);
            }

            return InteractionResult.SUCCESS;
         } else {
            â˜ƒ.startSleepInBed(â˜ƒ).ifLeft(var1x -> {
               if (var1x != null) {
                  â˜ƒ.displayClientMessage(var1x.getMessage(), true);
               }
            });
            return InteractionResult.SUCCESS;
         }
      }
   }

   public static boolean canSetSpawn(Level var0) {
      return â˜ƒ.dimensionType().bedWorks();
   }

   private boolean kickVillagerOutOfBed(Level var1, BlockPos var2) {
      List<Villager> â˜ƒ = â˜ƒ.getEntitiesOfClass(Villager.class, new AABB(â˜ƒ), LivingEntity::isSleeping);
      if (â˜ƒ.isEmpty()) {
         return false;
      } else {
         ((Villager)â˜ƒ.get(0)).stopSleeping();
         return true;
      }
   }

   @Override
   public void fallOn(Level var1, BlockState var2, BlockPos var3, Entity var4, float var5) {
      super.fallOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ * 0.5F);
   }

   @Override
   public void updateEntityAfterFallOn(BlockGetter var1, Entity var2) {
      if (â˜ƒ.isSuppressingBounce()) {
         super.updateEntityAfterFallOn(â˜ƒ, â˜ƒ);
      } else {
         this.bounceUp(â˜ƒ);
      }
   }

   private void bounceUp(Entity var1) {
      Vec3 â˜ƒ = â˜ƒ.getDeltaMovement();
      if (â˜ƒ.y < 0.0) {
         double â˜ƒx = â˜ƒ instanceof LivingEntity ? 1.0 : 0.8;
         â˜ƒ.setDeltaMovement(â˜ƒ.x, -â˜ƒ.y * 0.66F * â˜ƒx, â˜ƒ.z);
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == getNeighbourDirection(â˜ƒ.getValue(PART), â˜ƒ.getValue(FACING))) {
         return â˜ƒ.is(this) && â˜ƒ.getValue(PART) != â˜ƒ.getValue(PART)
            ? â˜ƒ.setValue(OCCUPIED, (Boolean)â˜ƒ.getValue(OCCUPIED))
            : Blocks.AIR.defaultBlockState();
      } else {
         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static Direction getNeighbourDirection(BedPart var0, Direction var1) {
      return â˜ƒ == BedPart.FOOT ? â˜ƒ : â˜ƒ.getOpposite();
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ.isCreative()) {
         BedPart â˜ƒ = â˜ƒ.getValue(PART);
         if (â˜ƒ == BedPart.FOOT) {
            BlockPos â˜ƒx = â˜ƒ.relative(getNeighbourDirection(â˜ƒ, â˜ƒ.getValue(FACING)));
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
            if (â˜ƒxx.is(this) && â˜ƒxx.getValue(PART) == BedPart.HEAD) {
               â˜ƒ.setBlock(â˜ƒx, Blocks.AIR.defaultBlockState(), 35);
               â˜ƒ.levelEvent(â˜ƒ, 2001, â˜ƒx, Block.getId(â˜ƒxx));
            }
         }
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Direction â˜ƒ = â˜ƒ.getHorizontalDirection();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockPos â˜ƒxx = â˜ƒx.relative(â˜ƒ);
      return â˜ƒ.getLevel().getBlockState(â˜ƒxx).canBeReplaced(â˜ƒ) ? this.defaultBlockState().setValue(FACING, â˜ƒ) : null;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      Direction â˜ƒ = getConnectedDirection(â˜ƒ).getOpposite();
      switch(â˜ƒ) {
         case NORTH:
            return NORTH_SHAPE;
         case SOUTH:
            return SOUTH_SHAPE;
         case WEST:
            return WEST_SHAPE;
         default:
            return EAST_SHAPE;
      }
   }

   public static Direction getConnectedDirection(BlockState var0) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      return â˜ƒ.getValue(PART) == BedPart.HEAD ? â˜ƒ.getOpposite() : â˜ƒ;
   }

   public static DoubleBlockCombiner.BlockType getBlockType(BlockState var0) {
      BedPart â˜ƒ = â˜ƒ.getValue(PART);
      return â˜ƒ == BedPart.HEAD ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
   }

   private static boolean isBunkBed(BlockGetter var0, BlockPos var1) {
      return â˜ƒ.getBlockState(â˜ƒ.below()).getBlock() instanceof BedBlock;
   }

   public static Optional<Vec3> findStandUpPosition(EntityType<?> var0, CollisionGetter var1, BlockPos var2, float var3) {
      Direction â˜ƒ = â˜ƒ.getBlockState(â˜ƒ).getValue(FACING);
      Direction â˜ƒx = â˜ƒ.getClockWise();
      Direction â˜ƒxx = â˜ƒx.isFacingAngle(â˜ƒ) ? â˜ƒx.getOpposite() : â˜ƒx;
      if (isBunkBed(â˜ƒ, â˜ƒ)) {
         return findBunkBedStandUpPosition(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx);
      } else {
         int[][] â˜ƒ = bedStandUpOffsets(â˜ƒ, â˜ƒxx);
         Optional<Vec3> â˜ƒx = findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
         return â˜ƒx.isPresent() ? â˜ƒx : findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
      }
   }

   private static Optional<Vec3> findBunkBedStandUpPosition(EntityType<?> var0, CollisionGetter var1, BlockPos var2, Direction var3, Direction var4) {
      int[][] â˜ƒ = bedSurroundStandUpOffsets(â˜ƒ, â˜ƒ);
      Optional<Vec3> â˜ƒx = findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
      if (â˜ƒx.isPresent()) {
         return â˜ƒx;
      } else {
         BlockPos â˜ƒ = â˜ƒ.below();
         Optional<Vec3> â˜ƒx = findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
         if (â˜ƒx.isPresent()) {
            return â˜ƒx;
         } else {
            int[][] â˜ƒ = bedAboveStandUpOffsets(â˜ƒ);
            Optional<Vec3> â˜ƒx = findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
            if (â˜ƒx.isPresent()) {
               return â˜ƒx;
            } else {
               Optional<Vec3> â˜ƒ = findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
               if (â˜ƒ.isPresent()) {
                  return â˜ƒ;
               } else {
                  Optional<Vec3> â˜ƒ = findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
                  return â˜ƒ.isPresent() ? â˜ƒ : findStandUpPositionAtOffset(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
               }
            }
         }
      }
   }

   private static Optional<Vec3> findStandUpPositionAtOffset(EntityType<?> var0, CollisionGetter var1, BlockPos var2, int[][] var3, boolean var4) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(int[] â˜ƒx : â˜ƒ) {
         â˜ƒ.set(â˜ƒ.getX() + â˜ƒx[0], â˜ƒ.getY(), â˜ƒ.getZ() + â˜ƒx[1]);
         Vec3 â˜ƒxx = DismountHelper.findSafeDismountLocation(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒxx != null) {
            return Optional.of(â˜ƒxx);
         }
      }

      return Optional.empty();
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, PART, OCCUPIED);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new BedBlockEntity(â˜ƒ, â˜ƒ, this.color);
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
      super.setPlacedBy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (!â˜ƒ.isClientSide) {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.getValue(FACING));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(PART, BedPart.HEAD), 3);
         â˜ƒ.blockUpdated(â˜ƒ, Blocks.AIR);
         â˜ƒ.updateNeighbourShapes(â˜ƒ, â˜ƒ, 3);
      }
   }

   public DyeColor getColor() {
      return this.color;
   }

   @Override
   public long getSeed(BlockState var1, BlockPos var2) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ.getValue(FACING), â˜ƒ.getValue(PART) == BedPart.HEAD ? 0 : 1);
      return Mth.getSeed(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   private static int[][] bedStandUpOffsets(Direction var0, Direction var1) {
      return ArrayUtils.addAll((int[][])bedSurroundStandUpOffsets(â˜ƒ, â˜ƒ), (int[][])bedAboveStandUpOffsets(â˜ƒ));
   }

   private static int[][] bedSurroundStandUpOffsets(Direction var0, Direction var1) {
      return new int[][]{
         {â˜ƒ.getStepX(), â˜ƒ.getStepZ()},
         {â˜ƒ.getStepX() - â˜ƒ.getStepX(), â˜ƒ.getStepZ() - â˜ƒ.getStepZ()},
         {â˜ƒ.getStepX() - â˜ƒ.getStepX() * 2, â˜ƒ.getStepZ() - â˜ƒ.getStepZ() * 2},
         {-â˜ƒ.getStepX() * 2, -â˜ƒ.getStepZ() * 2},
         {-â˜ƒ.getStepX() - â˜ƒ.getStepX() * 2, -â˜ƒ.getStepZ() - â˜ƒ.getStepZ() * 2},
         {-â˜ƒ.getStepX() - â˜ƒ.getStepX(), -â˜ƒ.getStepZ() - â˜ƒ.getStepZ()},
         {-â˜ƒ.getStepX(), -â˜ƒ.getStepZ()},
         {-â˜ƒ.getStepX() + â˜ƒ.getStepX(), -â˜ƒ.getStepZ() + â˜ƒ.getStepZ()},
         {â˜ƒ.getStepX(), â˜ƒ.getStepZ()},
         {â˜ƒ.getStepX() + â˜ƒ.getStepX(), â˜ƒ.getStepZ() + â˜ƒ.getStepZ()}
      };
   }

   private static int[][] bedAboveStandUpOffsets(Direction var0) {
      return new int[][]{{0, 0}, {-â˜ƒ.getStepX(), -â˜ƒ.getStepZ()}};
   }
}
