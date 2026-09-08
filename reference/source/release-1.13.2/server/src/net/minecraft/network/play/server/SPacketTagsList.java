package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.tags.NetworkTagManager;

public class SPacketTagsList implements Packet<INetHandlerPlayClient> {
   private NetworkTagManager field_199859_a;

   public SPacketTagsList() {
   }

   public SPacketTagsList(NetworkTagManager var1) {
      this.field_199859_a = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_199859_a = NetworkTagManager.func_199714_b(☃);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      this.field_199859_a.func_199716_a(☃);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_199723_a(this);
   }
}
