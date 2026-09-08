package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;

public class SimpleAnimatedParticle extends TextureSheetParticle {
   protected final SpriteSet sprites;
   private float fadeR;
   private float fadeG;
   private float fadeB;
   private boolean hasFade;

   protected SimpleAnimatedParticle(ClientLevel var1, double var2, double var4, double var6, SpriteSet var8, float var9) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.friction = 0.91F;
      this.gravity = â˜ƒ;
      this.sprites = â˜ƒ;
   }

   public void setColor(int var1) {
      float â˜ƒ = (float)((â˜ƒ & 0xFF0000) >> 16) / 255.0F;
      float â˜ƒx = (float)((â˜ƒ & 0xFF00) >> 8) / 255.0F;
      float â˜ƒxx = (float)((â˜ƒ & 0xFF) >> 0) / 255.0F;
      float â˜ƒxxx = 1.0F;
      this.setColor(â˜ƒ * 1.0F, â˜ƒx * 1.0F, â˜ƒxx * 1.0F);
   }

   public void setFadeColor(int var1) {
      this.fadeR = (float)((â˜ƒ & 0xFF0000) >> 16) / 255.0F;
      this.fadeG = (float)((â˜ƒ & 0xFF00) >> 8) / 255.0F;
      this.fadeB = (float)((â˜ƒ & 0xFF) >> 0) / 255.0F;
      this.hasFade = true;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @Override
   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
      if (this.age > this.lifetime / 2) {
         this.setAlpha(1.0F - ((float)this.age - (float)(this.lifetime / 2)) / (float)this.lifetime);
         if (this.hasFade) {
            this.rCol += (this.fadeR - this.rCol) * 0.2F;
            this.gCol += (this.fadeG - this.gCol) * 0.2F;
            this.bCol += (this.fadeB - this.bCol) * 0.2F;
         }
      }
   }

   @Override
   public int getLightColor(float var1) {
      return 15728880;
   }
}
