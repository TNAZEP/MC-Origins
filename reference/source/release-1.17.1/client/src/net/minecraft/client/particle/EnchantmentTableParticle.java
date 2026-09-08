package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class EnchantmentTableParticle extends TextureSheetParticle {
   private final double xStart;
   private final double yStart;
   private final double zStart;

   EnchantmentTableParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.xd = â˜ƒ;
      this.yd = â˜ƒ;
      this.zd = â˜ƒ;
      this.xStart = â˜ƒ;
      this.yStart = â˜ƒ;
      this.zStart = â˜ƒ;
      this.xo = â˜ƒ + â˜ƒ;
      this.yo = â˜ƒ + â˜ƒ;
      this.zo = â˜ƒ + â˜ƒ;
      this.x = this.xo;
      this.y = this.yo;
      this.z = this.zo;
      this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.2F);
      float â˜ƒ = this.random.nextFloat() * 0.6F + 0.4F;
      this.rCol = 0.9F * â˜ƒ;
      this.gCol = 0.9F * â˜ƒ;
      this.bCol = â˜ƒ;
      this.hasPhysics = false;
      this.lifetime = (int)(Math.random() * 10.0) + 30;
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
   public int getLightColor(float var1) {
      int â˜ƒ = super.getLightColor(â˜ƒ);
      float â˜ƒx = (float)this.age / (float)this.lifetime;
      â˜ƒx *= â˜ƒx;
      â˜ƒx *= â˜ƒx;
      int â˜ƒxx = â˜ƒ & 0xFF;
      int â˜ƒxxx = â˜ƒ >> 16 & 0xFF;
      â˜ƒxxx += (int)(â˜ƒx * 15.0F * 16.0F);
      if (â˜ƒxxx > 240) {
         â˜ƒxxx = 240;
      }

      return â˜ƒxx | â˜ƒxxx << 16;
   }

   @Override
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.age++ >= this.lifetime) {
         this.remove();
      } else {
         float â˜ƒ = (float)this.age / (float)this.lifetime;
         â˜ƒ = 1.0F - â˜ƒ;
         float â˜ƒx = 1.0F - â˜ƒ;
         â˜ƒx *= â˜ƒx;
         â˜ƒx *= â˜ƒx;
         this.x = this.xStart + this.xd * (double)â˜ƒ;
         this.y = this.yStart + this.yd * (double)â˜ƒ - (double)(â˜ƒx * 1.2F);
         this.z = this.zStart + this.zd * (double)â˜ƒ;
      }
   }

   public static class NautilusProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public NautilusProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         EnchantmentTableParticle â˜ƒ = new EnchantmentTableParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         EnchantmentTableParticle â˜ƒ = new EnchantmentTableParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
