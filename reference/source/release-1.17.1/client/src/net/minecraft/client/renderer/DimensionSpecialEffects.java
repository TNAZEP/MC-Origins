package net.minecraft.client.renderer;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

public abstract class DimensionSpecialEffects {
   private static final Object2ObjectMap<ResourceLocation, DimensionSpecialEffects> EFFECTS = Util.make(new Object2ObjectArrayMap<>(), var0 -> {
      DimensionSpecialEffects.OverworldEffects â˜ƒ = new DimensionSpecialEffects.OverworldEffects();
      var0.defaultReturnValue(â˜ƒ);
      var0.put(DimensionType.OVERWORLD_EFFECTS, â˜ƒ);
      var0.put(DimensionType.NETHER_EFFECTS, new DimensionSpecialEffects.NetherEffects());
      var0.put(DimensionType.END_EFFECTS, new DimensionSpecialEffects.EndEffects());
   });
   private final float[] sunriseCol = new float[4];
   private final float cloudLevel;
   private final boolean hasGround;
   private final DimensionSpecialEffects.SkyType skyType;
   private final boolean forceBrightLightmap;
   private final boolean constantAmbientLight;

   public DimensionSpecialEffects(float var1, boolean var2, DimensionSpecialEffects.SkyType var3, boolean var4, boolean var5) {
      this.cloudLevel = â˜ƒ;
      this.hasGround = â˜ƒ;
      this.skyType = â˜ƒ;
      this.forceBrightLightmap = â˜ƒ;
      this.constantAmbientLight = â˜ƒ;
   }

   public static DimensionSpecialEffects forType(DimensionType var0) {
      return EFFECTS.get(â˜ƒ.effectsLocation());
   }

   @Nullable
   public float[] getSunriseColor(float var1, float var2) {
      float â˜ƒ = 0.4F;
      float â˜ƒx = Mth.cos(â˜ƒ * (float) (Math.PI * 2)) - 0.0F;
      float â˜ƒxx = -0.0F;
      if (â˜ƒx >= -0.4F && â˜ƒx <= 0.4F) {
         float â˜ƒxxx = (â˜ƒx - -0.0F) / 0.4F * 0.5F + 0.5F;
         float â˜ƒxxxx = 1.0F - (1.0F - Mth.sin(â˜ƒxxx * (float) Math.PI)) * 0.99F;
         â˜ƒxxxx *= â˜ƒxxxx;
         this.sunriseCol[0] = â˜ƒxxx * 0.3F + 0.7F;
         this.sunriseCol[1] = â˜ƒxxx * â˜ƒxxx * 0.7F + 0.2F;
         this.sunriseCol[2] = â˜ƒxxx * â˜ƒxxx * 0.0F + 0.2F;
         this.sunriseCol[3] = â˜ƒxxxx;
         return this.sunriseCol;
      } else {
         return null;
      }
   }

   public float getCloudHeight() {
      return this.cloudLevel;
   }

   public boolean hasGround() {
      return this.hasGround;
   }

   public abstract Vec3 getBrightnessDependentFogColor(Vec3 var1, float var2);

   public abstract boolean isFoggyAt(int var1, int var2);

   public DimensionSpecialEffects.SkyType skyType() {
      return this.skyType;
   }

   public boolean forceBrightLightmap() {
      return this.forceBrightLightmap;
   }

   public boolean constantAmbientLight() {
      return this.constantAmbientLight;
   }

   public static class EndEffects extends DimensionSpecialEffects {
      public EndEffects() {
         super(Float.NaN, false, DimensionSpecialEffects.SkyType.END, true, false);
      }

      @Override
      public Vec3 getBrightnessDependentFogColor(Vec3 var1, float var2) {
         return â˜ƒ.scale(0.15F);
      }

      @Override
      public boolean isFoggyAt(int var1, int var2) {
         return false;
      }

      @Nullable
      @Override
      public float[] getSunriseColor(float var1, float var2) {
         return null;
      }
   }

   public static class NetherEffects extends DimensionSpecialEffects {
      public NetherEffects() {
         super(Float.NaN, true, DimensionSpecialEffects.SkyType.NONE, false, true);
      }

      @Override
      public Vec3 getBrightnessDependentFogColor(Vec3 var1, float var2) {
         return â˜ƒ;
      }

      @Override
      public boolean isFoggyAt(int var1, int var2) {
         return true;
      }
   }

   public static class OverworldEffects extends DimensionSpecialEffects {
      public static final int CLOUD_LEVEL = 128;

      public OverworldEffects() {
         super(128.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
      }

      @Override
      public Vec3 getBrightnessDependentFogColor(Vec3 var1, float var2) {
         return â˜ƒ.multiply((double)(â˜ƒ * 0.94F + 0.06F), (double)(â˜ƒ * 0.94F + 0.06F), (double)(â˜ƒ * 0.91F + 0.09F));
      }

      @Override
      public boolean isFoggyAt(int var1, int var2) {
         return false;
      }
   }

   public static enum SkyType {
      NONE,
      NORMAL,
      END;
   }
}
