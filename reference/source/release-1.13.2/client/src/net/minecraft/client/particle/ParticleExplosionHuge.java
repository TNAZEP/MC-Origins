package net.minecraft.client.particle;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.init.Particles;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleExplosionHuge extends Particle {
   private int field_70579_a;
   private final int field_70580_aq = 8;

   protected ParticleExplosionHuge(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, 0.0, 0.0, 0.0);
   }

   @Override
   public void func_180434_a(BufferBuilder var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   @Override
   public void func_189213_a() {
      for(int ☃ = 0; ☃ < 6; ++☃) {
         double ☃x = this.field_187126_f + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 4.0;
         double ☃xx = this.field_187127_g + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 4.0;
         double ☃xxx = this.field_187128_h + (this.field_187136_p.nextDouble() - this.field_187136_p.nextDouble()) * 4.0;
         this.field_187122_b.func_195594_a(Particles.field_197627_t, ☃x, ☃xx, ☃xxx, (double)((float)this.field_70579_a / (float)this.field_70580_aq), 0.0, 0.0);
      }

      ++this.field_70579_a;
      if (this.field_70579_a == this.field_70580_aq) {
         this.func_187112_i();
      }
   }

   @Override
   public int func_70537_b() {
      return 1;
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleExplosionHuge(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
