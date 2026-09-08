package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.math.BlockPos;

public class SPacketSignEditorOpen implements Packet<INetHandlerPlayClient> {
   private BlockPos field_179778_a;

   public SPacketSignEditorOpen() {
   }

   public SPacketSignEditorOpen(BlockPos var1) {
      this.field_179778_a = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147268_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179778_a = ☃.func_179259_c();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_179778_a);
   }

   public BlockPos func_179777_a() {
      return this.field_179778_a;
   }
}
