package net.minecraft.server.players;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.Objects;

public class UserBanList extends StoredUserList<GameProfile, UserBanListEntry> {
   public UserBanList(File var1) {
      super(â˜ƒ);
   }

   @Override
   protected StoredUserEntry<GameProfile> createEntry(JsonObject var1) {
      return new UserBanListEntry(â˜ƒ);
   }

   public boolean isBanned(GameProfile var1) {
      return this.contains(â˜ƒ);
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

   protected String getKeyForUser(GameProfile var1) {
      return â˜ƒ.getId().toString();
   }
}
