package net.minecraft.tags;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface TagCollection<T> {
   Map<ResourceLocation, Tag<T>> getAllTags();

   @Nullable
   default Tag<T> getTag(ResourceLocation var1) {
      return (Tag<T>)this.getAllTags().get(â˜ƒ);
   }

   Tag<T> getTagOrEmpty(ResourceLocation var1);

   @Nullable
   default ResourceLocation getId(Tag.Named<T> var1) {
      return â˜ƒ.getName();
   }

   @Nullable
   ResourceLocation getId(Tag<T> var1);

   default boolean hasTag(ResourceLocation var1) {
      return this.getAllTags().containsKey(â˜ƒ);
   }

   default Collection<ResourceLocation> getAvailableTags() {
      return this.getAllTags().keySet();
   }

   default Collection<ResourceLocation> getMatchingTags(T var1) {
      List<ResourceLocation> â˜ƒ = Lists.<ResourceLocation>newArrayList();

      for(Entry<ResourceLocation, Tag<T>> â˜ƒx : this.getAllTags().entrySet()) {
         if (((Tag)â˜ƒx.getValue()).contains(â˜ƒ)) {
            â˜ƒ.add((ResourceLocation)â˜ƒx.getKey());
         }
      }

      return â˜ƒ;
   }

   default TagCollection.NetworkPayload serializeToNetwork(Registry<T> var1) {
      Map<ResourceLocation, Tag<T>> â˜ƒ = this.getAllTags();
      Map<ResourceLocation, IntList> â˜ƒx = Maps.<ResourceLocation, IntList>newHashMapWithExpectedSize(â˜ƒ.size());
      â˜ƒ.forEach((var2x, var3x) -> {
         List<T> â˜ƒ = var3x.getValues();
         IntList â˜ƒx = new IntArrayList(â˜ƒ.size());

         for(T â˜ƒxx : â˜ƒ) {
            â˜ƒx.add(â˜ƒ.getId(â˜ƒxx));
         }

         â˜ƒ.put(var2x, â˜ƒx);
      });
      return new TagCollection.NetworkPayload(â˜ƒx);
   }

   static <T> TagCollection<T> createFromNetwork(TagCollection.NetworkPayload var0, Registry<? extends T> var1) {
      Map<ResourceLocation, Tag<T>> â˜ƒ = Maps.<ResourceLocation, Tag<T>>newHashMapWithExpectedSize(â˜ƒ.tags.size());
      â˜ƒ.tags.forEach((var2x, var3) -> {
         Builder<T> â˜ƒ = ImmutableSet.builder();

         for(int â˜ƒx : var3) {
            â˜ƒ.add(â˜ƒ.byId(â˜ƒx));
         }

         â˜ƒ.put(var2x, Tag.fromSet(â˜ƒ.build()));
      });
      return of(â˜ƒ);
   }

   static <T> TagCollection<T> empty() {
      return of(ImmutableBiMap.of());
   }

   static <T> TagCollection<T> of(Map<ResourceLocation, Tag<T>> var0) {
      final BiMap<ResourceLocation, Tag<T>> â˜ƒ = ImmutableBiMap.copyOf(â˜ƒ);
      return new TagCollection<T>() {
         private final Tag<T> empty = SetTag.empty();

         @Override
         public Tag<T> getTagOrEmpty(ResourceLocation var1x) {
            return (Tag<T>)â˜ƒ.getOrDefault(â˜ƒ, this.empty);
         }

         @Nullable
         @Override
         public ResourceLocation getId(Tag<T> var1x) {
            return â˜ƒ instanceof Tag.Named ? ((Tag.Named)â˜ƒ).getName() : (ResourceLocation)â˜ƒ.inverse().get(â˜ƒ);
         }

         @Override
         public Map<ResourceLocation, Tag<T>> getAllTags() {
            return â˜ƒ;
         }
      };
   }

   public static class NetworkPayload {
      final Map<ResourceLocation, IntList> tags;

      NetworkPayload(Map<ResourceLocation, IntList> var1) {
         this.tags = â˜ƒ;
      }

      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeMap(this.tags, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeIntIdList);
      }

      public static TagCollection.NetworkPayload read(FriendlyByteBuf var0) {
         return new TagCollection.NetworkPayload(â˜ƒ.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readIntIdList));
      }
   }
}
