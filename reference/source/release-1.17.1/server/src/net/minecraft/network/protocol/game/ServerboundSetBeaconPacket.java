package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundSetBeaconPacket implements Packet<ServerGamePacketListener> {
   private final int primary;
   private final int secondary;

   public ServerboundSetBeaconPacket(int var1, int var2) {
      this.primary = â˜ƒ;
      this.secondary = â˜ƒ;
   }

   public ServerboundSetBeaconPacket(FriendlyByteBuf var1) {
      this.primary = â˜ƒ.readVarInt();
      this.secondary = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.primary);
      â˜ƒ.writeVarInt(this.secondary);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleSetBeaconPacket(this);
   }

   public int getPrimary() {
      return this.primary;
   }

   public int getSecondary() {
      return this.secondary;
   }
}
