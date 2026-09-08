package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class RangedAttackGoal extends Goal {
   private final Mob mob;
   private final RangedAttackMob rangedAttackMob;
   private LivingEntity target;
   private int attackTime = -1;
   private final double speedModifier;
   private int seeTime;
   private final int attackIntervalMin;
   private final int attackIntervalMax;
   private final float attackRadius;
   private final float attackRadiusSqr;

   public RangedAttackGoal(RangedAttackMob var1, double var2, int var4, float var5) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public RangedAttackGoal(RangedAttackMob var1, double var2, int var4, int var5, float var6) {
      if (!(â˜ƒ instanceof LivingEntity)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.rangedAttackMob = â˜ƒ;
         this.mob = (Mob)â˜ƒ;
         this.speedModifier = â˜ƒ;
         this.attackIntervalMin = â˜ƒ;
         this.attackIntervalMax = â˜ƒ;
         this.attackRadius = â˜ƒ;
         this.attackRadiusSqr = â˜ƒ * â˜ƒ;
         this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }
   }

   @Override
   public boolean canUse() {
      LivingEntity â˜ƒ = this.mob.getTarget();
      if (â˜ƒ != null && â˜ƒ.isAlive()) {
         this.target = â˜ƒ;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean canContinueToUse() {
      return this.canUse() || !this.mob.getNavigation().isDone();
   }

   @Override
   public void stop() {
      this.target = null;
      this.seeTime = 0;
      this.attackTime = -1;
   }

   @Override
   public void tick() {
      double â˜ƒ = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
      boolean â˜ƒx = this.mob.getSensing().hasLineOfSight(this.target);
      if (â˜ƒx) {
         ++this.seeTime;
      } else {
         this.seeTime = 0;
      }

      if (!(â˜ƒ > (double)this.attackRadiusSqr) && this.seeTime >= 5) {
         this.mob.getNavigation().stop();
      } else {
         this.mob.getNavigation().moveTo(this.target, this.speedModifier);
      }

      this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
      if (--this.attackTime == 0) {
         if (!â˜ƒx) {
            return;
         }

         float â˜ƒ = (float)Math.sqrt(â˜ƒ) / this.attackRadius;
         float â˜ƒx = Mth.clamp(â˜ƒ, 0.1F, 1.0F);
         this.rangedAttackMob.performRangedAttack(this.target, â˜ƒx);
         this.attackTime = Mth.floor(â˜ƒ * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
      } else if (this.attackTime < 0) {
         this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(â˜ƒ) / (double)this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
      }
   }
}
