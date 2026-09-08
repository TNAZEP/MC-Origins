package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class ReversePortalParticle extends PortalParticle {
   ReversePortalParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.quadSize = (float)((double)this.quadSize * 1.5);
      this.lifetime = (int)(Math.random() * 2.0) + 60;
   }

   @Override
   public float getQuadSize(float var1) {
      float â˜ƒ = 1.0F - ((float)this.age + â˜ƒ) / ((float)this.lifetime * 1.5F);
      return this.quadSize * â˜ƒ;
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
         this.x += this.xd * (double)â˜ƒ;
         this.y += this.yd * (double)â˜ƒ;
         this.z += this.zd * (double)â˜ƒ;
      }
   }

   public static class ReversePortalProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public ReversePortalProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         ReversePortalParticle â˜ƒ = new ReversePortalParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
