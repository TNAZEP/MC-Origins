package net.minecraft.network.protocol.status;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundPongResponsePacket implements Packet<ClientStatusPacketListener> {
   private final long time;

   public ClientboundPongResponsePacket(long var1) {
      this.time = â˜ƒ;
   }

   public ClientboundPongResponsePacket(FriendlyByteBuf var1) {
      this.time = â˜ƒ.readLong();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeLong(this.time);
   }

   public void handle(ClientStatusPacketListener var1) {
      â˜ƒ.handlePongResponse(this);
   }

   public long getTime() {
      return this.time;
   }
}
