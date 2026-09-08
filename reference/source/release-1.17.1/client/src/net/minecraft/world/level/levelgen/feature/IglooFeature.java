package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.IglooPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class IglooFeature extends StructureFeature<NoneFeatureConfiguration> {
   public IglooFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return IglooFeature.FeatureStart::new;
   }

   public static class FeatureStart extends StructureStart<NoneFeatureConfiguration> {
      public FeatureStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         BlockPos â˜ƒ = new BlockPos(â˜ƒ.getMinBlockX(), 90, â˜ƒ.getMinBlockZ());
         Rotation â˜ƒx = Rotation.getRandom(this.random);
         IglooPieces.addPieces(â˜ƒ, â˜ƒ, â˜ƒx, this, this.random);
      }
   }
}
