package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;

public final class Ingredient implements Predicate<ItemStack> {
   private static final Predicate<? super Ingredient.IItemList> field_209362_b = var0 -> !var0.func_199799_a().stream().allMatch(ItemStack::func_190926_b);
   public static final Ingredient field_193370_a = new Ingredient(Stream.empty());
   private final Ingredient.IItemList[] field_199807_b;
   private ItemStack[] field_193371_b;
   private IntList field_194140_c;

   private Ingredient(Stream<? extends Ingredient.IItemList> var1) {
      this.field_199807_b = (Ingredient.IItemList[])☃.filter(field_209362_b).toArray(var0 -> new Ingredient.IItemList[var0]);
   }

   private void func_199806_d() {
      if (this.field_193371_b == null) {
         this.field_193371_b = (ItemStack[])Arrays.stream(this.field_199807_b)
            .flatMap(var0 -> var0.func_199799_a().stream())
            .distinct()
            .toArray(var0 -> new ItemStack[var0]);
      }
   }

   public boolean test(@Nullable ItemStack var1) {
      if (☃ == null) {
         return false;
      } else if (this.field_199807_b.length == 0) {
         return ☃.func_190926_b();
      } else {
         this.func_199806_d();

         for(ItemStack ☃ : this.field_193371_b) {
            if (☃.func_77973_b() == ☃.func_77973_b()) {
               return true;
            }
         }

         return false;
      }
   }

   public IntList func_194139_b() {
      if (this.field_194140_c == null) {
         this.func_199806_d();
         this.field_194140_c = new IntArrayList(this.field_193371_b.length);

         for(ItemStack ☃ : this.field_193371_b) {
            this.field_194140_c.add(RecipeItemHelper.func_194113_b(☃));
         }

         this.field_194140_c.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.field_194140_c;
   }

   public void func_199564_a(PacketBuffer var1) {
      this.func_199806_d();
      ☃.func_150787_b(this.field_193371_b.length);

      for(int ☃ = 0; ☃ < this.field_193371_b.length; ++☃) {
         ☃.func_150788_a(this.field_193371_b[☃]);
      }
   }

   public JsonElement func_200304_c() {
      if (this.field_199807_b.length == 1) {
         return this.field_199807_b[0].func_200303_b();
      } else {
         JsonArray ☃ = new JsonArray();

         for(Ingredient.IItemList ☃x : this.field_199807_b) {
            ☃.add(☃x.func_200303_b());
         }

         return ☃;
      }
   }

   public boolean func_203189_d() {
      return this.field_199807_b.length == 0
         && (this.field_193371_b == null || this.field_193371_b.length == 0)
         && (this.field_194140_c == null || this.field_194140_c.isEmpty());
   }

   private static Ingredient func_209357_a(Stream<? extends Ingredient.IItemList> var0) {
      Ingredient ☃ = new Ingredient(☃);
      return ☃.field_199807_b.length == 0 ? field_193370_a : ☃;
   }

   public static Ingredient func_199804_a(IItemProvider... var0) {
      return func_209357_a(Arrays.stream(☃).map(var0x -> new Ingredient.SingleItemList(new ItemStack(var0x))));
   }

   public static Ingredient func_199805_a(Tag<Item> var0) {
      return func_209357_a(Stream.of(new Ingredient.TagList(☃)));
   }

   public static Ingredient func_199566_b(PacketBuffer var0) {
      int ☃ = ☃.func_150792_a();
      return func_209357_a(Stream.generate(() -> new Ingredient.SingleItemList(☃.func_150791_c())).limit((long)☃));
   }

   public static Ingredient func_199802_a(@Nullable JsonElement var0) {
      if (☃ == null || ☃.isJsonNull()) {
         throw new JsonSyntaxException("Item cannot be null");
      } else if (☃.isJsonObject()) {
         return func_209357_a(Stream.of(func_199803_a(☃.getAsJsonObject())));
      } else if (☃.isJsonArray()) {
         JsonArray ☃ = ☃.getAsJsonArray();
         if (☃.size() == 0) {
            throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
         } else {
            return func_209357_a(StreamSupport.stream(☃.spliterator(), false).map(var0x -> func_199803_a(JsonUtils.func_151210_l(var0x, "item"))));
         }
      } else {
         throw new JsonSyntaxException("Expected item to be object or array of objects");
      }
   }

   public static Ingredient.IItemList func_199803_a(JsonObject var0) {
      if (☃.has("item") && ☃.has("tag")) {
         throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
      } else if (☃.has("item")) {
         ResourceLocation ☃ = new ResourceLocation(JsonUtils.func_151200_h(☃, "item"));
         Item ☃x = IRegistry.field_212630_s.func_212608_b(☃);
         if (☃x == null) {
            throw new JsonSyntaxException("Unknown item '" + ☃ + "'");
         } else {
            return new Ingredient.SingleItemList(new ItemStack(☃x));
         }
      } else if (☃.has("tag")) {
         ResourceLocation ☃ = new ResourceLocation(JsonUtils.func_151200_h(☃, "tag"));
         Tag<Item> ☃x = ItemTags.func_199903_a().func_199910_a(☃);
         if (☃x == null) {
            throw new JsonSyntaxException("Unknown item tag '" + ☃ + "'");
         } else {
            return new Ingredient.TagList(☃x);
         }
      } else {
         throw new JsonParseException("An ingredient entry needs either a tag or an item");
      }
   }

   interface IItemList {
      Collection<ItemStack> func_199799_a();

      JsonObject func_200303_b();
   }

   static class SingleItemList implements Ingredient.IItemList {
      private final ItemStack field_199801_a;

      private SingleItemList(ItemStack var1) {
         this.field_199801_a = ☃;
      }

      @Override
      public Collection<ItemStack> func_199799_a() {
         return Collections.singleton(this.field_199801_a);
      }

      @Override
      public JsonObject func_200303_b() {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("item", IRegistry.field_212630_s.func_177774_c(this.field_199801_a.func_77973_b()).toString());
         return ☃;
      }
   }

   static class TagList implements Ingredient.IItemList {
      private final Tag<Item> field_199800_a;

      private TagList(Tag<Item> var1) {
         this.field_199800_a = ☃;
      }

      @Override
      public Collection<ItemStack> func_199799_a() {
         List<ItemStack> ☃ = Lists.<ItemStack>newArrayList();

         for(Item ☃x : this.field_199800_a.func_199885_a()) {
            ☃.add(new ItemStack(☃x));
         }

         return ☃;
      }

      @Override
      public JsonObject func_200303_b() {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("tag", this.field_199800_a.func_199886_b().toString());
         return ☃;
      }
   }
}
