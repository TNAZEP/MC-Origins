package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class HugeExplosionParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   HugeExplosionParticle(ClientLevel var1, double var2, double var4, double var6, double var8, SpriteSet var10) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.lifetime = 6 + this.random.nextInt(4);
      float â˜ƒ = this.random.nextFloat() * 0.6F + 0.4F;
      this.rCol = â˜ƒ;
      this.gCol = â˜ƒ;
      this.bCol = â˜ƒ;
      this.quadSize = 2.0F * (1.0F - (float)â˜ƒ * 0.5F);
      this.sprites = â˜ƒ;
      this.setSpriteFromAge(â˜ƒ);
   }

   @Override
   public int getLightColor(float var1) {
      return 15728880;
   }

   @Override
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         this.setSpriteFromAge(this.sprites);
      }
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new HugeExplosionParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.sprites);
      }
   }
}
