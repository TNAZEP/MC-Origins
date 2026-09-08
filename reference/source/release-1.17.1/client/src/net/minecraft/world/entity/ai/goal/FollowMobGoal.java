package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FollowMobGoal extends Goal {
   private final Mob mob;
   private final Predicate<Mob> followPredicate;
   private Mob followingMob;
   private final double speedModifier;
   private final PathNavigation navigation;
   private int timeToRecalcPath;
   private final float stopDistance;
   private float oldWaterCost;
   private final float areaSize;

   public FollowMobGoal(Mob var1, double var2, float var4, float var5) {
      this.mob = â˜ƒ;
      this.followPredicate = var1x -> var1x != null && â˜ƒ.getClass() != var1x.getClass();
      this.speedModifier = â˜ƒ;
      this.navigation = â˜ƒ.getNavigation();
      this.stopDistance = â˜ƒ;
      this.areaSize = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(â˜ƒ.getNavigation() instanceof GroundPathNavigation) && !(â˜ƒ.getNavigation() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   @Override
   public boolean canUse() {
      List<Mob> â˜ƒ = this.mob.level.getEntitiesOfClass(Mob.class, this.mob.getBoundingBox().inflate((double)this.areaSize), this.followPredicate);
      if (!â˜ƒ.isEmpty()) {
         for(Mob â˜ƒx : â˜ƒ) {
            if (!â˜ƒx.isInvisible()) {
               this.followingMob = â˜ƒx;
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean canContinueToUse() {
      return this.followingMob != null
         && !this.navigation.isDone()
         && this.mob.distanceToSqr(this.followingMob) > (double)(this.stopDistance * this.stopDistance);
   }

   @Override
   public void start() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
      this.mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
   }

   @Override
   public void stop() {
      this.followingMob = null;
      this.navigation.stop();
      this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
   }

   @Override
   public void tick() {
      if (this.followingMob != null && !this.mob.isLeashed()) {
         this.mob.getLookControl().setLookAt(this.followingMob, 10.0F, (float)this.mob.getMaxHeadXRot());
         if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            double â˜ƒ = this.mob.getX() - this.followingMob.getX();
            double â˜ƒx = this.mob.getY() - this.followingMob.getY();
            double â˜ƒxx = this.mob.getZ() - this.followingMob.getZ();
            double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
            if (!(â˜ƒxxx <= (double)(this.stopDistance * this.stopDistance))) {
               this.navigation.moveTo(this.followingMob, this.speedModifier);
            } else {
               this.navigation.stop();
               LookControl â˜ƒ = this.followingMob.getLookControl();
               if (â˜ƒxxx <= (double)this.stopDistance
                  || â˜ƒ.getWantedX() == this.mob.getX() && â˜ƒ.getWantedY() == this.mob.getY() && â˜ƒ.getWantedZ() == this.mob.getZ()) {
                  double â˜ƒx = this.followingMob.getX() - this.mob.getX();
                  double â˜ƒxx = this.followingMob.getZ() - this.mob.getZ();
                  this.navigation.moveTo(this.mob.getX() - â˜ƒx, this.mob.getY(), this.mob.getZ() - â˜ƒxx, this.speedModifier);
               }
            }
         }
      }
   }
}
