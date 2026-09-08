package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundDisconnectPacket implements Packet<ClientGamePacketListener> {
   private final Component reason;

   public ClientboundDisconnectPacket(Component var1) {
      this.reason = â˜ƒ;
   }

   public ClientboundDisconnectPacket(FriendlyByteBuf var1) {
      this.reason = â˜ƒ.readComponent();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeComponent(this.reason);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleDisconnect(this);
   }

   public Component getReason() {
      return this.reason;
   }
}
