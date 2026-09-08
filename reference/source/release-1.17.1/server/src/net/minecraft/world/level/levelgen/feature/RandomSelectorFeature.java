package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

public class RandomSelectorFeature extends Feature<RandomFeatureConfiguration> {
   public RandomSelectorFeature(Codec<RandomFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<RandomFeatureConfiguration> var1) {
      RandomFeatureConfiguration â˜ƒ = â˜ƒ.config();
      Random â˜ƒx = â˜ƒ.random();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      ChunkGenerator â˜ƒxxx = â˜ƒ.chunkGenerator();
      BlockPos â˜ƒxxxx = â˜ƒ.origin();

      for(WeightedConfiguredFeature â˜ƒxxxxx : â˜ƒ.features) {
         if (â˜ƒx.nextFloat() < â˜ƒxxxxx.chance) {
            return â˜ƒxxxxx.place(â˜ƒxx, â˜ƒxxx, â˜ƒx, â˜ƒxxxx);
         }
      }

      return ((ConfiguredFeature)â˜ƒ.defaultFeature.get()).place(â˜ƒxx, â˜ƒxxx, â˜ƒx, â˜ƒxxxx);
   }
}
