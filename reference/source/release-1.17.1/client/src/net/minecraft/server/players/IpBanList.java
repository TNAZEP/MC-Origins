package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;

public class IpBanList extends StoredUserList<String, IpBanListEntry> {
   public IpBanList(File var1) {
      super(â˜ƒ);
   }

   @Override
   protected StoredUserEntry<String> createEntry(JsonObject var1) {
      return new IpBanListEntry(â˜ƒ);
   }

   public boolean isBanned(SocketAddress var1) {
      String â˜ƒ = this.getIpFromAddress(â˜ƒ);
      return this.contains(â˜ƒ);
   }

   public boolean isBanned(String var1) {
      return this.contains(â˜ƒ);
   }

   public IpBanListEntry get(SocketAddress var1) {
      String â˜ƒ = this.getIpFromAddress(â˜ƒ);
      return this.get(â˜ƒ);
   }

   private String getIpFromAddress(SocketAddress var1) {
      String â˜ƒ = â˜ƒ.toString();
      if (â˜ƒ.contains("/")) {
         â˜ƒ = â˜ƒ.substring(â˜ƒ.indexOf(47) + 1);
      }

      if (â˜ƒ.contains(":")) {
         â˜ƒ = â˜ƒ.substring(0, â˜ƒ.indexOf(58));
      }

      return â˜ƒ;
   }
}
