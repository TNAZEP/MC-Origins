package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;

public class IcebergPlacementDecorator extends FeatureDecorator<NoneDecoratorConfiguration> {
   public IcebergPlacementDecorator(Codec<NoneDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, NoneDecoratorConfiguration var3, BlockPos var4) {
      int â˜ƒ = â˜ƒ.nextInt(8) + 4 + â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.nextInt(8) + 4 + â˜ƒ.getZ();
      return Stream.of(new BlockPos(â˜ƒ, â˜ƒ.getY(), â˜ƒx));
   }
}
