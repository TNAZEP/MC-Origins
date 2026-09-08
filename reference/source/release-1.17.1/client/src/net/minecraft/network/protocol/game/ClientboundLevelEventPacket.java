package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundLevelEventPacket implements Packet<ClientGamePacketListener> {
   private final int type;
   private final BlockPos pos;
   private final int data;
   private final boolean globalEvent;

   public ClientboundLevelEventPacket(int var1, BlockPos var2, int var3, boolean var4) {
      this.type = â˜ƒ;
      this.pos = â˜ƒ.immutable();
      this.data = â˜ƒ;
      this.globalEvent = â˜ƒ;
   }

   public ClientboundLevelEventPacket(FriendlyByteBuf var1) {
      this.type = â˜ƒ.readInt();
      this.pos = â˜ƒ.readBlockPos();
      this.data = â˜ƒ.readInt();
      this.globalEvent = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.type);
      â˜ƒ.writeBlockPos(this.pos);
      â˜ƒ.writeInt(this.data);
      â˜ƒ.writeBoolean(this.globalEvent);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleLevelEvent(this);
   }

   public boolean isGlobalEvent() {
      return this.globalEvent;
   }

   public int getType() {
      return this.type;
   }

   public int getData() {
      return this.data;
   }

   public BlockPos getPos() {
      return this.pos;
   }
}
