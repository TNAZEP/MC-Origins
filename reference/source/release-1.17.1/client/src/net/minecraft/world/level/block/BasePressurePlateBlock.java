package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class BasePressurePlateBlock extends Block {
   protected static final VoxelShape PRESSED_AABB = Block.box(1.0, 0.0, 1.0, 15.0, 0.5, 15.0);
   protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0);
   protected static final AABB TOUCH_AABB = new AABB(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

   protected BasePressurePlateBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.getSignalForState(â˜ƒ) > 0 ? PRESSED_AABB : AABB;
   }

   protected int getPressedTime() {
      return 20;
   }

   @Override
   public boolean isPossibleToRespawnInThis() {
      return true;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == Direction.DOWN && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      return canSupportRigidBlock(â˜ƒ, â˜ƒ) || canSupportCenter(â˜ƒ, â˜ƒ, Direction.UP);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      int â˜ƒ = this.getSignalForState(â˜ƒ);
      if (â˜ƒ > 0) {
         this.checkPressed(null, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void entityInside(BlockState var1, Level var2, BlockPos var3, Entity var4) {
      if (!â˜ƒ.isClientSide) {
         int â˜ƒ = this.getSignalForState(â˜ƒ);
         if (â˜ƒ == 0) {
            this.checkPressed(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   protected void checkPressed(@Nullable Entity var1, Level var2, BlockPos var3, BlockState var4, int var5) {
      int â˜ƒ = this.getSignalStrength(â˜ƒ, â˜ƒ);
      boolean â˜ƒx = â˜ƒ > 0;
      boolean â˜ƒxx = â˜ƒ > 0;
      if (â˜ƒ != â˜ƒ) {
         BlockState â˜ƒxxx = this.setSignalForState(â˜ƒ, â˜ƒ);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒxxx, 2);
         this.updateNeighbours(â˜ƒ, â˜ƒ);
         â˜ƒ.setBlocksDirty(â˜ƒ, â˜ƒ, â˜ƒxxx);
      }

      if (!â˜ƒxx && â˜ƒx) {
         this.playOffSound(â˜ƒ, â˜ƒ);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_UNPRESS, â˜ƒ);
      } else if (â˜ƒxx && !â˜ƒx) {
         this.playOnSound(â˜ƒ, â˜ƒ);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_PRESS, â˜ƒ);
      }

      if (â˜ƒxx) {
         â˜ƒ.getBlockTicks().scheduleTick(new BlockPos(â˜ƒ), this, this.getPressedTime());
      }
   }

   protected abstract void playOnSound(LevelAccessor var1, BlockPos var2);

   protected abstract void playOffSound(LevelAccessor var1, BlockPos var2);

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         if (this.getSignalForState(â˜ƒ) > 0) {
            this.updateNeighbours(â˜ƒ, â˜ƒ);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected void updateNeighbours(Level var1, BlockPos var2) {
      â˜ƒ.updateNeighborsAt(â˜ƒ, this);
      â˜ƒ.updateNeighborsAt(â˜ƒ.below(), this);
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return this.getSignalForState(â˜ƒ);
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ == Direction.UP ? this.getSignalForState(â˜ƒ) : 0;
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   protected abstract int getSignalStrength(Level var1, BlockPos var2);

   protected abstract int getSignalForState(BlockState var1);

   protected abstract BlockState setSignalForState(BlockState var1, int var2);
}
