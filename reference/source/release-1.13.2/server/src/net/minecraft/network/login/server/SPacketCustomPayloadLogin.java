package net.minecraft.network.login.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.util.ResourceLocation;

public class SPacketCustomPayloadLogin implements Packet<INetHandlerLoginClient> {
   private int field_209919_a;
   private ResourceLocation field_209920_b;
   private PacketBuffer field_209921_c;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_209919_a = ☃.func_150792_a();
      this.field_209920_b = ☃.func_192575_l();
      int ☃ = ☃.readableBytes();
      if (☃ >= 0 && ☃ <= 1048576) {
         this.field_209921_c = new PacketBuffer(☃.readBytes(☃));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_209919_a);
      ☃.func_192572_a(this.field_209920_b);
      ☃.writeBytes(this.field_209921_c.copy());
   }

   public void func_148833_a(INetHandlerLoginClient var1) {
      ☃.func_209521_a(this);
   }
}
