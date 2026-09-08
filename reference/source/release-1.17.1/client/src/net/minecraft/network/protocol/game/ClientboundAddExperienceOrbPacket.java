package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.ExperienceOrb;

public class ClientboundAddExperienceOrbPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final double x;
   private final double y;
   private final double z;
   private final int value;

   public ClientboundAddExperienceOrbPacket(ExperienceOrb var1) {
      this.id = â˜ƒ.getId();
      this.x = â˜ƒ.getX();
      this.y = â˜ƒ.getY();
      this.z = â˜ƒ.getZ();
      this.value = â˜ƒ.getValue();
   }

   public ClientboundAddExperienceOrbPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.value = â˜ƒ.readShort();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeShort(this.value);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddExperienceOrb(this);
   }

   public int getId() {
      return this.id;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public int getValue() {
      return this.value;
   }
}
