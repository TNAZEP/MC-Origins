package net.minecraft.world.level.levelgen.synth;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import net.minecraft.world.level.levelgen.RandomSource;

public class NormalNoise {
   private static final double INPUT_FACTOR = 1.0181268882175227;
   private static final double TARGET_DEVIATION = 0.3333333333333333;
   private final double valueFactor;
   private final PerlinNoise first;
   private final PerlinNoise second;

   public static NormalNoise create(RandomSource var0, int var1, double... var2) {
      return new NormalNoise(â˜ƒ, â˜ƒ, new DoubleArrayList(â˜ƒ));
   }

   public static NormalNoise create(RandomSource var0, int var1, DoubleList var2) {
      return new NormalNoise(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private NormalNoise(RandomSource var1, int var2, DoubleList var3) {
      this.first = PerlinNoise.create(â˜ƒ, â˜ƒ, â˜ƒ);
      this.second = PerlinNoise.create(â˜ƒ, â˜ƒ, â˜ƒ);
      int â˜ƒ = Integer.MAX_VALUE;
      int â˜ƒx = Integer.MIN_VALUE;
      DoubleListIterator â˜ƒxx = â˜ƒ.iterator();

      while(â˜ƒxx.hasNext()) {
         int â˜ƒxxx = â˜ƒxx.nextIndex();
         double â˜ƒxxxx = â˜ƒxx.nextDouble();
         if (â˜ƒxxxx != 0.0) {
            â˜ƒ = Math.min(â˜ƒ, â˜ƒxxx);
            â˜ƒx = Math.max(â˜ƒx, â˜ƒxxx);
         }
      }

      this.valueFactor = 0.16666666666666666 / expectedDeviation(â˜ƒx - â˜ƒ);
   }

   private static double expectedDeviation(int var0) {
      return 0.1 * (1.0 + 1.0 / (double)(â˜ƒ + 1));
   }

   public double getValue(double var1, double var3, double var5) {
      double â˜ƒ = â˜ƒ * 1.0181268882175227;
      double â˜ƒx = â˜ƒ * 1.0181268882175227;
      double â˜ƒxx = â˜ƒ * 1.0181268882175227;
      return (this.first.getValue(â˜ƒ, â˜ƒ, â˜ƒ) + this.second.getValue(â˜ƒ, â˜ƒx, â˜ƒxx)) * this.valueFactor;
   }
}
