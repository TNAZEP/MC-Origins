package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class LeapAtTargetGoal extends Goal {
   private final Mob mob;
   private LivingEntity target;
   private final float yd;

   public LeapAtTargetGoal(Mob var1, float var2) {
      this.mob = â˜ƒ;
      this.yd = â˜ƒ;
      this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   @Override
   public boolean canUse() {
      if (this.mob.isVehicle()) {
         return false;
      } else {
         this.target = this.mob.getTarget();
         if (this.target == null) {
            return false;
         } else {
            double â˜ƒ = this.mob.distanceToSqr(this.target);
            if (â˜ƒ < 4.0 || â˜ƒ > 16.0) {
               return false;
            } else if (!this.mob.isOnGround()) {
               return false;
            } else {
               return this.mob.getRandom().nextInt(5) == 0;
            }
         }
      }
   }

   @Override
   public boolean canContinueToUse() {
      return !this.mob.isOnGround();
   }

   @Override
   public void start() {
      Vec3 â˜ƒ = this.mob.getDeltaMovement();
      Vec3 â˜ƒx = new Vec3(this.target.getX() - this.mob.getX(), 0.0, this.target.getZ() - this.mob.getZ());
      if (â˜ƒx.lengthSqr() > 1.0E-7) {
         â˜ƒx = â˜ƒx.normalize().scale(0.4).add(â˜ƒ.scale(0.2));
      }

      this.mob.setDeltaMovement(â˜ƒx.x, (double)this.yd, â˜ƒx.z);
   }
}
