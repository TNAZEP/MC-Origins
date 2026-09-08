package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketUnloadChunk implements Packet<INetHandlerPlayClient> {
   private int field_186942_a;
   private int field_186943_b;

   public SPacketUnloadChunk() {
   }

   public SPacketUnloadChunk(int var1, int var2) {
      this.field_186942_a = ☃;
      this.field_186943_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_186942_a = ☃.readInt();
      this.field_186943_b = ☃.readInt();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_186942_a);
      ☃.writeInt(this.field_186943_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_184326_a(this);
   }
}
