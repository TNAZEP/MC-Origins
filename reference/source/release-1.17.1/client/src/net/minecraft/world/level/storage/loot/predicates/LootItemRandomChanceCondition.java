package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;

public class LootItemRandomChanceCondition implements LootItemCondition {
   final float probability;

   LootItemRandomChanceCondition(float var1) {
      this.probability = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.RANDOM_CHANCE;
   }

   public boolean test(LootContext var1) {
      return â˜ƒ.getRandom().nextFloat() < this.probability;
   }

   public static LootItemCondition.Builder randomChance(float var0) {
      return () -> new LootItemRandomChanceCondition(â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemRandomChanceCondition> {
      public void serialize(JsonObject var1, LootItemRandomChanceCondition var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("chance", â˜ƒ.probability);
      }

      public LootItemRandomChanceCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         return new LootItemRandomChanceCondition(GsonHelper.getAsFloat(â˜ƒ, "chance"));
      }
   }
}
