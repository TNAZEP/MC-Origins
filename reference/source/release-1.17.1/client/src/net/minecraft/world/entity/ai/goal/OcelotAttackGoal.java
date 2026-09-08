package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;

public class OcelotAttackGoal extends Goal {
   private final BlockGetter level;
   private final Mob mob;
   private LivingEntity target;
   private int attackTime;

   public OcelotAttackGoal(Mob var1) {
      this.mob = â˜ƒ;
      this.level = â˜ƒ.level;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   @Override
   public boolean canUse() {
      LivingEntity â˜ƒ = this.mob.getTarget();
      if (â˜ƒ == null) {
         return false;
      } else {
         this.target = â˜ƒ;
         return true;
      }
   }

   @Override
   public boolean canContinueToUse() {
      if (!this.target.isAlive()) {
         return false;
      } else if (this.mob.distanceToSqr(this.target) > 225.0) {
         return false;
      } else {
         return !this.mob.getNavigation().isDone() || this.canUse();
      }
   }

   @Override
   public void stop() {
      this.target = null;
      this.mob.getNavigation().stop();
   }

   @Override
   public void tick() {
      this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
      double â˜ƒ = (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F);
      double â˜ƒx = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
      double â˜ƒxx = 0.8;
      if (â˜ƒx > â˜ƒ && â˜ƒx < 16.0) {
         â˜ƒxx = 1.33;
      } else if (â˜ƒx < 225.0) {
         â˜ƒxx = 0.6;
      }

      this.mob.getNavigation().moveTo(this.target, â˜ƒxx);
      this.attackTime = Math.max(this.attackTime - 1, 0);
      if (!(â˜ƒx > â˜ƒ)) {
         if (this.attackTime <= 0) {
            this.attackTime = 20;
            this.mob.doHurtTarget(this.target);
         }
      }
   }
}
