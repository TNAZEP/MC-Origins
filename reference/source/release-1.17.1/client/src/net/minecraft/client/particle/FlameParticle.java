package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class FlameParticle extends RisingParticle {
   FlameParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void move(double var1, double var3, double var5) {
      this.setBoundingBox(this.getBoundingBox().move(â˜ƒ, â˜ƒ, â˜ƒ));
      this.setLocationFromBoundingbox();
   }

   @Override
   public float getQuadSize(float var1) {
      float â˜ƒ = ((float)this.age + â˜ƒ) / (float)this.lifetime;
      return this.quadSize * (1.0F - â˜ƒ * â˜ƒ * 0.5F);
   }

   @Override
   public int getLightColor(float var1) {
      float â˜ƒ = ((float)this.age + â˜ƒ) / (float)this.lifetime;
      â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
      int â˜ƒx = super.getLightColor(â˜ƒ);
      int â˜ƒxx = â˜ƒx & 0xFF;
      int â˜ƒxxx = â˜ƒx >> 16 & 0xFF;
      â˜ƒxx += (int)(â˜ƒ * 15.0F * 16.0F);
      if (â˜ƒxx > 240) {
         â˜ƒxx = 240;
      }

      return â˜ƒxx | â˜ƒxxx << 16;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         FlameParticle â˜ƒ = new FlameParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class SmallFlameProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public SmallFlameProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         FlameParticle â˜ƒ = new FlameParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         â˜ƒ.scale(0.5F);
         return â˜ƒ;
      }
   }
}
