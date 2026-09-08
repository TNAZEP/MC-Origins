package net.minecraft.world.level.levelgen;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.ChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

public interface Decoratable<R> {
   R decorated(ConfiguredDecorator<?> var1);

   default R rarity(int var1) {
      return this.decorated(FeatureDecorator.CHANCE.configured(new ChanceDecoratorConfiguration(â˜ƒ)));
   }

   default R count(IntProvider var1) {
      return this.decorated(FeatureDecorator.COUNT.configured(new CountConfiguration(â˜ƒ)));
   }

   default R count(int var1) {
      return this.count(ConstantInt.of(â˜ƒ));
   }

   default R countRandom(int var1) {
      return this.count(UniformInt.of(0, â˜ƒ));
   }

   default R rangeUniform(VerticalAnchor var1, VerticalAnchor var2) {
      return this.range(new RangeDecoratorConfiguration(UniformHeight.of(â˜ƒ, â˜ƒ)));
   }

   default R rangeTriangle(VerticalAnchor var1, VerticalAnchor var2) {
      return this.range(new RangeDecoratorConfiguration(TrapezoidHeight.of(â˜ƒ, â˜ƒ)));
   }

   default R range(RangeDecoratorConfiguration var1) {
      return this.decorated(FeatureDecorator.RANGE.configured(â˜ƒ));
   }

   default R squared() {
      return this.decorated(FeatureDecorator.SQUARE.configured(NoneDecoratorConfiguration.INSTANCE));
   }
}
