package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public abstract class VerticalDecorator<DC extends DecoratorConfiguration> extends FeatureDecorator<DC> {
   public VerticalDecorator(Codec<DC> var1) {
      super(â˜ƒ);
   }

   protected abstract int y(DecorationContext var1, Random var2, DC var3, int var4);

   @Override
   public final Stream<BlockPos> getPositions(DecorationContext var1, Random var2, DC var3, BlockPos var4) {
      return Stream.of(new BlockPos(â˜ƒ.getX(), this.y(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getY()), â˜ƒ.getZ()));
   }
}
