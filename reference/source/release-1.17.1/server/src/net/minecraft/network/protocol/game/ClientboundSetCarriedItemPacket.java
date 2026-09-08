package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetCarriedItemPacket implements Packet<ClientGamePacketListener> {
   private final int slot;

   public ClientboundSetCarriedItemPacket(int var1) {
      this.slot = â˜ƒ;
   }

   public ClientboundSetCarriedItemPacket(FriendlyByteBuf var1) {
      this.slot = â˜ƒ.readByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.slot);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetCarriedItem(this);
   }

   public int getSlot() {
      return this.slot;
   }
}
