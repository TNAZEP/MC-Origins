package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class DamageSourceCondition implements LootItemCondition {
   final DamageSourcePredicate predicate;

   DamageSourceCondition(DamageSourcePredicate var1) {
      this.predicate = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.DAMAGE_SOURCE_PROPERTIES;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.ORIGIN, LootContextParams.DAMAGE_SOURCE);
   }

   public boolean test(LootContext var1) {
      DamageSource â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
      Vec3 â˜ƒx = â˜ƒ.getParamOrNull(LootContextParams.ORIGIN);
      return â˜ƒx != null && â˜ƒ != null && this.predicate.matches(â˜ƒ.getLevel(), â˜ƒx, â˜ƒ);
   }

   public static LootItemCondition.Builder hasDamageSource(DamageSourcePredicate.Builder var0) {
      return () -> new DamageSourceCondition(â˜ƒ.build());
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<DamageSourceCondition> {
      public void serialize(JsonObject var1, DamageSourceCondition var2, JsonSerializationContext var3) {
         â˜ƒ.add("predicate", â˜ƒ.predicate.serializeToJson());
      }

      public DamageSourceCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         DamageSourcePredicate â˜ƒ = DamageSourcePredicate.fromJson(â˜ƒ.get("predicate"));
         return new DamageSourceCondition(â˜ƒ);
      }
   }
}
