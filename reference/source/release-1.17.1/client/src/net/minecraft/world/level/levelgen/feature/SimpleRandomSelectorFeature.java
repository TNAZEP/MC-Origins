package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;

public class SimpleRandomSelectorFeature extends Feature<SimpleRandomFeatureConfiguration> {
   public SimpleRandomSelectorFeature(Codec<SimpleRandomFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<SimpleRandomFeatureConfiguration> var1) {
      Random â˜ƒ = â˜ƒ.random();
      SimpleRandomFeatureConfiguration â˜ƒx = â˜ƒ.config();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      BlockPos â˜ƒxxx = â˜ƒ.origin();
      ChunkGenerator â˜ƒxxxx = â˜ƒ.chunkGenerator();
      int â˜ƒxxxxx = â˜ƒ.nextInt(â˜ƒx.features.size());
      ConfiguredFeature<?, ?> â˜ƒxxxxxx = (ConfiguredFeature)((Supplier)â˜ƒx.features.get(â˜ƒxxxxx)).get();
      return â˜ƒxxxxxx.place(â˜ƒxx, â˜ƒxxxx, â˜ƒ, â˜ƒxxx);
   }
}
