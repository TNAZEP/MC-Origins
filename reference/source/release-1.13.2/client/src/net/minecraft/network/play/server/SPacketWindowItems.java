package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.NonNullList;

public class SPacketWindowItems implements Packet<INetHandlerPlayClient> {
   private int field_148914_a;
   private List<ItemStack> field_148913_b;

   public SPacketWindowItems() {
   }

   public SPacketWindowItems(int var1, NonNullList<ItemStack> var2) {
      this.field_148914_a = ☃;
      this.field_148913_b = NonNullList.<ItemStack>func_191197_a(☃.size(), ItemStack.field_190927_a);

      for(int ☃ = 0; ☃ < this.field_148913_b.size(); ++☃) {
         this.field_148913_b.set(☃, ☃.get(☃).func_77946_l());
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148914_a = ☃.readUnsignedByte();
      int ☃ = ☃.readShort();
      this.field_148913_b = NonNullList.<ItemStack>func_191197_a(☃, ItemStack.field_190927_a);

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.field_148913_b.set(☃x, ☃.func_150791_c());
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_148914_a);
      ☃.writeShort(this.field_148913_b.size());

      for(ItemStack ☃ : this.field_148913_b) {
         ☃.func_150788_a(☃);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147241_a(this);
   }

   public int func_148911_c() {
      return this.field_148914_a;
   }

   public List<ItemStack> func_148910_d() {
      return this.field_148913_b;
   }
}
