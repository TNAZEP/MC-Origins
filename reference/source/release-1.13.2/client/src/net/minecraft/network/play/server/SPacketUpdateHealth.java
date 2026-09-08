package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketUpdateHealth implements Packet<INetHandlerPlayClient> {
   private float field_149336_a;
   private int field_149334_b;
   private float field_149335_c;

   public SPacketUpdateHealth() {
   }

   public SPacketUpdateHealth(float var1, int var2, float var3) {
      this.field_149336_a = ☃;
      this.field_149334_b = ☃;
      this.field_149335_c = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149336_a = ☃.readFloat();
      this.field_149334_b = ☃.func_150792_a();
      this.field_149335_c = ☃.readFloat();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeFloat(this.field_149336_a);
      ☃.func_150787_b(this.field_149334_b);
      ☃.writeFloat(this.field_149335_c);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147249_a(this);
   }

   public float func_149332_c() {
      return this.field_149336_a;
   }

   public int func_149330_d() {
      return this.field_149334_b;
   }

   public float func_149331_e() {
      return this.field_149335_c;
   }
}
