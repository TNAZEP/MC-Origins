package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServerPlayerLists extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<RealmsServerPlayerList> servers;

   public static RealmsServerPlayerLists parse(String var0) {
      RealmsServerPlayerLists â˜ƒ = new RealmsServerPlayerLists();
      â˜ƒ.servers = Lists.<RealmsServerPlayerList>newArrayList();

      try {
         JsonParser â˜ƒx = new JsonParser();
         JsonObject â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject();
         if (â˜ƒxx.get("lists").isJsonArray()) {
            JsonArray â˜ƒxxx = â˜ƒxx.get("lists").getAsJsonArray();
            Iterator<JsonElement> â˜ƒxxxx = â˜ƒxxx.iterator();

            while(â˜ƒxxxx.hasNext()) {
               â˜ƒ.servers.add(RealmsServerPlayerList.parse(((JsonElement)â˜ƒxxxx.next()).getAsJsonObject()));
            }
         }
      } catch (Exception var6) {
         LOGGER.error("Could not parse RealmsServerPlayerLists: {}", var6.getMessage());
      }

      return â˜ƒ;
   }
}
