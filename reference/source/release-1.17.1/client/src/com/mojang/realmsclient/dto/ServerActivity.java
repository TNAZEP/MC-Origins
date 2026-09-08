package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;

public class ServerActivity extends ValueObject {
   public String profileUuid;
   public long joinTime;
   public long leaveTime;

   public static ServerActivity parse(JsonObject var0) {
      ServerActivity â˜ƒ = new ServerActivity();

      try {
         â˜ƒ.profileUuid = JsonUtils.getStringOr("profileUuid", â˜ƒ, null);
         â˜ƒ.joinTime = JsonUtils.getLongOr("joinTime", â˜ƒ, Long.MIN_VALUE);
         â˜ƒ.leaveTime = JsonUtils.getLongOr("leaveTime", â˜ƒ, Long.MIN_VALUE);
      } catch (Exception var3) {
      }

      return â˜ƒ;
   }
}
