package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityTeleport implements Packet<INetHandlerPlayClient> {
   private int field_149458_a;
   private double field_149456_b;
   private double field_149457_c;
   private double field_149454_d;
   private byte field_149455_e;
   private byte field_149453_f;
   private boolean field_179698_g;

   public SPacketEntityTeleport() {
   }

   public SPacketEntityTeleport(Entity var1) {
      this.field_149458_a = ☃.func_145782_y();
      this.field_149456_b = ☃.field_70165_t;
      this.field_149457_c = ☃.field_70163_u;
      this.field_149454_d = ☃.field_70161_v;
      this.field_149455_e = (byte)((int)(☃.field_70177_z * 256.0F / 360.0F));
      this.field_149453_f = (byte)((int)(☃.field_70125_A * 256.0F / 360.0F));
      this.field_179698_g = ☃.field_70122_E;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149458_a = ☃.func_150792_a();
      this.field_149456_b = ☃.readDouble();
      this.field_149457_c = ☃.readDouble();
      this.field_149454_d = ☃.readDouble();
      this.field_149455_e = ☃.readByte();
      this.field_149453_f = ☃.readByte();
      this.field_179698_g = ☃.readBoolean();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149458_a);
      ☃.writeDouble(this.field_149456_b);
      ☃.writeDouble(this.field_149457_c);
      ☃.writeDouble(this.field_149454_d);
      ☃.writeByte(this.field_149455_e);
      ☃.writeByte(this.field_149453_f);
      ☃.writeBoolean(this.field_179698_g);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147275_a(this);
   }
}
