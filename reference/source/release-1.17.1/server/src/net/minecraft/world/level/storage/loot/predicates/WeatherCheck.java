package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;

public class WeatherCheck implements LootItemCondition {
   @Nullable
   final Boolean isRaining;
   @Nullable
   final Boolean isThundering;

   WeatherCheck(@Nullable Boolean var1, @Nullable Boolean var2) {
      this.isRaining = â˜ƒ;
      this.isThundering = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.WEATHER_CHECK;
   }

   public boolean test(LootContext var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      if (this.isRaining != null && this.isRaining != â˜ƒ.isRaining()) {
         return false;
      } else {
         return this.isThundering == null || this.isThundering == â˜ƒ.isThundering();
      }
   }

   public static WeatherCheck.Builder weather() {
      return new WeatherCheck.Builder();
   }

   public static class Builder implements LootItemCondition.Builder {
      @Nullable
      private Boolean isRaining;
      @Nullable
      private Boolean isThundering;

      public WeatherCheck.Builder setRaining(@Nullable Boolean var1) {
         this.isRaining = â˜ƒ;
         return this;
      }

      public WeatherCheck.Builder setThundering(@Nullable Boolean var1) {
         this.isThundering = â˜ƒ;
         return this;
      }

      public WeatherCheck build() {
         return new WeatherCheck(this.isRaining, this.isThundering);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<WeatherCheck> {
      public void serialize(JsonObject var1, WeatherCheck var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("raining", â˜ƒ.isRaining);
         â˜ƒ.addProperty("thundering", â˜ƒ.isThundering);
      }

      public WeatherCheck deserialize(JsonObject var1, JsonDeserializationContext var2) {
         Boolean â˜ƒ = â˜ƒ.has("raining") ? GsonHelper.getAsBoolean(â˜ƒ, "raining") : null;
         Boolean â˜ƒx = â˜ƒ.has("thundering") ? GsonHelper.getAsBoolean(â˜ƒ, "thundering") : null;
         return new WeatherCheck(â˜ƒ, â˜ƒx);
      }
   }
}
