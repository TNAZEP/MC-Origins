package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumHand;

public class CPacketEditBook implements Packet<INetHandlerPlayServer> {
   private ItemStack field_210347_a;
   private boolean field_210348_b;
   private EnumHand field_212645_c;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_210347_a = ☃.func_150791_c();
      this.field_210348_b = ☃.readBoolean();
      this.field_212645_c = ☃.func_179257_a(EnumHand.class);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150788_a(this.field_210347_a);
      ☃.writeBoolean(this.field_210348_b);
      ☃.func_179249_a(this.field_212645_c);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_210156_a(this);
   }

   public ItemStack func_210346_a() {
      return this.field_210347_a;
   }

   public boolean func_210345_b() {
      return this.field_210348_b;
   }

   public EnumHand func_212644_d() {
      return this.field_212645_c;
   }
}
