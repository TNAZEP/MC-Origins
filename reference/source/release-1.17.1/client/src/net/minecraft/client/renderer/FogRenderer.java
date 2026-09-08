package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;

public class FogRenderer {
   private static final int WATER_FOG_DISTANCE = 192;
   public static final float BIOME_FOG_TRANSITION_TIME = 5000.0F;
   private static float fogRed;
   private static float fogGreen;
   private static float fogBlue;
   private static int targetBiomeFog = -1;
   private static int previousBiomeFog = -1;
   private static long biomeChangedTime = -1L;

   public static void setupColor(Camera var0, float var1, ClientLevel var2, int var3, float var4) {
      FogType â˜ƒ = â˜ƒ.getFluidInCamera();
      Entity â˜ƒx = â˜ƒ.getEntity();
      if (â˜ƒ == FogType.WATER) {
         long â˜ƒxx = Util.getMillis();
         int â˜ƒxxx = â˜ƒ.getBiome(new BlockPos(â˜ƒ.getPosition())).getWaterFogColor();
         if (biomeChangedTime < 0L) {
            targetBiomeFog = â˜ƒxxx;
            previousBiomeFog = â˜ƒxxx;
            biomeChangedTime = â˜ƒxx;
         }

         int â˜ƒxx = targetBiomeFog >> 16 & 0xFF;
         int â˜ƒxxx = targetBiomeFog >> 8 & 0xFF;
         int â˜ƒxxxx = targetBiomeFog & 0xFF;
         int â˜ƒxxxxx = previousBiomeFog >> 16 & 0xFF;
         int â˜ƒxxxxxx = previousBiomeFog >> 8 & 0xFF;
         int â˜ƒxxxxxxx = previousBiomeFog & 0xFF;
         float â˜ƒxxxxxxxx = Mth.clamp((float)(â˜ƒxx - biomeChangedTime) / 5000.0F, 0.0F, 1.0F);
         float â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, (float)â˜ƒxxxxx, (float)â˜ƒxx);
         float â˜ƒxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, (float)â˜ƒxxxxxx, (float)â˜ƒxxx);
         float â˜ƒxxxxxxxxxxx = Mth.lerp(â˜ƒxxxxxxxx, (float)â˜ƒxxxxxxx, (float)â˜ƒxxxx);
         fogRed = â˜ƒxxxxxxxxx / 255.0F;
         fogGreen = â˜ƒxxxxxxxxxx / 255.0F;
         fogBlue = â˜ƒxxxxxxxxxxx / 255.0F;
         if (targetBiomeFog != â˜ƒxxx) {
            targetBiomeFog = â˜ƒxxx;
            previousBiomeFog = Mth.floor(â˜ƒxxxxxxxxx) << 16 | Mth.floor(â˜ƒxxxxxxxxxx) << 8 | Mth.floor(â˜ƒxxxxxxxxxxx);
            biomeChangedTime = â˜ƒxx;
         }
      } else if (â˜ƒ == FogType.LAVA) {
         fogRed = 0.6F;
         fogGreen = 0.1F;
         fogBlue = 0.0F;
         biomeChangedTime = -1L;
      } else if (â˜ƒ == FogType.POWDER_SNOW) {
         fogRed = 0.623F;
         fogGreen = 0.734F;
         fogBlue = 0.785F;
         biomeChangedTime = -1L;
         RenderSystem.clearColor(fogRed, fogGreen, fogBlue, 0.0F);
      } else {
         float â˜ƒ = 0.25F + 0.75F * (float)â˜ƒ / 32.0F;
         â˜ƒ = 1.0F - (float)Math.pow((double)â˜ƒ, 0.25);
         Vec3 â˜ƒx = â˜ƒ.getSkyColor(â˜ƒ.getPosition(), â˜ƒ);
         float â˜ƒxx = (float)â˜ƒx.x;
         float â˜ƒxxx = (float)â˜ƒx.y;
         float â˜ƒxxxx = (float)â˜ƒx.z;
         float â˜ƒxxxxx = Mth.clamp(Mth.cos(â˜ƒ.getTimeOfDay(â˜ƒ) * (float) (Math.PI * 2)) * 2.0F + 0.5F, 0.0F, 1.0F);
         BiomeManager â˜ƒxxxxxx = â˜ƒ.getBiomeManager();
         Vec3 â˜ƒxxxxxxx = â˜ƒ.getPosition().subtract(2.0, 2.0, 2.0).scale(0.25);
         Vec3 â˜ƒxxxxxxxx = CubicSampler.gaussianSampleVec3(
            â˜ƒxxxxxxx,
            (var3x, var4x, var5x) -> â˜ƒ.effects()
                  .getBrightnessDependentFogColor(Vec3.fromRGB24(â˜ƒ.getNoiseBiomeAtQuart(var3x, var4x, var5x).getFogColor()), â˜ƒ)
         );
         fogRed = (float)â˜ƒxxxxxxxx.x();
         fogGreen = (float)â˜ƒxxxxxxxx.y();
         fogBlue = (float)â˜ƒxxxxxxxx.z();
         if (â˜ƒ >= 4) {
            float â˜ƒxxxxxxxxx = Mth.sin(â˜ƒ.getSunAngle(â˜ƒ)) > 0.0F ? -1.0F : 1.0F;
            Vector3f â˜ƒxxxxxxxxxx = new Vector3f(â˜ƒxxxxxxxxx, 0.0F, 0.0F);
            float â˜ƒxxxxxxxxxxx = â˜ƒ.getLookVector().dot(â˜ƒxxxxxxxxxx);
            if (â˜ƒxxxxxxxxxxx < 0.0F) {
               â˜ƒxxxxxxxxxxx = 0.0F;
            }

            if (â˜ƒxxxxxxxxxxx > 0.0F) {
               float[] â˜ƒxxxxxxxxx = â˜ƒ.effects().getSunriseColor(â˜ƒ.getTimeOfDay(â˜ƒ), â˜ƒ);
               if (â˜ƒxxxxxxxxx != null) {
                  â˜ƒxxxxxxxxxxx *= â˜ƒxxxxxxxxx[3];
                  fogRed = fogRed * (1.0F - â˜ƒxxxxxxxxxxx) + â˜ƒxxxxxxxxx[0] * â˜ƒxxxxxxxxxxx;
                  fogGreen = fogGreen * (1.0F - â˜ƒxxxxxxxxxxx) + â˜ƒxxxxxxxxx[1] * â˜ƒxxxxxxxxxxx;
                  fogBlue = fogBlue * (1.0F - â˜ƒxxxxxxxxxxx) + â˜ƒxxxxxxxxx[2] * â˜ƒxxxxxxxxxxx;
               }
            }
         }

         fogRed += (â˜ƒxx - fogRed) * â˜ƒ;
         fogGreen += (â˜ƒxxx - fogGreen) * â˜ƒ;
         fogBlue += (â˜ƒxxxx - fogBlue) * â˜ƒ;
         float â˜ƒ = â˜ƒ.getRainLevel(â˜ƒ);
         if (â˜ƒ > 0.0F) {
            float â˜ƒx = 1.0F - â˜ƒ * 0.5F;
            float â˜ƒxx = 1.0F - â˜ƒ * 0.4F;
            fogRed *= â˜ƒx;
            fogGreen *= â˜ƒx;
            fogBlue *= â˜ƒxx;
         }

         float â˜ƒ = â˜ƒ.getThunderLevel(â˜ƒ);
         if (â˜ƒ > 0.0F) {
            float â˜ƒx = 1.0F - â˜ƒ * 0.5F;
            fogRed *= â˜ƒx;
            fogGreen *= â˜ƒx;
            fogBlue *= â˜ƒx;
         }

         biomeChangedTime = -1L;
      }

      double â˜ƒ = (â˜ƒ.getPosition().y - (double)â˜ƒ.getMinBuildHeight()) * â˜ƒ.getLevelData().getClearColorScale();
      if (â˜ƒ.getEntity() instanceof LivingEntity && ((LivingEntity)â˜ƒ.getEntity()).hasEffect(MobEffects.BLINDNESS)) {
         int â˜ƒx = ((LivingEntity)â˜ƒ.getEntity()).getEffect(MobEffects.BLINDNESS).getDuration();
         if (â˜ƒx < 20) {
            â˜ƒ *= (double)(1.0F - (float)â˜ƒx / 20.0F);
         } else {
            â˜ƒ = 0.0;
         }
      }

      if (â˜ƒ < 1.0 && â˜ƒ != FogType.LAVA) {
         if (â˜ƒ < 0.0) {
            â˜ƒ = 0.0;
         }

         â˜ƒ *= â˜ƒ;
         fogRed = (float)((double)fogRed * â˜ƒ);
         fogGreen = (float)((double)fogGreen * â˜ƒ);
         fogBlue = (float)((double)fogBlue * â˜ƒ);
      }

      if (â˜ƒ > 0.0F) {
         fogRed = fogRed * (1.0F - â˜ƒ) + fogRed * 0.7F * â˜ƒ;
         fogGreen = fogGreen * (1.0F - â˜ƒ) + fogGreen * 0.6F * â˜ƒ;
         fogBlue = fogBlue * (1.0F - â˜ƒ) + fogBlue * 0.6F * â˜ƒ;
      }

      float â˜ƒ;
      if (â˜ƒ == FogType.WATER) {
         if (â˜ƒx instanceof LocalPlayer) {
            â˜ƒ = ((LocalPlayer)â˜ƒx).getWaterVision();
         } else {
            â˜ƒ = 1.0F;
         }
      } else if (â˜ƒx instanceof LivingEntity && ((LivingEntity)â˜ƒx).hasEffect(MobEffects.NIGHT_VISION)) {
         â˜ƒ = GameRenderer.getNightVisionScale((LivingEntity)â˜ƒx, â˜ƒ);
      } else {
         â˜ƒ = 0.0F;
      }

      if (fogRed != 0.0F && fogGreen != 0.0F && fogBlue != 0.0F) {
         float â˜ƒ = Math.min(1.0F / fogRed, Math.min(1.0F / fogGreen, 1.0F / fogBlue));
         fogRed = fogRed * (1.0F - â˜ƒ) + fogRed * â˜ƒ * â˜ƒ;
         fogGreen = fogGreen * (1.0F - â˜ƒ) + fogGreen * â˜ƒ * â˜ƒ;
         fogBlue = fogBlue * (1.0F - â˜ƒ) + fogBlue * â˜ƒ * â˜ƒ;
      }

      RenderSystem.clearColor(fogRed, fogGreen, fogBlue, 0.0F);
   }

   public static void setupNoFog() {
      RenderSystem.setShaderFogStart(Float.MAX_VALUE);
   }

   public static void setupFog(Camera var0, FogRenderer.FogMode var1, float var2, boolean var3) {
      FogType â˜ƒ = â˜ƒ.getFluidInCamera();
      Entity â˜ƒx = â˜ƒ.getEntity();
      if (â˜ƒ == FogType.WATER) {
         float â˜ƒxxx = 192.0F;
         if (â˜ƒx instanceof LocalPlayer â˜ƒxx) {
            â˜ƒxxx *= Math.max(0.25F, â˜ƒxx.getWaterVision());
            Biome â˜ƒxxxx = â˜ƒxx.level.getBiome(â˜ƒxx.blockPosition());
            if (â˜ƒxxxx.getBiomeCategory() == Biome.BiomeCategory.SWAMP) {
               â˜ƒxxx *= 0.85F;
            }
         }

         RenderSystem.setShaderFogStart(-8.0F);
         RenderSystem.setShaderFogEnd(â˜ƒxxx * 0.5F);
      } else {
         float â˜ƒ;
         float â˜ƒx;
         if (â˜ƒ == FogType.LAVA) {
            if (â˜ƒx.isSpectator()) {
               â˜ƒ = -8.0F;
               â˜ƒx = â˜ƒ * 0.5F;
            } else if (â˜ƒx instanceof LivingEntity && ((LivingEntity)â˜ƒx).hasEffect(MobEffects.FIRE_RESISTANCE)) {
               â˜ƒ = 0.0F;
               â˜ƒx = 3.0F;
            } else {
               â˜ƒ = 0.25F;
               â˜ƒx = 1.0F;
            }
         } else if (â˜ƒx instanceof LivingEntity && ((LivingEntity)â˜ƒx).hasEffect(MobEffects.BLINDNESS)) {
            int â˜ƒ = ((LivingEntity)â˜ƒx).getEffect(MobEffects.BLINDNESS).getDuration();
            float â˜ƒx = Mth.lerp(Math.min(1.0F, (float)â˜ƒ / 20.0F), â˜ƒ, 5.0F);
            if (â˜ƒ == FogRenderer.FogMode.FOG_SKY) {
               â˜ƒ = 0.0F;
               â˜ƒx = â˜ƒx * 0.8F;
            } else {
               â˜ƒ = â˜ƒx * 0.25F;
               â˜ƒx = â˜ƒx;
            }
         } else if (â˜ƒ == FogType.POWDER_SNOW) {
            if (â˜ƒx.isSpectator()) {
               â˜ƒ = -8.0F;
               â˜ƒx = â˜ƒ * 0.5F;
            } else {
               â˜ƒ = 0.0F;
               â˜ƒx = 2.0F;
            }
         } else if (â˜ƒ) {
            â˜ƒ = â˜ƒ * 0.05F;
            â˜ƒx = Math.min(â˜ƒ, 192.0F) * 0.5F;
         } else if (â˜ƒ == FogRenderer.FogMode.FOG_SKY) {
            â˜ƒ = 0.0F;
            â˜ƒx = â˜ƒ;
         } else {
            â˜ƒ = â˜ƒ * 0.75F;
            â˜ƒx = â˜ƒ;
         }

         RenderSystem.setShaderFogStart(â˜ƒ);
         RenderSystem.setShaderFogEnd(â˜ƒx);
      }
   }

   public static void levelFogColor() {
      RenderSystem.setShaderFogColor(fogRed, fogGreen, fogBlue);
   }

   public static enum FogMode {
      FOG_SKY,
      FOG_TERRAIN;
   }
}
