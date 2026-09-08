package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;

public class CountWithExtraChanceDecorator extends RepeatingDecorator<FrequencyWithExtraChanceDecoratorConfiguration> {
   public CountWithExtraChanceDecorator(Codec<FrequencyWithExtraChanceDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   protected int count(Random var1, FrequencyWithExtraChanceDecoratorConfiguration var2, BlockPos var3) {
      return â˜ƒ.count + (â˜ƒ.nextFloat() < â˜ƒ.extraChance ? â˜ƒ.extraCount : 0);
   }
}
