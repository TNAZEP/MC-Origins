package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.function.Supplier;

public class CheckerboardColumnBiomeSource extends BiomeSource {
   public static final Codec<CheckerboardColumnBiomeSource> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Biome.LIST_CODEC.fieldOf("biomes").forGetter(var0x -> var0x.allowedBiomes),
               Codec.intRange(0, 62).fieldOf("scale").orElse(2).forGetter(var0x -> var0x.size)
            )
            .apply(var0, CheckerboardColumnBiomeSource::new)
   );
   private final List<Supplier<Biome>> allowedBiomes;
   private final int bitShift;
   private final int size;

   public CheckerboardColumnBiomeSource(List<Supplier<Biome>> var1, int var2) {
      super(â˜ƒ.stream());
      this.allowedBiomes = â˜ƒ;
      this.bitShift = â˜ƒ + 2;
      this.size = â˜ƒ;
   }

   @Override
   protected Codec<? extends BiomeSource> codec() {
      return CODEC;
   }

   @Override
   public BiomeSource withSeed(long var1) {
      return this;
   }

   @Override
   public Biome getNoiseBiome(int var1, int var2, int var3) {
      return (Biome)((Supplier)this.allowedBiomes.get(Math.floorMod((â˜ƒ >> this.bitShift) + (â˜ƒ >> this.bitShift), this.allowedBiomes.size()))).get();
   }
}
