package net.minecraft.data.worldgen;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeDefaultFeatures {
   public static void addDefaultOverworldLandMesaStructures(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addStructureStart(StructureFeatures.MINESHAFT_MESA);
      â˜ƒ.addStructureStart(StructureFeatures.STRONGHOLD);
   }

   public static void addDefaultOverworldLandStructures(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addStructureStart(StructureFeatures.MINESHAFT);
      â˜ƒ.addStructureStart(StructureFeatures.STRONGHOLD);
   }

   public static void addDefaultOverworldOceanStructures(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addStructureStart(StructureFeatures.MINESHAFT);
      â˜ƒ.addStructureStart(StructureFeatures.SHIPWRECK);
   }

   public static void addDefaultCarvers(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
      â˜ƒ.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
   }

   public static void addOceanCarvers(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addCarver(GenerationStep.Carving.AIR, Carvers.OCEAN_CAVE);
      â˜ƒ.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
      â˜ƒ.addCarver(GenerationStep.Carving.LIQUID, Carvers.UNDERWATER_CANYON);
      â˜ƒ.addCarver(GenerationStep.Carving.LIQUID, Carvers.UNDERWATER_CAVE);
   }

   public static void addDefaultLakes(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.LAKES, Features.LAKE_WATER);
      â˜ƒ.addFeature(GenerationStep.Decoration.LAKES, Features.LAKE_LAVA);
   }

   public static void addDesertLakes(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.LAKES, Features.LAKE_LAVA);
   }

   public static void addDefaultMonsterRoom(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, Features.MONSTER_ROOM);
   }

   public static void addDefaultUndergroundVariety(BiomeGenerationSettings.Builder var0) {
      addDefaultUndergroundVariety(â˜ƒ, false);
   }

   public static void addDefaultUndergroundVariety(BiomeGenerationSettings.Builder var0, boolean var1) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_DIRT);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_GRAVEL);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_GRANITE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_DIORITE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_ANDESITE);
      if (!â˜ƒ) {
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.GLOW_LICHEN);
      }

      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_TUFF);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_DEEPSLATE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.RARE_DRIPSTONE_CLUSTER_FEATURE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.RARE_SMALL_DRIPSTONE_FEATURE);
   }

   public static void addDripstone(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.LARGE_DRIPSTONE_FEATURE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.DRIPSTONE_CLUSTER_FEATURE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SMALL_DRIPSTONE_FEATURE);
   }

   public static void addDefaultOres(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_COAL);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_IRON);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_GOLD);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_REDSTONE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_DIAMOND);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_LAPIS);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_COPPER);
   }

   public static void addExtraGold(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_GOLD_EXTRA);
   }

   public static void addExtraEmeralds(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_EMERALD);
   }

   public static void addInfestedStone(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_INFESTED);
   }

   public static void addDefaultSoftDisks(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.DISK_SAND);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.DISK_CLAY);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.DISK_GRAVEL);
   }

   public static void addSwampClayDisk(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.DISK_CLAY);
   }

   public static void addMossyStoneBlock(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.FOREST_ROCK);
   }

   public static void addFerns(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_LARGE_FERN);
   }

   public static void addBerryBushes(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_BERRY_DECORATED);
   }

   public static void addSparseBerryBushes(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_BERRY_SPARSE);
   }

   public static void addLightBambooVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BAMBOO_LIGHT);
   }

   public static void addBambooVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BAMBOO);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BAMBOO_VEGETATION);
   }

   public static void addTaigaTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TAIGA_VEGETATION);
   }

   public static void addWaterTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_WATER);
   }

   public static void addBirchTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_BIRCH);
   }

   public static void addOtherBirchTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BIRCH_OTHER);
   }

   public static void addTallBirchTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BIRCH_TALL);
   }

   public static void addSavannaTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_SAVANNA);
   }

   public static void addShatteredSavannaTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_SHATTERED_SAVANNA);
   }

   public static void addLushCavesVegetationFeatures(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.LUSH_CAVES_CEILING_VEGETATION);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.CAVE_VINES);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.LUSH_CAVES_CLAY);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.LUSH_CAVES_VEGETATION);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.ROOTED_AZALEA_TREES);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPORE_BLOSSOM_FEATURE);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.CLASSIC_VINES_CAVE_FEATURE);
   }

   public static void addLushCavesSpecialOres(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Features.ORE_CLAY);
   }

   public static void addMountainTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_MOUNTAIN);
   }

   public static void addMountainEdgeTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_MOUNTAIN_EDGE);
   }

   public static void addJungleTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_JUNGLE);
   }

   public static void addJungleEdgeTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_JUNGLE_EDGE);
   }

   public static void addBadlandsTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_BADLANDS);
   }

   public static void addSnowyTrees(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_SNOWY);
   }

   public static void addJungleGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_JUNGLE);
   }

   public static void addSavannaGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_TALL_GRASS);
   }

   public static void addShatteredSavannaGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
   }

   public static void addSavannaExtraGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_SAVANNA);
   }

   public static void addBadlandGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_BADLANDS);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH_BADLANDS);
   }

   public static void addForestFlowers(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_VEGETATION);
   }

   public static void addForestGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_FOREST);
   }

   public static void addSwampVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TREES_SWAMP);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_SWAMP);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_SWAMP);
   }

   public static void addMushroomFieldVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.MUSHROOM_FIELD_VEGETATION);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_TAIGA);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_TAIGA);
   }

   public static void addPlainVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PLAIN_VEGETATION);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_PLAIN_DECORATED);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_PLAIN);
   }

   public static void addDesertVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH_2);
   }

   public static void addGiantTaigaVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_TAIGA);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_GIANT);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_GIANT);
   }

   public static void addDefaultFlowers(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_DEFAULT);
   }

   public static void addWarmFlowers(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_WARM);
   }

   public static void addDefaultGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_BADLANDS);
   }

   public static void addTaigaGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_TAIGA_2);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_TAIGA);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_TAIGA);
   }

   public static void addPlainGrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_TALL_GRASS_2);
   }

   public static void addDefaultMushrooms(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_NORMAL);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_NORMAL);
   }

   public static void addDefaultExtraVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
   }

   public static void addBadlandExtraVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE_BADLANDS);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_CACTUS_DECORATED);
   }

   public static void addJungleExtraVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_MELON);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.VINES);
   }

   public static void addDesertExtraVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE_DESERT);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_CACTUS_DESERT);
   }

   public static void addSwampExtraVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE_SWAMP);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
   }

   public static void addDesertExtraDecoration(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.WELL);
   }

   public static void addFossilDecoration(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, Features.FOSSIL);
   }

   public static void addColdOceanExtraVegetation(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.KELP_COLD);
   }

   public static void addDefaultSeagrass(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SIMPLE);
   }

   public static void addLukeWarmKelp(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.KELP_WARM);
   }

   public static void addDefaultSprings(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
   }

   public static void addIcebergs(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.ICEBERG_PACKED);
      â˜ƒ.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.ICEBERG_BLUE);
   }

   public static void addBlueIce(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.BLUE_ICE);
   }

   public static void addSurfaceFreezing(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Features.FREEZE_TOP_LAYER);
   }

   public static void addNetherDefaultOres(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_GRAVEL_NETHER);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_BLACKSTONE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_GOLD_NETHER);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_QUARTZ_NETHER);
      addAncientDebris(â˜ƒ);
   }

   public static void addAncientDebris(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_DEBRIS_LARGE);
      â˜ƒ.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_DEBRIS_SMALL);
   }

   public static void addDefaultCrystalFormations(BiomeGenerationSettings.Builder var0) {
      â˜ƒ.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.AMETHYST_GEODE);
   }

   public static void farmAnimals(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
   }

   public static void caveSpawns(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8));
      caveWaterSpawns(â˜ƒ);
   }

   public static void commonSpawns(MobSpawnSettings.Builder var0) {
      caveSpawns(â˜ƒ);
      monsters(â˜ƒ, 95, 5, 100);
   }

   public static void caveWaterSpawns(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 6));
      â˜ƒ.addSpawn(MobCategory.UNDERGROUND_WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.AXOLOTL, 10, 4, 6));
   }

   public static void oceanSpawns(MobSpawnSettings.Builder var0, int var1, int var2, int var3) {
      â˜ƒ.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, â˜ƒ, 1, â˜ƒ));
      â˜ƒ.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, â˜ƒ, 3, 6));
      commonSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
   }

   public static void warmOceanSpawns(MobSpawnSettings.Builder var0, int var1, int var2) {
      â˜ƒ.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, â˜ƒ, â˜ƒ, 4));
      â˜ƒ.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8));
      â˜ƒ.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
      commonSpawns(â˜ƒ);
   }

   public static void plainsSpawns(MobSpawnSettings.Builder var0) {
      farmAnimals(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 5, 2, 6));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 3));
      commonSpawns(â˜ƒ);
   }

   public static void snowySpawns(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
      caveSpawns(â˜ƒ);
      monsters(â˜ƒ, 95, 5, 20);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.STRAY, 80, 4, 4));
   }

   public static void desertSpawns(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
      caveSpawns(â˜ƒ);
      monsters(â˜ƒ, 19, 1, 100);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HUSK, 80, 4, 4));
   }

   public static void monsters(MobSpawnSettings.Builder var0, int var1, int var2, int var3) {
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 100, 4, 4));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, â˜ƒ, 4, 4));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, â˜ƒ, 1, 1));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, â˜ƒ, 4, 4));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 100, 4, 4));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 100, 4, 4));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4));
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 5, 1, 1));
   }

   public static void mooshroomSpawns(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.MOOSHROOM, 8, 4, 8));
      caveSpawns(â˜ƒ);
   }

   public static void baseJungleSpawns(MobSpawnSettings.Builder var0) {
      farmAnimals(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
      commonSpawns(â˜ƒ);
   }

   public static void endSpawns(MobSpawnSettings.Builder var0) {
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 4, 4));
   }
}
