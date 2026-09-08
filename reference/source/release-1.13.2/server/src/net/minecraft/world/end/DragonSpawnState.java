package net.minecraft.world.end;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.EndCrystalTowerFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.EndSpikes;

public enum DragonSpawnState {
   START {
      @Override
      public void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5) {
         BlockPos ☃ = new BlockPos(0, 128, 0);

         for(EntityEnderCrystal ☃x : ☃) {
            ☃x.func_184516_a(☃);
         }

         ☃.func_186095_a(PREPARING_TO_SUMMON_PILLARS);
      }
   },
   PREPARING_TO_SUMMON_PILLARS {
      @Override
      public void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5) {
         if (☃ < 100) {
            if (☃ == 0 || ☃ == 50 || ☃ == 51 || ☃ == 52 || ☃ >= 95) {
               ☃.func_175718_b(3001, new BlockPos(0, 128, 0), 0);
            }
         } else {
            ☃.func_186095_a(SUMMONING_PILLARS);
         }
      }
   },
   SUMMONING_PILLARS {
      @Override
      public void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5) {
         int ☃ = 40;
         boolean ☃x = ☃ % 40 == 0;
         boolean ☃xx = ☃ % 40 == 39;
         if (☃x || ☃xx) {
            EndCrystalTowerFeature.EndSpike[] ☃xxx = EndSpikes.func_202466_a(☃);
            int ☃xxxx = ☃ / 40;
            if (☃xxxx < ☃xxx.length) {
               EndCrystalTowerFeature.EndSpike ☃xxxxx = ☃xxx[☃xxxx];
               if (☃x) {
                  for(EntityEnderCrystal ☃xxxxxx : ☃) {
                     ☃xxxxxx.func_184516_a(new BlockPos(☃xxxxx.func_186151_a(), ☃xxxxx.func_186149_d() + 1, ☃xxxxx.func_186152_b()));
                  }
               } else {
                  int ☃xxxxx = 10;

                  for(BlockPos.MutableBlockPos ☃xxxxxx : BlockPos.func_177975_b(
                     new BlockPos(☃xxxxx.func_186151_a() - 10, ☃xxxxx.func_186149_d() - 10, ☃xxxxx.func_186152_b() - 10),
                     new BlockPos(☃xxxxx.func_186151_a() + 10, ☃xxxxx.func_186149_d() + 10, ☃xxxxx.func_186152_b() + 10)
                  )) {
                     ☃.func_175698_g(☃xxxxxx);
                  }

                  ☃.func_72876_a(
                     null,
                     (double)((float)☃xxxxx.func_186151_a() + 0.5F),
                     (double)☃xxxxx.func_186149_d(),
                     (double)((float)☃xxxxx.func_186152_b() + 0.5F),
                     5.0F,
                     true
                  );
                  EndCrystalTowerFeature ☃xxxxxx = new EndCrystalTowerFeature();
                  ☃xxxxxx.func_186143_a(☃xxxxx);
                  ☃xxxxxx.func_186144_a(true);
                  ☃xxxxxx.func_186142_a(new BlockPos(0, 128, 0));
                  ☃xxxxxx.func_212245_a(
                     ☃,
                     ☃.func_72863_F().func_201711_g(),
                     new Random(),
                     new BlockPos(☃xxxxx.func_186151_a(), 45, ☃xxxxx.func_186152_b()),
                     IFeatureConfig.field_202429_e
                  );
               }
            } else if (☃x) {
               ☃.func_186095_a(SUMMONING_DRAGON);
            }
         }
      }
   },
   SUMMONING_DRAGON {
      @Override
      public void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5) {
         if (☃ >= 100) {
            ☃.func_186095_a(END);
            ☃.func_186087_f();

            for(EntityEnderCrystal ☃ : ☃) {
               ☃.func_184516_a(null);
               ☃.func_72876_a(☃, ☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v, 6.0F, false);
               ☃.func_70106_y();
            }
         } else if (☃ >= 80) {
            ☃.func_175718_b(3001, new BlockPos(0, 128, 0), 0);
         } else if (☃ == 0) {
            for(EntityEnderCrystal ☃ : ☃) {
               ☃.func_184516_a(new BlockPos(0, 128, 0));
            }
         } else if (☃ < 5) {
            ☃.func_175718_b(3001, new BlockPos(0, 128, 0), 0);
         }
      }
   },
   END {
      @Override
      public void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5) {
      }
   };

   private DragonSpawnState() {
   }

   public abstract void func_186079_a(WorldServer var1, DragonFightManager var2, List<EntityEnderCrystal> var3, int var4, BlockPos var5);
}
