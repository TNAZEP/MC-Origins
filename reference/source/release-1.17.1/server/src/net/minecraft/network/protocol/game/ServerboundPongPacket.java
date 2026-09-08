package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundPongPacket implements Packet<ServerGamePacketListener> {
   private final int id;

   public ServerboundPongPacket(int var1) {
      this.id = â˜ƒ;
   }

   public ServerboundPongPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.id);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handlePong(this);
   }

   public int getId() {
      return this.id;
   }
}
