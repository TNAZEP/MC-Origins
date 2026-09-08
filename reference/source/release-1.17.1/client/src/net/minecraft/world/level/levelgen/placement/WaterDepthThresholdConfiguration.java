package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class WaterDepthThresholdConfiguration implements DecoratorConfiguration {
   public static final Codec<WaterDepthThresholdConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.INT.fieldOf("max_water_depth").forGetter(var0x -> var0x.maxWaterDepth)).apply(var0, WaterDepthThresholdConfiguration::new)
   );
   public final int maxWaterDepth;

   public WaterDepthThresholdConfiguration(int var1) {
      this.maxWaterDepth = â˜ƒ;
   }
}
