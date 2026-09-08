package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

public class EntityHurtPlayerTrigger extends SimpleCriterionTrigger<EntityHurtPlayerTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("entity_hurt_player");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public EntityHurtPlayerTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      DamagePredicate â˜ƒ = DamagePredicate.fromJson(â˜ƒ.get("damage"));
      return new EntityHurtPlayerTrigger.TriggerInstance(â˜ƒ, â˜ƒ);
   }

   public void trigger(ServerPlayer var1, DamageSource var2, float var3, float var4, boolean var5) {
      this.trigger(â˜ƒ, var5x -> var5x.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final DamagePredicate damage;

      public TriggerInstance(EntityPredicate.Composite var1, DamagePredicate var2) {
         super(EntityHurtPlayerTrigger.ID, â˜ƒ);
         this.damage = â˜ƒ;
      }

      public static EntityHurtPlayerTrigger.TriggerInstance entityHurtPlayer() {
         return new EntityHurtPlayerTrigger.TriggerInstance(EntityPredicate.Composite.ANY, DamagePredicate.ANY);
      }

      public static EntityHurtPlayerTrigger.TriggerInstance entityHurtPlayer(DamagePredicate var0) {
         return new EntityHurtPlayerTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ);
      }

      public static EntityHurtPlayerTrigger.TriggerInstance entityHurtPlayer(DamagePredicate.Builder var0) {
         return new EntityHurtPlayerTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ.build());
      }

      public boolean matches(ServerPlayer var1, DamageSource var2, float var3, float var4, boolean var5) {
         return this.damage.matches(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("damage", this.damage.serializeToJson());
         return â˜ƒ;
      }
   }
}
