package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;

public class UserWhiteListEntry extends StoredUserEntry<GameProfile> {
   public UserWhiteListEntry(GameProfile var1) {
      super(â˜ƒ);
   }

   public UserWhiteListEntry(JsonObject var1) {
      super(createGameProfile(â˜ƒ));
   }

   @Override
   protected void serialize(JsonObject var1) {
      if (this.getUser() != null) {
         â˜ƒ.addProperty("uuid", this.getUser().getId() == null ? "" : this.getUser().getId().toString());
         â˜ƒ.addProperty("name", this.getUser().getName());
      }
   }

   private static GameProfile createGameProfile(JsonObject var0) {
      if (â˜ƒ.has("uuid") && â˜ƒ.has("name")) {
         String â˜ƒ = â˜ƒ.get("uuid").getAsString();

         UUID â˜ƒ;
         try {
            â˜ƒ = UUID.fromString(â˜ƒ);
         } catch (Throwable var4) {
            return null;
         }

         return new GameProfile(â˜ƒ, â˜ƒ.get("name").getAsString());
      } else {
         return null;
      }
   }
}
