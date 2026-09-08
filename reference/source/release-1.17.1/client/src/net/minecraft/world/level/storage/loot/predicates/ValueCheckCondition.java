package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class ValueCheckCondition implements LootItemCondition {
   final NumberProvider provider;
   final IntRange range;

   ValueCheckCondition(NumberProvider var1, IntRange var2) {
      this.provider = â˜ƒ;
      this.range = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.VALUE_CHECK;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Sets.<LootContextParam<?>>union(this.provider.getReferencedContextParams(), this.range.getReferencedContextParams());
   }

   public boolean test(LootContext var1) {
      return this.range.test(â˜ƒ, this.provider.getInt(â˜ƒ));
   }

   public static LootItemCondition.Builder hasValue(NumberProvider var0, IntRange var1) {
      return () -> new ValueCheckCondition(â˜ƒ, â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ValueCheckCondition> {
      public void serialize(JsonObject var1, ValueCheckCondition var2, JsonSerializationContext var3) {
         â˜ƒ.add("value", â˜ƒ.serialize(â˜ƒ.provider));
         â˜ƒ.add("range", â˜ƒ.serialize(â˜ƒ.range));
      }

      public ValueCheckCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         NumberProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "value", â˜ƒ, NumberProvider.class);
         IntRange â˜ƒx = GsonHelper.getAsObject(â˜ƒ, "range", â˜ƒ, IntRange.class);
         return new ValueCheckCondition(â˜ƒ, â˜ƒx);
      }
   }
}
