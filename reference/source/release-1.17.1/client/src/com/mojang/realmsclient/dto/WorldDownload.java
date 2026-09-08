package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldDownload extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String downloadLink;
   public String resourcePackUrl;
   public String resourcePackHash;

   public static WorldDownload parse(String var0) {
      JsonParser â˜ƒ = new JsonParser();
      JsonObject â˜ƒx = â˜ƒ.parse(â˜ƒ).getAsJsonObject();
      WorldDownload â˜ƒxx = new WorldDownload();

      try {
         â˜ƒxx.downloadLink = JsonUtils.getStringOr("downloadLink", â˜ƒx, "");
         â˜ƒxx.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", â˜ƒx, "");
         â˜ƒxx.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", â˜ƒx, "");
      } catch (Exception var5) {
         LOGGER.error("Could not parse WorldDownload: {}", var5.getMessage());
      }

      return â˜ƒxx;
   }
}
