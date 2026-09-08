package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class DripParticle extends TextureSheetParticle {
   private final Fluid type;
   protected boolean isGlowing;

   DripParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.setSize(0.01F, 0.01F);
      this.gravity = 0.06F;
      this.type = â˜ƒ;
   }

   protected Fluid getType() {
      return this.type;
   }

   @Override
   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public int getLightColor(float var1) {
      return this.isGlowing ? 240 : super.getLightColor(â˜ƒ);
   }

   @Override
   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.preMoveUpdate();
      if (!this.removed) {
         this.yd -= (double)this.gravity;
         this.move(this.xd, this.yd, this.zd);
         this.postMoveUpdate();
         if (!this.removed) {
            this.xd *= 0.98F;
            this.yd *= 0.98F;
            this.zd *= 0.98F;
            BlockPos â˜ƒ = new BlockPos(this.x, this.y, this.z);
            FluidState â˜ƒx = this.level.getFluidState(â˜ƒ);
            if (â˜ƒx.getType() == this.type && this.y < (double)((float)â˜ƒ.getY() + â˜ƒx.getHeight(this.level, â˜ƒ))) {
               this.remove();
            }
         }
      }
   }

   protected void preMoveUpdate() {
      if (this.lifetime-- <= 0) {
         this.remove();
      }
   }

   protected void postMoveUpdate() {
   }

   static class CoolingDripHangParticle extends DripParticle.DripHangParticle {
      CoolingDripHangParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8, ParticleOptions var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      protected void preMoveUpdate() {
         this.rCol = 1.0F;
         this.gCol = 16.0F / (float)(40 - this.lifetime + 16);
         this.bCol = 4.0F / (float)(40 - this.lifetime + 8);
         super.preMoveUpdate();
      }
   }

   static class DripHangParticle extends DripParticle {
      private final ParticleOptions fallingParticle;

      DripHangParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8, ParticleOptions var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.fallingParticle = â˜ƒ;
         this.gravity *= 0.02F;
         this.lifetime = 40;
      }

      @Override
      protected void preMoveUpdate() {
         if (this.lifetime-- <= 0) {
            this.remove();
            this.level.addParticle(this.fallingParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
         }
      }

      @Override
      protected void postMoveUpdate() {
         this.xd *= 0.02;
         this.yd *= 0.02;
         this.zd *= 0.02;
      }
   }

   static class DripLandParticle extends DripParticle {
      DripLandParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      }
   }

   static class DripstoneFallAndLandParticle extends DripParticle.FallAndLandParticle {
      DripstoneFallAndLandParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8, ParticleOptions var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            SoundEvent â˜ƒ = this.getType() == Fluids.LAVA ? SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA : SoundEvents.POINTED_DRIPSTONE_DRIP_WATER;
            float â˜ƒx = Mth.randomBetween(this.random, 0.3F, 1.0F);
            this.level.playLocalSound(this.x, this.y, this.z, â˜ƒ, SoundSource.BLOCKS, â˜ƒx, 1.0F, false);
         }
      }
   }

   public static class DripstoneLavaFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public DripstoneLavaFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripstoneFallAndLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
         â˜ƒ.setColor(1.0F, 0.2857143F, 0.083333336F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class DripstoneLavaHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public DripstoneLavaHangProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.CoolingDripHangParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.LAVA, ParticleTypes.FALLING_DRIPSTONE_LAVA);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class DripstoneWaterFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public DripstoneWaterFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripstoneFallAndLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.WATER, ParticleTypes.SPLASH);
         â˜ƒ.setColor(0.2F, 0.3F, 1.0F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class DripstoneWaterHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public DripstoneWaterHangProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripHangParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.WATER, ParticleTypes.FALLING_DRIPSTONE_WATER);
         â˜ƒ.setColor(0.2F, 0.3F, 1.0F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   static class FallAndLandParticle extends DripParticle.FallingParticle {
      protected final ParticleOptions landParticle;

      FallAndLandParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8, ParticleOptions var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.landParticle = â˜ƒ;
      }

      @Override
      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
         }
      }
   }

   static class FallingParticle extends DripParticle {
      FallingParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8) {
         this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, (int)(64.0 / (Math.random() * 0.8 + 0.2)));
      }

      FallingParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8, int var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.lifetime = â˜ƒ;
      }

      @Override
      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
         }
      }
   }

   static class HoneyFallAndLandParticle extends DripParticle.FallAndLandParticle {
      HoneyFallAndLandParticle(ClientLevel var1, double var2, double var4, double var6, Fluid var8, ParticleOptions var9) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      @Override
      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            float â˜ƒ = Mth.randomBetween(this.random, 0.3F, 1.0F);
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.BEEHIVE_DRIP, SoundSource.BLOCKS, â˜ƒ, 1.0F, false);
         }
      }
   }

   public static class HoneyFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public HoneyFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.HoneyFallAndLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
         â˜ƒ.gravity = 0.01F;
         â˜ƒ.setColor(0.582F, 0.448F, 0.082F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class HoneyHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public HoneyHangProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle.DripHangParticle â˜ƒ = new DripParticle.DripHangParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY, ParticleTypes.FALLING_HONEY);
         â˜ƒ.gravity *= 0.01F;
         â˜ƒ.lifetime = 100;
         â˜ƒ.setColor(0.622F, 0.508F, 0.082F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class HoneyLandProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public HoneyLandProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY);
         â˜ƒ.lifetime = (int)(128.0 / (Math.random() * 0.8 + 0.2));
         â˜ƒ.setColor(0.522F, 0.408F, 0.082F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class LavaFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public LavaFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.FallAndLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
         â˜ƒ.setColor(1.0F, 0.2857143F, 0.083333336F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class LavaHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public LavaHangProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle.CoolingDripHangParticle â˜ƒ = new DripParticle.CoolingDripHangParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class LavaLandProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public LavaLandProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.LAVA);
         â˜ƒ.setColor(1.0F, 0.2857143F, 0.083333336F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class NectarFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public NectarFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.FallingParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY);
         â˜ƒ.lifetime = (int)(16.0 / (Math.random() * 0.8 + 0.2));
         â˜ƒ.gravity = 0.007F;
         â˜ƒ.setColor(0.92F, 0.782F, 0.72F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class ObsidianTearFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public ObsidianTearFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.FallAndLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY, ParticleTypes.LANDING_OBSIDIAN_TEAR);
         â˜ƒ.isGlowing = true;
         â˜ƒ.gravity = 0.01F;
         â˜ƒ.setColor(0.51171875F, 0.03125F, 0.890625F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class ObsidianTearHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public ObsidianTearHangProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle.DripHangParticle â˜ƒ = new DripParticle.DripHangParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY, ParticleTypes.FALLING_OBSIDIAN_TEAR);
         â˜ƒ.isGlowing = true;
         â˜ƒ.gravity *= 0.01F;
         â˜ƒ.lifetime = 100;
         â˜ƒ.setColor(0.51171875F, 0.03125F, 0.890625F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class ObsidianTearLandProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public ObsidianTearLandProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY);
         â˜ƒ.isGlowing = true;
         â˜ƒ.lifetime = (int)(28.0 / (Math.random() * 0.8 + 0.2));
         â˜ƒ.setColor(0.51171875F, 0.03125F, 0.890625F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class SporeBlossomFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;
      private final Random random;

      public SporeBlossomFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
         this.random = new Random();
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         int â˜ƒ = (int)(64.0F / Mth.randomBetween(this.random, 0.1F, 0.9F));
         DripParticle â˜ƒx = new DripParticle.FallingParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.EMPTY, â˜ƒ);
         â˜ƒx.gravity = 0.005F;
         â˜ƒx.setColor(0.32F, 0.5F, 0.22F);
         â˜ƒx.pickSprite(this.sprite);
         return â˜ƒx;
      }
   }

   public static class WaterFallProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public WaterFallProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.FallAndLandParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.WATER, ParticleTypes.SPLASH);
         â˜ƒ.setColor(0.2F, 0.3F, 1.0F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }

   public static class WaterHangProvider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprite;

      public WaterHangProvider(SpriteSet var1) {
         this.sprite = â˜ƒ;
      }

      public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         DripParticle â˜ƒ = new DripParticle.DripHangParticle(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, Fluids.WATER, ParticleTypes.FALLING_WATER);
         â˜ƒ.setColor(0.2F, 0.3F, 1.0F);
         â˜ƒ.pickSprite(this.sprite);
         return â˜ƒ;
      }
   }
}
