package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCollectItem implements Packet<INetHandlerPlayClient> {
   private int field_149357_a;
   private int field_149356_b;
   private int field_191209_c;

   public SPacketCollectItem() {
   }

   public SPacketCollectItem(int var1, int var2, int var3) {
      this.field_149357_a = ☃;
      this.field_149356_b = ☃;
      this.field_191209_c = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149357_a = ☃.func_150792_a();
      this.field_149356_b = ☃.func_150792_a();
      this.field_191209_c = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149357_a);
      ☃.func_150787_b(this.field_149356_b);
      ☃.func_150787_b(this.field_191209_c);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147246_a(this);
   }
}
