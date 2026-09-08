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
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.StringUtils;

public class GsonHelper {
   private static final Gson GSON = new GsonBuilder().create();

   public static boolean isStringValue(JsonObject var0, String var1) {
      return !isValidPrimitive(â˜ƒ, â˜ƒ) ? false : â˜ƒ.getAsJsonPrimitive(â˜ƒ).isString();
   }

   public static boolean isStringValue(JsonElement var0) {
      return !â˜ƒ.isJsonPrimitive() ? false : â˜ƒ.getAsJsonPrimitive().isString();
   }

   public static boolean isNumberValue(JsonObject var0, String var1) {
      return !isValidPrimitive(â˜ƒ, â˜ƒ) ? false : â˜ƒ.getAsJsonPrimitive(â˜ƒ).isNumber();
   }

   public static boolean isNumberValue(JsonElement var0) {
      return !â˜ƒ.isJsonPrimitive() ? false : â˜ƒ.getAsJsonPrimitive().isNumber();
   }

   public static boolean isBooleanValue(JsonObject var0, String var1) {
      return !isValidPrimitive(â˜ƒ, â˜ƒ) ? false : â˜ƒ.getAsJsonPrimitive(â˜ƒ).isBoolean();
   }

   public static boolean isBooleanValue(JsonElement var0) {
      return !â˜ƒ.isJsonPrimitive() ? false : â˜ƒ.getAsJsonPrimitive().isBoolean();
   }

   public static boolean isArrayNode(JsonObject var0, String var1) {
      return !isValidNode(â˜ƒ, â˜ƒ) ? false : â˜ƒ.get(â˜ƒ).isJsonArray();
   }

   public static boolean isObjectNode(JsonObject var0, String var1) {
      return !isValidNode(â˜ƒ, â˜ƒ) ? false : â˜ƒ.get(â˜ƒ).isJsonObject();
   }

   public static boolean isValidPrimitive(JsonObject var0, String var1) {
      return !isValidNode(â˜ƒ, â˜ƒ) ? false : â˜ƒ.get(â˜ƒ).isJsonPrimitive();
   }

   public static boolean isValidNode(JsonObject var0, String var1) {
      if (â˜ƒ == null) {
         return false;
      } else {
         return â˜ƒ.get(â˜ƒ) != null;
      }
   }

