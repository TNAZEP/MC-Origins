package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.Difficulty;

public class ClientboundChangeDifficultyPacket implements Packet<ClientGamePacketListener> {
   private final Difficulty difficulty;
   private final boolean locked;

   public ClientboundChangeDifficultyPacket(Difficulty var1, boolean var2) {
      this.difficulty = â˜ƒ;
      this.locked = â˜ƒ;
   }

   public ClientboundChangeDifficultyPacket(FriendlyByteBuf var1) {
      this.difficulty = Difficulty.byId(â˜ƒ.readUnsignedByte());
      this.locked = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeByte(this.difficulty.getId());
      â˜ƒ.writeBoolean(this.locked);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleChangeDifficulty(this);
   }

   public boolean isLocked() {
      return this.locked;
   }

   public Difficulty getDifficulty() {
      return this.difficulty;
   }
}
