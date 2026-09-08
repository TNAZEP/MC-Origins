package net.minecraft.world.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShapedRecipe implements CraftingRecipe {
   final int width;
   final int height;
   final NonNullList<Ingredient> recipeItems;
   final ItemStack result;
   private final ResourceLocation id;
   final String group;

   public ShapedRecipe(ResourceLocation var1, String var2, int var3, int var4, NonNullList<Ingredient> var5, ItemStack var6) {
      this.id = â˜ƒ;
      this.group = â˜ƒ;
      this.width = â˜ƒ;
      this.height = â˜ƒ;
      this.recipeItems = â˜ƒ;
      this.result = â˜ƒ;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHAPED_RECIPE;
   }

   @Override
   public String getGroup() {
      return this.group;
   }

   @Override
   public ItemStack getResultItem() {
      return this.result;
   }

   @Override
   public NonNullList<Ingredient> getIngredients() {
      return this.recipeItems;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ >= this.width && â˜ƒ >= this.height;
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      for(int â˜ƒ = 0; â˜ƒ <= â˜ƒ.getWidth() - this.width; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx <= â˜ƒ.getHeight() - this.height; ++â˜ƒx) {
            if (this.matches(â˜ƒ, â˜ƒ, â˜ƒx, true)) {
               return true;
            }

            if (this.matches(â˜ƒ, â˜ƒ, â˜ƒx, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean matches(CraftingContainer var1, int var2, int var3, boolean var4) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.getWidth(); ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.getHeight(); ++â˜ƒx) {
            int â˜ƒxx = â˜ƒ - â˜ƒ;
            int â˜ƒxxx = â˜ƒx - â˜ƒ;
            Ingredient â˜ƒxxxx = Ingredient.EMPTY;
            if (â˜ƒxx >= 0 && â˜ƒxxx >= 0 && â˜ƒxx < this.width && â˜ƒxxx < this.height) {
               if (â˜ƒ) {
                  â˜ƒxxxx = this.recipeItems.get(this.width - â˜ƒxx - 1 + â˜ƒxxx * this.width);
               } else {
                  â˜ƒxxxx = this.recipeItems.get(â˜ƒxx + â˜ƒxxx * this.width);
               }
            }

            if (!â˜ƒxxxx.test(â˜ƒ.getItem(â˜ƒ + â˜ƒx * â˜ƒ.getWidth()))) {
               return false;
            }
         }
      }

      return true;
   }

   public ItemStack assemble(CraftingContainer var1) {
      return this.getResultItem().copy();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   static NonNullList<Ingredient> dissolvePattern(String[] var0, Map<String, Ingredient> var1, int var2, int var3) {
      NonNullList<Ingredient> â˜ƒ = NonNullList.withSize(â˜ƒ * â˜ƒ, Ingredient.EMPTY);
      Set<String> â˜ƒx = Sets.newHashSet(â˜ƒ.keySet());
      â˜ƒx.remove(" ");

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.length; ++â˜ƒxx) {
         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒ[â˜ƒxx].length(); ++â˜ƒxxx) {
            String â˜ƒxxxx = â˜ƒ[â˜ƒxx].substring(â˜ƒxxx, â˜ƒxxx + 1);
            Ingredient â˜ƒxxxxx = (Ingredient)â˜ƒ.get(â˜ƒxxxx);
            if (â˜ƒxxxxx == null) {
               throw new JsonSyntaxException("Pattern references symbol '" + â˜ƒxxxx + "' but it's not defined in the key");
            }

            â˜ƒx.remove(â˜ƒxxxx);
            â˜ƒ.set(â˜ƒxxx + â˜ƒ * â˜ƒxx, â˜ƒxxxxx);
         }
      }

      if (!â˜ƒx.isEmpty()) {
         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + â˜ƒx);
      } else {
         return â˜ƒ;
      }
   }

   @VisibleForTesting
   static String[] shrink(String... var0) {
      int â˜ƒ = Integer.MAX_VALUE;
      int â˜ƒx = 0;
      int â˜ƒxx = 0;
      int â˜ƒxxx = 0;

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒ.length; ++â˜ƒxxxx) {
         String â˜ƒxxxxx = â˜ƒ[â˜ƒxxxx];
         â˜ƒ = Math.min(â˜ƒ, firstNonSpace(â˜ƒxxxxx));
         int â˜ƒxxxxxx = lastNonSpace(â˜ƒxxxxx);
         â˜ƒx = Math.max(â˜ƒx, â˜ƒxxxxxx);
         if (â˜ƒxxxxxx < 0) {
            if (â˜ƒxx == â˜ƒxxxx) {
               ++â˜ƒxx;
            }

            ++â˜ƒxxx;
         } else {
            â˜ƒxxx = 0;
         }
      }

      if (â˜ƒ.length == â˜ƒxxx) {
         return new String[0];
      } else {
         String[] â˜ƒxxxx = new String[â˜ƒ.length - â˜ƒxxx - â˜ƒxx];

         for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxxx.length; ++â˜ƒxxxxx) {
            â˜ƒxxxx[â˜ƒxxxxx] = â˜ƒ[â˜ƒxxxxx + â˜ƒxx].substring(â˜ƒ, â˜ƒx + 1);
         }

         return â˜ƒxxxx;
      }
   }

   @Override
   public boolean isIncomplete() {
      NonNullList<Ingredient> â˜ƒ = this.getIngredients();
      return â˜ƒ.isEmpty() || â˜ƒ.stream().filter(var0 -> !var0.isEmpty()).anyMatch(var0 -> var0.getItems().length == 0);
   }

   private static int firstNonSpace(String var0) {
      int â˜ƒ = 0;

      while(â˜ƒ < â˜ƒ.length() && â˜ƒ.charAt(â˜ƒ) == ' ') {
         ++â˜ƒ;
      }

      return â˜ƒ;
   }

   private static int lastNonSpace(String var0) {
      int â˜ƒ = â˜ƒ.length() - 1;

      while(â˜ƒ >= 0 && â˜ƒ.charAt(â˜ƒ) == ' ') {
         --â˜ƒ;
      }

      return â˜ƒ;
   }

   static String[] patternFromJson(JsonArray var0) {
      String[] â˜ƒ = new String[â˜ƒ.size()];
      if (â˜ƒ.length > 3) {
         throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
      } else if (â˜ƒ.length == 0) {
         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
      } else {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ.length; ++â˜ƒ) {
            String â˜ƒx = GsonHelper.convertToString(â˜ƒ.get(â˜ƒ), "pattern[" + â˜ƒ + "]");
            if (â˜ƒx.length() > 3) {
               throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }

            if (â˜ƒ > 0 && â˜ƒ[0].length() != â˜ƒx.length()) {
               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }

            â˜ƒ[â˜ƒ] = â˜ƒx;
         }

         return â˜ƒ;
      }
   }

   static Map<String, Ingredient> keyFromJson(JsonObject var0) {
      Map<String, Ingredient> â˜ƒ = Maps.newHashMap();

      for(Entry<String, JsonElement> â˜ƒx : â˜ƒ.entrySet()) {
         if (((String)â˜ƒx.getKey()).length() != 1) {
            throw new JsonSyntaxException("Invalid key entry: '" + (String)â˜ƒx.getKey() + "' is an invalid symbol (must be 1 character only).");
         }

         if (" ".equals(â˜ƒx.getKey())) {
            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
         }

         â˜ƒ.put((String)â˜ƒx.getKey(), Ingredient.fromJson((JsonElement)â˜ƒx.getValue()));
      }

      â˜ƒ.put(" ", Ingredient.EMPTY);
      return â˜ƒ;
   }

   public static ItemStack itemStackFromJson(JsonObject var0) {
      Item â˜ƒ = itemFromJson(â˜ƒ);
      if (â˜ƒ.has("data")) {
         throw new JsonParseException("Disallowed data tag found");
      } else {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "count", 1);
         if (â˜ƒ < 1) {
            throw new JsonSyntaxException("Invalid output count: " + â˜ƒ);
         } else {
            return new ItemStack(â˜ƒ, â˜ƒ);
         }
      }
   }

   public static Item itemFromJson(JsonObject var0) {
      String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "item");
      Item â˜ƒx = (Item)Registry.ITEM.getOptional(new ResourceLocation(â˜ƒ)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + â˜ƒ + "'"));
      if (â˜ƒx == Items.AIR) {
         throw new JsonSyntaxException("Invalid item: " + â˜ƒ);
      } else {
         return â˜ƒx;
      }
   }

   public static class Serializer implements RecipeSerializer<ShapedRecipe> {
      public ShapedRecipe fromJson(ResourceLocation var1, JsonObject var2) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "group", "");
         Map<String, Ingredient> â˜ƒx = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(â˜ƒ, "key"));
         String[] â˜ƒxx = ShapedRecipe.shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(â˜ƒ, "pattern")));
         int â˜ƒxxx = â˜ƒxx[0].length();
         int â˜ƒxxxx = â˜ƒxx.length;
         NonNullList<Ingredient> â˜ƒxxxxx = ShapedRecipe.dissolvePattern(â˜ƒxx, â˜ƒx, â˜ƒxxx, â˜ƒxxxx);
         ItemStack â˜ƒxxxxxx = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(â˜ƒ, "result"));
         return new ShapedRecipe(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      }

      public ShapedRecipe fromNetwork(ResourceLocation var1, FriendlyByteBuf var2) {
         int â˜ƒ = â˜ƒ.readVarInt();
         int â˜ƒx = â˜ƒ.readVarInt();
         String â˜ƒxx = â˜ƒ.readUtf();
         NonNullList<Ingredient> â˜ƒxxx = NonNullList.withSize(â˜ƒ * â˜ƒx, Ingredient.EMPTY);

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx.size(); ++â˜ƒxxxx) {
            â˜ƒxxx.set(â˜ƒxxxx, Ingredient.fromNetwork(â˜ƒ));
         }

         ItemStack â˜ƒxxxx = â˜ƒ.readItem();
         return new ShapedRecipe(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒx, â˜ƒxxx, â˜ƒxxxx);
      }

      public void toNetwork(FriendlyByteBuf var1, ShapedRecipe var2) {
         â˜ƒ.writeVarInt(â˜ƒ.width);
         â˜ƒ.writeVarInt(â˜ƒ.height);
         â˜ƒ.writeUtf(â˜ƒ.group);

         for(Ingredient â˜ƒ : â˜ƒ.recipeItems) {
            â˜ƒ.toNetwork(â˜ƒ);
         }

         â˜ƒ.writeItem(â˜ƒ.result);
      }
   }
}
