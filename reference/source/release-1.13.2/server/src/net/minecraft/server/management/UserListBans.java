package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListBans extends UserList<GameProfile, UserListBansEntry> {
   public UserListBans(File var1) {
      super(☃);
   }

   @Override
   protected UserListEntry<GameProfile> func_152682_a(JsonObject var1) {
      return new UserListBansEntry(☃);
   }

   public boolean func_152702_a(GameProfile var1) {
      return this.func_152692_d(☃);
   }

   @Override
   public String[] func_152685_a() {
      String[] ☃ = new String[this.func_199043_f().size()];
      int ☃x = 0;

      for(UserListEntry<GameProfile> ☃xx : this.func_199043_f()) {
         ☃[☃x++] = ☃xx.func_152640_f().getName();
      }

      return ☃;
   }

   protected String func_152681_a(GameProfile var1) {
      return ☃.getId().toString();
   }
}
