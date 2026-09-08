package net.minecraft.world.level.levelgen;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class NoiseSampler {
   private static final int OLD_CELL_COUNT_Y = 32;
   private static final float[] BIOME_WEIGHTS = Util.make(new float[25], var0 -> {
      for(int â˜ƒ = -2; â˜ƒ <= 2; ++â˜ƒ) {
         for(int â˜ƒx = -2; â˜ƒx <= 2; ++â˜ƒx) {
            float â˜ƒxx = 10.0F / Mth.sqrt((float)(â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx) + 0.2F);
            var0[â˜ƒ + 2 + (â˜ƒx + 2) * 5] = â˜ƒxx;
         }
      }
   });
   private final BiomeSource biomeSource;
   private final int cellWidth;
   private final int cellHeight;
   private final int cellCountY;
   private final NoiseSettings noiseSettings;
   private final BlendedNoise blendedNoise;
   @Nullable
   private final SimplexNoise islandNoise;
   private final PerlinNoise depthNoise;
   private final double topSlideTarget;
   private final double topSlideSize;
   private final double topSlideOffset;
   private final double bottomSlideTarget;
   private final double bottomSlideSize;
   private final double bottomSlideOffset;
   private final double dimensionDensityFactor;
   private final double dimensionDensityOffset;
   private final NoiseModifier caveNoiseModifier;

   public NoiseSampler(
      BiomeSource var1, int var2, int var3, int var4, NoiseSettings var5, BlendedNoise var6, @Nullable SimplexNoise var7, PerlinNoise var8, NoiseModifier var9
   ) {
      this.cellWidth = â˜ƒ;
      this.cellHeight = â˜ƒ;
      this.biomeSource = â˜ƒ;
      this.cellCountY = â˜ƒ;
      this.noiseSettings = â˜ƒ;
      this.blendedNoise = â˜ƒ;
      this.islandNoise = â˜ƒ;
      this.depthNoise = â˜ƒ;
      this.topSlideTarget = (double)â˜ƒ.topSlideSettings().target();
      this.topSlideSize = (double)â˜ƒ.topSlideSettings().size();
      this.topSlideOffset = (double)â˜ƒ.topSlideSettings().offset();
      this.bottomSlideTarget = (double)â˜ƒ.bottomSlideSettings().target();
      this.bottomSlideSize = (double)â˜ƒ.bottomSlideSettings().size();
      this.bottomSlideOffset = (double)â˜ƒ.bottomSlideSettings().offset();
      this.dimensionDensityFactor = â˜ƒ.densityFactor();
      this.dimensionDensityOffset = â˜ƒ.densityOffset();
      this.caveNoiseModifier = â˜ƒ;
   }

   public void fillNoiseColumn(double[] var1, int var2, int var3, NoiseSettings var4, int var5, int var6, int var7) {
      double â˜ƒ;
      double â˜ƒx;
      if (this.islandNoise != null) {
         â˜ƒ = (double)(TheEndBiomeSource.getHeightValue(this.islandNoise, â˜ƒ, â˜ƒ) - 8.0F);
         if (â˜ƒ > 0.0) {
            â˜ƒx = 0.25;
         } else {
            â˜ƒx = 1.0;
         }
      } else {
         float â˜ƒ = 0.0F;
         float â˜ƒx = 0.0F;
         float â˜ƒxx = 0.0F;
         int â˜ƒxxx = 2;
         int â˜ƒxxxx = â˜ƒ;
         float â˜ƒxxxxx = this.biomeSource.getNoiseBiome(â˜ƒ, â˜ƒ, â˜ƒ).getDepth();

         for(int â˜ƒxxxxxx = -2; â˜ƒxxxxxx <= 2; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = -2; â˜ƒxxxxxxx <= 2; ++â˜ƒxxxxxxx) {
               Biome â˜ƒxxxxxxxxxx = this.biomeSource.getNoiseBiome(â˜ƒ + â˜ƒxxxxxx, â˜ƒxxxx, â˜ƒ + â˜ƒxxxxxxx);
               float â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getDepth();
               float â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.getScale();
               float â˜ƒxxxxxxxx;
               float â˜ƒxxxxxxxxx;
               if (â˜ƒ.isAmplified() && â˜ƒxxxxxxxxxxx > 0.0F) {
                  â˜ƒxxxxxxxx = 1.0F + â˜ƒxxxxxxxxxxx * 2.0F;
                  â˜ƒxxxxxxxxx = 1.0F + â˜ƒxxxxxxxxxxxx * 4.0F;
               } else {
                  â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxx;
                  â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxxx;
               }

               float â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxxx > â˜ƒxxxxx ? 0.5F : 1.0F;
               float â˜ƒxxxxxxxxx = â˜ƒxxxxxxxx * BIOME_WEIGHTS[â˜ƒxxxxxx + 2 + (â˜ƒxxxxxxx + 2) * 5] / (â˜ƒxxxxxxxx + 2.0F);
               â˜ƒ += â˜ƒxxxxxxxxx * â˜ƒxxxxxxxxx;
               â˜ƒx += â˜ƒxxxxxxxx * â˜ƒxxxxxxxxx;
               â˜ƒxx += â˜ƒxxxxxxxxx;
            }
         }

         float â˜ƒxxxxxx = â˜ƒx / â˜ƒxx;
         float â˜ƒxxxxxxx = â˜ƒ / â˜ƒxx;
         double â˜ƒxxxxxxxx = (double)(â˜ƒxxxxxx * 0.5F - 0.125F);
         double â˜ƒxxxxxxxxx = (double)(â˜ƒxxxxxxx * 0.9F + 0.1F);
         â˜ƒ = â˜ƒxxxxxxxx * 0.265625;
         â˜ƒx = 96.0 / â˜ƒxxxxxxxxx;
      }

      double â˜ƒ = 684.412 * â˜ƒ.noiseSamplingSettings().xzScale();
      double â˜ƒx = 684.412 * â˜ƒ.noiseSamplingSettings().yScale();
      double â˜ƒxx = â˜ƒ / â˜ƒ.noiseSamplingSettings().xzFactor();
      double â˜ƒxxx = â˜ƒx / â˜ƒ.noiseSamplingSettings().yFactor();
      double â˜ƒxxxx = â˜ƒ.randomDensityOffset() ? this.getRandomDensity(â˜ƒ, â˜ƒ) : 0.0;

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= â˜ƒ; ++â˜ƒxxxxx) {
         int â˜ƒxxxxxx = â˜ƒxxxxx + â˜ƒ;
         double â˜ƒxxxxxxx = this.blendedNoise.sampleAndClampNoise(â˜ƒ, â˜ƒxxxxxx, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         double â˜ƒxxxxxxxx = this.computeInitialDensity(â˜ƒxxxxxx, â˜ƒ, â˜ƒx, â˜ƒxxxx) + â˜ƒxxxxxxx;
         â˜ƒxxxxxxxx = this.caveNoiseModifier.modifyNoise(â˜ƒxxxxxxxx, â˜ƒxxxxxx * this.cellHeight, â˜ƒ * this.cellWidth, â˜ƒ * this.cellWidth);
         â˜ƒxxxxxxxx = this.applySlide(â˜ƒxxxxxxxx, â˜ƒxxxxxx);
         â˜ƒ[â˜ƒxxxxx] = â˜ƒxxxxxxxx;
      }
   }

   private double computeInitialDensity(int var1, double var2, double var4, double var6) {
      double â˜ƒ = 1.0 - (double)â˜ƒ * 2.0 / 32.0 + â˜ƒ;
      double â˜ƒx = â˜ƒ * this.dimensionDensityFactor + this.dimensionDensityOffset;
      double â˜ƒxx = (â˜ƒx + â˜ƒ) * â˜ƒ;
      return â˜ƒxx * (double)(â˜ƒxx > 0.0 ? 4 : 1);
   }

   private double applySlide(double var1, int var3) {
      int â˜ƒ = Mth.intFloorDiv(this.noiseSettings.minY(), this.cellHeight);
      int â˜ƒx = â˜ƒ - â˜ƒ;
      if (this.topSlideSize > 0.0) {
         double â˜ƒxx = ((double)(this.cellCountY - â˜ƒx) - this.topSlideOffset) / this.topSlideSize;
         â˜ƒ = Mth.clampedLerp(this.topSlideTarget, â˜ƒ, â˜ƒxx);
      }

      if (this.bottomSlideSize > 0.0) {
         double â˜ƒ = ((double)â˜ƒx - this.bottomSlideOffset) / this.bottomSlideSize;
         â˜ƒ = Mth.clampedLerp(this.bottomSlideTarget, â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   private double getRandomDensity(int var1, int var2) {
      double â˜ƒx = this.depthNoise.getValue((double)(â˜ƒ * 200), 10.0, (double)(â˜ƒ * 200), 1.0, 0.0, true);
      double â˜ƒ;
      if (â˜ƒx < 0.0) {
         â˜ƒ = -â˜ƒx * 0.3;
      } else {
         â˜ƒ = â˜ƒx;
      }

      double â˜ƒ = â˜ƒ * 24.575625 - 2.0;
      return â˜ƒ < 0.0 ? â˜ƒ * 0.009486607142857142 : Math.min(â˜ƒ, 1.0) * 0.006640625;
   }
}
