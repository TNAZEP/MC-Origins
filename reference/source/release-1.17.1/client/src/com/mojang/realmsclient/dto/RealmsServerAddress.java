package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerAddress extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String address;
   public String resourcePackUrl;
   public String resourcePackHash;

   public static RealmsServerAddress parse(String var0) {
      JsonParser â˜ƒ = new JsonParser();
      RealmsServerAddress â˜ƒx = new RealmsServerAddress();

      try {
         JsonObject â˜ƒxx = â˜ƒ.parse(â˜ƒ).getAsJsonObject();
         â˜ƒx.address = JsonUtils.getStringOr("address", â˜ƒxx, null);
         â˜ƒx.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", â˜ƒxx, null);
         â˜ƒx.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", â˜ƒxx, null);
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsServerAddress: {}", var4.getMessage());
      }

      return â˜ƒx;
   }
}
