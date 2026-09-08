package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketTimeUpdate implements Packet<INetHandlerPlayClient> {
   private long field_149369_a;
   private long field_149368_b;

   public SPacketTimeUpdate() {
   }

   public SPacketTimeUpdate(long var1, long var3, boolean var5) {
      this.field_149369_a = ☃;
      this.field_149368_b = ☃;
      if (!☃) {
         this.field_149368_b = -this.field_149368_b;
         if (this.field_149368_b == 0L) {
            this.field_149368_b = -1L;
         }
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149369_a = ☃.readLong();
      this.field_149368_b = ☃.readLong();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeLong(this.field_149369_a);
      ☃.writeLong(this.field_149368_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147285_a(this);
   }
}
