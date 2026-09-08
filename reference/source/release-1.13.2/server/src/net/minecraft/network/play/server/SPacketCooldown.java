package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCooldown implements Packet<INetHandlerPlayClient> {
   private Item field_186923_a;
   private int field_186924_b;

   public SPacketCooldown() {
   }

   public SPacketCooldown(Item var1, int var2) {
      this.field_186923_a = ☃;
      this.field_186924_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_186923_a = Item.func_150899_d(☃.func_150792_a());
      this.field_186924_b = ☃.func_150792_a();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(Item.func_150891_b(this.field_186923_a));
      ☃.func_150787_b(this.field_186924_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184324_a(this);
   }
}
