package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketUseBed implements Packet<INetHandlerPlayClient> {
   private int field_149097_a;
   private BlockPos field_179799_b;

   public SPacketUseBed() {
   }

   public SPacketUseBed(EntityPlayer var1, BlockPos var2) {
      this.field_149097_a = ☃.func_145782_y();
      this.field_179799_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149097_a = ☃.func_150792_a();
      this.field_179799_b = ☃.func_179259_c();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149097_a);
      ☃.func_179255_a(this.field_179799_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147278_a(this);
   }
}
