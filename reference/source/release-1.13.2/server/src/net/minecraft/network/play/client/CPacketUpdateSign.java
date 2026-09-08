package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.math.BlockPos;

public class CPacketUpdateSign implements Packet<INetHandlerPlayServer> {
   private BlockPos field_179723_a;
   private String[] field_149590_d;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179723_a = ☃.func_179259_c();
      this.field_149590_d = new String[4];

      for(int ☃ = 0; ☃ < 4; ++☃) {
         this.field_149590_d[☃] = ☃.func_150789_c(384);
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179255_a(this.field_179723_a);

      for(int ☃ = 0; ☃ < 4; ++☃) {
         ☃.func_180714_a(this.field_149590_d[☃]);
      }
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147343_a(this);
   }

   public BlockPos func_179722_a() {
      return this.field_179723_a;
   }

   public String[] func_187017_b() {
      return this.field_149590_d;
   }
}
