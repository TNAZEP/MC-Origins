package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundLoginCompressionPacket implements Packet<ClientLoginPacketListener> {
   private final int compressionThreshold;

   public ClientboundLoginCompressionPacket(int var1) {
      this.compressionThreshold = â˜ƒ;
   }

   public ClientboundLoginCompressionPacket(FriendlyByteBuf var1) {
      this.compressionThreshold = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.compressionThreshold);
   }

   public void handle(ClientLoginPacketListener var1) {
      â˜ƒ.handleCompression(this);
   }

   public int getCompressionThreshold() {
      return this.compressionThreshold;
   }
}
