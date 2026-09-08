package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

public class PillagerOutpostFeature extends JigsawFeature {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> OUTPOST_ENEMIES = WeightedRandomList.create(
      new MobSpawnSettings.SpawnerData(EntityType.PILLAGER, 1, 1, 1)
   );

   public PillagerOutpostFeature(Codec<JigsawConfiguration> var1) {
      super(â˜ƒ, 0, true, true);
   }

   @Override
   public WeightedRandomList<MobSpawnSettings.SpawnerData> getSpecialEnemies() {
      return OUTPOST_ENEMIES;
   }

   protected boolean isFeatureChunk(
      ChunkGenerator var1,
      BiomeSource var2,
      long var3,
      WorldgenRandom var5,
      ChunkPos var6,
      Biome var7,
      ChunkPos var8,
      JigsawConfiguration var9,
      LevelHeightAccessor var10
   ) {
      int â˜ƒ = â˜ƒ.x >> 4;
      int â˜ƒx = â˜ƒ.z >> 4;
      â˜ƒ.setSeed((long)(â˜ƒ ^ â˜ƒx << 4) ^ â˜ƒ);
      â˜ƒ.nextInt();
      if (â˜ƒ.nextInt(5) != 0) {
         return false;
      } else {
         return !this.isNearVillage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private boolean isNearVillage(ChunkGenerator var1, long var2, WorldgenRandom var4, ChunkPos var5) {
      StructureFeatureConfiguration â˜ƒ = â˜ƒ.getSettings().getConfig(StructureFeature.VILLAGE);
      if (â˜ƒ == null) {
         return false;
      } else {
         int â˜ƒ = â˜ƒ.x;
         int â˜ƒx = â˜ƒ.z;

         for(int â˜ƒxx = â˜ƒ - 10; â˜ƒxx <= â˜ƒ + 10; ++â˜ƒxx) {
            for(int â˜ƒxxx = â˜ƒx - 10; â˜ƒxxx <= â˜ƒx + 10; ++â˜ƒxxx) {
               ChunkPos â˜ƒxxxx = StructureFeature.VILLAGE.getPotentialFeatureChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx, â˜ƒxxx);
               if (â˜ƒxx == â˜ƒxxxx.x && â˜ƒxxx == â˜ƒxxxx.z) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
