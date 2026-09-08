package net.minecraft.world.level.chunk;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;
import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkBiomeContainer implements BiomeManager.NoiseBiomeSource {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int WIDTH_BITS = Mth.ceillog2(16) - 2;
   private static final int HORIZONTAL_MASK = (1 << WIDTH_BITS) - 1;
   public static final int MAX_SIZE = 1 << WIDTH_BITS + WIDTH_BITS + DimensionType.BITS_FOR_Y - 2;
   private final IdMap<Biome> biomeRegistry;
   private final Biome[] biomes;
   private final int quartMinY;
   private final int quartHeight;

   protected ChunkBiomeContainer(IdMap<Biome> var1, LevelHeightAccessor var2, Biome[] var3) {
      this.biomeRegistry = â˜ƒ;
      this.biomes = â˜ƒ;
      this.quartMinY = QuartPos.fromBlock(â˜ƒ.getMinBuildHeight());
      this.quartHeight = QuartPos.fromBlock(â˜ƒ.getHeight()) - 1;
   }

   public ChunkBiomeContainer(IdMap<Biome> var1, LevelHeightAccessor var2, int[] var3) {
      this(â˜ƒ, â˜ƒ, new Biome[â˜ƒ.length]);
      int â˜ƒ = -1;

      for(int â˜ƒx = 0; â˜ƒx < this.biomes.length; ++â˜ƒx) {
         int â˜ƒxx = â˜ƒ[â˜ƒx];
         Biome â˜ƒxxx = â˜ƒ.byId(â˜ƒxx);
         if (â˜ƒxxx == null) {
            if (â˜ƒ == -1) {
               â˜ƒ = â˜ƒx;
            }

            this.biomes[â˜ƒx] = â˜ƒ.byId(0);
         } else {
            this.biomes[â˜ƒx] = â˜ƒxxx;
         }
      }

      if (â˜ƒ != -1) {
         LOGGER.warn("Invalid biome data received, starting from {}: {}", â˜ƒ, Arrays.toString(â˜ƒ));
      }
   }

   public ChunkBiomeContainer(IdMap<Biome> var1, LevelHeightAccessor var2, ChunkPos var3, BiomeSource var4) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, null);
   }

   public ChunkBiomeContainer(IdMap<Biome> var1, LevelHeightAccessor var2, ChunkPos var3, BiomeSource var4, @Nullable int[] var5) {
      this(â˜ƒ, â˜ƒ, new Biome[(1 << WIDTH_BITS + WIDTH_BITS) * ceilDiv(â˜ƒ.getHeight(), 4)]);
      int â˜ƒ = QuartPos.fromBlock(â˜ƒ.getMinBlockX());
      int â˜ƒx = this.quartMinY;
      int â˜ƒxx = QuartPos.fromBlock(â˜ƒ.getMinBlockZ());

      for(int â˜ƒxxx = 0; â˜ƒxxx < this.biomes.length; ++â˜ƒxxx) {
         if (â˜ƒ != null && â˜ƒxxx < â˜ƒ.length) {
            this.biomes[â˜ƒxxx] = â˜ƒ.byId(â˜ƒ[â˜ƒxxx]);
         }

         if (this.biomes[â˜ƒxxx] == null) {
            this.biomes[â˜ƒxxx] = generateBiomeForIndex(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }
      }
   }

   private static int ceilDiv(int var0, int var1) {
      return (â˜ƒ + â˜ƒ - 1) / â˜ƒ;
   }

   private static Biome generateBiomeForIndex(BiomeSource var0, int var1, int var2, int var3, int var4) {
      int â˜ƒ = â˜ƒ & HORIZONTAL_MASK;
      int â˜ƒx = â˜ƒ >> WIDTH_BITS + WIDTH_BITS;
      int â˜ƒxx = â˜ƒ >> WIDTH_BITS & HORIZONTAL_MASK;
      return â˜ƒ.getNoiseBiome(â˜ƒ + â˜ƒ, â˜ƒ + â˜ƒx, â˜ƒ + â˜ƒxx);
   }

   public int[] writeBiomes() {
      int[] â˜ƒ = new int[this.biomes.length];

      for(int â˜ƒx = 0; â˜ƒx < this.biomes.length; ++â˜ƒx) {
         â˜ƒ[â˜ƒx] = this.biomeRegistry.getId(this.biomes[â˜ƒx]);
      }

      return â˜ƒ;
   }

   @Override
   public Biome getNoiseBiome(int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ & HORIZONTAL_MASK;
      int â˜ƒx = Mth.clamp(â˜ƒ - this.quartMinY, 0, this.quartHeight);
      int â˜ƒxx = â˜ƒ & HORIZONTAL_MASK;
      return this.biomes[â˜ƒx << WIDTH_BITS + WIDTH_BITS | â˜ƒxx << WIDTH_BITS | â˜ƒ];
   }
}
