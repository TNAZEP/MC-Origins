package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;

public class WaterDepthThresholdDecorator extends FeatureDecorator<WaterDepthThresholdConfiguration> {
   public WaterDepthThresholdDecorator(Codec<WaterDepthThresholdConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, WaterDepthThresholdConfiguration var3, BlockPos var4) {
      int â˜ƒ = â˜ƒ.getHeight(Heightmap.Types.OCEAN_FLOOR, â˜ƒ.getX(), â˜ƒ.getZ());
      int â˜ƒx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒ.getX(), â˜ƒ.getZ());
      return â˜ƒx - â˜ƒ > â˜ƒ.maxWaterDepth ? Stream.of() : Stream.of(â˜ƒ);
   }
}
