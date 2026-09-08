package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class RangeDecoratorConfiguration implements DecoratorConfiguration, FeatureConfiguration {
   public static final Codec<RangeDecoratorConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(HeightProvider.CODEC.fieldOf("height").forGetter(var0x -> var0x.height)).apply(var0, RangeDecoratorConfiguration::new)
   );
   public final HeightProvider height;

   public RangeDecoratorConfiguration(HeightProvider var1) {
      this.height = â˜ƒ;
   }
}
