package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoorBlock extends Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   protected static final float AABB_DOOR_THICKNESS = 3.0F;
   protected static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape WEST_AABB = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

   protected DoorBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(OPEN, Boolean.valueOf(false))
            .setValue(HINGE, DoorHingeSide.LEFT)
            .setValue(POWERED, Boolean.valueOf(false))
            .setValue(HALF, DoubleBlockHalf.LOWER)
      );
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      boolean â˜ƒx = !â˜ƒ.getValue(OPEN);
      boolean â˜ƒxx = â˜ƒ.getValue(HINGE) == DoorHingeSide.RIGHT;
      switch(â˜ƒ) {
         case EAST:
         default:
            return â˜ƒx ? EAST_AABB : (â˜ƒxx ? NORTH_AABB : SOUTH_AABB);
         case SOUTH:
            return â˜ƒx ? SOUTH_AABB : (â˜ƒxx ? EAST_AABB : WEST_AABB);
         case WEST:
            return â˜ƒx ? WEST_AABB : (â˜ƒxx ? SOUTH_AABB : NORTH_AABB);
         case NORTH:
            return â˜ƒx ? NORTH_AABB : (â˜ƒxx ? WEST_AABB : EAST_AABB);
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      DoubleBlockHalf â˜ƒ = â˜ƒ.getValue(HALF);
      if (â˜ƒ.getAxis() != Direction.Axis.Y || â˜ƒ == DoubleBlockHalf.LOWER != (â˜ƒ == Direction.UP)) {
         return â˜ƒ == DoubleBlockHalf.LOWER && â˜ƒ == Direction.DOWN && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return â˜ƒ.is(this) && â˜ƒ.getValue(HALF) != â˜ƒ
            ? â˜ƒ.setValue(FACING, (Direction)â˜ƒ.getValue(FACING))
               .setValue(OPEN, (Boolean)â˜ƒ.getValue(OPEN))
               .setValue(HINGE, (DoorHingeSide)â˜ƒ.getValue(HINGE))
               .setValue(POWERED, (Boolean)â˜ƒ.getValue(POWERED))
            : Blocks.AIR.defaultBlockState();
      }
   }

   @Override
   public void playerWillDestroy(Level var1, BlockPos var2, BlockState var3, Player var4) {
      if (!â˜ƒ.isClientSide && â˜ƒ.isCreative()) {
         DoublePlantBlock.preventCreativeDropFromBottomPart(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.playerWillDestroy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      switch(â˜ƒ) {
         case LAND:
            return â˜ƒ.getValue(OPEN);
         case WATER:
            return false;
         case AIR:
            return â˜ƒ.getValue(OPEN);
         default:
            return false;
      }
   }

   private int getCloseSound() {
      return this.material == Material.METAL ? 1011 : 1012;
   }

   private int getOpenSound() {
      return this.material == Material.METAL ? 1005 : 1006;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      Level â˜ƒx = â˜ƒ.getLevel();
      if (â˜ƒ.getY() < â˜ƒx.getMaxBuildHeight() - 1 && â˜ƒx.getBlockState(â˜ƒ.above()).canBeReplaced(â˜ƒ)) {
         boolean â˜ƒxx = â˜ƒx.hasNeighborSignal(â˜ƒ) || â˜ƒx.hasNeighborSignal(â˜ƒ.above());
         return this.defaultBlockState()
            .setValue(FACING, â˜ƒ.getHorizontalDirection())
            .setValue(HINGE, this.getHinge(â˜ƒ))
            .setValue(POWERED, Boolean.valueOf(â˜ƒxx))
            .setValue(OPEN, Boolean.valueOf(â˜ƒxx))
            .setValue(HALF, DoubleBlockHalf.LOWER);
      } else {
         return null;
      }
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      â˜ƒ.setBlock(â˜ƒ.above(), â˜ƒ.setValue(HALF, DoubleBlockHalf.UPPER), 3);
   }

   private DoorHingeSide getHinge(BlockPlaceContext var1) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      Direction â˜ƒxx = â˜ƒ.getHorizontalDirection();
      BlockPos â˜ƒxxx = â˜ƒx.above();
      Direction â˜ƒxxxx = â˜ƒxx.getCounterClockWise();
      BlockPos â˜ƒxxxxx = â˜ƒx.relative(â˜ƒxxxx);
      BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
      BlockPos â˜ƒxxxxxxx = â˜ƒxxx.relative(â˜ƒxxxx);
      BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxx);
      Direction â˜ƒxxxxxxxxx = â˜ƒxx.getClockWise();
      BlockPos â˜ƒxxxxxxxxxx = â˜ƒx.relative(â˜ƒxxxxxxxxx);
      BlockState â˜ƒxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxx);
      BlockPos â˜ƒxxxxxxxxxxxx = â˜ƒxxx.relative(â˜ƒxxxxxxxxx);
      BlockState â˜ƒxxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxxxxxxx);
      int â˜ƒxxxxxxxxxxxxxx = (â˜ƒxxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxx) ? -1 : 0)
         + (â˜ƒxxxxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxxx) ? -1 : 0)
         + (â˜ƒxxxxxxxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxxxxxx) ? 1 : 0)
         + (â˜ƒxxxxxxxxxxxxx.isCollisionShapeFullBlock(â˜ƒ, â˜ƒxxxxxxxxxxxx) ? 1 : 0);
      boolean â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxx.is(this) && â˜ƒxxxxxx.getValue(HALF) == DoubleBlockHalf.LOWER;
      boolean â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx.is(this) && â˜ƒxxxxxxxxxxx.getValue(HALF) == DoubleBlockHalf.LOWER;
      if ((!â˜ƒxxxxxxxxxxxxxxx || â˜ƒxxxxxxxxxxxxxxxx) && â˜ƒxxxxxxxxxxxxxx <= 0) {
         if ((!â˜ƒxxxxxxxxxxxxxxxx || â˜ƒxxxxxxxxxxxxxxx) && â˜ƒxxxxxxxxxxxxxx >= 0) {
            int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxx.getStepX();
            int â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxx.getStepZ();
            Vec3 â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒ.getClickLocation();
            double â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.x - (double)â˜ƒx.getX();
            double â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.z - (double)â˜ƒx.getZ();
            return (â˜ƒxxxxxxxxxxxxxxxxx >= 0 || !(â˜ƒxxxxxxxxxxxxxxxxxxxxx < 0.5))
                  && (â˜ƒxxxxxxxxxxxxxxxxx <= 0 || !(â˜ƒxxxxxxxxxxxxxxxxxxxxx > 0.5))
                  && (â˜ƒxxxxxxxxxxxxxxxxxx >= 0 || !(â˜ƒxxxxxxxxxxxxxxxxxxxx > 0.5))
                  && (â˜ƒxxxxxxxxxxxxxxxxxx <= 0 || !(â˜ƒxxxxxxxxxxxxxxxxxxxx < 0.5))
               ? DoorHingeSide.LEFT
               : DoorHingeSide.RIGHT;
         } else {
            return DoorHingeSide.LEFT;
         }
      } else {
         return DoorHingeSide.RIGHT;
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (this.material == Material.METAL) {
         return InteractionResult.PASS;
      } else {
         â˜ƒ = â˜ƒ.cycle(OPEN);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 10);
         â˜ƒ.levelEvent(â˜ƒ, â˜ƒ.getValue(OPEN) ? this.getOpenSound() : this.getCloseSound(), â˜ƒ, 0);
         â˜ƒ.gameEvent(â˜ƒ, this.isOpen(â˜ƒ) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   public boolean isOpen(BlockState var1) {
      return â˜ƒ.getValue(OPEN);
   }

   public void setOpen(@Nullable Entity var1, Level var2, BlockState var3, BlockPos var4, boolean var5) {
      if (â˜ƒ.is(this) && â˜ƒ.getValue(OPEN) != â˜ƒ) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(OPEN, Boolean.valueOf(â˜ƒ)), 10);
         this.playSound(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.gameEvent(â˜ƒ, â˜ƒ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, â˜ƒ);
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      boolean â˜ƒ = â˜ƒ.hasNeighborSignal(â˜ƒ)
         || â˜ƒ.hasNeighborSignal(â˜ƒ.relative(â˜ƒ.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
      if (!this.defaultBlockState().is(â˜ƒ) && â˜ƒ != â˜ƒ.getValue(POWERED)) {
         if (â˜ƒ != â˜ƒ.getValue(OPEN)) {
            this.playSound(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.gameEvent(â˜ƒ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, â˜ƒ);
         }

         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ)).setValue(OPEN, Boolean.valueOf(â˜ƒ)), 2);
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return â˜ƒ.getValue(HALF) == DoubleBlockHalf.LOWER ? â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP) : â˜ƒx.is(this);
   }

   private void playSound(Level var1, BlockPos var2, boolean var3) {
      â˜ƒ.levelEvent(null, â˜ƒ ? this.getOpenSound() : this.getCloseSound(), â˜ƒ, 0);
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ == Mirror.NONE ? â˜ƒ : â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING))).cycle(HINGE);
   }

   @Override
   public long getSeed(BlockState var1, BlockPos var2) {
      return Mth.getSeed(â˜ƒ.getX(), â˜ƒ.below(â˜ƒ.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), â˜ƒ.getZ());
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(HALF, FACING, OPEN, HINGE, POWERED);
   }

   public static boolean isWoodenDoor(Level var0, BlockPos var1) {
      return isWoodenDoor(â˜ƒ.getBlockState(â˜ƒ));
   }

   public static boolean isWoodenDoor(BlockState var0) {
      return â˜ƒ.getBlock() instanceof DoorBlock && (â˜ƒ.getMaterial() == Material.WOOD || â˜ƒ.getMaterial() == Material.NETHER_WOOD);
   }
}
