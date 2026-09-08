package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundLoginDisconnectPacket implements Packet<ClientLoginPacketListener> {
   private final Component reason;

   public ClientboundLoginDisconnectPacket(Component var1) {
      this.reason = â˜ƒ;
   }

   public ClientboundLoginDisconnectPacket(FriendlyByteBuf var1) {
      this.reason = Component.Serializer.fromJsonLenient(â˜ƒ.readUtf(262144));
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeComponent(this.reason);
   }

   public void handle(ClientLoginPacketListener var1) {
      â˜ƒ.handleDisconnect(this);
   }

   public Component getReason() {
      return this.reason;
   }
}
