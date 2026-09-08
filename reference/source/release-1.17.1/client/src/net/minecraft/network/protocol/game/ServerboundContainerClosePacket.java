package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundContainerClosePacket implements Packet<ServerGamePacketListener> {
   private final int containerId;

   public ServerboundContainerClosePacket(int var1) {
      this.containerId = â˜ƒ;
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleContainerClose(this);
   }

   public ServerboundContainerClosePacket(FriendlyByteBuf var1) {
      this.containerId = â˜ƒ.readByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.containerId);
   }

   public int getContainerId() {
      return this.containerId;
   }
}
