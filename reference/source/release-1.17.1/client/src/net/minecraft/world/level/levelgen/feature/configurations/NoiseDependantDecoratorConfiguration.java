package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;

public class NoiseDependantDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<NoiseDependantDecoratorConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.DOUBLE.fieldOf("noise_level").forGetter(var0x -> var0x.noiseLevel),
               Codec.INT.fieldOf("below_noise").forGetter(var0x -> var0x.belowNoise),
               Codec.INT.fieldOf("above_noise").forGetter(var0x -> var0x.aboveNoise)
            )
            .apply(var0, NoiseDependantDecoratorConfiguration::new)
   );
   public final double noiseLevel;
   public final int belowNoise;
   public final int aboveNoise;

   public NoiseDependantDecoratorConfiguration(double var1, int var3, int var4) {
      this.noiseLevel = â˜ƒ;
      this.belowNoise = â˜ƒ;
      this.aboveNoise = â˜ƒ;
   }
}
