package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;

public class EffectsChangedTrigger extends SimpleCriterionTrigger<EffectsChangedTrigger.TriggerInstance> {
   static final ResourceLocation ID = new ResourceLocation("effects_changed");

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public EffectsChangedTrigger.TriggerInstance createInstance(JsonObject var1, EntityPredicate.Composite var2, DeserializationContext var3) {
      MobEffectsPredicate â˜ƒ = MobEffectsPredicate.fromJson(â˜ƒ.get("effects"));
      EntityPredicate.Composite â˜ƒx = EntityPredicate.Composite.fromJson(â˜ƒ, "source", â˜ƒ);
      return new EffectsChangedTrigger.TriggerInstance(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   public void trigger(ServerPlayer var1, @Nullable Entity var2) {
      LootContext â˜ƒ = â˜ƒ != null ? EntityPredicate.createContext(â˜ƒ, â˜ƒ) : null;
      this.trigger(â˜ƒ, var2x -> var2x.matches(â˜ƒ, â˜ƒ));
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MobEffectsPredicate effects;
      private final EntityPredicate.Composite source;

      public TriggerInstance(EntityPredicate.Composite var1, MobEffectsPredicate var2, EntityPredicate.Composite var3) {
         super(EffectsChangedTrigger.ID, â˜ƒ);
         this.effects = â˜ƒ;
         this.source = â˜ƒ;
      }

      public static EffectsChangedTrigger.TriggerInstance hasEffects(MobEffectsPredicate var0) {
         return new EffectsChangedTrigger.TriggerInstance(EntityPredicate.Composite.ANY, â˜ƒ, EntityPredicate.Composite.ANY);
      }

      public static EffectsChangedTrigger.TriggerInstance gotEffectsFrom(EntityPredicate var0) {
         return new EffectsChangedTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MobEffectsPredicate.ANY, EntityPredicate.Composite.wrap(â˜ƒ));
      }

      public boolean matches(ServerPlayer var1, @Nullable LootContext var2) {
         if (!this.effects.matches((LivingEntity)â˜ƒ)) {
            return false;
         } else {
            return this.source == EntityPredicate.Composite.ANY || â˜ƒ != null && this.source.matches(â˜ƒ);
         }
      }

      @Override
      public JsonObject serializeToJson(SerializationContext var1) {
         JsonObject â˜ƒ = super.serializeToJson(â˜ƒ);
         â˜ƒ.add("effects", this.effects.serializeToJson());
         â˜ƒ.add("source", this.source.toJson(â˜ƒ));
         return â˜ƒ;
      }
   }
}
