package net.minecraft.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RegistryAccess {
   private static final Logger LOGGER = LogManager.getLogger();
   static final Map<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> REGISTRIES = Util.make(() -> {
      Builder<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> â˜ƒ = ImmutableMap.builder();
      put(â˜ƒ, Registry.DIMENSION_TYPE_REGISTRY, DimensionType.DIRECT_CODEC, DimensionType.DIRECT_CODEC);
      put(â˜ƒ, Registry.BIOME_REGISTRY, Biome.DIRECT_CODEC, Biome.NETWORK_CODEC);
      put(â˜ƒ, Registry.CONFIGURED_SURFACE_BUILDER_REGISTRY, ConfiguredSurfaceBuilder.DIRECT_CODEC);
      put(â˜ƒ, Registry.CONFIGURED_CARVER_REGISTRY, ConfiguredWorldCarver.DIRECT_CODEC);
      put(â˜ƒ, Registry.CONFIGURED_FEATURE_REGISTRY, ConfiguredFeature.DIRECT_CODEC);
      put(â˜ƒ, Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, ConfiguredStructureFeature.DIRECT_CODEC);
      put(â˜ƒ, Registry.PROCESSOR_LIST_REGISTRY, StructureProcessorType.DIRECT_CODEC);
      put(â˜ƒ, Registry.TEMPLATE_POOL_REGISTRY, StructureTemplatePool.DIRECT_CODEC);
      put(â˜ƒ, Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, NoiseGeneratorSettings.DIRECT_CODEC);
      return â˜ƒ.build();
   });
   private static final RegistryAccess.RegistryHolder BUILTIN = Util.make(() -> {
      RegistryAccess.RegistryHolder â˜ƒ = new RegistryAccess.RegistryHolder();
      DimensionType.registerBuiltin(â˜ƒ);
      REGISTRIES.keySet().stream().filter(var0x -> !var0x.equals(Registry.DIMENSION_TYPE_REGISTRY)).forEach(var1 -> copyBuiltin(â˜ƒ, var1));
      return â˜ƒ;
   });

   public abstract <E> Optional<WritableRegistry<E>> ownedRegistry(ResourceKey<? extends Registry<? extends E>> var1);

   public <E> WritableRegistry<E> ownedRegistryOrThrow(ResourceKey<? extends Registry<? extends E>> var1) {
      return (WritableRegistry<E>)this.ownedRegistry(â˜ƒ).orElseThrow(() -> new IllegalStateException("Missing registry: " + â˜ƒ));
   }

   public <E> Optional<? extends Registry<E>> registry(ResourceKey<? extends Registry<? extends E>> var1) {
      Optional<? extends Registry<E>> â˜ƒ = this.ownedRegistry(â˜ƒ);
      return â˜ƒ.isPresent() ? â˜ƒ : Registry.REGISTRY.getOptional(â˜ƒ.location());
   }

   public <E> Registry<E> registryOrThrow(ResourceKey<? extends Registry<? extends E>> var1) {
      return (Registry<E>)this.registry(â˜ƒ).orElseThrow(() -> new IllegalStateException("Missing registry: " + â˜ƒ));
   }

   private static <E> void put(
      Builder<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> var0, ResourceKey<? extends Registry<E>> var1, Codec<E> var2
   ) {
      â˜ƒ.put(â˜ƒ, new RegistryAccess.RegistryData<>(â˜ƒ, â˜ƒ, null));
   }

   private static <E> void put(
      Builder<ResourceKey<? extends Registry<?>>, RegistryAccess.RegistryData<?>> var0, ResourceKey<? extends Registry<E>> var1, Codec<E> var2, Codec<E> var3
   ) {
      â˜ƒ.put(â˜ƒ, new RegistryAccess.RegistryData<>(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static RegistryAccess.RegistryHolder builtin() {
      RegistryAccess.RegistryHolder â˜ƒ = new RegistryAccess.RegistryHolder();
      RegistryReadOps.ResourceAccess.MemoryMap â˜ƒx = new RegistryReadOps.ResourceAccess.MemoryMap();

      for(RegistryAccess.RegistryData<?> â˜ƒxx : REGISTRIES.values()) {
         addBuiltinElements(â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      RegistryReadOps.createAndLoad(JsonOps.INSTANCE, â˜ƒx, â˜ƒ);
      return â˜ƒ;
   }

   private static <E> void addBuiltinElements(
      RegistryAccess.RegistryHolder var0, RegistryReadOps.ResourceAccess.MemoryMap var1, RegistryAccess.RegistryData<E> var2
   ) {
      ResourceKey<? extends Registry<E>> â˜ƒ = â˜ƒ.key();
      boolean â˜ƒx = !â˜ƒ.equals(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY) && !â˜ƒ.equals(Registry.DIMENSION_TYPE_REGISTRY);
      Registry<E> â˜ƒxx = BUILTIN.registryOrThrow(â˜ƒ);
      WritableRegistry<E> â˜ƒxxx = â˜ƒ.ownedRegistryOrThrow(â˜ƒ);

      for(Entry<ResourceKey<E>, E> â˜ƒxxxx : â˜ƒxx.entrySet()) {
         ResourceKey<E> â˜ƒxxxxx = (ResourceKey)â˜ƒxxxx.getKey();
         E â˜ƒxxxxxx = (E)â˜ƒxxxx.getValue();
         if (â˜ƒx) {
            â˜ƒ.add(BUILTIN, â˜ƒxxxxx, â˜ƒ.codec(), â˜ƒxx.getId(â˜ƒxxxxxx), â˜ƒxxxxxx, â˜ƒxx.lifecycle(â˜ƒxxxxxx));
         } else {
            â˜ƒxxx.registerMapping(â˜ƒxx.getId(â˜ƒxxxxxx), â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxx.lifecycle(â˜ƒxxxxxx));
         }
      }
   }

   private static <R extends Registry<?>> void copyBuiltin(RegistryAccess.RegistryHolder var0, ResourceKey<R> var1) {
      Registry<R> â˜ƒ = BuiltinRegistries.REGISTRY;
      Registry<?> â˜ƒx = â˜ƒ.getOrThrow(â˜ƒ);
      copy(â˜ƒ, â˜ƒx);
   }

   private static <E> void copy(RegistryAccess.RegistryHolder var0, Registry<E> var1) {
      WritableRegistry<E> â˜ƒ = â˜ƒ.ownedRegistryOrThrow(â˜ƒ.key());

      for(Entry<ResourceKey<E>, E> â˜ƒx : â˜ƒ.entrySet()) {
         E â˜ƒxx = (E)â˜ƒx.getValue();
         â˜ƒ.registerMapping(â˜ƒ.getId(â˜ƒxx), (ResourceKey<E>)â˜ƒx.getKey(), â˜ƒxx, â˜ƒ.lifecycle(â˜ƒxx));
      }
   }

   public static void load(RegistryAccess var0, RegistryReadOps<?> var1) {
      for(RegistryAccess.RegistryData<?> â˜ƒ : REGISTRIES.values()) {
         readRegistry(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static <E> void readRegistry(RegistryReadOps<?> var0, RegistryAccess var1, RegistryAccess.RegistryData<E> var2) {
      ResourceKey<? extends Registry<E>> â˜ƒ = â˜ƒ.key();
      MappedRegistry<E> â˜ƒx = (MappedRegistry)â˜ƒ.<E>ownedRegistryOrThrow(â˜ƒ);
      DataResult<MappedRegistry<E>> â˜ƒxx = â˜ƒ.decodeElements(â˜ƒx, â˜ƒ.key(), â˜ƒ.codec());
      â˜ƒxx.error().ifPresent(var0x -> {
         throw new JsonParseException("Error loading registry data: " + var0x.message());
      });
   }

   static final class RegistryData<E> {
      private final ResourceKey<? extends Registry<E>> key;
      private final Codec<E> codec;
      @Nullable
      private final Codec<E> networkCodec;

      public RegistryData(ResourceKey<? extends Registry<E>> var1, Codec<E> var2, @Nullable Codec<E> var3) {
         this.key = â˜ƒ;
         this.codec = â˜ƒ;
         this.networkCodec = â˜ƒ;
      }

      public ResourceKey<? extends Registry<E>> key() {
         return this.key;
      }

      public Codec<E> codec() {
         return this.codec;
      }

      @Nullable
      public Codec<E> networkCodec() {
         return this.networkCodec;
      }

      public boolean sendToClient() {
         return this.networkCodec != null;
      }
   }

   public static final class RegistryHolder extends RegistryAccess {
      public static final Codec<RegistryAccess.RegistryHolder> NETWORK_CODEC = makeNetworkCodec();
      private final Map<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>> registries;

      private static <E> Codec<RegistryAccess.RegistryHolder> makeNetworkCodec() {
         Codec<ResourceKey<? extends Registry<E>>> â˜ƒ = ResourceLocation.CODEC.xmap(ResourceKey::createRegistryKey, ResourceKey::location);
         Codec<MappedRegistry<E>> â˜ƒx = â˜ƒ.partialDispatch(
            "type",
            var0x -> DataResult.success(var0x.key()),
            var0x -> getNetworkCodec(var0x).map(var1x -> MappedRegistry.networkCodec(var0x, Lifecycle.experimental(), var1x))
         );
         UnboundedMapCodec<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>> â˜ƒxx = Codec.unboundedMap(â˜ƒ, â˜ƒx);
         return captureMap(â˜ƒxx);
      }

      private static <K extends ResourceKey<? extends Registry<?>>, V extends MappedRegistry<?>> Codec<RegistryAccess.RegistryHolder> captureMap(
         UnboundedMapCodec<K, V> var0
      ) {
         return â˜ƒ.xmap(
            RegistryAccess.RegistryHolder::new,
            var0x -> (Map)var0x.registries
                  .entrySet()
                  .stream()
                  .filter(var0xx -> ((RegistryAccess.RegistryData)RegistryAccess.REGISTRIES.get(var0xx.getKey())).sendToClient())
                  .collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue))
         );
      }

      private static <E> DataResult<? extends Codec<E>> getNetworkCodec(ResourceKey<? extends Registry<E>> var0) {
         return (DataResult<? extends Codec<E>>)Optional.ofNullable((RegistryAccess.RegistryData)RegistryAccess.REGISTRIES.get(â˜ƒ))
            .map(var0x -> var0x.networkCodec())
            .map(DataResult::success)
            .orElseGet(() -> DataResult.error("Unknown or not serializable registry: " + â˜ƒ));
      }

      public RegistryHolder() {
         this(
            (Map<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>>)RegistryAccess.REGISTRIES
               .keySet()
               .stream()
               .collect(Collectors.toMap(Function.identity(), RegistryAccess.RegistryHolder::createRegistry))
         );
      }

      private RegistryHolder(Map<? extends ResourceKey<? extends Registry<?>>, ? extends MappedRegistry<?>> var1) {
         this.registries = â˜ƒ;
      }

      private static <E> MappedRegistry<?> createRegistry(ResourceKey<? extends Registry<?>> var0) {
         return new MappedRegistry<>(â˜ƒ, Lifecycle.stable());
      }

      @Override
      public <E> Optional<WritableRegistry<E>> ownedRegistry(ResourceKey<? extends Registry<? extends E>> var1) {
         return Optional.ofNullable((MappedRegistry)this.registries.get(â˜ƒ)).map(var0 -> var0);
      }
   }
}
