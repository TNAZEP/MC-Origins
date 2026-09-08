package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public abstract class SingleItemRecipe implements Recipe<Container> {
   protected final Ingredient ingredient;
   protected final ItemStack result;
   private final RecipeType<?> type;
   private final RecipeSerializer<?> serializer;
   protected final ResourceLocation id;
   protected final String group;

   public SingleItemRecipe(RecipeType<?> var1, RecipeSerializer<?> var2, ResourceLocation var3, String var4, Ingredient var5, ItemStack var6) {
      this.type = â˜ƒ;
      this.serializer = â˜ƒ;
      this.id = â˜ƒ;
      this.group = â˜ƒ;
      this.ingredient = â˜ƒ;
      this.result = â˜ƒ;
   }

   @Override
   public RecipeType<?> getType() {
      return this.type;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return this.serializer;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
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
      NonNullList<Ingredient> â˜ƒ = NonNullList.create();
      â˜ƒ.add(this.ingredient);
      return â˜ƒ;
   }

   @Override
   public boolean canCraftInDimensions(int var1, int var2) {
      return true;
   }

   @Override
   public ItemStack assemble(Container var1) {
      return this.result.copy();
   }

   public static class Serializer<T extends SingleItemRecipe> implements RecipeSerializer<T> {
      final SingleItemRecipe.Serializer.SingleItemMaker<T> factory;

      protected Serializer(SingleItemRecipe.Serializer.SingleItemMaker<T> var1) {
         this.factory = â˜ƒ;
      }

      public T fromJson(ResourceLocation var1, JsonObject var2) {
         String â˜ƒx = GsonHelper.getAsString(â˜ƒ, "group", "");
         Ingredient â˜ƒ;
         if (GsonHelper.isArrayNode(â˜ƒ, "ingredient")) {
            â˜ƒ = Ingredient.fromJson(GsonHelper.getAsJsonArray(â˜ƒ, "ingredient"));
         } else {
            â˜ƒ = Ingredient.fromJson(GsonHelper.getAsJsonObject(â˜ƒ, "ingredient"));
         }

         String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "result");
         int â˜ƒx = GsonHelper.getAsInt(â˜ƒ, "count");
         ItemStack â˜ƒxx = new ItemStack(Registry.ITEM.get(new ResourceLocation(â˜ƒ)), â˜ƒx);
         return this.factory.create(â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒxx);
      }

      public T fromNetwork(ResourceLocation var1, FriendlyByteBuf var2) {
         String â˜ƒ = â˜ƒ.readUtf();
         Ingredient â˜ƒx = Ingredient.fromNetwork(â˜ƒ);
         ItemStack â˜ƒxx = â˜ƒ.readItem();
         return this.factory.create(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      public void toNetwork(FriendlyByteBuf var1, T var2) {
         â˜ƒ.writeUtf(â˜ƒ.group);
         â˜ƒ.ingredient.toNetwork(â˜ƒ);
         â˜ƒ.writeItem(â˜ƒ.result);
      }

      interface SingleItemMaker<T extends SingleItemRecipe> {
         T create(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4);
      }
   }
}
