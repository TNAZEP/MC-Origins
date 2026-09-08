package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;

public class RandomBooleanSelectorFeature extends Feature<RandomBooleanFeatureConfiguration> {
   public RandomBooleanSelectorFeature(Codec<RandomBooleanFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<RandomBooleanFeatureConfiguration> var1) {
      Random â˜ƒ = â˜ƒ.random();
      RandomBooleanFeatureConfiguration â˜ƒx = â˜ƒ.config();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      ChunkGenerator â˜ƒxxx = â˜ƒ.chunkGenerator();
      BlockPos â˜ƒxxxx = â˜ƒ.origin();
      boolean â˜ƒxxxxx = â˜ƒ.nextBoolean();
      return â˜ƒxxxxx
         ? ((ConfiguredFeature)â˜ƒx.featureTrue.get()).place(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒxxxx)
         : ((ConfiguredFeature)â˜ƒx.featureFalse.get()).place(â˜ƒxx, â˜ƒxxx, â˜ƒ, â˜ƒxxxx);
   }
}
