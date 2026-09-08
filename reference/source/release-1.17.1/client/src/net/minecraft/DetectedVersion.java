package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.PackType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DetectedVersion implements GameVersion {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final GameVersion BUILT_IN = new DetectedVersion();
   private final String id;
   private final String name;
   private final boolean stable;
   private final int worldVersion;
   private final int protocolVersion;
   private final int resourcePackVersion;
   private final int dataPackVersion;
   private final Date buildTime;
   private final String releaseTarget;

   private DetectedVersion() {
      this.id = UUID.randomUUID().toString().replaceAll("-", "");
      this.name = "1.17.1";
      this.stable = true;
      this.worldVersion = 2730;
      this.protocolVersion = SharedConstants.getProtocolVersion();
      this.resourcePackVersion = 7;
      this.dataPackVersion = 7;
      this.buildTime = new Date();
      this.releaseTarget = "1.17.1";
   }

   private DetectedVersion(JsonObject var1) {
      this.id = GsonHelper.getAsString(â˜ƒ, "id");
      this.name = GsonHelper.getAsString(â˜ƒ, "name");
      this.releaseTarget = GsonHelper.getAsString(â˜ƒ, "release_target");
      this.stable = GsonHelper.getAsBoolean(â˜ƒ, "stable");
      this.worldVersion = GsonHelper.getAsInt(â˜ƒ, "world_version");
      this.protocolVersion = GsonHelper.getAsInt(â˜ƒ, "protocol_version");
      JsonObject â˜ƒ = GsonHelper.getAsJsonObject(â˜ƒ, "pack_version");
      this.resourcePackVersion = GsonHelper.getAsInt(â˜ƒ, "resource");
      this.dataPackVersion = GsonHelper.getAsInt(â˜ƒ, "data");
      this.buildTime = Date.from(ZonedDateTime.parse(GsonHelper.getAsString(â˜ƒ, "build_time")).toInstant());
   }

   public static GameVersion tryDetectVersion() {
      try {
         InputStream â˜ƒ = DetectedVersion.class.getResourceAsStream("/version.json");

         GameVersion var9;
         label63: {
            DetectedVersion var2;
            try {
               if (â˜ƒ == null) {
                  LOGGER.warn("Missing version information!");
                  var9 = BUILT_IN;
                  break label63;
               }

               InputStreamReader â˜ƒx = new InputStreamReader(â˜ƒ);

               try {
                  var2 = new DetectedVersion(GsonHelper.parse(â˜ƒx));
               } catch (Throwable var6) {
                  try {
                     â˜ƒx.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }

                  throw var6;
               }

               â˜ƒx.close();
            } catch (Throwable var7) {
               if (â˜ƒ != null) {
                  try {
                     â˜ƒ.close();
                  } catch (Throwable var4) {
                     var7.addSuppressed(var4);
                  }
               }

               throw var7;
            }

            if (â˜ƒ != null) {
               â˜ƒ.close();
            }

            return var2;
         }

         if (â˜ƒ != null) {
            â˜ƒ.close();
         }

         return var9;
      } catch (JsonParseException | IOException var8) {
         throw new IllegalStateException("Game version information is corrupt", var8);
      }
   }

   @Override
   public String getId() {
      return this.id;
   }

   @Override
   public String getName() {
      return this.name;
   }

   @Override
   public String getReleaseTarget() {
      return this.releaseTarget;
   }

   @Override
   public int getWorldVersion() {
      return this.worldVersion;
   }

   @Override
   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   @Override
   public int getPackVersion(PackType var1) {
      return â˜ƒ == PackType.DATA ? this.dataPackVersion : this.resourcePackVersion;
   }

   @Override
   public Date getBuildTime() {
      return this.buildTime;
   }

   @Override
   public boolean isStable() {
      return this.stable;
   }
}
