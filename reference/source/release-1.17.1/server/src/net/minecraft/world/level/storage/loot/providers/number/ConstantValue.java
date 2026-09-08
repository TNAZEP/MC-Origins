package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;

public final class ConstantValue implements NumberProvider {
   final float value;

   ConstantValue(float var1) {
      this.value = â˜ƒ;
   }

   @Override
   public LootNumberProviderType getType() {
      return NumberProviders.CONSTANT;
   }

   @Override
   public float getFloat(LootContext var1) {
      return this.value;
   }

   public static ConstantValue exactly(float var0) {
      return new ConstantValue(â˜ƒ);
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         return Float.compare(((ConstantValue)â˜ƒ).value, this.value) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.value != 0.0F ? Float.floatToIntBits(this.value) : 0;
   }

   public static class InlineSerializer implements GsonAdapterFactory.InlineSerializer<ConstantValue> {
      public JsonElement serialize(ConstantValue var1, JsonSerializationContext var2) {
         return new JsonPrimitive(â˜ƒ.value);
      }

      public ConstantValue deserialize(JsonElement var1, JsonDeserializationContext var2) {
         return new ConstantValue(GsonHelper.convertToFloat(â˜ƒ, "value"));
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConstantValue> {
      public void serialize(JsonObject var1, ConstantValue var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("value", â˜ƒ.value);
      }

      public ConstantValue deserialize(JsonObject var1, JsonDeserializationContext var2) {
         float â˜ƒ = GsonHelper.getAsFloat(â˜ƒ, "value");
         return new ConstantValue(â˜ƒ);
      }
   }
}
