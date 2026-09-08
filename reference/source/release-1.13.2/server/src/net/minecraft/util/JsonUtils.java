package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.util.registry.IRegistry;

public class JsonUtils {
   private static final Gson field_212747_a = new GsonBuilder().create();

   public static boolean func_151205_a(JsonObject var0, String var1) {
      return !func_151201_f(☃, ☃) ? false : ☃.getAsJsonPrimitive(☃).isString();
   }

   public static boolean func_188175_b(JsonElement var0) {
      return !☃.isJsonPrimitive() ? false : ☃.getAsJsonPrimitive().isNumber();
   }

   public static boolean func_151202_d(JsonObject var0, String var1) {
      return !func_151204_g(☃, ☃) ? false : ☃.get(☃).isJsonArray();
   }

   public static boolean func_151201_f(JsonObject var0, String var1) {
      return !func_151204_g(☃, ☃) ? false : ☃.get(☃).isJsonPrimitive();
   }

   public static boolean func_151204_g(JsonObject var0, String var1) {
      if (☃ == null) {
         return false;
      } else {
         return ☃.get(☃) != null;
      }
   }

   public static String func_151206_a(JsonElement var0, String var1) {
      if (☃.isJsonPrimitive()) {
         return ☃.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a string, was " + func_151222_d(☃));
      }
   }

   public static String func_151200_h(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_151206_a(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a string");
      }
   }

   public static String func_151219_a(JsonObject var0, String var1, String var2) {
      return ☃.has(☃) ? func_151206_a(☃.get(☃), ☃) : ☃;
   }

   public static Item func_188172_b(JsonElement var0, String var1) {
      if (☃.isJsonPrimitive()) {
         String ☃ = ☃.getAsString();
         Item ☃x = IRegistry.field_212630_s.func_212608_b(new ResourceLocation(☃));
         if (☃x == null) {
            throw new JsonSyntaxException("Expected " + ☃ + " to be an item, was unknown string '" + ☃ + "'");
         } else {
            return ☃x;
         }
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be an item, was " + func_151222_d(☃));
      }
   }

   public static Item func_188180_i(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_188172_b(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find an item");
      }
   }

   public static boolean func_151216_b(JsonElement var0, String var1) {
      if (☃.isJsonPrimitive()) {
         return ☃.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a Boolean, was " + func_151222_d(☃));
      }
   }

   public static boolean func_151212_i(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_151216_b(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a Boolean");
      }
   }

   public static boolean func_151209_a(JsonObject var0, String var1, boolean var2) {
      return ☃.has(☃) ? func_151216_b(☃.get(☃), ☃) : ☃;
   }

   public static float func_151220_d(JsonElement var0, String var1) {
      if (☃.isJsonPrimitive() && ☃.getAsJsonPrimitive().isNumber()) {
         return ☃.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a Float, was " + func_151222_d(☃));
      }
   }

   public static float func_151217_k(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_151220_d(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a Float");
      }
   }

   public static float func_151221_a(JsonObject var0, String var1, float var2) {
      return ☃.has(☃) ? func_151220_d(☃.get(☃), ☃) : ☃;
   }

   public static int func_151215_f(JsonElement var0, String var1) {
      if (☃.isJsonPrimitive() && ☃.getAsJsonPrimitive().isNumber()) {
         return ☃.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a Int, was " + func_151222_d(☃));
      }
   }

   public static int func_151203_m(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_151215_f(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a Int");
      }
   }

   public static int func_151208_a(JsonObject var0, String var1, int var2) {
      return ☃.has(☃) ? func_151215_f(☃.get(☃), ☃) : ☃;
   }

   public static byte func_204332_h(JsonElement var0, String var1) {
      if (☃.isJsonPrimitive() && ☃.getAsJsonPrimitive().isNumber()) {
         return ☃.getAsByte();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a Byte, was " + func_151222_d(☃));
      }
   }

   public static byte func_204331_o(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_204332_h(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a Byte");
      }
   }

   public static JsonObject func_151210_l(JsonElement var0, String var1) {
      if (☃.isJsonObject()) {
         return ☃.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a JsonObject, was " + func_151222_d(☃));
      }
   }

