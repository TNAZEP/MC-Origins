package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedConfiguredFeature;

public class RandomFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<RandomFeatureConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.apply2(
            RandomFeatureConfiguration::new,
            WeightedConfiguredFeature.CODEC.listOf().fieldOf("features").forGetter(var0x -> var0x.features),
            ConfiguredFeature.CODEC.fieldOf("default").forGetter(var0x -> var0x.defaultFeature)
         )
   );
   public final List<WeightedConfiguredFeature> features;
   public final Supplier<ConfiguredFeature<?, ?>> defaultFeature;

   public RandomFeatureConfiguration(List<WeightedConfiguredFeature> var1, ConfiguredFeature<?, ?> var2) {
      this(â˜ƒ, () -> â˜ƒ);
   }

   private RandomFeatureConfiguration(List<WeightedConfiguredFeature> var1, Supplier<ConfiguredFeature<?, ?>> var2) {
      this.features = â˜ƒ;
      this.defaultFeature = â˜ƒ;
   }

   @Override
   public Stream<ConfiguredFeature<?, ?>> getFeatures() {
      return Stream.concat(
         this.features.stream().flatMap(var0 -> ((ConfiguredFeature)var0.feature.get()).getFeatures()),
         ((ConfiguredFeature)this.defaultFeature.get()).getFeatures()
      );
   }
}
