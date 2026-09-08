package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import javax.annotation.Nullable;

public class ServerOpListEntry extends StoredUserEntry<GameProfile> {
   private final int level;
   private final boolean bypassesPlayerLimit;

   public ServerOpListEntry(GameProfile var1, int var2, boolean var3) {
      super(â˜ƒ);
      this.level = â˜ƒ;
      this.bypassesPlayerLimit = â˜ƒ;
   }

   public ServerOpListEntry(JsonObject var1) {
      super(createGameProfile(â˜ƒ));
      this.level = â˜ƒ.has("level") ? â˜ƒ.get("level").getAsInt() : 0;
      this.bypassesPlayerLimit = â˜ƒ.has("bypassesPlayerLimit") && â˜ƒ.get("bypassesPlayerLimit").getAsBoolean();
   }

   public int getLevel() {
      return this.level;
   }

   public boolean getBypassesPlayerLimit() {
      return this.bypassesPlayerLimit;
   }

   @Override
   protected void serialize(JsonObject var1) {
      if (this.getUser() != null) {
         â˜ƒ.addProperty("uuid", this.getUser().getId() == null ? "" : this.getUser().getId().toString());
         â˜ƒ.addProperty("name", this.getUser().getName());
         â˜ƒ.addProperty("level", this.level);
         â˜ƒ.addProperty("bypassesPlayerLimit", this.bypassesPlayerLimit);
      }
   }

   @Nullable
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
