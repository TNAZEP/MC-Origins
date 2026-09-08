package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundKeepAlivePacket implements Packet<ServerGamePacketListener> {
   private final long id;

   public ServerboundKeepAlivePacket(long var1) {
      this.id = â˜ƒ;
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleKeepAlive(this);
   }

   public ServerboundKeepAlivePacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readLong();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeLong(this.id);
   }

   public long getId() {
      return this.id;
   }
}
