package net.minecraft.world.level.storage.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class IntRange {
   @Nullable
   final NumberProvider min;
   @Nullable
   final NumberProvider max;
   private final IntRange.IntLimiter limiter;
   private final IntRange.IntChecker predicate;

   public Set<LootContextParam<?>> getReferencedContextParams() {
      Builder<LootContextParam<?>> â˜ƒ = ImmutableSet.builder();
      if (this.min != null) {
         â˜ƒ.addAll(this.min.getReferencedContextParams());
      }

      if (this.max != null) {
         â˜ƒ.addAll(this.max.getReferencedContextParams());
      }

      return â˜ƒ.build();
   }

   IntRange(@Nullable NumberProvider var1, @Nullable NumberProvider var2) {
      this.min = â˜ƒ;
      this.max = â˜ƒ;
      if (â˜ƒ == null) {
         if (â˜ƒ == null) {
            this.limiter = (var0, var1x) -> var1x;
            this.predicate = (var0, var1x) -> true;
         } else {
            this.limiter = (var1x, var2x) -> Math.min(â˜ƒ.getInt(var1x), var2x);
            this.predicate = (var1x, var2x) -> var2x <= â˜ƒ.getInt(var1x);
         }
      } else if (â˜ƒ == null) {
         this.limiter = (var1x, var2x) -> Math.max(â˜ƒ.getInt(var1x), var2x);
         this.predicate = (var1x, var2x) -> var2x >= â˜ƒ.getInt(var1x);
      } else {
         this.limiter = (var2x, var3) -> Mth.clamp(var3, â˜ƒ.getInt(var2x), â˜ƒ.getInt(var2x));
         this.predicate = (var2x, var3) -> var3 >= â˜ƒ.getInt(var2x) && var3 <= â˜ƒ.getInt(var2x);
      }
   }

   public static IntRange exact(int var0) {
      ConstantValue â˜ƒ = ConstantValue.exactly((float)â˜ƒ);
      return new IntRange(â˜ƒ, â˜ƒ);
   }

   public static IntRange range(int var0, int var1) {
      return new IntRange(ConstantValue.exactly((float)â˜ƒ), ConstantValue.exactly((float)â˜ƒ));
   }

   public static IntRange lowerBound(int var0) {
      return new IntRange(ConstantValue.exactly((float)â˜ƒ), null);
   }

   public static IntRange upperBound(int var0) {
      return new IntRange(null, ConstantValue.exactly((float)â˜ƒ));
   }

   public int clamp(LootContext var1, int var2) {
      return this.limiter.apply(â˜ƒ, â˜ƒ);
   }

   public boolean test(LootContext var1, int var2) {
      return this.predicate.test(â˜ƒ, â˜ƒ);
   }

   @FunctionalInterface
   interface IntChecker {
      boolean test(LootContext var1, int var2);
   }

   @FunctionalInterface
   interface IntLimiter {
      int apply(LootContext var1, int var2);
   }

   public static class Serializer implements JsonDeserializer<IntRange>, JsonSerializer<IntRange> {
      public IntRange deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         if (â˜ƒ.isJsonPrimitive()) {
            return IntRange.exact(â˜ƒ.getAsInt());
         } else {
            JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "value");
            NumberProvider â˜ƒx = â˜ƒ.has("min") ? GsonHelper.getAsObject(â˜ƒ, "min", â˜ƒ, NumberProvider.class) : null;
            NumberProvider â˜ƒxx = â˜ƒ.has("max") ? GsonHelper.getAsObject(â˜ƒ, "max", â˜ƒ, NumberProvider.class) : null;
            return new IntRange(â˜ƒx, â˜ƒxx);
         }
      }

      public JsonElement serialize(IntRange var1, Type var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();
         if (Objects.equals(â˜ƒ.max, â˜ƒ.min)) {
            return â˜ƒ.serialize(â˜ƒ.min);
         } else {
            if (â˜ƒ.max != null) {
               â˜ƒ.add("max", â˜ƒ.serialize(â˜ƒ.max));
            }

            if (â˜ƒ.min != null) {
               â˜ƒ.add("min", â˜ƒ.serialize(â˜ƒ.min));
            }

            return â˜ƒ;
         }
      }
   }
}
