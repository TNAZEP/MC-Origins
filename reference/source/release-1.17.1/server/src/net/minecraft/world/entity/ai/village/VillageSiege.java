package net.minecraft.world.entity.ai.village;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageSiege implements CustomSpawner {
   private static final Logger LOGGER = LogManager.getLogger();
   private boolean hasSetupSiege;
   private VillageSiege.State siegeState = VillageSiege.State.SIEGE_DONE;
   private int zombiesToSpawn;
   private int nextSpawnTime;
   private int spawnX;
   private int spawnY;
   private int spawnZ;

   @Override
   public int tick(ServerLevel var1, boolean var2, boolean var3) {
      if (!â˜ƒ.isDay() && â˜ƒ) {
         float â˜ƒ = â˜ƒ.getTimeOfDay(0.0F);
         if ((double)â˜ƒ == 0.5) {
            this.siegeState = â˜ƒ.random.nextInt(10) == 0 ? VillageSiege.State.SIEGE_TONIGHT : VillageSiege.State.SIEGE_DONE;
         }

         if (this.siegeState == VillageSiege.State.SIEGE_DONE) {
            return 0;
         } else {
            if (!this.hasSetupSiege) {
               if (!this.tryToSetupSiege(â˜ƒ)) {
                  return 0;
               }

               this.hasSetupSiege = true;
            }

            if (this.nextSpawnTime > 0) {
               --this.nextSpawnTime;
               return 0;
            } else {
               this.nextSpawnTime = 2;
               if (this.zombiesToSpawn > 0) {
                  this.trySpawn(â˜ƒ);
                  --this.zombiesToSpawn;
               } else {
                  this.siegeState = VillageSiege.State.SIEGE_DONE;
               }

               return 1;
            }
         }
      } else {
         this.siegeState = VillageSiege.State.SIEGE_DONE;
         this.hasSetupSiege = false;
         return 0;
      }
   }

   private boolean tryToSetupSiege(ServerLevel var1) {
      for(Player â˜ƒ : â˜ƒ.players()) {
         if (!â˜ƒ.isSpectator()) {
            BlockPos â˜ƒx = â˜ƒ.blockPosition();
            if (â˜ƒ.isVillage(â˜ƒx) && â˜ƒ.getBiome(â˜ƒx).getBiomeCategory() != Biome.BiomeCategory.MUSHROOM) {
               for(int â˜ƒxx = 0; â˜ƒxx < 10; ++â˜ƒxx) {
                  float â˜ƒxxx = â˜ƒ.random.nextFloat() * (float) (Math.PI * 2);
                  this.spawnX = â˜ƒx.getX() + Mth.floor(Mth.cos(â˜ƒxxx) * 32.0F);
                  this.spawnY = â˜ƒx.getY();
                  this.spawnZ = â˜ƒx.getZ() + Mth.floor(Mth.sin(â˜ƒxxx) * 32.0F);
                  if (this.findRandomSpawnPos(â˜ƒ, new BlockPos(this.spawnX, this.spawnY, this.spawnZ)) != null) {
                     this.nextSpawnTime = 0;
                     this.zombiesToSpawn = 20;
                     break;
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   private void trySpawn(ServerLevel var1) {
      Vec3 â˜ƒ = this.findRandomSpawnPos(â˜ƒ, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
      if (â˜ƒ != null) {
         Zombie â˜ƒ;
         try {
            â˜ƒ = new Zombie(â˜ƒ);
            â˜ƒ.finalizeSpawn(â˜ƒ, â˜ƒ.getCurrentDifficultyAt(â˜ƒ.blockPosition()), MobSpawnType.EVENT, null, null);
         } catch (Exception var5) {
            LOGGER.warn("Failed to create zombie for village siege at {}", â˜ƒ, var5);
            return;
         }

         â˜ƒ.moveTo(â˜ƒ.x, â˜ƒ.y, â˜ƒ.z, â˜ƒ.random.nextFloat() * 360.0F, 0.0F);
         â˜ƒ.addFreshEntityWithPassengers(â˜ƒ);
      }
   }

   @Nullable
   private Vec3 findRandomSpawnPos(ServerLevel var1, BlockPos var2) {
      for(int â˜ƒ = 0; â˜ƒ < 10; ++â˜ƒ) {
         int â˜ƒx = â˜ƒ.getX() + â˜ƒ.random.nextInt(16) - 8;
         int â˜ƒxx = â˜ƒ.getZ() + â˜ƒ.random.nextInt(16) - 8;
         int â˜ƒxxx = â˜ƒ.getHeight(Heightmap.Types.WORLD_SURFACE, â˜ƒx, â˜ƒxx);
         BlockPos â˜ƒxxxx = new BlockPos(â˜ƒx, â˜ƒxxx, â˜ƒxx);
         if (â˜ƒ.isVillage(â˜ƒxxxx) && Monster.checkMonsterSpawnRules(EntityType.ZOMBIE, â˜ƒ, MobSpawnType.EVENT, â˜ƒxxxx, â˜ƒ.random)) {
            return Vec3.atBottomCenterOf(â˜ƒxxxx);
         }
      }

      return null;
   }

   static enum State {
      SIEGE_CAN_ACTIVATE,
      SIEGE_TONIGHT,
      SIEGE_DONE;
   }
}
