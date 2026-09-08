package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PendingInvitesList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<PendingInvite> pendingInvites = Lists.<PendingInvite>newArrayList();

   public static PendingInvitesList parse(String var0) {
      PendingInvitesList â˜ƒ = new PendingInvitesList();

      try {
         JsonParser â˜ƒx = new JsonParser();
         JsonObject â˜ƒxx = â˜ƒx.parse(â˜ƒ).getAsJsonObject();
         if (â˜ƒxx.get("invites").isJsonArray()) {
            Iterator<JsonElement> â˜ƒxxx = â˜ƒxx.get("invites").getAsJsonArray().iterator();

            while(â˜ƒxxx.hasNext()) {
               â˜ƒ.pendingInvites.add(PendingInvite.parse(((JsonElement)â˜ƒxxx.next()).getAsJsonObject()));
            }
         }
      } catch (Exception var5) {
         LOGGER.error("Could not parse PendingInvitesList: {}", var5.getMessage());
      }

      return â˜ƒ;
   }
}
