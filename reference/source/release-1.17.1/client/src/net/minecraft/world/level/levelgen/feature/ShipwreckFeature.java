package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.structure.ShipwreckPieces;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class ShipwreckFeature extends StructureFeature<ShipwreckConfiguration> {
   public ShipwreckFeature(Codec<ShipwreckConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public StructureFeature.StructureStartFactory<ShipwreckConfiguration> getStartFactory() {
      return ShipwreckFeature.FeatureStart::new;
   }

   public static class FeatureStart extends StructureStart<ShipwreckConfiguration> {
      public FeatureStart(StructureFeature<ShipwreckConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, ShipwreckConfiguration var6, LevelHeightAccessor var7
      ) {
         Rotation â˜ƒ = Rotation.getRandom(this.random);
         BlockPos â˜ƒx = new BlockPos(â˜ƒ.getMinBlockX(), 90, â˜ƒ.getMinBlockZ());
         ShipwreckPieces.addPieces(â˜ƒ, â˜ƒx, â˜ƒ, this, this.random, â˜ƒ);
      }
   }
}
