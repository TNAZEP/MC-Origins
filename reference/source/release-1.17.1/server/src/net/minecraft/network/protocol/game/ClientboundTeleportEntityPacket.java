package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class ClientboundTeleportEntityPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final double x;
   private final double y;
   private final double z;
   private final byte yRot;
   private final byte xRot;
   private final boolean onGround;

   public ClientboundTeleportEntityPacket(Entity var1) {
      this.id = â˜ƒ.getId();
      this.x = â˜ƒ.getX();
      this.y = â˜ƒ.getY();
      this.z = â˜ƒ.getZ();
      this.yRot = (byte)((int)(â˜ƒ.getYRot() * 256.0F / 360.0F));
      this.xRot = (byte)((int)(â˜ƒ.getXRot() * 256.0F / 360.0F));
      this.onGround = â˜ƒ.isOnGround();
   }

   public ClientboundTeleportEntityPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.yRot = â˜ƒ.readByte();
      this.xRot = â˜ƒ.readByte();
      this.onGround = â˜ƒ.readBoolean();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeByte(this.yRot);
      â˜ƒ.writeByte(this.xRot);
      â˜ƒ.writeBoolean(this.onGround);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleTeleportEntity(this);
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

   public byte getyRot() {
      return this.yRot;
   }

   public byte getxRot() {
      return this.xRot;
   }

   public boolean isOnGround() {
      return this.onGround;
   }
}
