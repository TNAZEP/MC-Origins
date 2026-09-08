package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LootItemEntityPropertyCondition implements LootItemCondition {
   final EntityPredicate predicate;
   final LootContext.EntityTarget entityTarget;

   LootItemEntityPropertyCondition(EntityPredicate var1, LootContext.EntityTarget var2) {
      this.predicate = â˜ƒ;
      this.entityTarget = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.ENTITY_PROPERTIES;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.ORIGIN, this.entityTarget.getParam());
   }

   public boolean test(LootContext var1) {
      Entity â˜ƒ = â˜ƒ.getParamOrNull(this.entityTarget.getParam());
      Vec3 â˜ƒx = â˜ƒ.getParamOrNull(LootContextParams.ORIGIN);
      return this.predicate.matches(â˜ƒ.getLevel(), â˜ƒx, â˜ƒ);
   }

   public static LootItemCondition.Builder entityPresent(LootContext.EntityTarget var0) {
      return hasProperties(â˜ƒ, EntityPredicate.Builder.entity());
   }

   public static LootItemCondition.Builder hasProperties(LootContext.EntityTarget var0, EntityPredicate.Builder var1) {
      return () -> new LootItemEntityPropertyCondition(â˜ƒ.build(), â˜ƒ);
   }

   public static LootItemCondition.Builder hasProperties(LootContext.EntityTarget var0, EntityPredicate var1) {
      return () -> new LootItemEntityPropertyCondition(â˜ƒ, â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemEntityPropertyCondition> {
      public void serialize(JsonObject var1, LootItemEntityPropertyCondition var2, JsonSerializationContext var3) {
         â˜ƒ.add("predicate", â˜ƒ.predicate.serializeToJson());
         â˜ƒ.add("entity", â˜ƒ.serialize(â˜ƒ.entityTarget));
      }

      public LootItemEntityPropertyCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         EntityPredicate â˜ƒ = EntityPredicate.fromJson(â˜ƒ.get("predicate"));
         return new LootItemEntityPropertyCondition(â˜ƒ, GsonHelper.getAsObject(â˜ƒ, "entity", â˜ƒ, LootContext.EntityTarget.class));
      }
   }
}
