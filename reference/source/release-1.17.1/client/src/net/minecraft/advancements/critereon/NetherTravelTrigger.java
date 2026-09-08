package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class NetherTravelTrigger extends SimpleCriterionTrigger<NetherTravelTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("nether_travel");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public NetherTravelTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      LocationPredicate â˜ƒ = LocationPredicate.fromJson(â˜ƒ.get("entered"));
      LocationPredicate â˜ƒx = LocationPredicate.fromJson(â˜ƒ.get("exited"));
      DistancePredicate â˜ƒxx = DistancePredicate.fromJson(â˜ƒ.get("distance"));
      return new NetherTravelTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public void trigger(ServerPlayer var1, Vec3 var2) {
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ.getLevel(), â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ()));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final LocationPredicate entered;
      private final LocationPredicate exited;
      private final DistancePredicate distance;

      public TriggerInstance(EntityPredicate.Composite var1, LocationPredicate var2, LocationPredicate var3, DistancePredicate var4) {
         super(NetherTravelTrigger.ID, â˜ƒ);
         this.entered = â˜ƒ;
         this.exited = â˜ƒ;
         this.distance = â˜ƒ;
      }

      public static NetherTravelTrigger.TriggerInstance travelledThroughNether(DistancePredicate var0) {
         return new NetherTravelTrigger.TriggerInstance(EntityPredicate.Composite.ANY, LocationPredicate.ANY, LocationPredicate.ANY, â˜ƒ);
      }

      public boolean matches(ServerLevel var1, Vec3 var2, double var3, double var5, double var7) {
         if (!this.entered.matches(â˜ƒ, â˜ƒ.x, â˜ƒ.y, â˜ƒ.z)) {
            return false;
         } else if (!this.exited.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            return this.distance.matches(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("entered", this.entered.serializeToJson());
         â˜ƒ.add("exited", this.exited.serializeToJson());
         â˜ƒ.add("distance", this.distance.serializeToJson());
         return â˜ƒ;
      }
   }
}
