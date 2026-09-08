package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class ApplyBonusCount extends LootItemConditionalFunction {
   static final Map<ResourceLocation, ApplyBonusCount.FormulaDeserializer> FORMULAS = Maps.<ResourceLocation, ApplyBonusCount.FormulaDeserializer>newHashMap();
   final Enchantment enchantment;
   final ApplyBonusCount.Formula formula;

   ApplyBonusCount(LootItemCondition[] var1, Enchantment var2, ApplyBonusCount.Formula var3) {
      super(â˜ƒ);
      this.enchantment = â˜ƒ;
      this.formula = â˜ƒ;
   }

   @Override
   public LootItemFunctionType getType() {
      return LootItemFunctions.APPLY_BONUS;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return ImmutableSet.of(LootContextParams.TOOL);
   }

   @Override
   public ItemStack run(ItemStack var1, LootContext var2) {
      ItemStack â˜ƒ = â˜ƒ.getParamOrNull(LootContextParams.TOOL);
      if (â˜ƒ != null) {
         int â˜ƒx = EnchantmentHelper.getItemEnchantmentLevel(this.enchantment, â˜ƒ);
         int â˜ƒxx = this.formula.calculateNewCount(â˜ƒ.getRandom(), â˜ƒ.getCount(), â˜ƒx);
         â˜ƒ.setCount(â˜ƒxx);
      }

      return â˜ƒ;
   }

   public static LootItemConditionalFunction.Builder<?> addBonusBinomialDistributionCount(Enchantment var0, float var1, int var2) {
      return simpleBuilder(var3 -> new ApplyBonusCount(var3, â˜ƒ, new ApplyBonusCount.BinomialWithBonusCount(â˜ƒ, â˜ƒ)));
   }

   public static LootItemConditionalFunction.Builder<?> addOreBonusCount(Enchantment var0) {
      return simpleBuilder(var1 -> new ApplyBonusCount(var1, â˜ƒ, new ApplyBonusCount.OreDrops()));
   }

   public static LootItemConditionalFunction.Builder<?> addUniformBonusCount(Enchantment var0) {
      return simpleBuilder(var1 -> new ApplyBonusCount(var1, â˜ƒ, new ApplyBonusCount.UniformBonusCount(1)));
   }

   public static LootItemConditionalFunction.Builder<?> addUniformBonusCount(Enchantment var0, int var1) {
      return simpleBuilder(var2 -> new ApplyBonusCount(var2, â˜ƒ, new ApplyBonusCount.UniformBonusCount(â˜ƒ)));
   }

   static {
      FORMULAS.put(ApplyBonusCount.BinomialWithBonusCount.TYPE, ApplyBonusCount.BinomialWithBonusCount::deserialize);
      FORMULAS.put(ApplyBonusCount.OreDrops.TYPE, ApplyBonusCount.OreDrops::deserialize);
      FORMULAS.put(ApplyBonusCount.UniformBonusCount.TYPE, ApplyBonusCount.UniformBonusCount::deserialize);
   }

   static final class BinomialWithBonusCount implements ApplyBonusCount.Formula {
      public static final ResourceLocation TYPE = new ResourceLocation("binomial_with_bonus_count");
      private final int extraRounds;
      private final float probability;

      public BinomialWithBonusCount(int var1, float var2) {
         this.extraRounds = â˜ƒ;
         this.probability = â˜ƒ;
      }

      @Override
      public int calculateNewCount(Random var1, int var2, int var3) {
         for(int â˜ƒ = 0; â˜ƒ < â˜ƒ + this.extraRounds; ++â˜ƒ) {
            if (â˜ƒ.nextFloat() < this.probability) {
               ++â˜ƒ;
            }
         }

         return â˜ƒ;
      }

      @Override
      public void serializeParams(JsonObject var1, JsonSerializationContext var2) {
         â˜ƒ.addProperty("extra", this.extraRounds);
         â˜ƒ.addProperty("probability", this.probability);
      }

      public static ApplyBonusCount.Formula deserialize(JsonObject var0, JsonDeserializationContext var1) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "extra");
         float â˜ƒx = GsonHelper.getAsFloat(â˜ƒ, "probability");
         return new ApplyBonusCount.BinomialWithBonusCount(â˜ƒ, â˜ƒx);
      }

      @Override
      public ResourceLocation getType() {
         return TYPE;
      }
   }

   interface Formula {
      int calculateNewCount(Random var1, int var2, int var3);

      void serializeParams(JsonObject var1, JsonSerializationContext var2);

      ResourceLocation getType();
   }

   interface FormulaDeserializer {
      ApplyBonusCount.Formula deserialize(JsonObject var1, JsonDeserializationContext var2);
   }

   static final class OreDrops implements ApplyBonusCount.Formula {
      public static final ResourceLocation TYPE = new ResourceLocation("ore_drops");

      @Override
      public int calculateNewCount(Random var1, int var2, int var3) {
         if (â˜ƒ > 0) {
            int â˜ƒ = â˜ƒ.nextInt(â˜ƒ + 2) - 1;
            if (â˜ƒ < 0) {
               â˜ƒ = 0;
            }

            return â˜ƒ * (â˜ƒ + 1);
         } else {
            return â˜ƒ;
         }
      }

      @Override
      public void serializeParams(JsonObject var1, JsonSerializationContext var2) {
      }

      public static ApplyBonusCount.Formula deserialize(JsonObject var0, JsonDeserializationContext var1) {
         return new ApplyBonusCount.OreDrops();
      }

      @Override
      public ResourceLocation getType() {
         return TYPE;
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<ApplyBonusCount> {
      public void serialize(JsonObject var1, ApplyBonusCount var2, JsonSerializationContext var3) {
         super.serialize(â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.addProperty("enchantment", Registry.ENCHANTMENT.getKey(â˜ƒ.enchantment).toString());
         â˜ƒ.addProperty("formula", â˜ƒ.formula.getType().toString());
         JsonObject â˜ƒ = new JsonObject();
         â˜ƒ.formula.serializeParams(â˜ƒ, â˜ƒ);
         if (â˜ƒ.size() > 0) {
            â˜ƒ.add("parameters", â˜ƒ);
         }
      }

      public ApplyBonusCount deserialize(JsonObject var1, JsonDeserializationContext var2, LootItemCondition[] var3) {
         ResourceLocation â˜ƒ = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "enchantment"));
         Enchantment â˜ƒx = (Enchantment)Registry.ENCHANTMENT.getOptional(â˜ƒ).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + â˜ƒ));
         ResourceLocation â˜ƒxx = new ResourceLocation(GsonHelper.getAsString(â˜ƒ, "formula"));
         ApplyBonusCount.FormulaDeserializer â˜ƒxxx = (ApplyBonusCount.FormulaDeserializer)ApplyBonusCount.FORMULAS.get(â˜ƒxx);
         if (â˜ƒxxx == null) {
            throw new JsonParseException("Invalid formula id: " + â˜ƒxx);
         } else {
            ApplyBonusCount.Formula â˜ƒ;
            if (â˜ƒ.has("parameters")) {
               â˜ƒ = â˜ƒxxx.deserialize(GsonHelper.getAsJsonObject(â˜ƒ, "parameters"), â˜ƒ);
            } else {
               â˜ƒ = â˜ƒxxx.deserialize(new JsonObject(), â˜ƒ);
            }

            return new ApplyBonusCount(â˜ƒ, â˜ƒx, â˜ƒ);
         }
      }
   }

   static final class UniformBonusCount implements ApplyBonusCount.Formula {
      public static final ResourceLocation TYPE = new ResourceLocation("uniform_bonus_count");
      private final int bonusMultiplier;

      public UniformBonusCount(int var1) {
         this.bonusMultiplier = â˜ƒ;
      }

      @Override
      public int calculateNewCount(Random var1, int var2, int var3) {
         return â˜ƒ + â˜ƒ.nextInt(this.bonusMultiplier * â˜ƒ + 1);
      }

      @Override
      public void serializeParams(JsonObject var1, JsonSerializationContext var2) {
         â˜ƒ.addProperty("bonusMultiplier", this.bonusMultiplier);
      }

      public static ApplyBonusCount.Formula deserialize(JsonObject var0, JsonDeserializationContext var1) {
         int â˜ƒ = GsonHelper.getAsInt(â˜ƒ, "bonusMultiplier");
         return new ApplyBonusCount.UniformBonusCount(â˜ƒ);
      }

      @Override
      public ResourceLocation getType() {
         return TYPE;
      }
   }
}
