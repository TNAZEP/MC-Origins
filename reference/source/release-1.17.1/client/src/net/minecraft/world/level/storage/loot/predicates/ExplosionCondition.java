package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ExplosionCondition implements LootItemCondition {
   static final ExplosionCondition INSTANCE = new ExplosionCondition();

   private ExplosionCondition() {
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.SURVIVES_EXPLOSION;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.EXPLOSION_RADIUS);
   }

   public boolean test(LootContext var1) {
      Float â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.EXPLOSION_RADIUS);
      if (â˜ƒ != null) {
         Random â˜ƒx = â˜ƒ.getRandom();
         float â˜ƒxx = 1.0F / â˜ƒ;
         return â˜ƒx.nextFloat() <= â˜ƒxx;
      } else {
         return true;
      }
   }

   public static LootItemCondition.Builder survivesExplosion() {
      return () -> INSTANCE;
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ExplosionCondition> {
      public void serialize(JsonObject var1, ExplosionCondition var2, JsonSerializationContext var3) {
      }

      public ExplosionCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         return ExplosionCondition.INSTANCE;
      }
   }
}
