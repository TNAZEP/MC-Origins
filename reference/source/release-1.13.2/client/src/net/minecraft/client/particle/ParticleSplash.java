package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.world.World;

public class ParticleSplash extends ParticleRain {
   protected ParticleSplash(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(☃, ☃, ☃, ☃);
      this.field_70545_g = 0.04F;
      this.func_70536_a(20 + this.field_187136_p.nextInt(3));
      if (☃ == 0.0 && (☃ != 0.0 || ☃ != 0.0)) {
         this.field_187129_i = ☃;
         this.field_187130_j = 0.1;
         this.field_187131_k = ☃;
      }
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      public Particle func_199234_a(BasicParticleType var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ParticleSplash(☃, ☃, ☃, ☃, ☃, ☃, ☃);
      }
   }
}
