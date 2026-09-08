package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleSpit extends ParticleExplosion {
   protected ParticleSpit(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      this.field_70545_g = 0.5F;
   }

   @Override
   public void func_189213_a() {
      super.func_189213_a();
      this.field_187130_j -= 0.004 + 0.04 * (double)this.field_70545_g;
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleSpit(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
