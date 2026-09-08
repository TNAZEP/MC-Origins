package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class LootingEnchantBonus extends LootFunction {
   private final RandomValueRange field_186563_a;
   private final int field_189971_b;

   public LootingEnchantBonus(LootCondition[] var1, RandomValueRange var2, int var3) {
      super(☃);
      this.field_186563_a = ☃;
      this.field_189971_b = ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      Entity ☃ = ☃.func_186492_c();
      if (☃ instanceof EntityLivingBase) {
         int ☃x = EnchantmentHelper.func_185283_h((EntityLivingBase)☃);
         if (☃x == 0) {
            return ☃;
         }

         float ☃x = (float)☃x * this.field_186563_a.func_186507_b(☃);
         ☃.func_190917_f(Math.round(☃x));
         if (this.field_189971_b != 0 && ☃.func_190916_E() > this.field_189971_b) {
            ☃.func_190920_e(this.field_189971_b);
         }
      }

      return ☃;
   }

   public static class Serializer extends LootFunction.Serializer<LootingEnchantBonus> {
      protected Serializer() {
         super(new ResourceLocation("looting_enchant"), LootingEnchantBonus.class);
      }

      public void func_186532_a(JsonObject var1, LootingEnchantBonus var2, JsonSerializationContext var3) {
         ☃.add("count", ☃.serialize(☃.field_186563_a));
         if (☃.field_189971_b > 0) {
            ☃.add("limit", ☃.serialize(☃.field_189971_b));
         }
      }

      public LootingEnchantBonus func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         int ☃ = JsonUtils.func_151208_a(☃, "limit", 0);
         return new LootingEnchantBonus(☃, JsonUtils.func_188174_a(☃, "count", ☃, RandomValueRange.class), ☃);
      }
   }
}
