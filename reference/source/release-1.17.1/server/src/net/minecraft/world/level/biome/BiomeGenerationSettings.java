package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeGenerationSettings {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final BiomeGenerationSettings EMPTY = new BiomeGenerationSettings(
      () -> SurfaceBuilders.NOPE, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of()
   );
   public static final MapCodec<BiomeGenerationSettings> CODEC = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               ConfiguredSurfaceBuilder.CODEC
                  .fieldOf("surface_builder")
                  .flatXmap(ExtraCodecs.nonNullSupplierCheck(), ExtraCodecs.nonNullSupplierCheck())
                  .forGetter(var0x -> var0x.surfaceBuilder),
               Codec.simpleMap(
                     GenerationStep.Carving.CODEC,
                     ConfiguredWorldCarver.LIST_CODEC
                        .promotePartial(Util.prefix("Carver: ", LOGGER::error))
                        .flatXmap(ExtraCodecs.nonNullSupplierListCheck(), ExtraCodecs.nonNullSupplierListCheck()),
                     StringRepresentable.keys(GenerationStep.Carving.values())
                  )
                  .fieldOf("carvers")
                  .forGetter(var0x -> var0x.carvers),
               ConfiguredFeature.LIST_CODEC
                  .promotePartial(Util.prefix("Feature: ", LOGGER::error))
                  .flatXmap(ExtraCodecs.nonNullSupplierListCheck(), ExtraCodecs.nonNullSupplierListCheck())
                  .listOf()
                  .fieldOf("features")
                  .forGetter(var0x -> var0x.features),
               ConfiguredStructureFeature.LIST_CODEC
                  .promotePartial(Util.prefix("Structure start: ", LOGGER::error))
                  .fieldOf("starts")
                  .flatXmap(ExtraCodecs.nonNullSupplierListCheck(), ExtraCodecs.nonNullSupplierListCheck())
                  .forGetter(var0x -> var0x.structureStarts)
            )
            .apply(var0, BiomeGenerationSettings::new)
   );
   private final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilder;
   private final Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>> carvers;
   private final List<List<Supplier<ConfiguredFeature<?, ?>>>> features;
   private final List<Supplier<ConfiguredStructureFeature<?, ?>>> structureStarts;
   private final List<ConfiguredFeature<?, ?>> flowerFeatures;

   BiomeGenerationSettings(
      Supplier<ConfiguredSurfaceBuilder<?>> var1,
      Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>> var2,
      List<List<Supplier<ConfiguredFeature<?, ?>>>> var3,
      List<Supplier<ConfiguredStructureFeature<?, ?>>> var4
   ) {
      this.surfaceBuilder = â˜ƒ;
      this.carvers = â˜ƒ;
      this.features = â˜ƒ;
      this.structureStarts = â˜ƒ;
      this.flowerFeatures = (List)â˜ƒ.stream()
         .flatMap(Collection::stream)
         .map(Supplier::get)
         .flatMap(ConfiguredFeature::getFeatures)
         .filter(var0 -> var0.feature == Feature.FLOWER)
         .collect(ImmutableList.toImmutableList());
   }

   public List<Supplier<ConfiguredWorldCarver<?>>> getCarvers(GenerationStep.Carving var1) {
      return (List<Supplier<ConfiguredWorldCarver<?>>>)this.carvers.getOrDefault(â˜ƒ, ImmutableList.of());
   }

   public boolean isValidStart(StructureFeature<?> var1) {
      return this.structureStarts.stream().anyMatch(var1x -> ((ConfiguredStructureFeature)var1x.get()).feature == â˜ƒ);
   }

   public Collection<Supplier<ConfiguredStructureFeature<?, ?>>> structures() {
      return this.structureStarts;
   }

   public ConfiguredStructureFeature<?, ?> withBiomeConfig(ConfiguredStructureFeature<?, ?> var1) {
      return DataFixUtils.orElse(this.structureStarts.stream().map(Supplier::get).filter(var1x -> var1x.feature == â˜ƒ.feature).findAny(), â˜ƒ);
   }

   public List<ConfiguredFeature<?, ?>> getFlowerFeatures() {
      return this.flowerFeatures;
   }

   public List<List<Supplier<ConfiguredFeature<?, ?>>>> features() {
      return this.features;
   }

   public Supplier<ConfiguredSurfaceBuilder<?>> getSurfaceBuilder() {
      return this.surfaceBuilder;
   }

   public SurfaceBuilderConfiguration getSurfaceBuilderConfig() {
      return ((ConfiguredSurfaceBuilder)this.surfaceBuilder.get()).config();
   }

   public static class Builder {
      private Optional<Supplier<ConfiguredSurfaceBuilder<?>>> surfaceBuilder = Optional.empty();
      private final Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>> carvers = Maps.newLinkedHashMap();
      private final List<List<Supplier<ConfiguredFeature<?, ?>>>> features = Lists.newArrayList();
      private final List<Supplier<ConfiguredStructureFeature<?, ?>>> structureStarts = Lists.newArrayList();

      public BiomeGenerationSettings.Builder surfaceBuilder(ConfiguredSurfaceBuilder<?> var1) {
         return this.surfaceBuilder(() -> â˜ƒ);
      }

      public BiomeGenerationSettings.Builder surfaceBuilder(Supplier<ConfiguredSurfaceBuilder<?>> var1) {
         this.surfaceBuilder = Optional.of(â˜ƒ);
         return this;
      }

      public BiomeGenerationSettings.Builder addFeature(GenerationStep.Decoration var1, ConfiguredFeature<?, ?> var2) {
         return this.addFeature(â˜ƒ.ordinal(), () -> â˜ƒ);
      }

      public BiomeGenerationSettings.Builder addFeature(int var1, Supplier<ConfiguredFeature<?, ?>> var2) {
         this.addFeatureStepsUpTo(â˜ƒ);
         ((List)this.features.get(â˜ƒ)).add(â˜ƒ);
         return this;
      }

      public <C extends CarverConfiguration> BiomeGenerationSettings.Builder addCarver(GenerationStep.Carving var1, ConfiguredWorldCarver<C> var2) {
         ((List)this.carvers.computeIfAbsent(â˜ƒ, var0 -> Lists.newArrayList())).add((Supplier)() -> â˜ƒ);
         return this;
      }

      public BiomeGenerationSettings.Builder addStructureStart(ConfiguredStructureFeature<?, ?> var1) {
         this.structureStarts.add((Supplier)() -> â˜ƒ);
         return this;
      }

      private void addFeatureStepsUpTo(int var1) {
         while(this.features.size() <= â˜ƒ) {
            this.features.add(Lists.newArrayList());
         }
      }

      public BiomeGenerationSettings build() {
         return new BiomeGenerationSettings(
            (Supplier<ConfiguredSurfaceBuilder<?>>)this.surfaceBuilder.orElseThrow(() -> new IllegalStateException("Missing surface builder")),
            (Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>>)this.carvers
               .entrySet()
               .stream()
               .collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ImmutableList.copyOf((Collection)var0.getValue()))),
            (List<List<Supplier<ConfiguredFeature<?, ?>>>>)this.features.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()),
            ImmutableList.copyOf(this.structureStarts)
         );
      }
   }
}
