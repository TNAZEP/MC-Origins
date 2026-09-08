package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;

public class BaseAshSmokeParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected BaseAshSmokeParticle(
      ClientLevel var1,
      double var2,
      double var4,
      double var6,
      float var8,
      float var9,
      float var10,
      double var11,
      double var13,
      double var15,
      float var17,
      SpriteSet var18,
      float var19,
      int var20,
      float var21,
      boolean var22
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.friction = 0.96F;
      this.gravity = â˜ƒ;
      this.speedUpWhenYMotionIsBlocked = true;
      this.sprites = â˜ƒ;
      this.xd *= (double)â˜ƒ;
      this.yd *= (double)â˜ƒ;
      this.zd *= (double)â˜ƒ;
      this.xd += â˜ƒ;
      this.yd += â˜ƒ;
      this.zd += â˜ƒ;
      float â˜ƒ = â˜ƒ.random.nextFloat() * â˜ƒ;
      this.rCol = â˜ƒ;
      this.gCol = â˜ƒ;
      this.bCol = â˜ƒ;
      this.quadSize *= 0.75F * â˜ƒ;
      this.lifetime = (int)((double)â˜ƒ / ((double)â˜ƒ.random.nextFloat() * 0.8 + 0.2));
      this.lifetime = (int)((float)this.lifetime * â˜ƒ);
      this.lifetime = Math.max(this.lifetime, 1);
      this.setSpriteFromAge(â˜ƒ);
      this.hasPhysics = â˜ƒ;
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
