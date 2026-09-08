package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class TickTrigger extends SimpleCriterionTrigger<TickTrigger.TriggerInstance> {
   public static final ResourceLocation ID = new ResourceLocation("tick");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public TickTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      return new TickTrigger.TriggerInstance(â˜ƒ);
   }

   public void trigger(ServerPlayer var1) {
      this.trigger(â˜ƒ, var0 -> true);
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      public TriggerInstance(EntityPredicate.Composite var1) {
         super(TickTrigger.ID, â˜ƒ);
      }
   }
}
