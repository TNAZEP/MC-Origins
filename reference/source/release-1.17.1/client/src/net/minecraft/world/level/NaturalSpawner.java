package net.minecraft.world.level;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.NearestNeighborBiomeZoomer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NaturalSpawner {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MIN_SPAWN_DISTANCE = 24;
   public static final int SPAWN_DISTANCE_CHUNK = 8;
   public static final int SPAWN_DISTANCE_BLOCK = 128;
   static final int MAGIC_NUMBER = (int)Math.pow(17.0, 2.0);
   private static final MobCategory[] SPAWNING_CATEGORIES = (MobCategory[])Stream.of(MobCategory.values())
      .filter(var0 -> var0 != MobCategory.MISC)
      .toArray(var0 -> new MobCategory[var0]);

   private NaturalSpawner() {
   }

   public static NaturalSpawner.SpawnState createState(int var0, Iterable<Entity> var1, NaturalSpawner.ChunkGetter var2) {
      PotentialCalculator â˜ƒ = new PotentialCalculator();
      Object2IntOpenHashMap<MobCategory> â˜ƒx = new Object2IntOpenHashMap<>();

      for(Entity â˜ƒxx : â˜ƒ) {
         if (â˜ƒxx instanceof Mob â˜ƒxxx && (â˜ƒxxx.isPersistenceRequired() || â˜ƒxxx.requiresCustomPersistence())) {
            continue;
         }

         MobCategory â˜ƒxxx = â˜ƒxx.getType().getCategory();
         if (â˜ƒxxx != MobCategory.MISC) {
            BlockPos â˜ƒxxxx = â˜ƒxx.blockPosition();
            long â˜ƒxxxxx = ChunkPos.asLong(SectionPos.blockToSectionCoord(â˜ƒxxxx.getX()), SectionPos.blockToSectionCoord(â˜ƒxxxx.getZ()));
            â˜ƒ.query(â˜ƒxxxxx, var5 -> {
               MobSpawnSettings.MobSpawnCost â˜ƒ = getRoughBiome(â˜ƒ, var5).getMobSettings().getMobSpawnCost(â˜ƒ.getType());
               if (â˜ƒ != null) {
                  â˜ƒ.addCharge(â˜ƒ.blockPosition(), â˜ƒ.getCharge());
               }

               â˜ƒ.addTo(â˜ƒ, 1);
            });
         }
      }

      return new NaturalSpawner.SpawnState(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   static Biome getRoughBiome(BlockPos var0, ChunkAccess var1) {
      return NearestNeighborBiomeZoomer.INSTANCE.getBiome(0L, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), â˜ƒ.getBiomes());
   }

   public static void spawnForChunk(ServerLevel var0, LevelChunk var1, NaturalSpawner.SpawnState var2, boolean var3, boolean var4, boolean var5) {
      â˜ƒ.getProfiler().push("spawner");

      for(MobCategory â˜ƒ : SPAWNING_CATEGORIES) {
         if ((â˜ƒ || !â˜ƒ.isFriendly()) && (â˜ƒ || â˜ƒ.isFriendly()) && (â˜ƒ || !â˜ƒ.isPersistent()) && â˜ƒ.canSpawnForCategory(â˜ƒ)) {
            spawnCategoryForChunk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ::canSpawn, â˜ƒ::afterSpawn);
         }
      }

      â˜ƒ.getProfiler().pop();
   }

   public static void spawnCategoryForChunk(
      MobCategory var0, ServerLevel var1, LevelChunk var2, NaturalSpawner.SpawnPredicate var3, NaturalSpawner.AfterSpawnCallback var4
   ) {
      BlockPos â˜ƒ = getRandomPosWithin(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getY() >= â˜ƒ.getMinBuildHeight() + 1) {
         spawnCategoryForPosition(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @VisibleForDebug
   public static void spawnCategoryForPosition(MobCategory var0, ServerLevel var1, BlockPos var2) {
      spawnCategoryForPosition(â˜ƒ, â˜ƒ, â˜ƒ.getChunk(â˜ƒ), â˜ƒ, (var0x, var1x, var2x) -> true, (var0x, var1x) -> {
      });
   }

   public static void spawnCategoryForPosition(
      MobCategory var0, ServerLevel var1, ChunkAccess var2, BlockPos var3, NaturalSpawner.SpawnPredicate var4, NaturalSpawner.AfterSpawnCallback var5
   ) {
      StructureFeatureManager â˜ƒ = â˜ƒ.structureFeatureManager();
      ChunkGenerator â˜ƒx = â˜ƒ.getChunkSource().getGenerator();
      int â˜ƒxx = â˜ƒ.getY();
      BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ);
      if (!â˜ƒxxx.isRedstoneConductor(â˜ƒ, â˜ƒ)) {
         BlockPos.MutableBlockPos â˜ƒxxxx = new BlockPos.MutableBlockPos();
         int â˜ƒxxxxx = 0;

         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 3; ++â˜ƒxxxxxx) {
            int â˜ƒxxxxxxx = â˜ƒ.getX();
            int â˜ƒxxxxxxxx = â˜ƒ.getZ();
            int â˜ƒxxxxxxxxx = 6;
            MobSpawnSettings.SpawnerData â˜ƒxxxxxxxxxx = null;
            SpawnGroupData â˜ƒxxxxxxxxxxx = null;
            int â˜ƒxxxxxxxxxxxx = Mth.ceil(â˜ƒ.random.nextFloat() * 4.0F);
            int â˜ƒxxxxxxxxxxxxx = 0;

            for(int â˜ƒxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxx < â˜ƒxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxx) {
               â˜ƒxxxxxxx += â˜ƒ.random.nextInt(6) - â˜ƒ.random.nextInt(6);
               â˜ƒxxxxxxxx += â˜ƒ.random.nextInt(6) - â˜ƒ.random.nextInt(6);
               â˜ƒxxxx.set(â˜ƒxxxxxxx, â˜ƒxx, â˜ƒxxxxxxxx);
               double â˜ƒxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxx + 0.5;
               double â˜ƒxxxxxxxxxxxxxxxx = (double)â˜ƒxxxxxxxx + 0.5;
               Player â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒ.getNearestPlayer(â˜ƒxxxxxxxxxxxxxxx, (double)â˜ƒxx, â˜ƒxxxxxxxxxxxxxxxx, -1.0, false);
               if (â˜ƒxxxxxxxxxxxxxxxxx != null) {
                  double â˜ƒxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx.distanceToSqr(â˜ƒxxxxxxxxxxxxxxx, (double)â˜ƒxx, â˜ƒxxxxxxxxxxxxxxxx);
                  if (isRightDistanceToPlayerAndSpawnPoint(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxxxxxxxxxxxxxx)) {
                     if (â˜ƒxxxxxxxxxx == null) {
                        Optional<MobSpawnSettings.SpawnerData> â˜ƒxxxxxxxxxxxxxxxxxxx = getRandomSpawnMobAt(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ.random, â˜ƒxxxx);
                        if (!â˜ƒxxxxxxxxxxxxxxxxxxx.isPresent()) {
                           break;
                        }

                        â˜ƒxxxxxxxxxx = (MobSpawnSettings.SpawnerData)â˜ƒxxxxxxxxxxxxxxxxxxx.get();
                        â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.minCount + â˜ƒ.random.nextInt(1 + â˜ƒxxxxxxxxxx.maxCount - â˜ƒxxxxxxxxxx.minCount);
                     }

                     if (isValidSpawnPostitionForType(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxxxxxxxxxx, â˜ƒxxxx, â˜ƒxxxxxxxxxxxxxxxxxx)
                        && â˜ƒ.test(â˜ƒxxxxxxxxxx.type, â˜ƒxxxx, â˜ƒ)) {
                        Mob â˜ƒxxxxxxxxxxxxxxxxxxx = getMobForSpawn(â˜ƒ, â˜ƒxxxxxxxxxx.type);
                        if (â˜ƒxxxxxxxxxxxxxxxxxxx == null) {
                           return;
                        }

                        â˜ƒxxxxxxxxxxxxxxxxxxx.moveTo(â˜ƒxxxxxxxxxxxxxxx, (double)â˜ƒxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒ.random.nextFloat() * 360.0F, 0.0F);
                        if (isValidPositionForMob(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx)) {
                           â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxx.finalizeSpawn(
                              â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒxxxxxxxxxxxxxxxxxxx.blockPosition()), MobSpawnType.NATURAL, â˜ƒxxxxxxxxxxx, null
                           );
                           ++â˜ƒxxxxx;
                           ++â˜ƒxxxxxxxxxxxxx;
                           â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxxxxxxxxxxxxxxxxxx);
                           â˜ƒ.run(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒ);
                           if (â˜ƒxxxxx >= â˜ƒxxxxxxxxxxxxxxxxxxx.getMaxSpawnClusterSize()) {
                              return;
                           }

                           if (â˜ƒxxxxxxxxxxxxxxxxxxx.isMaxGroupSizeReached(â˜ƒxxxxxxxxxxxxx)) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isRightDistanceToPlayerAndSpawnPoint(ServerLevel var0, ChunkAccess var1, BlockPos.MutableBlockPos var2, double var3) {
      if (â˜ƒ <= 576.0) {
         return false;
      } else if (â˜ƒ.getSharedSpawnPos().closerThan(new Vec3((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5), 24.0)) {
         return false;
      } else {
         return Objects.equals(new ChunkPos(â˜ƒ), â˜ƒ.getPos()) || â˜ƒ.isPositionEntityTicking(â˜ƒ);
      }
   }

   private static boolean isValidSpawnPostitionForType(
      ServerLevel var0,
      MobCategory var1,
      StructureFeatureManager var2,
      ChunkGenerator var3,
      MobSpawnSettings.SpawnerData var4,
      BlockPos.MutableBlockPos var5,
      double var6
   ) {
      EntityType<?> â˜ƒ = â˜ƒ.type;
      if (â˜ƒ.getCategory() == MobCategory.MISC) {
         return false;
      } else if (!â˜ƒ.canSpawnFarFromPlayer() && â˜ƒ > (double)(â˜ƒ.getCategory().getDespawnDistance() * â˜ƒ.getCategory().getDespawnDistance())) {
         return false;
      } else if (â˜ƒ.canSummon() && canSpawnMobAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         SpawnPlacements.Type â˜ƒ = SpawnPlacements.getPlacementType(â˜ƒ);
         if (!isSpawnPositionOk(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return false;
         } else if (!SpawnPlacements.checkSpawnRules(â˜ƒ, â˜ƒ, MobSpawnType.NATURAL, â˜ƒ, â˜ƒ.random)) {
            return false;
         } else {
            return â˜ƒ.noCollision(â˜ƒ.getAABB((double)â˜ƒ.getX() + 0.5, (double)â˜ƒ.getY(), (double)â˜ƒ.getZ() + 0.5));
         }
      } else {
         return false;
      }
   }

   @Nullable
   private static Mob getMobForSpawn(ServerLevel var0, EntityType<?> var1) {
      try {
         Entity â˜ƒ = â˜ƒ.create(â˜ƒ);
         if (!(â˜ƒ instanceof Mob)) {
            throw new IllegalStateException("Trying to spawn a non-mob: " + Registry.ENTITY_TYPE.getKey(â˜ƒ));
         } else {
            return (Mob)â˜ƒ;
         }
      } catch (Exception var4) {
         LOGGER.warn("Failed to create mob", var4);
         return null;
      }
   }

   private static boolean isValidPositionForMob(ServerLevel var0, Mob var1, double var2) {
      if (â˜ƒ > (double)(â˜ƒ.getType().getCategory().getDespawnDistance() * â˜ƒ.getType().getCategory().getDespawnDistance()) && â˜ƒ.removeWhenFarAway(â˜ƒ)) {
         return false;
      } else {
         return â˜ƒ.checkSpawnRules(â˜ƒ, MobSpawnType.NATURAL) && â˜ƒ.checkSpawnObstruction(â˜ƒ);
      }
   }

   private static Optional<MobSpawnSettings.SpawnerData> getRandomSpawnMobAt(
      ServerLevel var0, StructureFeatureManager var1, ChunkGenerator var2, MobCategory var3, Random var4, BlockPos var5
   ) {
      Biome â˜ƒ = â˜ƒ.getBiome(â˜ƒ);
      return â˜ƒ == MobCategory.WATER_AMBIENT && â˜ƒ.getBiomeCategory() == Biome.BiomeCategory.RIVER && â˜ƒ.nextFloat() < 0.98F
         ? Optional.empty()
         : mobsAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).getRandom(â˜ƒ);
   }

   private static boolean canSpawnMobAt(
      ServerLevel var0, StructureFeatureManager var1, ChunkGenerator var2, MobCategory var3, MobSpawnSettings.SpawnerData var4, BlockPos var5
   ) {
      return mobsAt(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, null).unwrap().contains(â˜ƒ);
   }

   private static WeightedRandomList<MobSpawnSettings.SpawnerData> mobsAt(
      ServerLevel var0, StructureFeatureManager var1, ChunkGenerator var2, MobCategory var3, BlockPos var4, @Nullable Biome var5
   ) {
      return â˜ƒ == MobCategory.MONSTER
            && â˜ƒ.getBlockState(â˜ƒ.below()).is(Blocks.NETHER_BRICKS)
            && â˜ƒ.getStructureAt(â˜ƒ, false, StructureFeature.NETHER_BRIDGE).isValid()
         ? StructureFeature.NETHER_BRIDGE.getSpecialEnemies()
         : â˜ƒ.getMobsAt(â˜ƒ != null ? â˜ƒ : â˜ƒ.getBiome(â˜ƒ), â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static BlockPos getRandomPosWithin(Level var0, LevelChunk var1) {
      ChunkPos â˜ƒ = â˜ƒ.getPos();
      int â˜ƒx = â˜ƒ.getMinBlockX() + â˜ƒ.random.nextInt(16);
      int â˜ƒxx = â˜ƒ.getMinBlockZ() + â˜ƒ.random.nextInt(16);
      int â˜ƒxxx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒx, â˜ƒxx) + 1;
      int â˜ƒxxxx = Mth.randomBetweenInclusive(â˜ƒ.random, â˜ƒ.getMinBuildHeight(), â˜ƒxxx);
      return new BlockPos(â˜ƒx, â˜ƒxxxx, â˜ƒxx);
   }

   public static boolean isValidEmptySpawnBlock(BlockGetter var0, BlockPos var1, BlockState var2, FluidState var3, EntityType<?> var4) {
      if (â˜ƒ.isCollisionShapeFullBlock(â˜ƒ, â˜ƒ)) {
         return false;
      } else if (â˜ƒ.isSignalSource()) {
         return false;
      } else if (!â˜ƒ.isEmpty()) {
         return false;
      } else if (â˜ƒ.is(BlockTags.PREVENT_MOB_SPAWNING_INSIDE)) {
         return false;
      } else {
         return !â˜ƒ.isBlockDangerous(â˜ƒ);
      }
   }

   public static boolean isSpawnPositionOk(SpawnPlacements.Type var0, LevelReader var1, BlockPos var2, @Nullable EntityType<?> var3) {
      if (â˜ƒ == SpawnPlacements.Type.NO_RESTRICTIONS) {
         return true;
      } else if (â˜ƒ != null && â˜ƒ.getWorldBorder().isWithinBounds(â˜ƒ)) {
         BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
         FluidState â˜ƒx = â˜ƒ.getFluidState(â˜ƒ);
         BlockPos â˜ƒxx = â˜ƒ.above();
         BlockPos â˜ƒxxx = â˜ƒ.below();
         switch(â˜ƒ) {
            case IN_WATER:
               return â˜ƒx.is(FluidTags.WATER) && â˜ƒ.getFluidState(â˜ƒxxx).is(FluidTags.WATER) && !â˜ƒ.getBlockState(â˜ƒxx).isRedstoneConductor(â˜ƒ, â˜ƒxx);
            case IN_LAVA:
               return â˜ƒx.is(FluidTags.LAVA);
            case ON_GROUND:
            default:
               BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
               if (!â˜ƒxxxx.isValidSpawn(â˜ƒ, â˜ƒxxx, â˜ƒ)) {
                  return false;
               } else {
                  return isValidEmptySpawnBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ)
                     && isValidEmptySpawnBlock(â˜ƒ, â˜ƒxx, â˜ƒ.getBlockState(â˜ƒxx), â˜ƒ.getFluidState(â˜ƒxx), â˜ƒ);
               }
         }
      } else {
         return false;
      }
   }

   public static void spawnMobsForChunkGeneration(ServerLevelAccessor var0, Biome var1, ChunkPos var2, Random var3) {
      MobSpawnSettings â˜ƒ = â˜ƒ.getMobSettings();
      WeightedRandomList<MobSpawnSettings.SpawnerData> â˜ƒx = â˜ƒ.getMobs(MobCategory.CREATURE);
      if (!â˜ƒx.isEmpty()) {
         int â˜ƒxx = â˜ƒ.getMinBlockX();
         int â˜ƒxxx = â˜ƒ.getMinBlockZ();

         while(â˜ƒ.nextFloat() < â˜ƒ.getCreatureProbability()) {
            Optional<MobSpawnSettings.SpawnerData> â˜ƒxxxx = â˜ƒx.getRandom(â˜ƒ);
            if (â˜ƒxxxx.isPresent()) {
               MobSpawnSettings.SpawnerData â˜ƒxxxxx = (MobSpawnSettings.SpawnerData)â˜ƒxxxx.get();
               int â˜ƒxxxxxx = â˜ƒxxxxx.minCount + â˜ƒ.nextInt(1 + â˜ƒxxxxx.maxCount - â˜ƒxxxxx.minCount);
               SpawnGroupData â˜ƒxxxxxxx = null;
               int â˜ƒxxxxxxxx = â˜ƒxx + â˜ƒ.nextInt(16);
               int â˜ƒxxxxxxxxx = â˜ƒxxx + â˜ƒ.nextInt(16);
               int â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx;
               int â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxx;

               for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒxxxxxx; ++â˜ƒxxxxxxxxxxxx) {
                  boolean â˜ƒxxxxxxxxxxxxx = false;

                  for(int â˜ƒxxxxxxxxxxxxxx = 0; !â˜ƒxxxxxxxxxxxxx && â˜ƒxxxxxxxxxxxxxx < 4; ++â˜ƒxxxxxxxxxxxxxx) {
                     BlockPos â˜ƒxxxxxxxxxxxxxxx = getTopNonCollidingPos(â˜ƒ, â˜ƒxxxxx.type, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx);
                     if (â˜ƒxxxxx.type.canSummon()
                        && isSpawnPositionOk(SpawnPlacements.getPlacementType(â˜ƒxxxxx.type), â˜ƒ, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxx.type)) {
                        float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxx.type.getWidth();
                        double â˜ƒxxxxxxxxxxxxxxxxx = Mth.clamp(
                           (double)â˜ƒxxxxxxxx, (double)â˜ƒxx + (double)â˜ƒxxxxxxxxxxxxxxxx, (double)â˜ƒxx + 16.0 - (double)â˜ƒxxxxxxxxxxxxxxxx
                        );
                        double â˜ƒxxxxxxxxxxxxxxxxxx = Mth.clamp(
                           (double)â˜ƒxxxxxxxxx, (double)â˜ƒxxx + (double)â˜ƒxxxxxxxxxxxxxxxx, (double)â˜ƒxxx + 16.0 - (double)â˜ƒxxxxxxxxxxxxxxxx
                        );
                        if (!â˜ƒ.noCollision(â˜ƒxxxxx.type.getAABB(â˜ƒxxxxxxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxxxx.getY(), â˜ƒxxxxxxxxxxxxxxxxxx))
                           || !SpawnPlacements.checkSpawnRules(
                              â˜ƒxxxxx.type,
                              â˜ƒ,
                              MobSpawnType.CHUNK_GENERATION,
                              new BlockPos(â˜ƒxxxxxxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxxxx.getY(), â˜ƒxxxxxxxxxxxxxxxxxx),
                              â˜ƒ.getRandom()
                           )) {
                           continue;
                        }

                        Entity â˜ƒ;
                        try {
                           â˜ƒ = â˜ƒxxxxx.type.create(â˜ƒ.getLevel());
                        } catch (Exception var27) {
                           LOGGER.warn("Failed to create mob", var27);
                           continue;
                        }

                        â˜ƒ.moveTo(â˜ƒxxxxxxxxxxxxxxxxx, (double)â˜ƒxxxxxxxxxxxxxxx.getY(), â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒ.nextFloat() * 360.0F, 0.0F);
                        if (â˜ƒ instanceof Mob â˜ƒxxxxxxxxxxxxxxxx
                           && â˜ƒxxxxxxxxxxxxxxxx.checkSpawnRules(â˜ƒ, MobSpawnType.CHUNK_GENERATION)
                           && â˜ƒxxxxxxxxxxxxxxxx.checkSpawnObstruction(â˜ƒ)) {
                           â˜ƒxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx.finalizeSpawn(
                              â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒxxxxxxxxxxxxxxxx.blockPosition()), MobSpawnType.CHUNK_GENERATION, â˜ƒxxxxxxx, null
                           );
                           â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxxxxxxxxxxxxxxx);
                           â˜ƒxxxxxxxxxxxxx = true;
                        }
                     }

                     â˜ƒxxxxxxxx += â˜ƒ.nextInt(5) - â˜ƒ.nextInt(5);

                     for(â˜ƒxxxxxxxxx += â˜ƒ.nextInt(5) - â˜ƒ.nextInt(5);
                        â˜ƒxxxxxxxx < â˜ƒxx || â˜ƒxxxxxxxx >= â˜ƒxx + 16 || â˜ƒxxxxxxxxx < â˜ƒxxx || â˜ƒxxxxxxxxx >= â˜ƒxxx + 16;
                        â˜ƒxxxxxxxxx = â˜ƒxxxxxxxxxxx + â˜ƒ.nextInt(5) - â˜ƒ.nextInt(5)
                     ) {
                        â˜ƒxxxxxxxx = â˜ƒxxxxxxxxxx + â˜ƒ.nextInt(5) - â˜ƒ.nextInt(5);
                     }
                  }
               }
            }
         }
      }
   }

   private static BlockPos getTopNonCollidingPos(LevelReader var0, EntityType<?> var1, int var2, int var3) {
      int â˜ƒ = â˜ƒ.getHeight(SpawnPlacements.getHeightmapType(â˜ƒ), â˜ƒ, â˜ƒ);
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.dimensionType().hasCeiling()) {
         do {
            â˜ƒx.move(Direction.DOWN);
         } while(!â˜ƒ.getBlockState(â˜ƒx).isAir());

         do {
            â˜ƒx.move(Direction.DOWN);
         } while(â˜ƒ.getBlockState(â˜ƒx).isAir() && â˜ƒx.getY() > â˜ƒ.getMinBuildHeight());
      }

      if (SpawnPlacements.getPlacementType(â˜ƒ) == SpawnPlacements.Type.ON_GROUND) {
         BlockPos â˜ƒ = â˜ƒx.below();
         if (â˜ƒ.getBlockState(â˜ƒ).isPathfindable(â˜ƒ, â˜ƒ, PathComputationType.LAND)) {
            return â˜ƒ;
         }
      }

      return â˜ƒx.immutable();
   }

   @FunctionalInterface
   public interface AfterSpawnCallback {
      void run(Mob var1, ChunkAccess var2);
   }

   @FunctionalInterface
   public interface ChunkGetter {
      void query(long var1, Consumer<LevelChunk> var3);
   }

   @FunctionalInterface
   public interface SpawnPredicate {
      boolean test(EntityType<?> var1, BlockPos var2, ChunkAccess var3);
   }

   public static class SpawnState {
      private final int spawnableChunkCount;
      private final Object2IntOpenHashMap<MobCategory> mobCategoryCounts;
      private final PotentialCalculator spawnPotential;
      private final Object2IntMap<MobCategory> unmodifiableMobCategoryCounts;
      @Nullable
      private BlockPos lastCheckedPos;
      @Nullable
      private EntityType<?> lastCheckedType;
      private double lastCharge;

      SpawnState(int var1, Object2IntOpenHashMap<MobCategory> var2, PotentialCalculator var3) {
         this.spawnableChunkCount = â˜ƒ;
         this.mobCategoryCounts = â˜ƒ;
         this.spawnPotential = â˜ƒ;
         this.unmodifiableMobCategoryCounts = Object2IntMaps.unmodifiable(â˜ƒ);
      }

      private boolean canSpawn(EntityType<?> var1, BlockPos var2, ChunkAccess var3) {
         this.lastCheckedPos = â˜ƒ;
         this.lastCheckedType = â˜ƒ;
         MobSpawnSettings.MobSpawnCost â˜ƒ = NaturalSpawner.getRoughBiome(â˜ƒ, â˜ƒ).getMobSettings().getMobSpawnCost(â˜ƒ);
         if (â˜ƒ == null) {
            this.lastCharge = 0.0;
            return true;
         } else {
            double â˜ƒ = â˜ƒ.getCharge();
            this.lastCharge = â˜ƒ;
            double â˜ƒx = this.spawnPotential.getPotentialEnergyChange(â˜ƒ, â˜ƒ);
            return â˜ƒx <= â˜ƒ.getEnergyBudget();
         }
      }

      private void afterSpawn(Mob var1, ChunkAccess var2) {
         EntityType<?> â˜ƒx = â˜ƒ.getType();
         BlockPos â˜ƒxx = â˜ƒ.blockPosition();
         double â˜ƒ;
         if (â˜ƒxx.equals(this.lastCheckedPos) && â˜ƒx == this.lastCheckedType) {
            â˜ƒ = this.lastCharge;
         } else {
            MobSpawnSettings.MobSpawnCost â˜ƒ = NaturalSpawner.getRoughBiome(â˜ƒxx, â˜ƒ).getMobSettings().getMobSpawnCost(â˜ƒx);
            if (â˜ƒ != null) {
               â˜ƒ = â˜ƒ.getCharge();
            } else {
               â˜ƒ = 0.0;
            }
         }

         this.spawnPotential.addCharge(â˜ƒxx, â˜ƒ);
         this.mobCategoryCounts.addTo(â˜ƒx.getCategory(), 1);
      }

      public int getSpawnableChunkCount() {
         return this.spawnableChunkCount;
      }

      public Object2IntMap<MobCategory> getMobCategoryCounts() {
         return this.unmodifiableMobCategoryCounts;
      }

      boolean canSpawnForCategory(MobCategory var1) {
         int â˜ƒ = â˜ƒ.getMaxInstancesPerChunk() * this.spawnableChunkCount / NaturalSpawner.MAGIC_NUMBER;
         return this.mobCategoryCounts.getInt(â˜ƒ) < â˜ƒ;
      }
   }
}
