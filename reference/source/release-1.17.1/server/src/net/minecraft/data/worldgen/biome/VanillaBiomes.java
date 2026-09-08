package net.minecraft.data.worldgen.biome;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

public class VanillaBiomes {
   private static int calculateSkyColor(float var0) {
      float var1 = â˜ƒ / 3.0F;
      var1 = Mth.clamp(var1, -1.0F, 1.0F);
      return Mth.hsvToRgb(0.62222224F - var1 * 0.05F, 0.5F + var1 * 0.1F, 1.0F);
   }

   public static Biome giantTreeTaiga(float var0, float var1, float var2, boolean var3) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
      if (â˜ƒ) {
         BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      } else {
         BiomeDefaultFeatures.caveSpawns(â˜ƒ);
         BiomeDefaultFeatures.monsters(â˜ƒ, 100, 25, 100);
      }

      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GIANT_TREE_TAIGA);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒ);
      â˜ƒ.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      BiomeDefaultFeatures.addMossyStoneBlock(â˜ƒ);
      BiomeDefaultFeatures.addFerns(â˜ƒ);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, â˜ƒ ? Features.TREES_GIANT_SPRUCE : Features.TREES_GIANT);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒ);
      BiomeDefaultFeatures.addGiantTaigaVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      BiomeDefaultFeatures.addSparseBerryBushes(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.TAIGA)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(â˜ƒ)
         .downfall(0.8F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(â˜ƒ))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   public static Biome birchForestBiome(float var0, float var1, boolean var2) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addForestFlowers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addTallBirchTrees(â˜ƒx);
      } else {
         BiomeDefaultFeatures.addBirchTrees(â˜ƒx);
      }

      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addForestGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.FOREST)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.6F)
         .downfall(0.6F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.6F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome jungleBiome() {
      return jungleBiome(0.1F, 0.2F, 40, 2, 3);
   }

   public static Biome jungleEdgeBiome() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.baseJungleSpawns(â˜ƒ);
      return baseJungleBiome(0.1F, 0.2F, 0.8F, false, true, false, â˜ƒ);
   }

   public static Biome modifiedJungleEdgeBiome() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.baseJungleSpawns(â˜ƒ);
      return baseJungleBiome(0.2F, 0.4F, 0.8F, false, true, true, â˜ƒ);
   }

   public static Biome modifiedJungleBiome() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.baseJungleSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, 10, 1, 1))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 2, 1, 1));
      return baseJungleBiome(0.2F, 0.4F, 0.9F, false, false, true, â˜ƒ);
   }

   public static Biome jungleHillsBiome() {
      return jungleBiome(0.45F, 0.3F, 10, 1, 1);
   }

   public static Biome bambooJungleBiome() {
      return bambooJungleBiome(0.1F, 0.2F, 40, 2);
   }

   public static Biome bambooJungleHillsBiome() {
      return bambooJungleBiome(0.45F, 0.3F, 10, 1);
   }

   private static Biome jungleBiome(float var0, float var1, int var2, int var3, int var4) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.baseJungleSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, â˜ƒ, 1, â˜ƒ))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 2, 1, â˜ƒ))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 1, 1, 2));
      â˜ƒ.setPlayerCanSpawn();
      return baseJungleBiome(â˜ƒ, â˜ƒ, 0.9F, false, false, false, â˜ƒ);
   }

   private static Biome bambooJungleBiome(float var0, float var1, int var2, int var3) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.baseJungleSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PARROT, â˜ƒ, 1, â˜ƒ))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PANDA, 80, 1, 2))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.OCELOT, 2, 1, 1));
      return baseJungleBiome(â˜ƒ, â˜ƒ, 0.9F, true, false, false, â˜ƒ);
   }

   private static Biome baseJungleBiome(float var0, float var1, float var2, boolean var3, boolean var4, boolean var5, MobSpawnSettings.Builder var6) {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      if (!â˜ƒ && !â˜ƒ) {
         â˜ƒ.addStructureStart(StructureFeatures.JUNGLE_TEMPLE);
      }

      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒ);
      â˜ƒ.addStructureStart(StructureFeatures.RUINED_PORTAL_JUNGLE);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addBambooVegetation(â˜ƒ);
      } else {
         if (!â˜ƒ && !â˜ƒ) {
            BiomeDefaultFeatures.addLightBambooVegetation(â˜ƒ);
         }

         if (â˜ƒ) {
            BiomeDefaultFeatures.addJungleEdgeTrees(â˜ƒ);
         } else {
            BiomeDefaultFeatures.addJungleTrees(â˜ƒ);
         }
      }

      BiomeDefaultFeatures.addWarmFlowers(â˜ƒ);
      BiomeDefaultFeatures.addJungleGrass(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      BiomeDefaultFeatures.addJungleExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.JUNGLE)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.95F)
         .downfall(â˜ƒ)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.95F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   public static Biome mountainBiome(float var0, float var1, ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> var2, boolean var3) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.LLAMA, 5, 4, 6));
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 10, 4, 6));
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_MOUNTAIN);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addMountainEdgeTrees(â˜ƒx);
      } else {
         BiomeDefaultFeatures.addMountainTrees(â˜ƒx);
      }

      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addExtraEmeralds(â˜ƒx);
      BiomeDefaultFeatures.addInfestedStone(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.EXTREME_HILLS)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.2F)
         .downfall(0.3F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.2F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome desertBiome(float var0, float var1, boolean var2, boolean var3, boolean var4) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.desertSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.DESERT);
      if (â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.VILLAGE_DESERT);
         â˜ƒx.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
      }

      if (â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.DESERT_PYRAMID);
      }

      if (â˜ƒ) {
         BiomeDefaultFeatures.addFossilDecoration(â˜ƒx);
      }

      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_DESERT);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDesertLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒx);
      BiomeDefaultFeatures.addDesertVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDesertExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addDesertExtraDecoration(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.DESERT)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(2.0F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome plainsBiome(boolean var0) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.plainsSpawns(â˜ƒ);
      if (!â˜ƒ) {
         â˜ƒ.setPlayerCanSpawn();
      }

      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      if (!â˜ƒ) {
         â˜ƒ.addStructureStart(StructureFeatures.VILLAGE_PLAINS).addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
      }

      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒ);
      â˜ƒ.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      BiomeDefaultFeatures.addPlainGrass(â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_SUNFLOWER);
      }

      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      BiomeDefaultFeatures.addPlainVegetation(â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE);
      }

      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN);
      } else {
         BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      }

      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.PLAINS)
         .depth(0.125F)
         .scale(0.05F)
         .temperature(0.8F)
         .downfall(0.4F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.8F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   private static Biome baseEndBiome(BiomeGenerationSettings.Builder var0) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.endSpawns(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.THEEND)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(0.5F)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(10518688)
               .skyColor(0)
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   public static Biome endBarrensBiome() {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.END);
      return baseEndBiome(â˜ƒ);
   }

   public static Biome theEndBiome() {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.END)
         .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.END_SPIKE);
      return baseEndBiome(â˜ƒ);
   }

   public static Biome endMidlandsBiome() {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.END)
         .addStructureStart(StructureFeatures.END_CITY);
      return baseEndBiome(â˜ƒ);
   }

   public static Biome endHighlandsBiome() {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.END)
         .addStructureStart(StructureFeatures.END_CITY)
         .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.END_GATEWAY)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.CHORUS_PLANT);
      return baseEndBiome(â˜ƒ);
   }

   public static Biome smallEndIslandsBiome() {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.END)
         .addFeature(GenerationStep.Decoration.RAW_GENERATION, Features.END_ISLAND_DECORATED);
      return baseEndBiome(â˜ƒ);
   }

   public static Biome mushroomFieldsBiome(float var0, float var1) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.mooshroomSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.MYCELIUM);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addMushroomFieldVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.MUSHROOM)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.9F)
         .downfall(1.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.9F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   private static Biome baseSavannaBiome(float var0, float var1, float var2, boolean var3, boolean var4, MobSpawnSettings.Builder var5) {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(â˜ƒ ? SurfaceBuilders.SHATTERED_SAVANNA : SurfaceBuilders.GRASS);
      if (!â˜ƒ && !â˜ƒ) {
         â˜ƒ.addStructureStart(StructureFeatures.VILLAGE_SAVANNA).addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
      }

      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒ);
      â˜ƒ.addStructureStart(â˜ƒ ? StructureFeatures.RUINED_PORTAL_MOUNTAIN : StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      if (!â˜ƒ) {
         BiomeDefaultFeatures.addSavannaGrass(â˜ƒ);
      }

      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addShatteredSavannaTrees(â˜ƒ);
         BiomeDefaultFeatures.addDefaultFlowers(â˜ƒ);
         BiomeDefaultFeatures.addShatteredSavannaGrass(â˜ƒ);
      } else {
         BiomeDefaultFeatures.addSavannaTrees(â˜ƒ);
         BiomeDefaultFeatures.addWarmFlowers(â˜ƒ);
         BiomeDefaultFeatures.addSavannaExtraGrass(â˜ƒ);
      }

      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.SAVANNA)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(â˜ƒ)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(â˜ƒ))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   public static Biome savannaBiome(float var0, float var1, float var2, boolean var3, boolean var4) {
      MobSpawnSettings.Builder â˜ƒ = savannaMobs();
      return baseSavannaBiome(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static MobSpawnSettings.Builder savannaMobs() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 1, 2, 6))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 1));
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      return â˜ƒ;
   }

   public static Biome savanaPlateauBiome() {
      MobSpawnSettings.Builder â˜ƒ = savannaMobs();
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.LLAMA, 8, 4, 4));
      return baseSavannaBiome(1.5F, 0.025F, 1.0F, true, false, â˜ƒ);
   }

   private static Biome baseBadlandsBiome(ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> var0, float var1, float var2, boolean var3, boolean var4) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOverworldLandMesaStructures(â˜ƒx);
      â˜ƒx.addStructureStart(â˜ƒ ? StructureFeatures.RUINED_PORTAL_MOUNTAIN : StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addExtraGold(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addBadlandsTrees(â˜ƒx);
      }

      BiomeDefaultFeatures.addBadlandGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addBadlandExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.MESA)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(2.0F))
               .foliageColorOverride(10387789)
               .grassColorOverride(9470285)
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome badlandsBiome(float var0, float var1, boolean var2) {
      return baseBadlandsBiome(SurfaceBuilders.BADLANDS, â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   public static Biome woodedBadlandsPlateauBiome(float var0, float var1) {
      return baseBadlandsBiome(SurfaceBuilders.WOODED_BADLANDS, â˜ƒ, â˜ƒ, true, true);
   }

   public static Biome erodedBadlandsBiome() {
      return baseBadlandsBiome(SurfaceBuilders.ERODED_BADLANDS, 0.1F, 0.2F, true, false);
   }

   private static Biome baseOceanBiome(MobSpawnSettings.Builder var0, int var1, int var2, boolean var3, BiomeGenerationSettings.Builder var4) {
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.OCEAN)
         .depth(â˜ƒ ? -1.8F : -1.0F)
         .scale(0.1F)
         .temperature(0.5F)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(â˜ƒ)
               .waterFogColor(â˜ƒ)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.5F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   private static BiomeGenerationSettings.Builder baseOceanGeneration(
      ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> var0, boolean var1, boolean var2, boolean var3
   ) {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(â˜ƒ);
      ConfiguredStructureFeature<?, ?> â˜ƒx = â˜ƒ ? StructureFeatures.OCEAN_RUIN_WARM : StructureFeatures.OCEAN_RUIN_COLD;
      if (â˜ƒ) {
         if (â˜ƒ) {
            â˜ƒ.addStructureStart(StructureFeatures.OCEAN_MONUMENT);
         }

         BiomeDefaultFeatures.addDefaultOverworldOceanStructures(â˜ƒ);
         â˜ƒ.addStructureStart(â˜ƒx);
      } else {
         â˜ƒ.addStructureStart(â˜ƒx);
         if (â˜ƒ) {
            â˜ƒ.addStructureStart(StructureFeatures.OCEAN_MONUMENT);
         }

         BiomeDefaultFeatures.addDefaultOverworldOceanStructures(â˜ƒ);
      }

      â˜ƒ.addStructureStart(StructureFeatures.RUINED_PORTAL_OCEAN);
      BiomeDefaultFeatures.addOceanCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ, true);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      BiomeDefaultFeatures.addWaterTrees(â˜ƒ);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      return â˜ƒ;
   }

   public static Biome coldOceanBiome(boolean var0) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.oceanSpawns(â˜ƒ, 3, 4, 15);
      â˜ƒ.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5));
      boolean â˜ƒx = !â˜ƒ;
      BiomeGenerationSettings.Builder â˜ƒxx = baseOceanGeneration(SurfaceBuilders.GRASS, â˜ƒ, false, â˜ƒx);
      â˜ƒxx.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, â˜ƒ ? Features.SEAGRASS_DEEP_COLD : Features.SEAGRASS_COLD);
      BiomeDefaultFeatures.addDefaultSeagrass(â˜ƒxx);
      BiomeDefaultFeatures.addColdOceanExtraVegetation(â˜ƒxx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒxx);
      return baseOceanBiome(â˜ƒ, 4020182, 329011, â˜ƒ, â˜ƒxx);
   }

   public static Biome oceanBiome(boolean var0) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.oceanSpawns(â˜ƒ, 1, 4, 10);
      â˜ƒ.addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 1, 1, 2));
      BiomeGenerationSettings.Builder â˜ƒx = baseOceanGeneration(SurfaceBuilders.GRASS, â˜ƒ, false, true);
      â˜ƒx.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, â˜ƒ ? Features.SEAGRASS_DEEP : Features.SEAGRASS_NORMAL);
      BiomeDefaultFeatures.addDefaultSeagrass(â˜ƒx);
      BiomeDefaultFeatures.addColdOceanExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return baseOceanBiome(â˜ƒ, 4159204, 329011, â˜ƒ, â˜ƒx);
   }

   public static Biome lukeWarmOceanBiome(boolean var0) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      if (â˜ƒ) {
         BiomeDefaultFeatures.oceanSpawns(â˜ƒ, 8, 4, 8);
      } else {
         BiomeDefaultFeatures.oceanSpawns(â˜ƒ, 10, 2, 15);
      }

      â˜ƒ.addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 5, 1, 3))
         .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.TROPICAL_FISH, 25, 8, 8))
         .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 2, 1, 2));
      BiomeGenerationSettings.Builder â˜ƒ = baseOceanGeneration(SurfaceBuilders.OCEAN_SAND, â˜ƒ, true, false);
      â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, â˜ƒ ? Features.SEAGRASS_DEEP_WARM : Features.SEAGRASS_WARM);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addDefaultSeagrass(â˜ƒ);
      }

      BiomeDefaultFeatures.addLukeWarmKelp(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return baseOceanBiome(â˜ƒ, 4566514, 267827, â˜ƒ, â˜ƒ);
   }

   public static Biome warmOceanBiome() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.PUFFERFISH, 15, 1, 3));
      BiomeDefaultFeatures.warmOceanSpawns(â˜ƒ, 10, 4);
      BiomeGenerationSettings.Builder â˜ƒx = baseOceanGeneration(SurfaceBuilders.FULL_SAND, false, true, false)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.WARM_OCEAN_VEGETATION)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_WARM)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEA_PICKLE);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return baseOceanBiome(â˜ƒ, 4445678, 270131, false, â˜ƒx);
   }

   public static Biome deepWarmOceanBiome() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.warmOceanSpawns(â˜ƒ, 5, 1);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
      BiomeGenerationSettings.Builder â˜ƒx = baseOceanGeneration(SurfaceBuilders.FULL_SAND, true, true, false)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_DEEP_WARM);
      BiomeDefaultFeatures.addDefaultSeagrass(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return baseOceanBiome(â˜ƒ, 4445678, 270131, true, â˜ƒx);
   }

   public static Biome frozenOceanBiome(boolean var0) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 1, 1, 4))
         .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 15, 1, 5))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, 5, 1, 1));
      float â˜ƒx = â˜ƒ ? 0.5F : 0.0F;
      BiomeGenerationSettings.Builder â˜ƒxx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.FROZEN_OCEAN);
      â˜ƒxx.addStructureStart(StructureFeatures.OCEAN_RUIN_COLD);
      if (â˜ƒ) {
         â˜ƒxx.addStructureStart(StructureFeatures.OCEAN_MONUMENT);
      }

      BiomeDefaultFeatures.addDefaultOverworldOceanStructures(â˜ƒxx);
      â˜ƒxx.addStructureStart(StructureFeatures.RUINED_PORTAL_OCEAN);
      BiomeDefaultFeatures.addOceanCarvers(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒxx);
      BiomeDefaultFeatures.addIcebergs(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒxx);
      BiomeDefaultFeatures.addBlueIce(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒxx, true);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒxx);
      BiomeDefaultFeatures.addWaterTrees(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒxx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒxx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒxx);
      return new Biome.BiomeBuilder()
         .precipitation(â˜ƒ ? Biome.Precipitation.RAIN : Biome.Precipitation.SNOW)
         .biomeCategory(Biome.BiomeCategory.OCEAN)
         .depth(â˜ƒ ? -1.8F : -1.0F)
         .scale(0.1F)
         .temperature(â˜ƒx)
         .temperatureAdjustment(Biome.TemperatureModifier.FROZEN)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(3750089)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(â˜ƒx))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒxx.build())
         .build();
   }

   private static Biome baseForestBiome(float var0, float var1, boolean var2, MobSpawnSettings.Builder var3) {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒ);
      â˜ƒ.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_VEGETATION_COMMON);
      } else {
         BiomeDefaultFeatures.addForestFlowers(â˜ƒ);
      }

      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      if (â˜ƒ) {
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FOREST_FLOWER_TREES);
         â˜ƒ.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.FLOWER_FOREST);
         BiomeDefaultFeatures.addDefaultGrass(â˜ƒ);
      } else {
         BiomeDefaultFeatures.addOtherBirchTrees(â˜ƒ);
         BiomeDefaultFeatures.addDefaultFlowers(â˜ƒ);
         BiomeDefaultFeatures.addForestGrass(â˜ƒ);
      }

      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.FOREST)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.7F)
         .downfall(0.8F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.7F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   private static MobSpawnSettings.Builder defaultSpawns() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      return â˜ƒ;
   }

   public static Biome forestBiome(float var0, float var1) {
      MobSpawnSettings.Builder â˜ƒ = defaultSpawns()
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4))
         .setPlayerCanSpawn();
      return baseForestBiome(â˜ƒ, â˜ƒ, false, â˜ƒ);
   }

   public static Biome flowerForestBiome() {
      MobSpawnSettings.Builder â˜ƒ = defaultSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
      return baseForestBiome(0.1F, 0.4F, true, â˜ƒ);
   }

   public static Biome taigaBiome(float var0, float var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 4, 4))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
      if (!â˜ƒ && !â˜ƒ) {
         â˜ƒ.setPlayerCanSpawn();
      }

      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      float â˜ƒ = â˜ƒ ? -0.5F : 0.25F;
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      if (â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.VILLAGE_TAIGA);
         â˜ƒx.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
      }

      if (â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.IGLOO);
      }

      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(â˜ƒ ? StructureFeatures.RUINED_PORTAL_MOUNTAIN : StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addFerns(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addTaigaTrees(â˜ƒx);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addTaigaGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addBerryBushes(â˜ƒx);
      } else {
         BiomeDefaultFeatures.addSparseBerryBushes(â˜ƒx);
      }

      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(â˜ƒ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.TAIGA)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(â˜ƒ)
         .downfall(â˜ƒ ? 0.4F : 0.8F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(â˜ƒ ? 4020182 : 4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(â˜ƒ))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome darkForestBiome(float var0, float var1, boolean var2) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      â˜ƒx.addStructureStart(StructureFeatures.WOODLAND_MANSION);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      â˜ƒx.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, â˜ƒ ? Features.DARK_FOREST_VEGETATION_RED : Features.DARK_FOREST_VEGETATION_BROWN);
      BiomeDefaultFeatures.addForestFlowers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addForestGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.FOREST)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.7F)
         .downfall(0.8F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.7F))
               .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.DARK_FOREST)
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome swampBiome(float var0, float var1, boolean var2) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.farmAnimals(â˜ƒ);
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.SWAMP);
      if (!â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.SWAMP_HUT);
      }

      â˜ƒx.addStructureStart(StructureFeatures.MINESHAFT);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      if (!â˜ƒ) {
         BiomeDefaultFeatures.addFossilDecoration(â˜ƒx);
      }

      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addSwampClayDisk(â˜ƒx);
      BiomeDefaultFeatures.addSwampVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addSwampExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addFossilDecoration(â˜ƒx);
      } else {
         â˜ƒx.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
      }

      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.SWAMP)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.8F)
         .downfall(0.9F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(6388580)
               .waterFogColor(2302743)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.8F))
               .foliageColorOverride(6975545)
               .grassColorModifier(BiomeSpecialEffects.GrassColorModifier.SWAMP)
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome tundraBiome(float var0, float var1, boolean var2, boolean var3) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder().creatureGenerationProbability(0.07F);
      BiomeDefaultFeatures.snowySpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(â˜ƒ ? SurfaceBuilders.ICE_SPIKES : SurfaceBuilders.GRASS);
      if (!â˜ƒ && !â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.VILLAGE_SNOWY).addStructureStart(StructureFeatures.IGLOO);
      }

      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      if (!â˜ƒ && !â˜ƒ) {
         â˜ƒx.addStructureStart(StructureFeatures.PILLAGER_OUTPOST);
      }

      â˜ƒx.addStructureStart(â˜ƒ ? StructureFeatures.RUINED_PORTAL_MOUNTAIN : StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      if (â˜ƒ) {
         â˜ƒx.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.ICE_SPIKE);
         â˜ƒx.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
      }

      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addSnowyTrees(â˜ƒx);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.SNOW)
         .biomeCategory(Biome.BiomeCategory.ICY)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(0.0F)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.0F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome riverBiome(float var0, float var1, float var2, int var3, boolean var4) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4))
         .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      â˜ƒ.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, â˜ƒ ? 1 : 100, 1, 1));
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      â˜ƒx.addStructureStart(StructureFeatures.MINESHAFT);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addWaterTrees(â˜ƒx);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      if (!â˜ƒ) {
         â˜ƒx.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);
      }

      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(â˜ƒ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.RIVER)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(â˜ƒ)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(â˜ƒ)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(â˜ƒ))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome beachBiome(float var0, float var1, float var2, float var3, int var4, boolean var5, boolean var6) {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      if (!â˜ƒ && !â˜ƒ) {
         â˜ƒ.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 5, 2, 5));
      }

      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(â˜ƒ ? SurfaceBuilders.STONE : SurfaceBuilders.DESERT);
      if (â˜ƒ) {
         BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒ);
      } else {
         â˜ƒ.addStructureStart(StructureFeatures.MINESHAFT);
         â˜ƒ.addStructureStart(StructureFeatures.BURIED_TREASURE);
         â˜ƒ.addStructureStart(StructureFeatures.SHIPWRECH_BEACHED);
      }

      â˜ƒ.addStructureStart(â˜ƒ ? StructureFeatures.RUINED_PORTAL_MOUNTAIN : StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒ);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒ);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒ);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒ);
      BiomeDefaultFeatures.addDefaultFlowers(â˜ƒ);
      BiomeDefaultFeatures.addDefaultGrass(â˜ƒ);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒ);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒ);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒ);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒ);
      return new Biome.BiomeBuilder()
         .precipitation(â˜ƒ ? Biome.Precipitation.SNOW : Biome.Precipitation.RAIN)
         .biomeCategory(â˜ƒ ? Biome.BiomeCategory.NONE : Biome.BiomeCategory.BEACH)
         .depth(â˜ƒ)
         .scale(â˜ƒ)
         .temperature(â˜ƒ)
         .downfall(â˜ƒ)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(â˜ƒ)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(â˜ƒ))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒ.build())
         .build();
   }

   public static Biome theVoidBiome() {
      BiomeGenerationSettings.Builder â˜ƒ = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.NOPE);
      â˜ƒ.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Features.VOID_START_PLATFORM);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.NONE)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(0.5F)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.5F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(MobSpawnSettings.EMPTY)
         .generationSettings(â˜ƒ.build())
         .build();
   }

   public static Biome netherWastesBiome() {
      MobSpawnSettings â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 2, 4, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 15, 4, 4))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .build();
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.NETHER)
         .addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER)
         .addStructureStart(StructureFeatures.NETHER_BRIDGE)
         .addStructureStart(StructureFeatures.BASTION_REMNANT)
         .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      â˜ƒx.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.BROWN_MUSHROOM_NETHER)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.RED_MUSHROOM_NETHER)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED);
      BiomeDefaultFeatures.addNetherDefaultOres(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(3344392)
               .skyColor(calculateSkyColor(2.0F))
               .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_NETHER_WASTES_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES))
               .build()
         )
         .mobSpawnSettings(â˜ƒ)
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome soulSandValleyBiome() {
      double â˜ƒ = 0.7;
      double â˜ƒx = 0.15;
      MobSpawnSettings â˜ƒxx = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 20, 5, 5))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .addMobCharge(EntityType.SKELETON, 0.7, 0.15)
         .addMobCharge(EntityType.GHAST, 0.7, 0.15)
         .addMobCharge(EntityType.ENDERMAN, 0.7, 0.15)
         .addMobCharge(EntityType.STRIDER, 0.7, 0.15)
         .build();
      BiomeGenerationSettings.Builder â˜ƒxxx = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.SOUL_SAND_VALLEY)
         .addStructureStart(StructureFeatures.NETHER_BRIDGE)
         .addStructureStart(StructureFeatures.NETHER_FOSSIL)
         .addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER)
         .addStructureStart(StructureFeatures.BASTION_REMNANT)
         .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA)
         .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, Features.BASALT_PILLAR)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_CRIMSON_ROOTS)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_SOUL_SAND);
      BiomeDefaultFeatures.addNetherDefaultOres(â˜ƒxxx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(1787717)
               .skyColor(calculateSkyColor(2.0F))
               .ambientParticle(new AmbientParticleSettings(ParticleTypes.ASH, 0.00625F))
               .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY))
               .build()
         )
         .mobSpawnSettings(â˜ƒxx)
         .generationSettings(â˜ƒxxx.build())
         .build();
   }

   public static Biome basaltDeltasBiome() {
      MobSpawnSettings â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 40, 1, 1))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 100, 2, 5))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .build();
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.BASALT_DELTAS)
         .addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER)
         .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
         .addStructureStart(StructureFeatures.NETHER_BRIDGE)
         .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.DELTA)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA_DOUBLE)
         .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.SMALL_BASALT_COLUMNS)
         .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, Features.LARGE_BASALT_COLUMNS)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.BASALT_BLOBS)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.BLACKSTONE_BLOBS)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_DELTA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.BROWN_MUSHROOM_NETHER)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.RED_MUSHROOM_NETHER)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED_DOUBLE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_GOLD_DELTAS)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_QUARTZ_DELTAS);
      BiomeDefaultFeatures.addAncientDebris(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(4341314)
               .fogColor(6840176)
               .skyColor(calculateSkyColor(2.0F))
               .ambientParticle(new AmbientParticleSettings(ParticleTypes.WHITE_ASH, 0.118093334F))
               .ambientLoopSound(SoundEvents.AMBIENT_BASALT_DELTAS_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_BASALT_DELTAS_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_BASALT_DELTAS_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS))
               .build()
         )
         .mobSpawnSettings(â˜ƒ)
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome crimsonForestBiome() {
      MobSpawnSettings â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 1, 2, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.HOGLIN, 9, 3, 4))
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 5, 3, 4))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .build();
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.CRIMSON_FOREST)
         .addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER)
         .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
         .addStructureStart(StructureFeatures.NETHER_BRIDGE)
         .addStructureStart(StructureFeatures.BASTION_REMNANT)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      â˜ƒx.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.WEEPING_VINES)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.CRIMSON_FUNGI)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.CRIMSON_FOREST_VEGETATION);
      BiomeDefaultFeatures.addNetherDefaultOres(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(3343107)
               .skyColor(calculateSkyColor(2.0F))
               .ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.025F))
               .ambientLoopSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST))
               .build()
         )
         .mobSpawnSettings(â˜ƒ)
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome warpedForestBiome() {
      MobSpawnSettings â˜ƒ = new MobSpawnSettings.Builder()
         .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 1, 4, 4))
         .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2))
         .addMobCharge(EntityType.ENDERMAN, 1.0, 0.12)
         .build();
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder()
         .surfaceBuilder(SurfaceBuilders.WARPED_FOREST)
         .addStructureStart(StructureFeatures.NETHER_BRIDGE)
         .addStructureStart(StructureFeatures.BASTION_REMNANT)
         .addStructureStart(StructureFeatures.RUINED_PORTAL_NETHER)
         .addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      â˜ƒx.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_OPEN)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.PATCH_SOUL_FIRE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE_EXTRA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.GLOWSTONE)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.ORE_MAGMA)
         .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, Features.SPRING_CLOSED)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.WARPED_FUNGI)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.WARPED_FOREST_VEGETATION)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.NETHER_SPROUTS)
         .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Features.TWISTING_VINES);
      BiomeDefaultFeatures.addNetherDefaultOres(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.NONE)
         .biomeCategory(Biome.BiomeCategory.NETHER)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(2.0F)
         .downfall(0.0F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(1705242)
               .skyColor(calculateSkyColor(2.0F))
               .ambientParticle(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F))
               .ambientLoopSound(SoundEvents.AMBIENT_WARPED_FOREST_LOOP)
               .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_WARPED_FOREST_MOOD, 6000, 8, 2.0))
               .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_WARPED_FOREST_ADDITIONS, 0.0111))
               .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_BIOME_WARPED_FOREST))
               .build()
         )
         .mobSpawnSettings(â˜ƒ)
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome lushCaves() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addPlainGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addLushCavesSpecialOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addLushCavesVegetationFeatures(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.UNDERGROUND)
         .depth(0.1F)
         .scale(0.2F)
         .temperature(0.5F)
         .downfall(0.5F)
         .specialEffects(
            new BiomeSpecialEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.5F)).build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }

   public static Biome dripstoneCaves() {
      MobSpawnSettings.Builder â˜ƒ = new MobSpawnSettings.Builder();
      BiomeDefaultFeatures.commonSpawns(â˜ƒ);
      BiomeGenerationSettings.Builder â˜ƒx = new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilders.GRASS);
      BiomeDefaultFeatures.addDefaultOverworldLandStructures(â˜ƒx);
      â˜ƒx.addStructureStart(StructureFeatures.RUINED_PORTAL_STANDARD);
      BiomeDefaultFeatures.addDefaultCarvers(â˜ƒx);
      BiomeDefaultFeatures.addDefaultLakes(â˜ƒx);
      BiomeDefaultFeatures.addDefaultCrystalFormations(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMonsterRoom(â˜ƒx);
      BiomeDefaultFeatures.addPlainGrass(â˜ƒx);
      BiomeDefaultFeatures.addDefaultUndergroundVariety(â˜ƒx);
      BiomeDefaultFeatures.addDefaultOres(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSoftDisks(â˜ƒx);
      BiomeDefaultFeatures.addPlainVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultMushrooms(â˜ƒx);
      BiomeDefaultFeatures.addDefaultExtraVegetation(â˜ƒx);
      BiomeDefaultFeatures.addDefaultSprings(â˜ƒx);
      BiomeDefaultFeatures.addSurfaceFreezing(â˜ƒx);
      BiomeDefaultFeatures.addDripstone(â˜ƒx);
      return new Biome.BiomeBuilder()
         .precipitation(Biome.Precipitation.RAIN)
         .biomeCategory(Biome.BiomeCategory.UNDERGROUND)
         .depth(0.125F)
         .scale(0.05F)
         .temperature(0.8F)
         .downfall(0.4F)
         .specialEffects(
            new BiomeSpecialEffects.Builder()
               .waterColor(4159204)
               .waterFogColor(329011)
               .fogColor(12638463)
               .skyColor(calculateSkyColor(0.8F))
               .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
               .build()
         )
         .mobSpawnSettings(â˜ƒ.build())
         .generationSettings(â˜ƒx.build())
         .build();
   }
}
