package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class LavaParticle extends TextureSheetParticle {
   LavaParticle(ClientLevel var1, double var2, double var4, double var6) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, 0.0, 0.0);
      this.gravity = 0.75F;
      this.friction = 0.999F;
      this.xd *= 0.8F;
      this.yd *= 0.8F;
      this.zd *= 0.8F;
      this.yd = (double)(this.random.nextFloat() * 0.4F + 0.05F);
      this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
      this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public int getLightColor(float var1) {
      int â˜ƒ = super.getLightColor(â˜ƒ);
      int â˜ƒx = 240;
      int â˜ƒxx = â˜ƒ >> 16 & 0xFF;
      return 240 | â˜ƒxx << 16;
   }

   @Override
   public float getQuadSize(float var1) {
      float â˜ƒ = ((float)this.age + â˜ƒ) / (float)this.lifetime;
      return this.quadSize * (1.0F - â˜ƒ * â˜ƒ);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.removed) {
         float â˜ƒ = (float)this.age / (float)this.lifetime;
         if (this.random.nextFloat() > â˜ƒ) {
            this.level.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.xd, this.yd, this.zd);
         }
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         LavaParticle â˜ƒ = new LavaParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
