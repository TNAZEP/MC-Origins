package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetExperiencePacket implements Packet<ClientGamePacketListener> {
   private final float experienceProgress;
   private final int totalExperience;
   private final int experienceLevel;

   public ClientboundSetExperiencePacket(float var1, int var2, int var3) {
      this.experienceProgress = â˜ƒ;
      this.totalExperience = â˜ƒ;
      this.experienceLevel = â˜ƒ;
   }

   public ClientboundSetExperiencePacket(FriendlyByteBuf var1) {
      this.experienceProgress = â˜ƒ.readFloat();
      this.experienceLevel = â˜ƒ.readVarInt();
      this.totalExperience = â˜ƒ.readVarInt();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeFloat(this.experienceProgress);
      â˜ƒ.writeVarInt(this.experienceLevel);
      â˜ƒ.writeVarInt(this.totalExperience);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetExperience(this);
   }

   public float getExperienceProgress() {
      return this.experienceProgress;
   }

   public int getTotalExperience() {
      return this.totalExperience;
   }

   public int getExperienceLevel() {
      return this.experienceLevel;
   }
}
