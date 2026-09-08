package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CanyonCarverConfiguration extends CarverConfiguration {
   public static final Codec<CanyonCarverConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               CarverConfiguration.CODEC.forGetter(var0x -> var0x),
               FloatProvider.CODEC.fieldOf("vertical_rotation").forGetter(var0x -> var0x.verticalRotation),
               CanyonCarverConfiguration.CanyonShapeConfiguration.CODEC.fieldOf("shape").forGetter(var0x -> var0x.shape)
            )
            .apply(var0, CanyonCarverConfiguration::new)
   );
   public final FloatProvider verticalRotation;
   public final CanyonCarverConfiguration.CanyonShapeConfiguration shape;

   public CanyonCarverConfiguration(
      float var1,
      HeightProvider var2,
      FloatProvider var3,
      VerticalAnchor var4,
      boolean var5,
      CarverDebugSettings var6,
      FloatProvider var7,
      CanyonCarverConfiguration.CanyonShapeConfiguration var8
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.verticalRotation = â˜ƒ;
      this.shape = â˜ƒ;
   }

   public CanyonCarverConfiguration(CarverConfiguration var1, FloatProvider var2, CanyonCarverConfiguration.CanyonShapeConfiguration var3) {
      this(â˜ƒ.probability, â˜ƒ.y, â˜ƒ.yScale, â˜ƒ.lavaLevel, â˜ƒ.aquifersEnabled, â˜ƒ.debugSettings, â˜ƒ, â˜ƒ);
   }

   public static class CanyonShapeConfiguration {
      public static final Codec<CanyonCarverConfiguration.CanyonShapeConfiguration> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  FloatProvider.CODEC.fieldOf("distance_factor").forGetter(var0x -> var0x.distanceFactor),
                  FloatProvider.CODEC.fieldOf("thickness").forGetter(var0x -> var0x.thickness),
                  ExtraCodecs.NON_NEGATIVE_INT.fieldOf("width_smoothness").forGetter(var0x -> var0x.widthSmoothness),
                  FloatProvider.CODEC.fieldOf("horizontal_radius_factor").forGetter(var0x -> var0x.horizontalRadiusFactor),
                  Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter(var0x -> var0x.verticalRadiusDefaultFactor),
                  Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter(var0x -> var0x.verticalRadiusCenterFactor)
               )
               .apply(var0, CanyonCarverConfiguration.CanyonShapeConfiguration::new)
      );
      public final FloatProvider distanceFactor;
      public final FloatProvider thickness;
      public final int widthSmoothness;
      public final FloatProvider horizontalRadiusFactor;
      public final float verticalRadiusDefaultFactor;
      public final float verticalRadiusCenterFactor;

      public CanyonShapeConfiguration(FloatProvider var1, FloatProvider var2, int var3, FloatProvider var4, float var5, float var6) {
         this.widthSmoothness = â˜ƒ;
         this.horizontalRadiusFactor = â˜ƒ;
         this.verticalRadiusDefaultFactor = â˜ƒ;
         this.verticalRadiusCenterFactor = â˜ƒ;
         this.distanceFactor = â˜ƒ;
         this.thickness = â˜ƒ;
      }
   }
}
