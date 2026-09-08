package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;

public class ClientboundTabListPacket implements Packet<ClientGamePacketListener> {
   private final Component header;
   private final Component footer;

   public ClientboundTabListPacket(Component var1, Component var2) {
      this.header = â˜ƒ;
      this.footer = â˜ƒ;
   }

   public ClientboundTabListPacket(FriendlyByteBuf var1) {
      this.header = â˜ƒ.readComponent();
      this.footer = â˜ƒ.readComponent();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeComponent(this.header);
      â˜ƒ.writeComponent(this.footer);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleTabListCustomisation(this);
   }

   public Component getHeader() {
      return this.header;
   }

   public Component getFooter() {
      return this.footer;
   }
}
