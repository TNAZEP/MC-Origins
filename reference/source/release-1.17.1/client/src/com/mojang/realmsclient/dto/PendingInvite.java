package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PendingInvite extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String invitationId;
   public String worldName;
   public String worldOwnerName;
   public String worldOwnerUuid;
   public Date date;

   public static PendingInvite parse(JsonObject var0) {
      PendingInvite â˜ƒ = new PendingInvite();

      try {
         â˜ƒ.invitationId = JsonUtils.getStringOr("invitationId", â˜ƒ, "");
         â˜ƒ.worldName = JsonUtils.getStringOr("worldName", â˜ƒ, "");
         â˜ƒ.worldOwnerName = JsonUtils.getStringOr("worldOwnerName", â˜ƒ, "");
         â˜ƒ.worldOwnerUuid = JsonUtils.getStringOr("worldOwnerUuid", â˜ƒ, "");
         â˜ƒ.date = JsonUtils.getDateOr("date", â˜ƒ);
      } catch (Exception var3) {
         LOGGER.error("Could not parse PendingInvite: {}", var3.getMessage());
      }

      return â˜ƒ;
   }
}
