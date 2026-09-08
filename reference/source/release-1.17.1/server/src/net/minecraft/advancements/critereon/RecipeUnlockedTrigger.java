package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeUnlockedTrigger extends SimpleCriterionTrigger<RecipeUnlockedTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("recipe_unlocked");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public RecipeUnlockedTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "recipe"));
      return new RecipeUnlockedTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, Recipe<?> var2) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static RecipeUnlockedTrigger.TriggerInstance unlocked(ResourceLocation var0) {
      return new RecipeUnlockedTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ);
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation recipe;

      public TriggerInstance(EntityPredicate.Composite var1, ResourceLocation var2) {
         super(RecipeUnlockedTrigger.ID, â˜ƒ);
         this.recipe = â˜ƒ;
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.addProperty("recipe", this.recipe.toString());
         return â˜ƒ;
      }

      public boolean matches(Recipe<?> var1) {
         return this.recipe.equals(â˜ƒ.getId());
      }
   }
}
