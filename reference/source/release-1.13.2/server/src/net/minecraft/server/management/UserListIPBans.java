package net.minecraft.server.management;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class UserListIPBans extends UserList<String, UserListIPBansEntry> {
   public UserListIPBans(File var1) {
      super(☃);
   }

   @Override
   protected UserListEntry<String> func_152682_a(JsonObject var1) {
      return new UserListIPBansEntry(☃);
   }

   public boolean func_152708_a(SocketAddress var1) {
      String ☃ = this.func_152707_c(☃);
      return this.func_152692_d(☃);
   }

   public boolean func_199044_a(String var1) {
      return this.func_152692_d(☃);
   }

   public UserListIPBansEntry func_152709_b(SocketAddress var1) {
      String ☃ = this.func_152707_c(☃);
      return this.func_152683_b(☃);
   }

   private String func_152707_c(SocketAddress var1) {
      String ☃ = ☃.toString();
      if (☃.contains("/")) {
         ☃ = ☃.substring(☃.indexOf(47) + 1);
      }

      if (☃.contains(":")) {
         ☃ = ☃.substring(0, ☃.indexOf(58));
      }

      return ☃;
   }
}
