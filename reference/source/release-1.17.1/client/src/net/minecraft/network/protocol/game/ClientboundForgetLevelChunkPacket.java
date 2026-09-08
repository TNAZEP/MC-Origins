package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundForgetLevelChunkPacket implements Packet<ClientGamePacketListener> {
   private final int x;
   private final int z;

   public ClientboundForgetLevelChunkPacket(int var1, int var2) {
      this.x = â˜ƒ;
      this.z = â˜ƒ;
   }

   public ClientboundForgetLevelChunkPacket(FriendlyByteBuf var1) {
      this.x = â˜ƒ.readInt();
      this.z = â˜ƒ.readInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeInt(this.x);
      â˜ƒ.writeInt(this.z);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleForgetLevelChunk(this);
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }
}
