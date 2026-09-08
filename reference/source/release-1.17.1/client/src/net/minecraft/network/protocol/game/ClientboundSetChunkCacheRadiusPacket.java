package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetChunkCacheRadiusPacket implements Packet<ClientGamePacketListener> {
   private final int radius;

   public ClientboundSetChunkCacheRadiusPacket(int var1) {
      this.radius = â˜ƒ;
   }

   public ClientboundSetChunkCacheRadiusPacket(FriendlyByteBuf var1) {
      this.radius = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.radius);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetChunkCacheRadius(this);
   }

   public int getRadius() {
      return this.radius;
   }
}
