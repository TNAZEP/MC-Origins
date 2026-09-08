package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

public class CarvingMaskDecorator extends FeatureDecorator<CarvingMaskDecoratorConfiguration> {
   public CarvingMaskDecorator(Codec<CarvingMaskDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   public Stream<BlockPos> getPositions(DecorationContext var1, Random var2, CarvingMaskDecoratorConfiguration var3, BlockPos var4) {
      ChunkPos â˜ƒ = new ChunkPos(â˜ƒ);
      BitSet â˜ƒx = â˜ƒ.getCarvingMask(â˜ƒ, â˜ƒ.step);
      return IntStream.range(0, â˜ƒx.length()).filter(â˜ƒx::get).mapToObj(var1x -> {
         int â˜ƒ = var1x & 15;
         int â˜ƒx = var1x >> 4 & 15;
         int â˜ƒxx = var1x >> 8;
         return new BlockPos(â˜ƒ.getMinBlockX() + â˜ƒ, â˜ƒxx, â˜ƒ.getMinBlockZ() + â˜ƒx);
      });
   }
}
