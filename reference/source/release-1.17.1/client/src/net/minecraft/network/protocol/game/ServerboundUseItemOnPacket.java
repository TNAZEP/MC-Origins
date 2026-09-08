package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;

public class ServerboundUseItemOnPacket implements Packet<ServerGamePacketListener> {
   private final BlockHitResult blockHit;
   private final InteractionHand hand;

   public ServerboundUseItemOnPacket(InteractionHand var1, BlockHitResult var2) {
      this.hand = â˜ƒ;
      this.blockHit = â˜ƒ;
   }

   public ServerboundUseItemOnPacket(FriendlyByteBuf var1) {
      this.hand = â˜ƒ.readEnum(InteractionHand.class);
      this.blockHit = â˜ƒ.readBlockHitResult();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeEnum(this.hand);
      â˜ƒ.writeBlockHitResult(this.blockHit);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleUseItemOn(this);
   }

   public InteractionHand getHand() {
      return this.hand;
   }

   public BlockHitResult getHitResult() {
      return this.blockHit;
   }
}
