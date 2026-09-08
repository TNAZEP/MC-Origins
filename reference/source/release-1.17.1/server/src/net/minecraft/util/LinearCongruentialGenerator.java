package net.minecraft.util;

public class LinearCongruentialGenerator {
   private static final long MULTIPLIER = 6364136223846793005L;
   private static final long INCREMENT = 1442695040888963407L;

   public static long next(long var0, long var2) {
      â˜ƒ *= â˜ƒ * 6364136223846793005L + 1442695040888963407L;
      return â˜ƒ + â˜ƒ;
   }
}
