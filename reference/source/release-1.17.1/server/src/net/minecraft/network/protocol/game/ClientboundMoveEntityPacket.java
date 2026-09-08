package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class ClientboundMoveEntityPacket implements Packet<ClientGamePacketListener> {
   private static final double TRUNCATION_STEPS = 4096.0;
   protected final int entityId;
   protected final short xa;
   protected final short ya;
   protected final short za;
   protected final byte yRot;
   protected final byte xRot;
   protected final boolean onGround;
   protected final boolean hasRot;
   protected final boolean hasPos;

   public static long entityToPacket(double var0) {
      return Mth.lfloor(â˜ƒ * 4096.0);
   }

   public static double packetToEntity(long var0) {
      return (double)â˜ƒ / 4096.0;
   }

   public Vec3 updateEntityPosition(Vec3 var1) {
      double â˜ƒ = this.xa == 0 ? â˜ƒ.x : packetToEntity(entityToPacket(â˜ƒ.x) + (long)this.xa);
      double â˜ƒx = this.ya == 0 ? â˜ƒ.y : packetToEntity(entityToPacket(â˜ƒ.y) + (long)this.ya);
      double â˜ƒxx = this.za == 0 ? â˜ƒ.z : packetToEntity(entityToPacket(â˜ƒ.z) + (long)this.za);
      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public static Vec3 packetToEntity(long var0, long var2, long var4) {
      return new Vec3((double)â˜ƒ, (double)â˜ƒ, (double)â˜ƒ).scale(2.4414062E-4F);
   }

   protected ClientboundMoveEntityPacket(int var1, short var2, short var3, short var4, byte var5, byte var6, boolean var7, boolean var8, boolean var9) {
      this.entityId = â˜ƒ;
      this.xa = â˜ƒ;
      this.ya = â˜ƒ;
      this.za = â˜ƒ;
      this.yRot = â˜ƒ;
      this.xRot = â˜ƒ;
      this.onGround = â˜ƒ;
      this.hasRot = â˜ƒ;
      this.hasPos = â˜ƒ;
   }

   public void handle(ClientGamePacketListener var1) {
      â˜ƒ.handleMoveEntity(this);
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   @Nullable
   public Entity getEntity(Level var1) {
      return â˜ƒ.getEntity(this.entityId);
   }

   public short getXa() {
      return this.xa;
   }

   public short getYa() {
      return this.ya;
   }

   public short getZa() {
      return this.za;
   }

   public byte getyRot() {
      return this.yRot;
   }

   public byte getxRot() {
      return this.xRot;
   }

   public boolean hasRotation() {
      return this.hasRot;
   }

   public boolean hasPosition() {
      return this.hasPos;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public static class Pos extends ClientboundMoveEntityPacket {
      public Pos(int var1, short var2, short var3, short var4, boolean var5) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (byte)0, (byte)0, â˜ƒ, false, true);
      }

      public static ClientboundMoveEntityPacket.Pos read(FriendlyByteBuf var0) {
         int â˜ƒ = â˜ƒ.readVarInt();
         short â˜ƒx = â˜ƒ.readShort();
         short â˜ƒxx = â˜ƒ.readShort();
         short â˜ƒxxx = â˜ƒ.readShort();
         boolean â˜ƒxxxx = â˜ƒ.readBoolean();
         return new ClientboundMoveEntityPacket.Pos(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeVarInt(this.entityId);
         â˜ƒ.writeShort(this.xa);
         â˜ƒ.writeShort(this.ya);
         â˜ƒ.writeShort(this.za);
         â˜ƒ.writeBoolean(this.onGround);
      }
   }

   public static class PosRot extends ClientboundMoveEntityPacket {
      public PosRot(int var1, short var2, short var3, short var4, byte var5, byte var6, boolean var7) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, true);
      }

      public static ClientboundMoveEntityPacket.PosRot read(FriendlyByteBuf var0) {
         int â˜ƒ = â˜ƒ.readVarInt();
         short â˜ƒx = â˜ƒ.readShort();
         short â˜ƒxx = â˜ƒ.readShort();
         short â˜ƒxxx = â˜ƒ.readShort();
         byte â˜ƒxxxx = â˜ƒ.readByte();
         byte â˜ƒxxxxx = â˜ƒ.readByte();
         boolean â˜ƒxxxxxx = â˜ƒ.readBoolean();
         return new ClientboundMoveEntityPacket.PosRot(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeVarInt(this.entityId);
         â˜ƒ.writeShort(this.xa);
         â˜ƒ.writeShort(this.ya);
         â˜ƒ.writeShort(this.za);
         â˜ƒ.writeByte(this.yRot);
         â˜ƒ.writeByte(this.xRot);
         â˜ƒ.writeBoolean(this.onGround);
      }
   }

   public static class Rot extends ClientboundMoveEntityPacket {
      public Rot(int var1, byte var2, byte var3, boolean var4) {
         super(â˜ƒ, (short)0, (short)0, (short)0, â˜ƒ, â˜ƒ, â˜ƒ, true, false);
      }

      public static ClientboundMoveEntityPacket.Rot read(FriendlyByteBuf var0) {
         int â˜ƒ = â˜ƒ.readVarInt();
         byte â˜ƒx = â˜ƒ.readByte();
         byte â˜ƒxx = â˜ƒ.readByte();
         boolean â˜ƒxxx = â˜ƒ.readBoolean();
         return new ClientboundMoveEntityPacket.Rot(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeVarInt(this.entityId);
         â˜ƒ.writeByte(this.yRot);
         â˜ƒ.writeByte(this.xRot);
         â˜ƒ.writeBoolean(this.onGround);
      }
   }
}
