package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;

public class CPacketNBTQueryTileEntity implements Packet<INetHandlerPlayServer> {
   private int field_211718_a;
   private BlockPos field_211719_b;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_211718_a = ☃.func_150792_a();
      this.field_211719_b = ☃.func_179259_c();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_211718_a);
      ☃.func_179255_a(this.field_211719_b);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_211525_a(this);
   }

   public int func_211716_b() {
      return this.field_211718_a;
   }

   public BlockPos func_211717_c() {
      return this.field_211719_b;
   }
}
