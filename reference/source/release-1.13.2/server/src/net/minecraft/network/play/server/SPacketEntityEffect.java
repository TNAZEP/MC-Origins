package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SPacketEntityEffect implements Packet<INetHandlerPlayClient> {
   private int field_149434_a;
   private byte field_149432_b;
   private byte field_149433_c;
   private int field_149431_d;
   private byte field_186985_e;

   public SPacketEntityEffect() {
   }

   public SPacketEntityEffect(int var1, PotionEffect var2) {
      this.field_149434_a = ☃;
      this.field_149432_b = (byte)(Potion.func_188409_a(☃.func_188419_a()) & 0xFF);
      this.field_149433_c = (byte)(☃.func_76458_c() & 0xFF);
      if (☃.func_76459_b() > 32767) {
         this.field_149431_d = 32767;
      } else {
         this.field_149431_d = ☃.func_76459_b();
      }

      this.field_186985_e = 0;
      if (☃.func_82720_e()) {
         this.field_186985_e = (byte)(this.field_186985_e | 1);
      }

      if (☃.func_188418_e()) {
         this.field_186985_e = (byte)(this.field_186985_e | 2);
      }

      if (☃.func_205348_f()) {
         this.field_186985_e = (byte)(this.field_186985_e | 4);
      }
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      this.field_149434_a = ☃.func_150792_a();
      this.field_149432_b = ☃.readByte();
      this.field_149433_c = ☃.readByte();
      this.field_149431_d = ☃.func_150792_a();
      this.field_186985_e = ☃.readByte();
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.func_150787_b(this.field_149434_a);
      ☃.writeByte(this.field_149432_b);
      ☃.writeByte(this.field_149433_c);
      ☃.func_150787_b(this.field_149431_d);
      ☃.writeByte(this.field_186985_e);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147260_a(this);
   }
}
