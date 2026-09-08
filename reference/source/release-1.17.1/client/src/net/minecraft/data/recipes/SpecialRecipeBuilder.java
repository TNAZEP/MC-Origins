package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

public class SpecialRecipeBuilder {
   final SimpleRecipeSerializer<?> serializer;

   public SpecialRecipeBuilder(SimpleRecipeSerializer<?> var1) {
      this.serializer = â˜ƒ;
   }

   public static SpecialRecipeBuilder special(SimpleRecipeSerializer<?> var0) {
      return new SpecialRecipeBuilder(â˜ƒ);
   }

   public void save(Consumer<FinishedRecipe> var1, final String var2) {
      â˜ƒ.accept(new FinishedRecipe() {
         @Override
         public void serializeRecipeData(JsonObject var1) {
         }

         @Override
         public RecipeSerializer<?> getType() {
            return SpecialRecipeBuilder.this.serializer;
         }

         @Override
         public ResourceLocation getId() {
            return new ResourceLocation(â˜ƒ);
         }

         @Nullable
         @Override
         public JsonObject serializeAdvancement() {
            return null;
         }

         @Override
         public ResourceLocation getAdvancementId() {
            return new ResourceLocation("");
         }
      });
   }
}
