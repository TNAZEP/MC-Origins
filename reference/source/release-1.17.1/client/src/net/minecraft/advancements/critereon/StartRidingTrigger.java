package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class StartRidingTrigger extends SimpleCriterionTrigger<StartRidingTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("started_riding");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public StartRidingTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      return new StartRidingTrigger.TriggerInstance(â˜ƒ);
   }

   public void trigger(ServerPlayer var1) {
      this.trigger(â˜ƒ, var0 -> true);
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      public TriggerInstance(EntityPredicate.Composite var1) {
         super(StartRidingTrigger.ID, â˜ƒ);
      }

      public static StartRidingTrigger.TriggerInstance playerStartsRiding(EntityPredicate.Builder var0) {
         return new StartRidingTrigger.TriggerInstance(EntityPredicate.Composite.wrap(â˜ƒ.build()));
      }
   }
}
