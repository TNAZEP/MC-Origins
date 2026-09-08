package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class SPacketPlayerAbilities implements Packet<INetHandlerPlayClient> {
   private boolean field_149119_a;
   private boolean field_149117_b;
   private boolean field_149118_c;
   private boolean field_149115_d;
   private float field_149116_e;
   private float field_149114_f;

   public SPacketPlayerAbilities() {
   }

   public SPacketPlayerAbilities(PlayerCapabilities var1) {
      this.func_149108_a(☃.field_75102_a);
      this.func_149102_b(☃.field_75100_b);
      this.func_149109_c(☃.field_75101_c);
      this.func_149111_d(☃.field_75098_d);
      this.func_149104_a(☃.func_75093_a());
      this.func_149110_b(☃.func_75094_b());
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      byte ☃ = ☃.readByte();
      this.func_149108_a((☃ & 1) > 0);
      this.func_149102_b((☃ & 2) > 0);
      this.func_149109_c((☃ & 4) > 0);
      this.func_149111_d((☃ & 8) > 0);
      this.func_149104_a(☃.readFloat());
      this.func_149110_b(☃.readFloat());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      byte ☃ = 0;
      if (this.func_149112_c()) {
         ☃ = (byte)(☃ | 1);
      }

      if (this.func_149106_d()) {
         ☃ = (byte)(☃ | 2);
      }

      if (this.func_149105_e()) {
         ☃ = (byte)(☃ | 4);
      }

      if (this.func_149103_f()) {
         ☃ = (byte)(☃ | 8);
      }

      ☃.writeByte(☃);
      ☃.writeFloat(this.field_149116_e);
      ☃.writeFloat(this.field_149114_f);
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147270_a(this);
   }

   public boolean func_149112_c() {
      return this.field_149119_a;
   }

   public void func_149108_a(boolean var1) {
      this.field_149119_a = ☃;
   }

   public boolean func_149106_d() {
      return this.field_149117_b;
   }

   public void func_149102_b(boolean var1) {
      this.field_149117_b = ☃;
   }

   public boolean func_149105_e() {
      return this.field_149118_c;
   }

   public void func_149109_c(boolean var1) {
      this.field_149118_c = ☃;
   }

   public boolean func_149103_f() {
      return this.field_149115_d;
   }

   public void func_149111_d(boolean var1) {
      this.field_149115_d = ☃;
   }

   public void func_149104_a(float var1) {
      this.field_149116_e = ☃;
   }

   public void func_149110_b(float var1) {
      this.field_149114_f = ☃;
   }
}
