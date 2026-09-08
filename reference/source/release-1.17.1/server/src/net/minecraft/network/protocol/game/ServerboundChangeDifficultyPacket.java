package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.Difficulty;

public class ServerboundChangeDifficultyPacket implements Packet<ServerGamePacketListener> {
   private final Difficulty difficulty;

   public ServerboundChangeDifficultyPacket(Difficulty var1) {
      this.difficulty = â˜ƒ;
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleChangeDifficulty(this);
   }

   public ServerboundChangeDifficultyPacket(FriendlyByteBuf var1) {
      this.difficulty = Difficulty.byId(â˜ƒ.readUnsignedByte());
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.difficulty.getId());
   }

   public Difficulty getDifficulty() {
      return this.difficulty;
   }
}
