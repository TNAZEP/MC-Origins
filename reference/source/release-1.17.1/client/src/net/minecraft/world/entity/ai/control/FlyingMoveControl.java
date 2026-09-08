package net.minecraft.world.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FlyingMoveControl extends MoveControl {
   private final int maxTurn;
   private final boolean hoversInPlace;

   public FlyingMoveControl(Mob var1, int var2, boolean var3) {
      super(â˜ƒ);
      this.maxTurn = â˜ƒ;
      this.hoversInPlace = â˜ƒ;
   }

   @Override
   public void tick() {
      if (this.operation == MoveControl.Operation.MOVE_TO) {
         this.operation = MoveControl.Operation.WAIT;
         this.mob.setNoGravity(true);
         double â˜ƒ = this.wantedX - this.mob.getX();
         double â˜ƒx = this.wantedY - this.mob.getY();
         double â˜ƒxx = this.wantedZ - this.mob.getZ();
         double â˜ƒxxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx + â˜ƒxx * â˜ƒxx;
         if (â˜ƒxxx < 2.5000003E-7F) {
            this.mob.setYya(0.0F);
            this.mob.setZza(0.0F);
            return;
         }

         float â˜ƒx = (float)(Mth.atan2(â˜ƒxx, â˜ƒ) * 180.0F / (float)Math.PI) - 90.0F;
         this.mob.setYRot(this.rotlerp(this.mob.getYRot(), â˜ƒx, 90.0F));
         float â˜ƒ;
         if (this.mob.isOnGround()) {
            â˜ƒ = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
         } else {
            â˜ƒ = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
         }

         this.mob.setSpeed(â˜ƒ);
         double â˜ƒ = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx);
         if (Math.abs(â˜ƒx) > 1.0E-5F || Math.abs(â˜ƒ) > 1.0E-5F) {
            float â˜ƒx = (float)(-(Mth.atan2(â˜ƒx, â˜ƒ) * 180.0F / (float)Math.PI));
            this.mob.setXRot(this.rotlerp(this.mob.getXRot(), â˜ƒx, (float)this.maxTurn));
            this.mob.setYya(â˜ƒx > 0.0 ? â˜ƒ : -â˜ƒ);
         }
      } else {
         if (!this.hoversInPlace) {
            this.mob.setNoGravity(false);
         }

         this.mob.setYya(0.0F);
         this.mob.setZza(0.0F);
      }
   }
}
