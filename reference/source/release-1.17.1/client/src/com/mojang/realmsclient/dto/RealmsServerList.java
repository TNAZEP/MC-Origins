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

public class RealmsServerList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<RealmsServer> servers;

   public static RealmsServerList parse(String var0) {
      RealmsServerList â˜ƒ = new RealmsServerList();
      â˜ƒ.servers = Lists.<RealmsServer>newArrayList();

      try {
         JsonParser â˜ƒx = new JsonParser();
         JsonObject â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject();
         if (â˜ƒxx.get("servers").isJsonArray()) {
            JsonArray â˜ƒxxx = â˜ƒxx.get("servers").getAsJsonArray();
            Iterator<JsonElement> â˜ƒxxxx = â˜ƒxxx.iterator();

            while(â˜ƒxxxx.hasNext()) {
               â˜ƒ.servers.add(RealmsServer.parse(((JsonElement)â˜ƒxxxx.next()).getAsJsonObject()));
            }
         }
      } catch (Exception var6) {
         LOGGER.error("Could not parse McoServerList: {}", var6.getMessage());
      }

      return â˜ƒ;
   }
}
