package net.minecraft.network.protocol.handshake;

import net.minecraft.SharedConstants;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientIntentionPacket implements Packet<ServerHandshakePacketListener> {
   private static final int MAX_HOST_LENGTH = 255;
   private final int protocolVersion;
   private final String hostName;
   private final int port;
   private final ConnectionProtocol intention;

   public ClientIntentionPacket(String var1, int var2, ConnectionProtocol var3) {
      this.protocolVersion = SharedConstants.getCurrentVersion().getProtocolVersion();
      this.hostName = â˜ƒ;
      this.port = â˜ƒ;
      this.intention = â˜ƒ;
   }

   public ClientIntentionPacket(FriendlyByteBuf var1) {
      this.protocolVersion = â˜ƒ.readVarInt();
      this.hostName = â˜ƒ.readUtf(255);
      this.port = â˜ƒ.readUnsignedShort();
      this.intention = ConnectionProtocol.getById(â˜ƒ.readVarInt());
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.protocolVersion);
      â˜ƒ.writeUtf(this.hostName);
      â˜ƒ.writeShort(this.port);
      â˜ƒ.writeVarInt(this.intention.getId());
   }

   public void handle(ServerHandshakePacketListener var1) {
      â˜ƒ.handleIntention(this);
   }

   public ConnectionProtocol getIntention() {
      return this.intention;
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public String getHostName() {
      return this.hostName;
   }

   public int getPort() {
      return this.port;
   }
}
