package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SmoothSwimmingMoveControl extends MoveControl {
   private final int maxTurnX;
   private final int maxTurnY;
   private final float inWaterSpeedModifier;
   private final float outsideWaterSpeedModifier;
   private final boolean applyGravity;

   public SmoothSwimmingMoveControl(Mob var1, int var2, int var3, float var4, float var5, boolean var6) {
      super(â˜ƒ);
      this.maxTurnX = â˜ƒ;
      this.maxTurnY = â˜ƒ;
      this.inWaterSpeedModifier = â˜ƒ;
      this.outsideWaterSpeedModifier = â˜ƒ;
      this.applyGravity = â˜ƒ;
   }

   @Override
   public void tick() {
      if (this.applyGravity && this.mob.isInWater()) {
         this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, 0.005, 0.0));
      }

      if (this.operation == MoveControl.Operation.MOVE_TO && !this.mob.getNavigation().isDone()) {
         double â˜ƒ = this.wantedX - this.mob.getX();
         double â˜ƒx = this.wantedY - this.mob.getY();
         double â˜ƒxx = this.wantedZ - this.mob.getZ();
         double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
         if (â˜ƒxxx < 2.5000003E-7F) {
            this.mob.setZza(0.0F);
         } else {
            float â˜ƒ = (float)(Mth.atan2(â˜ƒxx, â˜ƒ) * 180.0F / (float)Math.PI) - 90.0F;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), â˜ƒ, (float)this.maxTurnY));
            this.mob.yBodyRot = this.mob.getYRot();
            this.mob.yHeadRot = this.mob.getYRot();
            float â˜ƒx = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            if (this.mob.isInWater()) {
               this.mob.setSpeed(â˜ƒx * this.inWaterSpeedModifier);
               double â˜ƒxx = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx);
               if (Math.abs(â˜ƒx) > 1.0E-5F || Math.abs(â˜ƒxx) > 1.0E-5F) {
                  float â˜ƒxxx = -((float)(Mth.atan2(â˜ƒx, â˜ƒxx) * 180.0F / (float)Math.PI));
                  â˜ƒxxx = Mth.clamp(Mth.wrapDegrees(â˜ƒxxx), (float)(-this.maxTurnX), (float)this.maxTurnX);
                  this.mob.setXRot(this.rotlerp(this.mob.getXRot(), â˜ƒxxx, 5.0F));
               }

               float â˜ƒxx = Mth.cos(this.mob.getXRot() * (float) (Math.PI / 180.0));
               float â˜ƒxxx = Mth.sin(this.mob.getXRot() * (float) (Math.PI / 180.0));
               this.mob.zza = â˜ƒxx * â˜ƒx;
               this.mob.yya = -â˜ƒxxx * â˜ƒx;
            } else {
               this.mob.setSpeed(â˜ƒx * this.outsideWaterSpeedModifier);
            }
         }
      } else {
         this.mob.setSpeed(0.0F);
         this.mob.setXxa(0.0F);
         this.mob.setYya(0.0F);
         this.mob.setZza(0.0F);
      }
   }
}
