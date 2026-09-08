package net.minecraft.world.level.levelgen;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

public class PatrolSpawner implements CustomSpawner {
   private int nextTick;

   @Override
   public int tick(ServerLevel var1, boolean var2, boolean var3) {
      if (!â˜ƒ) {
         return 0;
      } else if (!â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DO_PATROL_SPAWNING)) {
         return 0;
      } else {
         Random â˜ƒ = â˜ƒ.random;
         --this.nextTick;
         if (this.nextTick > 0) {
            return 0;
         } else {
            this.nextTick += 12000 + â˜ƒ.nextInt(1200);
            long â˜ƒ = â˜ƒ.getDayTime() / 24000L;
            if (â˜ƒ < 5L || !â˜ƒ.isDay()) {
               return 0;
            } else if (â˜ƒ.nextInt(5) != 0) {
               return 0;
            } else {
               int â˜ƒ = â˜ƒ.players().size();
               if (â˜ƒ < 1) {
                  return 0;
               } else {
                  Player â˜ƒ = (Player)â˜ƒ.players().get(â˜ƒ.nextInt(â˜ƒ));
                  if (â˜ƒ.isSpectator()) {
                     return 0;
                  } else if (â˜ƒ.isCloseToVillage(â˜ƒ.blockPosition(), 2)) {
                     return 0;
                  } else {
                     int â˜ƒ = (24 + â˜ƒ.nextInt(24)) * (â˜ƒ.nextBoolean() ? -1 : 1);
                     int â˜ƒx = (24 + â˜ƒ.nextInt(24)) * (â˜ƒ.nextBoolean() ? -1 : 1);
                     BlockPos.MutableBlockPos â˜ƒxx = â˜ƒ.blockPosition().mutable().move(â˜ƒ, 0, â˜ƒx);
                     int â˜ƒxxx = 10;
                     if (!â˜ƒ.hasChunksAt(â˜ƒxx.getX() - 10, â˜ƒxx.getZ() - 10, â˜ƒxx.getX() + 10, â˜ƒxx.getZ() + 10)) {
                        return 0;
                     } else {
                        Biome â˜ƒ = â˜ƒ.getBiome(â˜ƒxx);
                        Biome.BiomeCategory â˜ƒx = â˜ƒ.getBiomeCategory();
                        if (â˜ƒx == Biome.BiomeCategory.MUSHROOM) {
                           return 0;
                        } else {
                           int â˜ƒ = 0;
                           int â˜ƒx = (int)Math.ceil((double)â˜ƒ.getCurrentDifficultyAt(â˜ƒxx).getEffectiveDifficulty()) + 1;

                           for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
                              ++â˜ƒ;
                              â˜ƒxx.setY(â˜ƒ.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, â˜ƒxx).getY());
                              if (â˜ƒxx == 0) {
                                 if (!this.spawnPatrolMember(â˜ƒ, â˜ƒxx, â˜ƒ, true)) {
                                    break;
                                 }
                              } else {
                                 this.spawnPatrolMember(â˜ƒ, â˜ƒxx, â˜ƒ, false);
                              }

                              â˜ƒxx.setX(â˜ƒxx.getX() + â˜ƒ.nextInt(5) - â˜ƒ.nextInt(5));
                              â˜ƒxx.setZ(â˜ƒxx.getZ() + â˜ƒ.nextInt(5) - â˜ƒ.nextInt(5));
                           }

                           return â˜ƒ;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean spawnPatrolMember(ServerLevel var1, BlockPos var2, Random var3, boolean var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!NaturalSpawner.isValidEmptySpawnBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getFluidState(), EntityType.PILLAGER)) {
         return false;
      } else if (!PatrollingMonster.checkPatrollingMonsterSpawnRules(EntityType.PILLAGER, â˜ƒ, MobSpawnType.PATROL, â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         PatrollingMonster â˜ƒ = EntityType.PILLAGER.create(â˜ƒ);
         if (â˜ƒ != null) {
            if (â˜ƒ) {
               â˜ƒ.setPatrolLeader(true);
               â˜ƒ.findPatrolTarget();
            }

            â˜ƒ.setPos((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ());
            â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ), MobSpawnType.PATROL, null, null);
            â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
            return true;
         } else {
            return false;
         }
      }
   }
}
