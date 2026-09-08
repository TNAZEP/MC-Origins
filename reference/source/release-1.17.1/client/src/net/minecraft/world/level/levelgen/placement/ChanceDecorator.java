package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;

public class ChanceDecorator extends RepeatingDecorator<ChanceDecoratorConfiguration> {
   public ChanceDecorator(Codec<ChanceDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   protected int count(Random var1, ChanceDecoratorConfiguration var2, BlockPos var3) {
      return â˜ƒ.nextFloat() < 1.0F / (float)â˜ƒ.chance ? 1 : 0;
   }
}
