package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorSimplex {
   private static final int[][] field_151611_e = new int[][]{
      {1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}
   };
   public static final double field_151614_a = Math.sqrt(3.0);
   private final int[] field_151608_f = new int[512];
   public double field_151612_b;
   public double field_151613_c;
   public double field_151610_d;
   private static final double field_151609_g = 0.5 * (field_151614_a - 1.0);
   private static final double field_151615_h = (3.0 - field_151614_a) / 6.0;

   public NoiseGeneratorSimplex(Random var1) {
      this.field_151612_b = ☃.nextDouble() * 256.0;
      this.field_151613_c = ☃.nextDouble() * 256.0;
      this.field_151610_d = ☃.nextDouble() * 256.0;
      int ☃ = 0;

      while(☃ < 256) {
         this.field_151608_f[☃] = ☃++;
      }

      for(int ☃x = 0; ☃x < 256; ++☃x) {
         int ☃xx = ☃.nextInt(256 - ☃x) + ☃x;
         int ☃xxx = this.field_151608_f[☃x];
         this.field_151608_f[☃x] = this.field_151608_f[☃xx];
         this.field_151608_f[☃xx] = ☃xxx;
         this.field_151608_f[☃x + 256] = this.field_151608_f[☃x];
      }
   }

   private static int func_151607_a(double var0) {
      return ☃ > 0.0 ? (int)☃ : (int)☃ - 1;
   }

   private static double func_151604_a(int[] var0, double var1, double var3) {
      return (double)☃[0] * ☃ + (double)☃[1] * ☃;
   }

   public double func_151605_a(double var1, double var3) {
      double ☃xx = 0.5 * (field_151614_a - 1.0);
      double ☃xxx = (☃ + ☃) * ☃xx;
      int ☃xxxx = func_151607_a(☃ + ☃xxx);
      int ☃xxxxx = func_151607_a(☃ + ☃xxx);
      double ☃xxxxxx = (3.0 - field_151614_a) / 6.0;
      double ☃xxxxxxx = (double)(☃xxxx + ☃xxxxx) * ☃xxxxxx;
      double ☃xxxxxxxx = (double)☃xxxx - ☃xxxxxxx;
      double ☃xxxxxxxxx = (double)☃xxxxx - ☃xxxxxxx;
      double ☃xxxxxxxxxx = ☃ - ☃xxxxxxxx;
      double ☃xxxxxxxxxxx = ☃ - ☃xxxxxxxxx;
      int ☃;
      int ☃x;
      if (☃xxxxxxxxxx > ☃xxxxxxxxxxx) {
         ☃ = 1;
         ☃x = 0;
      } else {
         ☃ = 0;
         ☃x = 1;
      }

      double ☃x = ☃xxxxxxxxxx - (double)☃ + ☃xxxxxx;
      double ☃xx = ☃xxxxxxxxxxx - (double)☃x + ☃xxxxxx;
      double ☃xxx = ☃xxxxxxxxxx - 1.0 + 2.0 * ☃xxxxxx;
      double ☃xxxx = ☃xxxxxxxxxxx - 1.0 + 2.0 * ☃xxxxxx;
      int ☃xxxxx = ☃xxxx & 0xFF;
      int ☃xxxxxx = ☃xxxxx & 0xFF;
      int ☃xxxxxxx = this.field_151608_f[☃xxxxx + this.field_151608_f[☃xxxxxx]] % 12;
      int ☃xxxxxxxx = this.field_151608_f[☃xxxxx + ☃ + this.field_151608_f[☃xxxxxx + ☃x]] % 12;
      int ☃xxxxxxxxx = this.field_151608_f[☃xxxxx + 1 + this.field_151608_f[☃xxxxxx + 1]] % 12;
      double ☃xxxxxxxxxx = 0.5 - ☃xxxxxxxxxx * ☃xxxxxxxxxx - ☃xxxxxxxxxxx * ☃xxxxxxxxxxx;
      double ☃;
      if (☃xxxxxxxxxx < 0.0) {
         ☃ = 0.0;
      } else {
         ☃xxxxxxxxxx *= ☃xxxxxxxxxx;
         ☃ = ☃xxxxxxxxxx * ☃xxxxxxxxxx * func_151604_a(field_151611_e[☃xxxxxxx], ☃xxxxxxxxxx, ☃xxxxxxxxxxx);
      }

      double ☃x = 0.5 - ☃x * ☃x - ☃xx * ☃xx;
      double ☃;
      if (☃x < 0.0) {
         ☃ = 0.0;
      } else {
         ☃x *= ☃x;
         ☃ = ☃x * ☃x * func_151604_a(field_151611_e[☃xxxxxxxx], ☃x, ☃xx);
      }

      double ☃x = 0.5 - ☃xxx * ☃xxx - ☃xxxx * ☃xxxx;
      double ☃;
      if (☃x < 0.0) {
         ☃ = 0.0;
      } else {
         ☃x *= ☃x;
         ☃ = ☃x * ☃x * func_151604_a(field_151611_e[☃xxxxxxxxx], ☃xxx, ☃xxxx);
      }

      return 70.0 * (☃ + ☃ + ☃);
   }

   public void func_151606_a(double[] var1, double var2, double var4, int var6, int var7, double var8, double var10, double var12) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         double ☃xx = (☃ + (double)☃x) * ☃ + this.field_151613_c;

         for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
            double ☃xxxxxx = (☃ + (double)☃xxx) * ☃ + this.field_151612_b;
            double ☃xxxxxxx = (☃xxxxxx + ☃xx) * field_151609_g;
            int ☃xxxxxxxx = func_151607_a(☃xxxxxx + ☃xxxxxxx);
            int ☃xxxxxxxxx = func_151607_a(☃xx + ☃xxxxxxx);
            double ☃xxxxxxxxxx = (double)(☃xxxxxxxx + ☃xxxxxxxxx) * field_151615_h;
            double ☃xxxxxxxxxxx = (double)☃xxxxxxxx - ☃xxxxxxxxxx;
            double ☃xxxxxxxxxxxx = (double)☃xxxxxxxxx - ☃xxxxxxxxxx;
            double ☃xxxxxxxxxxxxx = ☃xxxxxx - ☃xxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxx = ☃xx - ☃xxxxxxxxxxxx;
            int ☃xxxx;
            int ☃xxxxx;
            if (☃xxxxxxxxxxxxx > ☃xxxxxxxxxxxxxx) {
               ☃xxxx = 1;
               ☃xxxxx = 0;
            } else {
               ☃xxxx = 0;
               ☃xxxxx = 1;
            }

            double ☃xxxxx = ☃xxxxxxxxxxxxx - (double)☃xxxx + field_151615_h;
            double ☃xxxxxx = ☃xxxxxxxxxxxxxx - (double)☃xxxxx + field_151615_h;
            double ☃xxxxxxx = ☃xxxxxxxxxxxxx - 1.0 + 2.0 * field_151615_h;
            double ☃xxxxxxxx = ☃xxxxxxxxxxxxxx - 1.0 + 2.0 * field_151615_h;
            int ☃xxxxxxxxx = ☃xxxxxxxx & 0xFF;
            int ☃xxxxxxxxxx = ☃xxxxxxxxx & 0xFF;
            int ☃xxxxxxxxxxx = this.field_151608_f[☃xxxxxxxxx + this.field_151608_f[☃xxxxxxxxxx]] % 12;
            int ☃xxxxxxxxxxxx = this.field_151608_f[☃xxxxxxxxx + ☃xxxx + this.field_151608_f[☃xxxxxxxxxx + ☃xxxxx]] % 12;
            int ☃xxxxxxxxxxxxx = this.field_151608_f[☃xxxxxxxxx + 1 + this.field_151608_f[☃xxxxxxxxxx + 1]] % 12;
            double ☃xxxxxxxxxxxxxx = 0.5 - ☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx - ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx;
            double ☃xxxx;
            if (☃xxxxxxxxxxxxxx < 0.0) {
               ☃xxxx = 0.0;
            } else {
               ☃xxxxxxxxxxxxxx *= ☃xxxxxxxxxxxxxx;
               ☃xxxx = ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx * func_151604_a(field_151611_e[☃xxxxxxxxxxx], ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
            }

            double ☃xxxxx = 0.5 - ☃xxxxx * ☃xxxxx - ☃xxxxxx * ☃xxxxxx;
            double ☃xxxx;
            if (☃xxxxx < 0.0) {
               ☃xxxx = 0.0;
            } else {
               ☃xxxxx *= ☃xxxxx;
               ☃xxxx = ☃xxxxx * ☃xxxxx * func_151604_a(field_151611_e[☃xxxxxxxxxxxx], ☃xxxxx, ☃xxxxxx);
            }

            double ☃xxxxx = 0.5 - ☃xxxxxxx * ☃xxxxxxx - ☃xxxxxxxx * ☃xxxxxxxx;
            double ☃xxxx;
            if (☃xxxxx < 0.0) {
               ☃xxxx = 0.0;
            } else {
               ☃xxxxx *= ☃xxxxx;
               ☃xxxx = ☃xxxxx * ☃xxxxx * func_151604_a(field_151611_e[☃xxxxxxxxxxxxx], ☃xxxxxxx, ☃xxxxxxxx);
            }

            int var10001 = ☃++;
            ☃[var10001] += 70.0 * (☃xxxx + ☃xxxx + ☃xxxx) * ☃;
         }
      }
   }
}
