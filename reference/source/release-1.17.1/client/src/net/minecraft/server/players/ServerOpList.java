package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Objects;

public class ServerOpList extends StoredUserList<GameProfile, ServerOpListEntry> {
   public ServerOpList(File var1) {
      super(â˜ƒ);
   }

   @Override
   protected StoredUserEntry<GameProfile> createEntry(JsonObject var1) {
      return new ServerOpListEntry(â˜ƒ);
   }

   @Override
   public String[] getUserList() {
      return (String[])this.getEntries()
         .stream()
         .map(StoredUserEntry::getUser)
         .filter(Objects::nonNull)
         .map(GameProfile::getName)
         .toArray(var0 -> new String[var0]);
   }

   public boolean canBypassPlayerLimit(GameProfile var1) {
      ServerOpListEntry â˜ƒ = this.get(â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.getBypassesPlayerLimit() : false;
   }

   protected String getKeyForUser(GameProfile var1) {
      return â˜ƒ.getId().toString();
   }
}
