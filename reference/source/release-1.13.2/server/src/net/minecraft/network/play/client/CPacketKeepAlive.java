package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketKeepAlive implements Packet<INetHandlerPlayServer> {
   private long field_149461_a;

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147353_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149461_a = ☃.readLong();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeLong(this.field_149461_a);
   }

   public long func_149460_c() {
      return this.field_149461_a;
   }
}
