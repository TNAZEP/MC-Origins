package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketMoveVehicle implements Packet<INetHandlerPlayClient> {
   private double field_186960_a;
   private double field_186961_b;
   private double field_186962_c;
   private float field_186963_d;
   private float field_186964_e;

   public SPacketMoveVehicle() {
   }

   public SPacketMoveVehicle(Entity var1) {
      this.field_186960_a = ☃.field_70165_t;
      this.field_186961_b = ☃.field_70163_u;
      this.field_186962_c = ☃.field_70161_v;
      this.field_186963_d = ☃.field_70177_z;
      this.field_186964_e = ☃.field_70125_A;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_186960_a = ☃.readDouble();
      this.field_186961_b = ☃.readDouble();
      this.field_186962_c = ☃.readDouble();
      this.field_186963_d = ☃.readFloat();
      this.field_186964_e = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeDouble(this.field_186960_a);
      ☃.writeDouble(this.field_186961_b);
      ☃.writeDouble(this.field_186962_c);
      ☃.writeFloat(this.field_186963_d);
      ☃.writeFloat(this.field_186964_e);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184323_a(this);
   }

   public double func_186957_a() {
      return this.field_186960_a;
   }

   public double func_186955_b() {
      return this.field_186961_b;
   }

   public double func_186956_c() {
      return this.field_186962_c;
   }

   public float func_186959_d() {
      return this.field_186963_d;
   }

   public float func_186958_e() {
      return this.field_186964_e;
   }
}
