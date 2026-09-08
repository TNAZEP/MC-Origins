package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSetPassengers implements Packet<INetHandlerPlayClient> {
   private int field_186973_a;
   private int[] field_186974_b;

   public SPacketSetPassengers() {
   }

   public SPacketSetPassengers(Entity var1) {
      this.field_186973_a = ☃.func_145782_y();
      List<Entity> ☃ = ☃.func_184188_bt();
      this.field_186974_b = new int[☃.size()];

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         this.field_186974_b[☃x] = ((Entity)☃.get(☃x)).func_145782_y();
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_186973_a = ☃.func_150792_a();
      this.field_186974_b = ☃.func_186863_b();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_186973_a);
      ☃.func_186875_a(this.field_186974_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184328_a(this);
   }
}
