package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketChatMessage implements Packet<INetHandlerPlayServer> {
   private String field_149440_a;

   public CPacketChatMessage() {
   }

   public CPacketChatMessage(String var1) {
      if (☃.length() > 256) {
         ☃ = ☃.substring(0, 256);
      }

      this.field_149440_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149440_a = ☃.func_150789_c(256);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_180714_a(this.field_149440_a);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147354_a(this);
   }

   public String func_149439_c() {
      return this.field_149440_a;
   }
}
