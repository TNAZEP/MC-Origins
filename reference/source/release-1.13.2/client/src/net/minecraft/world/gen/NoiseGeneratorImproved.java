package net.minecraft.world.gen;

import java.util.Random;

public class NoiseGeneratorImproved extends NoiseGenerator {
   private final int[] field_76312_d = new int[512];
   public double field_76315_a;
   public double field_76313_b;
   public double field_76314_c;
   private static final double[] field_152381_e = new double[]{1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0};
   private static final double[] field_152382_f = new double[]{1.0, 1.0, -1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0};
   private static final double[] field_152383_g = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0};
   private static final double[] field_152384_h = new double[]{1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 1.0, -1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, -1.0, 0.0};
   private static final double[] field_152385_i = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 1.0, -1.0, -1.0, 1.0, 1.0, -1.0, -1.0, 0.0, 1.0, 0.0, -1.0};

   public NoiseGeneratorImproved(Random var1) {
      this.field_76315_a = ☃.nextDouble() * 256.0;
      this.field_76313_b = ☃.nextDouble() * 256.0;
      this.field_76314_c = ☃.nextDouble() * 256.0;
      int ☃ = 0;

      while(☃ < 256) {
         this.field_76312_d[☃] = ☃++;
      }

      for(int ☃x = 0; ☃x < 256; ++☃x) {
         int ☃xx = ☃.nextInt(256 - ☃x) + ☃x;
         int ☃xxx = this.field_76312_d[☃x];
         this.field_76312_d[☃x] = this.field_76312_d[☃xx];
         this.field_76312_d[☃xx] = ☃xxx;
         this.field_76312_d[☃x + 256] = this.field_76312_d[☃x];
      }
   }

   public double func_205561_a(double var1, double var3, double var5) {
      double ☃ = ☃ + this.field_76315_a;
      double ☃x = ☃ + this.field_76313_b;
      double ☃xx = ☃ + this.field_76314_c;
      int ☃xxx = (int)☃;
      int ☃xxxx = (int)☃x;
      int ☃xxxxx = (int)☃xx;
      if (☃ < (double)☃xxx) {
         --☃xxx;
      }

      if (☃x < (double)☃xxxx) {
         --☃xxxx;
      }

      if (☃xx < (double)☃xxxxx) {
         --☃xxxxx;
      }

      int ☃ = ☃xxx & 0xFF;
      int ☃x = ☃xxxx & 0xFF;
      int ☃xx = ☃xxxxx & 0xFF;
      ☃ -= (double)☃xxx;
      ☃x -= (double)☃xxxx;
      ☃xx -= (double)☃xxxxx;
      double ☃xxx = ☃ * ☃ * ☃ * (☃ * (☃ * 6.0 - 15.0) + 10.0);
      double ☃xxxx = ☃x * ☃x * ☃x * (☃x * (☃x * 6.0 - 15.0) + 10.0);
      double ☃xxxxx = ☃xx * ☃xx * ☃xx * (☃xx * (☃xx * 6.0 - 15.0) + 10.0);
      int ☃xxxxxx = this.field_76312_d[☃] + ☃x;
      int ☃xxxxxxx = this.field_76312_d[☃xxxxxx] + ☃xx;
      int ☃xxxxxxxx = this.field_76312_d[☃xxxxxx + 1] + ☃xx;
      int ☃xxxxxxxxx = this.field_76312_d[☃ + 1] + ☃x;
      int ☃xxxxxxxxxx = this.field_76312_d[☃xxxxxxxxx] + ☃xx;
      int ☃xxxxxxxxxxx = this.field_76312_d[☃xxxxxxxxx + 1] + ☃xx;
      return this.func_76311_b(
         ☃xxxxx,
         this.func_76311_b(
            ☃xxxx,
            this.func_76311_b(
               ☃xxx, this.func_76310_a(this.field_76312_d[☃xxxxxxx], ☃, ☃x, ☃xx), this.func_76310_a(this.field_76312_d[☃xxxxxxxxxx], ☃ - 1.0, ☃x, ☃xx)
            ),
            this.func_76311_b(
               ☃xxx,
               this.func_76310_a(this.field_76312_d[☃xxxxxxxx], ☃, ☃x - 1.0, ☃xx),
               this.func_76310_a(this.field_76312_d[☃xxxxxxxxxxx], ☃ - 1.0, ☃x - 1.0, ☃xx)
            )
         ),
         this.func_76311_b(
            ☃xxxx,
            this.func_76311_b(
               ☃xxx,
               this.func_76310_a(this.field_76312_d[☃xxxxxxx + 1], ☃, ☃x, ☃xx - 1.0),
               this.func_76310_a(this.field_76312_d[☃xxxxxxxxxx + 1], ☃ - 1.0, ☃x, ☃xx - 1.0)
            ),
            this.func_76311_b(
               ☃xxx,
               this.func_76310_a(this.field_76312_d[☃xxxxxxxx + 1], ☃, ☃x - 1.0, ☃xx - 1.0),
               this.func_76310_a(this.field_76312_d[☃xxxxxxxxxxx + 1], ☃ - 1.0, ☃x - 1.0, ☃xx - 1.0)
            )
         )
      );
   }

   public final double func_76311_b(double var1, double var3, double var5) {
      return ☃ + ☃ * (☃ - ☃);
   }

   public final double func_76309_a(int var1, double var2, double var4) {
      int ☃ = ☃ & 15;
      return field_152384_h[☃] * ☃ + field_152385_i[☃] * ☃;
   }

   public final double func_76310_a(int var1, double var2, double var4, double var6) {
      int ☃ = ☃ & 15;
      return field_152381_e[☃] * ☃ + field_152382_f[☃] * ☃ + field_152383_g[☃] * ☃;
   }

   public double func_205562_a(double var1, double var3) {
      return this.func_205561_a(☃, ☃, 0.0);
   }

   public double func_205560_c(double var1, double var3, double var5) {
      return this.func_205561_a(☃, ☃, ☃);
   }

   public void func_76308_a(
      double[] var1, double var2, double var4, double var6, int var8, int var9, int var10, double var11, double var13, double var15, double var17
   ) {
      if (☃ == 1) {
         int ☃ = 0;
         int ☃x = 0;
         int ☃xx = 0;
         int ☃xxx = 0;
         double ☃xxxx = 0.0;
         double ☃xxxxx = 0.0;
         int ☃xxxxxx = 0;
         double ☃xxxxxxx = 1.0 / ☃;

         for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃; ++☃xxxxxxxx) {
            double ☃xxxxxxxxx = ☃ + (double)☃xxxxxxxx * ☃ + this.field_76315_a;
            int ☃xxxxxxxxxx = (int)☃xxxxxxxxx;
            if (☃xxxxxxxxx < (double)☃xxxxxxxxxx) {
               --☃xxxxxxxxxx;
            }

            int ☃xxxxxxxxx = ☃xxxxxxxxxx & 0xFF;
            ☃xxxxxxxxx -= (double)☃xxxxxxxxxx;
            double ☃xxxxxxxxxx = ☃xxxxxxxxx * ☃xxxxxxxxx * ☃xxxxxxxxx * (☃xxxxxxxxx * (☃xxxxxxxxx * 6.0 - 15.0) + 10.0);

            for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < ☃; ++☃xxxxxxxxxxx) {
               double ☃xxxxxxxxxxxx = ☃ + (double)☃xxxxxxxxxxx * ☃ + this.field_76314_c;
               int ☃xxxxxxxxxxxxx = (int)☃xxxxxxxxxxxx;
               if (☃xxxxxxxxxxxx < (double)☃xxxxxxxxxxxxx) {
                  --☃xxxxxxxxxxxxx;
               }

               int ☃xxxxxxxxxxxx = ☃xxxxxxxxxxxxx & 0xFF;
               ☃xxxxxxxxxxxx -= (double)☃xxxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx * (☃xxxxxxxxxxxx * (☃xxxxxxxxxxxx * 6.0 - 15.0) + 10.0);
               ☃ = this.field_76312_d[☃xxxxxxxxx] + 0;
               ☃x = this.field_76312_d[☃] + ☃xxxxxxxxxxxx;
               ☃xx = this.field_76312_d[☃xxxxxxxxx + 1] + 0;
               ☃xxx = this.field_76312_d[☃xx] + ☃xxxxxxxxxxxx;
               ☃xxxx = this.func_76311_b(
                  ☃xxxxxxxxxx,
                  this.func_76309_a(this.field_76312_d[☃x], ☃xxxxxxxxx, ☃xxxxxxxxxxxx),
                  this.func_76310_a(this.field_76312_d[☃xxx], ☃xxxxxxxxx - 1.0, 0.0, ☃xxxxxxxxxxxx)
               );
               ☃xxxxx = this.func_76311_b(
                  ☃xxxxxxxxxx,
                  this.func_76310_a(this.field_76312_d[☃x + 1], ☃xxxxxxxxx, 0.0, ☃xxxxxxxxxxxx - 1.0),
                  this.func_76310_a(this.field_76312_d[☃xxx + 1], ☃xxxxxxxxx - 1.0, 0.0, ☃xxxxxxxxxxxx - 1.0)
               );
               double ☃xxxxxxxxxxxxxx = this.func_76311_b(☃xxxxxxxxxxxxx, ☃xxxx, ☃xxxxx);
               int var97 = ☃xxxxxx++;
               ☃[var97] += ☃xxxxxxxxxxxxxx * ☃xxxxxxx;
            }
         }
      } else {
         int ☃ = 0;
         double ☃x = 1.0 / ☃;
         int ☃xx = -1;
         int ☃xxx = 0;
         int ☃xxxx = 0;
         int ☃xxxxx = 0;
         int ☃xxxxxx = 0;
         int ☃xxxxxxx = 0;
         int ☃xxxxxxxx = 0;
         double ☃xxxxxxxxx = 0.0;
         double ☃xxxxxxxxxx = 0.0;
         double ☃xxxxxxxxxxx = 0.0;
         double ☃xxxxxxxxxxxx = 0.0;

         for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxxx) {
            double ☃xxxxxxxxxxxxxx = ☃ + (double)☃xxxxxxxxxxxxx * ☃ + this.field_76315_a;
            int ☃xxxxxxxxxxxxxxx = (int)☃xxxxxxxxxxxxxx;
            if (☃xxxxxxxxxxxxxx < (double)☃xxxxxxxxxxxxxxx) {
               --☃xxxxxxxxxxxxxxx;
            }

            int ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx & 0xFF;
            ☃xxxxxxxxxxxxxx -= (double)☃xxxxxxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx * (☃xxxxxxxxxxxxxx * (☃xxxxxxxxxxxxxx * 6.0 - 15.0) + 10.0);

            for(int ☃xxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxxxxx = ☃ + (double)☃xxxxxxxxxxxxxxxx * ☃ + this.field_76314_c;
               int ☃xxxxxxxxxxxxxxxxxx = (int)☃xxxxxxxxxxxxxxxxx;
               if (☃xxxxxxxxxxxxxxxxx < (double)☃xxxxxxxxxxxxxxxxxx) {
                  --☃xxxxxxxxxxxxxxxxxx;
               }

               int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxx & 0xFF;
               ☃xxxxxxxxxxxxxxxxx -= (double)☃xxxxxxxxxxxxxxxxxx;
               double ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx
                  * ☃xxxxxxxxxxxxxxxxx
                  * ☃xxxxxxxxxxxxxxxxx
                  * (☃xxxxxxxxxxxxxxxxx * (☃xxxxxxxxxxxxxxxxx * 6.0 - 15.0) + 10.0);

               for(int ☃xxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxxxxxxxxx) {
                  double ☃xxxxxxxxxxxxxxxxxxxx = ☃ + (double)☃xxxxxxxxxxxxxxxxxxx * ☃ + this.field_76313_b;
                  int ☃xxxxxxxxxxxxxxxxxxxxx = (int)☃xxxxxxxxxxxxxxxxxxxx;
                  if (☃xxxxxxxxxxxxxxxxxxxx < (double)☃xxxxxxxxxxxxxxxxxxxxx) {
                     --☃xxxxxxxxxxxxxxxxxxxxx;
                  }

                  int ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxx & 0xFF;
                  ☃xxxxxxxxxxxxxxxxxxxx -= (double)☃xxxxxxxxxxxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxx
                     * ☃xxxxxxxxxxxxxxxxxxxx
                     * ☃xxxxxxxxxxxxxxxxxxxx
                     * (☃xxxxxxxxxxxxxxxxxxxx * (☃xxxxxxxxxxxxxxxxxxxx * 6.0 - 15.0) + 10.0);
                  if (☃xxxxxxxxxxxxxxxxxxx == 0 || ☃xxxxxxxxxxxxxxxxxxxx != ☃xx) {
                     ☃xx = ☃xxxxxxxxxxxxxxxxxxxx;
                     ☃xxx = this.field_76312_d[☃xxxxxxxxxxxxxx] + ☃xxxxxxxxxxxxxxxxxxxx;
                     ☃xxxx = this.field_76312_d[☃xxx] + ☃xxxxxxxxxxxxxxxxx;
                     ☃xxxxx = this.field_76312_d[☃xxx + 1] + ☃xxxxxxxxxxxxxxxxx;
                     ☃xxxxxx = this.field_76312_d[☃xxxxxxxxxxxxxx + 1] + ☃xxxxxxxxxxxxxxxxxxxx;
                     ☃xxxxxxx = this.field_76312_d[☃xxxxxx] + ☃xxxxxxxxxxxxxxxxx;
                     ☃xxxxxxxx = this.field_76312_d[☃xxxxxx + 1] + ☃xxxxxxxxxxxxxxxxx;
                     ☃xxxxxxxxx = this.func_76311_b(
                        ☃xxxxxxxxxxxxxxx,
                        this.func_76310_a(this.field_76312_d[☃xxxx], ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx),
                        this.func_76310_a(this.field_76312_d[☃xxxxxxx], ☃xxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx)
                     );
                     ☃xxxxxxxxxx = this.func_76311_b(
                        ☃xxxxxxxxxxxxxxx,
                        this.func_76310_a(this.field_76312_d[☃xxxxx], ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxx),
                        this.func_76310_a(this.field_76312_d[☃xxxxxxxx], ☃xxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxx)
                     );
                     ☃xxxxxxxxxxx = this.func_76311_b(
                        ☃xxxxxxxxxxxxxxx,
                        this.func_76310_a(this.field_76312_d[☃xxxx + 1], ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx - 1.0),
                        this.func_76310_a(this.field_76312_d[☃xxxxxxx + 1], ☃xxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx - 1.0)
                     );
                     ☃xxxxxxxxxxxx = this.func_76311_b(
                        ☃xxxxxxxxxxxxxxx,
                        this.func_76310_a(this.field_76312_d[☃xxxxx + 1], ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxx - 1.0),
                        this.func_76310_a(this.field_76312_d[☃xxxxxxxx + 1], ☃xxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxxxxx - 1.0, ☃xxxxxxxxxxxxxxxxx - 1.0)
                     );
                  }

                  double ☃xxxxxxxxxxxxxxxxxxxx = this.func_76311_b(☃xxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx);
                  double ☃xxxxxxxxxxxxxxxxxxxxx = this.func_76311_b(☃xxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
                  double ☃xxxxxxxxxxxxxxxxxxxxxx = this.func_76311_b(☃xxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxx);
                  int var10001 = ☃++;
                  ☃[var10001] += ☃xxxxxxxxxxxxxxxxxxxxxx * ☃x;
               }
            }
         }
      }
   }
}
