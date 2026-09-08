package net.minecraft.world.entity.ai.goal.target;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.scores.Team;

public abstract class TargetGoal extends Goal {
   private static final int EMPTY_REACH_CACHE = 0;
   private static final int CAN_REACH_CACHE = 1;
   private static final int CANT_REACH_CACHE = 2;
   protected final Mob mob;
   protected final boolean mustSee;
   private final boolean mustReach;
   private int reachCache;
   private int reachCacheTime;
   private int unseenTicks;
   protected LivingEntity targetMob;
   protected int unseenMemoryTicks = 60;

   public TargetGoal(Mob var1, boolean var2) {
      this(â˜ƒ, â˜ƒ, false);
   }

   public TargetGoal(Mob var1, boolean var2, boolean var3) {
      this.mob = â˜ƒ;
      this.mustSee = â˜ƒ;
      this.mustReach = â˜ƒ;
   }

   @Override
   public boolean canContinueToUse() {
      LivingEntity â˜ƒ = this.mob.getTarget();
      if (â˜ƒ == null) {
         â˜ƒ = this.targetMob;
      }

      if (â˜ƒ == null) {
         return false;
      } else if (!this.mob.canAttack(â˜ƒ)) {
         return false;
      } else {
         Team â˜ƒ = this.mob.getTeam();
         Team â˜ƒx = â˜ƒ.getTeam();
         if (â˜ƒ != null && â˜ƒx == â˜ƒ) {
            return false;
         } else {
            double â˜ƒ = this.getFollowDistance();
            if (this.mob.distanceToSqr(â˜ƒ) > â˜ƒ * â˜ƒ) {
               return false;
            } else {
               if (this.mustSee) {
                  if (this.mob.getSensing().hasLineOfSight(â˜ƒ)) {
                     this.unseenTicks = 0;
                  } else if (++this.unseenTicks > this.unseenMemoryTicks) {
                     return false;
                  }
               }

               this.mob.setTarget(â˜ƒ);
               return true;
            }
         }
      }
   }

   protected double getFollowDistance() {
      return this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
   }

   @Override
   public void start() {
      this.reachCache = 0;
      this.reachCacheTime = 0;
      this.unseenTicks = 0;
   }

   @Override
   public void stop() {
      this.mob.setTarget(null);
      this.targetMob = null;
   }

   protected boolean canAttack(@Nullable LivingEntity var1, TargetingConditions var2) {
      if (â˜ƒ == null) {
         return false;
      } else if (!â˜ƒ.test(this.mob, â˜ƒ)) {
         return false;
      } else if (!this.mob.isWithinRestriction(â˜ƒ.blockPosition())) {
         return false;
      } else {
         if (this.mustReach) {
            if (--this.reachCacheTime <= 0) {
               this.reachCache = 0;
            }

            if (this.reachCache == 0) {
               this.reachCache = this.canReach(â˜ƒ) ? 1 : 2;
            }

            if (this.reachCache == 2) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean canReach(LivingEntity var1) {
      this.reachCacheTime = 10 + this.mob.getRandom().nextInt(5);
      Path â˜ƒ = this.mob.getNavigation().createPath(â˜ƒ, 0);
      if (â˜ƒ == null) {
         return false;
      } else {
         Node â˜ƒ = â˜ƒ.getEndNode();
         if (â˜ƒ == null) {
            return false;
         } else {
            int â˜ƒ = â˜ƒ.x - â˜ƒ.getBlockX();
            int â˜ƒx = â˜ƒ.z - â˜ƒ.getBlockZ();
            return (double)(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx) <= 2.25;
         }
      }
   }

   public TargetGoal setUnseenMemoryTicks(int var1) {
      this.unseenMemoryTicks = â˜ƒ;
      return this;
   }
}
