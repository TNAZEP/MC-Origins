package net.minecraft.resources;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.Registry;

public class ResourceKey<T> {
   private static final Map<String, ResourceKey<?>> VALUES = Collections.synchronizedMap(Maps.newIdentityHashMap());
   private final ResourceLocation registryName;
   private final ResourceLocation location;

   public static <T> ResourceKey<T> create(ResourceKey<? extends Registry<T>> var0, ResourceLocation var1) {
      return create(â˜ƒ.location, â˜ƒ);
   }

   public static <T> ResourceKey<Registry<T>> createRegistryKey(ResourceLocation var0) {
      return create(Registry.ROOT_REGISTRY_NAME, â˜ƒ);
   }

   private static <T> ResourceKey<T> create(ResourceLocation var0, ResourceLocation var1) {
      String â˜ƒ = (â˜ƒ + ":" + â˜ƒ).intern();
      return (ResourceKey<T>)VALUES.computeIfAbsent(â˜ƒ, var2x -> new ResourceKey(â˜ƒ, â˜ƒ));
   }

   private ResourceKey(ResourceLocation var1, ResourceLocation var2) {
      this.registryName = â˜ƒ;
      this.location = â˜ƒ;
   }

   public String toString() {
      return "ResourceKey[" + this.registryName + " / " + this.location + "]";
   }

   public boolean isFor(ResourceKey<? extends Registry<?>> var1) {
      return this.registryName.equals(â˜ƒ.location());
   }

   public ResourceLocation location() {
      return this.location;
   }

   public static <T> Function<ResourceLocation, ResourceKey<T>> elementKey(ResourceKey<? extends Registry<T>> var0) {
      return var1 -> create(â˜ƒ, var1);
   }
}
