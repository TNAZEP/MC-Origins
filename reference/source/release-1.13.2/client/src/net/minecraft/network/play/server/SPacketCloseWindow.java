package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketCloseWindow implements Packet<INetHandlerPlayClient> {
   private int field_148896_a;

   public SPacketCloseWindow() {
   }

   public SPacketCloseWindow(int var1) {
      this.field_148896_a = ☃;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147276_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_148896_a = ☃.readUnsignedByte();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_148896_a);
   }
}
