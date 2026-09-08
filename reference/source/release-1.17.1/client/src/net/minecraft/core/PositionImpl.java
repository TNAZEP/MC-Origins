package net.minecraft.core;

public class PositionImpl implements Position {
   protected final double x;
   protected final double y;
   protected final double z;

   public PositionImpl(double var1, double var3, double var5) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
   }

   @Override
   public double x() {
      return this.x;
   }

   @Override
   public double y() {
      return this.y;
   }

   @Override
   public double z() {
      return this.z;
   }
}
