package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class WhiteAshParticle extends BaseAshSmokeParticle {
   private static final int COLOR_RGB24 = 12235202;

   protected WhiteAshParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14, SpriteSet var15) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, -0.1F, 0.1F, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.0F, 20, 0.0125F, false);
      this.rCol = 0.7294118F;
      this.gCol = 0.69411767F;
      this.bCol = 0.7607843F;
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Random â˜ƒ = â˜ƒ.random;
         double â˜ƒx = (double)â˜ƒ.nextFloat() * -1.9 * (double)â˜ƒ.nextFloat() * 0.1;
         double â˜ƒxx = (double)â˜ƒ.nextFloat() * -0.5 * (double)â˜ƒ.nextFloat() * 0.1 * 5.0;
         double â˜ƒxxx = (double)â˜ƒ.nextFloat() * -1.9 * (double)â˜ƒ.nextFloat() * 0.1;
         return new WhiteAshParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx, 1.0F, this.sprites);
      }
   }
}
