package net.minecraft.world.level.biome;

import com.google.common.hash.Hashing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;

public class BiomeManager {
   static final int CHUNK_CENTER_QUART = QuartPos.fromBlock(8);
   private final BiomeManager.NoiseBiomeSource noiseBiomeSource;
   private final long biomeZoomSeed;
   private final BiomeZoomer zoomer;

   public BiomeManager(BiomeManager.NoiseBiomeSource var1, long var2, BiomeZoomer var4) {
      this.noiseBiomeSource = â˜ƒ;
      this.biomeZoomSeed = â˜ƒ;
      this.zoomer = â˜ƒ;
   }

   public static long obfuscateSeed(long var0) {
      return Hashing.sha256().hashLong(â˜ƒ).asLong();
   }

   public BiomeManager withDifferentSource(BiomeSource var1) {
      return new BiomeManager(â˜ƒ, this.biomeZoomSeed, this.zoomer);
   }

   public Biome getBiome(BlockPos var1) {
      return this.zoomer.getBiome(this.biomeZoomSeed, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), this.noiseBiomeSource);
   }

   public Biome getNoiseBiomeAtPosition(double var1, double var3, double var5) {
      int â˜ƒ = QuartPos.fromBlock(Mth.floor(â˜ƒ));
      int â˜ƒx = QuartPos.fromBlock(Mth.floor(â˜ƒ));
      int â˜ƒxx = QuartPos.fromBlock(Mth.floor(â˜ƒ));
      return this.getNoiseBiomeAtQuart(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public Biome getNoiseBiomeAtPosition(BlockPos var1) {
      int â˜ƒ = QuartPos.fromBlock(â˜ƒ.getX());
      int â˜ƒx = QuartPos.fromBlock(â˜ƒ.getY());
      int â˜ƒxx = QuartPos.fromBlock(â˜ƒ.getZ());
      return this.getNoiseBiomeAtQuart(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public Biome getNoiseBiomeAtQuart(int var1, int var2, int var3) {
      return this.noiseBiomeSource.getNoiseBiome(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Biome getPrimaryBiomeAtChunk(ChunkPos var1) {
      return this.noiseBiomeSource.getPrimaryBiome(â˜ƒ);
   }

   public interface NoiseBiomeSource {
      Biome getNoiseBiome(int var1, int var2, int var3);

      default Biome getPrimaryBiome(ChunkPos var1) {
         return this.getNoiseBiome(
            QuartPos.fromSection(â˜ƒ.x) + BiomeManager.CHUNK_CENTER_QUART, 0, QuartPos.fromSection(â˜ƒ.z) + BiomeManager.CHUNK_CENTER_QUART
         );
      }
   }
}
