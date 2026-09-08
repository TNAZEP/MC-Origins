package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class KilledTrigger extends SimpleCriterionTrigger<KilledTrigger.TriggerInstance> {
   final ResourceLocation id;

   public KilledTrigger(ResourceLocation var1) {
      this.id = â˜ƒ;
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   public KilledTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      return new KilledTrigger.TriggerInstance(
         this.id, â˜ƒ, EntityPredicate.Composite.fromJson(â˜ƒ, "entity", â˜ƒ), DamageSourcePredicate.fromJson(â˜ƒ.get("killing_blow"))
      );
   }

   public void trigger(ServerPlayer var1, Entity var2, DamageSource var3) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var3x -> var3x.matches(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite entityPredicate;
      private final DamageSourcePredicate killingBlow;

      public TriggerInstance(ResourceLocation var1, EntityPredicate.Composite var2, EntityPredicate.Composite var3, DamageSourcePredicate var4) {
         super(â˜ƒ, â˜ƒ);
         this.entityPredicate = â˜ƒ;
         this.killingBlow = â˜ƒ;
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity(EntityPredicate var0) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), DamageSourcePredicate.ANY
         );
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity(EntityPredicate.Builder var0) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()), DamageSourcePredicate.ANY
         );
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity() {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, DamageSourcePredicate.ANY
         );
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity(EntityPredicate var0, DamageSourcePredicate var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), â˜ƒ
         );
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity(EntityPredicate.Builder var0, DamageSourcePredicate var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()), â˜ƒ
         );
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity(EntityPredicate var0, DamageSourcePredicate.Builder var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), â˜ƒ.build()
         );
      }

      public static KilledTrigger.TriggerInstance playerKilledEntity(EntityPredicate.Builder var0, DamageSourcePredicate.Builder var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.PLAYER_KILLED_ENTITY.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()), â˜ƒ.build()
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer(EntityPredicate var0) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), DamageSourcePredicate.ANY
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer(EntityPredicate.Builder var0) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()), DamageSourcePredicate.ANY
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer() {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY, DamageSourcePredicate.ANY
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer(EntityPredicate var0, DamageSourcePredicate var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), â˜ƒ
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer(EntityPredicate.Builder var0, DamageSourcePredicate var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()), â˜ƒ
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer(EntityPredicate var0, DamageSourcePredicate.Builder var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ), â˜ƒ.build()
         );
      }

      public static KilledTrigger.TriggerInstance entityKilledPlayer(EntityPredicate.Builder var0, DamageSourcePredicate.Builder var1) {
         return new KilledTrigger.TriggerInstance(
            CriteriaTriggers.ENTITY_KILLED_PLAYER.id, EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(â˜ƒ.build()), â˜ƒ.build()
         );
      }

      public boolean matches(ServerPlayer var1, LootContext var2, DamageSource var3) {
         return !this.killingBlow.matches(â˜ƒ, â˜ƒ) ? false : this.entityPredicate.matches(â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("entity", this.entityPredicate.toJson(â˜ƒ));
         â˜ƒ.add("killing_blow", this.killingBlow.serializeToJson());
         return â˜ƒ;
      }
   }
}
