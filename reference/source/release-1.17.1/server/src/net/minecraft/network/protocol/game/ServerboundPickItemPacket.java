package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPickItemPacket implements Packet<ServerGamePacketListener> {
   private final int slot;

   public ServerboundPickItemPacket(int var1) {
      this.slot = â˜ƒ;
   }

   public ServerboundPickItemPacket(FriendlyByteBuf var1) {
      this.slot = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.slot);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePickItem(this);
   }

   public int getSlot() {
      return this.slot;
   }
}
