package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class TimeCheck implements LootItemCondition {
   @Nullable
   final Long period;
   final IntRange value;

   TimeCheck(@Nullable Long var1, IntRange var2) {
      this.period = â˜ƒ;
      this.value = â˜ƒ;
   }

   @Override
   public LootItemConditionType getType() {
      return LootItemConditions.TIME_CHECK;
   }

   @Override
   public Set<LootContextParam<?>> getReferencedContextParams() {
      return this.value.getReferencedContextParams();
   }

   public boolean test(LootContext var1) {
      ServerLevel â˜ƒ = â˜ƒ.getLevel();
      long â˜ƒx = â˜ƒ.getDayTime();
      if (this.period != null) {
         â˜ƒx %= this.period;
      }

      return this.value.test(â˜ƒ, (int)â˜ƒx);
   }

   public static TimeCheck.Builder time(IntRange var0) {
      return new TimeCheck.Builder(â˜ƒ);
   }

   public static class Builder implements LootItemCondition.Builder {
      @Nullable
      private Long period;
      private final IntRange value;

      public Builder(IntRange var1) {
         this.value = â˜ƒ;
      }

      public TimeCheck.Builder setPeriod(long var1) {
         this.period = â˜ƒ;
         return this;
      }

      public TimeCheck build() {
         return new TimeCheck(this.period, this.value);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<TimeCheck> {
      public void serialize(JsonObject var1, TimeCheck var2, JsonSerializationContext var3) {
         â˜ƒ.addProperty("period", â˜ƒ.period);
         â˜ƒ.add("value", â˜ƒ.serialize(â˜ƒ.value));
      }

      public TimeCheck deserialize(JsonObject var1, JsonDeserializationContext var2) {
         Long â˜ƒ = â˜ƒ.has("period") ? GsonHelper.getAsLong(â˜ƒ, "period") : null;
         IntRange â˜ƒx = GsonHelper.getAsObject(â˜ƒ, "value", â˜ƒ, IntRange.class);
         return new TimeCheck(â˜ƒ, â˜ƒx);
      }
   }
}
