package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.configurations.NoiseDependantDecoratorConfiguration;

public class CountNoiseDecorator extends RepeatingDecorator<NoiseDependantDecoratorConfiguration> {
   public CountNoiseDecorator(Codec<NoiseDependantDecoratorConfiguration> var1) {
      super(â˜ƒ);
   }

   protected int count(Random var1, NoiseDependantDecoratorConfiguration var2, BlockPos var3) {
      double â˜ƒ = Biome.BIOME_INFO_NOISE.getValue((double)â˜ƒ.getX() / 200.0, (double)â˜ƒ.getZ() / 200.0, false);
      return â˜ƒ < â˜ƒ.noiseLevel ? â˜ƒ.belowNoise : â˜ƒ.aboveNoise;
   }
}
