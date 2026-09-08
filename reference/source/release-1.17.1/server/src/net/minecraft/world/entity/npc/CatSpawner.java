package net.minecraft.world.entity.npc;

import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.AABB;

public class CatSpawner implements CustomSpawner {
   private static final int TICK_DELAY = 1200;
   private int nextTick;

   @Override
   public int tick(ServerLevel var1, boolean var2, boolean var3) {
      if (â˜ƒ && â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
         --this.nextTick;
         if (this.nextTick > 0) {
            return 0;
         } else {
            this.nextTick = 1200;
            Player â˜ƒ = â˜ƒ.getRandomPlayer();
            if (â˜ƒ == null) {
               return 0;
            } else {
               Random â˜ƒ = â˜ƒ.random;
               int â˜ƒx = (8 + â˜ƒ.nextInt(24)) * (â˜ƒ.nextBoolean() ? -1 : 1);
               int â˜ƒxx = (8 + â˜ƒ.nextInt(24)) * (â˜ƒ.nextBoolean() ? -1 : 1);
               BlockPos â˜ƒxxx = â˜ƒ.blockPosition().offset(â˜ƒx, 0, â˜ƒxx);
               int â˜ƒxxxx = 10;
               if (!â˜ƒ.hasChunksAt(â˜ƒxxx.getX() - 10, â˜ƒxxx.getZ() - 10, â˜ƒxxx.getX() + 10, â˜ƒxxx.getZ() + 10)) {
                  return 0;
               } else {
                  if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, â˜ƒ, â˜ƒxxx, EntityType.CAT)) {
                     if (â˜ƒ.isCloseToVillage(â˜ƒxxx, 2)) {
                        return this.spawnInVillage(â˜ƒ, â˜ƒxxx);
                     }

                     if (â˜ƒ.structureFeatureManager().getStructureAt(â˜ƒxxx, true, StructureFeature.SWAMP_HUT).isValid()) {
                        return this.spawnInHut(â˜ƒ, â˜ƒxxx);
                     }
                  }

                  return 0;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private int spawnInVillage(ServerLevel var1, BlockPos var2) {
      int â˜ƒ = 48;
      if (â˜ƒ.getPoiManager().getCountInRange(PoiType.HOME.getPredicate(), â˜ƒ, 48, PoiManager.Occupancy.IS_OCCUPIED) > 4L) {
         List<Cat> â˜ƒx = â˜ƒ.getEntitiesOfClass(Cat.class, new AABB(â˜ƒ).inflate(48.0, 8.0, 48.0));
         if (â˜ƒx.size() < 5) {
            return this.spawnCat(â˜ƒ, â˜ƒ);
         }
      }

      return 0;
   }

   private int spawnInHut(ServerLevel var1, BlockPos var2) {
      int â˜ƒ = 16;
      List<Cat> â˜ƒx = â˜ƒ.getEntitiesOfClass(Cat.class, new AABB(â˜ƒ).inflate(16.0, 8.0, 16.0));
      return â˜ƒx.size() < 1 ? this.spawnCat(â˜ƒ, â˜ƒ) : 0;
   }

   private int spawnCat(BlockPos var1, ServerLevel var2) {
      Cat â˜ƒ = EntityType.CAT.create(â˜ƒ);
      if (â˜ƒ == null) {
         return 0;
      } else {
         â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ), MobSpawnType.NATURAL, null, null);
         â˜ƒ.moveTo(â˜ƒ, 0.0F, 0.0F);
         â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
         return 1;
      }
   }
}
