package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class PlayerCloudParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   PlayerCloudParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, SpriteSet var14) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.friction = 0.96F;
      this.sprites = â˜ƒ;
      float â˜ƒ = 2.5F;
      this.xd *= 0.1F;
      this.yd *= 0.1F;
      this.zd *= 0.1F;
      this.xd += â˜ƒ;
      this.yd += â˜ƒ;
      this.zd += â˜ƒ;
      float â˜ƒx = 1.0F - (float)(Math.random() * 0.3F);
      this.rCol = â˜ƒx;
      this.gCol = â˜ƒx;
      this.bCol = â˜ƒx;
      this.quadSize *= 1.875F;
      int â˜ƒxx = (int)(8.0 / (Math.random() * 0.8 + 0.3));
      this.lifetime = (int)Math.max((float)â˜ƒxx * 2.5F, 1.0F);
      this.hasPhysics = false;
      this.setSpriteFromAge(â˜ƒ);
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @Override
   public float getQuadSize(float var1) {
      return this.quadSize * Mth.clamp(((float)this.age + â˜ƒ) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.removed) {
         this.setSpriteFromAge(this.sprites);
         Player â˜ƒ = this.level.getNearestPlayer(this.x, this.y, this.z, 2.0, false);
         if (â˜ƒ != null) {
            double â˜ƒx = â˜ƒ.getY();
            if (this.y > â˜ƒx) {
               this.y += (â˜ƒx - this.y) * 0.2;
               this.yd += (â˜ƒ.getDeltaMovement().y - this.yd) * 0.2;
               this.setPos(this.x, this.y, this.z);
            }
         }
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         return new PlayerCloudParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.sprites);
      }
   }

   public static class SneezeProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public SneezeProvider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Particle â˜ƒ = new PlayerCloudParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, this.sprites);
         â˜ƒ.setColor(200.0F, 50.0F, 120.0F);
         â˜ƒ.setAlpha(0.4F);
         return â˜ƒ;
      }
   }
}
