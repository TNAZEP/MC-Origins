package net.minecraft.util.random;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.Util;

public class WeightedRandom {
   private WeightedRandom() {
   }

   public static int getTotalWeight(List<? extends WeightedEntry> var0) {
      long â˜ƒ = 0L;

      for(WeightedEntry â˜ƒx : â˜ƒ) {
         â˜ƒ += (long)â˜ƒx.getWeight().asInt();
      }

      if (â˜ƒ > 2147483647L) {
         throw new IllegalArgumentException("Sum of weights must be <= 2147483647");
      } else {
         return (int)â˜ƒ;
      }
   }

   public static <T extends WeightedEntry> Optional<T> getRandomItem(Random var0, List<T> var1, int var2) {
      if (â˜ƒ < 0) {
         throw (IllegalArgumentException)Util.pauseInIde((T)(new IllegalArgumentException("Negative total weight in getRandomItem")));
      } else if (â˜ƒ == 0) {
         return Optional.empty();
      } else {
         int â˜ƒ = â˜ƒ.nextInt(â˜ƒ);
         return getWeightedItem(â˜ƒ, â˜ƒ);
      }
   }

   public static <T extends WeightedEntry> Optional<T> getWeightedItem(List<T> var0, int var1) {
      for(T â˜ƒ : â˜ƒ) {
         â˜ƒ -= â˜ƒ.getWeight().asInt();
         if (â˜ƒ < 0) {
            return Optional.of(â˜ƒ);
         }
      }

      return Optional.empty();
   }

   public static <T extends WeightedEntry> Optional<T> getRandomItem(Random var0, List<T> var1) {
      return getRandomItem(â˜ƒ, â˜ƒ, getTotalWeight(â˜ƒ));
   }
}
