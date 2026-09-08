package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketResourcePackSend implements Packet<INetHandlerPlayClient> {
   private String field_179786_a;
   private String field_179785_b;

   public SPacketResourcePackSend() {
   }

   public SPacketResourcePackSend(String var1, String var2) {
      this.field_179786_a = ☃;
      this.field_179785_b = ☃;
      if (☃.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + ☃.length() + ")");
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179786_a = ☃.func_150789_c(32767);
      this.field_179785_b = ☃.func_150789_c(40);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_180714_a(this.field_179786_a);
      ☃.func_180714_a(this.field_179785_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_175095_a(this);
   }
}
