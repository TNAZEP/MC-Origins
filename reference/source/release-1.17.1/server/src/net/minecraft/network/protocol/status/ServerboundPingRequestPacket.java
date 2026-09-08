package net.minecraft.network.protocol.status;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPingRequestPacket implements Packet<ServerStatusPacketListener> {
   private final long time;

   public ServerboundPingRequestPacket(long var1) {
      this.time = â˜ƒ;
   }

   public ServerboundPingRequestPacket(FriendlyByteBuf var1) {
      this.time = â˜ƒ.readLong();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeLong(this.time);
   }

   public void handle(ServerStatusPacketListener var1) {
      â˜ƒ.handlePingRequest(this);
   }

   public long getTime() {
      return this.time;
   }
}
