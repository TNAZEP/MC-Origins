package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;

public class SimplexNoise {
   protected static final int[][] GRADIENT = new int[][]{
      {1, 1, 0},
      {-1, 1, 0},
      {1, -1, 0},
      {-1, -1, 0},
      {1, 0, 1},
      {-1, 0, 1},
      {1, 0, -1},
      {-1, 0, -1},
      {0, 1, 1},
      {0, -1, 1},
      {0, 1, -1},
      {0, -1, -1},
      {1, 1, 0},
      {0, -1, 1},
      {-1, 1, 0},
      {0, -1, -1}
   };
   private static final double SQRT_3 = Math.sqrt(3.0);
   private static final double F2 = 0.5 * (SQRT_3 - 1.0);
   private static final double G2 = (3.0 - SQRT_3) / 6.0;
   private final int[] p = new int[512];
   public final double xo;
   public final double yo;
   public final double zo;

   public SimplexNoise(RandomSource var1) {
      this.xo = â˜ƒ.nextDouble() * 256.0;
      this.yo = â˜ƒ.nextDouble() * 256.0;
      this.zo = â˜ƒ.nextDouble() * 256.0;
      int â˜ƒ = 0;

      while(â˜ƒ < 256) {
         this.p[â˜ƒ] = â˜ƒ++;
      }

      for(int â˜ƒx = 0; â˜ƒx < 256; ++â˜ƒx) {
         int â˜ƒxx = â˜ƒ.nextInt(256 - â˜ƒx);
         int â˜ƒxxx = this.p[â˜ƒx];
         this.p[â˜ƒx] = this.p[â˜ƒxx + â˜ƒx];
         this.p[â˜ƒxx + â˜ƒx] = â˜ƒxxx;
      }
   }

   private int p(int var1) {
      return this.p[â˜ƒ & 0xFF];
   }

   protected static double dot(int[] var0, double var1, double var3, double var5) {
      return (double)â˜ƒ[0] * â˜ƒ + (double)â˜ƒ[1] * â˜ƒ + (double)â˜ƒ[2] * â˜ƒ;
   }

   private double getCornerNoise3D(int var1, double var2, double var4, double var6, double var8) {
      double â˜ƒx = â˜ƒ - â˜ƒ * â˜ƒ - â˜ƒ * â˜ƒ - â˜ƒ * â˜ƒ;
      double â˜ƒ;
      if (â˜ƒx < 0.0) {
         â˜ƒ = 0.0;
      } else {
         â˜ƒx *= â˜ƒx;
         â˜ƒ = â˜ƒx * â˜ƒx * dot(GRADIENT[â˜ƒ], â˜ƒ, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   public double getValue(double var1, double var3) {
      double â˜ƒxx = (â˜ƒ + â˜ƒ) * F2;
      int â˜ƒxxx = Mth.floor(â˜ƒ + â˜ƒxx);
      int â˜ƒxxxx = Mth.floor(â˜ƒ + â˜ƒxx);
      double â˜ƒxxxxx = (double)(â˜ƒxxx + â˜ƒxxxx) * G2;
      double â˜ƒxxxxxx = (double)â˜ƒxxx - â˜ƒxxxxx;
      double â˜ƒxxxxxxx = (double)â˜ƒxxxx - â˜ƒxxxxx;
      double â˜ƒxxxxxxxx = â˜ƒ - â˜ƒxxxxxx;
      double â˜ƒxxxxxxxxx = â˜ƒ - â˜ƒxxxxxxx;
      int â˜ƒ;
      int â˜ƒx;
      if (â˜ƒxxxxxxxx > â˜ƒxxxxxxxxx) {
         â˜ƒ = 1;
         â˜ƒx = 0;
      } else {
         â˜ƒ = 0;
         â˜ƒx = 1;
      }

      double â˜ƒ = â˜ƒxxxxxxxx - (double)â˜ƒ + G2;
      double â˜ƒx = â˜ƒxxxxxxxxx - (double)â˜ƒx + G2;
      double â˜ƒxx = â˜ƒxxxxxxxx - 1.0 + 2.0 * G2;
      double â˜ƒxxx = â˜ƒxxxxxxxxx - 1.0 + 2.0 * G2;
      int â˜ƒxxxx = â˜ƒxxx & 0xFF;
      int â˜ƒxxxxx = â˜ƒxxxx & 0xFF;
      int â˜ƒxxxxxx = this.p(â˜ƒxxxx + this.p(â˜ƒxxxxx)) % 12;
      int â˜ƒxxxxxxx = this.p(â˜ƒxxxx + â˜ƒ + this.p(â˜ƒxxxxx + â˜ƒx)) % 12;
      int â˜ƒxxxxxxxx = this.p(â˜ƒxxxx + 1 + this.p(â˜ƒxxxxx + 1)) % 12;
      double â˜ƒxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, 0.0, 0.5);
      double â˜ƒxxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxxx, â˜ƒ, â˜ƒx, 0.0, 0.5);
      double â˜ƒxxxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxxxx, â˜ƒxx, â˜ƒxxx, 0.0, 0.5);
      return 70.0 * (â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxx);
   }

