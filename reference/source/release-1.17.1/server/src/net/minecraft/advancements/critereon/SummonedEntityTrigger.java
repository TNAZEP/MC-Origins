package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class SummonedEntityTrigger extends SimpleCriterionTrigger<SummonedEntityTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("summoned_entity");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public SummonedEntityTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      EntityPredicate.Composite â˜ƒ = EntityPredicate.Composite.fromJson(â˜ƒ, "entity", â˜ƒ);
      return new SummonedEntityTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, Entity var2) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var1x -> var1x.matches(â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite entity;

      public TriggerInstance(EntityPredicate.Composite var1, EntityPredicate.Composite var2) {
         super(SummonedEntityTrigger.ID, â˜ƒ);
         this.entity = â˜ƒ;
      }

      public static SummonedEntityTrigger.TriggerInstance summonedEntity(EntityPredicate.Builder var0) {
         return new SummonedEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()));
      }

      public boolean matches(LootContext var1) {
         return this.entity.matches(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("entity", this.entity.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
