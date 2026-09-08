package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSetExperience implements Packet<INetHandlerPlayClient> {
   private float field_149401_a;
   private int field_149399_b;
   private int field_149400_c;

   public SPacketSetExperience() {
   }

   public SPacketSetExperience(float var1, int var2, int var3) {
      this.field_149401_a = ☃;
      this.field_149399_b = ☃;
      this.field_149400_c = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149401_a = ☃.readFloat();
      this.field_149400_c = ☃.func_150792_a();
      this.field_149399_b = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeFloat(this.field_149401_a);
      ☃.func_150787_b(this.field_149400_c);
      ☃.func_150787_b(this.field_149399_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147295_a(this);
   }
}
