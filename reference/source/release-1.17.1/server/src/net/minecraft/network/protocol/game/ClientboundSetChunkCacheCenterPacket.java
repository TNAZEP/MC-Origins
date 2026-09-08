package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetChunkCacheCenterPacket implements Packet<ClientGamePacketListener> {
   private final int x;
   private final int z;

   public ClientboundSetChunkCacheCenterPacket(int var1, int var2) {
      this.x = â˜ƒ;
      this.z = â˜ƒ;
   }

   public ClientboundSetChunkCacheCenterPacket(FriendlyByteBuf var1) {
      this.x = â˜ƒ.readVarInt();
      this.z = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.x);
      â˜ƒ.writeVarInt(this.z);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetChunkCacheCenter(this);
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }
}
