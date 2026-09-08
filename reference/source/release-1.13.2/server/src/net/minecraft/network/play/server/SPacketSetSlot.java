package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketSetSlot implements Packet<INetHandlerPlayClient> {
   private int field_149179_a;
   private int field_149177_b;
   private ItemStack field_149178_c = ItemStack.field_190927_a;

   public SPacketSetSlot() {
   }

   public SPacketSetSlot(int var1, int var2, ItemStack var3) {
      this.field_149179_a = ☃;
      this.field_149177_b = ☃;
      this.field_149178_c = ☃.func_77946_l();
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147266_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149179_a = ☃.readByte();
      this.field_149177_b = ☃.readShort();
      this.field_149178_c = ☃.func_150791_c();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_149179_a);
      ☃.writeShort(this.field_149177_b);
      ☃.func_150788_a(this.field_149178_c);
   }
}
