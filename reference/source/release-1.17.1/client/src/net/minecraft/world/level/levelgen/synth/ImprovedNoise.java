package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;

public final class ImprovedNoise {
   private static final float SHIFT_UP_EPSILON = 1.0E-7F;
   private final byte[] p;
   public final double xo;
   public final double yo;
   public final double zo;

   public ImprovedNoise(RandomSource var1) {
      this.xo = â˜ƒ.nextDouble() * 256.0;
      this.yo = â˜ƒ.nextDouble() * 256.0;
      this.zo = â˜ƒ.nextDouble() * 256.0;
      this.p = new byte[256];

      for(int â˜ƒ = 0; â˜ƒ < 256; ++â˜ƒ) {
         this.p[â˜ƒ] = (byte)â˜ƒ;
      }

      for(int â˜ƒ = 0; â˜ƒ < 256; ++â˜ƒ) {
         int â˜ƒx = â˜ƒ.nextInt(256 - â˜ƒ);
         byte â˜ƒxx = this.p[â˜ƒ];
         this.p[â˜ƒ] = this.p[â˜ƒ + â˜ƒx];
         this.p[â˜ƒ + â˜ƒx] = â˜ƒxx;
      }
   }

   public double noise(double var1, double var3, double var5) {
      return this.noise(â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0);
   }

   @Deprecated
   public double noise(double var1, double var3, double var5, double var7, double var9) {
      double â˜ƒx = â˜ƒ + this.xo;
      double â˜ƒxx = â˜ƒ + this.yo;
      double â˜ƒxxx = â˜ƒ + this.zo;
      int â˜ƒxxxx = Mth.floor(â˜ƒx);
      int â˜ƒxxxxx = Mth.floor(â˜ƒxx);
      int â˜ƒxxxxxx = Mth.floor(â˜ƒxxx);
      double â˜ƒxxxxxxx = â˜ƒx - (double)â˜ƒxxxx;
      double â˜ƒxxxxxxxx = â˜ƒxx - (double)â˜ƒxxxxx;
      double â˜ƒxxxxxxxxx = â˜ƒxxx - (double)â˜ƒxxxxxx;
      double â˜ƒ;
      if (â˜ƒ != 0.0) {
         double â˜ƒxxxxxxxxxx;
         if (â˜ƒ >= 0.0 && â˜ƒ < â˜ƒxxxxxxxx) {
            â˜ƒxxxxxxxxxx = â˜ƒ;
         } else {
            â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx;
         }

         â˜ƒ = (double)Mth.floor(â˜ƒxxxxxxxxxx / â˜ƒ + 1.0E-7F) * â˜ƒ;
      } else {
         â˜ƒ = 0.0;
      }

      return this.sampleAndLerp(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx - â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx);
   }

   public double noiseWithDerivative(double var1, double var3, double var5, double[] var7) {
      double â˜ƒ = â˜ƒ + this.xo;
      double â˜ƒx = â˜ƒ + this.yo;
      double â˜ƒxx = â˜ƒ + this.zo;
      int â˜ƒxxx = Mth.floor(â˜ƒ);
      int â˜ƒxxxx = Mth.floor(â˜ƒx);
      int â˜ƒxxxxx = Mth.floor(â˜ƒxx);
      double â˜ƒxxxxxx = â˜ƒ - (double)â˜ƒxxx;
      double â˜ƒxxxxxxx = â˜ƒx - (double)â˜ƒxxxx;
      double â˜ƒxxxxxxxx = â˜ƒxx - (double)â˜ƒxxxxx;
      return this.sampleWithDerivative(â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒ);
   }

