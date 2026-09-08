package net.minecraft.world.level.biome;

import net.minecraft.util.LinearCongruentialGenerator;

public enum FuzzyOffsetBiomeZoomer implements BiomeZoomer {
   INSTANCE;

   private static final int ZOOM_BITS = 2;
   private static final int ZOOM = 4;
   private static final int ZOOM_MASK = 3;

   @Override
   public Biome getBiome(long var1, int var3, int var4, int var5, BiomeManager.NoiseBiomeSource var6) {
      int â˜ƒ = â˜ƒ - 2;
      int â˜ƒx = â˜ƒ - 2;
      int â˜ƒxx = â˜ƒ - 2;
      int â˜ƒxxx = â˜ƒ >> 2;
      int â˜ƒxxxx = â˜ƒx >> 2;
      int â˜ƒxxxxx = â˜ƒxx >> 2;
      double â˜ƒxxxxxx = (double)(â˜ƒ & 3) / 4.0;
      double â˜ƒxxxxxxx = (double)(â˜ƒx & 3) / 4.0;
      double â˜ƒxxxxxxxx = (double)(â˜ƒxx & 3) / 4.0;
      int â˜ƒxxxxxxxxx = 0;
      double â˜ƒxxxxxxxxxx = Double.POSITIVE_INFINITY;

      for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < 8; ++â˜ƒxxxxxxxxxxx) {
         boolean â˜ƒxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxx & 4) == 0;
         boolean â˜ƒxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxx & 2) == 0;
         boolean â˜ƒxxxxxxxxxxxxxx = (â˜ƒxxxxxxxxxxx & 1) == 0;
         int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx ? â˜ƒxxx : â˜ƒxxx + 1;
         int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx ? â˜ƒxxxx : â˜ƒxxxx + 1;
         int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx ? â˜ƒxxxxx : â˜ƒxxxxx + 1;
         double â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx ? â˜ƒxxxxxx : â˜ƒxxxxxx - 1.0;
         double â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx ? â˜ƒxxxxxxx : â˜ƒxxxxxxx - 1.0;
         double â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx ? â˜ƒxxxxxxxx : â˜ƒxxxxxxxx - 1.0;
         double â˜ƒxxxxxxxxxxxxxxxxxxxxx = getFiddledDistance(
            â˜ƒ, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx
         );
         if (â˜ƒxxxxxxxxxx > â˜ƒxxxxxxxxxxxxxxxxxxxxx) {
            â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxx;
            â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxx;
         }
      }

      int â˜ƒxxxxxxxxxxx = (â˜ƒxxxxxxxxx & 4) == 0 ? â˜ƒxxx : â˜ƒxxx + 1;
      int â˜ƒxxxxxxxxxxxx = (â˜ƒxxxxxxxxx & 2) == 0 ? â˜ƒxxxx : â˜ƒxxxx + 1;
      int â˜ƒxxxxxxxxxxxxx = (â˜ƒxxxxxxxxx & 1) == 0 ? â˜ƒxxxxx : â˜ƒxxxxx + 1;
      return â˜ƒ.getNoiseBiome(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx);
   }

   private static double getFiddledDistance(long var0, int var2, int var3, int var4, double var5, double var7, double var9) {
      long var11 = LinearCongruentialGenerator.next(â˜ƒ, (long)â˜ƒ);
      var11 = LinearCongruentialGenerator.next(var11, (long)â˜ƒ);
      var11 = LinearCongruentialGenerator.next(var11, (long)â˜ƒ);
      var11 = LinearCongruentialGenerator.next(var11, (long)â˜ƒ);
      var11 = LinearCongruentialGenerator.next(var11, (long)â˜ƒ);
      var11 = LinearCongruentialGenerator.next(var11, (long)â˜ƒ);
      double â˜ƒ = getFiddle(var11);
      var11 = LinearCongruentialGenerator.next(var11, â˜ƒ);
      double â˜ƒx = getFiddle(var11);
      var11 = LinearCongruentialGenerator.next(var11, â˜ƒ);
      double â˜ƒxx = getFiddle(var11);
      return sqr(â˜ƒ + â˜ƒxx) + sqr(â˜ƒ + â˜ƒx) + sqr(â˜ƒ + â˜ƒ);
   }

   private static double getFiddle(long var0) {
      double â˜ƒ = (double)Math.floorMod(â˜ƒ >> 24, 1024) / 1024.0;
      return (â˜ƒ - 0.5) * 0.9;
   }

   private static double sqr(double var0) {
      return â˜ƒ * â˜ƒ;
   }
}
