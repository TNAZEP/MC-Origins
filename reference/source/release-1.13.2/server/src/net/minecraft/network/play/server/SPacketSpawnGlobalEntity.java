package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSpawnGlobalEntity implements Packet<INetHandlerPlayClient> {
   private int field_149059_a;
   private double field_149057_b;
   private double field_149058_c;
   private double field_149055_d;
   private int field_149056_e;

   public SPacketSpawnGlobalEntity() {
   }

   public SPacketSpawnGlobalEntity(Entity var1) {
      this.field_149059_a = ☃.func_145782_y();
      this.field_149057_b = ☃.field_70165_t;
      this.field_149058_c = ☃.field_70163_u;
      this.field_149055_d = ☃.field_70161_v;
      if (☃ instanceof EntityLightningBolt) {
         this.field_149056_e = 1;
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149059_a = ☃.func_150792_a();
      this.field_149056_e = ☃.readByte();
      this.field_149057_b = ☃.readDouble();
      this.field_149058_c = ☃.readDouble();
      this.field_149055_d = ☃.readDouble();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149059_a);
      ☃.writeByte(this.field_149056_e);
      ☃.writeDouble(this.field_149057_b);
      ☃.writeDouble(this.field_149058_c);
      ☃.writeDouble(this.field_149055_d);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147292_a(this);
   }
}
