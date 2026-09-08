package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class PlayerHurtEntityTrigger extends SimpleCriterionTrigger<PlayerHurtEntityTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("player_hurt_entity");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public PlayerHurtEntityTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      DamagePredicate â˜ƒ = DamagePredicate.fromJson(â˜ƒ.get("damage"));
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "entity", â˜ƒ);
      return new PlayerHurtEntityTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, Entity var2, DamageSource var3, float var4, float var5, boolean var6) {
      LootContext â˜ƒ = EntityPredicate.createContext(â˜ƒ, â˜ƒ);
      this.trigger(â˜ƒ, var6x -> var6x.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final DamagePredicate damage;
      private final EntityPredicate.Composite entity;

      public TriggerInstance(EntityPredicate.Composite var1, DamagePredicate var2, EntityPredicate.Composite var3) {
         super(PlayerHurtEntityTrigger.ID, â˜ƒ);
         this.damage = â˜ƒ;
         this.entity = â˜ƒ;
      }

      public static PlayerHurtEntityTrigger.TriggerInstance playerHurtEntity() {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, DamagePredicate.ANY, EntityPredicate.Composite.ANY);
      }

      public static PlayerHurtEntityTrigger.TriggerInstance playerHurtEntity(DamagePredicate var0) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, EntityPredicate.Composite.ANY);
      }

      public static PlayerHurtEntityTrigger.TriggerInstance playerHurtEntity(DamagePredicate.Builder var0) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ.build(), EntityPredicate.Composite.ANY);
      }

      public static PlayerHurtEntityTrigger.TriggerInstance playerHurtEntity(EntityPredicate var0) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, DamagePredicate.ANY, EntityPredicate.Composite.wrap(â˜ƒ));
      }

      public static PlayerHurtEntityTrigger.TriggerInstance playerHurtEntity(DamagePredicate var0, EntityPredicate var1) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, EntityPredicate.Composite.wrap(â˜ƒ));
      }

      public static PlayerHurtEntityTrigger.TriggerInstance playerHurtEntity(DamagePredicate.Builder var0, EntityPredicate var1) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ.build(), EntityPredicate.Composite.wrap(â˜ƒ));
      }

      public boolean matches(ServerPlayer var1, LootContext var2, DamageSource var3, float var4, float var5, boolean var6) {
         if (!this.damage.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return false;
         } else {
            return this.entity.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("damage", this.damage.serializeToJson());
         â˜ƒ.add("entity", this.entity.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
