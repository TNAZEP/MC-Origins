package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

public class UserBanListEntry extends BanListEntry<GameProfile> {
   public UserBanListEntry(GameProfile var1) {
      this(â˜ƒ, null, null, null, null);
   }

   public UserBanListEntry(GameProfile var1, @Nullable Date var2, @Nullable String var3, @Nullable Date var4, @Nullable String var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public UserBanListEntry(JsonObject var1) {
      super(createGameProfile(â˜ƒ), â˜ƒ);
   }

   @Override
   protected void serialize(JsonObject var1) {
      if (this.getUser() != null) {
         â˜ƒ.addProperty("uuid", this.getUser().getId() == null ? "" : this.getUser().getId().toString());
         â˜ƒ.addProperty("name", this.getUser().getName());
         super.serialize(â˜ƒ);
      }
   }

   @Override
   public Component getDisplayName() {
      GameProfile â˜ƒ = this.getUser();
      return new TextComponent(â˜ƒ.getName() != null ? â˜ƒ.getName() : Objects.toString(â˜ƒ.getId(), "(Unknown)"));
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
