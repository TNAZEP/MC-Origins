package net.minecraft.world.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShapelessRecipe implements CraftingRecipe {
   private final ResourceLocation id;
   final String group;
   final ItemStack result;
   final NonNullList<Ingredient> ingredients;

   public ShapelessRecipe(ResourceLocation var1, String var2, ItemStack var3, NonNullList<Ingredient> var4) {
      this.id = â˜ƒ;
      this.group = â˜ƒ;
      this.result = â˜ƒ;
      this.ingredients = â˜ƒ;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHAPELESS_RECIPE;
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
      return this.ingredients;
   }

   public boolean matches(CraftingContainer var1, Level var2) {
      StackedContents â˜ƒ = new StackedContents();
      int â˜ƒx = 0;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getContainerSize(); ++â˜ƒxx) {
         ItemStack â˜ƒxxx = â˜ƒ.getItem(â˜ƒxx);
         if (!â˜ƒxxx.isEmpty()) {
            ++â˜ƒx;
            â˜ƒ.accountStack(â˜ƒxxx, 1);
         }
      }

      return â˜ƒx == this.ingredients.size() && â˜ƒ.canCraft(this, null);
   }

   public ItemStack assemble(CraftingContainer var1) {
      return this.result.copy();
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return â˜ƒ * â˜ƒ >= this.ingredients.size();
   }

   public static class Serializer implements RecipeSerializer<ShapelessRecipe> {
      public ShapelessRecipe fromJson(ResourceLocation var1, JsonObject var2) {
         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "group", "");
         NonNullList<Ingredient> â˜ƒx = itemsFromJson(GsonHelper.getAsJsonArray(â˜ƒ, "ingredients"));
         if (â˜ƒx.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if (â˜ƒx.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe");
         } else {
            ItemStack â˜ƒ = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(â˜ƒ, "result"));
            return new ShapelessRecipe(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
         }
      }

      private static NonNullList<Ingredient> itemsFromJson(JsonArray var0) {
         NonNullList<Ingredient> â˜ƒ = NonNullList.create();

         for(int â˜ƒx = 0; â˜ƒx < â˜ƒ.size(); ++â˜ƒx) {
            Ingredient â˜ƒxx = Ingredient.fromJson(â˜ƒ.get(â˜ƒx));
            if (!â˜ƒxx.isEmpty()) {
               â˜ƒ.add(â˜ƒxx);
            }
         }

         return â˜ƒ;
      }

      public ShapelessRecipe fromNetwork(ResourceLocation var1, FriendlyByteBuf var2) {
         String â˜ƒ = â˜ƒ.readUtf();
         int â˜ƒx = â˜ƒ.readVarInt();
         NonNullList<Ingredient> â˜ƒxx = NonNullList.withSize(â˜ƒx, Ingredient.EMPTY);

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx.size(); ++â˜ƒxxx) {
            â˜ƒxx.set(â˜ƒxxx, Ingredient.fromNetwork(â˜ƒ));
         }

         ItemStack â˜ƒxxx = â˜ƒ.readItem();
         return new ShapelessRecipe(â˜ƒ, â˜ƒ, â˜ƒxxx, â˜ƒxx);
      }

      public void toNetwork(FriendlyByteBuf var1, ShapelessRecipe var2) {
         â˜ƒ.writeUtf(â˜ƒ.group);
         â˜ƒ.writeVarInt(â˜ƒ.ingredients.size());

         for(Ingredient â˜ƒ : â˜ƒ.ingredients) {
            â˜ƒ.toNetwork(â˜ƒ);
         }

         â˜ƒ.writeItem(â˜ƒ.result);
      }
   }
}
