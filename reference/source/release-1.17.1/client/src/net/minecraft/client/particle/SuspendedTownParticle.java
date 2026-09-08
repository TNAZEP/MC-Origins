package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

public class SuspendedTownParticle extends TextureSheetParticle {
   SuspendedTownParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = this.random.nextFloat() * 0.1F + 0.2F;
      this.rCol = â˜ƒ;
      this.gCol = â˜ƒ;
      this.bCol = â˜ƒ;
      this.setSize(0.02F, 0.02F);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.5F;
      this.xd *= 0.02F;
      this.yd *= 0.02F;
      this.zd *= 0.02F;
      this.lifetime = (int)(20.0 / (Math.random() * 0.8 + 0.2));
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
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      if (this.lifetime-- <= 0) {
         this.remove();
      } else {
         this.move(this.xd, this.yd, this.zd);
         this.xd *= 0.99;
         this.yd *= 0.99;
         this.zd *= 0.99;
      }
   }

   public static class ComposterFillProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public ComposterFillProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         SuspendedTownParticle â˜ƒ = new SuspendedTownParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         â˜ƒ.setColor(1.0F, 1.0F, 1.0F);
         â˜ƒ.setLifetime(3 + â˜ƒ.getRandom().nextInt(5));
         return â˜ƒ;
      }
   }

   public static class DolphinSpeedProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public DolphinSpeedProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         SuspendedTownParticle â˜ƒ = new SuspendedTownParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.setColor(0.3F, 0.5F, 1.0F);
         â˜ƒ.pickSprite(this.sprite);
         â˜ƒ.setAlpha(1.0F - â˜ƒ.random.nextFloat() * 0.7F);
         â˜ƒ.setLifetime(â˜ƒ.getLifetime() / 2);
         return â˜ƒ;
      }
   }

   public static class HappyVillagerProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public HappyVillagerProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         SuspendedTownParticle â˜ƒ = new SuspendedTownParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         â˜ƒ.setColor(1.0F, 1.0F, 1.0F);
         return â˜ƒ;
      }
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         SuspendedTownParticle â˜ƒ = new SuspendedTownParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
