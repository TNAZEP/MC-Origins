package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class HeartParticle extends TextureSheetParticle {
   HeartParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.speedUpWhenYMotionIsBlocked = true;
      this.friction = 0.86F;
      this.xd *= 0.01F;
      this.yd *= 0.01F;
      this.zd *= 0.01F;
      this.yd += 0.1;
      this.quadSize *= 1.5F;
      this.lifetime = 16;
      this.hasPhysics = false;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getQuadSize(float var1) {
      return this.quadSize * Mth.clamp(((float)this.age + â˜ƒ) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public static class AngryVillagerProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public AngryVillagerProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         HeartParticle â˜ƒ = new HeartParticle(â˜ƒ, â˜ƒ, â˜ƒ + 0.5, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         â˜ƒ.setColor(1.0F, 1.0F, 1.0F);
         return â˜ƒ;
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         HeartParticle â˜ƒ = new HeartParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
