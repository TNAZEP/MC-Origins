package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class AshParticle extends BaseAshSmokeParticle {
   protected AshParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14, SpriteSet var15) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, -0.1F, 0.1F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.5F, 20, 0.1F, false);
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new AshParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0, 1.0F, this.sprites);
      }
   }
}
