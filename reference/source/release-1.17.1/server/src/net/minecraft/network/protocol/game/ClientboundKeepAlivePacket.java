package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundKeepAlivePacket implements Packet<ClientGamePacketListener> {
   private final long id;

   public ClientboundKeepAlivePacket(long var1) {
      this.id = â˜ƒ;
   }

   public ClientboundKeepAlivePacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readLong();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeLong(this.id);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleKeepAlive(this);
   }

   public long getId() {
      return this.id;
   }
}
