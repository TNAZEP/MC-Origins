package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class DepthAverage extends BasePlacement<DepthAverageConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, DepthAverageConfig var5, Feature<C> var6, C var7
   ) {
      int ☃ = ☃.field_202483_a;
      int ☃x = ☃.field_202484_b;
      int ☃xx = ☃.field_202485_c;

      for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
         int ☃xxxx = ☃.nextInt(16);
         int ☃xxxxx = ☃.nextInt(☃xx) + ☃.nextInt(☃xx) - ☃xx + ☃x;
         int ☃xxxxxx = ☃.nextInt(16);
         BlockPos ☃xxxxxxx = ☃.func_177982_a(☃xxxx, ☃xxxxx, ☃xxxxxx);
         ☃.func_212245_a(☃, ☃, ☃, ☃xxxxxxx, ☃);
      }

      return true;
   }
}
