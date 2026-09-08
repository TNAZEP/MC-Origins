package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.init.Particles;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.IRegistry;

public class SPacketParticles implements Packet<INetHandlerPlayClient> {
   private float field_149234_b;
   private float field_149235_c;
   private float field_149232_d;
   private float field_149233_e;
   private float field_149230_f;
   private float field_149231_g;
   private float field_149237_h;
   private int field_149238_i;
   private boolean field_179752_j;
   private IParticleData field_197700_j;

   public SPacketParticles() {
   }

   public <T extends IParticleData> SPacketParticles(
      T var1, boolean var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10
   ) {
      this.field_197700_j = ☃;
      this.field_179752_j = ☃;
      this.field_149234_b = ☃;
      this.field_149235_c = ☃;
      this.field_149232_d = ☃;
      this.field_149233_e = ☃;
      this.field_149230_f = ☃;
      this.field_149231_g = ☃;
      this.field_149237_h = ☃;
      this.field_149238_i = ☃;
   }

   @Override
   public void func_148837_a(PacketBuffer var1) throws IOException {
      ParticleType<?> ☃ = IRegistry.field_212632_u.func_148754_a(☃.readInt());
      if (☃ == null) {
         ☃ = Particles.field_197610_c;
      }

      this.field_179752_j = ☃.readBoolean();
      this.field_149234_b = ☃.readFloat();
      this.field_149235_c = ☃.readFloat();
      this.field_149232_d = ☃.readFloat();
      this.field_149233_e = ☃.readFloat();
      this.field_149230_f = ☃.readFloat();
      this.field_149231_g = ☃.readFloat();
      this.field_149237_h = ☃.readFloat();
      this.field_149238_i = ☃.readInt();
      this.field_197700_j = this.func_199855_a(☃, ☃);
   }

   private <T extends IParticleData> T func_199855_a(PacketBuffer var1, ParticleType<T> var2) {
      return ☃.func_197571_g().func_197543_b(☃, ☃);
   }

   @Override
   public void func_148840_b(PacketBuffer var1) throws IOException {
      ☃.writeInt(IRegistry.field_212632_u.func_148757_b(this.field_197700_j.func_197554_b()));
      ☃.writeBoolean(this.field_179752_j);
      ☃.writeFloat(this.field_149234_b);
      ☃.writeFloat(this.field_149235_c);
      ☃.writeFloat(this.field_149232_d);
      ☃.writeFloat(this.field_149233_e);
      ☃.writeFloat(this.field_149230_f);
      ☃.writeFloat(this.field_149231_g);
      ☃.writeFloat(this.field_149237_h);
      ☃.writeInt(this.field_149238_i);
      this.field_197700_j.func_197553_a(☃);
   }

   public boolean func_179750_b() {
      return this.field_179752_j;
   }

   public double func_149220_d() {
      return (double)this.field_149234_b;
   }

   public double func_149226_e() {
      return (double)this.field_149235_c;
   }

   public double func_149225_f() {
      return (double)this.field_149232_d;
   }

   public float func_149221_g() {
      return this.field_149233_e;
   }

   public float func_149224_h() {
      return this.field_149230_f;
   }

   public float func_149223_i() {
      return this.field_149231_g;
   }

   public float func_149227_j() {
      return this.field_149237_h;
   }

   public int func_149222_k() {
      return this.field_149238_i;
   }

   public IParticleData func_197699_j() {
      return this.field_197700_j;
   }

   public void func_148833_a(INetHandlerPlayClient var1) {
      ☃.func_147289_a(this);
   }
}
