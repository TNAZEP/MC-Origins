package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

public class NoiseBasedDecorator extends RepeatingDecorator<NoiseCountFactorDecoratorConfiguration> {
   public NoiseBasedDecorator(Codec<NoiseCountFactorDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   protected int count(Random var1, NoiseCountFactorDecoratorConfiguration var2, BlockPos var3) {
      double â˜ƒ = Biome.BIOME_INFO_NOISE.getValue((double)â˜ƒ.getX() / â˜ƒ.noiseFactor, (double)â˜ƒ.getZ() / â˜ƒ.noiseFactor, false);
      return (int)Math.ceil((â˜ƒ + â˜ƒ.noiseOffset) * (double)â˜ƒ.noiseToCountRatio);
   }
}
