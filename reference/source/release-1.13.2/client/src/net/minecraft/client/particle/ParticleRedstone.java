package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleRedstone extends Particle {
   private final float field_70570_a;

   public ParticleRedstone(World var1, double var2, double var4, double var6, double var8, double var10, double var12, RedstoneParticleData var14) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_187129_i *= 0.1F;
      this.field_187130_j *= 0.1F;
      this.field_187131_k *= 0.1F;
      float ☃ = (float)Math.random() * 0.4F + 0.6F;
      this.field_70552_h = ((float)(Math.random() * 0.2F) + 0.8F) * ☃.func_197562_c() * ☃;
      this.field_70553_i = ((float)(Math.random() * 0.2F) + 0.8F) * ☃.func_197563_d() * ☃;
      this.field_70551_j = ((float)(Math.random() * 0.2F) + 0.8F) * ☃.func_197561_e() * ☃;
      this.field_70544_f *= 0.75F;
      this.field_70544_f *= ☃.func_197560_f();
      this.field_70570_a = this.field_70544_f;
      this.field_70547_e = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.field_70547_e = (int)((float)this.field_70547_e * ☃.func_197560_f());
      this.field_70547_e = Math.max(this.field_70547_e, 1);
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float ☃ = ((float)this.field_70546_d + ☃) / (float)this.field_70547_e * 32.0F;
      ☃ = MathHelper.func_76131_a(☃, 0.0F, 1.0F);
      this.field_70544_f = this.field_70570_a * ☃;
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

      this.func_70536_a(7 - this.field_70546_d * 8 / this.field_70547_e);
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      if (this.field_187127_g == this.field_187124_d) {
         this.field_187129_i *= 1.1;
         this.field_187131_k *= 1.1;
      }

      this.field_187129_i *= 0.96F;
      this.field_187130_j *= 0.96F;
      this.field_187131_k *= 0.96F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory<RedstoneParticleData> {
      public Particle func_199234_a(RedstoneParticleData var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleRedstone(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
