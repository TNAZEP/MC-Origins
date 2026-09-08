package net.minecraft.data;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FurnaceRecipeBuilder {
   private static final Logger field_202144_a = LogManager.getLogger();
   private final Item field_202145_b;
   private final Ingredient field_202146_c;
   private final float field_202147_d;
   private final int field_202148_e;
   private final Advancement.Builder field_202149_f = Advancement.Builder.func_200278_a();
   private String field_202150_g;

   public FurnaceRecipeBuilder(Ingredient var1, IItemProvider var2, float var3, int var4) {
      this.field_202145_b = ☃.func_199767_j();
      this.field_202146_c = ☃;
      this.field_202147_d = ☃;
      this.field_202148_e = ☃;
   }

   public static FurnaceRecipeBuilder func_202138_a(Ingredient var0, IItemProvider var1, float var2, int var3) {
      return new FurnaceRecipeBuilder(☃, ☃, ☃, ☃);
   }

   public FurnaceRecipeBuilder func_202139_a(String var1, ICriterionInstance var2) {
      this.field_202149_f.func_200275_a(☃, ☃);
      return this;
   }

   public void func_202140_a(Consumer<IFinishedRecipe> var1) {
      this.func_202143_a(☃, IRegistry.field_212630_s.func_177774_c(this.field_202145_b));
   }

   public void func_202141_a(Consumer<IFinishedRecipe> var1, String var2) {
      ResourceLocation ☃ = IRegistry.field_212630_s.func_177774_c(this.field_202145_b);
      if (new ResourceLocation(☃).equals(☃)) {
         throw new IllegalStateException("Smelting Recipe " + ☃ + " should remove its 'save' argument");
      } else {
         this.func_202143_a(☃, new ResourceLocation(☃));
      }
   }

   public void func_202143_a(Consumer<IFinishedRecipe> var1, ResourceLocation var2) {
      this.func_202142_a(☃);
      this.field_202149_f
         .func_200272_a(new ResourceLocation("minecraft:recipes/root"))
         .func_200275_a("has_the_recipe", new RecipeUnlockedTrigger.Instance(☃))
         .func_200271_a(AdvancementRewards.Builder.func_200280_c(☃))
         .func_200270_a(RequirementsStrategy.OR);
      ☃.accept(
         new FurnaceRecipeBuilder.Result(
            ☃,
            this.field_202150_g == null ? "" : this.field_202150_g,
            this.field_202146_c,
            this.field_202145_b,
            this.field_202147_d,
            this.field_202148_e,
            this.field_202149_f,
            new ResourceLocation(☃.func_110624_b(), "recipes/" + this.field_202145_b.func_77640_w().func_200300_c() + "/" + ☃.func_110623_a())
         )
      );
   }

   private void func_202142_a(ResourceLocation var1) {
      if (this.field_202149_f.func_200277_c().isEmpty()) {
         throw new IllegalStateException("No way of obtaining recipe " + ☃);
      }
   }

   public static class Result implements IFinishedRecipe {
      private final ResourceLocation field_202117_a;
      private final String field_202118_b;
      private final Ingredient field_202119_c;
      private final Item field_202120_d;
      private final float field_202121_e;
      private final int field_202122_f;
      private final Advancement.Builder field_202123_g;
      private final ResourceLocation field_202124_h;

      public Result(ResourceLocation var1, String var2, Ingredient var3, Item var4, float var5, int var6, Advancement.Builder var7, ResourceLocation var8) {
         this.field_202117_a = ☃;
         this.field_202118_b = ☃;
         this.field_202119_c = ☃;
         this.field_202120_d = ☃;
         this.field_202121_e = ☃;
         this.field_202122_f = ☃;
         this.field_202123_g = ☃;
         this.field_202124_h = ☃;
      }

      @Override
      public JsonObject func_200441_a() {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("type", "smelting");
         if (!this.field_202118_b.isEmpty()) {
            ☃.addProperty("group", this.field_202118_b);
         }

         ☃.add("ingredient", this.field_202119_c.func_200304_c());
         ☃.addProperty("result", IRegistry.field_212630_s.func_177774_c(this.field_202120_d).toString());
         ☃.addProperty("experience", this.field_202121_e);
         ☃.addProperty("cookingtime", this.field_202122_f);
         return ☃;
      }

      @Override
      public ResourceLocation func_200442_b() {
         return this.field_202117_a;
      }

      @Nullable
      @Override
      public JsonObject func_200440_c() {
         return this.field_202123_g.func_200273_b();
      }

      @Nullable
      @Override
      public ResourceLocation func_200443_d() {
         return this.field_202124_h;
      }
   }
}
