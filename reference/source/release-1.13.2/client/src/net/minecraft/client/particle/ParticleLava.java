package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Particles;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleLava extends Particle {
   private final float field_70586_a;

   protected ParticleLava(World var1, double var2, double var4, double var6) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i *= 0.8F;
      this.field_187130_j *= 0.8F;
      this.field_187131_k *= 0.8F;
      this.field_187130_j = (double)(this.field_187136_p.nextFloat() * 0.4F + 0.05F);
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.field_70544_f *= this.field_187136_p.nextFloat() * 2.0F + 0.2F;
      this.field_70586_a = this.field_70544_f;
      this.field_70547_e = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.func_70536_a(49);
   }

   @Override
   public int func_189214_a(float var1) {
      int ☃ = super.func_189214_a(☃);
      int ☃x = 240;
      int ☃xx = ☃ >> 16 & 0xFF;
      return 240 | ☃xx << 16;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e;
      this.field_70544_f = this.field_70586_a * (1.0F - ☃ * ☃);
      super.func_180434_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      if (this.field_70546_d++ >= this.field_70547_e) {
         this.func_187112_i();
      }

      float ☃ = (float)this.field_70546_d / (float)this.field_70547_e;
      if (this.field_187136_p.nextFloat() > ☃) {
         this.field_187122_b
            .func_195594_a(
               Particles.field_197601_L,
               this.field_187126_f,
               this.field_187127_g,
               this.field_187128_h,
               this.field_187129_i,
               this.field_187130_j,
               this.field_187131_k
            );
      }

      this.field_187130_j -= 0.03;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.999F;
      this.field_187130_j *= 0.999F;
      this.field_187131_k *= 0.999F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleLava(☃, ☃, ☃, ☃);
      }
   }
}