   public static String convertToString(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive()) {
         return â˜ƒ.getAsString();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a string, was " + getType(â˜ƒ));
      }
   }

   public static String getAsString(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToString(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a string");
      }
   }

   public static String getAsString(JsonObject var0, String var1, String var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToString(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static Item convertToItem(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive()) {
         String â˜ƒ = â˜ƒ.getAsString();
         return (Item)Registry.ITEM
            .getOptional(new ResourceLocation(â˜ƒ))
            .orElseThrow(() -> new JsonSyntaxException("Expected " + â˜ƒ + " to be an item, was unknown string '" + â˜ƒ + "'"));
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be an item, was " + getType(â˜ƒ));
      }
   }

   public static Item getAsItem(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToItem(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find an item");
      }
   }

   public static Item getAsItem(JsonObject var0, String var1, Item var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToItem(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static boolean convertToBoolean(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive()) {
         return â˜ƒ.getAsBoolean();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Boolean, was " + getType(â˜ƒ));
      }
   }

   public static boolean getAsBoolean(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToBoolean(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Boolean");
      }
   }

   public static boolean getAsBoolean(JsonObject var0, String var1, boolean var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToBoolean(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static double convertToDouble(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsDouble();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Double, was " + getType(â˜ƒ));
      }
   }

   public static double getAsDouble(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToDouble(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Double");
      }
   }

   public static double getAsDouble(JsonObject var0, String var1, double var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToDouble(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static float convertToFloat(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsFloat();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Float, was " + getType(â˜ƒ));
      }
   }

   public static float getAsFloat(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToFloat(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Float");
      }
   }

   public static float getAsFloat(JsonObject var0, String var1, float var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToFloat(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static long convertToLong(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsLong();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Long, was " + getType(â˜ƒ));
      }
   }

   public static long getAsLong(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToLong(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Long");
      }
   }

   public static long getAsLong(JsonObject var0, String var1, long var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToLong(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static int convertToInt(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsInt();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Int, was " + getType(â˜ƒ));
      }
   }

   public static int getAsInt(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToInt(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Int");
      }
   }

   public static int getAsInt(JsonObject var0, String var1, int var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToInt(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static byte convertToByte(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsByte();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Byte, was " + getType(â˜ƒ));
      }
   }

   public static byte getAsByte(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToByte(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Byte");
      }
   }

   public static byte getAsByte(JsonObject var0, String var1, byte var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToByte(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static char convertToCharacter(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsCharacter();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Character, was " + getType(â˜ƒ));
      }
   }

   public static char getAsCharacter(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToCharacter(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Character");
      }
   }

   public static char getAsCharacter(JsonObject var0, String var1, char var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToCharacter(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static BigDecimal convertToBigDecimal(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsBigDecimal();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a BigDecimal, was " + getType(â˜ƒ));
      }
   }

   public static BigDecimal getAsBigDecimal(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToBigDecimal(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a BigDecimal");
      }
   }

   public static BigDecimal getAsBigDecimal(JsonObject var0, String var1, BigDecimal var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToBigDecimal(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static BigInteger convertToBigInteger(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsBigInteger();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a BigInteger, was " + getType(â˜ƒ));
      }
   }

   public static BigInteger getAsBigInteger(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToBigInteger(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a BigInteger");
      }
   }

   public static BigInteger getAsBigInteger(JsonObject var0, String var1, BigInteger var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToBigInteger(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static short convertToShort(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonPrimitive() && â˜ƒ.getAsJsonPrimitive().isNumber()) {
         return â˜ƒ.getAsShort();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a Short, was " + getType(â˜ƒ));
      }
   }

   public static short getAsShort(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToShort(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a Short");
      }
   }

   public static short getAsShort(JsonObject var0, String var1, short var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToShort(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static JsonObject convertToJsonObject(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonObject()) {
         return â˜ƒ.getAsJsonObject();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a JsonObject, was " + getType(â˜ƒ));
      }
   }

   public static JsonObject getAsJsonObject(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToJsonObject(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a JsonObject");
      }
   }

   public static JsonObject getAsJsonObject(JsonObject var0, String var1, JsonObject var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToJsonObject(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static JsonArray convertToJsonArray(JsonElement var0, String var1) {
      if (â˜ƒ.isJsonArray()) {
         return â˜ƒ.getAsJsonArray();
      } else {
         throw new JsonSyntaxException("Expected " + â˜ƒ + " to be a JsonArray, was " + getType(â˜ƒ));
      }
   }

   public static JsonArray getAsJsonArray(JsonObject var0, String var1) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToJsonArray(â˜ƒ.get(â˜ƒ), â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ + ", expected to find a JsonArray");
      }
   }

   @Nullable
   public static JsonArray getAsJsonArray(JsonObject var0, String var1, @Nullable JsonArray var2) {
      return â˜ƒ.has(â˜ƒ) ? convertToJsonArray(â˜ƒ.get(â˜ƒ), â˜ƒ) : â˜ƒ;
   }

   public static <T> T convertToObject(@Nullable JsonElement var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (â˜ƒ != null) {
         return â˜ƒ.deserialize(â˜ƒ, â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ);
      }
   }

   public static <T> T getAsObject(JsonObject var0, String var1, JsonDeserializationContext var2, Class<? extends T> var3) {
      if (â˜ƒ.has(â˜ƒ)) {
         return convertToObject(â˜ƒ.get(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         throw new JsonSyntaxException("Missing " + â˜ƒ);
      }
   }

   public static <T> T getAsObject(JsonObject var0, String var1, T var2, JsonDeserializationContext var3, Class<? extends T> var4) {
      return (T)(â˜ƒ.has(â˜ƒ) ? convertToObject(â˜ƒ.get(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ) : â˜ƒ);
   }

   public static String getType(JsonElement var0) {
      String â˜ƒ = StringUtils.abbreviateMiddle(String.valueOf(â˜ƒ), "...", 10);
      if (â˜ƒ == null) {
         return "null (missing)";
      } else if (â˜ƒ.isJsonNull()) {
         return "null (json)";
      } else if (â˜ƒ.isJsonArray()) {
         return "an array (" + â˜ƒ + ")";
      } else if (â˜ƒ.isJsonObject()) {
         return "an object (" + â˜ƒ + ")";
      } else {
         if (â˜ƒ.isJsonPrimitive()) {
            JsonPrimitive â˜ƒ = â˜ƒ.getAsJsonPrimitive();
            if (â˜ƒ.isNumber()) {
               return "a number (" + â˜ƒ + ")";
            }

            if (â˜ƒ.isBoolean()) {
               return "a boolean (" + â˜ƒ + ")";
            }
         }

         return â˜ƒ;
      }
   }

   @Nullable
   public static <T> T fromJson(Gson var0, Reader var1, Class<T> var2, boolean var3) {
      try {
         JsonReader â˜ƒ = new JsonReader(â˜ƒ);
         â˜ƒ.setLenient(â˜ƒ);
         return â˜ƒ.<T>getAdapter(â˜ƒ).read(â˜ƒ);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T fromJson(Gson var0, Reader var1, TypeToken<T> var2, boolean var3) {
      try {
         JsonReader â˜ƒ = new JsonReader(â˜ƒ);
         â˜ƒ.setLenient(â˜ƒ);
         return â˜ƒ.getAdapter(â˜ƒ).read(â˜ƒ);
      } catch (IOException var5) {
         throw new JsonParseException(var5);
      }
   }

   @Nullable
   public static <T> T fromJson(Gson var0, String var1, TypeToken<T> var2, boolean var3) {
      return fromJson(â˜ƒ, new StringReader(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   @Nullable
   public static <T> T fromJson(Gson var0, String var1, Class<T> var2, boolean var3) {
      return fromJson(â˜ƒ, new StringReader(â˜ƒ), â˜ƒ, â˜ƒ);
   }

   @Nullable
   public static <T> T fromJson(Gson var0, Reader var1, TypeToken<T> var2) {
      return fromJson(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   @Nullable
   public static <T> T fromJson(Gson var0, String var1, TypeToken<T> var2) {
      return fromJson(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   @Nullable
   public static <T> T fromJson(Gson var0, Reader var1, Class<T> var2) {
      return fromJson(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   @Nullable
   public static <T> T fromJson(Gson var0, String var1, Class<T> var2) {
      return fromJson(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public static JsonObject parse(String var0, boolean var1) {
      return parse(new StringReader(â˜ƒ), â˜ƒ);
   }

   public static JsonObject parse(Reader var0, boolean var1) {
      return fromJson(GSON, â˜ƒ, JsonObject.class, â˜ƒ);
   }

   public static JsonObject parse(String var0) {
      return parse(â˜ƒ, false);
   }

   public static JsonObject parse(Reader var0) {
      return parse(â˜ƒ, false);
   }

   public static JsonArray parseArray(Reader var0) {
      return fromJson(GSON, â˜ƒ, JsonArray.class, false);
   }
}
