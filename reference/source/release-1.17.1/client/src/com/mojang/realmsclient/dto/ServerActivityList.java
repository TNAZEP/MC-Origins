package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.List;

public class ServerActivityList extends ValueObject {
   public long periodInMillis;
   public List<ServerActivity> serverActivities = Lists.<ServerActivity>newArrayList();

   public static ServerActivityList parse(String var0) {
      ServerActivityList â˜ƒ = new ServerActivityList();
      JsonParser â˜ƒx = new JsonParser();

      try {
         JsonElement â˜ƒxx = â˜ƒx.parse(â˜ƒ);
         JsonObject â˜ƒxxx = â˜ƒxx.getAsJsonObject();
         â˜ƒ.periodInMillis = JsonUtils.getLongOr("periodInMillis", â˜ƒxxx, -1L);
         JsonElement â˜ƒxxxx = â˜ƒxxx.get("playerActivityDto");
         if (â˜ƒxxxx != null && â˜ƒxxxx.isJsonArray()) {
            for(JsonElement â˜ƒxxxxx : â˜ƒxxxx.getAsJsonArray()) {
               ServerActivity â˜ƒxxxxxx = ServerActivity.parse(â˜ƒxxxxx.getAsJsonObject());
               â˜ƒ.serverActivities.add(â˜ƒxxxxxx);
            }
         }
      } catch (Exception var10) {
      }

      return â˜ƒ;
   }
}
