package net.minecraft.world.level.levelgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.synth.NoiseUtils;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class Cavifier implements NoiseModifier {
   private final int minCellY;
   private final NormalNoise layerNoiseSource;
   private final NormalNoise pillarNoiseSource;
   private final NormalNoise pillarRarenessModulator;
   private final NormalNoise pillarThicknessModulator;
   private final NormalNoise spaghetti2dNoiseSource;
   private final NormalNoise spaghetti2dElevationModulator;
   private final NormalNoise spaghetti2dRarityModulator;
   private final NormalNoise spaghetti2dThicknessModulator;
   private final NormalNoise spaghetti3dNoiseSource1;
   private final NormalNoise spaghetti3dNoiseSource2;
   private final NormalNoise spaghetti3dRarityModulator;
   private final NormalNoise spaghetti3dThicknessModulator;
   private final NormalNoise spaghettiRoughnessNoise;
   private final NormalNoise spaghettiRoughnessModulator;
   private final NormalNoise caveEntranceNoiseSource;
   private final NormalNoise cheeseNoiseSource;
   private static final int CHEESE_NOISE_RANGE = 128;
   private static final int SURFACE_DENSITY_THRESHOLD = 170;

   public Cavifier(RandomSource var1, int var2) {
      this.minCellY = â˜ƒ;
      this.pillarNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0, 1.0);
      this.pillarRarenessModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.pillarThicknessModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.spaghetti2dNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
      this.spaghetti2dElevationModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.spaghetti2dRarityModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -11, 1.0);
      this.spaghetti2dThicknessModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -11, 1.0);
      this.spaghetti3dNoiseSource1 = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
      this.spaghetti3dNoiseSource2 = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
      this.spaghetti3dRarityModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -11, 1.0);
      this.spaghetti3dThicknessModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.spaghettiRoughnessNoise = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -5, 1.0);
      this.spaghettiRoughnessModulator = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.caveEntranceNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0, 1.0, 1.0);
      this.layerNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.cheeseNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0);
   }

   @Override
   public double modifyNoise(double var1, int var3, int var4, int var5) {
      boolean â˜ƒ = â˜ƒ < 170.0;
      double â˜ƒx = this.spaghettiRoughness(â˜ƒ, â˜ƒ, â˜ƒ);
      double â˜ƒxx = this.getSpaghetti3d(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ) {
         return Math.min(â˜ƒ, (â˜ƒxx + â˜ƒx) * 128.0 * 5.0);
      } else {
         double â˜ƒ = this.cheeseNoiseSource.getValue((double)â˜ƒ, (double)â˜ƒ / 1.5, (double)â˜ƒ);
         double â˜ƒx = Mth.clamp(â˜ƒ + 0.25, -1.0, 1.0);
         double â˜ƒxx = (double)((float)(30 - â˜ƒ) / 8.0F);
         double â˜ƒxxx = â˜ƒx + Mth.clampedLerp(0.5, 0.0, â˜ƒxx);
         double â˜ƒxxxx = this.getLayerizedCaverns(â˜ƒ, â˜ƒ, â˜ƒ);
         double â˜ƒxxxxx = this.getSpaghetti2d(â˜ƒ, â˜ƒ, â˜ƒ);
         double â˜ƒxxxxxx = â˜ƒxxx + â˜ƒxxxx;
         double â˜ƒxxxxxxx = Math.min(â˜ƒxxxxxx, Math.min(â˜ƒxx, â˜ƒxxxxx) + â˜ƒx);
         double â˜ƒxxxxxxxx = Math.max(â˜ƒxxxxxxx, this.getPillars(â˜ƒ, â˜ƒ, â˜ƒ));
         return 128.0 * Mth.clamp(â˜ƒxxxxxxxx, -1.0, 1.0);
      }
   }

   private double addEntrances(double var1, int var3, int var4, int var5) {
      double â˜ƒ = this.caveEntranceNoiseSource.getValue((double)(â˜ƒ * 2), (double)â˜ƒ, (double)(â˜ƒ * 2));
      â˜ƒ = NoiseUtils.biasTowardsExtreme(â˜ƒ, 1.0);
      int â˜ƒx = 0;
      double â˜ƒxx = (double)(â˜ƒ - 0) / 40.0;
      â˜ƒ += Mth.clampedLerp(0.5, â˜ƒ, â˜ƒxx);
      double â˜ƒxxx = 3.0;
      â˜ƒ = 4.0 * â˜ƒ + 3.0;
      return Math.min(â˜ƒ, â˜ƒ);
   }

   private double getPillars(int var1, int var2, int var3) {
      double â˜ƒ = 0.0;
      double â˜ƒx = 2.0;
      double â˜ƒxx = NoiseUtils.sampleNoiseAndMapToRange(this.pillarRarenessModulator, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, 0.0, 2.0);
      double â˜ƒxxx = 0.0;
      double â˜ƒxxxx = 1.1;
      double â˜ƒxxxxx = NoiseUtils.sampleNoiseAndMapToRange(this.pillarThicknessModulator, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, 0.0, 1.1);
      â˜ƒxxxxx = Math.pow(â˜ƒxxxxx, 3.0);
      double â˜ƒxxxxxx = 25.0;
      double â˜ƒxxxxxxx = 0.3;
      double â˜ƒxxxxxxxx = this.pillarNoiseSource.getValue((double)â˜ƒ * 25.0, (double)â˜ƒ * 0.3, (double)â˜ƒ * 25.0);
      â˜ƒxxxxxxxx = â˜ƒxxxxx * (â˜ƒxxxxxxxx * 2.0 - â˜ƒxx);
      return â˜ƒxxxxxxxx > 0.03 ? â˜ƒxxxxxxxx : Double.NEGATIVE_INFINITY;
   }

   private double getLayerizedCaverns(int var1, int var2, int var3) {
      double â˜ƒ = this.layerNoiseSource.getValue((double)â˜ƒ, (double)(â˜ƒ * 8), (double)â˜ƒ);
      return Mth.square(â˜ƒ) * 4.0;
   }

   private double getSpaghetti3d(int var1, int var2, int var3) {
      double â˜ƒ = this.spaghetti3dRarityModulator.getValue((double)(â˜ƒ * 2), (double)â˜ƒ, (double)(â˜ƒ * 2));
      double â˜ƒx = Cavifier.QuantizedSpaghettiRarity.getSpaghettiRarity3D(â˜ƒ);
      double â˜ƒxx = 0.065;
      double â˜ƒxxx = 0.088;
      double â˜ƒxxxx = NoiseUtils.sampleNoiseAndMapToRange(this.spaghetti3dThicknessModulator, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, 0.065, 0.088);
      double â˜ƒxxxxx = sampleWithRarity(this.spaghetti3dNoiseSource1, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, â˜ƒx);
      double â˜ƒxxxxxx = Math.abs(â˜ƒx * â˜ƒxxxxx) - â˜ƒxxxx;
      double â˜ƒxxxxxxx = sampleWithRarity(this.spaghetti3dNoiseSource2, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, â˜ƒx);
      double â˜ƒxxxxxxxx = Math.abs(â˜ƒx * â˜ƒxxxxxxx) - â˜ƒxxxx;
      return clampToUnit(Math.max(â˜ƒxxxxxx, â˜ƒxxxxxxxx));
   }

   private double getSpaghetti2d(int var1, int var2, int var3) {
      double â˜ƒ = this.spaghetti2dRarityModulator.getValue((double)(â˜ƒ * 2), (double)â˜ƒ, (double)(â˜ƒ * 2));
      double â˜ƒx = Cavifier.QuantizedSpaghettiRarity.getSphaghettiRarity2D(â˜ƒ);
      double â˜ƒxx = 0.6;
      double â˜ƒxxx = 1.3;
      double â˜ƒxxxx = NoiseUtils.sampleNoiseAndMapToRange(this.spaghetti2dThicknessModulator, (double)(â˜ƒ * 2), (double)â˜ƒ, (double)(â˜ƒ * 2), 0.6, 1.3);
      double â˜ƒxxxxx = sampleWithRarity(this.spaghetti2dNoiseSource, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, â˜ƒx);
      double â˜ƒxxxxxx = 0.083;
      double â˜ƒxxxxxxx = Math.abs(â˜ƒx * â˜ƒxxxxx) - 0.083 * â˜ƒxxxx;
      int â˜ƒxxxxxxxx = this.minCellY;
      int â˜ƒxxxxxxxxx = 8;
      double â˜ƒxxxxxxxxxx = NoiseUtils.sampleNoiseAndMapToRange(this.spaghetti2dElevationModulator, (double)â˜ƒ, 0.0, (double)â˜ƒ, (double)â˜ƒxxxxxxxx, 8.0);
      double â˜ƒxxxxxxxxxxx = Math.abs(â˜ƒxxxxxxxxxx - (double)â˜ƒ / 8.0) - 1.0 * â˜ƒxxxx;
      â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx;
      return clampToUnit(Math.max(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxx));
   }

   private double spaghettiRoughness(int var1, int var2, int var3) {
      double â˜ƒ = NoiseUtils.sampleNoiseAndMapToRange(this.spaghettiRoughnessModulator, (double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ, 0.0, 0.1);
      return (0.4 - Math.abs(this.spaghettiRoughnessNoise.getValue((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ))) * â˜ƒ;
   }

   private static double clampToUnit(double var0) {
      return Mth.clamp(â˜ƒ, -1.0, 1.0);
   }

   private static double sampleWithRarity(NormalNoise var0, double var1, double var3, double var5, double var7) {
      return â˜ƒ.getValue(â˜ƒ / â˜ƒ, â˜ƒ / â˜ƒ, â˜ƒ / â˜ƒ);
   }

   static final class QuantizedSpaghettiRarity {
      private QuantizedSpaghettiRarity() {
      }

      static double getSphaghettiRarity2D(double var0) {
         if (â˜ƒ < -0.75) {
            return 0.5;
         } else if (â˜ƒ < -0.5) {
            return 0.75;
         } else if (â˜ƒ < 0.5) {
            return 1.0;
         } else {
            return â˜ƒ < 0.75 ? 2.0 : 3.0;
         }
      }

      static double getSpaghettiRarity3D(double var0) {
         if (â˜ƒ < -0.5) {
            return 0.75;
         } else if (â˜ƒ < 0.0) {
            return 1.0;
         } else {
            return â˜ƒ < 0.5 ? 1.5 : 2.0;
         }
      }
   }
}
