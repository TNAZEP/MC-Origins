package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public abstract class BiomeSource implements BiomeManager.NoiseBiomeSource {
   public static final Codec<BiomeSource> CODEC = Registry.BIOME_SOURCE.dispatchStable(BiomeSource::codec, Function.identity());
   protected final Map<StructureFeature<?>, Boolean> supportedStructures = Maps.newHashMap();
   protected final Set<BlockState> surfaceBlocks = Sets.<BlockState>newHashSet();
   protected final List<Biome> possibleBiomes;

   protected BiomeSource(Stream<Supplier<Biome>> var1) {
      this((List<Biome>)â˜ƒ.map(Supplier::get).collect(ImmutableList.toImmutableList()));
   }

   protected BiomeSource(List<Biome> var1) {
      this.possibleBiomes = â˜ƒ;
   }

   protected abstract Codec<? extends BiomeSource> codec();

   public abstract BiomeSource withSeed(long var1);

   public List<Biome> possibleBiomes() {
      return this.possibleBiomes;
   }

   public Set<Biome> getBiomesWithin(int var1, int var2, int var3, int var4) {
      int â˜ƒ = QuartPos.fromBlock(â˜ƒ - â˜ƒ);
      int â˜ƒx = QuartPos.fromBlock(â˜ƒ - â˜ƒ);
      int â˜ƒxx = QuartPos.fromBlock(â˜ƒ - â˜ƒ);
      int â˜ƒxxx = QuartPos.fromBlock(â˜ƒ + â˜ƒ);
      int â˜ƒxxxx = QuartPos.fromBlock(â˜ƒ + â˜ƒ);
      int â˜ƒxxxxx = QuartPos.fromBlock(â˜ƒ + â˜ƒ);
      int â˜ƒxxxxxx = â˜ƒxxx - â˜ƒ + 1;
      int â˜ƒxxxxxxx = â˜ƒxxxx - â˜ƒx + 1;
      int â˜ƒxxxxxxxx = â˜ƒxxxxx - â˜ƒxx + 1;
      Set<Biome> â˜ƒxxxxxxxxx = Sets.<Biome>newHashSet();

      for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx < â˜ƒxxxxxxxx; ++â˜ƒxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxxx) {
            for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒxxxxxxx; ++â˜ƒxxxxxxxxxxxx) {
               int â˜ƒxxxxxxxxxxxxx = â˜ƒ + â˜ƒxxxxxxxxxxx;
               int â˜ƒxxxxxxxxxxxxxx = â˜ƒx + â˜ƒxxxxxxxxxxxx;
               int â˜ƒxxxxxxxxxxxxxxx = â˜ƒxx + â˜ƒxxxxxxxxxx;
               â˜ƒxxxxxxxxx.add(this.getNoiseBiome(â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx));
            }
         }
      }

      return â˜ƒxxxxxxxxx;
   }

   @Nullable
   public BlockPos findBiomeHorizontal(int var1, int var2, int var3, int var4, Predicate<Biome> var5, Random var6) {
      return this.findBiomeHorizontal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 1, â˜ƒ, â˜ƒ, false);
   }

   @Nullable
   public BlockPos findBiomeHorizontal(int var1, int var2, int var3, int var4, int var5, Predicate<Biome> var6, Random var7, boolean var8) {
      int â˜ƒ = QuartPos.fromBlock(â˜ƒ);
      int â˜ƒx = QuartPos.fromBlock(â˜ƒ);
      int â˜ƒxx = QuartPos.fromBlock(â˜ƒ);
      int â˜ƒxxx = QuartPos.fromBlock(â˜ƒ);
      BlockPos â˜ƒxxxx = null;
      int â˜ƒxxxxx = 0;
      int â˜ƒxxxxxx = â˜ƒ ? 0 : â˜ƒxx;

      for(int â˜ƒxxxxxxx = â˜ƒxxxxxx; â˜ƒxxxxxxx <= â˜ƒxx; â˜ƒxxxxxxx += â˜ƒ) {
         for(int â˜ƒxxxxxxxx = -â˜ƒxxxxxxx; â˜ƒxxxxxxxx <= â˜ƒxxxxxxx; â˜ƒxxxxxxxx += â˜ƒ) {
            boolean â˜ƒxxxxxxxxx = Math.abs(â˜ƒxxxxxxxx) == â˜ƒxxxxxxx;

            for(int â˜ƒxxxxxxxxxx = -â˜ƒxxxxxxx; â˜ƒxxxxxxxxxx <= â˜ƒxxxxxxx; â˜ƒxxxxxxxxxx += â˜ƒ) {
               if (â˜ƒ) {
                  boolean â˜ƒxxxxxxxxxxx = Math.abs(â˜ƒxxxxxxxxxx) == â˜ƒxxxxxxx;
                  if (!â˜ƒxxxxxxxxxxx && !â˜ƒxxxxxxxxx) {
                     continue;
                  }
               }

               int â˜ƒxxxxxxxxxxx = â˜ƒ + â˜ƒxxxxxxxxxx;
               int â˜ƒxxxxxxxxxxxx = â˜ƒx + â˜ƒxxxxxxxx;
               if (â˜ƒ.test(this.getNoiseBiome(â˜ƒxxxxxxxxxxx, â˜ƒxxx, â˜ƒxxxxxxxxxxxx))) {
                  if (â˜ƒxxxx == null || â˜ƒ.nextInt(â˜ƒxxxxx + 1) == 0) {
                     â˜ƒxxxx = new BlockPos(QuartPos.toBlock(â˜ƒxxxxxxxxxxx), â˜ƒ, QuartPos.toBlock(â˜ƒxxxxxxxxxxxx));
                     if (â˜ƒ) {
                        return â˜ƒxxxx;
                     }
                  }

                  ++â˜ƒxxxxx;
               }
            }
         }
      }

      return â˜ƒxxxx;
   }

   public boolean canGenerateStructure(StructureFeature<?> var1) {
      return this.supportedStructures
         .computeIfAbsent(â˜ƒ, var1x -> this.possibleBiomes.stream().anyMatch(var1xx -> var1xx.getGenerationSettings().isValidStart(var1x)));
   }

   public Set<BlockState> getSurfaceBlocks() {
      if (this.surfaceBlocks.isEmpty()) {
         for(Biome â˜ƒ : this.possibleBiomes) {
            this.surfaceBlocks.add(â˜ƒ.getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial());
         }
      }

      return this.surfaceBlocks;
   }

   static {
      Registry.register(Registry.BIOME_SOURCE, "fixed", FixedBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "multi_noise", MultiNoiseBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "checkerboard", CheckerboardColumnBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "vanilla_layered", OverworldBiomeSource.CODEC);
      Registry.register(Registry.BIOME_SOURCE, "the_end", TheEndBiomeSource.CODEC);
   }
}
