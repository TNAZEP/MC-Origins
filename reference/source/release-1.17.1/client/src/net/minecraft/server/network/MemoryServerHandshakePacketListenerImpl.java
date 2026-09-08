package net.minecraft.server.network;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
import net.minecraft.server.MinecraftServer;

public class MemoryServerHandshakePacketListenerImpl implements ServerHandshakePacketListener {
   private final MinecraftServer server;
   private final Connection connection;

   public MemoryServerHandshakePacketListenerImpl(MinecraftServer var1, Connection var2) {
      this.server = â˜ƒ;
      this.connection = â˜ƒ;
   }

   @Override
   public void handleIntention(ClientIntentionPacket var1) {
      this.connection.setProtocol(â˜ƒ.getIntention());
      this.connection.setListener(new ServerLoginPacketListenerImpl(this.server, this.connection));
   }

   @Override
   public void onDisconnect(Component var1) {
   }

   @Override
   public Connection getConnection() {
      return this.connection;
   }
}
