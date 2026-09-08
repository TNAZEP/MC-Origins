package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleHeart extends Particle {
   private final float field_70575_a;

   protected ParticleHeart(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(☃, ☃, ☃, ☃, ☃, ☃, ☃, 2.0F);
   }

   protected ParticleHeart(World var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i *= 0.01F;
      this.field_187130_j *= 0.01F;
      this.field_187131_k *= 0.01F;
      this.field_187130_j += 0.1;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= ☃;
      this.field_70575_a = this.field_70544_f;
      this.field_70547_e = 16;
      this.func_70536_a(80);
      this.field_190017_n = false;
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e * 32.0F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      this.field_70544_f = this.field_70575_a * ☃;
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
      if (this.field_187127_g == this.field_187124_d) {
         this.field_187129_i *= 1.1;
         this.field_187131_k *= 1.1;
      }

      this.field_187129_i *= 0.86F;
      this.field_187130_j *= 0.86F;
      this.field_187131_k *= 0.86F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public static class AngryVillagerFactory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle ☃ = new ParticleHeart(☃, ☃, ☃ + 0.5, ☃, ☃, ☃, ☃);
         ☃.func_70536_a(81);
         ☃.func_70538_b(1.0F, 1.0F, 1.0F);
         return ☃;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleHeart(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
