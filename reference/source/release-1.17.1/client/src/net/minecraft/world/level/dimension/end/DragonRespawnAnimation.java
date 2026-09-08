package net.minecraft.world.level.dimension.end;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SpikeConfiguration;

public enum DragonRespawnAnimation {
   START {
      @Override
      public void tick(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5) {
         BlockPos â˜ƒ = new BlockPos(0, 128, 0);

         for(EndCrystal â˜ƒx : â˜ƒ) {
            â˜ƒx.setBeamTarget(â˜ƒ);
         }

         â˜ƒ.setRespawnStage(PREPARING_TO_SUMMON_PILLARS);
      }
   },
   PREPARING_TO_SUMMON_PILLARS {
      @Override
      public void tick(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5) {
         if (â˜ƒ < 100) {
            if (â˜ƒ == 0 || â˜ƒ == 50 || â˜ƒ == 51 || â˜ƒ == 52 || â˜ƒ >= 95) {
               â˜ƒ.levelEvent(3001, new BlockPos(0, 128, 0), 0);
            }
         } else {
            â˜ƒ.setRespawnStage(SUMMONING_PILLARS);
         }
      }
   },
   SUMMONING_PILLARS {
      @Override
      public void tick(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5) {
         int â˜ƒ = 40;
         boolean â˜ƒx = â˜ƒ % 40 == 0;
         boolean â˜ƒxx = â˜ƒ % 40 == 39;
         if (â˜ƒx || â˜ƒxx) {
            List<SpikeFeature.EndSpike> â˜ƒxxx = SpikeFeature.getSpikesForLevel(â˜ƒ);
            int â˜ƒxxxx = â˜ƒ / 40;
            if (â˜ƒxxxx < â˜ƒxxx.size()) {
               SpikeFeature.EndSpike â˜ƒxxxxx = (SpikeFeature.EndSpike)â˜ƒxxx.get(â˜ƒxxxx);
               if (â˜ƒx) {
                  for(EndCrystal â˜ƒxxxxxx : â˜ƒ) {
                     â˜ƒxxxxxx.setBeamTarget(new BlockPos(â˜ƒxxxxx.getCenterX(), â˜ƒxxxxx.getHeight() + 1, â˜ƒxxxxx.getCenterZ()));
                  }
               } else {
                  int â˜ƒxxxxx = 10;

                  for(BlockPos â˜ƒxxxxxx : BlockPos.betweenClosed(
                     new BlockPos(â˜ƒxxxxx.getCenterX() - 10, â˜ƒxxxxx.getHeight() - 10, â˜ƒxxxxx.getCenterZ() - 10),
                     new BlockPos(â˜ƒxxxxx.getCenterX() + 10, â˜ƒxxxxx.getHeight() + 10, â˜ƒxxxxx.getCenterZ() + 10)
                  )) {
                     â˜ƒ.removeBlock(â˜ƒxxxxxx, false);
                  }

                  â˜ƒ.explode(
                     null,
                     (double)((float)â˜ƒxxxxx.getCenterX() + 0.5F),
                     (double)â˜ƒxxxxx.getHeight(),
                     (double)((float)â˜ƒxxxxx.getCenterZ() + 0.5F),
                     5.0F,
                     Explosion.BlockInteraction.DESTROY
                  );
                  SpikeConfiguration â˜ƒxxxxxx = new SpikeConfiguration(true, ImmutableList.of(â˜ƒxxxxx), new BlockPos(0, 128, 0));
                  Feature.END_SPIKE
                     .configured(â˜ƒxxxxxx)
                     .place(â˜ƒ, â˜ƒ.getChunkSource().getGenerator(), new Random(), new BlockPos(â˜ƒxxxxx.getCenterX(), 45, â˜ƒxxxxx.getCenterZ()));
               }
            } else if (â˜ƒx) {
               â˜ƒ.setRespawnStage(SUMMONING_DRAGON);
            }
         }
      }
   },
   SUMMONING_DRAGON {
      @Override
      public void tick(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5) {
         if (â˜ƒ >= 100) {
            â˜ƒ.setRespawnStage(END);
            â˜ƒ.resetSpikeCrystals();

            for(EndCrystal â˜ƒ : â˜ƒ) {
               â˜ƒ.setBeamTarget(null);
               â˜ƒ.explode(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), 6.0F, Explosion.BlockInteraction.NONE);
               â˜ƒ.discard();
            }
         } else if (â˜ƒ >= 80) {
            â˜ƒ.levelEvent(3001, new BlockPos(0, 128, 0), 0);
         } else if (â˜ƒ == 0) {
            for(EndCrystal â˜ƒ : â˜ƒ) {
               â˜ƒ.setBeamTarget(new BlockPos(0, 128, 0));
            }
         } else if (â˜ƒ < 5) {
            â˜ƒ.levelEvent(3001, new BlockPos(0, 128, 0), 0);
         }
      }
   },
   END {
      @Override
      public void tick(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5) {
      }
   };

   public abstract void tick(ServerLevel var1, EndDragonFight var2, List<EndCrystal> var3, int var4, BlockPos var5);
}
