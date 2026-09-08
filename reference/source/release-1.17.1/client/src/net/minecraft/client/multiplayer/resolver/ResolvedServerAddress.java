package net.minecraft.client.multiplayer.resolver;

import java.net.InetSocketAddress;

public interface ResolvedServerAddress {
   String getHostName();

   String getHostIp();

   int getPort();

   InetSocketAddress asInetSocketAddress();

   static ResolvedServerAddress from(final InetSocketAddress var0) {
      return new ResolvedServerAddress() {
         @Override
         public String getHostName() {
            return â˜ƒ.getAddress().getHostName();
         }

         @Override
         public String getHostIp() {
            return â˜ƒ.getAddress().getHostAddress();
         }

         @Override
         public int getPort() {
            return â˜ƒ.getPort();
         }

         @Override
         public InetSocketAddress asInetSocketAddress() {
            return â˜ƒ;
         }
      };
   }
}
