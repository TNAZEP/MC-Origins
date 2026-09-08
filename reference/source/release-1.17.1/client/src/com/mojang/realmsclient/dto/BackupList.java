package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupList extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public List<Backup> backups;

   public static BackupList parse(String var0) {
      JsonParser â˜ƒ = new JsonParser();
      BackupList â˜ƒx = new BackupList();
      â˜ƒx.backups = Lists.<Backup>newArrayList();

      try {
         JsonElement â˜ƒxx = â˜ƒ.parse(â˜ƒ).getAsJsonObject().get("backups");
         if (â˜ƒxx.isJsonArray()) {
            Iterator<JsonElement> â˜ƒxxx = â˜ƒxx.getAsJsonArray().iterator();

            while(â˜ƒxxx.hasNext()) {
               â˜ƒx.backups.add(Backup.parse((JsonElement)â˜ƒxxx.next()));
            }
         }
      } catch (Exception var5) {
         LOGGER.error("Could not parse BackupList: {}", var5.getMessage());
      }

      return â˜ƒx;
   }
}
