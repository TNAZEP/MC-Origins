package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketWindowProperty implements Packet<INetHandlerPlayClient> {
   private int field_149186_a;
   private int field_149184_b;
   private int field_149185_c;

   public SPacketWindowProperty() {
   }

   public SPacketWindowProperty(int var1, int var2, int var3) {
      this.field_149186_a = ☃;
      this.field_149184_b = ☃;
      this.field_149185_c = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147245_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149186_a = ☃.readUnsignedByte();
      this.field_149184_b = ☃.readShort();
      this.field_149185_c = ☃.readShort();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_149186_a);
      ☃.writeShort(this.field_149184_b);
      ☃.writeShort(this.field_149185_c);
   }
}
