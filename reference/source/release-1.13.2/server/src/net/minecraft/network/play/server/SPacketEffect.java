package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketEffect implements Packet<INetHandlerPlayClient> {
   private int field_149251_a;
   private BlockPos field_179747_b;
   private int field_149249_b;
   private boolean field_149246_f;

   public SPacketEffect() {
   }

   public SPacketEffect(int var1, BlockPos var2, int var3, boolean var4) {
      this.field_149251_a = ☃;
      this.field_179747_b = ☃.func_185334_h();
      this.field_149249_b = ☃;
      this.field_149246_f = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149251_a = ☃.readInt();
      this.field_179747_b = ☃.func_179259_c();
      this.field_149249_b = ☃.readInt();
      this.field_149246_f = ☃.readBoolean();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_149251_a);
      ☃.func_179255_a(this.field_179747_b);
      ☃.writeInt(this.field_149249_b);
      ☃.writeBoolean(this.field_149246_f);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147277_a(this);
   }
}
