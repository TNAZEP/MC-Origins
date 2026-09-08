package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class LevitationTrigger extends SimpleCriterionTrigger<LevitationTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("levitation");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public LevitationTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      DistancePredicate â˜ƒ = DistancePredicate.fromJson(â˜ƒ.get("distance"));
      MinMaxBounds.Ints â˜ƒx = MinMaxBounds.Ints.fromJson(â˜ƒ.get("duration"));
      return new LevitationTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, Vec3 var2, int var3) {
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final DistancePredicate distance;
      private final MinMaxBounds.Ints duration;

      public TriggerInstance(EntityPredicate.Composite var1, DistancePredicate var2, MinMaxBounds.Ints var3) {
         super(LevitationTrigger.ID, â˜ƒ);
         this.distance = â˜ƒ;
         this.duration = â˜ƒ;
      }

      public static LevitationTrigger.TriggerInstance levitated(DistancePredicate var0) {
         return new LevitationTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, MinMaxBounds.Ints.ANY);
      }

      public boolean matches(ServerPlayer var1, Vec3 var2, int var3) {
         if (!this.distance.matches(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ())) {
            return false;
         } else {
            return this.duration.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("distance", this.distance.serializeToJson());
         â˜ƒ.add("duration", this.duration.serializeToJson());
         return â˜ƒ;
      }
   }
}
