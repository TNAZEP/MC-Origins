package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketConfirmTransaction implements Packet<INetHandlerPlayServer> {
   private int field_149536_a;
   private short field_149534_b;
   private boolean field_149535_c;

   public CPacketConfirmTransaction() {
   }

   public CPacketConfirmTransaction(int var1, short var2, boolean var3) {
      this.field_149536_a = ☃;
      this.field_149534_b = ☃;
      this.field_149535_c = ☃;
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147339_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149536_a = ☃.readByte();
      this.field_149534_b = ☃.readShort();
      this.field_149535_c = ☃.readByte() != 0;
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_149536_a);
      ☃.writeShort(this.field_149534_b);
      ☃.writeByte(this.field_149535_c ? 1 : 0);
   }

   public int func_149532_c() {
      return this.field_149536_a;
   }

   public short func_149533_d() {
      return this.field_149534_b;
   }
}
