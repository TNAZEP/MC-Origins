package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundPingPacket implements Packet<ClientGamePacketListener> {
   private final int id;

   public ClientboundPingPacket(int var1) {
      this.id = â˜ƒ;
   }

   public ClientboundPingPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.id);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handlePing(this);
   }

   public int getId() {
      return this.id;
   }
}
