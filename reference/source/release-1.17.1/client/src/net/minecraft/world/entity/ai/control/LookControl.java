package net.minecraft.world.entity.ai.control;

import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class LookControl implements Control {
   protected final Mob mob;
   protected float yMaxRotSpeed;
   protected float xMaxRotAngle;
   protected boolean hasWanted;
   protected double wantedX;
   protected double wantedY;
   protected double wantedZ;

   public LookControl(Mob var1) {
      this.mob = â˜ƒ;
   }

   public void setLookAt(Vec3 var1) {
      this.setLookAt(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z);
   }

   public void setLookAt(Entity var1) {
      this.setLookAt(â˜ƒ.getX(), getWantedY(â˜ƒ), â˜ƒ.getZ());
   }

   public void setLookAt(Entity var1, float var2, float var3) {
      this.setLookAt(â˜ƒ.getX(), getWantedY(â˜ƒ), â˜ƒ.getZ(), â˜ƒ, â˜ƒ);
   }

   public void setLookAt(double var1, double var3, double var5) {
      this.setLookAt(â˜ƒ, â˜ƒ, â˜ƒ, (float)this.mob.getHeadRotSpeed(), (float)this.mob.getMaxHeadXRot());
   }

   public void setLookAt(double var1, double var3, double var5, float var7, float var8) {
      this.wantedX = â˜ƒ;
      this.wantedY = â˜ƒ;
      this.wantedZ = â˜ƒ;
      this.yMaxRotSpeed = â˜ƒ;
      this.xMaxRotAngle = â˜ƒ;
      this.hasWanted = true;
   }

   public void tick() {
      if (this.resetXRotOnTick()) {
         this.mob.setXRot(0.0F);
      }

      if (this.hasWanted) {
         this.hasWanted = false;
         this.getYRotD().ifPresent(var1 -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, var1, this.yMaxRotSpeed));
         this.getXRotD().ifPresent(var1 -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), var1, this.xMaxRotAngle)));
      } else {
         this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10.0F);
      }

      this.clampHeadRotationToBody();
   }

   protected void clampHeadRotationToBody() {
      if (!this.mob.getNavigation().isDone()) {
         this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float)this.mob.getMaxHeadYRot());
      }
   }

   protected boolean resetXRotOnTick() {
      return true;
   }

   public boolean isHasWanted() {
      return this.hasWanted;
   }

   public double getWantedX() {
      return this.wantedX;
   }

   public double getWantedY() {
      return this.wantedY;
   }

   public double getWantedZ() {
      return this.wantedZ;
   }

   protected Optional<Float> getXRotD() {
      double â˜ƒ = this.wantedX - this.mob.getX();
      double â˜ƒx = this.wantedY - this.mob.getEyeY();
      double â˜ƒxx = this.wantedZ - this.mob.getZ();
      double â˜ƒxxx = Math.sqrt(â˜ƒ * â˜ƒ + â˜ƒxx * â˜ƒxx);
      return !(Math.abs(â˜ƒx) > 1.0E-5F) && !(Math.abs(â˜ƒxxx) > 1.0E-5F)
         ? Optional.empty()
         : Optional.of((float)(-(Mth.atan2(â˜ƒx, â˜ƒxxx) * 180.0F / (float)Math.PI)));
   }

   protected Optional<Float> getYRotD() {
      double â˜ƒ = this.wantedX - this.mob.getX();
      double â˜ƒx = this.wantedZ - this.mob.getZ();
      return !(Math.abs(â˜ƒx) > 1.0E-5F) && !(Math.abs(â˜ƒ) > 1.0E-5F)
         ? Optional.empty()
         : Optional.of((float)(Mth.atan2(â˜ƒx, â˜ƒ) * 180.0F / (float)Math.PI) - 90.0F);
   }

   protected float rotateTowards(float var1, float var2, float var3) {
      float â˜ƒ = Mth.degreesDifference(â˜ƒ, â˜ƒ);
      float â˜ƒx = Mth.clamp(â˜ƒ, -â˜ƒ, â˜ƒ);
      return â˜ƒ + â˜ƒx;
   }

   private static double getWantedY(Entity var0) {
      return â˜ƒ instanceof LivingEntity ? â˜ƒ.getEyeY() : (â˜ƒ.getBoundingBox().minY + â˜ƒ.getBoundingBox().maxY) / 2.0;
   }
}
