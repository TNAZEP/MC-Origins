package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class EnchantWithLevelsFunction extends LootItemConditionalFunction {
   final NumberProvider levels;
   final boolean treasure;

   EnchantWithLevelsFunction(LootItemCondition[] var1, NumberProvider var2, boolean var3) {
      super(â˜ƒ);
      this.levels = â˜ƒ;
      this.treasure = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.ENCHANT_WITH_LEVELS;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.levels.getReferencedContextParams();
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      Random â˜ƒ = â˜ƒ.getRandom();
      return EnchantmentHelper.enchantItem(â˜ƒ, â˜ƒ, this.levels.getInt(â˜ƒ), this.treasure);
   }

   public static EnchantWithLevelsFunction.Builder enchantWithLevels(NumberProvider var0) {
      return new EnchantWithLevelsFunction.Builder(â˜ƒ);
   }

   public static class Builder extends LootItemConditionalFunction.Builder<EnchantWithLevelsFunction.Builder> {
      private final NumberProvider levels;
      private boolean treasure;

      public Builder(NumberProvider var1) {
         this.levels = â˜ƒ;
      }

      protected EnchantWithLevelsFunction.Builder getThis() {
         return this;
      }

      public EnchantWithLevelsFunction.Builder allowTreasure() {
         this.treasure = true;
         return this;
      }

      @Override
      public LootItemFunction build() {
         return new EnchantWithLevelsFunction(this.getConditions(), this.levels, this.treasure);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<EnchantWithLevelsFunction> {
      public void serialize(JsonObject var1, EnchantWithLevelsFunction var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.add("levels", â˜ƒ.serialize(â˜ƒ.levels));
         â˜ƒ.addProperty("treasure", â˜ƒ.treasure);
      }

      public EnchantWithLevelsFunction deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         NumberProvider â˜ƒ = GsonHelper.getAsObject(â˜ƒ, "levels", â˜ƒ, NumberProvider.class);
         boolean â˜ƒx = GsonHelper.getAsBoolean(â˜ƒ, "treasure", false);
         return new EnchantWithLevelsFunction(â˜ƒ, â˜ƒ, â˜ƒx);
      }
   }
}
