package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public class TargetBlockTrigger extends SimpleCriterionTrigger<TargetBlockTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("target_hit");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public TargetBlockTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      MinMaxBounds.Ints â˜ƒ = MinMaxBounds.Ints.fromJson(â˜ƒ.get("signal_strength"));
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "projectile", â˜ƒ);
      return new TargetBlockTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, Entity var2, Vec3 var3, int var4) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints signalStrength;
      private final EntityPredicate.Composite projectile;

      public TriggerInstance(EntityPredicate.Composite var1, MinMaxBounds.Ints var2, EntityPredicate.Composite var3) {
         super(TargetBlockTrigger.ID, â˜ƒ);
         this.signalStrength = â˜ƒ;
         this.projectile = â˜ƒ;
      }

      public static TargetBlockTrigger.TriggerInstance targetHit(MinMaxBounds.Ints var0, EntityPredicate.Composite var1) {
         return new TargetBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("signal_strength", this.signalStrength.serializeToJson());
         â˜ƒ.add("projectile", this.projectile.toJson(â˜ƒ));
         return â˜ƒ;
      }

      public boolean matches(LootContext var1, Vec3 var2, int var3) {
         if (!this.signalStrength.matches(â˜ƒ)) {
            return false;
         } else {
            return this.projectile.matches(â˜ƒ);
         }
      }
   }
}
