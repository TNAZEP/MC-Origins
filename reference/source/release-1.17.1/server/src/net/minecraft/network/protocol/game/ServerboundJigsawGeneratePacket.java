package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundJigsawGeneratePacket implements Packet<ServerGamePacketListener> {
   private final BlockPos pos;
   private final int levels;
   private final boolean keepJigsaws;

   public ServerboundJigsawGeneratePacket(BlockPos var1, int var2, boolean var3) {
      this.pos = â˜ƒ;
      this.levels = â˜ƒ;
      this.keepJigsaws = â˜ƒ;
   }

   public ServerboundJigsawGeneratePacket(FriendlyByteBuf var1) {
      this.pos = â˜ƒ.readBlockPos();
      this.levels = â˜ƒ.readVarInt();
      this.keepJigsaws = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeVarInt(this.levels);
      â˜ƒ.writeBoolean(this.keepJigsaws);
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleJigsawGenerate(this);
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public int levels() {
      return this.levels;
   }

   public boolean keepJigsaws() {
      return this.keepJigsaws;
   }
}
