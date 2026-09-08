package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.storage.loot.LootContext;

public class LightningStrikeTrigger extends SimpleCriterionTrigger<LightningStrikeTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("lightning_strike");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public LightningStrikeTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      EntityPredicate.Composite â˜ƒ = EntityPredicate.Composite.fromJson(â˜ƒ, "lightning", â˜ƒ);
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "bystander", â˜ƒ);
      return new LightningStrikeTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, LightningBolt var2, List<Entity> var3) {
      List<LootContext> â˜ƒ = (List)â˜ƒ.stream().map(var1x -> EntityPredicate.createContext(â˜ƒ, var1x)).collect(Collectors.toList());
      LootContext â˜ƒx = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite lightning;
      private final EntityPredicate.Composite bystander;

      public TriggerInstance(EntityPredicate.Composite var1, EntityPredicate.Composite var2, EntityPredicate.Composite var3) {
         super(LightningStrikeTrigger.ID, â˜ƒ);
         this.lightning = â˜ƒ;
         this.bystander = â˜ƒ;
      }

      public static LightningStrikeTrigger.TriggerInstance lighthingStrike(EntityPredicate var0, EntityPredicate var1) {
         return new LightningStrikeTrigger.TriggerInstance(
            EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), EntityPredicate.Composite.wrap(â˜ƒ)
         );
      }

      public boolean matches(LootContext var1, List<LootContext> var2) {
         if (!this.lightning.matches(â˜ƒ)) {
            return false;
         } else {
            return this.bystander == EntityPredicate.Composite.ANY || !â˜ƒ.stream().noneMatch(this.bystander::matches);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("lightning", this.lightning.toJson(â˜ƒ));
         â˜ƒ.add("bystander", this.bystander.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
