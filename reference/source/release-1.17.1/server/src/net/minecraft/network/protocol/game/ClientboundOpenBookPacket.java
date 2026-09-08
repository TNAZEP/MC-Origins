package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;

public class ClientboundOpenBookPacket implements Packet<ClientGamePacketListener> {
   private final InteractionHand hand;

   public ClientboundOpenBookPacket(InteractionHand var1) {
      this.hand = â˜ƒ;
   }

   public ClientboundOpenBookPacket(FriendlyByteBuf var1) {
      this.hand = â˜ƒ.readEnum(InteractionHand.class);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.hand);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleOpenBook(this);
   }

   public InteractionHand getHand() {
      return this.hand;
   }
}
