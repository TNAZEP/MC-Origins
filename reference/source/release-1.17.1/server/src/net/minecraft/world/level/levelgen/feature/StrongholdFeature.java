package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NoiseAffectingStructureStart;
import net.minecraft.world.level.levelgen.structure.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class StrongholdFeature extends StructureFeature<NoneFeatureConfiguration> {
   public StrongholdFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return StrongholdFeature.StrongholdStart::new;
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
      return â˜ƒ.hasStronghold(â˜ƒ);
   }

   public static class StrongholdStart extends NoiseAffectingStructureStart<NoneFeatureConfiguration> {
      private final long seed;

      public StrongholdStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.seed = â˜ƒ;
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         int â˜ƒ = 0;

         StrongholdPieces.StartPiece â˜ƒ;
         do {
            this.clearPieces();
            this.random.setLargeFeatureSeed(this.seed + (long)(â˜ƒ++), â˜ƒ.x, â˜ƒ.z);
            StrongholdPieces.resetPieces();
            â˜ƒ = new StrongholdPieces.StartPiece(this.random, â˜ƒ.getBlockX(2), â˜ƒ.getBlockZ(2));
            this.addPiece(â˜ƒ);
            â˜ƒ.addChildren(â˜ƒ, this, this.random);
            List<StructurePiece> â˜ƒx = â˜ƒ.pendingChildren;

            while(!â˜ƒx.isEmpty()) {
               int â˜ƒxx = this.random.nextInt(â˜ƒx.size());
               StructurePiece â˜ƒxxx = (StructurePiece)â˜ƒx.remove(â˜ƒxx);
               â˜ƒxxx.addChildren(â˜ƒ, this, this.random);
            }

            this.moveBelowSeaLevel(â˜ƒ.getSeaLevel(), â˜ƒ.getMinY(), this.random, 10);
         } while(this.hasNoPieces() || â˜ƒ.portalRoomPiece == null);
      }
   }
}
