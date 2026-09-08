package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSetCarriedItemPacket implements Packet<ServerGamePacketListener> {
   private final int slot;

   public ServerboundSetCarriedItemPacket(int var1) {
      this.slot = â˜ƒ;
   }

   public ServerboundSetCarriedItemPacket(FriendlyByteBuf var1) {
      this.slot = â˜ƒ.readShort();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeShort(this.slot);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetCarriedItem(this);
   }

   public int getSlot() {
      return this.slot;
   }
}
