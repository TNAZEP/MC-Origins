package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketEntityAttach implements Packet<INetHandlerPlayClient> {
   private int field_149406_b;
   private int field_149407_c;

   public SPacketEntityAttach() {
   }

   public SPacketEntityAttach(Entity var1, @Nullable Entity var2) {
      this.field_149406_b = ☃.func_145782_y();
      this.field_149407_c = ☃ != null ? ☃.func_145782_y() : -1;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149406_b = ☃.readInt();
      this.field_149407_c = ☃.readInt();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(this.field_149406_b);
      ☃.writeInt(this.field_149407_c);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147243_a(this);
   }
}
