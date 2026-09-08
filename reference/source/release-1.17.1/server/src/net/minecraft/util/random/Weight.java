package net.minecraft.util.random;

import com.mojang.serialization.Codec;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Weight {
   public static final Codec<Weight> CODEC = Codec.INT.xmap(Weight::of, Weight::asInt);
   private static final Weight ONE = new Weight(1);
   private static final Logger LOGGER = LogManager.getLogger();
   private final int value;

   private Weight(int var1) {
      this.value = â˜ƒ;
   }

   public static Weight of(int var0) {
      if (â˜ƒ == 1) {
         return ONE;
      } else {
         validateWeight(â˜ƒ);
         return new Weight(â˜ƒ);
      }
   }

   public int asInt() {
      return this.value;
   }

   private static void validateWeight(int var0) {
      if (â˜ƒ < 0) {
         throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("Weight should be >= 0"));
      } else {
         if (â˜ƒ == 0 && SharedConstants.IS_RUNNING_IN_IDE) {
            LOGGER.warn("Found 0 weight, make sure this is intentional!");
         }
      }
   }

   public String toString() {
      return Integer.toString(this.value);
   }

   public int hashCode() {
      return Integer.hashCode(this.value);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else {
         return â˜ƒ instanceof Weight && this.value == ((Weight)â˜ƒ).value;
      }
   }
}
