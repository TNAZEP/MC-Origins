package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.SwamplandHutPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

public class SwamplandHutFeature extends StructureFeature<NoneFeatureConfiguration> {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> SWAMPHUT_ENEMIES = WeightedRandomList.create(
      new MobSpawnSettings.SpawnerData(EntityType.WITCH, 1, 1, 1)
   );
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> SWAMPHUT_ANIMALS = WeightedRandomList.create(
      new MobSpawnSettings.SpawnerData(EntityType.CAT, 1, 1, 1)
   );

   public SwamplandHutFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public StructureFeature.StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
      return SwamplandHutFeature.FeatureStart::new;
   }

   @Override
   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialEnemies() {
      return SWAMPHUT_ENEMIES;
   }

   @Override
   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialAnimals() {
      return SWAMPHUT_ANIMALS;
   }

   public static class FeatureStart extends StructureStart<NoneFeatureConfiguration> {
      public FeatureStart(StructureFeature<NoneFeatureConfiguration> var1, ChunkPos var2, int var3, long var4) {
         super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }

      public void generatePieces(
         RegistryAccess var1, ChunkGenerator var2, StructureManager var3, ChunkPos var4, Biome var5, NoneFeatureConfiguration var6, LevelHeightAccessor var7
      ) {
         SwamplandHutPiece â˜ƒ = new SwamplandHutPiece(this.random, â˜ƒ.getMinBlockX(), â˜ƒ.getMinBlockZ());
         this.addPiece(â˜ƒ);
      }
   }
}
