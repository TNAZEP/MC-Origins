package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketBlockBreakAnim implements Packet<INetHandlerPlayClient> {
   private int field_148852_a;
   private BlockPos field_179822_b;
   private int field_148849_e;

   public SPacketBlockBreakAnim() {
   }

   public SPacketBlockBreakAnim(int var1, BlockPos var2, int var3) {
      this.field_148852_a = ☃;
      this.field_179822_b = ☃;
      this.field_148849_e = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148852_a = ☃.func_150792_a();
      this.field_179822_b = ☃.func_179259_c();
      this.field_148849_e = ☃.readUnsignedByte();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_148852_a);
      ☃.func_179255_a(this.field_179822_b);
      ☃.writeByte(this.field_148849_e);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147294_a(this);
   }
}
