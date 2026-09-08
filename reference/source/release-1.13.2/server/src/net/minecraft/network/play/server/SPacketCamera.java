package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCamera implements Packet<INetHandlerPlayClient> {
   public int field_179781_a;

   public SPacketCamera() {
   }

   public SPacketCamera(Entity var1) {
      this.field_179781_a = ☃.func_145782_y();
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179781_a = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_179781_a);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_175094_a(this);
   }
}
