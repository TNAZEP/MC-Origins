package net.minecraft.network.protocol.status;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

public class ServerStatus {
   public static final int FAVICON_WIDTH = 64;
   public static final int FAVICON_HEIGHT = 64;
   private Component description;
   private ServerStatus.Players players;
   private ServerStatus.Version version;
   private String favicon;

   public Component getDescription() {
      return this.description;
   }

   public void setDescription(Component var1) {
      this.description = â˜ƒ;
   }

   public ServerStatus.Players getPlayers() {
      return this.players;
   }

   public void setPlayers(ServerStatus.Players var1) {
      this.players = â˜ƒ;
   }

   public ServerStatus.Version getVersion() {
      return this.version;
   }

   public void setVersion(ServerStatus.Version var1) {
      this.version = â˜ƒ;
   }

   public void setFavicon(String var1) {
      this.favicon = â˜ƒ;
   }

   public String getFavicon() {
      return this.favicon;
   }

   public static class Players {
      private final int maxPlayers;
      private final int numPlayers;
      private GameProfile[] sample;

      public Players(int var1, int var2) {
         this.maxPlayers = â˜ƒ;
         this.numPlayers = â˜ƒ;
      }

      public int getMaxPlayers() {
         return this.maxPlayers;
      }

      public int getNumPlayers() {
         return this.numPlayers;
      }

      public GameProfile[] getSample() {
         return this.sample;
      }

      public void setSample(GameProfile[] var1) {
         this.sample = â˜ƒ;
      }

      public static class Serializer implements JsonDeserializer<ServerStatus.Players>, JsonSerializer<ServerStatus.Players> {
         public ServerStatus.Players deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "players");
            ServerStatus.Players â˜ƒx = new ServerStatus.Players(GsonHelper.getAsInt(â˜ƒ, "max"), GsonHelper.getAsInt(â˜ƒ, "online"));
            if (GsonHelper.isArrayNode(â˜ƒ, "sample")) {
               JsonArray â˜ƒxx = GsonHelper.getAsJsonArray(â˜ƒ, "sample");
               if (â˜ƒxx.size() > 0) {
                  GameProfile[] â˜ƒxxx = new GameProfile[â˜ƒxx.size()];

                  for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxx.length; ++â˜ƒxxxx) {
                     JsonObject â˜ƒxxxxx = GsonHelper.convertToJsonObject(â˜ƒxx.get(â˜ƒxxxx), "player[" + â˜ƒxxxx + "]");
                     String â˜ƒxxxxxx = GsonHelper.getAsString(â˜ƒxxxxx, "id");
                     â˜ƒxxx[â˜ƒxxxx] = new GameProfile(UUID.fromString(â˜ƒxxxxxx), GsonHelper.getAsString(â˜ƒxxxxx, "name"));
                  }

                  â˜ƒx.setSample(â˜ƒxxx);
               }
            }

            return â˜ƒx;
         }

         public JsonElement serialize(ServerStatus.Players var1, Type var2, JsonSerializationContext var3) {
            JsonObject â˜ƒ = new JsonObject();
            â˜ƒ.addProperty("max", â˜ƒ.getMaxPlayers());
            â˜ƒ.addProperty("online", â˜ƒ.getNumPlayers());
            if (â˜ƒ.getSample() != null && â˜ƒ.getSample().length > 0) {
               JsonArray â˜ƒx = new JsonArray();

               for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒ.getSample().length; ++â˜ƒxx) {
                  JsonObject â˜ƒxxx = new JsonObject();
                  UUID â˜ƒxxxx = â˜ƒ.getSample()[â˜ƒxx].getId();
                  â˜ƒxxx.addProperty("id", â˜ƒxxxx == null ? "" : â˜ƒxxxx.toString());
                  â˜ƒxxx.addProperty("name", â˜ƒ.getSample()[â˜ƒxx].getName());
                  â˜ƒx.add(â˜ƒxxx);
               }

               â˜ƒ.add("sample", â˜ƒx);
            }

            return â˜ƒ;
         }
      }
   }

   public static class Serializer implements JsonDeserializer<ServerStatus>, JsonSerializer<ServerStatus> {
      public ServerStatus deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "status");
         ServerStatus â˜ƒx = new ServerStatus();
         if (â˜ƒ.has("description")) {
            â˜ƒx.setDescription(â˜ƒ.deserialize(â˜ƒ.get("description"), Component.class));
         }

         if (â˜ƒ.has("players")) {
            â˜ƒx.setPlayers(â˜ƒ.deserialize(â˜ƒ.get("players"), ServerStatus.Players.class));
         }

         if (â˜ƒ.has("version")) {
            â˜ƒx.setVersion(â˜ƒ.deserialize(â˜ƒ.get("version"), ServerStatus.Version.class));
         }

         if (â˜ƒ.has("favicon")) {
            â˜ƒx.setFavicon(GsonHelper.getAsString(â˜ƒ, "favicon"));
         }

         return â˜ƒx;
      }

      public JsonElement serialize(ServerStatus var1, Type var2, JsonSerializationContext var3) {
         JsonObject â˜ƒ = new JsonObject();
         if (â˜ƒ.getDescription() != null) {
            â˜ƒ.add("description", â˜ƒ.serialize(â˜ƒ.getDescription()));
         }

         if (â˜ƒ.getPlayers() != null) {
            â˜ƒ.add("players", â˜ƒ.serialize(â˜ƒ.getPlayers()));
         }

         if (â˜ƒ.getVersion() != null) {
            â˜ƒ.add("version", â˜ƒ.serialize(â˜ƒ.getVersion()));
         }

         if (â˜ƒ.getFavicon() != null) {
            â˜ƒ.addProperty("favicon", â˜ƒ.getFavicon());
         }

         return â˜ƒ;
      }
   }

   public static class Version {
      private final String name;
      private final int protocol;

      public Version(String var1, int var2) {
         this.name = â˜ƒ;
         this.protocol = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public int getProtocol() {
         return this.protocol;
      }

      public static class Serializer implements JsonDeserializer<ServerStatus.Version>, JsonSerializer<ServerStatus.Version> {
         public ServerStatus.Version deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject â˜ƒ = GsonHelper.convertToJsonObject(â˜ƒ, "version");
            return new ServerStatus.Version(GsonHelper.getAsString(â˜ƒ, "name"), GsonHelper.getAsInt(â˜ƒ, "protocol"));
         }

         public JsonElement serialize(ServerStatus.Version var1, Type var2, JsonSerializationContext var3) {
            JsonObject â˜ƒ = new JsonObject();
            â˜ƒ.addProperty("name", â˜ƒ.getName());
            â˜ƒ.addProperty("protocol", â˜ƒ.getProtocol());
            return â˜ƒ;
         }
      }
   }
}
