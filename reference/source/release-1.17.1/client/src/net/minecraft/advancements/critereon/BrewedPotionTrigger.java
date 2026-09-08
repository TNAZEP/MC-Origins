package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.alchemy.Potion;

public class BrewedPotionTrigger extends SimpleCriterionTrigger<BrewedPotionTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("brewed_potion");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public BrewedPotionTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      Potion â˜ƒ = null;
      if (â˜ƒ.has("potion")) {
         ResourceLocation â˜ƒx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "potion"));
         â˜ƒ = (Potion)Registry.POTION.getOptional(â˜ƒx).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + â˜ƒ + "'"));
      }

      return new BrewedPotionTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, Potion var2) {
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Potion potion;

      public TriggerInstance(EntityPredicate.Composite var1, @Nullable Potion var2) {
         super(BrewedPotionTrigger.ID, â˜ƒ);
         this.potion = â˜ƒ;
      }

      public static BrewedPotionTrigger.TriggerInstance brewedPotion() {
         return new BrewedPotionTrigger.TriggerInstance(EntityPredicate.Composite.ANY, null);
      }

      public boolean matches(Potion var1) {
         return this.potion == null || this.potion == â˜ƒ;
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         if (this.potion != null) {
            â˜ƒ.addProperty("potion", Registry.POTION.getKey(this.potion).toString());
         }

         return â˜ƒ;
      }
   }
}
