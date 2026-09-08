package net.minecraft.network.login.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

public class CPacketCustomPayloadLogin implements Packet<INetHandlerLoginServer> {
   private int field_209922_a;
   private PacketBuffer field_209923_b;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_209922_a = ☃.func_150792_a();
      if (☃.readBoolean()) {
         int ☃ = ☃.readableBytes();
         if (☃ < 0 || ☃ > 1048576) {
            throw new IOException("Payload may not be larger than 1048576 bytes");
         }

         this.field_209923_b = new PacketBuffer(☃.readBytes(☃));
      } else {
         this.field_209923_b = null;
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_209922_a);
      if (this.field_209923_b != null) {
         ☃.writeBoolean(true);
         ☃.writeBytes(this.field_209923_b.copy());
      } else {
         ☃.writeBoolean(false);
      }
   }

   public void func_148833_a(INetHandlerLoginServer var1) {
      ☃.func_209526_a(this);
   }
}
