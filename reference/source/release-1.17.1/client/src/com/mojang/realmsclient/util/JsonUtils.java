package com.mojang.realmsclient.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;

public class JsonUtils {
   public static String getStringOr(String var0, JsonObject var1, String var2) {
      JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ.isJsonNull() ? â˜ƒ : â˜ƒ.getAsString();
      } else {
         return â˜ƒ;
      }
   }

   public static int getIntOr(String var0, JsonObject var1, int var2) {
      JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ.isJsonNull() ? â˜ƒ : â˜ƒ.getAsInt();
      } else {
         return â˜ƒ;
      }
   }

   public static long getLongOr(String var0, JsonObject var1, long var2) {
      JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ.isJsonNull() ? â˜ƒ : â˜ƒ.getAsLong();
      } else {
         return â˜ƒ;
      }
   }

   public static boolean getBooleanOr(String var0, JsonObject var1, boolean var2) {
      JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
      if (â˜ƒ != null) {
         return â˜ƒ.isJsonNull() ? â˜ƒ : â˜ƒ.getAsBoolean();
      } else {
         return â˜ƒ;
      }
   }

   public static Date getDateOr(String var0, JsonObject var1) {
      JsonElement â˜ƒ = â˜ƒ.get(â˜ƒ);
      return â˜ƒ != null ? new Date(Long.parseLong(â˜ƒ.getAsString())) : new Date();
   }
}
