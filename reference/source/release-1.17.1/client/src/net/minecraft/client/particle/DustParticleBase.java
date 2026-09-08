package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.util.Mth;

public class DustParticleBase<T extends DustParticleOptionsBase> extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected DustParticleBase(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, T var14, SpriteSet var15) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.friction = 0.96F;
      this.speedUpWhenYMotionIsBlocked = true;
      this.sprites = â˜ƒ;
      this.xd *= 0.1F;
      this.yd *= 0.1F;
      this.zd *= 0.1F;
      float â˜ƒ = this.random.nextFloat() * 0.4F + 0.6F;
      this.rCol = this.randomizeColor(â˜ƒ.getColor().x(), â˜ƒ);
      this.gCol = this.randomizeColor(â˜ƒ.getColor().y(), â˜ƒ);
      this.bCol = this.randomizeColor(â˜ƒ.getColor().z(), â˜ƒ);
      this.quadSize *= 0.75F * â˜ƒ.getScale();
      int â˜ƒx = (int)(8.0 / (this.random.nextDouble() * 0.8 + 0.2));
      this.lifetime = (int)Math.max((float)â˜ƒx * â˜ƒ.getScale(), 1.0F);
      this.setSpriteFromAge(â˜ƒ);
   }

   protected float randomizeColor(float var1, float var2) {
      return (this.random.nextFloat() * 0.2F + 0.8F) * â˜ƒ * â˜ƒ;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getQuadSize(float var1) {
      return this.quadSize * Mth.clamp(((float)this.age + â˜ƒ) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
   }
}
