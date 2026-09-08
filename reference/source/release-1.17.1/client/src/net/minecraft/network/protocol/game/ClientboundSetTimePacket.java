package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetTimePacket implements Packet<ClientGamePacketListener> {
   private final long gameTime;
   private final long dayTime;

   public ClientboundSetTimePacket(long var1, long var3, boolean var5) {
      this.gameTime = â˜ƒ;
      long â˜ƒ = â˜ƒ;
      if (!â˜ƒ) {
         â˜ƒ = -â˜ƒ;
         if (â˜ƒ == 0L) {
            â˜ƒ = -1L;
         }
      }

      this.dayTime = â˜ƒ;
   }

   public ClientboundSetTimePacket(FriendlyByteBuf var1) {
      this.gameTime = â˜ƒ.readLong();
      this.dayTime = â˜ƒ.readLong();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeLong(this.gameTime);
      â˜ƒ.writeLong(this.dayTime);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetTime(this);
   }

   public long getGameTime() {
      return this.gameTime;
   }

   public long getDayTime() {
      return this.dayTime;
   }
}
