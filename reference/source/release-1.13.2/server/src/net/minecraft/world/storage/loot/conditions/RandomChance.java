package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class RandomChance implements LootCondition {
   private final float field_186630_a;

   public RandomChance(float var1) {
      this.field_186630_a = ☃;
   }

   @Override
   public boolean func_186618_a(Random var1, LootContext var2) {
      return ☃.nextFloat() < this.field_186630_a;
   }

   public static class Serializer extends LootCondition.Serializer<RandomChance> {
      protected Serializer() {
         super(new ResourceLocation("random_chance"), RandomChance.class);
      }

      public void func_186605_a(JsonObject var1, RandomChance var2, JsonSerializationContext var3) {
         ☃.addProperty("chance", ☃.field_186630_a);
      }

      public RandomChance func_186603_b(JsonObject var1, JsonDeserializationContext var2) {
         return new RandomChance(JsonUtils.func_151217_k(☃, "chance"));
      }
   }
}
