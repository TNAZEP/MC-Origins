package net.minecraft.world.level.levelgen;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class PhantomSpawner implements CustomSpawner {
   private int nextTick;

   @Override
   public int tick(ServerLevel var1, boolean var2, boolean var3) {
      if (!â˜ƒ) {
         return 0;
      } else if (!â˜ƒ.getGameRules().getBoolean(GameRules.RULE_DOINSOMNIA)) {
         return 0;
      } else {
         Random â˜ƒ = â˜ƒ.random;
         --this.nextTick;
         if (this.nextTick > 0) {
            return 0;
         } else {
            this.nextTick += (60 + â˜ƒ.nextInt(60)) * 20;
            if (â˜ƒ.getSkyDarken() < 5 && â˜ƒ.dimensionType().hasSkyLight()) {
               return 0;
            } else {
               int â˜ƒ = 0;

               for(Player â˜ƒx : â˜ƒ.players()) {
                  if (!â˜ƒx.isSpectator()) {
                     BlockPos â˜ƒxx = â˜ƒx.blockPosition();
                     if (!â˜ƒ.dimensionType().hasSkyLight() || â˜ƒxx.getY() >= â˜ƒ.getSeaLevel() && â˜ƒ.canSeeSky(â˜ƒxx)) {
                        DifficultyInstance â˜ƒxxx = â˜ƒ.getCurrentDifficultyAt(â˜ƒxx);
                        if (â˜ƒxxx.isHarderThan(â˜ƒ.nextFloat() * 3.0F)) {
                           ServerStatsCounter â˜ƒxxxx = ((ServerPlayer)â˜ƒx).getStats();
                           int â˜ƒxxxxx = Mth.clamp(â˜ƒxxxx.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
                           int â˜ƒxxxxxx = 24000;
                           if (â˜ƒ.nextInt(â˜ƒxxxxx) >= 72000) {
                              BlockPos â˜ƒxxxxxxx = â˜ƒxx.above(20 + â˜ƒ.nextInt(15)).east(-10 + â˜ƒ.nextInt(21)).south(-10 + â˜ƒ.nextInt(21));
                              BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxx);
                              FluidState â˜ƒxxxxxxxxx = â˜ƒ.getFluidState(â˜ƒxxxxxxx);
                              if (NaturalSpawner.isValidEmptySpawnBlock(â˜ƒ, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxx, EntityType.PHANTOM)) {
                                 SpawnGroupData â˜ƒxxxxxxxxxx = null;
                                 int â˜ƒxxxxxxxxxxx = 1 + â˜ƒ.nextInt(â˜ƒxxx.getDifficulty().getId() + 1);

                                 for(int â˜ƒxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxx < â˜ƒxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxx) {
                                    Phantom â˜ƒxxxxxxxxxxxxx = EntityType.PHANTOM.create(â˜ƒ);
                                    â˜ƒxxxxxxxxxxxxx.moveTo(â˜ƒxxxxxxx, 0.0F, 0.0F);
                                    â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.finalizeSpawn(â˜ƒ, â˜ƒxxx, MobSpawnType.NATURAL, â˜ƒxxxxxxxxxx, null);
                                    â˜ƒ.addFreshEntityWithPassengers(â˜ƒxxxxxxxxxxxxx);
                                 }

                                 â˜ƒ += â˜ƒxxxxxxxxxxx;
                              }
                           }
                        }
                     }
                  }
               }

               return â˜ƒ;
            }
         }
      }
   }
}
