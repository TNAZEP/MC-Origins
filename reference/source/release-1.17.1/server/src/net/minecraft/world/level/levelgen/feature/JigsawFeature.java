package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureStart;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class JigsawFeature extends StructureFeature<JigsawConfiguration> {
   final int startY;
   final boolean doExpansionHack;
   final boolean projectStartToHeightmap;

   public JigsawFeature(Codec<JigsawConfiguration> var1, int var2, boolean var3, boolean var4) {
      super(â˜ƒ);
      this.startY = â˜ƒ;
      this.doExpansionHack = â˜ƒ;
      this.projectStartToHeightmap = â˜ƒ;
   }

   @Override
   public StructureFeature.StructureStartFactory<JigsawConfiguration> getStartFactory() {
      return (var1, var2, var3, var4) -> new JigsawFeature.FeatureStart(this, var2, var3, var4);
   }

   public static class FeatureStart extends NoiseAffectingStructureStart<JigsawConfiguration> {
      private final JigsawFeature feature;

      public FeatureStart(JigsawFeature var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.feature = â˜ƒ;
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, JigsawConfiguration var6, LevelHeightAccessor var7
      ) {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ.getMinBlockX(), this.feature.startY, â˜ƒ.getMinBlockZ());
         Pools.bootstrap();
         JigsawPlacement.addPieces(
            â˜ƒ, â˜ƒ, PoolElementStructurePiece::new, â˜ƒ, â˜ƒ, â˜ƒ, this, this.random, this.feature.doExpansionHack, this.feature.projectStartToHeightmap, â˜ƒ
         );
      }
   }
}
