package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BellBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BellBlock extends BaseEntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final EnumProperty<BellAttachType> ATTACHMENT = BlockStateProperties.BELL_ATTACHMENT;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private static final VoxelShape NORTH_SOUTH_FLOOR_SHAPE = Block.box(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
   private static final VoxelShape EAST_WEST_FLOOR_SHAPE = Block.box(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   private static final VoxelShape BELL_TOP_SHAPE = Block.box(5.0, 6.0, 5.0, 11.0, 13.0, 11.0);
   private static final VoxelShape BELL_BOTTOM_SHAPE = Block.box(4.0, 4.0, 4.0, 12.0, 6.0, 12.0);
   private static final VoxelShape BELL_SHAPE = Shapes.or(BELL_BOTTOM_SHAPE, BELL_TOP_SHAPE);
   private static final VoxelShape NORTH_SOUTH_BETWEEN = Shapes.or(BELL_SHAPE, Block.box(7.0, 13.0, 0.0, 9.0, 15.0, 16.0));
   private static final VoxelShape EAST_WEST_BETWEEN = Shapes.or(BELL_SHAPE, Block.box(0.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final VoxelShape TO_WEST = Shapes.or(BELL_SHAPE, Block.box(0.0, 13.0, 7.0, 13.0, 15.0, 9.0));
   private static final VoxelShape TO_EAST = Shapes.or(BELL_SHAPE, Block.box(3.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final VoxelShape TO_NORTH = Shapes.or(BELL_SHAPE, Block.box(7.0, 13.0, 0.0, 9.0, 15.0, 13.0));
   private static final VoxelShape TO_SOUTH = Shapes.or(BELL_SHAPE, Block.box(7.0, 13.0, 3.0, 9.0, 15.0, 16.0));
   private static final VoxelShape CEILING_SHAPE = Shapes.or(BELL_SHAPE, Block.box(7.0, 13.0, 7.0, 9.0, 16.0, 9.0));
   public static final int EVENT_BELL_RING = 1;

   public BellBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(ATTACHMENT, BellAttachType.FLOOR).setValue(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      boolean â˜ƒ = â˜ƒ.hasNeighborSignal(â˜ƒ);
      if (â˜ƒ != â˜ƒ.getValue(POWERED)) {
         if (â˜ƒ) {
            this.attemptToRing(â˜ƒ, â˜ƒ, null);
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ)), 3);
      }
   }

   @Override
   public void onProjectileHit(Level var1, BlockState var2, BlockHitResult var3, Projectile var4) {
      Entity â˜ƒ = â˜ƒ.getOwner();
      Player â˜ƒx = â˜ƒ instanceof Player ? (Player)â˜ƒ : null;
      this.onHit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, true);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      return this.onHit(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true) ? InteractionResult.sidedSuccess(â˜ƒ.isClientSide) : InteractionResult.PASS;
   }

   public boolean onHit(Level var1, BlockState var2, BlockHitResult var3, @Nullable Player var4, boolean var5) {
      Direction â˜ƒ = â˜ƒ.getDirection();
      BlockPos â˜ƒx = â˜ƒ.getBlockPos();
      boolean â˜ƒxx = !â˜ƒ || this.isProperHit(â˜ƒ, â˜ƒ, â˜ƒ.getLocation().y - (double)â˜ƒx.getY());
      if (â˜ƒxx) {
         boolean â˜ƒxxx = this.attemptToRing(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
         if (â˜ƒxxx && â˜ƒ != null) {
            â˜ƒ.awardStat(Stats.BELL_RING);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isProperHit(BlockState var1, Direction var2, double var3) {
      if (â˜ƒ.getAxis() != Direction.Axis.Y && !(â˜ƒ > 0.8124F)) {
         Direction â˜ƒ = â˜ƒ.getValue(FACING);
         BellAttachType â˜ƒx = â˜ƒ.getValue(ATTACHMENT);
         switch(â˜ƒx) {
            case FLOOR:
               return â˜ƒ.getAxis() == â˜ƒ.getAxis();
            case SINGLE_WALL:
            case DOUBLE_WALL:
               return â˜ƒ.getAxis() != â˜ƒ.getAxis();
            case CEILING:
               return true;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   public boolean attemptToRing(Level var1, BlockPos var2, @Nullable Direction var3) {
      return this.attemptToRing(null, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public boolean attemptToRing(@Nullable Entity var1, Level var2, BlockPos var3, @Nullable Direction var4) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (!â˜ƒ.isClientSide && â˜ƒ instanceof BellBlockEntity) {
         if (â˜ƒ == null) {
            â˜ƒ = â˜ƒ.getBlockState(â˜ƒ).getValue(FACING);
         }

         ((BellBlockEntity)â˜ƒ).onHit(â˜ƒ);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0F, 1.0F);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.RING_BELL, â˜ƒ);
         return true;
      } else {
         return false;
      }
   }

   private VoxelShape getVoxelShape(BlockState var1) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BellAttachType â˜ƒx = â˜ƒ.getValue(ATTACHMENT);
      if (â˜ƒx == BellAttachType.FLOOR) {
         return â˜ƒ != Direction.NORTH && â˜ƒ != Direction.SOUTH ? EAST_WEST_FLOOR_SHAPE : NORTH_SOUTH_FLOOR_SHAPE;
      } else if (â˜ƒx == BellAttachType.CEILING) {
         return CEILING_SHAPE;
      } else if (â˜ƒx == BellAttachType.DOUBLE_WALL) {
         return â˜ƒ != Direction.NORTH && â˜ƒ != Direction.SOUTH ? EAST_WEST_BETWEEN : NORTH_SOUTH_BETWEEN;
      } else if (â˜ƒ == Direction.NORTH) {
         return TO_NORTH;
      } else if (â˜ƒ == Direction.SOUTH) {
         return TO_SOUTH;
      } else {
         return â˜ƒ == Direction.EAST ? TO_EAST : TO_WEST;
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.getVoxelShape(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.getVoxelShape(â˜ƒ);
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Direction â˜ƒ = â˜ƒ.getClickedFace();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      Level â˜ƒxx = â˜ƒ.getLevel();
      Direction.Axis â˜ƒxxx = â˜ƒ.getAxis();
      if (â˜ƒxxx == Direction.Axis.Y) {
         BlockState â˜ƒxxxx = this.defaultBlockState()
            .setValue(ATTACHMENT, â˜ƒ == Direction.DOWN ? BellAttachType.CEILING : BellAttachType.FLOOR)
            .setValue(FACING, â˜ƒ.getHorizontalDirection());
         if (â˜ƒxxxx.canSurvive(â˜ƒ.getLevel(), â˜ƒx)) {
            return â˜ƒxxxx;
         }
      } else {
         boolean â˜ƒ = â˜ƒxxx == Direction.Axis.X
               && â˜ƒxx.getBlockState(â˜ƒx.west()).isFaceSturdy(â˜ƒxx, â˜ƒx.west(), Direction.EAST)
               && â˜ƒxx.getBlockState(â˜ƒx.east()).isFaceSturdy(â˜ƒxx, â˜ƒx.east(), Direction.WEST)
            || â˜ƒxxx == Direction.Axis.Z
               && â˜ƒxx.getBlockState(â˜ƒx.north()).isFaceSturdy(â˜ƒxx, â˜ƒx.north(), Direction.SOUTH)
               && â˜ƒxx.getBlockState(â˜ƒx.south()).isFaceSturdy(â˜ƒxx, â˜ƒx.south(), Direction.NORTH);
         BlockState â˜ƒx = this.defaultBlockState()
            .setValue(FACING, â˜ƒ.getOpposite())
            .setValue(ATTACHMENT, â˜ƒ ? BellAttachType.DOUBLE_WALL : BellAttachType.SINGLE_WALL);
         if (â˜ƒx.canSurvive(â˜ƒ.getLevel(), â˜ƒ.getClickedPos())) {
            return â˜ƒx;
         }

         boolean â˜ƒ = â˜ƒxx.getBlockState(â˜ƒx.below()).isFaceSturdy(â˜ƒxx, â˜ƒx.below(), Direction.UP);
         â˜ƒx = â˜ƒx.setValue(ATTACHMENT, â˜ƒ ? BellAttachType.FLOOR : BellAttachType.CEILING);
         if (â˜ƒx.canSurvive(â˜ƒ.getLevel(), â˜ƒ.getClickedPos())) {
            return â˜ƒx;
         }
      }

      return null;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      BellAttachType â˜ƒ = â˜ƒ.getValue(ATTACHMENT);
      Direction â˜ƒx = getConnectedDirection(â˜ƒ).getOpposite();
      if (â˜ƒx == â˜ƒ && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) && â˜ƒ != BellAttachType.DOUBLE_WALL) {
         return Blocks.AIR.defaultBlockState();
      } else {
         if (â˜ƒ.getAxis() == ((Direction)â˜ƒ.getValue(FACING)).getAxis()) {
            if (â˜ƒ == BellAttachType.DOUBLE_WALL && !â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ)) {
               return â˜ƒ.setValue(ATTACHMENT, BellAttachType.SINGLE_WALL).setValue(FACING, â˜ƒ.getOpposite());
            }

            if (â˜ƒ == BellAttachType.SINGLE_WALL && â˜ƒx.getOpposite() == â˜ƒ && â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ.getValue(FACING))) {
               return â˜ƒ.setValue(ATTACHMENT, BellAttachType.DOUBLE_WALL);
            }
         }

         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      Direction â˜ƒ = getConnectedDirection(â˜ƒ).getOpposite();
      return â˜ƒ == Direction.UP ? Block.canSupportCenter(â˜ƒ, â˜ƒ.above(), Direction.DOWN) : FaceAttachedHorizontalDirectionalBlock.canAttach(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static Direction getConnectedDirection(BlockState var0) {
      switch((BellAttachType)â˜ƒ.getValue(ATTACHMENT)) {
         case FLOOR:
            return Direction.UP;
         case CEILING:
            return Direction.DOWN;
         default:
            return ((Direction)â˜ƒ.getValue(FACING)).getOpposite();
      }
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, ATTACHMENT, POWERED);
   }

   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new BellBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return createTickerHelper(â˜ƒ, BlockEntityType.BELL, â˜ƒ.isClientSide ? BellBlockEntity::clientTick : BellBlockEntity::serverTick);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
