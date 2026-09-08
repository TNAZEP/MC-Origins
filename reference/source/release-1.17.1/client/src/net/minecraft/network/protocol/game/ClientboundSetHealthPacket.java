package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public class ClientboundSetHealthPacket implements Packet<ClientGamePacketListener> {
   private final float health;
   private final int food;
   private final float saturation;

   public ClientboundSetHealthPacket(float var1, int var2, float var3) {
      this.health = â˜ƒ;
      this.food = â˜ƒ;
      this.saturation = â˜ƒ;
   }

   public ClientboundSetHealthPacket(FriendlyByteBuf var1) {
      this.health = â˜ƒ.readFloat();
      this.food = â˜ƒ.readVarInt();
      this.saturation = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeFloat(this.health);
      â˜ƒ.writeVarInt(this.food);
      â˜ƒ.writeFloat(this.saturation);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleSetHealth(this);
   }

   public float getHealth() {
      return this.health;
   }

   public int getFood() {
      return this.food;
   }

   public float getSaturation() {
      return this.saturation;
   }
}
