package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NetherBridgePieces;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class NetherFortressFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> FORTRESS_ENEMIES = WeightedRandomList.create(
      new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 10, 2, 3),
      new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 5, 4, 4),
      new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 8, 5, 5),
      new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 2, 5, 5),
      new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 3, 4, 4)
   );

   public NetherFortressFeature(Codec<NoneFeatureConfiguration> var1) {
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
      NoneFeatureConfiguration var9,
      LevelHeightAccessor var10
   ) {
      return â˜ƒ.nextInt(5) < 2;
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return NetherFortressFeature.NetherBridgeStart::new;
   }

   @Override
   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialEnemies() {
      return FORTRESS_ENEMIES;
   }

   public static class NetherBridgeStart extends StructureStart<NoneFeatureConfiguration> {
      public NetherBridgeStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         NetherBridgePieces.StartPiece â˜ƒ = new NetherBridgePieces.StartPiece(this.random, â˜ƒ.getBlockX(2), â˜ƒ.getBlockZ(2));
         this.addPiece(â˜ƒ);
         â˜ƒ.addChildren(â˜ƒ, this, this.random);
         List<StructurePiece> â˜ƒx = â˜ƒ.pendingChildren;

         while(!â˜ƒx.isEmpty()) {
            int â˜ƒxx = this.random.nextInt(â˜ƒx.size());
            StructurePiece â˜ƒxxx = (StructurePiece)â˜ƒx.remove(â˜ƒxx);
            â˜ƒxxx.addChildren(â˜ƒ, this, this.random);
         }

         this.moveInsideHeights(this.random, 48, 70);
      }
   }
}
