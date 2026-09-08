package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSelectTradePacket implements Packet<ServerGamePacketListener> {
   private final int item;

   public ServerboundSelectTradePacket(int var1) {
      this.item = â˜ƒ;
   }

   public ServerboundSelectTradePacket(FriendlyByteBuf var1) {
      this.item = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.item);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSelectTrade(this);
   }

   public int getItem() {
      return this.item;
   }
}
