package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;

public class LakeLavaPlacementDecorator extends RepeatingDecorator<ChanceDecoratorConfiguration> {
   public LakeLavaPlacementDecorator(Codec<ChanceDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   protected int count(Random var1, ChanceDecoratorConfiguration var2, BlockPos var3) {
      return â˜ƒ.getY() >= 63 && â˜ƒ.nextInt(10) != 0 ? 0 : 1;
   }
}
