package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class RandomChanceWithLooting implements LootCondition {
   private final float field_186627_a;
   private final float field_186628_b;

   public RandomChanceWithLooting(float var1, float var2) {
      this.field_186627_a = ☃;
      this.field_186628_b = ☃;
   }

   @Override
   public boolean func_186618_a(Random var1, LootContext var2) {
      int ☃ = 0;
      if (☃.func_186492_c() instanceof EntityLivingBase) {
         ☃ = EnchantmentHelper.func_185283_h((EntityLivingBase)☃.func_186492_c());
      }

      return ☃.nextFloat() < this.field_186627_a + (float)☃ * this.field_186628_b;
   }

   public static class Serializer extends LootCondition.Serializer<RandomChanceWithLooting> {
      protected Serializer() {
         super(new ResourceLocation("random_chance_with_looting"), RandomChanceWithLooting.class);
      }

      public void func_186605_a(JsonObject var1, RandomChanceWithLooting var2, JsonSerializationContext var3) {
         ☃.addProperty("chance", ☃.field_186627_a);
         ☃.addProperty("looting_multiplier", ☃.field_186628_b);
      }

      public RandomChanceWithLooting func_186603_b(JsonObject var1, JsonDeserializationContext var2) {
         return new RandomChanceWithLooting(JsonUtils.func_151217_k(☃, "chance"), JsonUtils.func_151217_k(☃, "looting_multiplier"));
      }
   }
}
