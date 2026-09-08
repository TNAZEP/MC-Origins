package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class StaticTagHelper<T> {
   private final ResourceKey<? extends Registry<T>> key;
   private final String directory;
   private TagCollection<T> source = TagCollection.empty();
   private final List<StaticTagHelper.Wrapper<T>> wrappers = Lists.<StaticTagHelper.Wrapper<T>>newArrayList();

   public StaticTagHelper(ResourceKey<? extends Registry<T>> var1, String var2) {
      this.key = â˜ƒ;
      this.directory = â˜ƒ;
   }

   public Tag.Named<T> bind(String var1) {
      StaticTagHelper.Wrapper<T> â˜ƒ = new StaticTagHelper.Wrapper<>(new ResourceLocation(â˜ƒ));
      this.wrappers.add(â˜ƒ);
      return â˜ƒ;
   }

   public void resetToEmpty() {
      this.source = TagCollection.empty();
      Tag<T> â˜ƒ = SetTag.empty();
      this.wrappers.forEach(var1x -> var1x.rebind(var1xx -> â˜ƒ));
   }

   public void reset(TagContainer var1) {
      TagCollection<T> â˜ƒ = â˜ƒ.getOrEmpty(this.key);
      this.source = â˜ƒ;
      this.wrappers.forEach(var1x -> var1x.rebind(â˜ƒ::getTag));
   }

   public TagCollection<T> getAllTags() {
      return this.source;
   }

   public Set<ResourceLocation> getMissingTags(TagContainer var1) {
      TagCollection<T> â˜ƒ = â˜ƒ.getOrEmpty(this.key);
      Set<ResourceLocation> â˜ƒx = (Set)this.wrappers.stream().map(StaticTagHelper.Wrapper::getName).collect(Collectors.toSet());
      ImmutableSet<ResourceLocation> â˜ƒxx = ImmutableSet.copyOf(â˜ƒ.getAvailableTags());
      return Sets.<ResourceLocation>difference(â˜ƒx, â˜ƒxx);
   }

   public ResourceKey<? extends Registry<T>> getKey() {
      return this.key;
   }

   public String getDirectory() {
      return this.directory;
   }

   protected void addToCollection(TagContainer.Builder var1) {
      â˜ƒ.add(this.key, TagCollection.of((Map<ResourceLocation, Tag<T>>)this.wrappers.stream().collect(Collectors.toMap(Tag.Named::getName, var0 -> var0))));
   }

   static class Wrapper<T> implements Tag.Named<T> {
      @Nullable
      private Tag<T> tag;
      protected final ResourceLocation name;

      Wrapper(ResourceLocation var1) {
         this.name = â˜ƒ;
      }

      @Override
      public ResourceLocation getName() {
         return this.name;
      }

      private Tag<T> resolve() {
         if (this.tag == null) {
            throw new IllegalStateException("Tag " + this.name + " used before it was bound");
         } else {
            return this.tag;
         }
      }

      void rebind(Function<ResourceLocation, Tag<T>> var1) {
         this.tag = (Tag)â˜ƒ.apply(this.name);
      }

      @Override
      public boolean contains(T var1) {
         return this.resolve().contains(â˜ƒ);
      }

      @Override
      public List<T> getValues() {
         return this.resolve().getValues();
      }
   }
}
