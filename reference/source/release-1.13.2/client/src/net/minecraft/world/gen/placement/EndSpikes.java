package net.minecraft.world.gen.placement;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.EndCrystalTowerFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndSpikes extends BasePlacement<NoPlacementConfig> {
   private static final LoadingCache<Long, EndCrystalTowerFeature.EndSpike[]> field_202467_a = CacheBuilder.newBuilder()
      .expireAfterWrite(5L, TimeUnit.MINUTES)
      .build(new EndSpikes.CacheLoader());

   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoPlacementConfig var5, Feature<C> var6, C var7
   ) {
      EndCrystalTowerFeature.EndSpike[] ☃ = func_202466_a(☃);
      boolean ☃x = false;

      for(EndCrystalTowerFeature.EndSpike ☃xx : ☃) {
         if (☃xx.func_186154_a(☃)) {
            ((EndCrystalTowerFeature)☃).func_186143_a(☃xx);
            ☃x |= ((EndCrystalTowerFeature)☃).func_212245_a(☃, ☃, ☃, new BlockPos(☃xx.func_186151_a(), 45, ☃xx.func_186152_b()), IFeatureConfig.field_202429_e);
         }
      }

      return ☃x;
   }

   public static EndCrystalTowerFeature.EndSpike[] func_202466_a(IWorld var0) {
      Random ☃ = new Random(☃.func_72905_C());
      long ☃x = ☃.nextLong() & 65535L;
      return field_202467_a.getUnchecked(☃x);
   }

   static class CacheLoader extends com.google.common.cache.CacheLoader<Long, EndCrystalTowerFeature.EndSpike[]> {
      private CacheLoader() {
      }

      public EndCrystalTowerFeature.EndSpike[] load(Long var1) throws Exception {
         List<Integer> ☃ = Lists.newArrayList(ContiguousSet.create(Range.closedOpen(0, 10), DiscreteDomain.integers()));
         Collections.shuffle(☃, new Random(☃));
         EndCrystalTowerFeature.EndSpike[] ☃x = new EndCrystalTowerFeature.EndSpike[10];

         for(int ☃xx = 0; ☃xx < 10; ++☃xx) {
            int ☃xxx = (int)(42.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 10) * (double)☃xx)));
            int ☃xxxx = (int)(42.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 10) * (double)☃xx)));
            int ☃xxxxx = ☃.get(☃xx);
            int ☃xxxxxx = 2 + ☃xxxxx / 3;
            int ☃xxxxxxx = 76 + ☃xxxxx * 3;
            boolean ☃xxxxxxxx = ☃xxxxx == 1 || ☃xxxxx == 2;
            ☃x[☃xx] = new EndCrystalTowerFeature.EndSpike(☃xxx, ☃xxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
         }

         return ☃x;
      }
   }
}
