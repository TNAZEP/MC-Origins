package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class ExplodeParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected ExplodeParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, SpriteSet var14) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.gravity = -0.1F;
      this.friction = 0.9F;
      this.sprites = â˜ƒ;
      this.xd = â˜ƒ + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.yd = â˜ƒ + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.zd = â˜ƒ + (Math.random() * 2.0 - 1.0) * 0.05F;
      float â˜ƒ = this.random.nextFloat() * 0.3F + 0.7F;
      this.rCol = â˜ƒ;
      this.gCol = â˜ƒ;
      this.bCol = â˜ƒ;
      this.quadSize = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 6.0F + 1.0F);
      this.lifetime = (int)(16.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
      this.setSpriteFromAge(â˜ƒ);
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new ExplodeParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.sprites);
      }
   }
}
