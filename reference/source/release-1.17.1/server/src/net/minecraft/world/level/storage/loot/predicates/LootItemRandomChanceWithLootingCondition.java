package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class LootItemRandomChanceWithLootingCondition implements LootItemCondition {
   final float percent;
   final float lootingMultiplier;

   LootItemRandomChanceWithLootingCondition(float var1, float var2) {
      this.percent = â˜ƒ;
      this.lootingMultiplier = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.RANDOM_CHANCE_WITH_LOOTING;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.KILLER_ENTITY);
   }

   public boolean test(LootContext var1) {
      Entity â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.KILLER_ENTITY);
      int â˜ƒx = 0;
      if (â˜ƒ instanceof LivingEntity) {
         â˜ƒx = EnchantmentHelper.getMobLooting((LivingEntity)â˜ƒ);
      }

      return â˜ƒ.getRandom().nextFloat() < this.percent + (float)â˜ƒx * this.lootingMultiplier;
   }

   public static LootItemCondition.Builder randomChanceAndLootingBoost(float var0, float var1) {
      return () -> new LootItemRandomChanceWithLootingCondition(â˜ƒ, â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemRandomChanceWithLootingCondition> {
      public void serialize(JsonObject var1, LootItemRandomChanceWithLootingCondition var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("chance", â˜ƒ.percent);
         â˜ƒ.addProperty("looting_multiplier", â˜ƒ.lootingMultiplier);
      }

      public LootItemRandomChanceWithLootingCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         return new LootItemRandomChanceWithLootingCondition(GsonHelper.getAsFloat(â˜ƒ, "chance"), GsonHelper.getAsFloat(â˜ƒ, "looting_multiplier"));
      }
   }
}
