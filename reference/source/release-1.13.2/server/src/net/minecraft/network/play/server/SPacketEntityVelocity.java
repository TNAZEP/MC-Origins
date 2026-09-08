package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityVelocity implements Packet<INetHandlerPlayClient> {
   private int field_149417_a;
   private int field_149415_b;
   private int field_149416_c;
   private int field_149414_d;

   public SPacketEntityVelocity() {
   }

   public SPacketEntityVelocity(Entity var1) {
      this(☃.func_145782_y(), ☃.field_70159_w, ☃.field_70181_x, ☃.field_70179_y);
   }

   public SPacketEntityVelocity(int var1, double var2, double var4, double var6) {
      this.field_149417_a = ☃;
      double ☃ = 3.9;
      if (☃ < -3.9) {
         ☃ = -3.9;
      }

      if (☃ < -3.9) {
         ☃ = -3.9;
      }

      if (☃ < -3.9) {
         ☃ = -3.9;
      }

      if (☃ > 3.9) {
         ☃ = 3.9;
      }

      if (☃ > 3.9) {
         ☃ = 3.9;
      }

      if (☃ > 3.9) {
         ☃ = 3.9;
      }

      this.field_149415_b = (int)(☃ * 8000.0);
      this.field_149416_c = (int)(☃ * 8000.0);
      this.field_149414_d = (int)(☃ * 8000.0);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149417_a = ☃.func_150792_a();
      this.field_149415_b = ☃.readShort();
      this.field_149416_c = ☃.readShort();
      this.field_149414_d = ☃.readShort();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149417_a);
      ☃.writeShort(this.field_149415_b);
      ☃.writeShort(this.field_149416_c);
      ☃.writeShort(this.field_149414_d);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147244_a(this);
   }
}
