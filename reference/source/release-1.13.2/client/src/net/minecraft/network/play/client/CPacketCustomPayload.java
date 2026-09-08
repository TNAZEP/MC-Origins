package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.ResourceLocation;

public class CPacketCustomPayload implements Packet<INetHandlerPlayServer> {
   public static final ResourceLocation field_210344_a = new ResourceLocation("minecraft:brand");
   private ResourceLocation field_149562_a;
   private PacketBuffer field_149561_c;

   public CPacketCustomPayload() {
   }

   public CPacketCustomPayload(ResourceLocation var1, PacketBuffer var2) {
      this.field_149562_a = ☃;
      this.field_149561_c = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149562_a = ☃.func_192575_l();
      int ☃ = ☃.readableBytes();
      if (☃ >= 0 && ☃ <= 32767) {
         this.field_149561_c = new PacketBuffer(☃.readBytes(☃));
      } else {
         throw new IOException("Payload may not be larger than 32767 bytes");
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_192572_a(this.field_149562_a);
      ☃.writeBytes(this.field_149561_c);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147349_a(this);
      if (this.field_149561_c != null) {
         this.field_149561_c.release();
      }
   }
}
