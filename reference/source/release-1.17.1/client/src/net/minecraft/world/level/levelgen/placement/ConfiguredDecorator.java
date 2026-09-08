package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.Decoratable;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class ConfiguredDecorator<DC extends DecoratorConfiguration> implements Decoratable<ConfiguredDecorator<?>> {
   public static final Codec<ConfiguredDecorator<?>> CODEC = Registry.DECORATOR.dispatch("type", var0 -> var0.decorator, FeatureDecorator::configuredCodec);
   private final FeatureDecorator<DC> decorator;
   private final DC config;

   public ConfiguredDecorator(FeatureDecorator<DC> var1, DC var2) {
      this.decorator = â˜ƒ;
      this.config = â˜ƒ;
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, BlockPos var3) {
      return this.decorator.getPositions(â˜ƒ, â˜ƒ, this.config, â˜ƒ);
   }

   public String toString() {
      return String.format("[%s %s]", Registry.DECORATOR.getKey(this.decorator), this.config);
   }

   public ConfiguredDecorator<?> decorated(ConfiguredDecorator<?> var1) {
      return new ConfiguredDecorator<>(FeatureDecorator.DECORATED, new DecoratedDecoratorConfiguration(â˜ƒ, this));
   }

   public DC config() {
      return this.config;
   }
}
