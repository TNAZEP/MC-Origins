package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class OceanMonumentFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> MONUMENT_ENEMIES = WeightedRandomList.create(
      new MobSpawnSettings.SpawnerData(EntityType.GUARDIAN, 1, 2, 4)
   );

   public OceanMonumentFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected boolean linearSeparation() {
      return false;
   }

   protected boolean isFeatureChunk(
      ChunkGenerator var1,
      BiomeSource var2,
      long var3,
      WorldgenRandom var5,
      ChunkPos var6,
      Biome var7,
      ChunkPos var8,
      NoneFeatureConfiguration var9,
      LevelHeightAccessor var10
   ) {
      int â˜ƒ = â˜ƒ.getBlockX(9);
      int â˜ƒx = â˜ƒ.getBlockZ(9);

      for(Biome â˜ƒxx : â˜ƒ.getBiomesWithin(â˜ƒ, â˜ƒ.getSeaLevel(), â˜ƒx, 16)) {
         if (!â˜ƒxx.getGenerationSettings().isValidStart(this)) {
            return false;
         }
      }

      for(Biome â˜ƒxx : â˜ƒ.getBiomesWithin(â˜ƒ, â˜ƒ.getSeaLevel(), â˜ƒx, 29)) {
         if (â˜ƒxx.getBiomeCategory() != Biome.BiomeCategory.OCEAN && â˜ƒxx.getBiomeCategory() != Biome.BiomeCategory.RIVER) {
            return false;
         }
      }

      return true;
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return OceanMonumentFeature.OceanMonumentStart::new;
   }

   @Override
   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialEnemies() {
      return MONUMENT_ENEMIES;
   }

   public static class OceanMonumentStart extends StructureStart<NoneFeatureConfiguration> {
      private boolean isCreated;

      public OceanMonumentStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         this.generatePieces(â˜ƒ);
      }

      private void generatePieces(ChunkPos var1) {
         int â˜ƒ = â˜ƒ.getMinBlockX() - 29;
         int â˜ƒx = â˜ƒ.getMinBlockZ() - 29;
         Direction â˜ƒxx = Direction.Plane.HORIZONTAL.getRandomDirection(this.random);
         this.addPiece(new OceanMonumentPieces.MonumentBuilding(this.random, â˜ƒ, â˜ƒx, â˜ƒxx));
         this.isCreated = true;
      }

      @Override
      public void placeInChunk(WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6) {
         if (!this.isCreated) {
            this.pieces.clear();
            this.generatePieces(this.getChunkPos());
         }

         super.placeInChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }
}
