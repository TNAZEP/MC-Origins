package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class CPacketPing implements Packet<INetHandlerStatusServer> {
   private long field_149290_a;

   public CPacketPing() {
   }

   public CPacketPing(long var1) {
      this.field_149290_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149290_a = ☃.readLong();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeLong(this.field_149290_a);
   }

   public void func_148833_a(INetHandlerStatusServer var1) {
      ☃.func_147311_a(this);
   }

   public long func_149289_c() {
      return this.field_149290_a;
   }
}
