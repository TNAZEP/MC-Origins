package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public class ClientboundAddEntityPacket implements Packet<ClientGamePacketListener> {
   public static final double MAGICAL_QUANTIZATION = 8000.0;
   private final int id;
   private final UUID uuid;
   private final double x;
   private final double y;
   private final double z;
   private final int xa;
   private final int ya;
   private final int za;
   private final int xRot;
   private final int yRot;
   private final EntityType<?> type;
   private final int data;
   public static final double LIMIT = 3.9;

   public ClientboundAddEntityPacket(
      int var1, UUID var2, double var3, double var5, double var7, float var9, float var10, EntityType<?> var11, int var12, Vec3 var13
   ) {
      this.id = â˜ƒ;
      this.uuid = â˜ƒ;
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.xRot = Mth.floor(â˜ƒ * 256.0F / 360.0F);
      this.yRot = Mth.floor(â˜ƒ * 256.0F / 360.0F);
      this.type = â˜ƒ;
      this.data = â˜ƒ;
      this.xa = (int)(Mth.clamp(â˜ƒ.x, -3.9, 3.9) * 8000.0);
      this.ya = (int)(Mth.clamp(â˜ƒ.y, -3.9, 3.9) * 8000.0);
      this.za = (int)(Mth.clamp(â˜ƒ.z, -3.9, 3.9) * 8000.0);
   }

   public ClientboundAddEntityPacket(Entity var1) {
      this(â˜ƒ, 0);
   }

   public ClientboundAddEntityPacket(Entity var1, int var2) {
      this(â˜ƒ.getId(), â˜ƒ.getUUID(), â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getXRot(), â˜ƒ.getYRot(), â˜ƒ.getType(), â˜ƒ, â˜ƒ.getDeltaMovement());
   }

   public ClientboundAddEntityPacket(Entity var1, EntityType<?> var2, int var3, BlockPos var4) {
      this(
         â˜ƒ.getId(), â˜ƒ.getUUID(), (double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), â˜ƒ.getXRot(), â˜ƒ.getYRot(), â˜ƒ, â˜ƒ, â˜ƒ.getDeltaMovement()
      );
   }

   public ClientboundAddEntityPacket(FriendlyByteBuf var1) {
      this.id = â˜ƒ.readVarInt();
      this.uuid = â˜ƒ.readUUID();
      this.type = Registry.ENTITY_TYPE.byId(â˜ƒ.readVarInt());
      this.x = â˜ƒ.readDouble();
      this.y = â˜ƒ.readDouble();
      this.z = â˜ƒ.readDouble();
      this.xRot = â˜ƒ.readByte();
      this.yRot = â˜ƒ.readByte();
      this.data = â˜ƒ.readInt();
      this.xa = â˜ƒ.readShort();
      this.ya = â˜ƒ.readShort();
      this.za = â˜ƒ.readShort();
   }

   @Override
   public void write(FriendlyByteBuf var1) {
      â˜ƒ.writeVarInt(this.id);
      â˜ƒ.writeUUID(this.uuid);
      â˜ƒ.writeVarInt(Registry.ENTITY_TYPE.getId(this.type));
      â˜ƒ.writeDouble(this.x);
      â˜ƒ.writeDouble(this.y);
      â˜ƒ.writeDouble(this.z);
      â˜ƒ.writeByte(this.xRot);
      â˜ƒ.writeByte(this.yRot);
      â˜ƒ.writeInt(this.data);
      â˜ƒ.writeShort(this.xa);
      â˜ƒ.writeShort(this.ya);
      â˜ƒ.writeShort(this.za);
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleAddEntity(this);
   }

   public int getId() {
      return this.id;
   }

   public UUID getUUID() {
      return this.uuid;
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

   public double getXa() {
      return (double)this.xa / 8000.0;
   }

   public double getYa() {
      return (double)this.ya / 8000.0;
   }

   public double getZa() {
      return (double)this.za / 8000.0;
   }

   public int getxRot() {
      return this.xRot;
   }

   public int getyRot() {
      return this.yRot;
   }

   public EntityType<?> getType() {
      return this.type;
   }

   public int getData() {
      return this.data;
   }
}
