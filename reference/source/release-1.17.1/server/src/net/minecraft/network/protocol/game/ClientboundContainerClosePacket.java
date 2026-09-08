package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundContainerClosePacket implements Packet<ClientGamePacketListener> {
   private final int containerId;

   public ClientboundContainerClosePacket(int var1) {
      this.containerId = â˜ƒ;
   }

   public ClientboundContainerClosePacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readUnsignedByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleContainerClose(this);
   }

   public int getContainerId() {
      return this.containerId;
   }
}
