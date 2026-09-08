package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityHeadLook implements Packet<INetHandlerPlayClient> {
   private int field_149384_a;
   private byte field_149383_b;

   public SPacketEntityHeadLook() {
   }

   public SPacketEntityHeadLook(Entity var1, byte var2) {
      this.field_149384_a = ☃.func_145782_y();
      this.field_149383_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149384_a = ☃.func_150792_a();
      this.field_149383_b = ☃.readByte();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149384_a);
      ☃.writeByte(this.field_149383_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147267_a(this);
   }
}
