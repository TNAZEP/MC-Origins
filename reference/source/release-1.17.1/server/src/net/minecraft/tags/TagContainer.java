package net.minecraft.tags;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagContainer {
   static final Logger LOGGER = LogManager.getLogger();
   public static final TagContainer EMPTY = new TagContainer(ImmutableMap.of());
   private final Map<ResourceKey<? extends Registry<?>>, TagCollection<?>> collections;

   TagContainer(Map<ResourceKey<? extends Registry<?>>, TagCollection<?>> var1) {
      this.collections = â˜ƒ;
   }

   @Nullable
   private <T> TagCollection<T> get(ResourceKey<? extends Registry<T>> var1) {
      return (TagCollection<T>)this.collections.get(â˜ƒ);
   }

   public <T> TagCollection<T> getOrEmpty(ResourceKey<? extends Registry<T>> var1) {
      return (TagCollection<T>)this.collections.getOrDefault(â˜ƒ, TagCollection.empty());
   }

   public <T, E extends Exception> Tag<T> getTagOrThrow(ResourceKey<? extends Registry<T>> var1, ResourceLocation var2, Function<ResourceLocation, E> var3) throws E {
      TagCollection<T> â˜ƒ = this.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw (Exception)â˜ƒ.apply(â˜ƒ);
      } else {
         Tag<T> â˜ƒ = â˜ƒ.getTag(â˜ƒ);
         if (â˜ƒ == null) {
            throw (Exception)â˜ƒ.apply(â˜ƒ);
         } else {
            return â˜ƒ;
         }
      }
   }

   public <T, E extends Exception> ResourceLocation getIdOrThrow(ResourceKey<? extends Registry<T>> var1, Tag<T> var2, Supplier<E> var3) throws E {
      TagCollection<T> â˜ƒ = this.get(â˜ƒ);
      if (â˜ƒ == null) {
         throw (Exception)â˜ƒ.get();
      } else {
         ResourceLocation â˜ƒ = â˜ƒ.getId(â˜ƒ);
         if (â˜ƒ == null) {
            throw (Exception)â˜ƒ.get();
         } else {
            return â˜ƒ;
         }
      }
   }

   public void getAll(TagContainer.CollectionConsumer var1) {
      this.collections.forEach((var1x, var2) -> acceptCap(â˜ƒ, var1x, var2));
   }

   private static <T> void acceptCap(TagContainer.CollectionConsumer var0, ResourceKey<? extends Registry<?>> var1, TagCollection<?> var2) {
      â˜ƒ.accept(â˜ƒ, â˜ƒ);
   }

   public void bindToGlobal() {
      StaticTags.resetAll(this);
      Blocks.rebuildCache();
   }

   public Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> serializeToNetwork(final RegistryAccess var1) {
      final Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> â˜ƒ = Maps.<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload>newHashMap(
         
      );
      this.getAll(new TagContainer.CollectionConsumer() {
         @Override
         public <T> void accept(ResourceKey<? extends Registry<T>> var1x, TagCollection<T> var2x) {
            Optional<? extends Registry<T>> â˜ƒ = â˜ƒ.registry(â˜ƒ);
            if (â˜ƒ.isPresent()) {
               â˜ƒ.put(â˜ƒ, â˜ƒ.serializeToNetwork((Registry<T>)â˜ƒ.get()));
            } else {
               TagContainer.LOGGER.error("Unknown registry {}", â˜ƒ);
            }
         }
      });
      return â˜ƒ;
   }

   public static TagContainer deserializeFromNetwork(RegistryAccess var0, Map<ResourceKey<? extends Registry<?>>, TagCollection.NetworkPayload> var1) {
      TagContainer.Builder â˜ƒ = new TagContainer.Builder();
      â˜ƒ.forEach((var2x, var3) -> addTagsFromPayload(â˜ƒ, â˜ƒ, var2x, var3));
      return â˜ƒ.build();
   }

   private static <T> void addTagsFromPayload(
      RegistryAccess var0, TagContainer.Builder var1, ResourceKey<? extends Registry<? extends T>> var2, TagCollection.NetworkPayload var3
   ) {
      Optional<? extends Registry<? extends T>> â˜ƒ = â˜ƒ.registry(â˜ƒ);
      if (â˜ƒ.isPresent()) {
         â˜ƒ.add(â˜ƒ, TagCollection.createFromNetwork(â˜ƒ, (Registry<? extends T>)â˜ƒ.get()));
      } else {
         LOGGER.error("Unknown registry {}", â˜ƒ);
      }
   }

   public static class Builder {
      private final ImmutableMap.Builder<ResourceKey<? extends Registry<?>>, TagCollection<?>> result = ImmutableMap.builder();

      public <T> TagContainer.Builder add(ResourceKey<? extends Registry<? extends T>> var1, TagCollection<T> var2) {
         this.result.put(â˜ƒ, â˜ƒ);
         return this;
      }

      public TagContainer build() {
         return new TagContainer(this.result.build());
      }
   }

   @FunctionalInterface
   interface CollectionConsumer {
      <T> void accept(ResourceKey<? extends Registry<T>> var1, TagCollection<T> var2);
   }
}
