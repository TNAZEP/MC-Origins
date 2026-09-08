package net.minecraft.resources;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryReadOps<T> extends DelegatingOps<T> {
   static final Logger LOGGER = LogManager.getLogger();
   private static final String JSON = ".json";
   private final RegistryReadOps.ResourceAccess resources;
   private final RegistryAccess registryAccess;
   private final Map<ResourceKey<? extends Registry<?>>, RegistryReadOps.ReadCache<?>> readCache;
   private final RegistryReadOps<JsonElement> jsonOps;

   public static <T> RegistryReadOps<T> createAndLoad(DynamicOps<T> var0, ResourceManager var1, RegistryAccess var2) {
      return createAndLoad(â˜ƒ, RegistryReadOps.ResourceAccess.forResourceManager(â˜ƒ), â˜ƒ);
   }

   public static <T> RegistryReadOps<T> createAndLoad(DynamicOps<T> var0, RegistryReadOps.ResourceAccess var1, RegistryAccess var2) {
      RegistryReadOps<T> â˜ƒ = new RegistryReadOps<>(â˜ƒ, â˜ƒ, â˜ƒ, Maps.newIdentityHashMap());
      RegistryAccess.load(â˜ƒ, â˜ƒ);
      return â˜ƒ;
   }

   public static <T> RegistryReadOps<T> create(DynamicOps<T> var0, ResourceManager var1, RegistryAccess var2) {
      return create(â˜ƒ, RegistryReadOps.ResourceAccess.forResourceManager(â˜ƒ), â˜ƒ);
   }

   public static <T> RegistryReadOps<T> create(DynamicOps<T> var0, RegistryReadOps.ResourceAccess var1, RegistryAccess var2) {
      return new RegistryReadOps<>(â˜ƒ, â˜ƒ, â˜ƒ, Maps.newIdentityHashMap());
   }

   private RegistryReadOps(
      DynamicOps<T> var1,
      RegistryReadOps.ResourceAccess var2,
      RegistryAccess var3,
      IdentityHashMap<ResourceKey<? extends Registry<?>>, RegistryReadOps.ReadCache<?>> var4
   ) {
      super(â˜ƒ);
      this.resources = â˜ƒ;
      this.registryAccess = â˜ƒ;
      this.readCache = â˜ƒ;
      this.jsonOps = â˜ƒ == JsonOps.INSTANCE ? this : new RegistryReadOps<>(JsonOps.INSTANCE, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> decodeElement(T var1, ResourceKey<? extends Registry<E>> var2, Codec<E> var3, boolean var4) {
      Optional<WritableRegistry<E>> â˜ƒ = this.registryAccess.ownedRegistry(â˜ƒ);
      if (!â˜ƒ.isPresent()) {
         return DataResult.error("Unknown registry: " + â˜ƒ);
      } else {
         WritableRegistry<E> â˜ƒ = (WritableRegistry)â˜ƒ.get();
         DataResult<Pair<ResourceLocation, T>> â˜ƒx = ResourceLocation.CODEC.decode(this.delegate, â˜ƒ);
         if (!â˜ƒx.result().isPresent()) {
            return !â˜ƒ ? DataResult.error("Inline definitions not allowed here") : â˜ƒ.decode(this, â˜ƒ).map(var0 -> var0.mapFirst(var0x -> () -> var0x));
         } else {
            Pair<ResourceLocation, T> â˜ƒ = (Pair)â˜ƒx.result().get();
            ResourceLocation â˜ƒx = â˜ƒ.getFirst();
            return this.readAndRegisterElement(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx).map(var1x -> Pair.of(var1x, â˜ƒ.getSecond()));
         }
      }
   }

   public <E> DataResult<MappedRegistry<E>> decodeElements(MappedRegistry<E> var1, ResourceKey<? extends Registry<E>> var2, Codec<E> var3) {
      Collection<ResourceLocation> â˜ƒ = this.resources.listResources(â˜ƒ);
      DataResult<MappedRegistry<E>> â˜ƒx = DataResult.success(â˜ƒ, Lifecycle.stable());
      String â˜ƒxx = â˜ƒ.location().getPath() + "/";

      for(ResourceLocation â˜ƒxxx : â˜ƒ) {
         String â˜ƒxxxx = â˜ƒxxx.getPath();
         if (!â˜ƒxxxx.endsWith(".json")) {
            LOGGER.warn("Skipping resource {} since it is not a json file", â˜ƒxxx);
         } else if (!â˜ƒxxxx.startsWith(â˜ƒxx)) {
            LOGGER.warn("Skipping resource {} since it does not have a registry name prefix", â˜ƒxxx);
         } else {
            String â˜ƒxxxx = â˜ƒxxxx.substring(â˜ƒxx.length(), â˜ƒxxxx.length() - ".json".length());
            ResourceLocation â˜ƒxxxxx = new ResourceLocation(â˜ƒxxx.getNamespace(), â˜ƒxxxx);
            â˜ƒx = â˜ƒx.flatMap(var4x -> this.readAndRegisterElement(â˜ƒ, var4x, â˜ƒ, â˜ƒ).map(var1x -> var4x));
         }
      }

      return â˜ƒx.setPartial(â˜ƒ);
   }

   private <E> DataResult<Supplier<E>> readAndRegisterElement(
      ResourceKey<? extends Registry<E>> var1, final WritableRegistry<E> var2, Codec<E> var3, ResourceLocation var4
   ) {
      final ResourceKey<E> â˜ƒ = ResourceKey.create(â˜ƒ, â˜ƒ);
      RegistryReadOps.ReadCache<E> â˜ƒx = this.readCache(â˜ƒ);
      DataResult<Supplier<E>> â˜ƒxx = (DataResult)â˜ƒx.values.get(â˜ƒ);
      if (â˜ƒxx != null) {
         return â˜ƒxx;
      } else {
         Supplier<E> â˜ƒx = Suppliers.memoize(() -> {
            E â˜ƒ = â˜ƒ.get(â˜ƒ);
            if (â˜ƒ == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + â˜ƒ);
            } else {
               return â˜ƒ;
            }
         });
         â˜ƒx.values.put(â˜ƒ, DataResult.success(â˜ƒx));
         Optional<DataResult<Pair<E, OptionalInt>>> â˜ƒxx = this.resources.parseElement(this.jsonOps, â˜ƒ, â˜ƒ, â˜ƒ);
         DataResult<Supplier<E>> â˜ƒ;
         if (!â˜ƒxx.isPresent()) {
            â˜ƒ = DataResult.success(new Supplier<E>() {
               public E get() {
                  return â˜ƒ.get(â˜ƒ);
               }

               public String toString() {
                  return â˜ƒ.toString();
               }
            }, Lifecycle.stable());
         } else {
            DataResult<Pair<E, OptionalInt>> â˜ƒ = (DataResult)â˜ƒxx.get();
            Optional<Pair<E, OptionalInt>> â˜ƒx = â˜ƒ.result();
            if (â˜ƒx.isPresent()) {
               Pair<E, OptionalInt> â˜ƒxx = (Pair)â˜ƒx.get();
               â˜ƒ.registerOrOverride((OptionalInt)â˜ƒxx.getSecond(), â˜ƒ, â˜ƒxx.getFirst(), â˜ƒ.lifecycle());
            }

            â˜ƒ = â˜ƒ.map(var2x -> () -> â˜ƒ.get(â˜ƒ));
         }

         â˜ƒx.values.put(â˜ƒ, â˜ƒ);
         return â˜ƒ;
      }
   }

   private <E> RegistryReadOps.ReadCache<E> readCache(ResourceKey<? extends Registry<E>> var1) {
      return (RegistryReadOps.ReadCache<E>)this.readCache.computeIfAbsent(â˜ƒ, var0 -> new RegistryReadOps.ReadCache());
   }

   protected <E> DataResult<Registry<E>> registry(ResourceKey<? extends Registry<E>> var1) {
      return (DataResult<Registry<E>>)this.registryAccess
         .ownedRegistry(â˜ƒ)
         .map(var0 -> DataResult.success(var0, var0.elementsLifecycle()))
         .orElseGet(() -> DataResult.error("Unknown registry: " + â˜ƒ));
   }

   static final class ReadCache<E> {
      final Map<ResourceKey<E>, DataResult<Supplier<E>>> values = Maps.<ResourceKey<E>, DataResult<Supplier<E>>>newIdentityHashMap();
   }

   public interface ResourceAccess {
      Collection<ResourceLocation> listResources(ResourceKey<? extends Registry<?>> var1);

      <E> Optional<DataResult<Pair<E, OptionalInt>>> parseElement(
         DynamicOps<JsonElement> var1, ResourceKey<? extends Registry<E>> var2, ResourceKey<E> var3, Decoder<E> var4
      );

      static RegistryReadOps.ResourceAccess forResourceManager(final ResourceManager var0) {
         return new RegistryReadOps.ResourceAccess() {
            @Override
            public Collection<ResourceLocation> listResources(ResourceKey<? extends Registry<?>> var1) {
               return â˜ƒ.listResources(â˜ƒ.location().getPath(), var0x -> var0x.endsWith(".json"));
            }

            @Override
            public <E> Optional<DataResult<Pair<E, OptionalInt>>> parseElement(
               DynamicOps<JsonElement> var1, ResourceKey<? extends Registry<E>> var2, ResourceKey<E> var3, Decoder<E> var4
            ) {
               ResourceLocation â˜ƒ = â˜ƒ.location();
               ResourceLocation â˜ƒx = new ResourceLocation(â˜ƒ.getNamespace(), â˜ƒ.location().getPath() + "/" + â˜ƒ.getPath() + ".json");
               if (!â˜ƒ.hasResource(â˜ƒx)) {
                  return Optional.empty();
               } else {
                  try {
                     Resource â˜ƒ = â˜ƒ.getResource(â˜ƒx);

                     Optional var11;
                     try {
                        Reader â˜ƒx = new InputStreamReader(â˜ƒ.getInputStream(), StandardCharsets.UTF_8);

                        try {
                           JsonParser â˜ƒxx = new JsonParser();
                           JsonElement â˜ƒxxx = â˜ƒxx.parse(â˜ƒx);
                           var11 = Optional.of(â˜ƒ.parse(â˜ƒ, â˜ƒxxx).map(var0x -> Pair.of(var0x, OptionalInt.empty())));
                        } catch (Throwable var14) {
                           try {
                              â˜ƒx.close();
                           } catch (Throwable var13) {
                              var14.addSuppressed(var13);
                           }

                           throw var14;
                        }

                        â˜ƒx.close();
                     } catch (Throwable var15) {
                        if (â˜ƒ != null) {
                           try {
                              â˜ƒ.close();
                           } catch (Throwable var12) {
                              var15.addSuppressed(var12);
                           }
                        }

                        throw var15;
                     }

                     if (â˜ƒ != null) {
                        â˜ƒ.close();
                     }

                     return var11;
                  } catch (JsonIOException | JsonSyntaxException | IOException var16) {
                     return Optional.of(DataResult.error("Failed to parse " + â˜ƒx + " file: " + var16.getMessage()));
                  }
               }
            }

            public String toString() {
               return "ResourceAccess[" + â˜ƒ + "]";
            }
         };
      }

      public static final class MemoryMap implements RegistryReadOps.ResourceAccess {
         private final Map<ResourceKey<?>, JsonElement> data = Maps.<ResourceKey<?>, JsonElement>newIdentityHashMap();
         private final Object2IntMap<ResourceKey<?>> ids = new Object2IntOpenCustomHashMap<>(Util.identityStrategy());
         private final Map<ResourceKey<?>, Lifecycle> lifecycles = Maps.<ResourceKey<?>, Lifecycle>newIdentityHashMap();

         public <E> void add(RegistryAccess.RegistryHolder var1, ResourceKey<E> var2, Encoder<E> var3, int var4, E var5, Lifecycle var6) {
            DataResult<JsonElement> â˜ƒ = â˜ƒ.encodeStart(RegistryWriteOps.create(JsonOps.INSTANCE, â˜ƒ), â˜ƒ);
            Optional<PartialResult<JsonElement>> â˜ƒx = â˜ƒ.error();
            if (â˜ƒx.isPresent()) {
               RegistryReadOps.LOGGER.error("Error adding element: {}", ((PartialResult)â˜ƒx.get()).message());
            } else {
               this.data.put(â˜ƒ, (JsonElement)â˜ƒ.result().get());
               this.ids.put(â˜ƒ, â˜ƒ);
               this.lifecycles.put(â˜ƒ, â˜ƒ);
            }
         }

         @Override
         public Collection<ResourceLocation> listResources(ResourceKey<? extends Registry<?>> var1) {
            return (Collection<ResourceLocation>)this.data
               .keySet()
               .stream()
               .filter(var1x -> var1x.isFor(â˜ƒ))
               .map(var1x -> new ResourceLocation(var1x.location().getNamespace(), â˜ƒ.location().getPath() + "/" + var1x.location().getPath() + ".json"))
               .collect(Collectors.toList());
         }

         @Override
         public <E> Optional<DataResult<Pair<E, OptionalInt>>> parseElement(
            DynamicOps<JsonElement> var1, ResourceKey<? extends Registry<E>> var2, ResourceKey<E> var3, Decoder<E> var4
         ) {
            JsonElement â˜ƒ = (JsonElement)this.data.get(â˜ƒ);
            return â˜ƒ == null
               ? Optional.of(DataResult.error("Unknown element: " + â˜ƒ))
               : Optional.of(
                  â˜ƒ.parse(â˜ƒ, â˜ƒ).setLifecycle((Lifecycle)this.lifecycles.get(â˜ƒ)).map(var2x -> Pair.of(var2x, OptionalInt.of(this.ids.getInt(â˜ƒ))))
               );
         }
      }
   }
}
