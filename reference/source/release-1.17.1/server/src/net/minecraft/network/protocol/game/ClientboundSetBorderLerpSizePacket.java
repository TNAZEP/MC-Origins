package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderLerpSizePacket implements Packet<ClientGamePacketListener> {
   private final double oldSize;
   private final double newSize;
   private final long lerpTime;

   public ClientboundSetBorderLerpSizePacket(WorldBorder var1) {
      this.oldSize = â˜ƒ.getSize();
      this.newSize = â˜ƒ.getLerpTarget();
      this.lerpTime = â˜ƒ.getLerpRemainingTime();
   }

   public ClientboundSetBorderLerpSizePacket(FriendlyByteBuf var1) {
      this.oldSize = â˜ƒ.readDouble();
      this.newSize = â˜ƒ.readDouble();
      this.lerpTime = â˜ƒ.readVarLong();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeDouble(this.oldSize);
      â˜ƒ.writeDouble(this.newSize);
      â˜ƒ.writeVarLong(this.lerpTime);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetBorderLerpSize(this);
   }

   public double getOldSize() {
      return this.oldSize;
   }

   public double getNewSize() {
      return this.newSize;
   }

   public long getLerpTime() {
      return this.lerpTime;
   }
}
