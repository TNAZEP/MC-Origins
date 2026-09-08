package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketKeepAlive implements Packet<INetHandlerPlayClient> {
   private long field_149136_a;

   public SPacketKeepAlive() {
   }

   public SPacketKeepAlive(long var1) {
      this.field_149136_a = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147272_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149136_a = ☃.readLong();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeLong(this.field_149136_a);
   }
}
