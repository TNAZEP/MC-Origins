package com.mojang.realmsclient.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsError {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String errorMessage;
   private final int errorCode;

   private RealmsError(String var1, int var2) {
      this.errorMessage = â˜ƒ;
      this.errorCode = â˜ƒ;
   }

   public static RealmsError create(String var0) {
      try {
         JsonParser â˜ƒ = new JsonParser();
         JsonObject â˜ƒx = â˜ƒ.parse(â˜ƒ).getAsJsonObject();
         String â˜ƒxx = JsonUtils.getStringOr("errorMsg", â˜ƒx, "");
         int â˜ƒxxx = JsonUtils.getIntOr("errorCode", â˜ƒx, -1);
         return new RealmsError(â˜ƒxx, â˜ƒxxx);
      } catch (Exception var5) {
         LOGGER.error("Could not parse RealmsError: {}", var5.getMessage());
         LOGGER.error("The error was: {}", â˜ƒ);
         return new RealmsError("Failed to parse response from server", -1);
      }
   }

   public String getErrorMessage() {
      return this.errorMessage;
   }

   public int getErrorCode() {
      return this.errorCode;
   }
}
