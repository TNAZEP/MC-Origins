package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;

public class DecoratedFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<DecoratedFeatureConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ConfiguredFeature.CODEC.fieldOf("feature").forGetter(var0x -> var0x.feature),
               ConfiguredDecorator.CODEC.fieldOf("decorator").forGetter(var0x -> var0x.decorator)
            )
            .apply(var0, DecoratedFeatureConfiguration::new)
   );
   public final Supplier<ConfiguredFeature<?, ?>> feature;
   public final ConfiguredDecorator<?> decorator;

   public DecoratedFeatureConfiguration(Supplier<ConfiguredFeature<?, ?>> var1, ConfiguredDecorator<?> var2) {
      this.feature = â˜ƒ;
      this.decorator = â˜ƒ;
   }

   public String toString() {
      return String.format(
         "< %s [%s | %s] >", this.getClass().getSimpleName(), Registry.FEATURE.getKey(((ConfiguredFeature)this.feature.get()).feature()), this.decorator
      );
   }

   @Override
   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
      return ((ConfiguredFeature)this.feature.get()).getFeatures();
   }
}
