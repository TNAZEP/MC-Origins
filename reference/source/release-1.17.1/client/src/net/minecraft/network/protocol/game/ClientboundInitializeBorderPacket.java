package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundInitializeBorderPacket implements Packet<ClientGamePacketListener> {
   private final double newCenterX;
   private final double newCenterZ;
   private final double oldSize;
   private final double newSize;
   private final long lerpTime;
   private final int newAbsoluteMaxSize;
   private final int warningBlocks;
   private final int warningTime;

   public ClientboundInitializeBorderPacket(FriendlyByteBuf var1) {
      this.newCenterX = â˜ƒ.readDouble();
      this.newCenterZ = â˜ƒ.readDouble();
      this.oldSize = â˜ƒ.readDouble();
      this.newSize = â˜ƒ.readDouble();
      this.lerpTime = â˜ƒ.readVarLong();
      this.newAbsoluteMaxSize = â˜ƒ.readVarInt();
      this.warningBlocks = â˜ƒ.readVarInt();
      this.warningTime = â˜ƒ.readVarInt();
   }

   public ClientboundInitializeBorderPacket(WorldBorder var1) {
      this.newCenterX = â˜ƒ.getCenterX();
      this.newCenterZ = â˜ƒ.getCenterZ();
      this.oldSize = â˜ƒ.getSize();
      this.newSize = â˜ƒ.getLerpTarget();
      this.lerpTime = â˜ƒ.getLerpRemainingTime();
      this.newAbsoluteMaxSize = â˜ƒ.getAbsoluteMaxSize();
      this.warningBlocks = â˜ƒ.getWarningBlocks();
      this.warningTime = â˜ƒ.getWarningTime();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeDouble(this.newCenterX);
      â˜ƒ.writeDouble(this.newCenterZ);
      â˜ƒ.writeDouble(this.oldSize);
      â˜ƒ.writeDouble(this.newSize);
      â˜ƒ.writeVarLong(this.lerpTime);
      â˜ƒ.writeVarInt(this.newAbsoluteMaxSize);
      â˜ƒ.writeVarInt(this.warningBlocks);
      â˜ƒ.writeVarInt(this.warningTime);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleInitializeBorder(this);
   }

   public double getNewCenterX() {
      return this.newCenterX;
   }

   public double getNewCenterZ() {
      return this.newCenterZ;
   }

   public double getNewSize() {
      return this.newSize;
   }

   public double getOldSize() {
      return this.oldSize;
   }

   public long getLerpTime() {
      return this.lerpTime;
   }

   public int getNewAbsoluteMaxSize() {
      return this.newAbsoluteMaxSize;
   }

   public int getWarningTime() {
      return this.warningTime;
   }

   public int getWarningBlocks() {
      return this.warningBlocks;
   }
}
