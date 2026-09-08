package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundAcceptTeleportationPacket implements Packet<ServerGamePacketListener> {
   private final int id;

   public ServerboundAcceptTeleportationPacket(int var1) {
      this.id = â˜ƒ;
   }

   public ServerboundAcceptTeleportationPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleAcceptTeleportPacket(this);
   }

   public int getId() {
      return this.id;
   }
}
