package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;

public class CaveSurfaceDecorator extends FeatureDecorator<CaveDecoratorConfiguration> {
   public CaveSurfaceDecorator(Codec<CaveDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, CaveDecoratorConfiguration var3, BlockPos var4) {
      Optional<Column> â˜ƒ = Column.scan(
         â˜ƒ.getLevel(), â˜ƒ, â˜ƒ.floorToCeilingSearchRange, BlockBehaviour.BlockStateBase::isAir, var0 -> var0.getMaterial().isSolid()
      );
      if (!â˜ƒ.isPresent()) {
         return Stream.of();
      } else {
         OptionalInt â˜ƒ = â˜ƒ.surface == CaveSurface.CEILING ? ((Column)â˜ƒ.get()).getCeiling() : ((Column)â˜ƒ.get()).getFloor();
         return !â˜ƒ.isPresent() ? Stream.of() : Stream.of(â˜ƒ.atY(â˜ƒ.getAsInt() - â˜ƒ.surface.getY()));
      }
   }
}
