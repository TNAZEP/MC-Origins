package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetCount extends LootFunction {
   private final RandomValueRange field_186568_a;

   public SetCount(LootCondition[] var1, RandomValueRange var2) {
      super(☃);
      this.field_186568_a = ☃;
   }

   @Override
   public ItemStack func_186553_a(ItemStack var1, Random var2, LootContext var3) {
      ☃.func_190920_e(this.field_186568_a.func_186511_a(☃));
      return ☃;
   }

   public static class Serializer extends LootFunction.Serializer<SetCount> {
      protected Serializer() {
         super(new ResourceLocation("set_count"), SetCount.class);
      }

      public void func_186532_a(JsonObject var1, SetCount var2, JsonSerializationContext var3) {
         ☃.add("count", ☃.serialize(☃.field_186568_a));
      }

      public SetCount func_186530_b(JsonObject var1, JsonDeserializationContext var2, LootCondition[] var3) {
         return new SetCount(☃, JsonUtils.func_188174_a(☃, "count", ☃, RandomValueRange.class));
      }
   }
}
