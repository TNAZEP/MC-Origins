package net.minecraft.tags;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class Tag<T> {
   private final ResourceLocation field_199888_a;
   private final Set<T> field_199889_b;
   private final Collection<Tag.ITagEntry<T>> field_200150_c;

   public Tag(ResourceLocation var1) {
      this.field_199888_a = ☃;
      this.field_199889_b = Collections.emptySet();
      this.field_200150_c = Collections.emptyList();
   }

   public Tag(ResourceLocation var1, Collection<Tag.ITagEntry<T>> var2, boolean var3) {
      this.field_199888_a = ☃;
      this.field_199889_b = (Set<T>)(☃ ? Sets.<T>newLinkedHashSet() : Sets.<T>newHashSet());
      this.field_200150_c = ☃;

      for(Tag.ITagEntry<T> ☃ : ☃) {
         ☃.func_200162_a(this.field_199889_b);
      }
   }

   public JsonObject func_200571_a(Function<T, ResourceLocation> var1) {
      JsonObject ☃ = new JsonObject();
      JsonArray ☃x = new JsonArray();

      for(Tag.ITagEntry<T> ☃xx : this.field_200150_c) {
         ☃xx.func_200576_a(☃x, ☃);
      }

      ☃.addProperty("replace", false);
      ☃.add("values", ☃x);
      return ☃;
   }

   public boolean func_199685_a_(T var1) {
      return this.field_199889_b.contains(☃);
   }

   public Collection<T> func_199885_a() {
      return this.field_199889_b;
   }

   public Collection<Tag.ITagEntry<T>> func_200570_b() {
      return this.field_200150_c;
   }

   public T func_205596_a(Random var1) {
      List<T> ☃ = Lists.<T>newArrayList(this.func_199885_a());
      return (T)☃.get(☃.nextInt(☃.size()));
   }

   public ResourceLocation func_199886_b() {
      return this.field_199888_a;
   }

   public static class Builder<T> {
      private final Set<Tag.ITagEntry<T>> field_200052_a = Sets.<Tag.ITagEntry<T>>newLinkedHashSet();
      private boolean field_200053_b;

      public static <T> Tag.Builder<T> func_200047_a() {
         return new Tag.Builder<>();
      }

      public Tag.Builder<T> func_200575_a(Tag.ITagEntry<T> var1) {
         this.field_200052_a.add(☃);
         return this;
      }

      public Tag.Builder<T> func_200048_a(T var1) {
         this.field_200052_a.add(new Tag.ListEntry(Collections.singleton(☃)));
         return this;
      }

      @SafeVarargs
      public final Tag.Builder<T> func_200573_a(T... var1) {
         this.field_200052_a.add(new Tag.ListEntry(Lists.<T>newArrayList(☃)));
         return this;
      }

      public Tag.Builder<T> func_200046_a(Collection<T> var1) {
         this.field_200052_a.add(new Tag.ListEntry(☃));
         return this;
      }

      public Tag.Builder<T> func_200159_a(ResourceLocation var1) {
         this.field_200052_a.add(new Tag.TagEntry(☃));
         return this;
      }

      public Tag.Builder<T> func_200574_a(Tag<T> var1) {
         this.field_200052_a.add(new Tag.TagEntry<>(☃));
         return this;
      }

      public Tag.Builder<T> func_200045_a(boolean var1) {
         this.field_200053_b = ☃;
         return this;
      }

      public boolean func_200160_a(Function<ResourceLocation, Tag<T>> var1) {
         for(Tag.ITagEntry<T> ☃ : this.field_200052_a) {
            if (!☃.func_200161_a(☃)) {
               return false;
            }
         }

         return true;
      }

      public Tag<T> func_200051_a(ResourceLocation var1) {
         return new Tag<>(☃, this.field_200052_a, this.field_200053_b);
      }

      public Tag.Builder<T> func_200158_a(Predicate<ResourceLocation> var1, Function<ResourceLocation, T> var2, JsonObject var3) {
         JsonArray ☃ = JsonUtils.func_151214_t(☃, "values");
         if (JsonUtils.func_151209_a(☃, "replace", false)) {
            this.field_200052_a.clear();
         }

         for(JsonElement ☃ : ☃) {
            String ☃x = JsonUtils.func_151206_a(☃, "value");
            if (!☃x.startsWith("#")) {
               ResourceLocation ☃xx = new ResourceLocation(☃x);
               T ☃xxx = (T)☃.apply(☃xx);
               if (☃xxx == null || !☃.test(☃xx)) {
                  throw new JsonParseException("Unknown value '" + ☃xx + "'");
               }

               this.func_200048_a(☃xxx);
            } else {
               this.func_200159_a(new ResourceLocation(☃x.substring(1)));
            }
         }

         return this;
      }
   }

   public interface ITagEntry<T> {
      default boolean func_200161_a(Function<ResourceLocation, Tag<T>> var1) {
         return true;
      }

      void func_200162_a(Collection<T> var1);

      void func_200576_a(JsonArray var1, Function<T, ResourceLocation> var2);
   }

   public static class ListEntry<T> implements Tag.ITagEntry<T> {
      private final Collection<T> field_200165_a;

      public ListEntry(Collection<T> var1) {
         this.field_200165_a = ☃;
      }

      @Override
      public void func_200162_a(Collection<T> var1) {
         ☃.addAll(this.field_200165_a);
      }

      @Override
      public void func_200576_a(JsonArray var1, Function<T, ResourceLocation> var2) {
         for(T ☃ : this.field_200165_a) {
            ResourceLocation ☃x = (ResourceLocation)☃.apply(☃);
            if (☃x == null) {
               throw new IllegalStateException("Unable to serialize an anonymous value to json!");
            }

            ☃.add(☃x.toString());
         }
      }

      public Collection<T> func_200578_a() {
         return this.field_200165_a;
      }
   }

   public static class TagEntry<T> implements Tag.ITagEntry<T> {
      @Nullable
      private final ResourceLocation field_200163_a;
      @Nullable
      private Tag<T> field_200164_b;

      public TagEntry(ResourceLocation var1) {
         this.field_200163_a = ☃;
      }

      public TagEntry(Tag<T> var1) {
         this.field_200163_a = ☃.func_199886_b();
         this.field_200164_b = ☃;
      }

      @Override
      public boolean func_200161_a(Function<ResourceLocation, Tag<T>> var1) {
         if (this.field_200164_b == null) {
            this.field_200164_b = (Tag)☃.apply(this.field_200163_a);
         }

         return this.field_200164_b != null;
      }

      @Override
      public void func_200162_a(Collection<T> var1) {
         if (this.field_200164_b == null) {
            throw new IllegalStateException("Cannot build unresolved tag entry");
         } else {
            ☃.addAll(this.field_200164_b.func_199885_a());
         }
      }

      public ResourceLocation func_200577_a() {
         if (this.field_200164_b != null) {
            return this.field_200164_b.func_199886_b();
         } else if (this.field_200163_a != null) {
            return this.field_200163_a;
         } else {
            throw new IllegalStateException("Cannot serialize an anonymous tag to json!");
         }
      }

      @Override
      public void func_200576_a(JsonArray var1, Function<T, ResourceLocation> var2) {
         ☃.add("#" + this.func_200577_a());
      }
   }
}
