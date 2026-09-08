package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class WoodlandMansionFeature extends StructureFeature<NoneFeatureConfiguration> {
   public WoodlandMansionFeature(Codec<NoneFeatureConfiguration> var1) {
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
      for(Biome â˜ƒ : â˜ƒ.getBiomesWithin(â˜ƒ.getBlockX(9), â˜ƒ.getSeaLevel(), â˜ƒ.getBlockZ(9), 32)) {
         if (!â˜ƒ.getGenerationSettings().isValidStart(this)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return WoodlandMansionFeature.WoodlandMansionStart::new;
   }

   public static class WoodlandMansionStart extends StructureStart<NoneFeatureConfiguration> {
      public WoodlandMansionStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         Rotation â˜ƒ = Rotation.getRandom(this.random);
         int â˜ƒx = 5;
         int â˜ƒxx = 5;
         if (â˜ƒ == Rotation.CLOCKWISE_90) {
            â˜ƒx = -5;
         } else if (â˜ƒ == Rotation.CLOCKWISE_180) {
            â˜ƒx = -5;
            â˜ƒxx = -5;
         } else if (â˜ƒ == Rotation.COUNTERCLOCKWISE_90) {
            â˜ƒxx = -5;
         }

         int â˜ƒ = â˜ƒ.getBlockX(7);
         int â˜ƒx = â˜ƒ.getBlockZ(7);
         int â˜ƒxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ, â˜ƒx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
         int â˜ƒxxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ, â˜ƒx + â˜ƒxx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
         int â˜ƒxxxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ + â˜ƒx, â˜ƒx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
         int â˜ƒxxxxx = â˜ƒ.getFirstOccupiedHeight(â˜ƒ + â˜ƒx, â˜ƒx + â˜ƒxx, Heightmap.Types.WORLD_SURFACE_WG, â˜ƒ);
         int â˜ƒxxxxxx = Math.min(Math.min(â˜ƒxx, â˜ƒxxx), Math.min(â˜ƒxxxx, â˜ƒxxxxx));
         if (â˜ƒxxxxxx >= 60) {
            BlockPos â˜ƒxxxxxxx = new BlockPos(â˜ƒ.getBlockX(8), â˜ƒxxxxxx + 1, â˜ƒ.getBlockZ(8));
            List<WoodlandMansionPieces.WoodlandMansionPiece> â˜ƒxxxxxxxx = Lists.<WoodlandMansionPieces.WoodlandMansionPiece>newLinkedList();
            WoodlandMansionPieces.generateMansion(â˜ƒ, â˜ƒxxxxxxx, â˜ƒ, â˜ƒxxxxxxxx, this.random);
            â˜ƒxxxxxxxx.forEach(this::addPiece);
         }
      }

      @Override
      public void placeInChunk(WorldGenLevel var1, StructureFeatureManager var2, ChunkGenerator var3, Random var4, BoundingBox var5, ChunkPos var6) {
         super.placeInChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         BoundingBox â˜ƒ = this.getBoundingBox();
         int â˜ƒx = â˜ƒ.minY();

         for(int â˜ƒxx = â˜ƒ.minX(); â˜ƒxx <= â˜ƒ.maxX(); ++â˜ƒxx) {
            for(int â˜ƒxxx = â˜ƒ.minZ(); â˜ƒxxx <= â˜ƒ.maxZ(); ++â˜ƒxxx) {
               BlockPos â˜ƒxxxx = new BlockPos(â˜ƒxx, â˜ƒx, â˜ƒxxx);
               if (!â˜ƒ.isEmptyBlock(â˜ƒxxxx) && â˜ƒ.isInside(â˜ƒxxxx) && this.isInsidePiece(â˜ƒxxxx)) {
                  for(int â˜ƒxxxxx = â˜ƒx - 1; â˜ƒxxxxx > 1; --â˜ƒxxxxx) {
                     BlockPos â˜ƒxxxxxx = new BlockPos(â˜ƒxx, â˜ƒxxxxx, â˜ƒxxx);
                     if (!â˜ƒ.isEmptyBlock(â˜ƒxxxxxx) && !â˜ƒ.getBlockState(â˜ƒxxxxxx).getMaterial().isLiquid()) {
                        break;
                     }

                     â˜ƒ.setBlock(â˜ƒxxxxxx, Blocks.COBBLESTONE.defaultBlockState(), 2);
                  }
               }
            }
         }
      }
   }
}
