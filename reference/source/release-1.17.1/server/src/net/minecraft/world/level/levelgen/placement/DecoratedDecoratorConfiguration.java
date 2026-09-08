package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class DecoratedDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<DecoratedDecoratorConfiguration> CODEC = RecordCodecBuilder.create(
      var0 -> var0.group(
               ConfiguredDecorator.CODEC.fieldOf("outer").forGetter(DecoratedDecoratorConfiguration::outer),
               ConfiguredDecorator.CODEC.fieldOf("inner").forGetter(DecoratedDecoratorConfiguration::inner)
            )
            .apply(var0, DecoratedDecoratorConfiguration::new)
   );
   private final ConfiguredDecorator<?> outer;
   private final ConfiguredDecorator<?> inner;

   public DecoratedDecoratorConfiguration(ConfiguredDecorator<?> var1, ConfiguredDecorator<?> var2) {
      this.outer = â˜ƒ;
      this.inner = â˜ƒ;
   }

   public ConfiguredDecorator<?> outer() {
      return this.outer;
   }

   public ConfiguredDecorator<?> inner() {
      return this.inner;
   }
}
