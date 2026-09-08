package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketConfirmTransaction implements Packet<INetHandlerPlayClient> {
   private int field_148894_a;
   private short field_148892_b;
   private boolean field_148893_c;

   public SPacketConfirmTransaction() {
   }

   public SPacketConfirmTransaction(int var1, short var2, boolean var3) {
      this.field_148894_a = ☃;
      this.field_148892_b = ☃;
      this.field_148893_c = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147239_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148894_a = ☃.readUnsignedByte();
      this.field_148892_b = ☃.readShort();
      this.field_148893_c = ☃.readBoolean();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_148894_a);
      ☃.writeShort(this.field_148892_b);
      ☃.writeBoolean(this.field_148893_c);
   }
}
