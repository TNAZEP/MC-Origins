package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldTemplate extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String id = "";
   public String name = "";
   public String version = "";
   public String author = "";
   public String link = "";
   @Nullable
   public String image;
   public String trailer = "";
   public String recommendedPlayers = "";
   public WorldTemplate.WorldTemplateType type = WorldTemplate.WorldTemplateType.WORLD_TEMPLATE;

   public static WorldTemplate parse(JsonObject var0) {
      WorldTemplate â˜ƒ = new WorldTemplate();

      try {
         â˜ƒ.id = JsonUtils.getStringOr("id", â˜ƒ, "");
         â˜ƒ.name = JsonUtils.getStringOr("name", â˜ƒ, "");
         â˜ƒ.version = JsonUtils.getStringOr("version", â˜ƒ, "");
         â˜ƒ.author = JsonUtils.getStringOr("author", â˜ƒ, "");
         â˜ƒ.link = JsonUtils.getStringOr("link", â˜ƒ, "");
         â˜ƒ.image = JsonUtils.getStringOr("image", â˜ƒ, null);
         â˜ƒ.trailer = JsonUtils.getStringOr("trailer", â˜ƒ, "");
         â˜ƒ.recommendedPlayers = JsonUtils.getStringOr("recommendedPlayers", â˜ƒ, "");
         â˜ƒ.type = WorldTemplate.WorldTemplateType.valueOf(JsonUtils.getStringOr("type", â˜ƒ, WorldTemplate.WorldTemplateType.WORLD_TEMPLATE.name()));
      } catch (Exception var3) {
         LOGGER.error("Could not parse WorldTemplate: {}", var3.getMessage());
      }

      return â˜ƒ;
   }

   public static enum WorldTemplateType {
      WORLD_TEMPLATE,
      MINIGAME,
      ADVENTUREMAP,
      EXPERIENCE,
      INSPIRATION;
   }
}
