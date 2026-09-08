package net.minecraft.world.entity.npc;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ServerLevelData;

public class WanderingTraderSpawner implements CustomSpawner {
   private static final int DEFAULT_TICK_DELAY = 1200;
   public static final int DEFAULT_SPAWN_DELAY = 24000;
   private static final int MIN_SPAWN_CHANCE = 25;
   private static final int MAX_SPAWN_CHANCE = 75;
   private static final int SPAWN_CHANCE_INCREASE = 25;
   private static final int SPAWN_ONE_IN_X_CHANCE = 10;
   private static final int NUMBER_OF_SPAWN_ATTEMPTS = 10;
   private final Random random = new Random();
   private final ServerLevelData serverLevelData;
   private int tickDelay;
   private int spawnDelay;
   private int spawnChance;

   public WanderingTraderSpawner(ServerLevelData var1) {
      this.serverLevelData = â˜ƒ;
      this.tickDelay = 1200;
      this.spawnDelay = â˜ƒ.getWanderingTraderSpawnDelay();
      this.spawnChance = â˜ƒ.getWanderingTraderSpawnChance();
      if (this.spawnDelay == 0 && this.spawnChance == 0) {
         this.spawnDelay = 24000;
         â˜ƒ.setWanderingTraderSpawnDelay(this.spawnDelay);
         this.spawnChance = 25;
         â˜ƒ.setWanderingTraderSpawnChance(this.spawnChance);
      }
   }

   @Override
   public int tick(ServerLevel var1, boolean var2, boolean var3) {
      if (!â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
         return 0;
      } else if (--this.tickDelay > 0) {
         return 0;
      } else {
         this.tickDelay = 1200;
         this.spawnDelay -= 1200;
         this.serverLevelData.setWanderingTraderSpawnDelay(this.spawnDelay);
         if (this.spawnDelay > 0) {
            return 0;
         } else {
            this.spawnDelay = 24000;
            if (!â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
               return 0;
            } else {
               int â˜ƒ = this.spawnChance;
               this.spawnChance = Mth.clamp(this.spawnChance + 25, 25, 75);
               this.serverLevelData.setWanderingTraderSpawnChance(this.spawnChance);
               if (this.random.nextInt(100) > â˜ƒ) {
                  return 0;
               } else if (this.spawn(â˜ƒ)) {
                  this.spawnChance = 25;
                  return 1;
               } else {
                  return 0;
               }
            }
         }
      }
   }

   private boolean spawn(ServerLevel var1) {
      Player â˜ƒ = â˜ƒ.getRandomPlayer();
      if (â˜ƒ == null) {
         return true;
      } else if (this.random.nextInt(10) != 0) {
         return false;
      } else {
         BlockPos â˜ƒ = â˜ƒ.blockPosition();
         int â˜ƒx = 48;
         PoiManager â˜ƒxx = â˜ƒ.getPoiManager();
         Optional<BlockPos> â˜ƒxxx = â˜ƒxx.find(PoiType.MEETING.getPredicate(), var0 -> true, â˜ƒ, 48, PoiManager.Occupancy.ANY);
         BlockPos â˜ƒxxxx = (BlockPos)â˜ƒxxx.orElse(â˜ƒ);
         BlockPos â˜ƒxxxxx = this.findSpawnPositionNear(â˜ƒ, â˜ƒxxxx, 48);
         if (â˜ƒxxxxx != null && this.hasEnoughSpace(â˜ƒ, â˜ƒxxxxx)) {
            if (â˜ƒ.getBiomeName(â˜ƒxxxxx).equals(Optional.of(Biomes.THE_VOID))) {
               return false;
            }

            WanderingTrader â˜ƒxxxxxx = EntityType.WANDERING_TRADER.spawn(â˜ƒ, null, null, null, â˜ƒxxxxx, MobSpawnType.EVENT, false, false);
            if (â˜ƒxxxxxx != null) {
               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 2; ++â˜ƒxxxxxxx) {
                  this.tryToSpawnLlamaFor(â˜ƒ, â˜ƒxxxxxx, 4);
               }

               this.serverLevelData.setWanderingTraderId(â˜ƒxxxxxx.getUUID());
               â˜ƒxxxxxx.setDespawnDelay(48000);
               â˜ƒxxxxxx.setWanderTarget(â˜ƒxxxx);
               â˜ƒxxxxxx.restrictTo(â˜ƒxxxx, 16);
               return true;
            }
         }

         return false;
      }
   }

   private void tryToSpawnLlamaFor(ServerLevel var1, WanderingTrader var2, int var3) {
      BlockPos â˜ƒ = this.findSpawnPositionNear(â˜ƒ, â˜ƒ.blockPosition(), â˜ƒ);
      if (â˜ƒ != null) {
         TraderLlama â˜ƒx = EntityType.TRADER_LLAMA.spawn(â˜ƒ, null, null, null, â˜ƒ, MobSpawnType.EVENT, false, false);
         if (â˜ƒx != null) {
            â˜ƒx.setLeashedTo(â˜ƒ, true);
         }
      }
   }

   @Nullable
   private BlockPos findSpawnPositionNear(LevelReader var1, BlockPos var2, int var3) {
      BlockPos â˜ƒ = null;

      for(int â˜ƒx = 0; â˜ƒx < 10; ++â˜ƒx) {
         int â˜ƒxx = â˜ƒ.getX() + this.random.nextInt(â˜ƒ * 2) - â˜ƒ;
         int â˜ƒxxx = â˜ƒ.getZ() + this.random.nextInt(â˜ƒ * 2) - â˜ƒ;
         int â˜ƒxxxx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒxx, â˜ƒxxx);
         BlockPos â˜ƒxxxxx = new BlockPos(â˜ƒxx, â˜ƒxxxx, â˜ƒxxx);
         if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, â˜ƒ, â˜ƒxxxxx, EntityType.WANDERING_TRADER)) {
            â˜ƒ = â˜ƒxxxxx;
            break;
         }
      }

      return â˜ƒ;
   }

   private boolean hasEnoughSpace(BlockGetter var1, BlockPos var2) {
      for(BlockPos â˜ƒ : BlockPos.betweenClosed(â˜ƒ, â˜ƒ.offset(1, 2, 1))) {
         if (!â˜ƒ.getBlockState(â˜ƒ).getCollisionShape(â˜ƒ, â˜ƒ).isEmpty()) {
            return false;
         }
      }

      return true;
   }
}
