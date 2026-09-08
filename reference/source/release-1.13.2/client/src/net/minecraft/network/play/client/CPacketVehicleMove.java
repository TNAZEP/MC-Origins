package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketVehicleMove implements Packet<INetHandlerPlayServer> {
   private double field_187007_a;
   private double field_187008_b;
   private double field_187009_c;
   private float field_187010_d;
   private float field_187011_e;

   public CPacketVehicleMove() {
   }

   public CPacketVehicleMove(Entity var1) {
      this.field_187007_a = ☃.field_70165_t;
      this.field_187008_b = ☃.field_70163_u;
      this.field_187009_c = ☃.field_70161_v;
      this.field_187010_d = ☃.field_70177_z;
      this.field_187011_e = ☃.field_70125_A;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_187007_a = ☃.readDouble();
      this.field_187008_b = ☃.readDouble();
      this.field_187009_c = ☃.readDouble();
      this.field_187010_d = ☃.readFloat();
      this.field_187011_e = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeDouble(this.field_187007_a);
      ☃.writeDouble(this.field_187008_b);
      ☃.writeDouble(this.field_187009_c);
      ☃.writeFloat(this.field_187010_d);
      ☃.writeFloat(this.field_187011_e);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_184338_a(this);
   }

   public double func_187004_a() {
      return this.field_187007_a;
   }

   public double func_187002_b() {
      return this.field_187008_b;
   }

   public double func_187003_c() {
      return this.field_187009_c;
   }

   public float func_187006_d() {
      return this.field_187010_d;
   }

   public float func_187005_e() {
      return this.field_187011_e;
   }
}
