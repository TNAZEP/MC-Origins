package net.minecraft.data.recipes;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class ShapelessRecipeBuilder implements RecipeBuilder {
   private final Item result;
   private final int count;
   private final List<Ingredient> ingredients = Lists.<Ingredient>newArrayList();
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   @Nullable
   private String group;

   public ShapelessRecipeBuilder(ItemLike var1, int var2) {
      this.result = â˜ƒ.asItem();
      this.count = â˜ƒ;
   }

   public static ShapelessRecipeBuilder shapeless(ItemLike var0) {
      return new ShapelessRecipeBuilder(â˜ƒ, 1);
   }

   public static ShapelessRecipeBuilder shapeless(ItemLike var0, int var1) {
      return new ShapelessRecipeBuilder(â˜ƒ, â˜ƒ);
   }

   public ShapelessRecipeBuilder requires(Tag<Item> var1) {
      return this.requires(Ingredient.of(â˜ƒ));
   }

   public ShapelessRecipeBuilder requires(ItemLike var1) {
      return this.requires(â˜ƒ, 1);
   }

   public ShapelessRecipeBuilder requires(ItemLike var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.requires(Ingredient.of(â˜ƒ));
      }

      return this;
   }

   public ShapelessRecipeBuilder requires(Ingredient var1) {
      return this.requires(â˜ƒ, 1);
   }

   public ShapelessRecipeBuilder requires(Ingredient var1, int var2) {
      for(int â˜ƒ = 0; â˜ƒ < â˜ƒ; ++â˜ƒ) {
         this.ingredients.add(â˜ƒ);
      }

      return this;
   }

   public ShapelessRecipeBuilder unlockedBy(String var1, CriterionTriggerInstance var2) {
      this.advancement.addCriterion(â˜ƒ, â˜ƒ);
      return this;
   }

   public ShapelessRecipeBuilder group(@Nullable String var1) {
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
         new ShapelessRecipeBuilder.Result(
            â˜ƒ,
            this.result,
            this.count,
            this.group == null ? "" : this.group,
            this.ingredients,
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
      private final Item result;
      private final int count;
      private final String group;
      private final List<Ingredient> ingredients;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;

      public Result(ResourceLocation var1, Item var2, int var3, String var4, List<Ingredient> var5, Advancement.Builder var6, ResourceLocation var7) {
         this.id = â˜ƒ;
         this.result = â˜ƒ;
         this.count = â˜ƒ;
         this.group = â˜ƒ;
         this.ingredients = â˜ƒ;
         this.advancement = â˜ƒ;
         this.advancementId = â˜ƒ;
      }

      @Override
      public void serializeRecipeData(JsonObject var1) {
         if (!this.group.isEmpty()) {
            â˜ƒ.addProperty("group", this.group);
         }

         JsonArray â˜ƒ = new JsonArray();

         for(Ingredient â˜ƒx : this.ingredients) {
            â˜ƒ.add(â˜ƒx.toJson());
         }

         â˜ƒ.add("ingredients", â˜ƒ);
         JsonObject â˜ƒx = new JsonObject();
         â˜ƒx.addProperty("item", Registry.ITEM.getKey(this.result).toString());
         if (this.count > 1) {
            â˜ƒx.addProperty("count", this.count);
         }

         â˜ƒ.add("result", â˜ƒx);
      }

      @Override
      public RecipeSerializer<?> getType() {
         return RecipeSerializer.SHAPELESS_RECIPE;
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
