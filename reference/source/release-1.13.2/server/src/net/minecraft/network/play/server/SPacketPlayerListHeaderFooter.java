package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;

public class SPacketPlayerListHeaderFooter implements Packet<INetHandlerPlayClient> {
   private ITextComponent field_179703_a;
   private ITextComponent field_179702_b;

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_179703_a = ☃.func_179258_d();
      this.field_179702_b = ☃.func_179258_d();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_179256_a(this.field_179703_a);
      ☃.func_179256_a(this.field_179702_b);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_175096_a(this);
   }
}
