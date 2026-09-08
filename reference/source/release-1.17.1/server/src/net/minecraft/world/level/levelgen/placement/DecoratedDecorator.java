package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;

public class DecoratedDecorator extends FeatureDecorator<DecoratedDecoratorConfiguration> {
   public DecoratedDecorator(Codec<DecoratedDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, DecoratedDecoratorConfiguration var3, BlockPos var4) {
      return â˜ƒ.outer().getPositions(â˜ƒ, â˜ƒ, â˜ƒ).flatMap(var3x -> â˜ƒ.inner().getPositions(â˜ƒ, â˜ƒ, var3x));
   }
}