   public double getValue(double var1, double var3, double var5) {
      double â˜ƒxxxxxx = 0.3333333333333333;
      double â˜ƒxxxxxxx = (â˜ƒ + â˜ƒ + â˜ƒ) * 0.3333333333333333;
      int â˜ƒxxxxxxxx = Mth.floor(â˜ƒ + â˜ƒxxxxxxx);
      int â˜ƒxxxxxxxxx = Mth.floor(â˜ƒ + â˜ƒxxxxxxx);
      int â˜ƒxxxxxxxxxx = Mth.floor(â˜ƒ + â˜ƒxxxxxxx);
      double â˜ƒxxxxxxxxxxx = 0.16666666666666666;
      double â˜ƒxxxxxxxxxxxx = (double)(â˜ƒxxxxxxxx + â˜ƒxxxxxxxxx + â˜ƒxxxxxxxxxx) * 0.16666666666666666;
      double â˜ƒxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxx - â˜ƒxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxx - â˜ƒxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxxxx - â˜ƒxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxxx = â˜ƒ - â˜ƒxxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ - â˜ƒxxxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒ - â˜ƒxxxxxxxxxxxxxxx;
      int â˜ƒ;
      int â˜ƒx;
      int â˜ƒxx;
      int â˜ƒxxx;
      int â˜ƒxxxx;
      int â˜ƒxxxxx;
      if (â˜ƒxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxx) {
         if (â˜ƒxxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxxx) {
            â˜ƒ = 1;
            â˜ƒx = 0;
            â˜ƒxx = 0;
            â˜ƒxxx = 1;
            â˜ƒxxxx = 1;
            â˜ƒxxxxx = 0;
         } else if (â˜ƒxxxxxxxxxxxxxxxx >= â˜ƒxxxxxxxxxxxxxxxxxx) {
            â˜ƒ = 1;
            â˜ƒx = 0;
            â˜ƒxx = 0;
            â˜ƒxxx = 1;
            â˜ƒxxxx = 0;
            â˜ƒxxxxx = 1;
         } else {
            â˜ƒ = 0;
            â˜ƒx = 0;
            â˜ƒxx = 1;
            â˜ƒxxx = 1;
            â˜ƒxxxx = 0;
            â˜ƒxxxxx = 1;
         }
      } else if (â˜ƒxxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxxxxx) {
         â˜ƒ = 0;
         â˜ƒx = 0;
         â˜ƒxx = 1;
         â˜ƒxxx = 0;
         â˜ƒxxxx = 1;
         â˜ƒxxxxx = 1;
      } else if (â˜ƒxxxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxxxxxxxx) {
         â˜ƒ = 0;
         â˜ƒx = 1;
         â˜ƒxx = 0;
         â˜ƒxxx = 0;
         â˜ƒxxxx = 1;
         â˜ƒxxxxx = 1;
      } else {
         â˜ƒ = 0;
         â˜ƒx = 1;
         â˜ƒxx = 0;
         â˜ƒxxx = 1;
         â˜ƒxxxx = 1;
         â˜ƒxxxxx = 0;
      }

      double â˜ƒ = â˜ƒxxxxxxxxxxxxxxxx - (double)â˜ƒ + 0.16666666666666666;
      double â˜ƒx = â˜ƒxxxxxxxxxxxxxxxxx - (double)â˜ƒx + 0.16666666666666666;
      double â˜ƒxx = â˜ƒxxxxxxxxxxxxxxxxxx - (double)â˜ƒxx + 0.16666666666666666;
      double â˜ƒxxx = â˜ƒxxxxxxxxxxxxxxxx - (double)â˜ƒxxx + 0.3333333333333333;
      double â˜ƒxxxx = â˜ƒxxxxxxxxxxxxxxxxx - (double)â˜ƒxxxx + 0.3333333333333333;
      double â˜ƒxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx - (double)â˜ƒxxxxx + 0.3333333333333333;
      double â˜ƒxxxxxx = â˜ƒxxxxxxxxxxxxxxxx - 1.0 + 0.5;
      double â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx - 1.0 + 0.5;
      double â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxx - 1.0 + 0.5;
      int â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx & 0xFF;
      int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxx & 0xFF;
      int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx & 0xFF;
      int â˜ƒxxxxxxxxxxxx = this.p(â˜ƒxxxxxxxxx + this.p(â˜ƒxxxxxxxxxx + this.p(â˜ƒxxxxxxxxxxx))) % 12;
      int â˜ƒxxxxxxxxxxxxx = this.p(â˜ƒxxxxxxxxx + â˜ƒ + this.p(â˜ƒxxxxxxxxxx + â˜ƒx + this.p(â˜ƒxxxxxxxxxxx + â˜ƒxx))) % 12;
      int â˜ƒxxxxxxxxxxxxxx = this.p(â˜ƒxxxxxxxxx + â˜ƒxxx + this.p(â˜ƒxxxxxxxxxx + â˜ƒxxxx + this.p(â˜ƒxxxxxxxxxxx + â˜ƒxxxxx))) % 12;
      int â˜ƒxxxxxxxxxxxxxxx = this.p(â˜ƒxxxxxxxxx + 1 + this.p(â˜ƒxxxxxxxxxx + 1 + this.p(â˜ƒxxxxxxxxxxx + 1))) % 12;
      double â˜ƒxxxxxxxxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, 0.6);
      double â˜ƒxxxxxxxxxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxxxxxxxxx, â˜ƒ, â˜ƒx, â˜ƒxx, 0.6);
      double â˜ƒxxxxxxxxxxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, 0.6);
      double â˜ƒxxxxxxxxxxxxxxxxxxx = this.getCornerNoise3D(â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, 0.6);
      return 32.0 * (â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxx);
   }
}
