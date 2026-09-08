package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;

public class ServerboundUseItemPacket implements Packet<ServerGamePacketListener> {
   private final InteractionHand hand;

   public ServerboundUseItemPacket(InteractionHand var1) {
      this.hand = â˜ƒ;
   }

   public ServerboundUseItemPacket(FriendlyByteBuf var1) {
      this.hand = â˜ƒ.readEnum(InteractionHand.class);
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.hand);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleUseItem(this);
   }

   public InteractionHand getHand() {
      return this.hand;
   }
}
