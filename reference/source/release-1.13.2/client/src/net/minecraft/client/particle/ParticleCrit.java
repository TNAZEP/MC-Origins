package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleCrit extends Particle {
   private final float field_174839_a;

   protected ParticleCrit(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(☃, ☃, ☃, ☃, ☃, ☃, ☃, 1.0F);
   }

   protected ParticleCrit(World var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i *= 0.1F;
      this.field_187130_j *= 0.1F;
      this.field_187131_k *= 0.1F;
      this.field_187129_i += ☃ * 0.4;
      this.field_187130_j += ☃ * 0.4;
      this.field_187131_k += ☃ * 0.4;
      float ☃ = (float)(Math.random() * 0.3F + 0.6F);
      this.field_70552_h = ☃;
      this.field_70553_i = ☃;
      this.field_70551_j = ☃;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= ☃;
      this.field_174839_a = this.field_70544_f;
      this.field_70547_e = (int)(6.0 / (Math.random() * 0.8 + 0.6));
      this.field_70547_e = (int)((float)this.field_70547_e * ☃);
      this.field_70547_e = Math.max(this.field_70547_e, 1);
      this.field_190017_n = false;
      this.func_70536_a(65);
      this.func_189213_a();
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e * 32.0F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      this.field_70544_f = this.field_174839_a * ☃;
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

      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_70553_i = (float)((double)this.field_70553_i * 0.96);
      this.field_70551_j = (float)((double)this.field_70551_j * 0.9);
      this.field_187129_i *= 0.7F;
      this.field_187130_j *= 0.7F;
      this.field_187131_k *= 0.7F;
      this.field_187130_j -= 0.02F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public static class DamageIndicatorFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleCrit(☃, ☃, ☃, ☃, ☃, ☃ + 1.0, ☃, 1.0F);
         ☃.func_187114_a(20);
         ☃.func_70536_a(67);
         return ☃;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleCrit(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   public static class MagicFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleCrit(☃, ☃, ☃, ☃, ☃, ☃, ☃);
         ☃.func_70538_b(☃.func_70534_d() * 0.3F, ☃.func_70542_f() * 0.8F, ☃.func_70535_g());
         ☃.func_94053_h();
         return ☃;
      }
   }
}
