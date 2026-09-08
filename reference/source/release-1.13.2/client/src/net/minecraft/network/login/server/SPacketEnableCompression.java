package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;

public class SPacketEnableCompression implements Packet<INetHandlerLoginClient> {
   private int field_179733_a;

   public SPacketEnableCompression() {
   }

   public SPacketEnableCompression(int var1) {
      this.field_179733_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179733_a = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_179733_a);
   }

   public void func_148833_a(INetHandlerLoginClient var1) {
      ☃.func_180464_a(this);
   }

   public int func_179731_a() {
      return this.field_179733_a;
   }
}
