package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;

public class WrappedMinMaxBounds {
   public static final WrappedMinMaxBounds ANY = new WrappedMinMaxBounds(null, null);
   public static final SimpleCommandExceptionType ERROR_INTS_ONLY = new SimpleCommandExceptionType(new TranslatableComponent("argument.range.ints"));
   private final Float min;
   private final Float max;

   public WrappedMinMaxBounds(@Nullable Float var1, @Nullable Float var2) {
      this.min = â˜ƒ;
      this.max = â˜ƒ;
   }

   public static WrappedMinMaxBounds exactly(float var0) {
      return new WrappedMinMaxBounds(â˜ƒ, â˜ƒ);
   }

   public static WrappedMinMaxBounds between(float var0, float var1) {
      return new WrappedMinMaxBounds(â˜ƒ, â˜ƒ);
   }

   public static WrappedMinMaxBounds atLeast(float var0) {
      return new WrappedMinMaxBounds(â˜ƒ, null);
   }

   public static WrappedMinMaxBounds atMost(float var0) {
      return new WrappedMinMaxBounds(null, â˜ƒ);
   }

   public boolean matches(float var1) {
      if (this.min != null && this.max != null && this.min > this.max && this.min > â˜ƒ && this.max < â˜ƒ) {
         return false;
      } else if (this.min != null && this.min > â˜ƒ) {
         return false;
      } else {
         return this.max == null || !(this.max < â˜ƒ);
      }
   }

   public boolean matchesSqr(double var1) {
      if (this.min != null && this.max != null && this.min > this.max && (double)(this.min * this.min) > â˜ƒ && (double)(this.max * this.max) < â˜ƒ) {
         return false;
      } else if (this.min != null && (double)(this.min * this.min) > â˜ƒ) {
         return false;
      } else {
         return this.max == null || !((double)(this.max * this.max) < â˜ƒ);
      }
   }

   @Nullable
   public Float getMin() {
      return this.min;
   }

   @Nullable
   public Float getMax() {
      return this.max;
   }

   public JsonElement serializeToJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else if (this.min != null && this.max != null && this.min.equals(this.max)) {
         return new JsonPrimitive(this.min);
      } else {
         JsonObject â˜ƒ = new JsonObject();
         if (this.min != null) {
            â˜ƒ.addProperty("min", this.min);
         }

         if (this.max != null) {
            â˜ƒ.addProperty("max", this.min);
         }

         return â˜ƒ;
      }
   }

   public static WrappedMinMaxBounds fromJson(@Nullable JsonElement var0) {
      if (â˜ƒ == null || â˜ƒ.isJsonNull()) {
         return ANY;
      } else if (GsonHelper.isNumberValue(â˜ƒ)) {
         float â˜ƒ = GsonHelper.convertToFloat(â˜ƒ, "value");
         return new WrappedMinMaxBounds(â˜ƒ, â˜ƒ);
      } else {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "value");
         Float â˜ƒx = â˜ƒ.has("min") ? GsonHelper.getAsFloat(â˜ƒ, "min") : null;
         Float â˜ƒxx = â˜ƒ.has("max") ? GsonHelper.getAsFloat(â˜ƒ, "max") : null;
         return new WrappedMinMaxBounds(â˜ƒx, â˜ƒxx);
      }
   }

   public static WrappedMinMaxBounds fromReader(StringReader var0, boolean var1) throws CommandSyntaxException {
      return fromReader(â˜ƒ, â˜ƒ, var0x -> var0x);
   }

   public static WrappedMinMaxBounds fromReader(StringReader var0, boolean var1, Function<Float, Float> var2) throws CommandSyntaxException {
      if (!â˜ƒ.canRead()) {
         throw MinMaxBounds.ERROR_EMPTY.createWithContext(â˜ƒ);
      } else {
         int â˜ƒx = â˜ƒ.getCursor();
         Float â˜ƒxx = optionallyFormat(readNumber(â˜ƒ, â˜ƒ), â˜ƒ);
         Float â˜ƒ;
         if (â˜ƒ.canRead(2) && â˜ƒ.peek() == '.' && â˜ƒ.peek(1) == '.') {
            â˜ƒ.skip();
            â˜ƒ.skip();
            â˜ƒ = optionallyFormat(readNumber(â˜ƒ, â˜ƒ), â˜ƒ);
            if (â˜ƒxx == null && â˜ƒ == null) {
               â˜ƒ.setCursor(â˜ƒx);
               throw MinMaxBounds.ERROR_EMPTY.createWithContext(â˜ƒ);
            }
         } else {
            if (!â˜ƒ && â˜ƒ.canRead() && â˜ƒ.peek() == '.') {
               â˜ƒ.setCursor(â˜ƒx);
               throw ERROR_INTS_ONLY.createWithContext(â˜ƒ);
            }

            â˜ƒ = â˜ƒxx;
         }

         if (â˜ƒxx == null && â˜ƒ == null) {
            â˜ƒ.setCursor(â˜ƒx);
            throw MinMaxBounds.ERROR_EMPTY.createWithContext(â˜ƒ);
         } else {
            return new WrappedMinMaxBounds(â˜ƒxx, â˜ƒ);
         }
      }
   }

   @Nullable
   private static Float readNumber(StringReader var0, boolean var1) throws CommandSyntaxException {
      int â˜ƒ = â˜ƒ.getCursor();

      while(â˜ƒ.canRead() && isAllowedNumber(â˜ƒ, â˜ƒ)) {
         â˜ƒ.skip();
      }

      String â˜ƒx = â˜ƒ.getString().substring(â˜ƒ, â˜ƒ.getCursor());
      if (â˜ƒx.isEmpty()) {
         return null;
      } else {
         try {
            return Float.parseFloat(â˜ƒx);
         } catch (NumberFormatException var5) {
            if (â˜ƒ) {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(â˜ƒ, â˜ƒx);
            } else {
               throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(â˜ƒ, â˜ƒx);
            }
         }
      }
   }

   private static boolean isAllowedNumber(StringReader var0, boolean var1) {
      char â˜ƒ = â˜ƒ.peek();
      if ((â˜ƒ < '0' || â˜ƒ > '9') && â˜ƒ != '-') {
         if (â˜ƒ && â˜ƒ == '.') {
            return !â˜ƒ.canRead(2) || â˜ƒ.peek(1) != '.';
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   @Nullable
   private static Float optionallyFormat(@Nullable Float var0, Function<Float, Float> var1) {
      return â˜ƒ == null ? null : (Float)â˜ƒ.apply(â˜ƒ);
   }
}
