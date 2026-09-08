package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class DecoratedFeature extends Feature<DecoratedFeatureConfiguration> {
   public DecoratedFeature(Codec<DecoratedFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<DecoratedFeatureConfiguration> var1) {
      MutableBoolean â˜ƒ = new MutableBoolean();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      DecoratedFeatureConfiguration â˜ƒxx = â˜ƒ.config();
      ChunkGenerator â˜ƒxxx = â˜ƒ.chunkGenerator();
      Random â˜ƒxxxx = â˜ƒ.random();
      BlockPos â˜ƒxxxxx = â˜ƒ.origin();
      ConfiguredFeature<?, ?> â˜ƒxxxxxx = (ConfiguredFeature)â˜ƒxx.feature.get();
      â˜ƒxx.decorator.getPositions(new DecorationContext(â˜ƒx, â˜ƒxxx), â˜ƒxxxx, â˜ƒxxxxx).forEach(var5x -> {
         if (â˜ƒ.place(â˜ƒ, â˜ƒ, â˜ƒ, var5x)) {
            â˜ƒ.setTrue();
         }
      });
      return â˜ƒ.isTrue();
   }

   public String toString() {
      return String.format("< %s [%s] >", this.getClass().getSimpleName(), Registry.FEATURE.getKey(this));
   }
}
