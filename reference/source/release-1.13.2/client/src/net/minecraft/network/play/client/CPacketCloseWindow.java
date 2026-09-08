package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketCloseWindow implements Packet<INetHandlerPlayServer> {
   private int field_149556_a;

   public CPacketCloseWindow() {
   }

   public CPacketCloseWindow(int var1) {
      this.field_149556_a = ☃;
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147356_a(this);
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149556_a = ☃.readByte();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeByte(this.field_149556_a);
   }
}
