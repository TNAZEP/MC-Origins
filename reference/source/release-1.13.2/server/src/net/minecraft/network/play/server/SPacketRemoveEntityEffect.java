package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.Potion;

public class SPacketRemoveEntityEffect implements Packet<INetHandlerPlayClient> {
   private int field_149079_a;
   private Potion field_149078_b;

   public SPacketRemoveEntityEffect() {
   }

   public SPacketRemoveEntityEffect(int var1, Potion var2) {
      this.field_149079_a = ☃;
      this.field_149078_b = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149079_a = ☃.func_150792_a();
      this.field_149078_b = Potion.func_188412_a(☃.readUnsignedByte());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149079_a);
      ☃.writeByte(Potion.func_188409_a(this.field_149078_b));
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147262_a(this);
   }
}
