package net.minecraft.util;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeighedRandom {
   static final Logger LOGGER = LogManager.getLogger();

   public static int getTotalWeight(List<? extends WeighedRandom.WeighedRandomItem> var0) {
      long â˜ƒ = 0L;

      for(WeighedRandom.WeighedRandomItem â˜ƒx : â˜ƒ) {
         â˜ƒ += (long)â˜ƒx.weight;
      }

      if (â˜ƒ > 2147483647L) {
         throw new IllegalArgumentException("Sum of weights must be <= 2147483647");
      } else {
         return (int)â˜ƒ;
      }
   }

   public static <T extends WeighedRandom.WeighedRandomItem> Optional<T> getRandomItem(Random var0, List<T> var1, int var2) {
      if (â˜ƒ < 0) {
         throw (IllegalArgumentException)Util.pauseInIde((T)(new IllegalArgumentException("Negative total weight in getRandomItem")));
      } else if (â˜ƒ == 0) {
         return Optional.empty();
      } else {
         int â˜ƒ = â˜ƒ.nextInt(â˜ƒ);
         return getWeightedItem(â˜ƒ, â˜ƒ);
      }
   }

   public static <T extends WeighedRandom.WeighedRandomItem> Optional<T> getWeightedItem(List<T> var0, int var1) {
      for(T â˜ƒ : â˜ƒ) {
         â˜ƒ -= â˜ƒ.weight;
         if (â˜ƒ < 0) {
            return Optional.of(â˜ƒ);
         }
      }

      return Optional.empty();
   }

   public static <T extends WeighedRandom.WeighedRandomItem> Optional<T> getRandomItem(Random var0, List<T> var1) {
      return getRandomItem(â˜ƒ, â˜ƒ, getTotalWeight(â˜ƒ));
   }

   public static class WeighedRandomItem {
      protected final int weight;

      public WeighedRandomItem(int var1) {
         if (â˜ƒ < 0) {
            throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("Weight should be >= 0"));
         } else {
            if (â˜ƒ == 0 && SharedConstants.IS_RUNNING_IN_IDE) {
               WeighedRandom.LOGGER.warn("Found 0 weight, make sure this is intentional!");
            }

            this.weight = â˜ƒ;
         }
      }
   }
}
