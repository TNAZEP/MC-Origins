package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.block.Block;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;

public class SPacketBlockAction implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179826_a;
   private int field_148872_d;
   private int field_148873_e;
   private Block field_148871_f;

   public SPacketBlockAction() {
   }

   public SPacketBlockAction(BlockPos var1, Block var2, int var3, int var4) {
      this.field_179826_a = ☃;
      this.field_148871_f = ☃;
      this.field_148872_d = ☃;
      this.field_148873_e = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179826_a = ☃.func_179259_c();
      this.field_148872_d = ☃.readUnsignedByte();
      this.field_148873_e = ☃.readUnsignedByte();
      this.field_148871_f = IRegistry.field_212618_g.func_148754_a(☃.func_150792_a());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_179826_a);
      ☃.writeByte(this.field_148872_d);
      ☃.writeByte(this.field_148873_e);
      ☃.func_150787_b(IRegistry.field_212618_g.func_148757_b(this.field_148871_f));
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147261_a(this);
   }
}
