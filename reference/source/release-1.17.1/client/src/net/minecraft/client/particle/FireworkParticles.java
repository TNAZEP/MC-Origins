package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;

public class FireworkParticles {
   public static class FlashProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public FlashProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         FireworkParticles.OverlayParticle â˜ƒ = new FireworkParticles.OverlayParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class OverlayParticle extends TextureSheetParticle {
      OverlayParticle(ClientLevel var1, double var2, double var4, double var6) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.lifetime = 4;
      }

      @Override
      public ParticleRenderType getRenderType() {
         return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
      }

      @Override
      public void render(VertexConsumer var1, Camera var2, float var3) {
         this.setAlpha(0.6F - ((float)this.age + â˜ƒ - 1.0F) * 0.25F * 0.5F);
         super.render(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      public float getQuadSize(float var1) {
         return 7.1F * Mth.sin(((float)this.age + â˜ƒ - 1.0F) * 0.25F * (float) Math.PI);
      }
   }

   static class SparkParticle extends SimpleAnimatedParticle {
      private boolean trail;
      private boolean flicker;
      private final ParticleEngine engine;
      private float fadeR;
      private float fadeG;
      private float fadeB;
      private boolean hasFade;

      SparkParticle(ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, ParticleEngine var14, SpriteSet var15) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.1F);
         this.xd = â˜ƒ;
         this.yd = â˜ƒ;
         this.zd = â˜ƒ;
         this.engine = â˜ƒ;
         this.quadSize *= 0.75F;
         this.lifetime = 48 + this.random.nextInt(12);
         this.setSpriteFromAge(â˜ƒ);
      }

      public void setTrail(boolean var1) {
         this.trail = â˜ƒ;
      }

      public void setFlicker(boolean var1) {
         this.flicker = â˜ƒ;
      }

      @Override
      public void render(VertexConsumer var1, Camera var2, float var3) {
         if (!this.flicker || this.age < this.lifetime / 3 || (this.age + this.lifetime) / 3 % 2 == 0) {
            super.render(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }

      @Override
      public void tick() {
         super.tick();
         if (this.trail && this.age < this.lifetime / 2 && (this.age + this.lifetime) % 2 == 0) {
            FireworkParticles.SparkParticle â˜ƒ = new FireworkParticles.SparkParticle(
               this.level, this.x, this.y, this.z, 0.0, 0.0, 0.0, this.engine, this.sprites
            );
            â˜ƒ.setAlpha(0.99F);
            â˜ƒ.setColor(this.rCol, this.gCol, this.bCol);
            â˜ƒ.age = â˜ƒ.lifetime / 2;
            if (this.hasFade) {
               â˜ƒ.hasFade = true;
               â˜ƒ.fadeR = this.fadeR;
               â˜ƒ.fadeG = this.fadeG;
               â˜ƒ.fadeB = this.fadeB;
            }

            â˜ƒ.flicker = this.flicker;
            this.engine.add(â˜ƒ);
         }
      }
   }

   public static class SparkProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public SparkProvider(SpriteSet var1) {
         this.sprites = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         FireworkParticles.SparkParticle â˜ƒ = new FireworkParticles.SparkParticle(
            â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Minecraft.getInstance().particleEngine, this.sprites
         );
         â˜ƒ.setAlpha(0.99F);
         return â˜ƒ;
      }
   }

   public static class Starter extends NoRenderParticle {
      private int life;
      private final ParticleEngine engine;
      private ListTag explosions;
      private boolean twinkleDelay;

