package com.mojang.realmsclient.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadInfo extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String DEFAULT_SCHEMA = "http://";
   private static final int DEFAULT_PORT = 8080;
   private static final Pattern URI_SCHEMA_PATTERN = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
   private final boolean worldClosed;
   @Nullable
   private final String token;
   private final URI uploadEndpoint;

   private UploadInfo(boolean var1, @Nullable String var2, URI var3) {
      this.worldClosed = â˜ƒ;
      this.token = â˜ƒ;
      this.uploadEndpoint = â˜ƒ;
   }

   @Nullable
   public static UploadInfo parse(String var0) {
      try {
         JsonParser â˜ƒ = new JsonParser();
         JsonObject â˜ƒx = â˜ƒ.parse(â˜ƒ).getAsJsonObject();
         String â˜ƒxx = JsonUtils.getStringOr("uploadEndpoint", â˜ƒx, null);
         if (â˜ƒxx != null) {
            int â˜ƒxxx = JsonUtils.getIntOr("port", â˜ƒx, -1);
            URI â˜ƒxxxx = assembleUri(â˜ƒxx, â˜ƒxxx);
            if (â˜ƒxxxx != null) {
               boolean â˜ƒxxxxx = JsonUtils.getBooleanOr("worldClosed", â˜ƒx, false);
               String â˜ƒxxxxxx = JsonUtils.getStringOr("token", â˜ƒx, null);
               return new UploadInfo(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxx);
            }
         }
      } catch (Exception var8) {
         LOGGER.error("Could not parse UploadInfo: {}", var8.getMessage());
      }

      return null;
   }

   @Nullable
   @VisibleForTesting
   public static URI assembleUri(String var0, int var1) {
      Matcher â˜ƒ = URI_SCHEMA_PATTERN.matcher(â˜ƒ);
      String â˜ƒx = ensureEndpointSchema(â˜ƒ, â˜ƒ);

      try {
         URI â˜ƒxx = new URI(â˜ƒx);
         int â˜ƒxxx = selectPortOrDefault(â˜ƒ, â˜ƒxx.getPort());
         return â˜ƒxxx != â˜ƒxx.getPort()
            ? new URI(â˜ƒxx.getScheme(), â˜ƒxx.getUserInfo(), â˜ƒxx.getHost(), â˜ƒxxx, â˜ƒxx.getPath(), â˜ƒxx.getQuery(), â˜ƒxx.getFragment())
            : â˜ƒxx;
      } catch (URISyntaxException var6) {
         LOGGER.warn("Failed to parse URI {}", â˜ƒx, var6);
         return null;
      }
   }

   private static int selectPortOrDefault(int var0, int var1) {
      if (â˜ƒ != -1) {
         return â˜ƒ;
      } else {
         return â˜ƒ != -1 ? â˜ƒ : 8080;
      }
   }

   private static String ensureEndpointSchema(String var0, Matcher var1) {
      return â˜ƒ.find() ? â˜ƒ : "http://" + â˜ƒ;
   }

   public static String createRequest(@Nullable String var0) {
      JsonObject â˜ƒ = new JsonObject();
      if (â˜ƒ != null) {
         â˜ƒ.addProperty("token", â˜ƒ);
      }

      return â˜ƒ.toString();
   }

   @Nullable
   public String getToken() {
      return this.token;
   }

   public URI getUploadEndpoint() {
      return this.uploadEndpoint;
   }

   public boolean isWorldClosed() {
      return this.worldClosed;
   }
}
