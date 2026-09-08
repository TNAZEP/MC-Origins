package net.minecraft.server.network;

import net.minecraft.SharedConstants;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.MinecraftServer;

public class ServerHandshakePacketListenerImpl implements ServerHandshakePacketListener {
   private static final Component IGNORE_STATUS_REASON = new TextComponent("Ignoring status request");
   private final MinecraftServer server;
   private final Connection connection;

   public ServerHandshakePacketListenerImpl(MinecraftServer var1, Connection var2) {
      this.server = â˜ƒ;
      this.connection = â˜ƒ;
   }

   @Override
   public void handleIntention(ClientIntentionPacket var1) {
      switch(â˜ƒ.getIntention()) {
         case LOGIN:
            this.connection.setProtocol(ConnectionProtocol.LOGIN);
            if (â˜ƒ.getProtocolVersion() != SharedConstants.getCurrentVersion().getProtocolVersion()) {
               Component â˜ƒ;
               if (â˜ƒ.getProtocolVersion() < 754) {
                  â˜ƒ = new TranslatableComponent("multiplayer.disconnect.outdated_client", SharedConstants.getCurrentVersion().getName());
               } else {
                  â˜ƒ = new TranslatableComponent("multiplayer.disconnect.incompatible", SharedConstants.getCurrentVersion().getName());
               }

               this.connection.send(new ClientboundLoginDisconnectPacket(â˜ƒ));
               this.connection.disconnect(â˜ƒ);
            } else {
               this.connection.setListener(new ServerLoginPacketListenerImpl(this.server, this.connection));
            }
            break;
         case STATUS:
            if (this.server.repliesToStatus()) {
               this.connection.setProtocol(ConnectionProtocol.STATUS);
               this.connection.setListener(new ServerStatusPacketListenerImpl(this.server, this.connection));
            } else {
               this.connection.disconnect(IGNORE_STATUS_REASON);
            }
            break;
         default:
            throw new UnsupportedOperationException("Invalid intention " + â˜ƒ.getIntention());
      }
   }

   @Override
   public void onDisconnect(Component var1) {
   }

   @Override
   public Connection getConnection() {
      return this.connection;
   }
}
