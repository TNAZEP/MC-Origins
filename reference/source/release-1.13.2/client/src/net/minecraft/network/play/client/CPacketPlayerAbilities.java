package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketPlayerAbilities implements Packet<INetHandlerPlayServer> {
   private boolean field_149500_a;
   private boolean field_149498_b;
   private boolean field_149499_c;
   private boolean field_149496_d;
   private float field_149497_e;
   private float field_149495_f;

   public CPacketPlayerAbilities() {
   }

   public CPacketPlayerAbilities(PlayerCapabilities var1) {
      this.func_149490_a(☃.field_75102_a);
      this.func_149483_b(☃.field_75100_b);
      this.func_149491_c(☃.field_75101_c);
      this.func_149493_d(☃.field_75098_d);
      this.func_149485_a(☃.func_75093_a());
      this.func_149492_b(☃.func_75094_b());
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      byte ☃ = ☃.readByte();
      this.func_149490_a((☃ & 1) > 0);
      this.func_149483_b((☃ & 2) > 0);
      this.func_149491_c((☃ & 4) > 0);
      this.func_149493_d((☃ & 8) > 0);
      this.func_149485_a(☃.readFloat());
      this.func_149492_b(☃.readFloat());
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      byte ☃ = 0;
      if (this.func_149494_c()) {
         ☃ = (byte)(☃ | 1);
      }

      if (this.func_149488_d()) {
         ☃ = (byte)(☃ | 2);
      }

      if (this.func_149486_e()) {
         ☃ = (byte)(☃ | 4);
      }

      if (this.func_149484_f()) {
         ☃ = (byte)(☃ | 8);
      }

      ☃.writeByte(☃);
      ☃.writeFloat(this.field_149497_e);
      ☃.writeFloat(this.field_149495_f);
   }

   public void func_148833_a(INetHandlerPlayServer var1) {
      ☃.func_147348_a(this);
   }

   public boolean func_149494_c() {
      return this.field_149500_a;
   }

   public void func_149490_a(boolean var1) {
      this.field_149500_a = ☃;
   }

   public boolean func_149488_d() {
      return this.field_149498_b;
   }

   public void func_149483_b(boolean var1) {
      this.field_149498_b = ☃;
   }

   public boolean func_149486_e() {
      return this.field_149499_c;
   }

   public void func_149491_c(boolean var1) {
      this.field_149499_c = ☃;
   }

   public boolean func_149484_f() {
      return this.field_149496_d;
   }

   public void func_149493_d(boolean var1) {
      this.field_149496_d = ☃;
   }

   public void func_149485_a(float var1) {
      this.field_149497_e = ☃;
   }

   public void func_149492_b(float var1) {
      this.field_149495_f = ☃;
   }
}
