package net.minecraft.world.level.levelgen.feature;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class FeaturePlaceContext<FC extends FeatureConfiguration> {
   private final WorldGenLevel level;
   private final ChunkGenerator chunkGenerator;
   private final Random random;
   private final BlockPos origin;
   private final FC config;

   public FeaturePlaceContext(WorldGenLevel var1, ChunkGenerator var2, Random var3, BlockPos var4, FC var5) {
      this.level = â˜ƒ;
      this.chunkGenerator = â˜ƒ;
      this.random = â˜ƒ;
      this.origin = â˜ƒ;
      this.config = â˜ƒ;
   }

   public WorldGenLevel level() {
      return this.level;
   }

   public ChunkGenerator chunkGenerator() {
      return this.chunkGenerator;
   }

   public Random random() {
      return this.random;
   }

   public BlockPos origin() {
      return this.origin;
   }

   public FC config() {
      return this.config;
   }
}
