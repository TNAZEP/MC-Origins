package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FollowOwnerGoal extends Goal {
   public static final int TELEPORT_WHEN_DISTANCE_IS = 12;
   private static final int MIN_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 2;
   private static final int MAX_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 3;
   private static final int MAX_VERTICAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 1;
   private final TamableAnimal tamable;
   private LivingEntity owner;
   private final LevelReader level;
   private final double speedModifier;
   private final PathNavigation navigation;
   private int timeToRecalcPath;
   private final float stopDistance;
   private final float startDistance;
   private float oldWaterCost;
   private final boolean canFly;

   public FollowOwnerGoal(TamableAnimal var1, double var2, float var4, float var5, boolean var6) {
      this.tamable = â˜ƒ;
      this.level = â˜ƒ.level;
      this.speedModifier = â˜ƒ;
      this.navigation = â˜ƒ.getNavigation();
      this.startDistance = â˜ƒ;
      this.stopDistance = â˜ƒ;
      this.canFly = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(â˜ƒ.getNavigation() instanceof GroundPathNavigation) && !(â˜ƒ.getNavigation() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   @Override
   public boolean canUse() {
      LivingEntity â˜ƒ = this.tamable.getOwner();
      if (â˜ƒ == null) {
         return false;
      } else if (â˜ƒ.isSpectator()) {
         return false;
      } else if (this.tamable.isOrderedToSit()) {
         return false;
      } else if (this.tamable.distanceToSqr(â˜ƒ) < (double)(this.startDistance * this.startDistance)) {
         return false;
      } else {
         this.owner = â˜ƒ;
         return true;
      }
   }

   @Override
   public boolean canContinueToUse() {
      if (this.navigation.isDone()) {
         return false;
      } else if (this.tamable.isOrderedToSit()) {
         return false;
      } else {
         return !(this.tamable.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
      }
   }

   @Override
   public void start() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.tamable.getPathfindingMalus(BlockPathTypes.WATER);
      this.tamable.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
   }

   @Override
   public void stop() {
      this.owner = null;
      this.navigation.stop();
      this.tamable.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
   }

   @Override
   public void tick() {
      this.tamable.getLookControl().setLookAt(this.owner, 10.0F, (float)this.tamable.getMaxHeadXRot());
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         if (!this.tamable.isLeashed() && !this.tamable.isPassenger()) {
            if (this.tamable.distanceToSqr(this.owner) >= 144.0) {
               this.teleportToOwner();
            } else {
               this.navigation.moveTo(this.owner, this.speedModifier);
            }
         }
      }
   }

   private void teleportToOwner() {
      BlockPos â˜ƒ = this.owner.blockPosition();

      for(int â˜ƒx = 0; â˜ƒx < 10; ++â˜ƒx) {
         int â˜ƒxx = this.randomIntInclusive(-3, 3);
         int â˜ƒxxx = this.randomIntInclusive(-1, 1);
         int â˜ƒxxxx = this.randomIntInclusive(-3, 3);
         boolean â˜ƒxxxxx = this.maybeTeleportTo(â˜ƒ.getX() + â˜ƒxx, â˜ƒ.getY() + â˜ƒxxx, â˜ƒ.getZ() + â˜ƒxxxx);
         if (â˜ƒxxxxx) {
            return;
         }
      }
   }

   private boolean maybeTeleportTo(int var1, int var2, int var3) {
      if (Math.abs((double)â˜ƒ - this.owner.getX()) < 2.0 && Math.abs((double)â˜ƒ - this.owner.getZ()) < 2.0) {
         return false;
      } else if (!this.canTeleportTo(new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ))) {
         return false;
      } else {
         this.tamable.moveTo((double)â˜ƒ + 0.5, (double)â˜ƒ, (double)â˜ƒ + 0.5, this.tamable.getYRot(), this.tamable.getXRot());
         this.navigation.stop();
         return true;
      }
   }

   private boolean canTeleportTo(BlockPos var1) {
      BlockPathTypes â˜ƒ = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, â˜ƒ.mutable());
      if (â˜ƒ != BlockPathTypes.WALKABLE) {
         return false;
      } else {
         BlockState â˜ƒ = this.level.getBlockState(â˜ƒ.below());
         if (!this.canFly && â˜ƒ.getBlock() instanceof LeavesBlock) {
            return false;
         } else {
            BlockPos â˜ƒ = â˜ƒ.subtract(this.tamable.blockPosition());
            return this.level.noCollision(this.tamable, this.tamable.getBoundingBox().move(â˜ƒ));
         }
      }
   }

   private int randomIntInclusive(int var1, int var2) {
      return this.tamable.getRandom().nextInt(â˜ƒ - â˜ƒ + 1) + â˜ƒ;
   }
}
