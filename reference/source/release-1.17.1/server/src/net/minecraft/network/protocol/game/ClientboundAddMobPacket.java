package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ClientboundAddMobPacket implements Packet<ClientGamePacketListener> {
   private final int id;
   private final UUID uuid;
   private final int type;
   private final double x;
   private final double y;
   private final double z;
   private final int xd;
   private final int yd;
   private final int zd;
   private final byte yRot;
   private final byte xRot;
   private final byte yHeadRot;

   public ClientboundAddMobPacket(LivingEntity var1) {
      this.id = â˜ƒ.getId();
      this.uuid = â˜ƒ.getUUID();
      this.type = Registry.ENTITY_TYPE.getId(â˜ƒ.getType());
      this.x = â˜ƒ.getX();
      this.y = â˜ƒ.getY();
      this.z = â˜ƒ.getZ();
      this.yRot = (byte)((int)(â˜ƒ.getYRot() * 256.0F / 360.0F));
      this.xRot = (byte)((int)(â˜ƒ.getXRot() * 256.0F / 360.0F));
      this.yHeadRot = (byte)((int)(â˜ƒ.yHeadRot * 256.0F / 360.0F));
      double â˜ƒ = 3.9;
      Vec3 â˜ƒx = â˜ƒ.getDeltaMovement();
      double â˜ƒxx = Mth.clamp(â˜ƒx.x, -3.9, 3.9);
      double â˜ƒxxx = Mth.clamp(â˜ƒx.y, -3.9, 3.9);
      double â˜ƒxxxx = Mth.clamp(â˜ƒx.z, -3.9, 3.9);
      this.xd = (int)(â˜ƒxx * 8000.0);
      this.yd = (int)(â˜ƒxxx * 8000.0);
      this.zd = (int)(â˜ƒxxxx * 8000.0);
   }

   public ClientboundAddMobPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.uuid = â˜ƒ.readUUID();
      this.type = â˜ƒ.readVarInt();
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.yRot = â˜ƒ.readByte();
      this.xRot = â˜ƒ.readByte();
      this.yHeadRot = â˜ƒ.readByte();
      this.xd = â˜ƒ.readShort();
      this.yd = â˜ƒ.readShort();
      this.zd = â˜ƒ.readShort();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeUUID(this.uuid);
      â˜ƒ.writeVarInt(this.type);
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeByte(this.yRot);
      â˜ƒ.writeByte(this.xRot);
      â˜ƒ.writeByte(this.yHeadRot);
      â˜ƒ.writeShort(this.xd);
      â˜ƒ.writeShort(this.yd);
      â˜ƒ.writeShort(this.zd);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddMob(this);
   }

   public int getId() {
      return this.id;
   }

   public UUID getUUID() {
      return this.uuid;
   }

   public int getType() {
      return this.type;
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

   public int getXd() {
      return this.xd;
   }

   public int getYd() {
      return this.yd;
   }

   public int getZd() {
      return this.zd;
   }

   public byte getyRot() {
      return this.yRot;
   }

   public byte getxRot() {
      return this.xRot;
   }

   public byte getyHeadRot() {
      return this.yHeadRot;
   }
}
