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
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;

public class SimpleCookingRecipeBuilder implements RecipeBuilder {
   private final Item result;
   private final Ingredient ingredient;
   private final float experience;
   private final int cookingTime;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   @Nullable
   private String group;
   private final SimpleCookingSerializer<?> serializer;

   private SimpleCookingRecipeBuilder(ItemLike var1, Ingredient var2, float var3, int var4, SimpleCookingSerializer<?> var5) {
      this.result = â˜ƒ.asItem();
      this.ingredient = â˜ƒ;
      this.experience = â˜ƒ;
      this.cookingTime = â˜ƒ;
      this.serializer = â˜ƒ;
   }

   public static SimpleCookingRecipeBuilder cooking(Ingredient var0, ItemLike var1, float var2, int var3, SimpleCookingSerializer<?> var4) {
      return new SimpleCookingRecipeBuilder(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static SimpleCookingRecipeBuilder campfireCooking(Ingredient var0, ItemLike var1, float var2, int var3) {
      return cooking(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
   }

   public static SimpleCookingRecipeBuilder blasting(Ingredient var0, ItemLike var1, float var2, int var3) {
      return cooking(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, RecipeSerializer.BLASTING_RECIPE);
   }

   public static SimpleCookingRecipeBuilder smelting(Ingredient var0, ItemLike var1, float var2, int var3) {
      return cooking(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, RecipeSerializer.SMELTING_RECIPE);
   }

   public static SimpleCookingRecipeBuilder smoking(Ingredient var0, ItemLike var1, float var2, int var3) {
      return cooking(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, RecipeSerializer.SMOKING_RECIPE);
   }

   public SimpleCookingRecipeBuilder unlockedBy(String var1, CriterionTriggerInstance var2) {
      this.advancement.addCriterion(â˜ƒ, â˜ƒ);
      return this;
   }

   public SimpleCookingRecipeBuilder group(@Nullable String var1) {
      this.group = â˜ƒ;
      return this;
   }

   @Override
   public Item getResult() {
      return this.result;
   }

   @Override
   public void save(Consumer<FinishedRecipe> var1, ResourceLocation var2) {
      this.ensureValid(â˜ƒ);
      this.advancement
         .parent(new ResourceLocation("recipes/root"))
         .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(â˜ƒ))
         .rewards(AdvancementRewards.Builder.recipe(â˜ƒ))
         .requirements(RequirementsStrategy.OR);
      â˜ƒ.accept(
         new SimpleCookingRecipeBuilder.Result(
            â˜ƒ,
            this.group == null ? "" : this.group,
            this.ingredient,
            this.result,
            this.experience,
            this.cookingTime,
            this.advancement,
            new ResourceLocation(â˜ƒ.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + â˜ƒ.getPath()),
            this.serializer
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
      private final String group;
      private final Ingredient ingredient;
      private final Item result;
      private final float experience;
      private final int cookingTime;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;
      private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

      public Result(
         ResourceLocation var1,
         String var2,
         Ingredient var3,
         Item var4,
         float var5,
         int var6,
         Advancement.Builder var7,
         ResourceLocation var8,
         RecipeSerializer<? extends AbstractCookingRecipe> var9
      ) {
         this.id = â˜ƒ;
         this.group = â˜ƒ;
         this.ingredient = â˜ƒ;
         this.result = â˜ƒ;
         this.experience = â˜ƒ;
         this.cookingTime = â˜ƒ;
         this.advancement = â˜ƒ;
         this.advancementId = â˜ƒ;
         this.serializer = â˜ƒ;
      }

      @Override
      public void serializeRecipeData(JsonObject var1) {
         if (!this.group.isEmpty()) {
            â˜ƒ.addProperty("group", this.group);
         }

         â˜ƒ.add("ingredient", this.ingredient.toJson());
         â˜ƒ.addProperty("result", Registry.ITEM.getKey(this.result).toString());
         â˜ƒ.addProperty("experience", this.experience);
         â˜ƒ.addProperty("cookingtime", this.cookingTime);
      }

      @Override
      public RecipeSerializer<?> getType() {
         return this.serializer;
      }

      @Override
      public ResourceLocation getId() {
         return this.id;
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
