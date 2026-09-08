package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.Mth;

public class NoiseUtils {
   public static double sampleNoiseAndMapToRange(NormalNoise var0, double var1, double var3, double var5, double var7, double var9) {
      double â˜ƒ = â˜ƒ.getValue(â˜ƒ, â˜ƒ, â˜ƒ);
      return Mth.map(â˜ƒ, -1.0, 1.0, â˜ƒ, â˜ƒ);
   }

   public static double biasTowardsExtreme(double var0, double var2) {
      return â˜ƒ + Math.sin(Math.PI * â˜ƒ) * â˜ƒ / Math.PI;
   }
}
