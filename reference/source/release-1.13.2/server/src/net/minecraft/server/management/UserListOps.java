package net.minecraft.server.management;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class UserListOps extends UserList<GameProfile, UserListOpsEntry> {
   public UserListOps(File var1) {
      super(☃);
   }

   @Override
   protected UserListEntry<GameProfile> func_152682_a(JsonObject var1) {
      return new UserListOpsEntry(☃);
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

   public boolean func_183026_b(GameProfile var1) {
      UserListOpsEntry ☃ = this.func_152683_b(☃);
      return ☃ != null ? ☃.func_183024_b() : false;
   }

   protected String func_152681_a(GameProfile var1) {
      return ☃.getId().toString();
   }
}
