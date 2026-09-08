package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class LightTexture implements AutoCloseable {
   public static final int FULL_BRIGHT = 15728880;
   public static final int FULL_SKY = 15728640;
   public static final int FULL_BLOCK = 240;
   private final DynamicTexture lightTexture;
   private final NativeImage lightPixels;
   private final ResourceLocation lightTextureLocation;
   private boolean updateLightTexture;
   private float blockLightRedFlicker;
   private final GameRenderer renderer;
   private final Minecraft minecraft;

   public LightTexture(GameRenderer var1, Minecraft var2) {
      this.renderer = â˜ƒ;
      this.minecraft = â˜ƒ;
      this.lightTexture = new DynamicTexture(16, 16, false);
      this.lightTextureLocation = this.minecraft.getTextureManager().register("light_map", this.lightTexture);
      this.lightPixels = this.lightTexture.getPixels();

      for(int â˜ƒ = 0; â˜ƒ < 16; ++â˜ƒ) {
         for(int â˜ƒx = 0; â˜ƒx < 16; ++â˜ƒx) {
            this.lightPixels.setPixelRGBA(â˜ƒx, â˜ƒ, -1);
         }
      }

      this.lightTexture.upload();
   }

   public void close() {
      this.lightTexture.close();
   }

   public void tick() {
      this.blockLightRedFlicker = (float)((double)this.blockLightRedFlicker + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
      this.blockLightRedFlicker = (float)((double)this.blockLightRedFlicker * 0.9);
      this.updateLightTexture = true;
   }

   public void turnOffLightLayer() {
      RenderSystem.setShaderTexture(2, 0);
   }

   public void turnOnLightLayer() {
      RenderSystem.setShaderTexture(2, this.lightTextureLocation);
      this.minecraft.getTextureManager().bindForSetup(this.lightTextureLocation);
      RenderSystem.texParameter(3553, 10241, 9729);
      RenderSystem.texParameter(3553, 10240, 9729);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void updateLightTexture(float var1) {
      if (this.updateLightTexture) {
         this.updateLightTexture = false;
         this.minecraft.getProfiler().push("lightTex");
         ClientLevel â˜ƒ = this.minecraft.level;
         if (â˜ƒ != null) {
            float â˜ƒxx = â˜ƒ.getSkyDarken(1.0F);
            float â˜ƒx;
            if (â˜ƒ.getSkyFlashTime() > 0) {
               â˜ƒx = 1.0F;
            } else {
               â˜ƒx = â˜ƒxx * 0.95F + 0.05F;
            }

            float â˜ƒxx = this.minecraft.player.getWaterVision();
            float â˜ƒx;
            if (this.minecraft.player.hasEffect(MobEffects.NIGHT_VISION)) {
               â˜ƒx = GameRenderer.getNightVisionScale(this.minecraft.player, â˜ƒ);
            } else if (â˜ƒxx > 0.0F && this.minecraft.player.hasEffect(MobEffects.CONDUIT_POWER)) {
               â˜ƒx = â˜ƒxx;
            } else {
               â˜ƒx = 0.0F;
            }

            Vector3f â˜ƒx = new Vector3f(â˜ƒxx, â˜ƒxx, 1.0F);
            â˜ƒx.lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float â˜ƒxx = this.blockLightRedFlicker + 1.5F;
            Vector3f â˜ƒxxx = new Vector3f();

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < 16; ++â˜ƒxxxx) {
               for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < 16; ++â˜ƒxxxxx) {
                  float â˜ƒxxxxxx = this.getBrightness(â˜ƒ, â˜ƒxxxx) * â˜ƒx;
                  float â˜ƒxxxxxxx = this.getBrightness(â˜ƒ, â˜ƒxxxxx) * â˜ƒxx;
                  float â˜ƒxxxxxxxx = â˜ƒxxxxxxx * ((â˜ƒxxxxxxx * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float â˜ƒxxxxxxxxx = â˜ƒxxxxxxx * (â˜ƒxxxxxxx * â˜ƒxxxxxxx * 0.6F + 0.4F);
                  â˜ƒxxx.set(â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
                  if (â˜ƒ.effects().forceBrightLightmap()) {
                     â˜ƒxxx.lerp(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                  } else {
                     Vector3f â˜ƒxxxxxx = â˜ƒx.copy();
                     â˜ƒxxxxxx.mul(â˜ƒxxxxxx);
                     â˜ƒxxx.add(â˜ƒxxxxxx);
                     â˜ƒxxx.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.renderer.getDarkenWorldAmount(â˜ƒ) > 0.0F) {
                        float â˜ƒxxxxxxx = this.renderer.getDarkenWorldAmount(â˜ƒ);
                        Vector3f â˜ƒxxxxxxxx = â˜ƒxxx.copy();
                        â˜ƒxxxxxxxx.mul(0.7F, 0.6F, 0.6F);
                        â˜ƒxxx.lerp(â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
                     }
                  }

                  â˜ƒxxx.clamp(0.0F, 1.0F);
                  if (â˜ƒx > 0.0F) {
                     float â˜ƒxxxxxx = Math.max(â˜ƒxxx.x(), Math.max(â˜ƒxxx.y(), â˜ƒxxx.z()));
                     if (â˜ƒxxxxxx < 1.0F) {
                        float â˜ƒxxxxxxx = 1.0F / â˜ƒxxxxxx;
                        Vector3f â˜ƒxxxxxxxx = â˜ƒxxx.copy();
                        â˜ƒxxxxxxxx.mul(â˜ƒxxxxxxx);
                        â˜ƒxxx.lerp(â˜ƒxxxxxxxx, â˜ƒx);
                     }
                  }

                  float â˜ƒxxxxxx = (float)this.minecraft.options.gamma;
                  Vector3f â˜ƒxxxxxxx = â˜ƒxxx.copy();
                  â˜ƒxxxxxxx.map(this::notGamma);
                  â˜ƒxxx.lerp(â˜ƒxxxxxxx, â˜ƒxxxxxx);
                  â˜ƒxxx.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                  â˜ƒxxx.clamp(0.0F, 1.0F);
                  â˜ƒxxx.mul(255.0F);
                  int â˜ƒxxxxxxxx = 255;
                  int â˜ƒxxxxxxxxx = (int)â˜ƒxxx.x();
                  int â˜ƒxxxxxxxxxx = (int)â˜ƒxxx.y();
                  int â˜ƒxxxxxxxxxxx = (int)â˜ƒxxx.z();
                  this.lightPixels.setPixelRGBA(â˜ƒxxxxx, â˜ƒxxxx, 0xFF000000 | â˜ƒxxxxxxxxxxx << 16 | â˜ƒxxxxxxxxxx << 8 | â˜ƒxxxxxxxxx);
               }
            }

            this.lightTexture.upload();
            this.minecraft.getProfiler().pop();
         }
      }
   }

   private float notGamma(float var1) {
      float â˜ƒ = 1.0F - â˜ƒ;
      return 1.0F - â˜ƒ * â˜ƒ * â˜ƒ * â˜ƒ;
   }

   private float getBrightness(Level var1, int var2) {
      return â˜ƒ.dimensionType().brightness(â˜ƒ);
   }

   public static int pack(int var0, int var1) {
      return â˜ƒ << 4 | â˜ƒ << 20;
   }

   public static int block(int var0) {
      return â˜ƒ >> 4 & 65535;
   }

   public static int sky(int var0) {
      return â˜ƒ >> 20 & 65535;
   }
}
