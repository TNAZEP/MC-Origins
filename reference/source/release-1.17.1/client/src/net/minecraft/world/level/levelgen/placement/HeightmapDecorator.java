package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;

public class HeightmapDecorator extends FeatureDecorator<HeightmapConfiguration> {
   public HeightmapDecorator(Codec<HeightmapConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, HeightmapConfiguration var3, BlockPos var4) {
      int â˜ƒ = â˜ƒ.getX();
      int â˜ƒx = â˜ƒ.getZ();
      int â˜ƒxx = â˜ƒ.getHeight(â˜ƒ.heightmap, â˜ƒ, â˜ƒx);
      return â˜ƒxx > â˜ƒ.getMinBuildHeight() ? Stream.of(new BlockPos(â˜ƒ, â˜ƒxx, â˜ƒx)) : Stream.of();
   }
}
