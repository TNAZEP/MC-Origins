package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

public class SPacketStopSound implements Packet<INetHandlerPlayClient> {
   private ResourceLocation field_197705_a;
   private SoundCategory field_197706_b;

   public SPacketStopSound() {
   }

   public SPacketStopSound(@Nullable ResourceLocation var1, @Nullable SoundCategory var2) {
      this.field_197705_a = ☃;
      this.field_197706_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      int ☃ = ☃.readByte();
      if ((☃ & 1) > 0) {
         this.field_197706_b = ☃.func_179257_a(SoundCategory.class);
      }

      if ((☃ & 2) > 0) {
         this.field_197705_a = ☃.func_192575_l();
      }
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      if (this.field_197706_b != null) {
         if (this.field_197705_a != null) {
            ☃.writeByte(3);
            ☃.func_179249_a(this.field_197706_b);
            ☃.func_192572_a(this.field_197705_a);
         } else {
            ☃.writeByte(1);
            ☃.func_179249_a(this.field_197706_b);
         }
      } else if (this.field_197705_a != null) {
         ☃.writeByte(2);
         ☃.func_192572_a(this.field_197705_a);
      } else {
         ☃.writeByte(0);
      }
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_195512_a(this);
   }
}
