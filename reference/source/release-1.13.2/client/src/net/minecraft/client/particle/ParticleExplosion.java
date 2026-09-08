package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleExplosion extends Particle {
   protected ParticleExplosion(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_187129_i = ☃ + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.field_187130_j = ☃ + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.field_187131_k = ☃ + (Math.random() * 2.0 - 1.0) * 0.05F;
      float ☃ = this.field_187136_p.nextFloat() * 0.3F + 0.7F;
      this.field_70552_h = ☃;
      this.field_70553_i = ☃;
      this.field_70551_j = ☃;
      this.field_70544_f = this.field_187136_p.nextFloat() * this.field_187136_p.nextFloat() * 6.0F + 1.0F;
      this.field_70547_e = (int)(16.0 / ((double)this.field_187136_p.nextFloat() * 0.8 + 0.2)) + 2;
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
      this.field_187130_j += 0.004;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.9F;
      this.field_187130_j *= 0.9F;
      this.field_187131_k *= 0.9F;
      if (this.field_187132_l) {
         this.field_187129_i *= 0.7F;
         this.field_187131_k *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleExplosion(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
