package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class TheEndBiomeSource extends BiomeSource {
   public static final Codec<TheEndBiomeSource> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(var0x -> var0x.biomes),
               Codec.LONG.fieldOf("seed").stable().forGetter(var0x -> var0x.seed)
            )
            .apply(var0, var0.stable(TheEndBiomeSource::new))
   );
   private static final float ISLAND_THRESHOLD = -0.9F;
   public static final int ISLAND_CHUNK_DISTANCE = 64;
   private static final long ISLAND_CHUNK_DISTANCE_SQR = 4096L;
   private final SimplexNoise islandNoise;
   private final Registry<Biome> biomes;
   private final long seed;
   private final Biome end;
   private final Biome highlands;
   private final Biome midlands;
   private final Biome islands;
   private final Biome barrens;

   public TheEndBiomeSource(Registry<Biome> var1, long var2) {
      this(
         â˜ƒ,
         â˜ƒ,
         â˜ƒ.getOrThrow(Biomes.THE_END),
         â˜ƒ.getOrThrow(Biomes.END_HIGHLANDS),
         â˜ƒ.getOrThrow(Biomes.END_MIDLANDS),
         â˜ƒ.getOrThrow(Biomes.SMALL_END_ISLANDS),
         â˜ƒ.getOrThrow(Biomes.END_BARRENS)
      );
   }

   private TheEndBiomeSource(Registry<Biome> var1, long var2, Biome var4, Biome var5, Biome var6, Biome var7, Biome var8) {
      super(ImmutableList.of(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      this.biomes = â˜ƒ;
      this.seed = â˜ƒ;
      this.end = â˜ƒ;
      this.highlands = â˜ƒ;
      this.midlands = â˜ƒ;
      this.islands = â˜ƒ;
      this.barrens = â˜ƒ;
      WorldgenRandom â˜ƒ = new WorldgenRandom(â˜ƒ);
      â˜ƒ.consumeCount(17292);
      this.islandNoise = new SimplexNoise(â˜ƒ);
   }

   @Override
   protected Codec<? extends BiomeSource> codec() {
      return CODEC;
   }

   @Override
   public BiomeSource withSeed(long var1) {
      return new TheEndBiomeSource(this.biomes, â˜ƒ, this.end, this.highlands, this.midlands, this.islands, this.barrens);
   }

   @Override
   public Biome getNoiseBiome(int var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ >> 2;
      int â˜ƒx = â˜ƒ >> 2;
      if ((long)â˜ƒ * (long)â˜ƒ + (long)â˜ƒx * (long)â˜ƒx <= 4096L) {
         return this.end;
      } else {
         float â˜ƒ = getHeightValue(this.islandNoise, â˜ƒ * 2 + 1, â˜ƒx * 2 + 1);
         if (â˜ƒ > 40.0F) {
            return this.highlands;
         } else if (â˜ƒ >= 0.0F) {
            return this.midlands;
         } else {
            return â˜ƒ < -20.0F ? this.islands : this.barrens;
         }
      }
   }

   public boolean stable(long var1) {
      return this.seed == â˜ƒ;
   }

   public static float getHeightValue(SimplexNoise var0, int var1, int var2) {
      int â˜ƒ = â˜ƒ / 2;
      int â˜ƒx = â˜ƒ / 2;
      int â˜ƒxx = â˜ƒ % 2;
      int â˜ƒxxx = â˜ƒ % 2;
      float â˜ƒxxxx = 100.0F - Mth.sqrt((float)(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ)) * 8.0F;
      â˜ƒxxxx = Mth.clamp(â˜ƒxxxx, -100.0F, 80.0F);

      for(int â˜ƒxxxxx = -12; â˜ƒxxxxx <= 12; ++â˜ƒxxxxx) {
         for(int â˜ƒxxxxxx = -12; â˜ƒxxxxxx <= 12; ++â˜ƒxxxxxx) {
            long â˜ƒxxxxxxx = (long)(â˜ƒ + â˜ƒxxxxx);
            long â˜ƒxxxxxxxx = (long)(â˜ƒx + â˜ƒxxxxxx);
            if (â˜ƒxxxxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxxxx * â˜ƒxxxxxxxx > 4096L && â˜ƒ.getValue((double)â˜ƒxxxxxxx, (double)â˜ƒxxxxxxxx) < -0.9F) {
               float â˜ƒxxxxxxxxx = (Mth.abs((float)â˜ƒxxxxxxx) * 3439.0F + Mth.abs((float)â˜ƒxxxxxxxx) * 147.0F) % 13.0F + 9.0F;
               float â˜ƒxxxxxxxxxx = (float)(â˜ƒxx - â˜ƒxxxxx * 2);
               float â˜ƒxxxxxxxxxxx = (float)(â˜ƒxxx - â˜ƒxxxxxx * 2);
               float â˜ƒxxxxxxxxxxxx = 100.0F - Mth.sqrt(â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxxxx * â˜ƒxxxxxxxxxxx) * â˜ƒxxxxxxxxx;
               â˜ƒxxxxxxxxxxxx = Mth.clamp(â˜ƒxxxxxxxxxxxx, -100.0F, 80.0F);
               â˜ƒxxxx = Math.max(â˜ƒxxxx, â˜ƒxxxxxxxxxxxx);
            }
         }
      }

      return â˜ƒxxxx;
   }
}
