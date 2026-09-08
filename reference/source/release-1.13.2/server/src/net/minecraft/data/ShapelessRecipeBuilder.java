package net.minecraft.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShapelessRecipeBuilder {
   private static final Logger field_200493_a = LogManager.getLogger();
   private final Item field_200494_b;
   private final int field_200495_c;
   private final List<Ingredient> field_200496_d = Lists.<Ingredient>newArrayList();
   private final Advancement.Builder field_200497_e = Advancement.Builder.func_200278_a();
   private String field_200498_f;

   public ShapelessRecipeBuilder(IItemProvider var1, int var2) {
      this.field_200494_b = ☃.func_199767_j();
      this.field_200495_c = ☃;
   }

   public static ShapelessRecipeBuilder func_200486_a(IItemProvider var0) {
      return new ShapelessRecipeBuilder(☃, 1);
   }

   public static ShapelessRecipeBuilder func_200488_a(IItemProvider var0, int var1) {
      return new ShapelessRecipeBuilder(☃, ☃);
   }

   public ShapelessRecipeBuilder func_203221_a(Tag<Item> var1) {
      return this.func_200489_a(Ingredient.func_199805_a(☃));
   }

   public ShapelessRecipeBuilder func_200487_b(IItemProvider var1) {
      return this.func_200491_b(☃, 1);
   }

   public ShapelessRecipeBuilder func_200491_b(IItemProvider var1, int var2) {
      for(int ☃ = 0; ☃ < ☃; ++☃) {
         this.func_200489_a(Ingredient.func_199804_a(☃));
      }

      return this;
   }

   public ShapelessRecipeBuilder func_200489_a(Ingredient var1) {
      return this.func_200492_a(☃, 1);
   }

   public ShapelessRecipeBuilder func_200492_a(Ingredient var1, int var2) {
      for(int ☃ = 0; ☃ < ☃; ++☃) {
         this.field_200496_d.add(☃);
      }

      return this;
   }

   public ShapelessRecipeBuilder func_200483_a(String var1, ICriterionInstance var2) {
      this.field_200497_e.func_200275_a(☃, ☃);
      return this;
   }

   public ShapelessRecipeBuilder func_200490_a(String var1) {
      this.field_200498_f = ☃;
      return this;
   }

   public void func_200482_a(Consumer<IFinishedRecipe> var1) {
      this.func_200485_a(☃, IRegistry.field_212630_s.func_177774_c(this.field_200494_b));
   }

   public void func_200484_a(Consumer<IFinishedRecipe> var1, String var2) {
      ResourceLocation ☃ = IRegistry.field_212630_s.func_177774_c(this.field_200494_b);
      if (new ResourceLocation(☃).equals(☃)) {
         throw new IllegalStateException("Shapeless Recipe " + ☃ + " should remove its 'save' argument");
      } else {
         this.func_200485_a(☃, new ResourceLocation(☃));
      }
   }

   public void func_200485_a(Consumer<IFinishedRecipe> var1, ResourceLocation var2) {
      this.func_200481_a(☃);
      this.field_200497_e
         .func_200272_a(new ResourceLocation("minecraft:recipes/root"))
         .func_200275_a("has_the_recipe", new RecipeUnlockedTrigger.Instance(☃))
         .func_200271_a(AdvancementRewards.Builder.func_200280_c(☃))
         .func_200270_a(RequirementsStrategy.OR);
      ☃.accept(
         new ShapelessRecipeBuilder.Result(
            ☃,
            this.field_200494_b,
            this.field_200495_c,
            this.field_200498_f == null ? "" : this.field_200498_f,
            this.field_200496_d,
            this.field_200497_e,
            new ResourceLocation(☃.func_110624_b(), "recipes/" + this.field_200494_b.func_77640_w().func_200300_c() + "/" + ☃.func_110623_a())
         )
      );
   }

   private void func_200481_a(ResourceLocation var1) {
      if (this.field_200497_e.func_200277_c().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + ☃);
      }
   }

   public static class Result implements IFinishedRecipe {
      private final ResourceLocation field_200453_a;
      private final Item field_200454_b;
      private final int field_200455_c;
      private final String field_200456_d;
      private final List<Ingredient> field_200457_e;
      private final Advancement.Builder field_200458_f;
      private final ResourceLocation field_200459_g;

      public Result(ResourceLocation var1, Item var2, int var3, String var4, List<Ingredient> var5, Advancement.Builder var6, ResourceLocation var7) {
         this.field_200453_a = ☃;
         this.field_200454_b = ☃;
         this.field_200455_c = ☃;
         this.field_200456_d = ☃;
         this.field_200457_e = ☃;
         this.field_200458_f = ☃;
         this.field_200459_g = ☃;
      }

      @Override
      public JsonObject func_200441_a() {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("type", "crafting_shapeless");
         if (!this.field_200456_d.isEmpty()) {
            ☃.addProperty("group", this.field_200456_d);
         }

         JsonArray ☃ = new JsonArray();

         for(Ingredient ☃x : this.field_200457_e) {
            ☃.add(☃x.func_200304_c());
         }

         ☃.add("ingredients", ☃);
         JsonObject ☃x = new JsonObject();
         ☃x.addProperty("item", IRegistry.field_212630_s.func_177774_c(this.field_200454_b).toString());
         if (this.field_200455_c > 1) {
            ☃x.addProperty("count", this.field_200455_c);
         }

         ☃.add("result", ☃x);
         return ☃;
      }

      @Override
      public ResourceLocation func_200442_b() {
         return this.field_200453_a;
      }

      @Nullable
      @Override
      public JsonObject func_200440_c() {
         return this.field_200458_f.func_200273_b();
      }

      @Nullable
      @Override
      public ResourceLocation func_200443_d() {
         return this.field_200459_g;
      }
   }
}
