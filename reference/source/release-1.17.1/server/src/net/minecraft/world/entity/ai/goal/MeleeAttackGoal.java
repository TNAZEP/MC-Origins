package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class MeleeAttackGoal extends Goal {
   protected final PathfinderMob mob;
   private final double speedModifier;
   private final boolean followingTargetEvenIfNotSeen;
   private Path path;
   private double pathedTargetX;
   private double pathedTargetY;
   private double pathedTargetZ;
   private int ticksUntilNextPathRecalculation;
   private int ticksUntilNextAttack;
   private final int attackInterval = 20;
   private long lastCanUseCheck;
   private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;

   public MeleeAttackGoal(PathfinderMob var1, double var2, boolean var4) {
      this.mob = â˜ƒ;
      this.speedModifier = â˜ƒ;
      this.followingTargetEvenIfNotSeen = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   @Override
   public boolean canUse() {
      long â˜ƒ = this.mob.level.getGameTime();
      if (â˜ƒ - this.lastCanUseCheck < 20L) {
         return false;
      } else {
         this.lastCanUseCheck = â˜ƒ;
         LivingEntity â˜ƒ = this.mob.getTarget();
         if (â˜ƒ == null) {
            return false;
         } else if (!â˜ƒ.isAlive()) {
            return false;
         } else {
            this.path = this.mob.getNavigation().createPath(â˜ƒ, 0);
            if (this.path != null) {
               return true;
            } else {
               return this.getAttackReachSqr(â˜ƒ) >= this.mob.distanceToSqr(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
            }
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      LivingEntity â˜ƒ = this.mob.getTarget();
      if (â˜ƒ == null) {
         return false;
      } else if (!â˜ƒ.isAlive()) {
         return false;
      } else if (!this.followingTargetEvenIfNotSeen) {
         return !this.mob.getNavigation().isDone();
      } else if (!this.mob.isWithinRestriction(â˜ƒ.blockPosition())) {
         return false;
      } else {
         return !(â˜ƒ instanceof Player) || !â˜ƒ.isSpectator() && !((Player)â˜ƒ).isCreative();
      }
   }

   @Override
   public void start() {
      this.mob.getNavigation().moveTo(this.path, this.speedModifier);
      this.mob.setAggressive(true);
      this.ticksUntilNextPathRecalculation = 0;
      this.ticksUntilNextAttack = 0;
   }

   @Override
   public void stop() {
      LivingEntity â˜ƒ = this.mob.getTarget();
      if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(â˜ƒ)) {
         this.mob.setTarget(null);
      }

      this.mob.setAggressive(false);
      this.mob.getNavigation().stop();
   }

   @Override
   public void tick() {
      LivingEntity â˜ƒ = this.mob.getTarget();
      this.mob.getLookControl().setLookAt(â˜ƒ, 30.0F, 30.0F);
      double â˜ƒx = this.mob.distanceToSqr(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
      this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
      if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(â˜ƒ))
         && this.ticksUntilNextPathRecalculation <= 0
         && (
            this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
               || â˜ƒ.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
               || this.mob.getRandom().nextFloat() < 0.05F
         )) {
         this.pathedTargetX = â˜ƒ.getX();
         this.pathedTargetY = â˜ƒ.getY();
         this.pathedTargetZ = â˜ƒ.getZ();
         this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
         if (â˜ƒx > 1024.0) {
            this.ticksUntilNextPathRecalculation += 10;
         } else if (â˜ƒx > 256.0) {
            this.ticksUntilNextPathRecalculation += 5;
         }

         if (!this.mob.getNavigation().moveTo(â˜ƒ, this.speedModifier)) {
            this.ticksUntilNextPathRecalculation += 15;
         }
      }

      this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
      this.checkAndPerformAttack(â˜ƒ, â˜ƒx);
   }

   protected void checkAndPerformAttack(LivingEntity var1, double var2) {
      double â˜ƒ = this.getAttackReachSqr(â˜ƒ);
      if (â˜ƒ <= â˜ƒ && this.ticksUntilNextAttack <= 0) {
         this.resetAttackCooldown();
         this.mob.swing(InteractionHand.MAIN_HAND);
         this.mob.doHurtTarget(â˜ƒ);
      }
   }

   protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = 20;
   }

   protected boolean isTimeToAttack() {
      return this.ticksUntilNextAttack <= 0;
   }

   protected int getTicksUntilNextAttack() {
      return this.ticksUntilNextAttack;
   }

   protected int getAttackInterval() {
      return 20;
   }

   protected double getAttackReachSqr(LivingEntity var1) {
      return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + â˜ƒ.getBbWidth());
   }
}
