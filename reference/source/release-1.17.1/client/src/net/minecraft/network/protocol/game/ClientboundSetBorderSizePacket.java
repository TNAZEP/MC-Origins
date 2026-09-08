package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderSizePacket implements Packet<ClientGamePacketListener> {
   private final double size;

   public ClientboundSetBorderSizePacket(WorldBorder var1) {
      this.size = â˜ƒ.getLerpTarget();
   }

   public ClientboundSetBorderSizePacket(FriendlyByteBuf var1) {
      this.size = â˜ƒ.readDouble();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeDouble(this.size);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetBorderSize(this);
   }

   public double getSize() {
      return this.size;
   }
}
