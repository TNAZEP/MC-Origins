package net.minecraft.network.status.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusClient;

public class SPacketPong implements Packet<INetHandlerStatusClient> {
   private long field_149293_a;

   public SPacketPong() {
   }

   public SPacketPong(long var1) {
      this.field_149293_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149293_a = ☃.readLong();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeLong(this.field_149293_a);
   }

   public void func_148833_a(INetHandlerStatusClient var1) {
      ☃.func_147398_a(this);
   }
}
