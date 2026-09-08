package net.minecraft.world.level.biome;

public enum FuzzyOffsetConstantColumnBiomeZoomer implements BiomeZoomer {
   INSTANCE;

   @Override
   public Biome getBiome(long var1, int var3, int var4, int var5, BiomeManager.NoiseBiomeSource var6) {
      return FuzzyOffsetBiomeZoomer.INSTANCE.getBiome(â˜ƒ, â˜ƒ, 0, â˜ƒ, â˜ƒ);
   }
}
