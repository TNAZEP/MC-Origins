package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;

public class DarkOakTreePlacementDecorator extends FeatureDecorator<NoneDecoratorConfiguration> {
   public DarkOakTreePlacementDecorator(Codec<NoneDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, NoneDecoratorConfiguration var3, BlockPos var4) {
      return IntStream.range(0, 16).mapToObj(var2x -> {
         int â˜ƒ = var2x / 4;
         int â˜ƒx = var2x % 4;
         int â˜ƒxx = â˜ƒ * 4 + 1 + â˜ƒ.nextInt(3) + â˜ƒ.getX();
         int â˜ƒxxx = â˜ƒx * 4 + 1 + â˜ƒ.nextInt(3) + â˜ƒ.getZ();
         return new BlockPos(â˜ƒxx, â˜ƒ.getY(), â˜ƒxxx);
      });
   }
}
