package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class EndCityFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final int RANDOM_SALT = 10387313;

   public EndCityFeature(Codec<NoneFeatureConfiguration> var1) {
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
      return getYPositionForFeature(â˜ƒ, â˜ƒ, â˜ƒ) >= 60;
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return EndCityFeature.EndCityStart::new;
   }

   static int getYPositionForFeature(ChunkPos var0, ChunkGenerator var1, LevelHeightAccessor var2) {
      Random â˜ƒ = new Random((long)(â˜ƒ.x + â˜ƒ.z * 10387313));
      Rotation â˜ƒx = Rotation.getRandom(â˜ƒ);
      int â˜ƒxx = 5;
      int â˜ƒxxx = 5;
      if (â˜ƒx == Rotation.CLOCKWISE_90) {
         â˜ƒxx = -5;
      } else if (â˜ƒx == Rotation.CLOCKWISE_180) {
         â˜ƒxx = -5;
         â˜ƒxxx = -5;
      } else if (â˜ƒx == Rotation.COUNTERCLOCKWISE_90) {
         â˜ƒxxx = -5;
      }

      int â˜ƒ = â˜ƒ.getBlockX(7);
      int â˜ƒx = â˜ƒ.getBlockZ(7);
      int â˜ƒxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ, â˜ƒx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
      int â˜ƒxxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ, â˜ƒx + â˜ƒxxx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
      int â˜ƒxxxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ + â˜ƒxx, â˜ƒx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
      int â˜ƒxxxxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ + â˜ƒxx, â˜ƒx + â˜ƒxxx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
      return Math.min(Math.min(â˜ƒxx, â˜ƒxxx), Math.min(â˜ƒxxxx, â˜ƒxxxxx));
   }

   public static class EndCityStart extends StructureStart<NoneFeatureConfiguration> {
      public EndCityStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         Rotation â˜ƒ = Rotation.getRandom(this.random);
         int â˜ƒx = EndCityFeature.getYPositionForFeature(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒx >= 60) {
            BlockPos â˜ƒxx = â˜ƒ.getMiddleBlockPosition(â˜ƒx);
            List<StructurePiece> â˜ƒxxx = Lists.<StructurePiece>newArrayList();
            EndCityPieces.startHouseTower(â˜ƒ, â˜ƒxx, â˜ƒ, â˜ƒxxx, this.random);
            â˜ƒxxx.forEach(this::addPiece);
         }
      }
   }
}
