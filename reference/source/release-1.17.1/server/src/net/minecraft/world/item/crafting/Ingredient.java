package net.minecraft.world.item.crafting;

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
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class Ingredient implements Predicate<ItemStack> {
   public static final Ingredient EMPTY = new Ingredient(Stream.empty());
   private final Ingredient.Value[] values;
   private ItemStack[] itemStacks;
   private IntList stackingIds;

   private Ingredient(Stream<? extends Ingredient.Value> var1) {
      this.values = (Ingredient.Value[])â˜ƒ.toArray(var0 -> new Ingredient.Value[var0]);
   }

   public ItemStack[] getItems() {
      this.dissolve();
      return this.itemStacks;
   }

   private void dissolve() {
      if (this.itemStacks == null) {
         this.itemStacks = (ItemStack[])Arrays.stream(this.values).flatMap(var0 -> var0.getItems().stream()).distinct().toArray(var0 -> new ItemStack[var0]);
      }
   }

   public boolean test(@Nullable ItemStack var1) {
      if (â˜ƒ == null) {
         return false;
      } else {
         this.dissolve();
         if (this.itemStacks.length == 0) {
            return â˜ƒ.isEmpty();
         } else {
            for(ItemStack â˜ƒ : this.itemStacks) {
               if (â˜ƒ.is(â˜ƒ.getItem())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public IntList getStackingIds() {
      if (this.stackingIds == null) {
         this.dissolve();
         this.stackingIds = new IntArrayList(this.itemStacks.length);

         for(ItemStack â˜ƒ : this.itemStacks) {
            this.stackingIds.add(StackedContents.getStackingIndex(â˜ƒ));
         }

         this.stackingIds.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.stackingIds;
   }

   public void toNetwork(FriendlyByteBuf var1) {
      this.dissolve();
      â˜ƒ.writeCollection(Arrays.asList(this.itemStacks), FriendlyByteBuf::writeItem);
   }

   public JsonElement toJson() {
      if (this.values.length == 1) {
         return this.values[0].serialize();
      } else {
         JsonArray â˜ƒ = new JsonArray();

         for(Ingredient.Value â˜ƒx : this.values) {
            â˜ƒ.add(â˜ƒx.serialize());
         }

         return â˜ƒ;
      }
   }

   public boolean isEmpty() {
      return this.values.length == 0 && (this.itemStacks == null || this.itemStacks.length == 0) && (this.stackingIds == null || this.stackingIds.isEmpty());
   }

   private static Ingredient fromValues(Stream<? extends Ingredient.Value> var0) {
      Ingredient â˜ƒ = new Ingredient(â˜ƒ);
      return â˜ƒ.values.length == 0 ? EMPTY : â˜ƒ;
   }

   public static Ingredient of() {
      return EMPTY;
   }

   public static Ingredient of(ItemLike... var0) {
      return of(Arrays.stream(â˜ƒ).map(ItemStack::new));
   }

   public static Ingredient of(ItemStack... var0) {
      return of(Arrays.stream(â˜ƒ));
   }

   public static Ingredient of(Stream<ItemStack> var0) {
      return fromValues(â˜ƒ.filter(var0x -> !var0x.isEmpty()).map(Ingredient.ItemValue::new));
   }

   public static Ingredient of(Tag<Item> var0) {
      return fromValues(Stream.of(new Ingredient.TagValue(â˜ƒ)));
   }

   public static Ingredient fromNetwork(FriendlyByteBuf var0) {
      return fromValues(â˜ƒ.readList(FriendlyByteBuf::readItem).stream().map(Ingredient.ItemValue::new));
   }

   public static Ingredient fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ == null || â˜ƒ.isJsonNull()) {
         throw new JsonSyntaxException("Item cannot be null");
      } else if (â˜ƒ.isJsonObject()) {
         return fromValues(Stream.of(valueFromJson(â˜ƒ.getAsJsonObject())));
      } else if (â˜ƒ.isJsonArray()) {
         JsonArray â˜ƒ = â˜ƒ.getAsJsonArray();
         if (â˜ƒ.size() == 0) {
            throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
         } else {
            return fromValues(StreamSupport.stream(â˜ƒ.spliterator(), false).map(var0x -> valueFromJson(GsonHelper.convertToJsonObject(var0x, "item"))));
         }
      } else {
         throw new JsonSyntaxException("Expected item to be object or array of objects");
      }
   }

   private static Ingredient.Value valueFromJson(JsonObject var0) {
      if (â˜ƒ.has("item") && â˜ƒ.has("tag")) {
         throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
      } else if (â˜ƒ.has("item")) {
         Item â˜ƒ = ShapedRecipe.itemFromJson(â˜ƒ);
         return new Ingredient.ItemValue(new ItemStack(â˜ƒ));
      } else if (â˜ƒ.has("tag")) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "tag"));
         Tag<Item> â˜ƒx = SerializationTags.getInstance()
            .getTagOrThrow(Registry.ITEM_REGISTRY, â˜ƒ, var0x -> new JsonSyntaxException("Unknown item tag '" + var0x + "'"));
         return new Ingredient.TagValue(â˜ƒx);
      } else {
         throw new JsonParseException("An ingredient entry needs either a tag or an item");
      }
   }

   static class ItemValue implements Ingredient.Value {
      private final ItemStack item;

      ItemValue(ItemStack var1) {
         this.item = â˜ƒ;
      }

      @Override
      public Collection<ItemStack> getItems() {
         return Collections.singleton(this.item);
      }

      @Override
      public JsonObject serialize() {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("item", Registry.ITEM.getKey(this.item.getItem()).toString());
         return â˜ƒ;
      }
   }

   static class TagValue implements Ingredient.Value {
      private final Tag<Item> tag;

      TagValue(Tag<Item> var1) {
         this.tag = â˜ƒ;
      }

      @Override
      public Collection<ItemStack> getItems() {
         List<ItemStack> â˜ƒ = Lists.<ItemStack>newArrayList();

         for(Item â˜ƒx : this.tag.getValues()) {
            â˜ƒ.add(new ItemStack(â˜ƒx));
         }

         return â˜ƒ;
      }

      @Override
      public JsonObject serialize() {
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty(
            "tag",
            SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, this.tag, () -> new IllegalStateException("Unknown item tag")).toString()
         );
         return â˜ƒ;
      }
   }

   interface Value {
      Collection<ItemStack> getItems();

      JsonObject serialize();
   }
}
