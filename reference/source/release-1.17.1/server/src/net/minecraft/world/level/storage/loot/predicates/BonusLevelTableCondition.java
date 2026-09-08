package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class BonusLevelTableCondition implements LootItemCondition {
   final Enchantment enchantment;
   final float[] values;

   BonusLevelTableCondition(Enchantment var1, float[] var2) {
      this.enchantment = â˜ƒ;
      this.values = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.TABLE_BONUS;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.TOOL);
   }

   public boolean test(LootContext var1) {
      ItemStack â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.TOOL);
      int â˜ƒx = â˜ƒ != null ? EnchantmentHelper.getItemEnchantmentLevel(this.enchantment, â˜ƒ) : 0;
      float â˜ƒxx = this.values[Math.min(â˜ƒx, this.values.length - 1)];
      return â˜ƒ.getRandom().nextFloat() < â˜ƒxx;
   }

   public static LootItemCondition.Builder bonusLevelFlatChance(Enchantment var0, float... var1) {
      return () -> new BonusLevelTableCondition(â˜ƒ, â˜ƒ);
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<BonusLevelTableCondition> {
      public void serialize(JsonObject var1, BonusLevelTableCondition var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("enchantment", Registry.ENCHANTMENT.getKey(â˜ƒ.enchantment).toString());
         â˜ƒ.add("chances", â˜ƒ.serialize(â˜ƒ.values));
      }

      public BonusLevelTableCondition deserialize(JsonObject var1, JsonDeserializationContext var2) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "enchantment"));
         Enchantment â˜ƒx = (Enchantment)Registry.ENCHANTMENT.getOptional(â˜ƒ).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + â˜ƒ));
         float[] â˜ƒxx = (float[])GsonHelper.getAsObject(â˜ƒ, "chances", â˜ƒ, float[].class);
         return new BonusLevelTableCondition(â˜ƒx, â˜ƒxx);
      }
   }
}
