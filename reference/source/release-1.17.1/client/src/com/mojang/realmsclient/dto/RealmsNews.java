package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsNews extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String newsLink;

   public static RealmsNews parse(String var0) {
      RealmsNews â˜ƒ = new RealmsNews();

      try {
         JsonParser â˜ƒx = new JsonParser();
         JsonObject â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject();
         â˜ƒ.newsLink = JsonUtils.getStringOr("newsLink", â˜ƒxx, null);
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsNews: {}", var4.getMessage());
      }

      return â˜ƒ;
   }
}
