package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ServerboundLockDifficultyPacket implements Packet<ServerGamePacketListener> {
   private final boolean locked;

   public ServerboundLockDifficultyPacket(boolean var1) {
      this.locked = â˜ƒ;
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleLockDifficulty(this);
   }

   public ServerboundLockDifficultyPacket(FriendlyByteBuf var1) {
      this.locked = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeBoolean(this.locked);
   }

   public boolean isLocked() {
      return this.locked;
   }
}
