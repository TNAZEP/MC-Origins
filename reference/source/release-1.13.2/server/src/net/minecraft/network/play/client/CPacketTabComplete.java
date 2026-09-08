package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketTabComplete implements Packet<INetHandlerPlayServer> {
   private int field_197710_a;
   private String field_197711_b;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_197710_a = ☃.func_150792_a();
      this.field_197711_b = ☃.func_150789_c(32500);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_197710_a);
      ☃.func_211400_a(this.field_197711_b, 32500);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_195518_a(this);
   }

   public int func_197709_a() {
      return this.field_197710_a;
   }

   public String func_197707_b() {
      return this.field_197711_b;
   }
}
