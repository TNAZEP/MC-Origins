package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundBlockEntityTagQuery implements Packet<ServerGamePacketListener> {
   private final int transactionId;
   private final BlockPos pos;

   public ServerboundBlockEntityTagQuery(int var1, BlockPos var2) {
      this.transactionId = â˜ƒ;
      this.pos = â˜ƒ;
   }

   public ServerboundBlockEntityTagQuery(FriendlyByteBuf var1) {
      this.transactionId = â˜ƒ.readVarInt();
      this.pos = â˜ƒ.readBlockPos();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.transactionId);
      â˜ƒ.writeBlockPos(this.pos);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleBlockEntityTagQuery(this);
   }

   public int getTransactionId() {
      return this.transactionId;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
