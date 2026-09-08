package net.minecraft.client.util;

public class MouseSmoother {
   private double field_199103_a;
   private double field_199104_b;
   private double field_199105_c;

   public double func_199102_a(double var1, double var3) {
      this.field_199103_a += ☃;
      double ☃ = this.field_199103_a - this.field_199104_b;
      double ☃x = this.field_199105_c + (☃ - this.field_199105_c) * 0.5;
      double ☃xx = Math.signum(☃);
      if (☃xx * ☃ > ☃xx * this.field_199105_c) {
         ☃ = ☃x;
      }

      this.field_199105_c = ☃x;
      this.field_199104_b += ☃ * ☃;
      return ☃ * ☃;
   }

   public void func_199101_a() {
      this.field_199103_a = 0.0;
      this.field_199104_b = 0.0;
      this.field_199105_c = 0.0;
   }
}
