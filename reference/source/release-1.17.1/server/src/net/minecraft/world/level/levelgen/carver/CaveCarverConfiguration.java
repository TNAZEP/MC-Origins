package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CaveCarverConfiguration extends CarverConfiguration {
   public static final Codec<CaveCarverConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               CarverConfiguration.CODEC.forGetter(var0x -> var0x),
               FloatProvider.CODEC.fieldOf("horizontal_radius_multiplier").forGetter(var0x -> var0x.horizontalRadiusMultiplier),
               FloatProvider.CODEC.fieldOf("vertical_radius_multiplier").forGetter(var0x -> var0x.verticalRadiusMultiplier),
               FloatProvider.codec(-1.0F, 1.0F).fieldOf("floor_level").forGetter(var0x -> var0x.floorLevel)
            )
            .apply(var0, CaveCarverConfiguration::new)
   );
   public final FloatProvider horizontalRadiusMultiplier;
   public final FloatProvider verticalRadiusMultiplier;
   final FloatProvider floorLevel;

   public CaveCarverConfiguration(
      float var1,
      HeightProvider var2,
      FloatProvider var3,
      VerticalAnchor var4,
      boolean var5,
      CarverDebugSettings var6,
      FloatProvider var7,
      FloatProvider var8,
      FloatProvider var9
   ) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.horizontalRadiusMultiplier = â˜ƒ;
      this.verticalRadiusMultiplier = â˜ƒ;
      this.floorLevel = â˜ƒ;
   }

   public CaveCarverConfiguration(
      float var1, HeightProvider var2, FloatProvider var3, VerticalAnchor var4, boolean var5, FloatProvider var6, FloatProvider var7, FloatProvider var8
   ) {
      this(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, CarverDebugSettings.DEFAULT, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public CaveCarverConfiguration(CarverConfiguration var1, FloatProvider var2, FloatProvider var3, FloatProvider var4) {
      this(â˜ƒ.probability, â˜ƒ.y, â˜ƒ.yScale, â˜ƒ.lavaLevel, â˜ƒ.aquifersEnabled, â˜ƒ.debugSettings, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
