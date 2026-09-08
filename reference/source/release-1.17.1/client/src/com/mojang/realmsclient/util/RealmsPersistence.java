package com.mojang.realmsclient.util;

import com.google.gson.annotations.SerializedName;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.ReflectionBasedSerialization;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

public class RealmsPersistence {
   private static final String FILE_NAME = "realms_persistence.json";
   private static final GuardedSerializer GSON = new GuardedSerializer();

   public RealmsPersistence.RealmsPersistenceData read() {
      return readFile();
   }

   public void save(RealmsPersistence.RealmsPersistenceData var1) {
      writeFile(â˜ƒ);
   }

   public static RealmsPersistence.RealmsPersistenceData readFile() {
      File â˜ƒ = getPathToData();

      try {
         String â˜ƒx = FileUtils.readFileToString(â˜ƒ, StandardCharsets.UTF_8);
         RealmsPersistence.RealmsPersistenceData â˜ƒxx = GSON.fromJson(â˜ƒx, RealmsPersistence.RealmsPersistenceData.class);
         return â˜ƒxx != null ? â˜ƒxx : new RealmsPersistence.RealmsPersistenceData();
      } catch (IOException var3) {
         return new RealmsPersistence.RealmsPersistenceData();
      }
   }

   public static void writeFile(RealmsPersistence.RealmsPersistenceData var0) {
      File â˜ƒ = getPathToData();

      try {
         FileUtils.writeStringToFile(â˜ƒ, GSON.toJson(â˜ƒ), StandardCharsets.UTF_8);
      } catch (IOException var3) {
      }
   }

   private static File getPathToData() {
      return new File(Minecraft.getInstance().gameDirectory, "realms_persistence.json");
   }

   public static class RealmsPersistenceData implements ReflectionBasedSerialization {
      @SerializedName("newsLink")
      public String newsLink;
      @SerializedName("hasUnreadNews")
      public boolean hasUnreadNews;
   }
}
