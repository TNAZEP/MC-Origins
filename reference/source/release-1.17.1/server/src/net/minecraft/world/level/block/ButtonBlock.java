package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class ButtonBlock extends FaceAttachedHorizontalDirectionalBlock {
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   private static final int PRESSED_DEPTH = 1;
   private static final int UNPRESSED_DEPTH = 2;
   protected static final int HALF_AABB_HEIGHT = 2;
   protected static final int HALF_AABB_WIDTH = 3;
   protected static final VoxelShape CEILING_AABB_X = Block.box(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
   protected static final VoxelShape CEILING_AABB_Z = Block.box(5.0, 14.0, 6.0, 11.0, 16.0, 10.0);
   protected static final VoxelShape FLOOR_AABB_X = Block.box(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
   protected static final VoxelShape FLOOR_AABB_Z = Block.box(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
   protected static final VoxelShape NORTH_AABB = Block.box(5.0, 6.0, 14.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(5.0, 6.0, 0.0, 11.0, 10.0, 2.0);
   protected static final VoxelShape WEST_AABB = Block.box(14.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape EAST_AABB = Block.box(0.0, 6.0, 5.0, 2.0, 10.0, 11.0);
   protected static final VoxelShape PRESSED_CEILING_AABB_X = Block.box(6.0, 15.0, 5.0, 10.0, 16.0, 11.0);
   protected static final VoxelShape PRESSED_CEILING_AABB_Z = Block.box(5.0, 15.0, 6.0, 11.0, 16.0, 10.0);
   protected static final VoxelShape PRESSED_FLOOR_AABB_X = Block.box(6.0, 0.0, 5.0, 10.0, 1.0, 11.0);
   protected static final VoxelShape PRESSED_FLOOR_AABB_Z = Block.box(5.0, 0.0, 6.0, 11.0, 1.0, 10.0);
   protected static final VoxelShape PRESSED_NORTH_AABB = Block.box(5.0, 6.0, 15.0, 11.0, 10.0, 16.0);
   protected static final VoxelShape PRESSED_SOUTH_AABB = Block.box(5.0, 6.0, 0.0, 11.0, 10.0, 1.0);
   protected static final VoxelShape PRESSED_WEST_AABB = Block.box(15.0, 6.0, 5.0, 16.0, 10.0, 11.0);
   protected static final VoxelShape PRESSED_EAST_AABB = Block.box(0.0, 6.0, 5.0, 1.0, 10.0, 11.0);
   private final boolean sensitive;

   protected ButtonBlock(boolean var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)).setValue(FACE, AttachFace.WALL)
      );
      this.sensitive = â˜ƒ;
   }

   private int getPressDuration() {
      return this.sensitive ? 30 : 20;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      boolean â˜ƒx = â˜ƒ.getValue(POWERED);
      switch((AttachFace)â˜ƒ.getValue(FACE)) {
         case FLOOR:
            if (â˜ƒ.getAxis() == Direction.Axis.X) {
               return â˜ƒx ? PRESSED_FLOOR_AABB_X : FLOOR_AABB_X;
            }

            return â˜ƒx ? PRESSED_FLOOR_AABB_Z : FLOOR_AABB_Z;
         case WALL:
            switch(â˜ƒ) {
               case EAST:
                  return â˜ƒx ? PRESSED_EAST_AABB : EAST_AABB;
               case WEST:
                  return â˜ƒx ? PRESSED_WEST_AABB : WEST_AABB;
               case SOUTH:
                  return â˜ƒx ? PRESSED_SOUTH_AABB : SOUTH_AABB;
               case NORTH:
               default:
                  return â˜ƒx ? PRESSED_NORTH_AABB : NORTH_AABB;
            }
         case CEILING:
         default:
            if (â˜ƒ.getAxis() == Direction.Axis.X) {
               return â˜ƒx ? PRESSED_CEILING_AABB_X : CEILING_AABB_X;
            } else {
               return â˜ƒx ? PRESSED_CEILING_AABB_Z : CEILING_AABB_Z;
            }
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.getValue(POWERED)) {
         return InteractionResult.CONSUME;
      } else {
         this.press(â˜ƒ, â˜ƒ, â˜ƒ);
         this.playSound(â˜ƒ, â˜ƒ, â˜ƒ, true);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_PRESS, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   public void press(BlockState var1, Level var2, BlockPos var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(true)), 3);
      this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, this.getPressDuration());
   }

   protected void playSound(@Nullable Player var1, LevelAccessor var2, BlockPos var3, boolean var4) {
      â˜ƒ.playSound(â˜ƒ ? â˜ƒ : null, â˜ƒ, this.getSound(â˜ƒ), SoundSource.BLOCKS, 0.3F, â˜ƒ ? 0.6F : 0.5F);
   }

   protected abstract SoundEvent getSound(boolean var1);

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(POWERED)) {
            this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) && getConnectedDirection(â˜ƒ) == â˜ƒ ? 15 : 0;
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(POWERED)) {
         if (this.sensitive) {
            this.checkPressed(â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)), 3);
            this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
            this.playSound(null, â˜ƒ, â˜ƒ, false);
            â˜ƒ.gameEvent(GameEvent.BLOCK_UNPRESS, â˜ƒ);
         }
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide && this.sensitive && !â˜ƒ.getValue(POWERED)) {
         this.checkPressed(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void checkPressed(BlockState var1, Level var2, BlockPos var3) {
      List<? extends Entity> â˜ƒ = â˜ƒ.getEntitiesOfClass(AbstractArrow.class, â˜ƒ.getShape(â˜ƒ, â˜ƒ).bounds().move(â˜ƒ));
      boolean â˜ƒx = !â˜ƒ.isEmpty();
      boolean â˜ƒxx = â˜ƒ.getValue(POWERED);
      if (â˜ƒx != â˜ƒxx) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒx)), 3);
         this.updateNeighbours(â˜ƒ, â˜ƒ, â˜ƒ);
         this.playSound(null, â˜ƒ, â˜ƒ, â˜ƒx);
         â˜ƒ.gameEvent((Entity)â˜ƒ.stream().findFirst().orElse(null), â˜ƒx ? GameEvent.BLOCK_PRESS : GameEvent.BLOCK_UNPRESS, â˜ƒ);
      }

      if (â˜ƒx) {
         â˜ƒ.getBlockTicks().scheduleTick(new BlockPos(â˜ƒ), this, this.getPressDuration());
      }
   }

   private void updateNeighbours(BlockState var1, Level var2, BlockPos var3) {
      â˜ƒ.updateNeighborsAt(â˜ƒ, this);
      â˜ƒ.updateNeighborsAt(â˜ƒ.relative(getConnectedDirection(â˜ƒ).getOpposite()), this);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, POWERED, FACE);
   }
}