   private static double gradDot(int var0, double var1, double var3, double var5) {
      return SimplexNoise.dot(SimplexNoise.GRADIENT[â˜ƒ & 15], â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private int p(int var1) {
      return this.p[â˜ƒ & 0xFF] & 0xFF;
   }

   private double sampleAndLerp(int var1, int var2, int var3, double var4, double var6, double var8, double var10) {
      int â˜ƒ = this.p(â˜ƒ);
      int â˜ƒx = this.p(â˜ƒ + 1);
      int â˜ƒxx = this.p(â˜ƒ + â˜ƒ);
      int â˜ƒxxx = this.p(â˜ƒ + â˜ƒ + 1);
      int â˜ƒxxxx = this.p(â˜ƒx + â˜ƒ);
      int â˜ƒxxxxx = this.p(â˜ƒx + â˜ƒ + 1);
      double â˜ƒxxxxxx = gradDot(this.p(â˜ƒxx + â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
      double â˜ƒxxxxxxx = gradDot(this.p(â˜ƒxxxx + â˜ƒ), â˜ƒ - 1.0, â˜ƒ, â˜ƒ);
      double â˜ƒxxxxxxxx = gradDot(this.p(â˜ƒxxx + â˜ƒ), â˜ƒ, â˜ƒ - 1.0, â˜ƒ);
      double â˜ƒxxxxxxxxx = gradDot(this.p(â˜ƒxxxxx + â˜ƒ), â˜ƒ - 1.0, â˜ƒ - 1.0, â˜ƒ);
      double â˜ƒxxxxxxxxxx = gradDot(this.p(â˜ƒxx + â˜ƒ + 1), â˜ƒ, â˜ƒ, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxx = gradDot(this.p(â˜ƒxxxx + â˜ƒ + 1), â˜ƒ - 1.0, â˜ƒ, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxx = gradDot(this.p(â˜ƒxxx + â˜ƒ + 1), â˜ƒ, â˜ƒ - 1.0, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxxx = gradDot(this.p(â˜ƒxxxxx + â˜ƒ + 1), â˜ƒ - 1.0, â˜ƒ - 1.0, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxxxx = Mth.smoothstep(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxx = Mth.smoothstep(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxx = Mth.smoothstep(â˜ƒ);
      return Mth.lerp3(
         â˜ƒxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxx,
         â˜ƒxxxxxxx,
         â˜ƒxxxxxxxx,
         â˜ƒxxxxxxxxx,
         â˜ƒxxxxxxxxxx,
         â˜ƒxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxx
      );
   }

   private double sampleWithDerivative(int var1, int var2, int var3, double var4, double var6, double var8, double[] var10) {
      int â˜ƒ = this.p(â˜ƒ);
      int â˜ƒx = this.p(â˜ƒ + 1);
      int â˜ƒxx = this.p(â˜ƒ + â˜ƒ);
      int â˜ƒxxx = this.p(â˜ƒ + â˜ƒ + 1);
      int â˜ƒxxxx = this.p(â˜ƒx + â˜ƒ);
      int â˜ƒxxxxx = this.p(â˜ƒx + â˜ƒ + 1);
      int â˜ƒxxxxxx = this.p(â˜ƒxx + â˜ƒ);
      int â˜ƒxxxxxxx = this.p(â˜ƒxxxx + â˜ƒ);
      int â˜ƒxxxxxxxx = this.p(â˜ƒxxx + â˜ƒ);
      int â˜ƒxxxxxxxxx = this.p(â˜ƒxxxxx + â˜ƒ);
      int â˜ƒxxxxxxxxxx = this.p(â˜ƒxx + â˜ƒ + 1);
      int â˜ƒxxxxxxxxxxx = this.p(â˜ƒxxxx + â˜ƒ + 1);
      int â˜ƒxxxxxxxxxxxx = this.p(â˜ƒxxx + â˜ƒ + 1);
      int â˜ƒxxxxxxxxxxxxx = this.p(â˜ƒxxxxx + â˜ƒ + 1);
      int[] â˜ƒxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxxxxxxx & 15];
      int[] â˜ƒxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.GRADIENT[â˜ƒxxxxxxxxxxxxx & 15];
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxx, â˜ƒ - 1.0, â˜ƒ, â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒ - 1.0, â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒ - 1.0, â˜ƒ - 1.0, â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ - 1.0, â˜ƒ, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxxxxxxx, â˜ƒ, â˜ƒ - 1.0, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = SimplexNoise.dot(â˜ƒxxxxxxxxxxxxxxxxxxxxx, â˜ƒ - 1.0, â˜ƒ - 1.0, â˜ƒ - 1.0);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.smoothstep(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.smoothstep(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.smoothstep(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.lerp3(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         (double)â˜ƒxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxxx[0],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxxxx[0]
      );
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.lerp3(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         (double)â˜ƒxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxxx[1],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxxxx[1]
      );
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.lerp3(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         (double)â˜ƒxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxxx[2],
         (double)â˜ƒxxxxxxxxxxxxxxxxxxxxx[2]
      );
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.lerp2(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx
      );
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.lerp2(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx
      );
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.lerp2(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx
      );
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.smoothstepDerivative(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.smoothstepDerivative(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.smoothstepDerivative(â˜ƒ);
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      double â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒ[0] += â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒ[1] += â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      â˜ƒ[2] += â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      return Mth.lerp3(
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
      );
   }
}
