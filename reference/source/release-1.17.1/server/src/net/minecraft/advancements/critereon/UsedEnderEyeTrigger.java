package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UsedEnderEyeTrigger extends SimpleCriterionTrigger<UsedEnderEyeTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("used_ender_eye");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public UsedEnderEyeTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      MinMaxBounds.Doubles â˜ƒ = MinMaxBounds.Doubles.fromJson(â˜ƒ.get("distance"));
      return new UsedEnderEyeTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, BlockPos var2) {
      double â˜ƒ = â˜ƒ.getX() - (double)â˜ƒ.getX();
      double â˜ƒx = â˜ƒ.getZ() - (double)â˜ƒ.getZ();
      double â˜ƒxx = â˜ƒ * â˜ƒ + â˜ƒx * â˜ƒx;
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Doubles level;

      public TriggerInstance(EntityPredicate.Composite var1, MinMaxBounds.Doubles var2) {
         super(UsedEnderEyeTrigger.ID, â˜ƒ);
         this.level = â˜ƒ;
      }

      public boolean matches(double var1) {
         return this.level.matchesSqr(â˜ƒ);
      }
   }
}
