package net.minecraft.world.level.levelgen.synth;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class PerlinNoise implements SurfaceNoise {
   private static final int ROUND_OFF = 33554432;
   private final ImprovedNoise[] noiseLevels;
   private final DoubleList amplitudes;
   private final double lowestFreqValueFactor;
   private final double lowestFreqInputFactor;

   public PerlinNoise(RandomSource var1, IntStream var2) {
      this(â˜ƒ, (List<Integer>)â˜ƒ.boxed().collect(ImmutableList.toImmutableList()));
   }

   public PerlinNoise(RandomSource var1, List<Integer> var2) {
      this(â˜ƒ, new IntRBTreeSet(â˜ƒ));
   }

   public static PerlinNoise create(RandomSource var0, int var1, double... var2) {
      return create(â˜ƒ, â˜ƒ, new DoubleArrayList(â˜ƒ));
   }

   public static PerlinNoise create(RandomSource var0, int var1, DoubleList var2) {
      return new PerlinNoise(â˜ƒ, Pair.of(â˜ƒ, â˜ƒ));
   }

   private static Pair<Integer, DoubleList> makeAmplitudes(IntSortedSet var0) {
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int â˜ƒ = -â˜ƒ.firstInt();
         int â˜ƒx = â˜ƒ.lastInt();
         int â˜ƒxx = â˜ƒ + â˜ƒx + 1;
         if (â˜ƒxx < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            DoubleList â˜ƒ = new DoubleArrayList(new double[â˜ƒxx]);
            IntBidirectionalIterator â˜ƒx = â˜ƒ.iterator();

            while(â˜ƒx.hasNext()) {
               int â˜ƒxx = â˜ƒx.nextInt();
               â˜ƒ.set(â˜ƒxx + â˜ƒ, 1.0);
            }

            return Pair.of(-â˜ƒ, â˜ƒ);
         }
      }
   }

   private PerlinNoise(RandomSource var1, IntSortedSet var2) {
      this(â˜ƒ, â˜ƒ, WorldgenRandom::new);
   }

   private PerlinNoise(RandomSource var1, IntSortedSet var2, LongFunction<RandomSource> var3) {
      this(â˜ƒ, makeAmplitudes(â˜ƒ), â˜ƒ);
   }

   protected PerlinNoise(RandomSource var1, Pair<Integer, DoubleList> var2) {
      this(â˜ƒ, â˜ƒ, WorldgenRandom::new);
   }

   protected PerlinNoise(RandomSource var1, Pair<Integer, DoubleList> var2, LongFunction<RandomSource> var3) {
      int â˜ƒ = â˜ƒ.getFirst();
      this.amplitudes = â˜ƒ.getSecond();
      ImprovedNoise â˜ƒx = new ImprovedNoise(â˜ƒ);
      int â˜ƒxx = this.amplitudes.size();
      int â˜ƒxxx = -â˜ƒ;
      this.noiseLevels = new ImprovedNoise[â˜ƒxx];
      if (â˜ƒxxx >= 0 && â˜ƒxxx < â˜ƒxx) {
         double â˜ƒxxxx = this.amplitudes.getDouble(â˜ƒxxx);
         if (â˜ƒxxxx != 0.0) {
            this.noiseLevels[â˜ƒxxx] = â˜ƒx;
         }
      }

      for(int â˜ƒ = â˜ƒxxx - 1; â˜ƒ >= 0; --â˜ƒ) {
         if (â˜ƒ < â˜ƒxx) {
            double â˜ƒx = this.amplitudes.getDouble(â˜ƒ);
            if (â˜ƒx != 0.0) {
               this.noiseLevels[â˜ƒ] = new ImprovedNoise(â˜ƒ);
            } else {
               skipOctave(â˜ƒ);
            }
         } else {
            skipOctave(â˜ƒ);
         }
      }

      if (â˜ƒxxx < â˜ƒxx - 1) {
         throw new IllegalArgumentException("Positive octaves are temporarily disabled");
      } else {
         this.lowestFreqInputFactor = Math.pow(2.0, (double)(-â˜ƒxxx));
         this.lowestFreqValueFactor = Math.pow(2.0, (double)(â˜ƒxx - 1)) / (Math.pow(2.0, (double)â˜ƒxx) - 1.0);
      }
   }

   private static void skipOctave(RandomSource var0) {
      â˜ƒ.consumeCount(262);
   }

   public double getValue(double var1, double var3, double var5) {
      return this.getValue(â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, false);
   }

   @Deprecated
   public double getValue(double var1, double var3, double var5, double var7, double var9, boolean var11) {
      double â˜ƒ = 0.0;
      double â˜ƒx = this.lowestFreqInputFactor;
      double â˜ƒxx = this.lowestFreqValueFactor;

      for(int â˜ƒxxx = 0; â˜ƒxxx < this.noiseLevels.length; ++â˜ƒxxx) {
         ImprovedNoise â˜ƒxxxx = this.noiseLevels[â˜ƒxxx];
         if (â˜ƒxxxx != null) {
            double â˜ƒxxxxx = â˜ƒxxxx.noise(wrap(â˜ƒ * â˜ƒx), â˜ƒ ? -â˜ƒxxxx.yo : wrap(â˜ƒ * â˜ƒx), wrap(â˜ƒ * â˜ƒx), â˜ƒ * â˜ƒx, â˜ƒ * â˜ƒx);
            â˜ƒ += this.amplitudes.getDouble(â˜ƒxxx) * â˜ƒxxxxx * â˜ƒxx;
         }

         â˜ƒx *= 2.0;
         â˜ƒxx /= 2.0;
      }

      return â˜ƒ;
   }

   @Nullable
   public ImprovedNoise getOctaveNoise(int var1) {
      return this.noiseLevels[this.noiseLevels.length - 1 - â˜ƒ];
   }

   public static double wrap(double var0) {
      return â˜ƒ - (double)Mth.lfloor(â˜ƒ / 3.3554432E7 + 0.5) * 3.3554432E7;
   }

   @Override
   public double getSurfaceNoiseValue(double var1, double var3, double var5, double var7) {
      return this.getValue(â˜ƒ, â˜ƒ, 0.0, â˜ƒ, â˜ƒ, false);
   }
}
