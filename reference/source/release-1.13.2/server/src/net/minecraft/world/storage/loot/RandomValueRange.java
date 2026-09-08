package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.MathHelper;

public class RandomValueRange {
   private final float field_186514_a;
   private final float field_186515_b;

   public RandomValueRange(float var1, float var2) {
      this.field_186514_a = ☃;
      this.field_186515_b = ☃;
   }

   public RandomValueRange(float var1) {
      this.field_186514_a = ☃;
      this.field_186515_b = ☃;
   }

   public float func_186509_a() {
      return this.field_186514_a;
   }

   public float func_186512_b() {
      return this.field_186515_b;
   }

   public int func_186511_a(Random var1) {
      return MathHelper.func_76136_a(☃, MathHelper.func_76141_d(this.field_186514_a), MathHelper.func_76141_d(this.field_186515_b));
   }

   public float func_186507_b(Random var1) {
      return MathHelper.func_151240_a(☃, this.field_186514_a, this.field_186515_b);
   }

   public boolean func_186510_a(int var1) {
      return (float)☃ <= this.field_186515_b && (float)☃ >= this.field_186514_a;
   }

   public static class Serializer implements JsonDeserializer<RandomValueRange>, JsonSerializer<RandomValueRange> {
      public RandomValueRange deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (JsonUtils.func_188175_b(☃)) {
            return new RandomValueRange(JsonUtils.func_151220_d(☃, "value"));
         } else {
            JsonObject ☃ = JsonUtils.func_151210_l(☃, "value");
            float ☃x = JsonUtils.func_151217_k(☃, "min");
            float ☃xx = JsonUtils.func_151217_k(☃, "max");
            return new RandomValueRange(☃x, ☃xx);
         }
      }

      public JsonElement serialize(RandomValueRange var1, Type var2, JsonSerializationContext var3) {
         if (☃.field_186514_a == ☃.field_186515_b) {
            return new JsonPrimitive(☃.field_186514_a);
         } else {
            JsonObject ☃ = new JsonObject();
            ☃.addProperty("min", ☃.field_186514_a);
            ☃.addProperty("max", ☃.field_186515_b);
            return ☃;
         }
      }
   }
}
