package net.minecraft.world.level.biome;

import net.minecraft.core.QuartPos;

public enum NearestNeighborBiomeZoomer implements BiomeZoomer {
   INSTANCE;

   @Override
   public Biome getBiome(long var1, int var3, int var4, int var5, BiomeManager.NoiseBiomeSource var6) {
      return â˜ƒ.getNoiseBiome(QuartPos.fromBlock(â˜ƒ), QuartPos.fromBlock(â˜ƒ), QuartPos.fromBlock(â˜ƒ));
   }
}
