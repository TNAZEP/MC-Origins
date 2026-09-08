package net.minecraft.advancements.criterion;

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
import net.minecraft.util.JsonUtils;
import net.minecraft.util.text.TextComponentTranslation;

public abstract class MinMaxBounds<T extends Number> {
   public static final SimpleCommandExceptionType field_196978_b = new SimpleCommandExceptionType(new TextComponentTranslation("argument.range.empty"));
   public static final SimpleCommandExceptionType field_196980_d = new SimpleCommandExceptionType(new TextComponentTranslation("argument.range.swapped"));
   protected final T field_192517_b;
   protected final T field_192518_c;

   protected MinMaxBounds(@Nullable T var1, @Nullable T var2) {
      this.field_192517_b = ☃;
      this.field_192518_c = ☃;
   }

   @Nullable
   public T func_196973_a() {
      return this.field_192517_b;
   }

   @Nullable
   public T func_196977_b() {
      return this.field_192518_c;
   }

   public boolean func_211335_c() {
      return this.field_192517_b == null && this.field_192518_c == null;
   }

   public JsonElement func_200321_c() {
      if (this.func_211335_c()) {
         return JsonNull.INSTANCE;
      } else if (this.field_192517_b != null && this.field_192517_b.equals(this.field_192518_c)) {
         return new JsonPrimitive(this.field_192517_b);
      } else {
         JsonObject ☃ = new JsonObject();
         if (this.field_192517_b != null) {
            ☃.addProperty("min", this.field_192517_b);
         }

         if (this.field_192518_c != null) {
            ☃.addProperty("max", this.field_192517_b);
         }

         return ☃;
      }
   }

   protected static <T extends Number, R extends MinMaxBounds<T>> R func_211331_a(
      @Nullable JsonElement var0, R var1, BiFunction<JsonElement, String, T> var2, MinMaxBounds.IBoundFactory<T, R> var3
   ) {
      if (☃ == null || ☃.isJsonNull()) {
         return ☃;
      } else if (JsonUtils.func_188175_b(☃)) {
         T ☃ = (T)☃.apply(☃, "value");
         return ☃.create(☃, ☃);
      } else {
         JsonObject ☃ = JsonUtils.func_151210_l(☃, "value");
         T ☃x = (T)(☃.has("min") ? ☃.apply(☃.get("min"), "min") : null);
         T ☃xx = (T)(☃.has("max") ? ☃.apply(☃.get("max"), "max") : null);
         return ☃.create(☃x, ☃xx);
      }
   }

   protected static <T extends Number, R extends MinMaxBounds<T>> R func_211337_a(
      StringReader var0, MinMaxBounds.IBoundReader<T, R> var1, Function<String, T> var2, Supplier<DynamicCommandExceptionType> var3, Function<T, T> var4
   ) throws CommandSyntaxException {
      if (!☃.canRead()) {
         throw field_196978_b.createWithContext(☃);
      } else {
         int ☃ = ☃.getCursor();

         try {
            T ☃xx = (T)func_196972_a(func_196975_b(☃, ☃, ☃), ☃);
            T ☃x;
            if (☃.canRead(2) && ☃.peek() == '.' && ☃.peek(1) == '.') {
               ☃.skip();
               ☃.skip();
               ☃x = (T)func_196972_a(func_196975_b(☃, ☃, ☃), ☃);
               if (☃xx == null && ☃x == null) {
                  throw field_196978_b.createWithContext(☃);
               }
            } else {
               ☃x = ☃xx;
            }

            if (☃xx == null && ☃x == null) {
               throw field_196978_b.createWithContext(☃);
            } else {
               return ☃.create(☃, ☃xx, ☃x);
            }
         } catch (CommandSyntaxException var8) {
            ☃.setCursor(☃);
            throw new CommandSyntaxException(var8.getType(), var8.getRawMessage(), var8.getInput(), ☃);
         }
      }
   }

   @Nullable
   private static <T extends Number> T func_196975_b(StringReader var0, Function<String, T> var1, Supplier<DynamicCommandExceptionType> var2) throws CommandSyntaxException {
      int ☃ = ☃.getCursor();

      while(☃.canRead() && func_196970_c(☃)) {
         ☃.skip();
      }

      String ☃x = ☃.getString().substring(☃, ☃.getCursor());
      if (☃x.isEmpty()) {
         return null;
      } else {
         try {
            return (T)☃.apply(☃x);
         } catch (NumberFormatException var6) {
            throw ((DynamicCommandExceptionType)☃.get()).createWithContext(☃, ☃x);
         }
      }
   }

