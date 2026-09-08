package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.Entity;

public class ClientboundMoveVehiclePacket implements Packet<ClientGamePacketListener> {
   private final double x;
   private final double y;
   private final double z;
   private final float yRot;
   private final float xRot;

   public ClientboundMoveVehiclePacket(Entity var1) {
      this.x = â˜ƒ.getX();
      this.y = â˜ƒ.getY();
      this.z = â˜ƒ.getZ();
      this.yRot = â˜ƒ.getYRot();
      this.xRot = â˜ƒ.getXRot();
   }

   public ClientboundMoveVehiclePacket(FriendlyByteBuf var1) {
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.yRot = â˜ƒ.readFloat();
      this.xRot = â˜ƒ.readFloat();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeFloat(this.yRot);
      â˜ƒ.writeFloat(this.xRot);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleMoveVehicle(this);
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

   public float getYRot() {
      return this.yRot;
   }

   public float getXRot() {
      return this.xRot;
   }
}
