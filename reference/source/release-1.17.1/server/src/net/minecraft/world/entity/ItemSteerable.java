package net.minecraft.world.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface ItemSteerable {
   boolean boost();

   void travelWithInput(Vec3 var1);

   float getSteeringSpeed();

   default boolean travel(Mob var1, ItemBasedSteering var2, Vec3 var3) {
      if (!â˜ƒ.isAlive()) {
         return false;
      } else {
         Entity â˜ƒ = â˜ƒ.getFirstPassenger();
         if (â˜ƒ.isVehicle() && â˜ƒ.canBeControlledByRider() && â˜ƒ instanceof Player) {
            â˜ƒ.setYRot(â˜ƒ.getYRot());
            â˜ƒ.yRotO = â˜ƒ.getYRot();
            â˜ƒ.setXRot(â˜ƒ.getXRot() * 0.5F);
            â˜ƒ.setRot(â˜ƒ.getYRot(), â˜ƒ.getXRot());
            â˜ƒ.yBodyRot = â˜ƒ.getYRot();
            â˜ƒ.yHeadRot = â˜ƒ.getYRot();
            â˜ƒ.maxUpStep = 1.0F;
            â˜ƒ.flyingSpeed = â˜ƒ.getSpeed() * 0.1F;
            if (â˜ƒ.boosting && â˜ƒ.boostTime++ > â˜ƒ.boostTimeTotal) {
               â˜ƒ.boosting = false;
            }

            if (â˜ƒ.isControlledByLocalInstance()) {
               float â˜ƒx = this.getSteeringSpeed();
               if (â˜ƒ.boosting) {
                  â˜ƒx += â˜ƒx * 1.15F * Mth.sin((float)â˜ƒ.boostTime / (float)â˜ƒ.boostTimeTotal * (float) Math.PI);
               }

               â˜ƒ.setSpeed(â˜ƒx);
               this.travelWithInput(new Vec3(0.0, 0.0, 1.0));
               â˜ƒ.lerpSteps = 0;
            } else {
               â˜ƒ.calculateEntityAnimation(â˜ƒ, false);
               â˜ƒ.setDeltaMovement(Vec3.ZERO);
            }

            â˜ƒ.tryCheckInsideBlocks();
            return true;
         } else {
            â˜ƒ.maxUpStep = 0.5F;
            â˜ƒ.flyingSpeed = 0.02F;
            this.travelWithInput(â˜ƒ);
            return false;
         }
      }
   }
}
