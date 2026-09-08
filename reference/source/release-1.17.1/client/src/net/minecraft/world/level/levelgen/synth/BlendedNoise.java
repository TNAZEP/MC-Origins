package net.minecraft.world.level.levelgen.synth;

import java.util.stream.IntStream;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;

public class BlendedNoise {
   private final PerlinNoise minLimitNoise;
   private final PerlinNoise maxLimitNoise;
   private final PerlinNoise mainNoise;

   public BlendedNoise(PerlinNoise var1, PerlinNoise var2, PerlinNoise var3) {
      this.minLimitNoise = â˜ƒ;
      this.maxLimitNoise = â˜ƒ;
      this.mainNoise = â˜ƒ;
   }

   public BlendedNoise(RandomSource var1) {
      this(
         new PerlinNoise(â˜ƒ, IntStream.rangeClosed(-15, 0)),
         new PerlinNoise(â˜ƒ, IntStream.rangeClosed(-15, 0)),
         new PerlinNoise(â˜ƒ, IntStream.rangeClosed(-7, 0))
      );
   }

   public double sampleAndClampNoise(int var1, int var2, int var3, double var4, double var6, double var8, double var10) {
      double â˜ƒ = 0.0;
      double â˜ƒx = 0.0;
      double â˜ƒxx = 0.0;
      boolean â˜ƒxxx = true;
      double â˜ƒxxxx = 1.0;

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 8; ++â˜ƒxxxxx) {
         ImprovedNoise â˜ƒxxxxxx = this.mainNoise.getOctaveNoise(â˜ƒxxxxx);
         if (â˜ƒxxxxxx != null) {
            â˜ƒxx += â˜ƒxxxxxx.noise(
                  PerlinNoise.wrap((double)â˜ƒ * â˜ƒ * â˜ƒxxxx),
                  PerlinNoise.wrap((double)â˜ƒ * â˜ƒ * â˜ƒxxxx),
                  PerlinNoise.wrap((double)â˜ƒ * â˜ƒ * â˜ƒxxxx),
                  â˜ƒ * â˜ƒxxxx,
                  (double)â˜ƒ * â˜ƒ * â˜ƒxxxx
               )
               / â˜ƒxxxx;
         }

         â˜ƒxxxx /= 2.0;
      }

      double â˜ƒxxxxx = (â˜ƒxx / 10.0 + 1.0) / 2.0;
      boolean â˜ƒxxxxxx = â˜ƒxxxxx >= 1.0;
      boolean â˜ƒxxxxxxx = â˜ƒxxxxx <= 0.0;
      â˜ƒxxxx = 1.0;

      for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 16; ++â˜ƒxxxxxxxx) {
         double â˜ƒxxxxxxxxx = PerlinNoise.wrap((double)â˜ƒ * â˜ƒ * â˜ƒxxxx);
         double â˜ƒxxxxxxxxxx = PerlinNoise.wrap((double)â˜ƒ * â˜ƒ * â˜ƒxxxx);
         double â˜ƒxxxxxxxxxxx = PerlinNoise.wrap((double)â˜ƒ * â˜ƒ * â˜ƒxxxx);
         double â˜ƒxxxxxxxxxxxx = â˜ƒ * â˜ƒxxxx;
         if (!â˜ƒxxxxxx) {
            ImprovedNoise â˜ƒxxxxxxxxxxxxx = this.minLimitNoise.getOctaveNoise(â˜ƒxxxxxxxx);
            if (â˜ƒxxxxxxxxxxxxx != null) {
               â˜ƒ += â˜ƒxxxxxxxxxxxxx.noise(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, (double)â˜ƒ * â˜ƒxxxxxxxxxxxx) / â˜ƒxxxx;
            }
         }

         if (!â˜ƒxxxxxxx) {
            ImprovedNoise â˜ƒxxxxxxxxx = this.maxLimitNoise.getOctaveNoise(â˜ƒxxxxxxxx);
            if (â˜ƒxxxxxxxxx != null) {
               â˜ƒx += â˜ƒxxxxxxxxx.noise(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, (double)â˜ƒ * â˜ƒxxxxxxxxxxxx) / â˜ƒxxxx;
            }
         }

         â˜ƒxxxx /= 2.0;
      }

      return Mth.clampedLerp(â˜ƒ / 512.0, â˜ƒx / 512.0, â˜ƒxxxxx);
   }
}
