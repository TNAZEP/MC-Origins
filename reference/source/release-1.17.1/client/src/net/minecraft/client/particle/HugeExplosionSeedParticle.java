package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class HugeExplosionSeedParticle extends NoRenderParticle {
   private int life;
   private final int lifeTime = 8;

   HugeExplosionSeedParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
   }

   @Override
   public void tick() {
      for(int â˜ƒ = 0; â˜ƒ < 6; ++â˜ƒ) {
         double â˜ƒx = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
         double â˜ƒxx = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
         double â˜ƒxxx = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
         this.level.addParticle(ParticleTypes.EXPLOSION, â˜ƒx, â˜ƒxx, â˜ƒxxx, (double)((float)this.life / (float)this.lifeTime), 0.0, 0.0);
      }

      ++this.life;
      if (this.life == this.lifeTime) {
         this.remove();
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new HugeExplosionSeedParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
