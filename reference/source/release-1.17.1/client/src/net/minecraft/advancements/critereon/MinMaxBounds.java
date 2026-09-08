package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;

public abstract class MinMaxBounds<T extends Number> {
   public static final SimpleCommandExceptionType ERROR_EMPTY = new SimpleCommandExceptionType(new TranslatableComponent("argument.range.empty"));
   public static final SimpleCommandExceptionType ERROR_SWAPPED = new SimpleCommandExceptionType(new TranslatableComponent("argument.range.swapped"));
   protected final T min;
   protected final T max;

   protected MinMaxBounds(@Nullable T var1, @Nullable T var2) {
      this.min = â˜ƒ;
      this.max = â˜ƒ;
   }

   @Nullable
   public T getMin() {
      return this.min;
   }

   @Nullable
   public T getMax() {
      return this.max;
   }

   public boolean isAny() {
      return this.min == null && this.max == null;
   }

   public JsonElement serializeToJson() {
      if (this.isAny()) {
         return JsonNull.INSTANCE;
      } else if (this.min != null && this.min.equals(this.max)) {
         return new JsonPrimitive(this.min);
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.min != null) {
            â˜ƒ.addProperty("min", this.min);
         }

         if (this.max != null) {
            â˜ƒ.addProperty("max", this.max);
         }

         return â˜ƒ;
      }
   }

   protected static <T extends Number, R extends MinMaxBounds<T>> R fromJson(
      @Nullable JsonElement var0, R var1, BiFunction<JsonElement, String, T> var2, MinMaxBounds.BoundsFactory<T, R> var3
   ) {
      if (â˜ƒ == null || â˜ƒ.isJsonNull()) {
         return â˜ƒ;
      } else if (GsonHelper.isNumberValue(â˜ƒ)) {
         T â˜ƒ = (T)â˜ƒ.apply(â˜ƒ, "value");
         return â˜ƒ.create(â˜ƒ, â˜ƒ);
      } else {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "value");
         T â˜ƒx = (T)(â˜ƒ.has("min") ? â˜ƒ.apply(â˜ƒ.get("min"), "min") : null);
         T â˜ƒxx = (T)(â˜ƒ.has("max") ? â˜ƒ.apply(â˜ƒ.get("max"), "max") : null);
         return â˜ƒ.create(â˜ƒx, â˜ƒxx);
      }
   }

   protected static <T extends Number, R extends MinMaxBounds<T>> R fromReader(
      StringReader var0,
      MinMaxBounds.BoundsFromReaderFactory<T, R> var1,
      Function<String, T> var2,
      Supplier<DynamicCommandExceptionType> var3,
      Function<T, T> var4
   ) throws CommandSyntaxException {
      if (!â˜ƒ.canRead()) {
         throw ERROR_EMPTY.createWithContext(â˜ƒ);
      } else {
         int â˜ƒ = â˜ƒ.getCursor();

         try {
            T â˜ƒxx = (T)optionallyFormat(readNumber(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
            T â˜ƒx;
            if (â˜ƒ.canRead(2) && â˜ƒ.peek() == '.' && â˜ƒ.peek(1) == '.') {
               â˜ƒ.skip();
               â˜ƒ.skip();
               â˜ƒx = (T)optionallyFormat(readNumber(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ);
               if (â˜ƒxx == null && â˜ƒx == null) {
                  throw ERROR_EMPTY.createWithContext(â˜ƒ);
               }
            } else {
               â˜ƒx = â˜ƒxx;
            }

            if (â˜ƒxx == null && â˜ƒx == null) {
               throw ERROR_EMPTY.createWithContext(â˜ƒ);
            } else {
               return â˜ƒ.create(â˜ƒ, â˜ƒxx, â˜ƒx);
            }
         } catch (CommandSyntaxException var8) {
            â˜ƒ.setCursor(â˜ƒ);
            throw new CommandSyntaxException(var8.getType(), var8.getRawMessage(), var8.getInput(), â˜ƒ);
         }
      }
   }

   @Nullable
   private static <T extends Number> T readNumber(StringReader var0, Function<String, T> var1, Supplier<DynamicCommandExceptionType> var2) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();

      while(â˜ƒ.canRead() && isAllowedInputChat(â˜ƒ)) {
         â˜ƒ.skip();
      }

      String â˜ƒx = â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());
      if (â˜ƒx.isEmpty()) {
         return null;
      } else {
         try {
            return (T)â˜ƒ.apply(â˜ƒx);
         } catch (NumberFormatException var6) {
            throw ((DynamicCommandExceptionType)â˜ƒ.get()).createWithContext(â˜ƒ, â˜ƒx);
         }
      }
   }

   private static boolean isAllowedInputChat(StringReader var0) {
      char â˜ƒ = â˜ƒ.peek();
      if ((â˜ƒ < '0' || â˜ƒ > '9') && â˜ƒ != '-') {
         if (â˜ƒ != '.') {
            return false;
         } else {
            return !â˜ƒ.canRead(2) || â˜ƒ.peek(1) != '.';
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static <T> T optionallyFormat(@Nullable T var0, Function<T, T> var1) {
      return (T)(â˜ƒ == null ? null : â˜ƒ.apply(â˜ƒ));
   }

   @FunctionalInterface
   protected interface BoundsFactory<T extends Number, R extends MinMaxBounds<T>> {
      R create(@Nullable T var1, @Nullable T var2);
   }

   @FunctionalInterface
   protected interface BoundsFromReaderFactory<T extends Number, R extends MinMaxBounds<T>> {
      R create(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
   }

   public static class Doubles extends MinMaxBounds<Double> {
      public static final MinMaxBounds.Doubles ANY = new MinMaxBounds.Doubles(null, null);
      private final Double minSq;
      private final Double maxSq;

      private static MinMaxBounds.Doubles create(StringReader var0, @Nullable Double var1, @Nullable Double var2) throws CommandSyntaxException {
         if (â˜ƒ != null && â˜ƒ != null && â˜ƒ > â˜ƒ) {
            throw ERROR_SWAPPED.createWithContext(â˜ƒ);
         } else {
            return new MinMaxBounds.Doubles(â˜ƒ, â˜ƒ);
         }
      }

      @Nullable
      private static Double squareOpt(@Nullable Double var0) {
         return â˜ƒ == null ? null : â˜ƒ * â˜ƒ;
      }

      private Doubles(@Nullable Double var1, @Nullable Double var2) {
         super(â˜ƒ, â˜ƒ);
         this.minSq = squareOpt(â˜ƒ);
         this.maxSq = squareOpt(â˜ƒ);
      }

      public static MinMaxBounds.Doubles exactly(double var0) {
         return new MinMaxBounds.Doubles(â˜ƒ, â˜ƒ);
      }

      public static MinMaxBounds.Doubles between(double var0, double var2) {
         return new MinMaxBounds.Doubles(â˜ƒ, â˜ƒ);
      }

      public static MinMaxBounds.Doubles atLeast(double var0) {
         return new MinMaxBounds.Doubles(â˜ƒ, null);
      }

      public static MinMaxBounds.Doubles atMost(double var0) {
         return new MinMaxBounds.Doubles(null, â˜ƒ);
      }

      public boolean matches(double var1) {
         if (this.min != null && this.min > â˜ƒ) {
            return false;
         } else {
            return this.max == null || !(this.max < â˜ƒ);
         }
      }

      public boolean matchesSqr(double var1) {
         if (this.minSq != null && this.minSq > â˜ƒ) {
            return false;
         } else {
            return this.maxSq == null || !(this.maxSq < â˜ƒ);
         }
      }

      public static MinMaxBounds.Doubles fromJson(@Nullable JsonElement var0) {
         return fromJson(â˜ƒ, ANY, GsonHelper::convertToDouble, MinMaxBounds.Doubles::new);
      }

      public static MinMaxBounds.Doubles fromReader(StringReader var0) throws CommandSyntaxException {
         return fromReader(â˜ƒ, var0x -> var0x);
      }

      public static MinMaxBounds.Doubles fromReader(StringReader var0, Function<Double, Double> var1) throws CommandSyntaxException {
         return fromReader(â˜ƒ, MinMaxBounds.Doubles::create, Double::parseDouble, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidDouble, â˜ƒ);
      }
   }

   public static class Ints extends MinMaxBounds<Integer> {
      public static final MinMaxBounds.Ints ANY = new MinMaxBounds.Ints(null, null);
      private final Long minSq;
      private final Long maxSq;

      private static MinMaxBounds.Ints create(StringReader var0, @Nullable Integer var1, @Nullable Integer var2) throws CommandSyntaxException {
         if (â˜ƒ != null && â˜ƒ != null && â˜ƒ > â˜ƒ) {
            throw ERROR_SWAPPED.createWithContext(â˜ƒ);
         } else {
            return new MinMaxBounds.Ints(â˜ƒ, â˜ƒ);
         }
      }

      @Nullable
      private static Long squareOpt(@Nullable Integer var0) {
         return â˜ƒ == null ? null : â˜ƒ.longValue() * â˜ƒ.longValue();
      }

      private Ints(@Nullable Integer var1, @Nullable Integer var2) {
         super(â˜ƒ, â˜ƒ);
         this.minSq = squareOpt(â˜ƒ);
         this.maxSq = squareOpt(â˜ƒ);
      }

      public static MinMaxBounds.Ints exactly(int var0) {
         return new MinMaxBounds.Ints(â˜ƒ, â˜ƒ);
      }

      public static MinMaxBounds.Ints between(int var0, int var1) {
         return new MinMaxBounds.Ints(â˜ƒ, â˜ƒ);
      }

      public static MinMaxBounds.Ints atLeast(int var0) {
         return new MinMaxBounds.Ints(â˜ƒ, null);
      }

      public static MinMaxBounds.Ints atMost(int var0) {
         return new MinMaxBounds.Ints(null, â˜ƒ);
      }

      public boolean matches(int var1) {
         if (this.min != null && this.min > â˜ƒ) {
            return false;
         } else {
            return this.max == null || this.max >= â˜ƒ;
         }
      }

      public boolean matchesSqr(long var1) {
         if (this.minSq != null && this.minSq > â˜ƒ) {
            return false;
         } else {
            return this.maxSq == null || this.maxSq >= â˜ƒ;
         }
      }

      public static MinMaxBounds.Ints fromJson(@Nullable JsonElement var0) {
         return fromJson(â˜ƒ, ANY, GsonHelper::convertToInt, MinMaxBounds.Ints::new);
      }

      public static MinMaxBounds.Ints fromReader(StringReader var0) throws CommandSyntaxException {
         return fromReader(â˜ƒ, var0x -> var0x);
      }

      public static MinMaxBounds.Ints fromReader(StringReader var0, Function<Integer, Integer> var1) throws CommandSyntaxException {
         return fromReader(â˜ƒ, MinMaxBounds.Ints::create, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, â˜ƒ);
      }
   }
}
