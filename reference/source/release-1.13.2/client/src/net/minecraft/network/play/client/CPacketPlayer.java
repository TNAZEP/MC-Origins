package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketPlayer implements Packet<INetHandlerPlayServer> {
   protected double field_149479_a;
   protected double field_149477_b;
   protected double field_149478_c;
   protected float field_149476_e;
   protected float field_149473_f;
   protected boolean field_149474_g;
   protected boolean field_149480_h;
   protected boolean field_149481_i;

   public CPacketPlayer() {
   }

   public CPacketPlayer(boolean var1) {
      this.field_149474_g = ☃;
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147347_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149474_g = ☃.readUnsignedByte() != 0;
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_149474_g ? 1 : 0);
   }

   public double func_186997_a(double var1) {
      return this.field_149480_h ? this.field_149479_a : ☃;
   }

   public double func_186996_b(double var1) {
      return this.field_149480_h ? this.field_149477_b : ☃;
   }

   public double func_187000_c(double var1) {
      return this.field_149480_h ? this.field_149478_c : ☃;
   }

   public float func_186999_a(float var1) {
      return this.field_149481_i ? this.field_149476_e : ☃;
   }

   public float func_186998_b(float var1) {
      return this.field_149481_i ? this.field_149473_f : ☃;
   }

   public boolean func_149465_i() {
      return this.field_149474_g;
   }

   public static class Position extends CPacketPlayer {
      public Position() {
         this.field_149480_h = true;
      }

      public Position(double var1, double var3, double var5, boolean var7) {
         this.field_149479_a = ☃;
         this.field_149477_b = ☃;
         this.field_149478_c = ☃;
         this.field_149474_g = ☃;
         this.field_149480_h = true;
      }

      @Override
      public void func_148837_a(PacketBuffer var1) throws IOException {
         this.field_149479_a = ☃.readDouble();
         this.field_149477_b = ☃.readDouble();
         this.field_149478_c = ☃.readDouble();
         super.func_148837_a(☃);
      }

      @Override
      public void func_148840_b(PacketBuffer var1) throws IOException {
         ☃.writeDouble(this.field_149479_a);
         ☃.writeDouble(this.field_149477_b);
         ☃.writeDouble(this.field_149478_c);
         super.func_148840_b(☃);
      }
   }

   public static class PositionRotation extends CPacketPlayer {
      public PositionRotation() {
         this.field_149480_h = true;
         this.field_149481_i = true;
      }

      public PositionRotation(double var1, double var3, double var5, float var7, float var8, boolean var9) {
         this.field_149479_a = ☃;
         this.field_149477_b = ☃;
         this.field_149478_c = ☃;
         this.field_149476_e = ☃;
         this.field_149473_f = ☃;
         this.field_149474_g = ☃;
         this.field_149481_i = true;
         this.field_149480_h = true;
      }

      @Override
      public void func_148837_a(PacketBuffer var1) throws IOException {
         this.field_149479_a = ☃.readDouble();
         this.field_149477_b = ☃.readDouble();
         this.field_149478_c = ☃.readDouble();
         this.field_149476_e = ☃.readFloat();
         this.field_149473_f = ☃.readFloat();
         super.func_148837_a(☃);
      }

      @Override
      public void func_148840_b(PacketBuffer var1) throws IOException {
         ☃.writeDouble(this.field_149479_a);
         ☃.writeDouble(this.field_149477_b);
         ☃.writeDouble(this.field_149478_c);
         ☃.writeFloat(this.field_149476_e);
         ☃.writeFloat(this.field_149473_f);
         super.func_148840_b(☃);
      }
   }

   public static class Rotation extends CPacketPlayer {
      public Rotation() {
         this.field_149481_i = true;
      }

      public Rotation(float var1, float var2, boolean var3) {
         this.field_149476_e = ☃;
         this.field_149473_f = ☃;
         this.field_149474_g = ☃;
         this.field_149481_i = true;
      }

      @Override
      public void func_148837_a(PacketBuffer var1) throws IOException {
         this.field_149476_e = ☃.readFloat();
         this.field_149473_f = ☃.readFloat();
         super.func_148837_a(☃);
      }

      @Override
      public void func_148840_b(PacketBuffer var1) throws IOException {
         ☃.writeFloat(this.field_149476_e);
         ☃.writeFloat(this.field_149473_f);
         super.func_148840_b(☃);
      }
   }
}
