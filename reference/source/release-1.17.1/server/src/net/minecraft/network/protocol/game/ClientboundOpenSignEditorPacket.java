package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundOpenSignEditorPacket implements Packet<ClientGamePacketListener> {
   private final BlockPos pos;

   public ClientboundOpenSignEditorPacket(BlockPos var1) {
      this.pos = â˜ƒ;
   }

   public ClientboundOpenSignEditorPacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleOpenSignEditor(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
