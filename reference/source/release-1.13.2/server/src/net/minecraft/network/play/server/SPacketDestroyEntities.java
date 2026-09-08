package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketDestroyEntities implements Packet<INetHandlerPlayClient> {
   private int[] field_149100_a;

   public SPacketDestroyEntities() {
   }

   public SPacketDestroyEntities(int... var1) {
      this.field_149100_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149100_a = new int[☃.func_150792_a()];

      for(int ☃ = 0; ☃ < this.field_149100_a.length; ++☃) {
         this.field_149100_a[☃] = ☃.func_150792_a();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149100_a.length);

      for(int ☃ : this.field_149100_a) {
         ☃.func_150787_b(☃);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147238_a(this);
   }
}
