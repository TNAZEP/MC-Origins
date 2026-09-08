package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class RandomBooleanFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<RandomBooleanFeatureConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ConfiguredFeature.CODEC.fieldOf("feature_true").forGetter(var0x -> var0x.featureTrue),
               ConfiguredFeature.CODEC.fieldOf("feature_false").forGetter(var0x -> var0x.featureFalse)
            )
            .apply(var0, RandomBooleanFeatureConfiguration::new)
   );
   public final Supplier<ConfiguredFeature<?, ?>> featureTrue;
   public final Supplier<ConfiguredFeature<?, ?>> featureFalse;

   public RandomBooleanFeatureConfiguration(Supplier<ConfiguredFeature<?, ?>> var1, Supplier<ConfiguredFeature<?, ?>> var2) {
      this.featureTrue = â˜ƒ;
      this.featureFalse = â˜ƒ;
   }

   @Override
   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
      return Stream.concat(((ConfiguredFeature)this.featureTrue.get()).getFeatures(), ((ConfiguredFeature)this.featureFalse.get()).getFeatures());
   }
}
