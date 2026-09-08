package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;

public class FixedBiomeSource extends BiomeSource {
   public static final Codec<FixedBiomeSource> CODEC = Biome.CODEC
      .fieldOf("biome")
      .<FixedBiomeSource>xmap(FixedBiomeSource::new, var0 -> var0.biome)
      .stable()
      .codec();
   private final Supplier<Biome> biome;

   public FixedBiomeSource(Biome var1) {
      this(() -> â˜ƒ);
   }

   public FixedBiomeSource(Supplier<Biome> var1) {
      super(ImmutableList.of((Biome)â˜ƒ.get()));
      this.biome = â˜ƒ;
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
      return (Biome)this.biome.get();
   }

   @Nullable
   @Override
   public BlockPos findBiomeHorizontal(int var1, int var2, int var3, int var4, int var5, Predicate<Biome> var6, Random var7, boolean var8) {
      if (â˜ƒ.test((Biome)this.biome.get())) {
         return â˜ƒ ? new BlockPos(â˜ƒ, â˜ƒ, â˜ƒ) : new BlockPos(â˜ƒ - â˜ƒ + â˜ƒ.nextInt(â˜ƒ * 2 + 1), â˜ƒ, â˜ƒ - â˜ƒ + â˜ƒ.nextInt(â˜ƒ * 2 + 1));
      } else {
         return null;
      }
   }

   @Override
   public Set<Biome> getBiomesWithin(int var1, int var2, int var3, int var4) {
      return Sets.<Biome>newHashSet((Biome)this.biome.get());
   }
}
