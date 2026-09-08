package net.minecraft.world.level.levelgen.synth;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class PerlinSimplexNoise implements SurfaceNoise {
   private final SimplexNoise[] noiseLevels;
   private final double highestFreqValueFactor;
   private final double highestFreqInputFactor;

   public PerlinSimplexNoise(RandomSource var1, IntStream var2) {
      this(â˜ƒ, (List<Integer>)â˜ƒ.boxed().collect(ImmutableList.toImmutableList()));
   }

   public PerlinSimplexNoise(RandomSource var1, List<Integer> var2) {
      this(â˜ƒ, new IntRBTreeSet(â˜ƒ));
   }

   private PerlinSimplexNoise(RandomSource var1, IntSortedSet var2) {
      if (â˜ƒ.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int â˜ƒ = -â˜ƒ.firstInt();
         int â˜ƒx = â˜ƒ.lastInt();
         int â˜ƒxx = â˜ƒ + â˜ƒx + 1;
         if (â˜ƒxx < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            SimplexNoise â˜ƒ = new SimplexNoise(â˜ƒ);
            int â˜ƒx = â˜ƒx;
            this.noiseLevels = new SimplexNoise[â˜ƒxx];
            if (â˜ƒx >= 0 && â˜ƒx < â˜ƒxx && â˜ƒ.contains(0)) {
               this.noiseLevels[â˜ƒx] = â˜ƒ;
            }

            for(int â˜ƒ = â˜ƒx + 1; â˜ƒ < â˜ƒxx; ++â˜ƒ) {
               if (â˜ƒ >= 0 && â˜ƒ.contains(â˜ƒx - â˜ƒ)) {
                  this.noiseLevels[â˜ƒ] = new SimplexNoise(â˜ƒ);
               } else {
                  â˜ƒ.consumeCount(262);
               }
            }

            if (â˜ƒx > 0) {
               long â˜ƒ = (long)(â˜ƒ.getValue(â˜ƒ.xo, â˜ƒ.yo, â˜ƒ.zo) * 9.223372E18F);
               RandomSource â˜ƒx = new WorldgenRandom(â˜ƒ);

               for(int â˜ƒxx = â˜ƒx - 1; â˜ƒxx >= 0; --â˜ƒxx) {
                  if (â˜ƒxx < â˜ƒxx && â˜ƒ.contains(â˜ƒx - â˜ƒxx)) {
                     this.noiseLevels[â˜ƒxx] = new SimplexNoise(â˜ƒx);
                  } else {
                     â˜ƒx.consumeCount(262);
                  }
               }
            }

            this.highestFreqInputFactor = Math.pow(2.0, (double)â˜ƒx);
            this.highestFreqValueFactor = 1.0 / (Math.pow(2.0, (double)â˜ƒxx) - 1.0);
         }
      }
   }

   public double getValue(double var1, double var3, boolean var5) {
      double â˜ƒ = 0.0;
      double â˜ƒx = this.highestFreqInputFactor;
      double â˜ƒxx = this.highestFreqValueFactor;

      for(SimplexNoise â˜ƒxxx : this.noiseLevels) {
         if (â˜ƒxxx != null) {
            â˜ƒ += â˜ƒxxx.getValue(â˜ƒ * â˜ƒx + (â˜ƒ ? â˜ƒxxx.xo : 0.0), â˜ƒ * â˜ƒx + (â˜ƒ ? â˜ƒxxx.yo : 0.0)) * â˜ƒxx;
         }

         â˜ƒx /= 2.0;
         â˜ƒxx *= 2.0;
      }

      return â˜ƒ;
   }

   @Override
   public double getSurfaceNoiseValue(double var1, double var3, double var5, double var7) {
      return this.getValue(â˜ƒ, â˜ƒ, true) * 0.55;
   }
}
