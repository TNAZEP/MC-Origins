package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public interface Tag<T> {
   static <T> Codec<Tag<T>> codec(Supplier<TagCollection<T>> var0) {
      return ResourceLocation.CODEC
         .flatXmap(
            var1 -> (DataResult)Optional.ofNullable(((TagCollection)â˜ƒ.get()).getTag(var1))
                  .map(DataResult::success)
                  .orElseGet(() -> DataResult.error("Unknown tag: " + var1)),
            var1 -> (DataResult)Optional.ofNullable(((TagCollection)â˜ƒ.get()).getId(var1))
                  .map(DataResult::success)
                  .orElseGet(() -> DataResult.error("Unknown tag: " + var1))
         );
   }

   boolean contains(T var1);

   List<T> getValues();

   default T getRandomElement(Random var1) {
      List<T> â˜ƒ = this.getValues();
      return (T)â˜ƒ.get(â˜ƒ.nextInt(â˜ƒ.size()));
   }

   static <T> Tag<T> fromSet(Set<T> var0) {
      return SetTag.create(â˜ƒ);
   }

   public static class Builder {
      private final List<Tag.BuilderEntry> entries = Lists.<Tag.BuilderEntry>newArrayList();

      public static Tag.Builder tag() {
         return new Tag.Builder();
      }

      public Tag.Builder add(Tag.BuilderEntry var1) {
         this.entries.add(â˜ƒ);
         return this;
      }

      public Tag.Builder add(Tag.Entry var1, String var2) {
         return this.add(new Tag.BuilderEntry(â˜ƒ, â˜ƒ));
      }

      public Tag.Builder addElement(ResourceLocation var1, String var2) {
         return this.add(new Tag.ElementEntry(â˜ƒ), â˜ƒ);
      }

      public Tag.Builder addOptionalElement(ResourceLocation var1, String var2) {
         return this.add(new Tag.OptionalElementEntry(â˜ƒ), â˜ƒ);
      }

      public Tag.Builder addTag(ResourceLocation var1, String var2) {
         return this.add(new Tag.TagEntry(â˜ƒ), â˜ƒ);
      }

      public Tag.Builder addOptionalTag(ResourceLocation var1, String var2) {
         return this.add(new Tag.OptionalTagEntry(â˜ƒ), â˜ƒ);
      }

      public <T> Either<Collection<Tag.BuilderEntry>, Tag<T>> build(Function<ResourceLocation, Tag<T>> var1, Function<ResourceLocation, T> var2) {
         ImmutableSet.Builder<T> â˜ƒ = ImmutableSet.builder();
         List<Tag.BuilderEntry> â˜ƒx = Lists.<Tag.BuilderEntry>newArrayList();

         for(Tag.BuilderEntry â˜ƒxx : this.entries) {
            if (!â˜ƒxx.getEntry().build(â˜ƒ, â˜ƒ, â˜ƒ::add)) {
               â˜ƒx.add(â˜ƒxx);
            }
         }

         return â˜ƒx.isEmpty() ? Either.right(Tag.fromSet(â˜ƒ.build())) : Either.left(â˜ƒx);
      }

      public Stream<Tag.BuilderEntry> getEntries() {
         return this.entries.stream();
      }

      public void visitRequiredDependencies(Consumer<ResourceLocation> var1) {
         this.entries.forEach(var1x -> var1x.entry.visitRequiredDependencies(â˜ƒ));
      }

      public void visitOptionalDependencies(Consumer<ResourceLocation> var1) {
         this.entries.forEach(var1x -> var1x.entry.visitOptionalDependencies(â˜ƒ));
      }

      public Tag.Builder addFromJson(JsonObject var1, String var2) {
         JsonArray â˜ƒ = GsonHelper.getAsJsonArray(â˜ƒ, "values");
         List<Tag.Entry> â˜ƒx = Lists.<Tag.Entry>newArrayList();

         for(JsonElement â˜ƒxx : â˜ƒ) {
            â˜ƒx.add(parseEntry(â˜ƒxx));
         }

         if (GsonHelper.getAsBoolean(â˜ƒ, "replace", false)) {
            this.entries.clear();
         }

         â˜ƒx.forEach(var2x -> this.entries.add(new Tag.BuilderEntry(var2x, â˜ƒ)));
         return this;
      }

      private static Tag.Entry parseEntry(JsonElement var0) {
         String â˜ƒ;
         boolean â˜ƒx;
         if (â˜ƒ.isJsonObject()) {
            JsonObject â˜ƒxx = â˜ƒ.getAsJsonObject();
            â˜ƒ = GsonHelper.getAsString(â˜ƒxx, "id");
            â˜ƒx = GsonHelper.getAsBoolean(â˜ƒxx, "required", true);
         } else {
            â˜ƒ = GsonHelper.convertToString(â˜ƒ, "id");
            â˜ƒx = true;
         }

         if (â˜ƒ.startsWith("#")) {
            ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ.substring(1));
            return (Tag.Entry)(â˜ƒx ? new Tag.TagEntry(â˜ƒ) : new Tag.OptionalTagEntry(â˜ƒ));
         } else {
            ResourceLocation â˜ƒ = new ResourceLocation(â˜ƒ);
            return (Tag.Entry)(â˜ƒx ? new Tag.ElementEntry(â˜ƒ) : new Tag.OptionalElementEntry(â˜ƒ));
         }
      }

      public JsonObject serializeToJson() {
         JsonObject â˜ƒ = new JsonObject();
         JsonArray â˜ƒx = new JsonArray();

         for(Tag.BuilderEntry â˜ƒxx : this.entries) {
            â˜ƒxx.getEntry().serializeTo(â˜ƒx);
         }

         â˜ƒ.addProperty("replace", false);
         â˜ƒ.add("values", â˜ƒx);
         return â˜ƒ;
      }
   }

   public static class BuilderEntry {
      final Tag.Entry entry;
      private final String source;

      BuilderEntry(Tag.Entry var1, String var2) {
         this.entry = â˜ƒ;
         this.source = â˜ƒ;
      }

      public Tag.Entry getEntry() {
         return this.entry;
      }

      public String getSource() {
         return this.source;
      }

      public String toString() {
         return this.entry + " (from " + this.source + ")";
      }
   }

   public static class ElementEntry implements Tag.Entry {
      private final ResourceLocation id;

      public ElementEntry(ResourceLocation var1) {
         this.id = â˜ƒ;
      }

      @Override
      public <T> boolean build(Function<ResourceLocation, Tag<T>> var1, Function<ResourceLocation, T> var2, Consumer<T> var3) {
         T â˜ƒ = (T)â˜ƒ.apply(this.id);
         if (â˜ƒ == null) {
            return false;
         } else {
            â˜ƒ.accept(â˜ƒ);
            return true;
         }
      }

      @Override
      public void serializeTo(JsonArray var1) {
         â˜ƒ.add(this.id.toString());
      }

      @Override
      public boolean verifyIfPresent(Predicate<ResourceLocation> var1, Predicate<ResourceLocation> var2) {
         return â˜ƒ.test(this.id);
      }

      public String toString() {
         return this.id.toString();
      }
   }

   public interface Entry {
      <T> boolean build(Function<ResourceLocation, Tag<T>> var1, Function<ResourceLocation, T> var2, Consumer<T> var3);

      void serializeTo(JsonArray var1);

      default void visitRequiredDependencies(Consumer<ResourceLocation> var1) {
      }

      default void visitOptionalDependencies(Consumer<ResourceLocation> var1) {
      }

      boolean verifyIfPresent(Predicate<ResourceLocation> var1, Predicate<ResourceLocation> var2);
   }

   public interface Named<T> extends Tag<T> {
      ResourceLocation getName();
   }

   public static class OptionalElementEntry implements Tag.Entry {
      private final ResourceLocation id;

      public OptionalElementEntry(ResourceLocation var1) {
         this.id = â˜ƒ;
      }

      @Override
      public <T> boolean build(Function<ResourceLocation, Tag<T>> var1, Function<ResourceLocation, T> var2, Consumer<T> var3) {
         T â˜ƒ = (T)â˜ƒ.apply(this.id);
         if (â˜ƒ != null) {
            â˜ƒ.accept(â˜ƒ);
         }

         return true;
      }

      @Override
      public void serializeTo(JsonArray var1) {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("id", this.id.toString());
         â˜ƒ.addProperty("required", false);
         â˜ƒ.add(â˜ƒ);
      }

      @Override
      public boolean verifyIfPresent(Predicate<ResourceLocation> var1, Predicate<ResourceLocation> var2) {
         return true;
      }

      public String toString() {
         return this.id + "?";
      }
   }

   public static class OptionalTagEntry implements Tag.Entry {
      private final ResourceLocation id;

      public OptionalTagEntry(ResourceLocation var1) {
         this.id = â˜ƒ;
      }

      @Override
      public <T> boolean build(Function<ResourceLocation, Tag<T>> var1, Function<ResourceLocation, T> var2, Consumer<T> var3) {
         Tag<T> â˜ƒ = (Tag)â˜ƒ.apply(this.id);
         if (â˜ƒ != null) {
            â˜ƒ.getValues().forEach(â˜ƒ);
         }

         return true;
      }

      @Override
      public void serializeTo(JsonArray var1) {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("id", "#" + this.id);
         â˜ƒ.addProperty("required", false);
         â˜ƒ.add(â˜ƒ);
      }

      public String toString() {
         return "#" + this.id + "?";
      }

      @Override
      public void visitOptionalDependencies(Consumer<ResourceLocation> var1) {
         â˜ƒ.accept(this.id);
      }

      @Override
      public boolean verifyIfPresent(Predicate<ResourceLocation> var1, Predicate<ResourceLocation> var2) {
         return true;
      }
   }

   public static class TagEntry implements Tag.Entry {
      private final ResourceLocation id;

      public TagEntry(ResourceLocation var1) {
         this.id = â˜ƒ;
      }

      @Override
      public <T> boolean build(Function<ResourceLocation, Tag<T>> var1, Function<ResourceLocation, T> var2, Consumer<T> var3) {
         Tag<T> â˜ƒ = (Tag)â˜ƒ.apply(this.id);
         if (â˜ƒ == null) {
            return false;
         } else {
            â˜ƒ.getValues().forEach(â˜ƒ);
            return true;
         }
      }

      @Override
      public void serializeTo(JsonArray var1) {
         â˜ƒ.add("#" + this.id);
      }

      public String toString() {
         return "#" + this.id;
      }

      @Override
      public boolean verifyIfPresent(Predicate<ResourceLocation> var1, Predicate<ResourceLocation> var2) {
         return â˜ƒ.test(this.id);
      }

      @Override
      public void visitRequiredDependencies(Consumer<ResourceLocation> var1) {
         â˜ƒ.accept(this.id);
      }
   }
}
