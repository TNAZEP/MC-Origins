package net.minecraft.util;

public class SmoothDouble {
   private double targetValue;
   private double remainingValue;
   private double lastAmount;

   public double getNewDeltaValue(double var1, double var3) {
      this.targetValue += â˜ƒ;
      double â˜ƒ = this.targetValue - this.remainingValue;
      double â˜ƒx = Mth.lerp(0.5, this.lastAmount, â˜ƒ);
      double â˜ƒxx = Math.signum(â˜ƒ);
      if (â˜ƒxx * â˜ƒ > â˜ƒxx * this.lastAmount) {
         â˜ƒ = â˜ƒx;
      }

      this.lastAmount = â˜ƒx;
      this.remainingValue += â˜ƒ * â˜ƒ;
      return â˜ƒ * â˜ƒ;
   }

   public void reset() {
      this.targetValue = 0.0;
      this.remainingValue = 0.0;
      this.lastAmount = 0.0;
   }
}
