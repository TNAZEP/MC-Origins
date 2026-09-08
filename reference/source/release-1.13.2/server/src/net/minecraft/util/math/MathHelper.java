package net.minecraft.util.math;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import net.minecraft.util.Util;

public class MathHelper {
   public static final float field_180189_a = func_76129_c(2.0F);
   private static final float[] field_76144_a = Util.func_200696_a(new float[65536], var0x -> {
      for(int ☃ = 0; ☃ < var0x.length; ++☃) {
         var0x[☃] = (float)Math.sin((double)☃ * Math.PI * 2.0 / 65536.0);
      }
   });
   private static final Random field_188211_c = new Random();
   private static final int[] field_151242_b = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static final double field_181163_d = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] field_181164_e = new double[257];
   private static final double[] field_181165_f = new double[257];

   public static float func_76126_a(float var0) {
      return field_76144_a[(int)(☃ * 10430.378F) & 65535];
   }

   public static float func_76134_b(float var0) {
      return field_76144_a[(int)(☃ * 10430.378F + 16384.0F) & 65535];
   }

   public static float func_76129_c(float var0) {
      return (float)Math.sqrt((double)☃);
   }

   public static float func_76133_a(double var0) {
      return (float)Math.sqrt(☃);
   }

   public static int func_76141_d(float var0) {
      int ☃ = (int)☃;
      return ☃ < (float)☃ ? ☃ - 1 : ☃;
   }

   public static int func_76128_c(double var0) {
      int ☃ = (int)☃;
      return ☃ < (double)☃ ? ☃ - 1 : ☃;
   }

   public static long func_76124_d(double var0) {
      long ☃ = (long)☃;
      return ☃ < (double)☃ ? ☃ - 1L : ☃;
   }

   public static float func_76135_e(float var0) {
      return ☃ >= 0.0F ? ☃ : -☃;
   }

   public static int func_76130_a(int var0) {
      return ☃ >= 0 ? ☃ : -☃;
   }

   public static int func_76123_f(float var0) {
      int ☃ = (int)☃;
      return ☃ > (float)☃ ? ☃ + 1 : ☃;
   }

   public static int func_76143_f(double var0) {
      int ☃ = (int)☃;
      return ☃ > (double)☃ ? ☃ + 1 : ☃;
   }

   public static int func_76125_a(int var0, int var1, int var2) {
      if (☃ < ☃) {
         return ☃;
      } else {
         return ☃ > ☃ ? ☃ : ☃;
      }
   }

   public static float func_76131_a(float var0, float var1, float var2) {
      if (☃ < ☃) {
         return ☃;
      } else {
         return ☃ > ☃ ? ☃ : ☃;
      }
   }

   public static double func_151237_a(double var0, double var2, double var4) {
      if (☃ < ☃) {
         return ☃;
      } else {
         return ☃ > ☃ ? ☃ : ☃;
      }
   }

   public static double func_151238_b(double var0, double var2, double var4) {
      if (☃ < 0.0) {
         return ☃;
      } else {
         return ☃ > 1.0 ? ☃ : ☃ + (☃ - ☃) * ☃;
      }
   }

   public static double func_76132_a(double var0, double var2) {
      if (☃ < 0.0) {
         ☃ = -☃;
      }

      if (☃ < 0.0) {
         ☃ = -☃;
      }

      return ☃ > ☃ ? ☃ : ☃;
   }

   public static int func_76137_a(int var0, int var1) {
      return Math.floorDiv(☃, ☃);
   }

   public static int func_76136_a(Random var0, int var1, int var2) {
      return ☃ >= ☃ ? ☃ : ☃.nextInt(☃ - ☃ + 1) + ☃;
   }

   public static float func_151240_a(Random var0, float var1, float var2) {
      return ☃ >= ☃ ? ☃ : ☃.nextFloat() * (☃ - ☃) + ☃;
   }

   public static double func_82716_a(Random var0, double var1, double var3) {
      return ☃ >= ☃ ? ☃ : ☃.nextDouble() * (☃ - ☃) + ☃;
   }

   public static double func_76127_a(long[] var0) {
      long ☃ = 0L;

      for(long ☃x : ☃) {
         ☃ += ☃x;
      }

      return (double)☃ / (double)☃.length;
   }

   public static int func_180184_b(int var0, int var1) {
      return Math.floorMod(☃, ☃);
   }

   public static float func_76142_g(float var0) {
      ☃ %= 360.0F;
      if (☃ >= 180.0F) {
         ☃ -= 360.0F;
      }

      if (☃ < -180.0F) {
         ☃ += 360.0F;
      }

      return ☃;
   }

   public static double func_76138_g(double var0) {
      ☃ %= 360.0;
      if (☃ >= 180.0) {
         ☃ -= 360.0;
      }

      if (☃ < -180.0) {
         ☃ += 360.0;
      }

      return ☃;
   }

   public static float func_203302_c(float var0, float var1) {
      float ☃ = func_76142_g(☃ - ☃);
      return ☃ < 180.0F ? ☃ : ☃ - 360.0F;
   }

   public static float func_203301_d(float var0, float var1) {
      float ☃ = func_76142_g(☃ - ☃);
      return ☃ < 180.0F ? func_76135_e(☃) : func_76135_e(☃ - 360.0F);
   }

   public static float func_203300_b(float var0, float var1, float var2) {
      ☃ = func_76135_e(☃);
      return ☃ < ☃ ? func_76131_a(☃ + ☃, ☃, ☃) : func_76131_a(☃ - ☃, ☃, ☃);
   }

   public static float func_203303_c(float var0, float var1, float var2) {
      float ☃ = func_203302_c(☃, ☃);
      return func_203300_b(☃, ☃ + ☃, ☃);
   }

   public static int func_151236_b(int var0) {
      int ☃ = ☃ - 1;
      ☃ |= ☃ >> 1;
      ☃ |= ☃ >> 2;
      ☃ |= ☃ >> 4;
      ☃ |= ☃ >> 8;
      ☃ |= ☃ >> 16;
      return ☃ + 1;
   }

   private static boolean func_151235_d(int var0) {
      return ☃ != 0 && (☃ & ☃ - 1) == 0;
   }

   public static int func_151241_e(int var0) {
      ☃ = func_151235_d(☃) ? ☃ : func_151236_b(☃);
      return field_151242_b[(int)((long)☃ * 125613361L >> 27) & 31];
   }

   public static int func_151239_c(int var0) {
      return func_151241_e(☃) - (func_151235_d(☃) ? 0 : 1);
   }

   public static int func_154354_b(int var0, int var1) {
      if (☃ == 0) {
         return 0;
      } else if (☃ == 0) {
         return ☃;
      } else {
         if (☃ < 0) {
            ☃ *= -1;
         }

         int ☃ = ☃ % ☃;
         return ☃ == 0 ? ☃ : ☃ + ☃ - ☃;
      }
   }

   public static long func_180187_c(int var0, int var1, int var2) {
      long ☃ = (long)(☃ * 3129871) ^ (long)☃ * 116129781L ^ (long)☃;
      ☃ = ☃ * ☃ * 42317861L + ☃ * 11L;
      return ☃ >> 16;
   }

   public static UUID func_180182_a(Random var0) {
      long ☃ = ☃.nextLong() & -61441L | 16384L;
      long ☃x = ☃.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(☃, ☃x);
   }

   public static UUID func_188210_a() {
      return func_180182_a(field_188211_c);
   }

   public static double func_181160_c(double var0, double var2, double var4) {
      return (☃ - ☃) / (☃ - ☃);
   }

   public static double func_181159_b(double var0, double var2) {
      double ☃ = ☃ * ☃ + ☃ * ☃;
      if (Double.isNaN(☃)) {
         return Double.NaN;
      } else {
         boolean ☃ = ☃ < 0.0;
         if (☃) {
            ☃ = -☃;
         }

         boolean ☃ = ☃ < 0.0;
         if (☃) {
            ☃ = -☃;
         }

         boolean ☃ = ☃ > ☃;
         if (☃) {
            double ☃x = ☃;
            ☃ = ☃;
            ☃ = ☃x;
         }

         double ☃ = func_181161_i(☃);
         ☃ *= ☃;
         ☃ *= ☃;
         double ☃x = field_181163_d + ☃;
         int ☃xx = (int)Double.doubleToRawLongBits(☃x);
         double ☃xxx = field_181164_e[☃xx];
         double ☃xxxx = field_181165_f[☃xx];
         double ☃xxxxx = ☃x - field_181163_d;
         double ☃xxxxxx = ☃ * ☃xxxx - ☃ * ☃xxxxx;
         double ☃xxxxxxx = (6.0 + ☃xxxxxx * ☃xxxxxx) * ☃xxxxxx * 0.16666666666666666;
         double ☃xxxxxxxx = ☃xxx + ☃xxxxxxx;
         if (☃) {
            ☃xxxxxxxx = (Math.PI / 2) - ☃xxxxxxxx;
         }

         if (☃) {
            ☃xxxxxxxx = Math.PI - ☃xxxxxxxx;
         }

         if (☃) {
            ☃xxxxxxxx = -☃xxxxxxxx;
         }

         return ☃xxxxxxxx;
      }
   }

   public static double func_181161_i(double var0) {
      double ☃ = 0.5 * ☃;
      long ☃x = Double.doubleToRawLongBits(☃);
      ☃x = 6910469410427058090L - (☃x >> 1);
      ☃ = Double.longBitsToDouble(☃x);
      return ☃ * (1.5 - ☃ * ☃ * ☃);
   }

   public static int func_188208_f(int var0) {
      ☃ ^= ☃ >>> 16;
      ☃ *= -2048144789;
      ☃ ^= ☃ >>> 13;
      ☃ *= -1028477387;
      return ☃ ^ ☃ >>> 16;
   }

   public static int func_199093_a(int var0, int var1, IntPredicate var2) {
      int ☃ = ☃ - ☃;

      while(☃ > 0) {
         int ☃x = ☃ / 2;
         int ☃xx = ☃ + ☃x;
         if (☃.test(☃xx)) {
            ☃ = ☃x;
         } else {
            ☃ = ☃xx + 1;
            ☃ -= ☃x + 1;
         }
      }

      return ☃;
   }

   static {
      for(int ☃ = 0; ☃ < 257; ++☃) {
         double ☃x = (double)☃ / 256.0;
         double ☃xx = Math.asin(☃x);
         field_181165_f[☃] = Math.cos(☃xx);
         field_181164_e[☃] = ☃xx;
      }
   }
}
