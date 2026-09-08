package net.minecraft.data;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.RecipeSerializers;
import net.minecraft.util.ResourceLocation;

public class CustomRecipeBuilder {
   private final RecipeSerializers.SimpleSerializer<?> field_200501_a;

   public CustomRecipeBuilder(RecipeSerializers.SimpleSerializer<?> var1) {
      this.field_200501_a = ☃;
   }

   public static CustomRecipeBuilder func_200500_a(RecipeSerializers.SimpleSerializer<?> var0) {
      return new CustomRecipeBuilder(☃);
   }

   public void func_200499_a(Consumer<IFinishedRecipe> var1, final String var2) {
      ☃.accept(new IFinishedRecipe() {
         @Override
         public JsonObject func_200441_a() {
            JsonObject ☃ = new JsonObject();
            ☃.addProperty("type", CustomRecipeBuilder.this.field_200501_a.func_199567_a());
            return ☃;
         }

         @Override
         public ResourceLocation func_200442_b() {
            return new ResourceLocation(☃);
         }

         @Nullable
         @Override
         public JsonObject func_200440_c() {
            return null;
         }

         @Nullable
         @Override
         public ResourceLocation func_200443_d() {
            return new ResourceLocation("");
         }
      });
   }
}