      public Starter(
         ClientLevel var1, double var2, double var4, double var6, double var8, double var10, double var12, ParticleEngine var14, @Nullable CompoundTag var15
      ) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.xd = â˜ƒ;
         this.yd = â˜ƒ;
         this.zd = â˜ƒ;
         this.engine = â˜ƒ;
         this.lifetime = 8;
         if (â˜ƒ != null) {
            this.explosions = â˜ƒ.getList("Explosions", 10);
            if (this.explosions.isEmpty()) {
               this.explosions = null;
            } else {
               this.lifetime = this.explosions.size() * 2 - 1;

               for(int â˜ƒ = 0; â˜ƒ < this.explosions.size(); ++â˜ƒ) {
                  CompoundTag â˜ƒx = this.explosions.getCompound(â˜ƒ);
                  if (â˜ƒx.getBoolean("Flicker")) {
                     this.twinkleDelay = true;
                     this.lifetime += 15;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void tick() {
         if (this.life == 0 && this.explosions != null) {
            boolean â˜ƒ = this.isFarAwayFromCamera();
            boolean â˜ƒx = false;
            if (this.explosions.size() >= 3) {
               â˜ƒx = true;
            } else {
               for(int â˜ƒ = 0; â˜ƒ < this.explosions.size(); ++â˜ƒ) {
                  CompoundTag â˜ƒx = this.explosions.getCompound(â˜ƒ);
                  if (FireworkRocketItem.Shape.byId(â˜ƒx.getByte("Type")) == FireworkRocketItem.Shape.LARGE_BALL) {
                     â˜ƒx = true;
                     break;
                  }
               }
            }

            SoundEvent â˜ƒ;
            if (â˜ƒx) {
               â˜ƒ = â˜ƒ ? SoundEvents.FIREWORK_ROCKET_LARGE_BLAST_FAR : SoundEvents.FIREWORK_ROCKET_LARGE_BLAST;
            } else {
               â˜ƒ = â˜ƒ ? SoundEvents.FIREWORK_ROCKET_BLAST_FAR : SoundEvents.FIREWORK_ROCKET_BLAST;
            }

            this.level.playLocalSound(this.x, this.y, this.z, â˜ƒ, SoundSource.AMBIENT, 20.0F, 0.95F + this.random.nextFloat() * 0.1F, true);
         }

         if (this.life % 2 == 0 && this.explosions != null && this.life / 2 < this.explosions.size()) {
            int â˜ƒ = this.life / 2;
            CompoundTag â˜ƒx = this.explosions.getCompound(â˜ƒ);
            FireworkRocketItem.Shape â˜ƒxx = FireworkRocketItem.Shape.byId(â˜ƒx.getByte("Type"));
            boolean â˜ƒxxx = â˜ƒx.getBoolean("Trail");
            boolean â˜ƒxxxx = â˜ƒx.getBoolean("Flicker");
            int[] â˜ƒxxxxx = â˜ƒx.getIntArray("Colors");
            int[] â˜ƒxxxxxx = â˜ƒx.getIntArray("FadeColors");
            if (â˜ƒxxxxx.length == 0) {
               â˜ƒxxxxx = new int[]{DyeColor.BLACK.getFireworkColor()};
            }

            switch(â˜ƒxx) {
               case SMALL_BALL:
               default:
                  this.createParticleBall(0.25, 2, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxx);
                  break;
               case LARGE_BALL:
                  this.createParticleBall(0.5, 4, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxx);
                  break;
               case STAR:
                  this.createParticleShape(
                     0.5,
                     new double[][]{
                        {0.0, 1.0},
                        {0.3455, 0.309},
                        {0.9511, 0.309},
                        {0.3795918367346939, -0.12653061224489795},
                        {0.6122448979591837, -0.8040816326530612},
                        {0.0, -0.35918367346938773}
                     },
                     â˜ƒxxxxx,
                     â˜ƒxxxxxx,
                     â˜ƒxxx,
                     â˜ƒxxxx,
                     false
                  );
                  break;
               case CREEPER:
                  this.createParticleShape(
                     0.5,
                     new double[][]{
                        {0.0, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.6},
                        {0.6, 0.6},
                        {0.6, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.0},
                        {0.4, 0.0},
                        {0.4, -0.6},
                        {0.2, -0.6},
                        {0.2, -0.4},
                        {0.0, -0.4}
                     },
                     â˜ƒxxxxx,
                     â˜ƒxxxxxx,
                     â˜ƒxxx,
                     â˜ƒxxxx,
                     true
                  );
                  break;
               case BURST:
                  this.createParticleBurst(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxx);
            }

            int â˜ƒ = â˜ƒxxxxx[0];
            float â˜ƒx = (float)((â˜ƒ & 0xFF0000) >> 16) / 255.0F;
            float â˜ƒxx = (float)((â˜ƒ & 0xFF00) >> 8) / 255.0F;
            float â˜ƒxxx = (float)((â˜ƒ & 0xFF) >> 0) / 255.0F;
            Particle â˜ƒxxxx = this.engine.createParticle(ParticleTypes.FLASH, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            â˜ƒxxxx.setColor(â˜ƒx, â˜ƒxx, â˜ƒxxx);
         }

         ++this.life;
         if (this.life > this.lifetime) {
            if (this.twinkleDelay) {
               boolean â˜ƒ = this.isFarAwayFromCamera();
               SoundEvent â˜ƒx = â˜ƒ ? SoundEvents.FIREWORK_ROCKET_TWINKLE_FAR : SoundEvents.FIREWORK_ROCKET_TWINKLE;
               this.level.playLocalSound(this.x, this.y, this.z, â˜ƒx, SoundSource.AMBIENT, 20.0F, 0.9F + this.random.nextFloat() * 0.15F, true);
            }

            this.remove();
         }
      }

      private boolean isFarAwayFromCamera() {
         Minecraft â˜ƒ = Minecraft.getInstance();
         return â˜ƒ.gameRenderer.getMainCamera().getPosition().distanceToSqr(this.x, this.y, this.z) >= 256.0;
      }

      private void createParticle(
         double var1, double var3, double var5, double var7, double var9, double var11, int[] var13, int[] var14, boolean var15, boolean var16
      ) {
         FireworkParticles.SparkParticle â˜ƒ = (FireworkParticles.SparkParticle)this.engine
            .createParticle(ParticleTypes.FIREWORK, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.setTrail(â˜ƒ);
         â˜ƒ.setFlicker(â˜ƒ);
         â˜ƒ.setAlpha(0.99F);
         int â˜ƒx = this.random.nextInt(â˜ƒ.length);
         â˜ƒ.setColor(â˜ƒ[â˜ƒx]);
         if (â˜ƒ.length > 0) {
            â˜ƒ.setFadeColor(Util.getRandom(â˜ƒ, this.random));
         }
      }

      private void createParticleBall(double var1, int var3, int[] var4, int[] var5, boolean var6, boolean var7) {
         double â˜ƒ = this.x;
         double â˜ƒx = this.y;
         double â˜ƒxx = this.z;

         for(int â˜ƒxxx = -â˜ƒ; â˜ƒxxx <= â˜ƒ; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = -â˜ƒ; â˜ƒxxxx <= â˜ƒ; ++â˜ƒxxxx) {
               for(int â˜ƒxxxxx = -â˜ƒ; â˜ƒxxxxx <= â˜ƒ; ++â˜ƒxxxxx) {
                  double â˜ƒxxxxxx = (double)â˜ƒxxxx + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double â˜ƒxxxxxxx = (double)â˜ƒxxx + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double â˜ƒxxxxxxxx = (double)â˜ƒxxxxx + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double â˜ƒxxxxxxxxx = Math.sqrt(â˜ƒxxxxxx * â˜ƒxxxxxx + â˜ƒxxxxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxxxx * â˜ƒxxxxxxxx) / â˜ƒ
                     + this.random.nextGaussian() * 0.05;
                  this.createParticle(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxxxxx / â˜ƒxxxxxxxxx, â˜ƒxxxxxxx / â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx / â˜ƒxxxxxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
                  if (â˜ƒxxx != -â˜ƒ && â˜ƒxxx != â˜ƒ && â˜ƒxxxx != -â˜ƒ && â˜ƒxxxx != â˜ƒ) {
                     â˜ƒxxxxx += â˜ƒ * 2 - 1;
                  }
               }
            }
         }
      }

      private void createParticleShape(double var1, double[][] var3, int[] var4, int[] var5, boolean var6, boolean var7, boolean var8) {
         double â˜ƒ = â˜ƒ[0][0];
         double â˜ƒx = â˜ƒ[0][1];
         this.createParticle(this.x, this.y, this.z, â˜ƒ * â˜ƒ, â˜ƒx * â˜ƒ, 0.0, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         float â˜ƒxx = this.random.nextFloat() * (float) Math.PI;
         double â˜ƒxxx = â˜ƒ ? 0.034 : 0.34;

         for(int â˜ƒxxxx = 0; â˜ƒxxxx < 3; ++â˜ƒxxxx) {
            double â˜ƒxxxxx = (double)â˜ƒxx + (double)((float)â˜ƒxxxx * (float) Math.PI) * â˜ƒxxx;
            double â˜ƒxxxxxx = â˜ƒ;
            double â˜ƒxxxxxxx = â˜ƒx;

            for(int â˜ƒxxxxxxxx = 1; â˜ƒxxxxxxxx < â˜ƒ.length; ++â˜ƒxxxxxxxx) {
               double â˜ƒxxxxxxxxx = â˜ƒ[â˜ƒxxxxxxxx][0];
               double â˜ƒxxxxxxxxxx = â˜ƒ[â˜ƒxxxxxxxx][1];

               for(double â˜ƒxxxxxxxxxxx = 0.25; â˜ƒxxxxxxxxxxx <= 1.0; â˜ƒxxxxxxxxxxx += 0.25) {
                  double â˜ƒxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxx) * â˜ƒ;
                  double â˜ƒxxxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxx) * â˜ƒ;
                  double â˜ƒxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx * Math.sin(â˜ƒxxxxx);
                  â˜ƒxxxxxxxxxxxx *= Math.cos(â˜ƒxxxxx);

                  for(double â˜ƒxxxxxxxxxxxxxxx = -1.0; â˜ƒxxxxxxxxxxxxxxx <= 1.0; â˜ƒxxxxxxxxxxxxxxx += 2.0) {
                     this.createParticle(
                        this.x,
                        this.y,
                        this.z,
                        â˜ƒxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx,
                        â˜ƒxxxxxxxxxxxxx,
                        â˜ƒxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxx,
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ,
                        â˜ƒ
                     );
                  }
               }

               â˜ƒxxxxxx = â˜ƒxxxxxxxxx;
               â˜ƒxxxxxxx = â˜ƒxxxxxxxxxx;
            }
         }
      }

      private void createParticleBurst(int[] var1, int[] var2, boolean var3, boolean var4) {
         double â˜ƒ = this.random.nextGaussian() * 0.05;
         double â˜ƒx = this.random.nextGaussian() * 0.05;

         for(int â˜ƒxx = 0; â˜ƒxx < 70; ++â˜ƒxx) {
            double â˜ƒxxx = this.xd * 0.5 + this.random.nextGaussian() * 0.15 + â˜ƒ;
            double â˜ƒxxxx = this.zd * 0.5 + this.random.nextGaussian() * 0.15 + â˜ƒx;
            double â˜ƒxxxxx = this.yd * 0.5 + this.random.nextDouble() * 0.5;
            this.createParticle(this.x, this.y, this.z, â˜ƒxxx, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }
}
