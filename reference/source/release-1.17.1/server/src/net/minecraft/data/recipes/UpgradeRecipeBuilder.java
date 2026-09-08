package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class UpgradeRecipeBuilder {
   private final Ingredient base;
   private final Ingredient addition;
   private final Item result;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   private final RecipeSerializer<?> type;

   public UpgradeRecipeBuilder(RecipeSerializer<?> var1, Ingredient var2, Ingredient var3, Item var4) {
      this.type = â˜ƒ;
      this.base = â˜ƒ;
      this.addition = â˜ƒ;
      this.result = â˜ƒ;
   }

   public static UpgradeRecipeBuilder smithing(Ingredient var0, Ingredient var1, Item var2) {
      return new UpgradeRecipeBuilder(RecipeSerializer.SMITHING, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public UpgradeRecipeBuilder unlocks(String var1, CriterionTriggerInstance var2) {
      this.advancement.addCriterion(â˜ƒ, â˜ƒ);
      return this;
   }

   public void save(Consumer<FinishedRecipe> var1, String var2) {
      this.save(â˜ƒ, new ResourceLocation(â˜ƒ));
   }

   public void save(Consumer<FinishedRecipe> var1, ResourceLocation var2) {
      this.ensureValid(â˜ƒ);
      this.advancement
         .parent(new ResourceLocation("recipes/root"))
         .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(â˜ƒ))
         .rewards(AdvancementRewards.Builder.recipe(â˜ƒ))
         .requirements(RequirementsStrategy.OR);
      â˜ƒ.accept(
         new UpgradeRecipeBuilder.Result(
            â˜ƒ,
            this.type,
            this.base,
            this.addition,
            this.result,
            this.advancement,
            new ResourceLocation(â˜ƒ.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + â˜ƒ.getPath())
         )
      );
   }

   private void ensureValid(ResourceLocation var1) {
      if (this.advancement.getCriteria().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + â˜ƒ);
      }
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final Ingredient base;
      private final Ingredient addition;
      private final Item result;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;
      private final RecipeSerializer<?> type;

      public Result(
         ResourceLocation var1, RecipeSerializer<?> var2, Ingredient var3, Ingredient var4, Item var5, Advancement.Builder var6, ResourceLocation var7
      ) {
         this.id = â˜ƒ;
         this.type = â˜ƒ;
         this.base = â˜ƒ;
         this.addition = â˜ƒ;
         this.result = â˜ƒ;
         this.advancement = â˜ƒ;
         this.advancementId = â˜ƒ;
      }

      @Override
      public void serializeRecipeData(JsonObject var1) {
         â˜ƒ.add("base", this.base.toJson());
         â˜ƒ.add("addition", this.addition.toJson());
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.addProperty("item", Registry.ITEM.getKey(this.result).toString());
         â˜ƒ.add("result", â˜ƒ);
      }

      @Override
      public ResourceLocation getId() {
         return this.id;
      }

      @Override
      public RecipeSerializer<?> getType() {
         return this.type;
      }

      @Nullable
      @Override
      public JsonObject serializeAdvancement() {
         return this.advancement.serializeToJson();
      }

      @Nullable
      @Override
      public ResourceLocation getAdvancementId() {
         return this.advancementId;
      }
   }
}
