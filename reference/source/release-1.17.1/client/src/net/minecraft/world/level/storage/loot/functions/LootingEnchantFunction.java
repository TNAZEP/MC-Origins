package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootingEnchantFunction extends LootItemConditionalFunction {
   public static final int NO_LIMIT = 0;
   final NumberProvider value;
   final int limit;

   LootingEnchantFunction(LootItemCondition[] var1, NumberProvider var2, int var3) {
      super(â˜ƒ);
      this.value = â˜ƒ;
      this.limit = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.LOOTING_ENCHANT;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return Sets.<LootContextParam<?>>union(ImmutableSet.of(LootContextParams.KILLER_ENTITY), this.value.getReferencedContextParams());
   }

   boolean hasLimit() {
      return this.limit > 0;
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Entity â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.KILLER_ENTITY);
      if (â˜ƒ instanceof LivingEntity) {
         int â˜ƒx = EnchantmentHelper.getMobLooting((LivingEntity)â˜ƒ);
         if (â˜ƒx == 0) {
            return â˜ƒ;
         }

         float â˜ƒx = (float)â˜ƒx * this.value.getFloat(â˜ƒ);
         â˜ƒ.grow(Math.round(â˜ƒx));
         if (this.hasLimit() && â˜ƒ.getCount() > this.limit) {
            â˜ƒ.setCount(this.limit);
         }
      }

      return â˜ƒ;
   }

   public static LootingEnchantFunction.Builder lootingMultiplier(NumberProvider var0) {
      return new LootingEnchantFunction.Builder(â˜ƒ);
   }

   public static class Builder extends LootItemConditionalFunction.Builder<LootingEnchantFunction.Builder> {
      private final NumberProvider count;
      private int limit = 0;

      public Builder(NumberProvider var1) {
         this.count = â˜ƒ;
      }

      protected LootingEnchantFunction.Builder getThis() {
         return this;
      }

      public LootingEnchantFunction.Builder setLimit(int var1) {
         this.limit = â˜ƒ;
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new LootingEnchantFunction(this.getConditions(), this.count, this.limit);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<LootingEnchantFunction> {
      public void serialize(JsonObject var1, LootingEnchantFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("count", â˜ƒ.serialize(â˜ƒ.value));
         if (â˜ƒ.hasLimit()) {
            â˜ƒ.add("limit", â˜ƒ.serialize(â˜ƒ.limit));
         }
      }

      public LootingEnchantFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "limit", 0);
         return new LootingEnchantFunction(â˜ƒ, GsonHelper.getAsObject(â˜ƒ, "count", â˜ƒ, NumberProvider.class), â˜ƒ);
      }
   }
}
