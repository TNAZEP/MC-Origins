package net.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class NoiseGeneratorOctaves extends NoiseGenerator {
   private final NoiseGeneratorImproved[] field_76307_a;
   private final int field_76306_b;

   public NoiseGeneratorOctaves(Random var1, int var2) {
      this.field_76306_b = ☃;
      this.field_76307_a = new NoiseGeneratorImproved[☃];

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         this.field_76307_a[☃] = new NoiseGeneratorImproved(☃);
      }
   }

   public double func_205563_a(double var1, double var3, double var5) {
      double ☃ = 0.0;
      double ☃x = 1.0;

      for(int ☃xx = 0; ☃xx < this.field_76306_b; ++☃xx) {
         ☃ += this.field_76307_a[☃xx].func_205560_c(☃ * ☃x, ☃ * ☃x, ☃ * ☃x) / ☃x;
         ☃x /= 2.0;
      }

      return ☃;
   }

   public double[] func_202647_a(int var1, int var2, int var3, int var4, int var5, int var6, double var7, double var9, double var11) {
      double[] ☃ = new double[☃ * ☃ * ☃];
      double ☃x = 1.0;

      for(int ☃xx = 0; ☃xx < this.field_76306_b; ++☃xx) {
         double ☃xxx = (double)☃ * ☃x * ☃;
         double ☃xxxx = (double)☃ * ☃x * ☃;
         double ☃xxxxx = (double)☃ * ☃x * ☃;
         long ☃xxxxxx = MathHelper.func_76124_d(☃xxx);
         long ☃xxxxxxx = MathHelper.func_76124_d(☃xxxxx);
         ☃xxx -= (double)☃xxxxxx;
         ☃xxxxx -= (double)☃xxxxxxx;
         ☃xxxxxx %= 16777216L;
         ☃xxxxxxx %= 16777216L;
         ☃xxx += (double)☃xxxxxx;
         ☃xxxxx += (double)☃xxxxxxx;
         this.field_76307_a[☃xx].func_76308_a(☃, ☃xxx, ☃xxxx, ☃xxxxx, ☃, ☃, ☃, ☃ * ☃x, ☃ * ☃x, ☃ * ☃x, ☃x);
         ☃x /= 2.0;
      }

      return ☃;
   }

   public double[] func_202646_a(int var1, int var2, int var3, int var4, double var5, double var7, double var9) {
      return this.func_202647_a(☃, 10, ☃, ☃, 1, ☃, ☃, 1.0, ☃);
   }
}
