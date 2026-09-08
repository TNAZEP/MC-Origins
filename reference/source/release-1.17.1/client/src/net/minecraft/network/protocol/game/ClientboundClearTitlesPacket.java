package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundClearTitlesPacket implements Packet<ClientGamePacketListener> {
   private final boolean resetTimes;

   public ClientboundClearTitlesPacket(boolean var1) {
      this.resetTimes = â˜ƒ;
   }

   public ClientboundClearTitlesPacket(FriendlyByteBuf var1) {
      this.resetTimes = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBoolean(this.resetTimes);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleTitlesClear(this);
   }

   public boolean shouldResetTimes() {
      return this.resetTimes;
   }
}
