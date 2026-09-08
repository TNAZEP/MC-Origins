package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public abstract class RepeatingDecorator<DC extends DecoratorConfiguration> extends FeatureDecorator<DC> {
   public RepeatingDecorator(Codec<DC> var1) {
      super(â˜ƒ);
   }

   protected abstract int count(Random var1, DC var2, BlockPos var3);

   @Override
   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, DC var3, BlockPos var4) {
      return IntStream.range(0, this.count(â˜ƒ, â˜ƒ, â˜ƒ)).mapToObj(var1x -> â˜ƒ);
   }
}
