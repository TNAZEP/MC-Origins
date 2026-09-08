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
import net.minecraft.world.level.ItemLike;

public class SingleItemRecipeBuilder implements RecipeBuilder {
   private final Item result;
   private final Ingredient ingredient;
   private final int count;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   @Nullable
   private String group;
   private final RecipeSerializer<?> type;

   public SingleItemRecipeBuilder(RecipeSerializer<?> var1, Ingredient var2, ItemLike var3, int var4) {
      this.type = â˜ƒ;
      this.result = â˜ƒ.asItem();
      this.ingredient = â˜ƒ;
      this.count = â˜ƒ;
   }

   public static SingleItemRecipeBuilder stonecutting(Ingredient var0, ItemLike var1) {
      return new SingleItemRecipeBuilder(RecipeSerializer.STONECUTTER, â˜ƒ, â˜ƒ, 1);
   }

   public static SingleItemRecipeBuilder stonecutting(Ingredient var0, ItemLike var1, int var2) {
      return new SingleItemRecipeBuilder(RecipeSerializer.STONECUTTER, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public SingleItemRecipeBuilder unlockedBy(String var1, CriterionTriggerInstance var2) {
      this.advancement.addCriterion(â˜ƒ, â˜ƒ);
      return this;
   }

   public SingleItemRecipeBuilder group(@Nullable String var1) {
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
         new SingleItemRecipeBuilder.Result(
            â˜ƒ,
            this.type,
            this.group == null ? "" : this.group,
            this.ingredient,
            this.result,
            this.count,
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
      private final String group;
      private final Ingredient ingredient;
      private final Item result;
      private final int count;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;
      private final RecipeSerializer<?> type;

      public Result(
         ResourceLocation var1, RecipeSerializer<?> var2, String var3, Ingredient var4, Item var5, int var6, Advancement.Builder var7, ResourceLocation var8
      ) {
         this.id = â˜ƒ;
         this.type = â˜ƒ;
         this.group = â˜ƒ;
         this.ingredient = â˜ƒ;
         this.result = â˜ƒ;
         this.count = â˜ƒ;
         this.advancement = â˜ƒ;
         this.advancementId = â˜ƒ;
      }

      @Override
      public void serializeRecipeData(JsonObject var1) {
         if (!this.group.isEmpty()) {
            â˜ƒ.addProperty("group", this.group);
         }

         â˜ƒ.add("ingredient", this.ingredient.toJson());
         â˜ƒ.addProperty("result", Registry.ITEM.getKey(this.result).toString());
         â˜ƒ.addProperty("count", this.count);
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
