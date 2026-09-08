package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;

public class CountConfiguration implements DecoratorConfiguration, FeatureConfiguration {
   public static final Codec<CountConfiguration> CODEC = IntProvider.codec(0, 256)
      .fieldOf("count")
      .<CountConfiguration>xmap(CountConfiguration::new, CountConfiguration::count)
      .codec();
   private final IntProvider count;

   public CountConfiguration(int var1) {
      this.count = ConstantInt.of(â˜ƒ);
   }

   public CountConfiguration(IntProvider var1) {
      this.count = â˜ƒ;
   }

   public IntProvider count() {
      return this.count;
   }
}
