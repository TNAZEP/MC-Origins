package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundBlockDestructionPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final BlockPos pos;
   private final int progress;

   public ClientboundBlockDestructionPacket(int var1, BlockPos var2, int var3) {
      this.id = â˜ƒ;
      this.pos = â˜ƒ;
      this.progress = â˜ƒ;
   }

   public ClientboundBlockDestructionPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.pos = â˜ƒ.readBlockPos();
      this.progress = â˜ƒ.readUnsignedByte();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeByte(this.progress);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleBlockDestruction(this);
   }

   public int getId() {
      return this.id;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public int getProgress() {
      return this.progress;
   }
}
