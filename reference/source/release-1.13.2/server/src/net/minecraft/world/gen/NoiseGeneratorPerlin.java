package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
   private final NoiseGeneratorSimplex[] field_151603_a;
   private final int field_151602_b;

   public NoiseGeneratorPerlin(Random var1, int var2) {
      this.field_151602_b = ☃;
      this.field_151603_a = new NoiseGeneratorSimplex[☃];

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         this.field_151603_a[☃] = new NoiseGeneratorSimplex(☃);
      }
   }

   public double func_151601_a(double var1, double var3) {
      double ☃ = 0.0;
      double ☃x = 1.0;

      for(int ☃xx = 0; ☃xx < this.field_151602_b; ++☃xx) {
         ☃ += this.field_151603_a[☃xx].func_151605_a(☃ * ☃x, ☃ * ☃x) / ☃x;
         ☃x /= 2.0;
      }

      return ☃;
   }

   public double[] func_202644_a(double var1, double var3, int var5, int var6, double var7, double var9, double var11) {
      return this.func_202645_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, 0.5);
   }

   public double[] func_202645_a(double var1, double var3, int var5, int var6, double var7, double var9, double var11, double var13) {
      double[] ☃ = new double[☃ * ☃];
      double ☃x = 1.0;
      double ☃xx = 1.0;

      for(int ☃xxx = 0; ☃xxx < this.field_151602_b; ++☃xxx) {
         this.field_151603_a[☃xxx].func_151606_a(☃, ☃, ☃, ☃, ☃, ☃ * ☃xx * ☃x, ☃ * ☃xx * ☃x, 0.55 / ☃x);
         ☃xx *= ☃;
         ☃x *= ☃;
      }

      return ☃;
   }
}
