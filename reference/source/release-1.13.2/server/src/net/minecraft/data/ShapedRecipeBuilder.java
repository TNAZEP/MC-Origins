package net.minecraft.data;

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

public class ShapedRecipeBuilder {
   private static final Logger field_200474_a = LogManager.getLogger();
   private final Item field_200475_b;
   private final int field_200476_c;
   private final List<String> field_200477_d = Lists.newArrayList();
   private final Map<Character, Ingredient> field_200478_e = Maps.newLinkedHashMap();
   private final Advancement.Builder field_200479_f = Advancement.Builder.func_200278_a();
   private String field_200480_g;

   public ShapedRecipeBuilder(IItemProvider var1, int var2) {
      this.field_200475_b = ☃.func_199767_j();
      this.field_200476_c = ☃;
   }

   public static ShapedRecipeBuilder func_200470_a(IItemProvider var0) {
      return func_200468_a(☃, 1);
   }

   public static ShapedRecipeBuilder func_200468_a(IItemProvider var0, int var1) {
      return new ShapedRecipeBuilder(☃, ☃);
   }

   public ShapedRecipeBuilder func_200469_a(Character var1, Tag<Item> var2) {
      return this.func_200471_a(☃, Ingredient.func_199805_a(☃));
   }

   public ShapedRecipeBuilder func_200462_a(Character var1, IItemProvider var2) {
      return this.func_200471_a(☃, Ingredient.func_199804_a(☃));
   }

   public ShapedRecipeBuilder func_200471_a(Character var1, Ingredient var2) {
      if (this.field_200478_e.containsKey(☃)) {
         throw new IllegalArgumentException("Symbol '" + ☃ + "' is already defined!");
      } else if (☃ == ' ') {
         throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
      } else {
         this.field_200478_e.put(☃, ☃);
         return this;
      }
   }

   public ShapedRecipeBuilder func_200472_a(String var1) {
      if (!this.field_200477_d.isEmpty() && ☃.length() != ((String)this.field_200477_d.get(0)).length()) {
         throw new IllegalArgumentException("Pattern must be the same width on every line!");
      } else {
         this.field_200477_d.add(☃);
         return this;
      }
   }

   public ShapedRecipeBuilder func_200465_a(String var1, ICriterionInstance var2) {
      this.field_200479_f.func_200275_a(☃, ☃);
      return this;
   }

   public ShapedRecipeBuilder func_200473_b(String var1) {
      this.field_200480_g = ☃;
      return this;
   }

   public void func_200464_a(Consumer<IFinishedRecipe> var1) {
      this.func_200467_a(☃, IRegistry.field_212630_s.func_177774_c(this.field_200475_b));
   }

   public void func_200466_a(Consumer<IFinishedRecipe> var1, String var2) {
      ResourceLocation ☃ = IRegistry.field_212630_s.func_177774_c(this.field_200475_b);
      if (new ResourceLocation(☃).equals(☃)) {
         throw new IllegalStateException("Shaped Recipe " + ☃ + " should remove its 'save' argument");
      } else {
         this.func_200467_a(☃, new ResourceLocation(☃));
      }
   }

   public void func_200467_a(Consumer<IFinishedRecipe> var1, ResourceLocation var2) {
      this.func_200463_a(☃);
      this.field_200479_f
         .func_200272_a(new ResourceLocation("minecraft:recipes/root"))
         .func_200275_a("has_the_recipe", new RecipeUnlockedTrigger.Instance(☃))
         .func_200271_a(AdvancementRewards.Builder.func_200280_c(☃))
         .func_200270_a(RequirementsStrategy.OR);
      ☃.accept(
         new ShapedRecipeBuilder.Result(
            ☃,
            this.field_200475_b,
            this.field_200476_c,
            this.field_200480_g == null ? "" : this.field_200480_g,
            this.field_200477_d,
            this.field_200478_e,
            this.field_200479_f,
            new ResourceLocation(☃.func_110624_b(), "recipes/" + this.field_200475_b.func_77640_w().func_200300_c() + "/" + ☃.func_110623_a())
         )
      );
   }

   private void func_200463_a(ResourceLocation var1) {
      if (this.field_200477_d.isEmpty()) {
         throw new IllegalStateException("No pattern is defined for shaped recipe " + ☃ + "!");
      } else {
         Set<Character> ☃ = Sets.newHashSet(this.field_200478_e.keySet());
         ☃.remove(' ');

         for(String ☃x : this.field_200477_d) {
            for(int ☃xx = 0; ☃xx < ☃x.length(); ++☃xx) {
               char ☃xxx = ☃x.charAt(☃xx);
               if (!this.field_200478_e.containsKey(☃xxx) && ☃xxx != ' ') {
                  throw new IllegalStateException("Pattern in recipe " + ☃ + " uses undefined symbol '" + ☃xxx + "'");
               }

               ☃.remove(☃xxx);
            }
         }

         if (!☃.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + ☃);
         } else if (this.field_200477_d.size() == 1 && ((String)this.field_200477_d.get(0)).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + ☃ + " only takes in a single item - should it be a shapeless recipe instead?");
         } else if (this.field_200479_f.func_200277_c().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + ☃);
         }
      }
   }

   class Result implements IFinishedRecipe {
      private final ResourceLocation field_200445_b;
      private final Item field_200446_c;
      private final int field_200447_d;
      private final String field_200448_e;
      private final List<String> field_200449_f;
      private final Map<Character, Ingredient> field_200450_g;
      private final Advancement.Builder field_200451_h;
      private final ResourceLocation field_200452_i;

      public Result(
         ResourceLocation var2,
         Item var3,
         int var4,
         String var5,
         List<String> var6,
         Map<Character, Ingredient> var7,
         Advancement.Builder var8,
         ResourceLocation var9
      ) {
         this.field_200445_b = ☃;
         this.field_200446_c = ☃;
         this.field_200447_d = ☃;
         this.field_200448_e = ☃;
         this.field_200449_f = ☃;
         this.field_200450_g = ☃;
         this.field_200451_h = ☃;
         this.field_200452_i = ☃;
      }

      @Override
      public JsonObject func_200441_a() {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("type", "crafting_shaped");
         if (!this.field_200448_e.isEmpty()) {
            ☃.addProperty("group", this.field_200448_e);
         }

         JsonArray ☃ = new JsonArray();

         for(String ☃x : this.field_200449_f) {
            ☃.add(☃x);
         }

         ☃.add("pattern", ☃);
         JsonObject ☃x = new JsonObject();

         for(Entry<Character, Ingredient> ☃xx : this.field_200450_g.entrySet()) {
            ☃x.add(String.valueOf(☃xx.getKey()), ((Ingredient)☃xx.getValue()).func_200304_c());
         }

         ☃.add("key", ☃x);
         JsonObject ☃xx = new JsonObject();
         ☃xx.addProperty("item", IRegistry.field_212630_s.func_177774_c(this.field_200446_c).toString());
         if (this.field_200447_d > 1) {
            ☃xx.addProperty("count", this.field_200447_d);
         }

         ☃.add("result", ☃xx);
         return ☃;
      }

      @Override
      public ResourceLocation func_200442_b() {
         return this.field_200445_b;
      }

      @Nullable
      @Override
      public JsonObject func_200440_c() {
         return this.field_200451_h.func_200273_b();
      }

      @Nullable
      @Override
      public ResourceLocation func_200443_d() {
         return this.field_200452_i;
      }
   }
}
