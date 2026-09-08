package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleWaterWake extends Particle {
   protected ParticleWaterWake(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
      this.field_187129_i *= 0.3F;
      this.field_187130_j = Math.random() * 0.2F + 0.1F;
      this.field_187131_k *= 0.3F;
      this.field_70552_h = 1.0F;
      this.field_70553_i = 1.0F;
      this.field_70551_j = 1.0F;
      this.func_70536_a(19);
      this.func_187115_a(0.01F, 0.01F);
      this.field_70547_e = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.field_70545_g = 0.0F;
      this.field_187129_i = ☃;
      this.field_187130_j = ☃;
      this.field_187131_k = ☃;
   }

   @Override
   public void func_189213_a() {
      this.field_187123_c = this.field_187126_f;
      this.field_187124_d = this.field_187127_g;
      this.field_187125_e = this.field_187128_h;
      this.field_187130_j -= (double)this.field_70545_g;
      this.func_187110_a(this.field_187129_i, this.field_187130_j, this.field_187131_k);
      this.field_187129_i *= 0.98F;
      this.field_187130_j *= 0.98F;
      this.field_187131_k *= 0.98F;
      int ☃ = 60 - this.field_70547_e;
      float ☃x = (float)☃ * 0.001F;
      this.func_187115_a(☃x, ☃x);
      this.func_70536_a(19 + ☃ % 4);
      if (this.field_70547_e-- <= 0) {
         this.func_187112_i();
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleWaterWake(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
