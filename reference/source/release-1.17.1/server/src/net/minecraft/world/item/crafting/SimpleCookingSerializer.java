package net.minecraft.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class SimpleCookingSerializer<T extends AbstractCookingRecipe> implements RecipeSerializer<T> {
   private final int defaultCookingTime;
   private final SimpleCookingSerializer.CookieBaker<T> factory;

   public SimpleCookingSerializer(SimpleCookingSerializer.CookieBaker<T> var1, int var2) {
      this.defaultCookingTime = â˜ƒ;
      this.factory = â˜ƒ;
   }

   public T fromJson(ResourceLocation var1, JsonObject var2) {
      String â˜ƒ = GsonHelper.getAsString(â˜ƒ, "group", "");
      JsonElement â˜ƒx = (JsonElement)(GsonHelper.isArrayNode(â˜ƒ, "ingredient")
         ? GsonHelper.getAsJsonArray(â˜ƒ, "ingredient")
         : GsonHelper.getAsJsonObject(â˜ƒ, "ingredient"));
      Ingredient â˜ƒxx = Ingredient.fromJson(â˜ƒx);
      String â˜ƒxxx = GsonHelper.getAsString(â˜ƒ, "result");
      ResourceLocation â˜ƒxxxx = new ResourceLocation(â˜ƒxxx);
      ItemStack â˜ƒxxxxx = new ItemStack(
         (ItemLike)Registry.ITEM.getOptional(â˜ƒxxxx).orElseThrow(() -> new IllegalStateException("Item: " + â˜ƒ + " does not exist"))
      );
      float â˜ƒxxxxxx = GsonHelper.getAsFloat(â˜ƒ, "experience", 0.0F);
      int â˜ƒxxxxxxx = GsonHelper.getAsInt(â˜ƒ, "cookingtime", this.defaultCookingTime);
      return this.factory.create(â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx);
   }

   public T fromNetwork(ResourceLocation var1, FriendlyByteBuf var2) {
      String â˜ƒ = â˜ƒ.readUtf();
      Ingredient â˜ƒx = Ingredient.fromNetwork(â˜ƒ);
      ItemStack â˜ƒxx = â˜ƒ.readItem();
      float â˜ƒxxx = â˜ƒ.readFloat();
      int â˜ƒxxxx = â˜ƒ.readVarInt();
      return this.factory.create(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   public void toNetwork(FriendlyByteBuf var1, T var2) {
      â˜ƒ.writeUtf(â˜ƒ.group);
      â˜ƒ.ingredient.toNetwork(â˜ƒ);
      â˜ƒ.writeItem(â˜ƒ.result);
      â˜ƒ.writeFloat(â˜ƒ.experience);
      â˜ƒ.writeVarInt(â˜ƒ.cookingTime);
   }

   interface CookieBaker<T extends AbstractCookingRecipe> {
      T create(ResourceLocation var1, String var2, Ingredient var3, ItemStack var4, float var5, int var6);
   }
}
