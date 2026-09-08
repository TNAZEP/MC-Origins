package net.minecraft.client.particle;

import java.util.Optional;
import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class SuspendedParticle extends TextureSheetParticle {
   SuspendedParticle(ClientLevel var1, SpriteSet var2, double var3, double var5, double var7) {
      super(â˜ƒ, â˜ƒ, â˜ƒ - 0.125, â˜ƒ);
      this.setSize(0.01F, 0.01F);
      this.pickSprite(â˜ƒ);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
      this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.gravity = 0.0F;
   }

   SuspendedParticle(ClientLevel var1, SpriteSet var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      super(â˜ƒ, â˜ƒ, â˜ƒ - 0.125, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setSize(0.01F, 0.01F);
      this.pickSprite(â˜ƒ);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
      this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.gravity = 0.0F;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public static class CrimsonSporeProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public CrimsonSporeProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         Random â˜ƒ = â˜ƒ.random;
         double â˜ƒx = â˜ƒ.nextGaussian() * 1.0E-6F;
         double â˜ƒxx = â˜ƒ.nextGaussian() * 1.0E-4F;
         double â˜ƒxxx = â˜ƒ.nextGaussian() * 1.0E-6F;
         SuspendedParticle â˜ƒxxxx = new SuspendedParticle(â˜ƒ, this.sprite, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
         â˜ƒxxxx.setColor(0.9F, 0.4F, 0.5F);
         return â˜ƒxxxx;
      }
   }

   public static class SporeBlossomAirProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public SporeBlossomAirProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         SuspendedParticle â˜ƒ = new SuspendedParticle(â˜ƒ, this.sprite, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, -0.8F, 0.0) {
            @Override
            public Optional<ParticleGroup> getParticleGroup() {
               return Optional.of(ParticleGroup.SPORE_BLOSSOM);
            }
         };
         â˜ƒ.lifetime = Mth.randomBetweenInclusive(â˜ƒ.random, 500, 1000);
         â˜ƒ.gravity = 0.01F;
         â˜ƒ.setColor(0.32F, 0.5F, 0.22F);
         return â˜ƒ;
      }
   }

   public static class UnderwaterProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public UnderwaterProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         SuspendedParticle â˜ƒ = new SuspendedParticle(â˜ƒ, this.sprite, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.setColor(0.4F, 0.4F, 0.7F);
         return â˜ƒ;
      }
   }

   public static class WarpedSporeProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public WarpedSporeProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         double â˜ƒ = (double)â˜ƒ.random.nextFloat() * -1.9 * (double)â˜ƒ.random.nextFloat() * 0.1;
         SuspendedParticle â˜ƒx = new SuspendedParticle(â˜ƒ, this.sprite, â˜ƒ, â˜ƒ, â˜ƒ, 0.0, â˜ƒ, 0.0);
         â˜ƒx.setColor(0.1F, 0.1F, 0.3F);
         â˜ƒx.setSize(0.001F, 0.001F);
         return â˜ƒx;
      }
   }
}