   public static JsonObject func_152754_s(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_151210_l(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a JsonObject");
      }
   }

   public static JsonObject func_151218_a(JsonObject var0, String var1, JsonObject var2) {
      return ☃.has(☃) ? func_151210_l(☃.get(☃), ☃) : ☃;
   }

   public static JsonArray func_151207_m(JsonElement var0, String var1) {
      if (☃.isJsonArray()) {
         return ☃.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + ☃ + " to be a JsonArray, was " + func_151222_d(☃));
      }
   }

   public static JsonArray func_151214_t(JsonObject var0, String var1) {
      if (☃.has(☃)) {
         return func_151207_m(☃.get(☃), ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃ + ", expected to find a JsonArray");
      }
   }

   public static JsonArray func_151213_a(JsonObject var0, String var1, @Nullable JsonArray var2) {
      return ☃.has(☃) ? func_151207_m(☃.get(☃), ☃) : ☃;
   }

   public static <T> T func_188179_a(@Nullable JsonElement var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (☃ != null) {
         return ☃.deserialize(☃, ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃);
      }
   }

   public static <T> T func_188174_a(JsonObject var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (☃.has(☃)) {
         return func_188179_a(☃.get(☃), ☃, ☃, ☃);
      } else {
         throw new JsonSyntaxException("Missing " + ☃);
      }
   }

   public static <T> T func_188177_a(JsonObject var0, String var1, T var2, JsonDeserializationContext var3, Class<? extends T> var4) {
      return (T)(☃.has(☃) ? func_188179_a(☃.get(☃), ☃, ☃, ☃) : ☃);
   }

   public static String func_151222_d(JsonElement var0) {
      String ☃ = org.apache.commons.lang3.StringUtils.abbreviateMiddle(String.valueOf(☃), "...", 10);
      if (☃ == null) {
         return "null (missing)";
      } else if (☃.isJsonNull()) {
         return "null (json)";
      } else if (☃.isJsonArray()) {
         return "an array (" + ☃ + ")";
      } else if (☃.isJsonObject()) {
         return "an object (" + ☃ + ")";
      } else {
         if (☃.isJsonPrimitive()) {
            JsonPrimitive ☃ = ☃.getAsJsonPrimitive();
            if (☃.isNumber()) {
               return "a number (" + ☃ + ")";
            }

            if (☃.isBoolean()) {
               return "a boolean (" + ☃ + ")";
            }
         }

         return ☃;
      }
   }

   @Nullable
   public static <T> T func_188173_a(Gson var0, Reader var1, Class<T> var2, boolean var3) {
      try {
         JsonReader ☃ = new JsonReader(☃);
         ☃.setLenient(☃);
         return ☃.<T>getAdapter(☃).read(☃);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T func_193838_a(Gson var0, Reader var1, Type var2, boolean var3) {
      try {
         JsonReader ☃ = new JsonReader(☃);
         ☃.setLenient(☃);
         return ☃.getAdapter(TypeToken.get(☃)).read(☃);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T func_188176_a(Gson var0, String var1, Class<T> var2, boolean var3) {
      return func_188173_a(☃, new StringReader(☃), ☃, ☃);
   }

   @Nullable
   public static <T> T func_193841_a(Gson var0, Reader var1, Type var2) {
      return func_193838_a(☃, ☃, ☃, false);
   }

   @Nullable
   public static <T> T func_188178_a(Gson var0, String var1, Class<T> var2) {
      return func_188176_a(☃, ☃, ☃, false);
   }

   public static JsonObject func_212746_a(String var0, boolean var1) {
      return func_212744_a(new StringReader(☃), ☃);
   }

   public static JsonObject func_212744_a(Reader var0, boolean var1) {
      return func_188173_a(field_212747_a, ☃, JsonObject.class, ☃);
   }

   public static JsonObject func_212745_a(String var0) {
      return func_212746_a(☃, false);
   }

   public static JsonObject func_212743_a(Reader var0) {
      return func_212744_a(☃, false);
   }
}
