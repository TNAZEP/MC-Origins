package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;

public class SPacketCustomPayload implements Packet<INetHandlerPlayClient> {
   public static final ResourceLocation field_209910_a = new ResourceLocation("minecraft:trader_list");
   public static final ResourceLocation field_209911_b = new ResourceLocation("minecraft:brand");
   public static final ResourceLocation field_209912_c = new ResourceLocation("minecraft:book_open");
   public static final ResourceLocation field_209913_d = new ResourceLocation("minecraft:debug/path");
   public static final ResourceLocation field_209914_e = new ResourceLocation("minecraft:debug/neighbors_update");
   public static final ResourceLocation field_209915_f = new ResourceLocation("minecraft:debug/caves");
   public static final ResourceLocation field_209916_g = new ResourceLocation("minecraft:debug/structures");
   public static final ResourceLocation field_209917_h = new ResourceLocation("minecraft:debug/worldgen_attempt");
   private ResourceLocation field_149172_a;
   private PacketBuffer field_149171_b;

   public SPacketCustomPayload() {
   }

   public SPacketCustomPayload(ResourceLocation var1, PacketBuffer var2) {
      this.field_149172_a = ☃;
      this.field_149171_b = ☃;
      if (☃.writerIndex() > 1048576) {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149172_a = ☃.func_192575_l();
      int ☃ = ☃.readableBytes();
      if (☃ >= 0 && ☃ <= 1048576) {
         this.field_149171_b = new PacketBuffer(☃.readBytes(☃));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_192572_a(this.field_149172_a);
      ☃.writeBytes(this.field_149171_b.copy());
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147240_a(this);
   }
}
