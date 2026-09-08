package net.minecraft.data.recipes;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public interface RecipeBuilder {
   RecipeBuilder unlockedBy(String var1, CriterionTriggerInstance var2);

   RecipeBuilder group(@Nullable String var1);

   Item getResult();

   void save(Consumer<FinishedRecipe> var1, ResourceLocation var2);

   default void save(Consumer<FinishedRecipe> var1) {
      this.save(â˜ƒ, getDefaultRecipeId(this.getResult()));
   }

   default void save(Consumer<FinishedRecipe> var1, String var2) {
      ResourceLocation â˜ƒ = getDefaultRecipeId(this.getResult());
      ResourceLocation â˜ƒx = new ResourceLocation(â˜ƒ);
      if (â˜ƒx.equals(â˜ƒ)) {
         throw new IllegalStateException("Recipe " + â˜ƒ + " should remove its 'save' argument as it is equal to default one");
      } else {
         this.save(â˜ƒ, â˜ƒx);
      }
   }

   static ResourceLocation getDefaultRecipeId(ItemLike var0) {
      return Registry.ITEM.getKey(â˜ƒ.asItem());
   }
}
