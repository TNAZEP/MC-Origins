package net.minecraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class RecipeUnlockedTrigger implements ICriterionTrigger<RecipeUnlockedTrigger.Instance> {
   private static final ResourceLocation field_192227_a = new ResourceLocation("recipe_unlocked");
   private final Map<PlayerAdvancements, RecipeUnlockedTrigger.Listeners> field_192228_b = Maps.<PlayerAdvancements, RecipeUnlockedTrigger.Listeners>newHashMap(
      
   );

   @Override
   public ResourceLocation func_192163_a() {
      return field_192227_a;
   }

   @Override
   public void func_192165_a(PlayerAdvancements var1, ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> var2) {
      RecipeUnlockedTrigger.Listeners ☃ = (RecipeUnlockedTrigger.Listeners)this.field_192228_b.get(☃);
      if (☃ == null) {
         ☃ = new RecipeUnlockedTrigger.Listeners(☃);
         this.field_192228_b.put(☃, ☃);
      }

      ☃.func_192528_a(☃);
   }

   @Override
   public void func_192164_b(PlayerAdvancements var1, ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> var2) {
      RecipeUnlockedTrigger.Listeners ☃ = (RecipeUnlockedTrigger.Listeners)this.field_192228_b.get(☃);
      if (☃ != null) {
         ☃.func_192525_b(☃);
         if (☃.func_192527_a()) {
            this.field_192228_b.remove(☃);
         }
      }
   }

   @Override
   public void func_192167_a(PlayerAdvancements var1) {
      this.field_192228_b.remove(☃);
   }

   public RecipeUnlockedTrigger.Instance func_192166_a(JsonObject var1, JsonDeserializationContext var2) {
      ResourceLocation ☃ = new ResourceLocation(JsonUtils.func_151200_h(☃, "recipe"));
      return new RecipeUnlockedTrigger.Instance(☃);
   }

   public void func_192225_a(EntityPlayerMP var1, IRecipe var2) {
      RecipeUnlockedTrigger.Listeners ☃ = (RecipeUnlockedTrigger.Listeners)this.field_192228_b.get(☃.func_192039_O());
      if (☃ != null) {
         ☃.func_193493_a(☃);
      }
   }

   public static class Instance extends AbstractCriterionInstance {
      private final ResourceLocation field_212243_a;

      public Instance(ResourceLocation var1) {
         super(RecipeUnlockedTrigger.field_192227_a);
         this.field_212243_a = ☃;
      }

      @Override
      public JsonElement func_200288_b() {
         JsonObject ☃ = new JsonObject();
         ☃.addProperty("recipe", this.field_212243_a.toString());
         return ☃;
      }

      public boolean func_193215_a(IRecipe var1) {
         return this.field_212243_a.equals(☃.func_199560_c());
      }
   }

   static class Listeners {
      private final PlayerAdvancements field_192529_a;
      private final Set<ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance>> field_192530_b = Sets.<ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance>>newHashSet(
         
      );

      public Listeners(PlayerAdvancements var1) {
         this.field_192529_a = ☃;
      }

      public boolean func_192527_a() {
         return this.field_192530_b.isEmpty();
      }

      public void func_192528_a(ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> var1) {
         this.field_192530_b.add(☃);
      }

      public void func_192525_b(ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> var1) {
         this.field_192530_b.remove(☃);
      }

      public void func_193493_a(IRecipe var1) {
         List<ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance>> ☃ = null;

         for(ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> ☃x : this.field_192530_b) {
            if (☃x.func_192158_a().func_193215_a(☃)) {
               if (☃ == null) {
                  ☃ = Lists.<ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance>>newArrayList();
               }

               ☃.add(☃x);
            }
         }

         if (☃ != null) {
            for(ICriterionTrigger.Listener<RecipeUnlockedTrigger.Instance> ☃x : ☃) {
               ☃x.func_192159_a(this.field_192529_a);
            }
         }
      }
   }
}
