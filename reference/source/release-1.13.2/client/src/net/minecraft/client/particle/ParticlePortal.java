package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticlePortal extends Particle {
   private final float field_70571_a;
   private final double field_70574_aq;
   private final double field_70573_ar;
   private final double field_70572_as;

   protected ParticlePortal(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_187129_i = ☃;
      this.field_187130_j = ☃;
      this.field_187131_k = ☃;
      this.field_187126_f = ☃;
      this.field_187127_g = ☃;
      this.field_187128_h = ☃;
      this.field_70574_aq = this.field_187126_f;
      this.field_70573_ar = this.field_187127_g;
      this.field_70572_as = this.field_187128_h;
      float ☃ = this.field_187136_p.nextFloat() * 0.6F + 0.4F;
      this.field_70544_f = this.field_187136_p.nextFloat() * 0.2F + 0.5F;
      this.field_70571_a = this.field_70544_f;
      this.field_70552_h = ☃ * 0.9F;
      this.field_70553_i = ☃ * 0.3F;
      this.field_70551_j = ☃;
      this.field_70547_e = (int)(Math.random() * 10.0) + 40;
      this.func_70536_a((int)(Math.random() * 8.0));
   }

   @Override
   public void func_187110_a(double var1, double var3, double var5) {
      this.func_187108_a(this.func_187116_l().func_72317_d(☃, ☃, ☃));
      this.func_187118_j();
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e;
      ☃ = 1.0F - ☃;
      ☃ *= ☃;
      ☃ = 1.0F - ☃;
      this.field_70544_f = this.field_70571_a * ☃;
      super.func_180434_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public int func_189214_a(float var1) {
      int ☃ = super.func_189214_a(☃);
      float ☃x = (float)this.field_70546_d / (float)this.field_70547_e;
      ☃x *= ☃x;
      ☃x *= ☃x;
      int ☃xx = ☃ & 0xFF;
      int ☃xxx = ☃ >> 16 & 0xFF;
      ☃xxx += (int)(☃x * 15.0F * 16.0F);
      if (☃xxx > 240) {
         ☃xxx = 240;
      }

      return ☃xx | ☃xxx << 16;
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      float ☃ = (float)this.field_70546_d / (float)this.field_70547_e;
      float var3 = -☃ + ☃ * ☃ * 2.0F;
      float var4 = 1.0F - var3;
      this.field_187126_f = this.field_70574_aq + this.field_187129_i * (double)var4;
      this.field_187127_g = this.field_70573_ar + this.field_187130_j * (double)var4 + (double)(1.0F - ☃);
      this.field_187128_h = this.field_70572_as + this.field_187131_k * (double)var4;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticlePortal(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
