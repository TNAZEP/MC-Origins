package net.minecraft.data.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
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

public class ShapedRecipeBuilder implements RecipeBuilder {
   private final Item result;
   private final int count;
   private final List<String> rows = Lists.newArrayList();
   private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   @Nullable
   private String group;

   public ShapedRecipeBuilder(ItemLike var1, int var2) {
      this.result = â˜ƒ.asItem();
      this.count = â˜ƒ;
   }

   public static ShapedRecipeBuilder shaped(ItemLike var0) {
      return shaped(â˜ƒ, 1);
   }

   public static ShapedRecipeBuilder shaped(ItemLike var0, int var1) {
      return new ShapedRecipeBuilder(â˜ƒ, â˜ƒ);
   }

   public ShapedRecipeBuilder define(Character var1, Tag<Item> var2) {
      return this.define(â˜ƒ, Ingredient.of(â˜ƒ));
   }

   public ShapedRecipeBuilder define(Character var1, ItemLike var2) {
      return this.define(â˜ƒ, Ingredient.of(â˜ƒ));
   }

   public ShapedRecipeBuilder define(Character var1, Ingredient var2) {
      if (this.key.containsKey(â˜ƒ)) {
         throw new IllegalArgumentException("Symbol '" + â˜ƒ + "' is already defined!");
      } else if (â˜ƒ == ' ') {
         throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
      } else {
         this.key.put(â˜ƒ, â˜ƒ);
         return this;
      }
   }

   public ShapedRecipeBuilder pattern(String var1) {
      if (!this.rows.isEmpty() && â˜ƒ.length() != ((String)this.rows.get(0)).length()) {
         throw new IllegalArgumentException("Pattern must be the same width on every line!");
      } else {
         this.rows.add(â˜ƒ);
         return this;
      }
   }

   public ShapedRecipeBuilder unlockedBy(String var1, CriterionTriggerInstance var2) {
      this.advancement.addCriterion(â˜ƒ, â˜ƒ);
      return this;
   }

   public ShapedRecipeBuilder group(@Nullable String var1) {
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
         new ShapedRecipeBuilder.Result(
            â˜ƒ,
            this.result,
            this.count,
            this.group == null ? "" : this.group,
            this.rows,
            this.key,
            this.advancement,
            new ResourceLocation(â˜ƒ.getNamespace(), "recipes/" + this.result.getItemCategory().getRecipeFolderName() + "/" + â˜ƒ.getPath())
         )
      );
   }

   private void ensureValid(ResourceLocation var1) {
      if (this.rows.isEmpty()) {
         throw new IllegalStateException("No pattern is defined for shaped recipe " + â˜ƒ + "!");
      } else {
         Set<Character> â˜ƒ = Sets.newHashSet(this.key.keySet());
         â˜ƒ.remove(' ');

         for(String â˜ƒx : this.rows) {
            for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx.length(); ++â˜ƒxx) {
               char â˜ƒxxx = â˜ƒx.charAt(â˜ƒxx);
               if (!this.key.containsKey(â˜ƒxxx) && â˜ƒxxx != ' ') {
                  throw new IllegalStateException("Pattern in recipe " + â˜ƒ + " uses undefined symbol '" + â˜ƒxxx + "'");
               }

               â˜ƒ.remove(â˜ƒxxx);
            }
         }

         if (!â˜ƒ.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + â˜ƒ);
         } else if (this.rows.size() == 1 && ((String)this.rows.get(0)).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + â˜ƒ + " only takes in a single item - should it be a shapeless recipe instead?");
         } else if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + â˜ƒ);
         }
      }
   }

   static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final Item result;
      private final int count;
      private final String group;
      private final List<String> pattern;
      private final Map<Character, Ingredient> key;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;

      public Result(
         ResourceLocation var1,
         Item var2,
         int var3,
         String var4,
         List<String> var5,
         Map<Character, Ingredient> var6,
         Advancement.Builder var7,
         ResourceLocation var8
      ) {
         this.id = â˜ƒ;
         this.result = â˜ƒ;
         this.count = â˜ƒ;
         this.group = â˜ƒ;
         this.pattern = â˜ƒ;
         this.key = â˜ƒ;
         this.advancement = â˜ƒ;
         this.advancementId = â˜ƒ;
      }

      @Override
      public void serializeRecipeData(JsonObject var1) {
         if (!this.group.isEmpty()) {
            â˜ƒ.addProperty("group", this.group);
         }

         JsonArray â˜ƒ = new JsonArray();

         for(String â˜ƒx : this.pattern) {
            â˜ƒ.add(â˜ƒx);
         }

         â˜ƒ.add("pattern", â˜ƒ);
         JsonObject â˜ƒx = new JsonObject();

         for(Entry<Character, Ingredient> â˜ƒxx : this.key.entrySet()) {
            â˜ƒx.add(String.valueOf(â˜ƒxx.getKey()), ((Ingredient)â˜ƒxx.getValue()).toJson());
         }

         â˜ƒ.add("key", â˜ƒx);
         JsonObject â˜ƒxx = new JsonObject();
         â˜ƒxx.addProperty("item", Registry.ITEM.getKey(this.result).toString());
         if (this.count > 1) {
            â˜ƒxx.addProperty("count", this.count);
         }

         â˜ƒ.add("result", â˜ƒxx);
      }

      @Override
      public RecipeSerializer<?> getType() {
         return RecipeSerializer.SHAPED_RECIPE;
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
