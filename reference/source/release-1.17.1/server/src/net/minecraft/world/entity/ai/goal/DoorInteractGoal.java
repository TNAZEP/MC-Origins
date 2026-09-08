package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public abstract class DoorInteractGoal extends Goal {
   protected Mob mob;
   protected BlockPos doorPos = BlockPos.ZERO;
   protected boolean hasDoor;
   private boolean passed;
   private float doorOpenDirX;
   private float doorOpenDirZ;

   public DoorInteractGoal(Mob var1) {
      this.mob = â˜ƒ;
      if (!GoalUtils.hasGroundPathNavigation(â˜ƒ)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   protected boolean isOpen() {
      if (!this.hasDoor) {
         return false;
      } else {
         BlockState â˜ƒ = this.mob.level.getBlockState(this.doorPos);
         if (!(â˜ƒ.getBlock() instanceof DoorBlock)) {
            this.hasDoor = false;
            return false;
         } else {
            return â˜ƒ.getValue(DoorBlock.OPEN);
         }
      }
   }

   protected void setOpen(boolean var1) {
      if (this.hasDoor) {
         BlockState â˜ƒ = this.mob.level.getBlockState(this.doorPos);
         if (â˜ƒ.getBlock() instanceof DoorBlock) {
            ((DoorBlock)â˜ƒ.getBlock()).setOpen(this.mob, this.mob.level, â˜ƒ, this.doorPos, â˜ƒ);
         }
      }
   }

   @Override
   public boolean canUse() {
      if (!GoalUtils.hasGroundPathNavigation(this.mob)) {
         return false;
      } else if (!this.mob.horizontalCollision) {
         return false;
      } else {
         GroundPathNavigation â˜ƒ = (GroundPathNavigation)this.mob.getNavigation();
         Path â˜ƒx = â˜ƒ.getPath();
         if (â˜ƒx != null && !â˜ƒx.isDone() && â˜ƒ.canOpenDoors()) {
            for(int â˜ƒxx = 0; â˜ƒxx < Math.min(â˜ƒx.getNextNodeIndex() + 2, â˜ƒx.getNodeCount()); ++â˜ƒxx) {
               Node â˜ƒxxx = â˜ƒx.getNode(â˜ƒxx);
               this.doorPos = new BlockPos(â˜ƒxxx.x, â˜ƒxxx.y + 1, â˜ƒxxx.z);
               if (!(this.mob.distanceToSqr((double)this.doorPos.getX(), this.mob.getY(), (double)this.doorPos.getZ()) > 2.25)) {
                  this.hasDoor = DoorBlock.isWoodenDoor(this.mob.level, this.doorPos);
                  if (this.hasDoor) {
                     return true;
                  }
               }
            }

            this.doorPos = this.mob.blockPosition().above();
            this.hasDoor = DoorBlock.isWoodenDoor(this.mob.level, this.doorPos);
            return this.hasDoor;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.passed;
   }

   @Override
   public void start() {
      this.passed = false;
      this.doorOpenDirX = (float)((double)this.doorPos.getX() + 0.5 - this.mob.getX());
      this.doorOpenDirZ = (float)((double)this.doorPos.getZ() + 0.5 - this.mob.getZ());
   }

   @Override
   public void tick() {
      float â˜ƒ = (float)((double)this.doorPos.getX() + 0.5 - this.mob.getX());
      float â˜ƒx = (float)((double)this.doorPos.getZ() + 0.5 - this.mob.getZ());
      float â˜ƒxx = this.doorOpenDirX * â˜ƒ + this.doorOpenDirZ * â˜ƒx;
      if (â˜ƒxx < 0.0F) {
         this.passed = true;
      }
   }
}
