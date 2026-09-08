package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface FinishedRecipe {
   void serializeRecipeData(JsonObject var1);

   default JsonObject serializeRecipe() {
      JsonObject â˜ƒ = new JsonObject();
      â˜ƒ.addProperty("type", Registry.RECIPE_SERIALIZER.getKey(this.getType()).toString());
      this.serializeRecipeData(â˜ƒ);
      return â˜ƒ;
   }

   ResourceLocation getId();

   RecipeSerializer<?> getType();

   @Nullable
   JsonObject serializeAdvancement();

   @Nullable
   ResourceLocation getAdvancementId();
}
