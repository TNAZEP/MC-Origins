package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderWarningDelayPacket implements Packet<ClientGamePacketListener> {
   private final int warningDelay;

   public ClientboundSetBorderWarningDelayPacket(WorldBorder var1) {
      this.warningDelay = â˜ƒ.getWarningTime();
   }

   public ClientboundSetBorderWarningDelayPacket(FriendlyByteBuf var1) {
      this.warningDelay = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.warningDelay);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetBorderWarningDelay(this);
   }

   public int getWarningDelay() {
      return this.warningDelay;
   }
}
