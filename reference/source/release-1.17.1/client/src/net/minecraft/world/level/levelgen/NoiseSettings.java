package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.function.Function;
import net.minecraft.world.level.dimension.DimensionType;

public class NoiseSettings {
   public static final Codec<NoiseSettings> CODEC = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.intRange(DimensionType.MIN_Y, DimensionType.MAX_Y).fieldOf("min_y").forGetter(NoiseSettings::minY),
                  Codec.intRange(0, DimensionType.Y_SIZE).fieldOf("height").forGetter(NoiseSettings::height),
                  NoiseSamplingSettings.CODEC.fieldOf("sampling").forGetter(NoiseSettings::noiseSamplingSettings),
                  NoiseSlideSettings.CODEC.fieldOf("top_slide").forGetter(NoiseSettings::topSlideSettings),
                  NoiseSlideSettings.CODEC.fieldOf("bottom_slide").forGetter(NoiseSettings::bottomSlideSettings),
                  Codec.intRange(1, 4).fieldOf("size_horizontal").forGetter(NoiseSettings::noiseSizeHorizontal),
                  Codec.intRange(1, 4).fieldOf("size_vertical").forGetter(NoiseSettings::noiseSizeVertical),
                  Codec.DOUBLE.fieldOf("density_factor").forGetter(NoiseSettings::densityFactor),
                  Codec.DOUBLE.fieldOf("density_offset").forGetter(NoiseSettings::densityOffset),
                  Codec.BOOL.fieldOf("simplex_surface_noise").forGetter(NoiseSettings::useSimplexSurfaceNoise),
                  Codec.BOOL
                     .optionalFieldOf("random_density_offset", Boolean.valueOf(false), Lifecycle.experimental())
                     .forGetter(NoiseSettings::randomDensityOffset),
                  Codec.BOOL
                     .optionalFieldOf("island_noise_override", Boolean.valueOf(false), Lifecycle.experimental())
                     .forGetter(NoiseSettings::islandNoiseOverride),
                  Codec.BOOL.optionalFieldOf("amplified", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(NoiseSettings::isAmplified)
               )
               .apply(var0, NoiseSettings::new)
      )
      .comapFlatMap(NoiseSettings::guardY, Function.identity());
   private final int minY;
   private final int height;
   private final NoiseSamplingSettings noiseSamplingSettings;
   private final NoiseSlideSettings topSlideSettings;
   private final NoiseSlideSettings bottomSlideSettings;
   private final int noiseSizeHorizontal;
   private final int noiseSizeVertical;
   private final double densityFactor;
   private final double densityOffset;
   private final boolean useSimplexSurfaceNoise;
   private final boolean randomDensityOffset;
   private final boolean islandNoiseOverride;
   private final boolean isAmplified;

   private static DataResult<NoiseSettings> guardY(NoiseSettings var0) {
      if (â˜ƒ.minY() + â˜ƒ.height() > DimensionType.MAX_Y + 1) {
         return DataResult.error("min_y + height cannot be higher than: " + (DimensionType.MAX_Y + 1));
      } else if (â˜ƒ.height() % 16 != 0) {
         return DataResult.error("height has to be a multiple of 16");
      } else {
         return â˜ƒ.minY() % 16 != 0 ? DataResult.error("min_y has to be a multiple of 16") : DataResult.success(â˜ƒ);
      }
   }

   private NoiseSettings(
      int var1,
      int var2,
      NoiseSamplingSettings var3,
      NoiseSlideSettings var4,
      NoiseSlideSettings var5,
      int var6,
      int var7,
      double var8,
      double var10,
      boolean var12,
      boolean var13,
      boolean var14,
      boolean var15
   ) {
      this.minY = â˜ƒ;
      this.height = â˜ƒ;
      this.noiseSamplingSettings = â˜ƒ;
      this.topSlideSettings = â˜ƒ;
      this.bottomSlideSettings = â˜ƒ;
      this.noiseSizeHorizontal = â˜ƒ;
      this.noiseSizeVertical = â˜ƒ;
      this.densityFactor = â˜ƒ;
      this.densityOffset = â˜ƒ;
      this.useSimplexSurfaceNoise = â˜ƒ;
      this.randomDensityOffset = â˜ƒ;
      this.islandNoiseOverride = â˜ƒ;
      this.isAmplified = â˜ƒ;
   }

   public static NoiseSettings create(
      int var0,
      int var1,
      NoiseSamplingSettings var2,
      NoiseSlideSettings var3,
      NoiseSlideSettings var4,
      int var5,
      int var6,
      double var7,
      double var9,
      boolean var11,
      boolean var12,
      boolean var13,
      boolean var14
   ) {
      NoiseSettings â˜ƒ = new NoiseSettings(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      guardY(â˜ƒ).error().ifPresent(var0x -> {
         throw new IllegalStateException(var0x.message());
      });
      return â˜ƒ;
   }

   public int minY() {
      return this.minY;
   }

   public int height() {
      return this.height;
   }

   public NoiseSamplingSettings noiseSamplingSettings() {
      return this.noiseSamplingSettings;
   }

   public NoiseSlideSettings topSlideSettings() {
      return this.topSlideSettings;
   }

   public NoiseSlideSettings bottomSlideSettings() {
      return this.bottomSlideSettings;
   }

   public int noiseSizeHorizontal() {
      return this.noiseSizeHorizontal;
   }

   public int noiseSizeVertical() {
      return this.noiseSizeVertical;
   }

   public double densityFactor() {
      return this.densityFactor;
   }

   public double densityOffset() {
      return this.densityOffset;
   }

   @Deprecated
   public boolean useSimplexSurfaceNoise() {
      return this.useSimplexSurfaceNoise;
   }

   @Deprecated
   public boolean randomDensityOffset() {
      return this.randomDensityOffset;
   }

   @Deprecated
   public boolean islandNoiseOverride() {
      return this.islandNoiseOverride;
   }

   @Deprecated
   public boolean isAmplified() {
      return this.isAmplified;
   }
}
