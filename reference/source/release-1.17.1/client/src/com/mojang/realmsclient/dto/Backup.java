package com.mojang.realmsclient.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Backup extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public String backupId;
   public Date lastModifiedDate;
   public long size;
   private boolean uploadedVersion;
   public Map<String, String> metadata = Maps.newHashMap();
   public Map<String, String> changeList = Maps.newHashMap();

   public static Backup parse(JsonElement var0) {
      JsonObject â˜ƒ = â˜ƒ.getAsJsonObject();
      Backup â˜ƒx = new Backup();

      try {
         â˜ƒx.backupId = JsonUtils.getStringOr("backupId", â˜ƒ, "");
         â˜ƒx.lastModifiedDate = JsonUtils.getDateOr("lastModifiedDate", â˜ƒ);
         â˜ƒx.size = JsonUtils.getLongOr("size", â˜ƒ, 0L);
         if (â˜ƒ.has("metadata")) {
            JsonObject â˜ƒxx = â˜ƒ.getAsJsonObject("metadata");

            for(Entry<String, JsonElement> â˜ƒxxx : â˜ƒxx.entrySet()) {
               if (!((JsonElement)â˜ƒxxx.getValue()).isJsonNull()) {
                  â˜ƒx.metadata.put(format((String)â˜ƒxxx.getKey()), ((JsonElement)â˜ƒxxx.getValue()).getAsString());
               }
            }
         }
      } catch (Exception var7) {
         LOGGER.error("Could not parse Backup: {}", var7.getMessage());
      }

      return â˜ƒx;
   }

   private static String format(String var0) {
      String[] â˜ƒ = â˜ƒ.split("_");
      StringBuilder â˜ƒx = new StringBuilder();

      for(String â˜ƒxx : â˜ƒ) {
         if (â˜ƒxx != null && â˜ƒxx.length() >= 1) {
            if ("of".equals(â˜ƒxx)) {
               â˜ƒx.append(â˜ƒxx).append(" ");
            } else {
               char â˜ƒxxx = Character.toUpperCase(â˜ƒxx.charAt(0));
               â˜ƒx.append(â˜ƒxxx).append(â˜ƒxx.substring(1)).append(" ");
            }
         }
      }

      return â˜ƒx.toString();
   }

   public boolean isUploadedVersion() {
      return this.uploadedVersion;
   }

   public void setUploadedVersion(boolean var1) {
      this.uploadedVersion = â˜ƒ;
   }
}
