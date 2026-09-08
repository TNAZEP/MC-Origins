package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BuriedTreasurePieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class BuriedTreasureFeature extends StructureFeature<ProbabilityFeatureConfiguration> {
   private static final int RANDOM_SALT = 10387320;

   public BuriedTreasureFeature(Codec<ProbabilityFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   protected boolean isFeatureChunk(
      ChunkGenerator var1,
      BiomeSource var2,
      long var3,
      WorldgenRandom var5,
      ChunkPos var6,
      Biome var7,
      ChunkPos var8,
      ProbabilityFeatureConfiguration var9,
      LevelHeightAccessor var10
   ) {
      â˜ƒ.setLargeFeatureWithSalt(â˜ƒ, â˜ƒ.x, â˜ƒ.z, 10387320);
      return â˜ƒ.nextFloat() < â˜ƒ.probability;
   }

   @Override
   public StructureFeature.StructureStartFactory<ProbabilityFeatureConfiguration> getStartFactory() {
      return BuriedTreasureFeature.BuriedTreasureStart::new;
   }

   public static class BuriedTreasureStart extends StructureStart<ProbabilityFeatureConfiguration> {
      public BuriedTreasureStart(StructureFeature<ProbabilityFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1,
         ChunkGenerator var2,
         StructureManager var3,
         ChunkPos var4,
         Biome var5,
         ProbabilityFeatureConfiguration var6,
         LevelHeightAccessor var7
      ) {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ.getBlockX(9), 90, â˜ƒ.getBlockZ(9));
         this.addPiece(new BuriedTreasurePieces.BuriedTreasurePiece(â˜ƒ));
      }

      @Override
      public BlockPos getLocatePos() {
         ChunkPos â˜ƒ = this.getChunkPos();
         return new BlockPos(â˜ƒ.getBlockX(9), 0, â˜ƒ.getBlockZ(9));
      }
   }
}
