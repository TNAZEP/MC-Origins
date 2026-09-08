package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerPlayerList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final JsonParser JSON_PARSER = new JsonParser();
   public long serverId;
   public List<String> players;

   public static RealmsServerPlayerList parse(JsonObject var0) {
      RealmsServerPlayerList â˜ƒ = new RealmsServerPlayerList();

      try {
         â˜ƒ.serverId = JsonUtils.getLongOr("serverId", â˜ƒ, -1L);
         String â˜ƒx = JsonUtils.getStringOr("playerList", â˜ƒ, null);
         if (â˜ƒx != null) {
            JsonElement â˜ƒxx = JSON_PARSER.parse(â˜ƒx);
            if (â˜ƒxx.isJsonArray()) {
               â˜ƒ.players = parsePlayers(â˜ƒxx.getAsJsonArray());
            } else {
               â˜ƒ.players = Lists.newArrayList();
            }
         } else {
            â˜ƒ.players = Lists.newArrayList();
         }
      } catch (Exception var4) {
         LOGGER.error("Could not parse RealmsServerPlayerList: {}", var4.getMessage());
      }

      return â˜ƒ;
   }

   private static List<String> parsePlayers(JsonArray var0) {
      List<String> â˜ƒ = Lists.newArrayList();

      for(JsonElement â˜ƒx : â˜ƒ) {
         try {
            â˜ƒ.add(â˜ƒx.getAsString());
         } catch (Exception var5) {
         }
      }

      return â˜ƒ;
   }
}