   private static boolean func_196970_c(StringReader var0) {
      char ☃ = ☃.peek();
      if ((☃ < '0' || ☃ > '9') && ☃ != '-') {
         if (☃ != '.') {
            return false;
         } else {
            return !☃.canRead(2) || ☃.peek(1) != '.';
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static <T> T func_196972_a(@Nullable T var0, Function<T, T> var1) {
      return (T)(☃ == null ? null : ☃.apply(☃));
   }

   public static class FloatBound extends MinMaxBounds<Float> {
      public static final MinMaxBounds.FloatBound field_211359_e = new MinMaxBounds.FloatBound(null, null);
      private final Double field_211360_f;
      private final Double field_211361_g;

      private static MinMaxBounds.FloatBound func_211352_a(StringReader var0, @Nullable Float var1, @Nullable Float var2) throws CommandSyntaxException {
         if (☃ != null && ☃ != null && ☃ > ☃) {
            throw field_196980_d.createWithContext(☃);
         } else {
            return new MinMaxBounds.FloatBound(☃, ☃);
         }
      }

      @Nullable
      private static Double func_211350_a(@Nullable Float var0) {
         return ☃ == null ? null : ☃.doubleValue() * ☃.doubleValue();
      }

      private FloatBound(@Nullable Float var1, @Nullable Float var2) {
         super(☃, ☃);
         this.field_211360_f = func_211350_a(☃);
         this.field_211361_g = func_211350_a(☃);
      }

      public static MinMaxBounds.FloatBound func_211355_b(float var0) {
         return new MinMaxBounds.FloatBound(☃, null);
      }

      public boolean func_211354_d(float var1) {
         if (this.field_192517_b != null && this.field_192517_b > ☃) {
            return false;
         } else {
            return this.field_192518_c == null || !(this.field_192518_c < ☃);
         }
      }

      public boolean func_211351_a(double var1) {
         if (this.field_211360_f != null && this.field_211360_f > ☃) {
            return false;
         } else {
            return this.field_211361_g == null || !(this.field_211361_g < ☃);
         }
      }

      public static MinMaxBounds.FloatBound func_211356_a(@Nullable JsonElement var0) {
         return func_211331_a(☃, field_211359_e, JsonUtils::func_151220_d, MinMaxBounds.FloatBound::new);
      }

      public static MinMaxBounds.FloatBound func_211357_a(StringReader var0) throws CommandSyntaxException {
         return func_211353_a(☃, var0x -> var0x);
      }

      public static MinMaxBounds.FloatBound func_211353_a(StringReader var0, Function<Float, Float> var1) throws CommandSyntaxException {
         return func_211337_a(☃, MinMaxBounds.FloatBound::func_211352_a, Float::parseFloat, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidFloat, ☃);
      }
   }

   @FunctionalInterface
   public interface IBoundFactory<T extends Number, R extends MinMaxBounds<T>> {
      R create(@Nullable T var1, @Nullable T var2);
   }

   @FunctionalInterface
   public interface IBoundReader<T extends Number, R extends MinMaxBounds<T>> {
      R create(StringReader var1, @Nullable T var2, @Nullable T var3) throws CommandSyntaxException;
   }

   public static class IntBound extends MinMaxBounds<Integer> {
      public static final MinMaxBounds.IntBound field_211347_e = new MinMaxBounds.IntBound(null, null);
      private final Long field_211348_f;
      private final Long field_211349_g;

      private static MinMaxBounds.IntBound func_211338_a(StringReader var0, @Nullable Integer var1, @Nullable Integer var2) throws CommandSyntaxException {
         if (☃ != null && ☃ != null && ☃ > ☃) {
            throw field_196980_d.createWithContext(☃);
         } else {
            return new MinMaxBounds.IntBound(☃, ☃);
         }
      }

      @Nullable
      private static Long func_211343_a(@Nullable Integer var0) {
         return ☃ == null ? null : ☃.longValue() * ☃.longValue();
      }

      private IntBound(@Nullable Integer var1, @Nullable Integer var2) {
         super(☃, ☃);
         this.field_211348_f = func_211343_a(☃);
         this.field_211349_g = func_211343_a(☃);
      }

      public static MinMaxBounds.IntBound func_211345_a(int var0) {
         return new MinMaxBounds.IntBound(☃, ☃);
      }

      public static MinMaxBounds.IntBound func_211340_b(int var0) {
         return new MinMaxBounds.IntBound(☃, null);
      }

      public boolean func_211339_d(int var1) {
         if (this.field_192517_b != null && this.field_192517_b > ☃) {
            return false;
         } else {
            return this.field_192518_c == null || this.field_192518_c >= ☃;
         }
      }

      public static MinMaxBounds.IntBound func_211344_a(@Nullable JsonElement var0) {
         return func_211331_a(☃, field_211347_e, JsonUtils::func_151215_f, MinMaxBounds.IntBound::new);
      }

      public static MinMaxBounds.IntBound func_211342_a(StringReader var0) throws CommandSyntaxException {
         return func_211341_a(☃, var0x -> var0x);
      }

      public static MinMaxBounds.IntBound func_211341_a(StringReader var0, Function<Integer, Integer> var1) throws CommandSyntaxException {
         return func_211337_a(☃, MinMaxBounds.IntBound::func_211338_a, Integer::parseInt, CommandSyntaxException.BUILT_IN_EXCEPTIONS::readerInvalidInt, ☃);
      }
   }
}
