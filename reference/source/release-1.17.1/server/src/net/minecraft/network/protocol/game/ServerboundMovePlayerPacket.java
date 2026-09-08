package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;

public abstract class ServerboundMovePlayerPacket implements Packet<ServerGamePacketListener> {
   protected final double x;
   protected final double y;
   protected final double z;
   protected final float yRot;
   protected final float xRot;
   protected final boolean onGround;
   protected final boolean hasPos;
   protected final boolean hasRot;

   protected ServerboundMovePlayerPacket(double var1, double var3, double var5, float var7, float var8, boolean var9, boolean var10, boolean var11) {
      this.x = â˜ƒ;
      this.y = â˜ƒ;
      this.z = â˜ƒ;
      this.yRot = â˜ƒ;
      this.xRot = â˜ƒ;
      this.onGround = â˜ƒ;
      this.hasPos = â˜ƒ;
      this.hasRot = â˜ƒ;
   }

   public void handle(ServerGamePacketListener var1) {
      â˜ƒ.handleMovePlayer(this);
   }

   public double getX(double var1) {
      return this.hasPos ? this.x : â˜ƒ;
   }

   public double getY(double var1) {
      return this.hasPos ? this.y : â˜ƒ;
   }

   public double getZ(double var1) {
      return this.hasPos ? this.z : â˜ƒ;
   }

   public float getYRot(float var1) {
      return this.hasRot ? this.yRot : â˜ƒ;
   }

   public float getXRot(float var1) {
      return this.hasRot ? this.xRot : â˜ƒ;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public boolean hasPosition() {
      return this.hasPos;
   }

   public boolean hasRotation() {
      return this.hasRot;
   }

   public static class Pos extends ServerboundMovePlayerPacket {
      public Pos(double var1, double var3, double var5, boolean var7) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 0.0F, â˜ƒ, true, false);
      }

      public static ServerboundMovePlayerPacket.Pos read(FriendlyByteBuf var0) {
         double â˜ƒ = â˜ƒ.readDouble();
         double â˜ƒx = â˜ƒ.readDouble();
         double â˜ƒxx = â˜ƒ.readDouble();
         boolean â˜ƒxxx = â˜ƒ.readUnsignedByte() != 0;
         return new ServerboundMovePlayerPacket.Pos(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeDouble(this.x);
         â˜ƒ.writeDouble(this.y);
         â˜ƒ.writeDouble(this.z);
         â˜ƒ.writeByte(this.onGround ? 1 : 0);
      }
   }

   public static class PosRot extends ServerboundMovePlayerPacket {
      public PosRot(double var1, double var3, double var5, float var7, float var8, boolean var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, true, true);
      }

      public static ServerboundMovePlayerPacket.PosRot read(FriendlyByteBuf var0) {
         double â˜ƒ = â˜ƒ.readDouble();
         double â˜ƒx = â˜ƒ.readDouble();
         double â˜ƒxx = â˜ƒ.readDouble();
         float â˜ƒxxx = â˜ƒ.readFloat();
         float â˜ƒxxxx = â˜ƒ.readFloat();
         boolean â˜ƒxxxxx = â˜ƒ.readUnsignedByte() != 0;
         return new ServerboundMovePlayerPacket.PosRot(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeDouble(this.x);
         â˜ƒ.writeDouble(this.y);
         â˜ƒ.writeDouble(this.z);
         â˜ƒ.writeFloat(this.yRot);
         â˜ƒ.writeFloat(this.xRot);
         â˜ƒ.writeByte(this.onGround ? 1 : 0);
      }
   }

   public static class Rot extends ServerboundMovePlayerPacket {
      public Rot(float var1, float var2, boolean var3) {
         super(0.0, 0.0, 0.0, â˜ƒ, â˜ƒ, â˜ƒ, false, true);
      }

      public static ServerboundMovePlayerPacket.Rot read(FriendlyByteBuf var0) {
         float â˜ƒ = â˜ƒ.readFloat();
         float â˜ƒx = â˜ƒ.readFloat();
         boolean â˜ƒxx = â˜ƒ.readUnsignedByte() != 0;
         return new ServerboundMovePlayerPacket.Rot(â˜ƒ, â˜ƒx, â˜ƒxx);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeFloat(this.yRot);
         â˜ƒ.writeFloat(this.xRot);
         â˜ƒ.writeByte(this.onGround ? 1 : 0);
      }
   }

   public static class StatusOnly extends ServerboundMovePlayerPacket {
      public StatusOnly(boolean var1) {
         super(0.0, 0.0, 0.0, 0.0F, 0.0F, â˜ƒ, false, false);
      }

      public static ServerboundMovePlayerPacket.StatusOnly read(FriendlyByteBuf var0) {
         boolean â˜ƒ = â˜ƒ.readUnsignedByte() != 0;
         return new ServerboundMovePlayerPacket.StatusOnly(â˜ƒ);
      }

      @Override
      public void write(FriendlyByteBuf var1) {
         â˜ƒ.writeByte(this.onGround ? 1 : 0);
      }
   }
}
