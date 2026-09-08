package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetActionBarTextPacket implements Packet<ClientGamePacketListener> {
   private final Component text;

   public ClientboundSetActionBarTextPacket(Component var1) {
      this.text = â˜ƒ;
   }

   public ClientboundSetActionBarTextPacket(FriendlyByteBuf var1) {
      this.text = â˜ƒ.readComponent();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeComponent(this.text);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.setActionBarText(this);
   }

   public Component getText() {
      return this.text;
   }
}
