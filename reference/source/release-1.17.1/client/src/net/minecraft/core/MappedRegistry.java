package net.minecraft.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.RegistryDataPackCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MappedRegistry<T> extends WritableRegistry<T> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private final ObjectList<T> byId = new ObjectArrayList<>(256);
   private final Object2IntMap<T> toId = new Object2IntOpenCustomHashMap<>(Util.identityStrategy());
   private final BiMap<ResourceLocation, T> storage;
   private final BiMap<ResourceKey<T>, T> keyStorage;
   private final Map<T, Lifecycle> lifecycles;
   private Lifecycle elementsLifecycle;
   protected Object[] randomCache;
   private int nextId;

   public MappedRegistry(ResourceKey<? extends Registry<T>> var1, Lifecycle var2) {
      super(â˜ƒ, â˜ƒ);
      this.toId.defaultReturnValue(-1);
      this.storage = HashBiMap.create();
      this.keyStorage = HashBiMap.create();
      this.lifecycles = Maps.<T, Lifecycle>newIdentityHashMap();
      this.elementsLifecycle = â˜ƒ;
   }

   public static <T> MapCodec<MappedRegistry.RegistryEntry<T>> withNameAndId(ResourceKey<? extends Registry<T>> var0, MapCodec<T> var1) {
      return RecordCodecBuilder.mapCodec(
         var2 -> var2.group(
                  ResourceLocation.CODEC.xmap(ResourceKey.elementKey(â˜ƒ), ResourceKey::location).fieldOf("name").forGetter(var0x -> var0x.key),
                  Codec.INT.fieldOf("id").forGetter(var0x -> var0x.id),
                  â˜ƒ.forGetter(var0x -> var0x.value)
               )
               .apply(var2, MappedRegistry.RegistryEntry::new)
      );
   }

   @Override
   public <V extends T> V registerMapping(int var1, ResourceKey<T> var2, V var3, Lifecycle var4) {
      return this.registerMapping(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true);
   }

   private <V extends T> V registerMapping(int var1, ResourceKey<T> var2, V var3, Lifecycle var4, boolean var5) {
      Validate.notNull(â˜ƒ);
      Validate.notNull((T)â˜ƒ);
      this.byId.size(Math.max(this.byId.size(), â˜ƒ + 1));
      this.byId.set(â˜ƒ, â˜ƒ);
      this.toId.put((T)â˜ƒ, â˜ƒ);
      this.randomCache = null;
      if (â˜ƒ && this.keyStorage.containsKey(â˜ƒ)) {
         LOGGER.debug("Adding duplicate key '{}' to registry", â˜ƒ);
      }

      if (this.storage.containsValue(â˜ƒ)) {
         LOGGER.error("Adding duplicate value '{}' to registry", â˜ƒ);
      }

      this.storage.put(â˜ƒ.location(), (T)â˜ƒ);
      this.keyStorage.put(â˜ƒ, (T)â˜ƒ);
      this.lifecycles.put(â˜ƒ, â˜ƒ);
      this.elementsLifecycle = this.elementsLifecycle.add(â˜ƒ);
      if (this.nextId <= â˜ƒ) {
         this.nextId = â˜ƒ + 1;
      }

      return â˜ƒ;
   }

   @Override
   public <V extends T> V register(ResourceKey<T> var1, V var2, Lifecycle var3) {
      return this.registerMapping(this.nextId, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public <V extends T> V registerOrOverride(OptionalInt var1, ResourceKey<T> var2, V var3, Lifecycle var4) {
      Validate.notNull(â˜ƒ);
      Validate.notNull((T)â˜ƒ);
      T â˜ƒx = (T)this.keyStorage.get(â˜ƒ);
      int â˜ƒ;
      if (â˜ƒx == null) {
         â˜ƒ = â˜ƒ.isPresent() ? â˜ƒ.getAsInt() : this.nextId;
      } else {
         â˜ƒ = this.toId.getInt(â˜ƒx);
         if (â˜ƒ.isPresent() && â˜ƒ.getAsInt() != â˜ƒ) {
            throw new IllegalStateException("ID mismatch");
         }

         this.toId.removeInt(â˜ƒx);
         this.lifecycles.remove(â˜ƒx);
      }

      return this.registerMapping(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   @Nullable
   @Override
   public ResourceLocation getKey(T var1) {
      return (ResourceLocation)this.storage.inverse().get(â˜ƒ);
   }

   @Override
   public Optional<ResourceKey<T>> getResourceKey(T var1) {
      return Optional.ofNullable((ResourceKey)this.keyStorage.inverse().get(â˜ƒ));
   }

   @Override
   public int getId(@Nullable T var1) {
      return this.toId.getInt(â˜ƒ);
   }

   @Nullable
   @Override
   public T get(@Nullable ResourceKey<T> var1) {
      return (T)this.keyStorage.get(â˜ƒ);
   }

   @Nullable
   @Override
   public T byId(int var1) {
      return (T)(â˜ƒ >= 0 && â˜ƒ < this.byId.size() ? this.byId.get(â˜ƒ) : null);
   }

   @Override
   public Lifecycle lifecycle(T var1) {
      return (Lifecycle)this.lifecycles.get(â˜ƒ);
   }

   @Override
   public Lifecycle elementsLifecycle() {
      return this.elementsLifecycle;
   }

   public Iterator<T> iterator() {
      return Iterators.filter(this.byId.iterator(), Objects::nonNull);
   }

   @Nullable
   @Override
   public T get(@Nullable ResourceLocation var1) {
      return (T)this.storage.get(â˜ƒ);
   }

   @Override
   public Set<ResourceLocation> keySet() {
      return Collections.unmodifiableSet(this.storage.keySet());
   }

   @Override
   public Set<Entry<ResourceKey<T>, T>> entrySet() {
      return Collections.unmodifiableMap(this.keyStorage).entrySet();
   }

   @Override
   public boolean isEmpty() {
      return this.storage.isEmpty();
   }

   @Nullable
   @Override
   public T getRandom(Random var1) {
      if (this.randomCache == null) {
         Collection<?> â˜ƒ = this.storage.values();
         if (â˜ƒ.isEmpty()) {
            return null;
         }

         this.randomCache = â˜ƒ.toArray(new Object[â˜ƒ.size()]);
      }

      return Util.getRandom((T[])this.randomCache, â˜ƒ);
   }

   @Override
   public boolean containsKey(ResourceLocation var1) {
      return this.storage.containsKey(â˜ƒ);
   }

   @Override
   public boolean containsKey(ResourceKey<T> var1) {
      return this.keyStorage.containsKey(â˜ƒ);
   }

   public static <T> Codec<MappedRegistry<T>> networkCodec(ResourceKey<? extends Registry<T>> var0, Lifecycle var1, Codec<T> var2) {
      return withNameAndId(â˜ƒ, â˜ƒ.fieldOf("element")).codec().listOf().xmap(var2x -> {
         MappedRegistry<T> â˜ƒ = new MappedRegistry<>(â˜ƒ, â˜ƒ);

         for(MappedRegistry.RegistryEntry<T> â˜ƒx : var2x) {
            â˜ƒ.registerMapping(â˜ƒx.id, â˜ƒx.key, â˜ƒx.value, â˜ƒ);
         }

         return â˜ƒ;
      }, var0x -> {
         Builder<MappedRegistry.RegistryEntry<T>> â˜ƒ = ImmutableList.builder();

         for(T â˜ƒx : var0x) {
            â˜ƒ.add(new MappedRegistry.RegistryEntry<>((ResourceKey<T>)var0x.getResourceKey(â˜ƒx).get(), var0x.getId(â˜ƒx), â˜ƒx));
         }

         return â˜ƒ.build();
      });
   }

   public static <T> Codec<MappedRegistry<T>> dataPackCodec(ResourceKey<? extends Registry<T>> var0, Lifecycle var1, Codec<T> var2) {
      return RegistryDataPackCodec.create(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static <T> Codec<MappedRegistry<T>> directCodec(ResourceKey<? extends Registry<T>> var0, Lifecycle var1, Codec<T> var2) {
      return Codec.unboundedMap(ResourceLocation.CODEC.xmap(ResourceKey.elementKey(â˜ƒ), ResourceKey::location), â˜ƒ).xmap(var2x -> {
         MappedRegistry<T> â˜ƒ = new MappedRegistry<>(â˜ƒ, â˜ƒ);
         var2x.forEach((var2xx, var3x) -> â˜ƒ.register(var2xx, var3x, â˜ƒ));
         return â˜ƒ;
      }, var0x -> ImmutableMap.copyOf(var0x.keyStorage));
   }

   public static class RegistryEntry<T> {
      public final ResourceKey<T> key;
      public final int id;
      public final T value;

      public RegistryEntry(ResourceKey<T> var1, int var2, T var3) {
         this.key = â˜ƒ;
         this.id = â˜ƒ;
         this.value = â˜ƒ;
      }
   }
}
