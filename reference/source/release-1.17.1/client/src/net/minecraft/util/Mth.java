package net.minecraft.util;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import net.minecraft.Util;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.math.NumberUtils;

public class Mth {
   private static final int BIG_ENOUGH_INT = 1024;
   private static final float BIG_ENOUGH_FLOAT = 1024.0F;
   private static final long UUID_VERSION = 61440L;
   private static final long UUID_VERSION_TYPE_4 = 16384L;
   private static final long UUID_VARIANT = -4611686018427387904L;
   private static final long UUID_VARIANT_2 = Long.MIN_VALUE;
   public static final float PI = (float) Math.PI;
   public static final float HALF_PI = (float) (Math.PI / 2);
   public static final float TWO_PI = (float) (Math.PI * 2);
   public static final float DEG_TO_RAD = (float) (Math.PI / 180.0);
   public static final float RAD_TO_DEG = 180.0F / (float)Math.PI;
   public static final float EPSILON = 1.0E-5F;
   public static final float SQRT_OF_TWO = sqrt(2.0F);
   private static final float SIN_SCALE = 10430.378F;
   private static final float[] SIN = Util.make(new float[65536], var0x -> {
      for(int â˜ƒ = 0; â˜ƒ < var0x.length; ++â˜ƒ) {
         var0x[â˜ƒ] = (float)Math.sin((double)â˜ƒ * Math.PI * 2.0 / 65536.0);
      }
   });
   private static final Random RANDOM = new Random();
   private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static final double ONE_SIXTH = 0.16666666666666666;
   private static final int FRAC_EXP = 8;
   private static final int LUT_SIZE = 257;
   private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] ASIN_TAB = new double[257];
   private static final double[] COS_TAB = new double[257];

   public static float sin(float var0) {
      return SIN[(int)(â˜ƒ * 10430.378F) & 65535];
   }

   public static float cos(float var0) {
      return SIN[(int)(â˜ƒ * 10430.378F + 16384.0F) & 65535];
   }

   public static float sqrt(float var0) {
      return (float)Math.sqrt((double)â˜ƒ);
   }

   public static int floor(float var0) {
      int â˜ƒ = (int)â˜ƒ;
      return â˜ƒ < (float)â˜ƒ ? â˜ƒ - 1 : â˜ƒ;
   }

   public static int fastFloor(double var0) {
      return (int)(â˜ƒ + 1024.0) - 1024;
   }

   public static int floor(double var0) {
      int â˜ƒ = (int)â˜ƒ;
      return â˜ƒ < (double)â˜ƒ ? â˜ƒ - 1 : â˜ƒ;
   }

   public static long lfloor(double var0) {
      long â˜ƒ = (long)â˜ƒ;
      return â˜ƒ < (double)â˜ƒ ? â˜ƒ - 1L : â˜ƒ;
   }

   public static int absFloor(double var0) {
      return (int)(â˜ƒ >= 0.0 ? â˜ƒ : -â˜ƒ + 1.0);
   }

   public static float abs(float var0) {
      return Math.abs(â˜ƒ);
   }

   public static int abs(int var0) {
      return Math.abs(â˜ƒ);
   }

   public static int ceil(float var0) {
      int â˜ƒ = (int)â˜ƒ;
      return â˜ƒ > (float)â˜ƒ ? â˜ƒ + 1 : â˜ƒ;
   }

   public static int ceil(double var0) {
      int â˜ƒ = (int)â˜ƒ;
      return â˜ƒ > (double)â˜ƒ ? â˜ƒ + 1 : â˜ƒ;
   }

   public static byte clamp(byte var0, byte var1, byte var2) {
      if (â˜ƒ < â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
      }
   }

   public static int clamp(int var0, int var1, int var2) {
      if (â˜ƒ < â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
      }
   }

   public static long clamp(long var0, long var2, long var4) {
      if (â˜ƒ < â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
      }
   }

   public static float clamp(float var0, float var1, float var2) {
      if (â˜ƒ < â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
      }
   }

   public static double clamp(double var0, double var2, double var4) {
      if (â˜ƒ < â˜ƒ) {
         return â˜ƒ;
      } else {
         return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
      }
   }

   public static double clampedLerp(double var0, double var2, double var4) {
      if (â˜ƒ < 0.0) {
         return â˜ƒ;
      } else {
         return â˜ƒ > 1.0 ? â˜ƒ : lerp(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static float clampedLerp(float var0, float var1, float var2) {
      if (â˜ƒ < 0.0F) {
         return â˜ƒ;
      } else {
         return â˜ƒ > 1.0F ? â˜ƒ : lerp(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static double absMax(double var0, double var2) {
      if (â˜ƒ < 0.0) {
         â˜ƒ = -â˜ƒ;
      }

      if (â˜ƒ < 0.0) {
         â˜ƒ = -â˜ƒ;
      }

      return â˜ƒ > â˜ƒ ? â˜ƒ : â˜ƒ;
   }

   public static int intFloorDiv(int var0, int var1) {
      return Math.floorDiv(â˜ƒ, â˜ƒ);
   }

   public static int nextInt(Random var0, int var1, int var2) {
      return â˜ƒ >= â˜ƒ ? â˜ƒ : â˜ƒ.nextInt(â˜ƒ - â˜ƒ + 1) + â˜ƒ;
   }

   public static float nextFloat(Random var0, float var1, float var2) {
      return â˜ƒ >= â˜ƒ ? â˜ƒ : â˜ƒ.nextFloat() * (â˜ƒ - â˜ƒ) + â˜ƒ;
   }

   public static double nextDouble(Random var0, double var1, double var3) {
      return â˜ƒ >= â˜ƒ ? â˜ƒ : â˜ƒ.nextDouble() * (â˜ƒ - â˜ƒ) + â˜ƒ;
   }

   public static double average(long[] var0) {
      long â˜ƒ = 0L;

      for(long â˜ƒx : â˜ƒ) {
         â˜ƒ += â˜ƒx;
      }

      return (double)â˜ƒ / (double)â˜ƒ.length;
   }

   public static boolean equal(float var0, float var1) {
      return Math.abs(â˜ƒ - â˜ƒ) < 1.0E-5F;
   }

   public static boolean equal(double var0, double var2) {
      return Math.abs(â˜ƒ - â˜ƒ) < 1.0E-5F;
   }

   public static int positiveModulo(int var0, int var1) {
      return Math.floorMod(â˜ƒ, â˜ƒ);
   }

   public static float positiveModulo(float var0, float var1) {
      return (â˜ƒ % â˜ƒ + â˜ƒ) % â˜ƒ;
   }

   public static double positiveModulo(double var0, double var2) {
      return (â˜ƒ % â˜ƒ + â˜ƒ) % â˜ƒ;
   }

   public static int wrapDegrees(int var0) {
      int â˜ƒ = â˜ƒ % 360;
      if (â˜ƒ >= 180) {
         â˜ƒ -= 360;
      }

      if (â˜ƒ < -180) {
         â˜ƒ += 360;
      }

      return â˜ƒ;
   }

   public static float wrapDegrees(float var0) {
      float â˜ƒ = â˜ƒ % 360.0F;
      if (â˜ƒ >= 180.0F) {
         â˜ƒ -= 360.0F;
      }

      if (â˜ƒ < -180.0F) {
         â˜ƒ += 360.0F;
      }

      return â˜ƒ;
   }

   public static double wrapDegrees(double var0) {
      double â˜ƒ = â˜ƒ % 360.0;
      if (â˜ƒ >= 180.0) {
         â˜ƒ -= 360.0;
      }

      if (â˜ƒ < -180.0) {
         â˜ƒ += 360.0;
      }

      return â˜ƒ;
   }

   public static float degreesDifference(float var0, float var1) {
      return wrapDegrees(â˜ƒ - â˜ƒ);
   }

   public static float degreesDifferenceAbs(float var0, float var1) {
      return abs(degreesDifference(â˜ƒ, â˜ƒ));
   }

   public static float rotateIfNecessary(float var0, float var1, float var2) {
      float â˜ƒ = degreesDifference(â˜ƒ, â˜ƒ);
      float â˜ƒx = clamp(â˜ƒ, -â˜ƒ, â˜ƒ);
      return â˜ƒ - â˜ƒx;
   }

   public static float approach(float var0, float var1, float var2) {
      â˜ƒ = abs(â˜ƒ);
      return â˜ƒ < â˜ƒ ? clamp(â˜ƒ + â˜ƒ, â˜ƒ, â˜ƒ) : clamp(â˜ƒ - â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static float approachDegrees(float var0, float var1, float var2) {
      float â˜ƒ = degreesDifference(â˜ƒ, â˜ƒ);
      return approach(â˜ƒ, â˜ƒ + â˜ƒ, â˜ƒ);
   }

   public static int getInt(String var0, int var1) {
      return NumberUtils.toInt(â˜ƒ, â˜ƒ);
   }

   public static int getInt(String var0, int var1, int var2) {
      return Math.max(â˜ƒ, getInt(â˜ƒ, â˜ƒ));
   }

   public static double getDouble(String var0, double var1) {
      try {
         return Double.parseDouble(â˜ƒ);
      } catch (Throwable var4) {
         return â˜ƒ;
      }
   }

   public static double getDouble(String var0, double var1, double var3) {
      return Math.max(â˜ƒ, getDouble(â˜ƒ, â˜ƒ));
   }

   public static int smallestEncompassingPowerOfTwo(int var0) {
      int â˜ƒ = â˜ƒ - 1;
      â˜ƒ |= â˜ƒ >> 1;
      â˜ƒ |= â˜ƒ >> 2;
      â˜ƒ |= â˜ƒ >> 4;
      â˜ƒ |= â˜ƒ >> 8;
      â˜ƒ |= â˜ƒ >> 16;
      return â˜ƒ + 1;
   }

   public static boolean isPowerOfTwo(int var0) {
      return â˜ƒ != 0 && (â˜ƒ & â˜ƒ - 1) == 0;
   }

   public static int ceillog2(int var0) {
      â˜ƒ = isPowerOfTwo(â˜ƒ) ? â˜ƒ : smallestEncompassingPowerOfTwo(â˜ƒ);
      return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)((long)â˜ƒ * 125613361L >> 27) & 31];
   }

   public static int log2(int var0) {
      return ceillog2(â˜ƒ) - (isPowerOfTwo(â˜ƒ) ? 0 : 1);
   }

   public static int color(float var0, float var1, float var2) {
      return color(floor(â˜ƒ * 255.0F), floor(â˜ƒ * 255.0F), floor(â˜ƒ * 255.0F));
   }

   public static int color(int var0, int var1, int var2) {
      int var3 = (â˜ƒ << 8) + â˜ƒ;
      return (var3 << 8) + â˜ƒ;
   }

   public static int colorMultiply(int var0, int var1) {
      int â˜ƒ = (â˜ƒ & 0xFF0000) >> 16;
      int â˜ƒx = (â˜ƒ & 0xFF0000) >> 16;
      int â˜ƒxx = (â˜ƒ & 0xFF00) >> 8;
      int â˜ƒxxx = (â˜ƒ & 0xFF00) >> 8;
      int â˜ƒxxxx = (â˜ƒ & 0xFF) >> 0;
      int â˜ƒxxxxx = (â˜ƒ & 0xFF) >> 0;
      int â˜ƒxxxxxx = (int)((float)â˜ƒ * (float)â˜ƒx / 255.0F);
      int â˜ƒxxxxxxx = (int)((float)â˜ƒxx * (float)â˜ƒxxx / 255.0F);
      int â˜ƒxxxxxxxx = (int)((float)â˜ƒxxxx * (float)â˜ƒxxxxx / 255.0F);
      return â˜ƒ & 0xFF000000 | â˜ƒxxxxxx << 16 | â˜ƒxxxxxxx << 8 | â˜ƒxxxxxxxx;
   }

   public static int colorMultiply(int var0, float var1, float var2, float var3) {
      int â˜ƒ = (â˜ƒ & 0xFF0000) >> 16;
      int â˜ƒx = (â˜ƒ & 0xFF00) >> 8;
      int â˜ƒxx = (â˜ƒ & 0xFF) >> 0;
      int â˜ƒxxx = (int)((float)â˜ƒ * â˜ƒ);
      int â˜ƒxxxx = (int)((float)â˜ƒx * â˜ƒ);
      int â˜ƒxxxxx = (int)((float)â˜ƒxx * â˜ƒ);
      return â˜ƒ & 0xFF000000 | â˜ƒxxx << 16 | â˜ƒxxxx << 8 | â˜ƒxxxxx;
   }

   public static float frac(float var0) {
      return â˜ƒ - (float)floor(â˜ƒ);
   }

   public static double frac(double var0) {
      return â˜ƒ - (double)lfloor(â˜ƒ);
   }

   public static Vec3 catmullRomSplinePos(Vec3 var0, Vec3 var1, Vec3 var2, Vec3 var3, double var4) {
      double â˜ƒ = ((-â˜ƒ + 2.0) * â˜ƒ - 1.0) * â˜ƒ * 0.5;
      double â˜ƒx = ((3.0 * â˜ƒ - 5.0) * â˜ƒ * â˜ƒ + 2.0) * 0.5;
      double â˜ƒxx = ((-3.0 * â˜ƒ + 4.0) * â˜ƒ + 1.0) * â˜ƒ * 0.5;
      double â˜ƒxxx = (â˜ƒ - 1.0) * â˜ƒ * â˜ƒ * 0.5;
      return new Vec3(
         â˜ƒ.x * â˜ƒ + â˜ƒ.x * â˜ƒx + â˜ƒ.x * â˜ƒxx + â˜ƒ.x * â˜ƒxxx,
         â˜ƒ.y * â˜ƒ + â˜ƒ.y * â˜ƒx + â˜ƒ.y * â˜ƒxx + â˜ƒ.y * â˜ƒxxx,
         â˜ƒ.z * â˜ƒ + â˜ƒ.z * â˜ƒx + â˜ƒ.z * â˜ƒxx + â˜ƒ.z * â˜ƒxxx
      );
   }

   public static long getSeed(Vec3i var0) {
      return getSeed(â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ());
   }

   public static long getSeed(int var0, int var1, int var2) {
      long â˜ƒ = (long)(â˜ƒ * 3129871) ^ (long)â˜ƒ * 116129781L ^ (long)â˜ƒ;
      â˜ƒ = â˜ƒ * â˜ƒ * 42317861L + â˜ƒ * 11L;
      return â˜ƒ >> 16;
   }

   public static UUID createInsecureUUID(Random var0) {
      long â˜ƒ = â˜ƒ.nextLong() & -61441L | 16384L;
      long â˜ƒx = â˜ƒ.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(â˜ƒ, â˜ƒx);
   }

   public static UUID createInsecureUUID() {
      return createInsecureUUID(RANDOM);
   }

   public static double inverseLerp(double var0, double var2, double var4) {
      return (â˜ƒ - â˜ƒ) / (â˜ƒ - â˜ƒ);
   }

   public static boolean rayIntersectsAABB(Vec3 var0, Vec3 var1, AABB var2) {
      double â˜ƒ = (â˜ƒ.minX + â˜ƒ.maxX) * 0.5;
      double â˜ƒx = (â˜ƒ.maxX - â˜ƒ.minX) * 0.5;
      double â˜ƒxx = â˜ƒ.x - â˜ƒ;
      if (Math.abs(â˜ƒxx) > â˜ƒx && â˜ƒxx * â˜ƒ.x >= 0.0) {
         return false;
      } else {
         double â˜ƒ = (â˜ƒ.minY + â˜ƒ.maxY) * 0.5;
         double â˜ƒx = (â˜ƒ.maxY - â˜ƒ.minY) * 0.5;
         double â˜ƒxx = â˜ƒ.y - â˜ƒ;
         if (Math.abs(â˜ƒxx) > â˜ƒx && â˜ƒxx * â˜ƒ.y >= 0.0) {
            return false;
         } else {
            double â˜ƒ = (â˜ƒ.minZ + â˜ƒ.maxZ) * 0.5;
            double â˜ƒx = (â˜ƒ.maxZ - â˜ƒ.minZ) * 0.5;
            double â˜ƒxx = â˜ƒ.z - â˜ƒ;
            if (Math.abs(â˜ƒxx) > â˜ƒx && â˜ƒxx * â˜ƒ.z >= 0.0) {
               return false;
            } else {
               double â˜ƒ = Math.abs(â˜ƒ.x);
               double â˜ƒx = Math.abs(â˜ƒ.y);
               double â˜ƒxx = Math.abs(â˜ƒ.z);
               double â˜ƒxxx = â˜ƒ.y * â˜ƒxx - â˜ƒ.z * â˜ƒxx;
               if (Math.abs(â˜ƒxxx) > â˜ƒx * â˜ƒxx + â˜ƒx * â˜ƒx) {
                  return false;
               } else {
                  â˜ƒxxx = â˜ƒ.z * â˜ƒxx - â˜ƒ.x * â˜ƒxx;
                  if (Math.abs(â˜ƒxxx) > â˜ƒx * â˜ƒxx + â˜ƒx * â˜ƒ) {
                     return false;
                  } else {
                     â˜ƒxxx = â˜ƒ.x * â˜ƒxx - â˜ƒ.y * â˜ƒxx;
                     return Math.abs(â˜ƒxxx) < â˜ƒx * â˜ƒx + â˜ƒx * â˜ƒ;
                  }
               }
            }
         }
      }
   }

   public static double atan2(double var0, double var2) {
      double â˜ƒ = â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ;
      if (Double.isNaN(â˜ƒ)) {
         return Double.NaN;
      } else {
         boolean â˜ƒ = â˜ƒ < 0.0;
         if (â˜ƒ) {
            â˜ƒ = -â˜ƒ;
         }

         boolean â˜ƒ = â˜ƒ < 0.0;
         if (â˜ƒ) {
            â˜ƒ = -â˜ƒ;
         }

         boolean â˜ƒ = â˜ƒ > â˜ƒ;
         if (â˜ƒ) {
            double â˜ƒx = â˜ƒ;
            â˜ƒ = â˜ƒ;
            â˜ƒ = â˜ƒx;
         }

         double â˜ƒ = fastInvSqrt(â˜ƒ);
         â˜ƒ *= â˜ƒ;
         â˜ƒ *= â˜ƒ;
         double â˜ƒx = FRAC_BIAS + â˜ƒ;
         int â˜ƒxx = (int)Double.doubleToRawLongBits(â˜ƒx);
         double â˜ƒxxx = ASIN_TAB[â˜ƒxx];
         double â˜ƒxxxx = COS_TAB[â˜ƒxx];
         double â˜ƒxxxxx = â˜ƒx - FRAC_BIAS;
         double â˜ƒxxxxxx = â˜ƒ * â˜ƒxxxx - â˜ƒ * â˜ƒxxxxx;
         double â˜ƒxxxxxxx = (6.0 + â˜ƒxxxxxx * â˜ƒxxxxxx) * â˜ƒxxxxxx * 0.16666666666666666;
         double â˜ƒxxxxxxxx = â˜ƒxxx + â˜ƒxxxxxxx;
         if (â˜ƒ) {
            â˜ƒxxxxxxxx = (Math.PI / 2) - â˜ƒxxxxxxxx;
         }

         if (â˜ƒ) {
            â˜ƒxxxxxxxx = Math.PI - â˜ƒxxxxxxxx;
         }

         if (â˜ƒ) {
            â˜ƒxxxxxxxx = -â˜ƒxxxxxxxx;
         }

         return â˜ƒxxxxxxxx;
      }
   }

   public static float fastInvSqrt(float var0) {
      float â˜ƒ = 0.5F * â˜ƒ;
      int â˜ƒx = Float.floatToIntBits(â˜ƒ);
      â˜ƒx = 1597463007 - (â˜ƒx >> 1);
      â˜ƒ = Float.intBitsToFloat(â˜ƒx);
      return â˜ƒ * (1.5F - â˜ƒ * â˜ƒ * â˜ƒ);
   }

   public static double fastInvSqrt(double var0) {
      double â˜ƒ = 0.5 * â˜ƒ;
      long â˜ƒx = Double.doubleToRawLongBits(â˜ƒ);
      â˜ƒx = 6910469410427058090L - (â˜ƒx >> 1);
      â˜ƒ = Double.longBitsToDouble(â˜ƒx);
      return â˜ƒ * (1.5 - â˜ƒ * â˜ƒ * â˜ƒ);
   }

   public static float fastInvCubeRoot(float var0) {
      int â˜ƒ = Float.floatToIntBits(â˜ƒ);
      â˜ƒ = 1419967116 - â˜ƒ / 3;
      float â˜ƒx = Float.intBitsToFloat(â˜ƒ);
      â˜ƒx = 0.6666667F * â˜ƒx + 1.0F / (3.0F * â˜ƒx * â˜ƒx * â˜ƒ);
      return 0.6666667F * â˜ƒx + 1.0F / (3.0F * â˜ƒx * â˜ƒx * â˜ƒ);
   }

   public static int hsvToRgb(float var0, float var1, float var2) {
      int â˜ƒxxx = (int)(â˜ƒ * 6.0F) % 6;
      float â˜ƒxxxx = â˜ƒ * 6.0F - (float)â˜ƒxxx;
      float â˜ƒxxxxx = â˜ƒ * (1.0F - â˜ƒ);
      float â˜ƒxxxxxx = â˜ƒ * (1.0F - â˜ƒxxxx * â˜ƒ);
      float â˜ƒxxxxxxx = â˜ƒ * (1.0F - (1.0F - â˜ƒxxxx) * â˜ƒ);
      float â˜ƒ;
      float â˜ƒx;
      float â˜ƒxx;
      switch(â˜ƒxxx) {
         case 0:
            â˜ƒ = â˜ƒ;
            â˜ƒx = â˜ƒxxxxxxx;
            â˜ƒxx = â˜ƒxxxxx;
            break;
         case 1:
            â˜ƒ = â˜ƒxxxxxx;
            â˜ƒx = â˜ƒ;
            â˜ƒxx = â˜ƒxxxxx;
            break;
         case 2:
            â˜ƒ = â˜ƒxxxxx;
            â˜ƒx = â˜ƒ;
            â˜ƒxx = â˜ƒxxxxxxx;
            break;
         case 3:
            â˜ƒ = â˜ƒxxxxx;
            â˜ƒx = â˜ƒxxxxxx;
            â˜ƒxx = â˜ƒ;
            break;
         case 4:
            â˜ƒ = â˜ƒxxxxxxx;
            â˜ƒx = â˜ƒxxxxx;
            â˜ƒxx = â˜ƒ;
            break;
         case 5:
            â˜ƒ = â˜ƒ;
            â˜ƒx = â˜ƒxxxxx;
            â˜ƒxx = â˜ƒxxxxxx;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + â˜ƒ + ", " + â˜ƒ + ", " + â˜ƒ);
      }

      int â˜ƒ = clamp((int)(â˜ƒ * 255.0F), 0, 255);
      int â˜ƒx = clamp((int)(â˜ƒx * 255.0F), 0, 255);
      int â˜ƒxx = clamp((int)(â˜ƒxx * 255.0F), 0, 255);
      return â˜ƒ << 16 | â˜ƒx << 8 | â˜ƒxx;
   }

   public static int murmurHash3Mixer(int var0) {
      â˜ƒ ^= â˜ƒ >>> 16;
      â˜ƒ *= -2048144789;
      â˜ƒ ^= â˜ƒ >>> 13;
      â˜ƒ *= -1028477387;
      return â˜ƒ ^ â˜ƒ >>> 16;
   }

   public static long murmurHash3Mixer(long var0) {
      â˜ƒ ^= â˜ƒ >>> 33;
      â˜ƒ *= -49064778989728563L;
      â˜ƒ ^= â˜ƒ >>> 33;
      â˜ƒ *= -4265267296055464877L;
      return â˜ƒ ^ â˜ƒ >>> 33;
   }

   public static double[] cumulativeSum(double... var0) {
      float â˜ƒ = 0.0F;

      for(double â˜ƒx : â˜ƒ) {
         â˜ƒ = (float)((double)â˜ƒ + â˜ƒx);
      }

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx] /= (double)â˜ƒ;
      }

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx] += â˜ƒx == 0 ? 0.0 : â˜ƒ[â˜ƒx - 1];
      }

      return â˜ƒ;
   }

   public static int getRandomForDistributionIntegral(Random var0, double[] var1) {
      double â˜ƒ = â˜ƒ.nextDouble();

      for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.length; ++â˜ƒx) {
         if (â˜ƒ < â˜ƒ[â˜ƒx]) {
            return â˜ƒx;
         }
      }

      return â˜ƒ.length;
   }

   public static double[] binNormalDistribution(double var0, double var2, double var4, int var6, int var7) {
      double[] â˜ƒ = new double[â˜ƒ - â˜ƒ + 1];
      int â˜ƒx = 0;

      for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
         â˜ƒ[â˜ƒx] = Math.max(0.0, â˜ƒ * StrictMath.exp(-((double)â˜ƒxx - â˜ƒ) * ((double)â˜ƒxx - â˜ƒ) / (2.0 * â˜ƒ * â˜ƒ)));
         ++â˜ƒx;
      }

      return â˜ƒ;
   }

   public static double[] binBiModalNormalDistribution(double var0, double var2, double var4, double var6, double var8, double var10, int var12, int var13) {
      double[] â˜ƒ = new double[â˜ƒ - â˜ƒ + 1];
      int â˜ƒx = 0;

      for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
         â˜ƒ[â˜ƒx] = Math.max(
            0.0,
            â˜ƒ * StrictMath.exp(-((double)â˜ƒxx - â˜ƒ) * ((double)â˜ƒxx - â˜ƒ) / (2.0 * â˜ƒ * â˜ƒ))
               + â˜ƒ * StrictMath.exp(-((double)â˜ƒxx - â˜ƒ) * ((double)â˜ƒxx - â˜ƒ) / (2.0 * â˜ƒ * â˜ƒ))
         );
         ++â˜ƒx;
      }

      return â˜ƒ;
   }

   public static double[] binLogDistribution(double var0, double var2, int var4, int var5) {
      double[] â˜ƒ = new double[â˜ƒ - â˜ƒ + 1];
      int â˜ƒx = 0;

      for(int â˜ƒxx = â˜ƒ; â˜ƒxx <= â˜ƒ; ++â˜ƒxx) {
         â˜ƒ[â˜ƒx] = Math.max(â˜ƒ * StrictMath.log((double)â˜ƒxx) + â˜ƒ, 0.0);
         ++â˜ƒx;
      }

      return â˜ƒ;
   }

   public static int binarySearch(int var0, int var1, IntPredicate var2) {
      int â˜ƒ = â˜ƒ - â˜ƒ;

      while(â˜ƒ > 0) {
         int â˜ƒx = â˜ƒ / 2;
         int â˜ƒxx = â˜ƒ + â˜ƒx;
         if (â˜ƒ.test(â˜ƒxx)) {
            â˜ƒ = â˜ƒx;
         } else {
            â˜ƒ = â˜ƒxx + 1;
            â˜ƒ -= â˜ƒx + 1;
         }
      }

      return â˜ƒ;
   }

   public static float lerp(float var0, float var1, float var2) {
      return â˜ƒ + â˜ƒ * (â˜ƒ - â˜ƒ);
   }

   public static double lerp(double var0, double var2, double var4) {
      return â˜ƒ + â˜ƒ * (â˜ƒ - â˜ƒ);
   }

   public static double lerp2(double var0, double var2, double var4, double var6, double var8, double var10) {
      return lerp(â˜ƒ, lerp(â˜ƒ, â˜ƒ, â˜ƒ), lerp(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static double lerp3(
      double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16, double var18, double var20
   ) {
      return lerp(â˜ƒ, lerp2(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ), lerp2(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static double smoothstep(double var0) {
      return â˜ƒ * â˜ƒ * â˜ƒ * (â˜ƒ * (â˜ƒ * 6.0 - 15.0) + 10.0);
   }

   public static double smoothstepDerivative(double var0) {
      return 30.0 * â˜ƒ * â˜ƒ * (â˜ƒ - 1.0) * (â˜ƒ - 1.0);
   }

   public static int sign(double var0) {
      if (â˜ƒ == 0.0) {
         return 0;
      } else {
         return â˜ƒ > 0.0 ? 1 : -1;
      }
   }

   public static float rotLerp(float var0, float var1, float var2) {
      return â˜ƒ + â˜ƒ * wrapDegrees(â˜ƒ - â˜ƒ);
   }

   public static float diffuseLight(float var0, float var1, float var2) {
      return Math.min(â˜ƒ * â˜ƒ * 0.6F + â˜ƒ * â˜ƒ * ((3.0F + â˜ƒ) / 4.0F) + â˜ƒ * â˜ƒ * 0.8F, 1.0F);
   }

   @Deprecated
   public static float rotlerp(float var0, float var1, float var2) {
      float â˜ƒ = â˜ƒ - â˜ƒ;

      while(â˜ƒ < -180.0F) {
         â˜ƒ += 360.0F;
      }

      while(â˜ƒ >= 180.0F) {
         â˜ƒ -= 360.0F;
      }

      return â˜ƒ + â˜ƒ * â˜ƒ;
   }

   @Deprecated
   public static float rotWrap(double var0) {
      while(â˜ƒ >= 180.0) {
         â˜ƒ -= 360.0;
      }

      while(â˜ƒ < -180.0) {
         â˜ƒ += 360.0;
      }

      return (float)â˜ƒ;
   }

   public static float triangleWave(float var0, float var1) {
      return (Math.abs(â˜ƒ % â˜ƒ - â˜ƒ * 0.5F) - â˜ƒ * 0.25F) / (â˜ƒ * 0.25F);
   }

   public static float square(float var0) {
      return â˜ƒ * â˜ƒ;
   }

   public static double square(double var0) {
      return â˜ƒ * â˜ƒ;
   }

   public static int square(int var0) {
      return â˜ƒ * â˜ƒ;
   }

   public static double clampedMap(double var0, double var2, double var4, double var6, double var8) {
      return clampedLerp(â˜ƒ, â˜ƒ, inverseLerp(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static double map(double var0, double var2, double var4, double var6, double var8) {
      return lerp(inverseLerp(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ, â˜ƒ);
   }

   public static double wobble(double var0) {
      return â˜ƒ + (2.0 * new Random((long)floor(â˜ƒ * 3000.0)).nextDouble() - 1.0) * 1.0E-7 / 2.0;
   }

   public static int roundToward(int var0, int var1) {
      return (â˜ƒ + â˜ƒ - 1) / â˜ƒ * â˜ƒ;
   }

   public static int randomBetweenInclusive(Random var0, int var1, int var2) {
      return â˜ƒ.nextInt(â˜ƒ - â˜ƒ + 1) + â˜ƒ;
   }

   public static float randomBetween(Random var0, float var1, float var2) {
      return â˜ƒ.nextFloat() * (â˜ƒ - â˜ƒ) + â˜ƒ;
   }

   public static float normal(Random var0, float var1, float var2) {
      return â˜ƒ + (float)â˜ƒ.nextGaussian() * â˜ƒ;
   }

   public static double length(int var0, double var1, int var3) {
      return Math.sqrt((double)(â˜ƒ * â˜ƒ) + â˜ƒ * â˜ƒ + (double)(â˜ƒ * â˜ƒ));
   }

   static {
      for(int â˜ƒ = 0; â˜ƒ < 257; ++â˜ƒ) {
         double â˜ƒx = (double)â˜ƒ / 256.0;
         double â˜ƒxx = Math.asin(â˜ƒx);
         COS_TAB[â˜ƒ] = Math.cos(â˜ƒxx);
         ASIN_TAB[â˜ƒ] = â˜ƒxx;
      }
   }
}
