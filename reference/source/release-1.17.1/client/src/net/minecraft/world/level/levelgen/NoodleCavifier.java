package net.minecraft.world.level.levelgen;

import java.util.Random;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.synth.NoiseUtils;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class NoodleCavifier {
   private static final int NOODLES_MAX_Y = 30;
   private static final double SPACING_AND_STRAIGHTNESS = 1.5;
   private static final double XZ_FREQUENCY = 2.6666666666666665;
   private static final double Y_FREQUENCY = 2.6666666666666665;
   private final NormalNoise toggleNoiseSource;
   private final NormalNoise thicknessNoiseSource;
   private final NormalNoise noodleANoiseSource;
   private final NormalNoise noodleBNoiseSource;

   public NoodleCavifier(long var1) {
      Random â˜ƒ = new Random(â˜ƒ);
      this.toggleNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.thicknessNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -8, 1.0);
      this.noodleANoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
      this.noodleBNoiseSource = NormalNoise.create(new SimpleRandomSource(â˜ƒ.nextLong()), -7, 1.0);
   }

   public void fillToggleNoiseColumn(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.toggleNoiseSource, 1.0);
   }

   public void fillThicknessNoiseColumn(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.thicknessNoiseSource, 1.0);
   }

   public void fillRidgeANoiseColumn(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.noodleANoiseSource, 2.6666666666666665, 2.6666666666666665);
   }

   public void fillRidgeBNoiseColumn(double[] var1, int var2, int var3, int var4, int var5) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.noodleBNoiseSource, 2.6666666666666665, 2.6666666666666665);
   }

   public void fillNoiseColumn(double[] var1, int var2, int var3, int var4, int var5, NormalNoise var6, double var7) {
      this.fillNoiseColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void fillNoiseColumn(double[] var1, int var2, int var3, int var4, int var5, NormalNoise var6, double var7, double var9) {
      int â˜ƒ = 8;
      int â˜ƒx = 4;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ; ++â˜ƒxx) {
         int â˜ƒxxxx = â˜ƒxx + â˜ƒ;
         int â˜ƒxxxxx = â˜ƒ * 4;
         int â˜ƒxxxxxx = â˜ƒxxxx * 8;
         int â˜ƒxxxxxxx = â˜ƒ * 4;
         double â˜ƒxxx;
         if (â˜ƒxxxxxx < 38) {
            â˜ƒxxx = NoiseUtils.sampleNoiseAndMapToRange(â˜ƒ, (double)â˜ƒxxxxx * â˜ƒ, (double)â˜ƒxxxxxx * â˜ƒ, (double)â˜ƒxxxxxxx * â˜ƒ, -1.0, 1.0);
         } else {
            â˜ƒxxx = 1.0;
         }

         â˜ƒ[â˜ƒxx] = â˜ƒxxx;
      }
   }

   public double noodleCavify(double var1, int var3, int var4, int var5, double var6, double var8, double var10, double var12, int var14) {
      if (â˜ƒ > 30 || â˜ƒ < â˜ƒ + 4) {
         return â˜ƒ;
      } else if (â˜ƒ < 0.0) {
         return â˜ƒ;
      } else if (â˜ƒ < 0.0) {
         return â˜ƒ;
      } else {
         double â˜ƒ = 0.05;
         double â˜ƒx = 0.1;
         double â˜ƒxx = Mth.clampedMap(â˜ƒ, -1.0, 1.0, 0.05, 0.1);
         double â˜ƒxxx = Math.abs(1.5 * â˜ƒ) - â˜ƒxx;
         double â˜ƒxxxx = Math.abs(1.5 * â˜ƒ) - â˜ƒxx;
         double â˜ƒxxxxx = Math.max(â˜ƒxxx, â˜ƒxxxx);
         return Math.min(â˜ƒ, â˜ƒxxxxx);
      }
   }
}
