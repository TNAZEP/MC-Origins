package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityStatus implements Packet<INetHandlerPlayClient> {
   private int field_149164_a;
   private byte field_149163_b;

   public SPacketEntityStatus() {
   }

   public SPacketEntityStatus(Entity var1, byte var2) {
      this.field_149164_a = ☃.func_145782_y();
      this.field_149163_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149164_a = ☃.readInt();
      this.field_149163_b = ☃.readByte();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_149164_a);
      ☃.writeByte(this.field_149163_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147236_a(this);
   }
}
