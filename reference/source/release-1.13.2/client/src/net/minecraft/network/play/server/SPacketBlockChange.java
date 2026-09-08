package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SPacketBlockChange implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179828_a;
   private IBlockState field_197686_b;

   public SPacketBlockChange() {
   }

   public SPacketBlockChange(IBlockReader var1, BlockPos var2) {
      this.field_179828_a = ☃;
      this.field_197686_b = ☃.func_180495_p(☃);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179828_a = ☃.func_179259_c();
      this.field_197686_b = Block.field_176229_d.func_148745_a(☃.func_150792_a());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_179828_a);
      ☃.func_150787_b(Block.func_196246_j(this.field_197686_b));
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147234_a(this);
   }

   public IBlockState func_197685_a() {
      return this.field_197686_b;
   }

   public BlockPos func_179827_b() {
      return this.field_179828_a;
   }
}
