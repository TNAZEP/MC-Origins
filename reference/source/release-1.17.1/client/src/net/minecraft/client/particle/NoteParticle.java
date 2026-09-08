package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class NoteParticle extends TextureSheetParticle {
   NoteParticle(ClientLevel var1, double var2, double var4, double var6, double var8) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.friction = 0.66F;
      this.speedUpWhenYMotionIsBlocked = true;
      this.xd *= 0.01F;
      this.yd *= 0.01F;
      this.zd *= 0.01F;
      this.yd += 0.2;
      this.rCol = Math.max(0.0F, Mth.sin(((float)â˜ƒ + 0.0F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.gCol = Math.max(0.0F, Mth.sin(((float)â˜ƒ + 0.33333334F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.bCol = Math.max(0.0F, Mth.sin(((float)â˜ƒ + 0.6666667F) * (float) (Math.PI * 2)) * 0.65F + 0.35F);
      this.quadSize *= 1.5F;
      this.lifetime = 6;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getQuadSize(float var1) {
      return this.quadSize * Mth.clamp(((float)this.age + â˜ƒ) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         NoteParticle â˜ƒ = new NoteParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
