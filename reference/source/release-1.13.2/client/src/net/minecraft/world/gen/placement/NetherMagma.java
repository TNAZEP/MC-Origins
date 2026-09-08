package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NetherMagma extends BasePlacement<FrequencyConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, FrequencyConfig var5, Feature<C> var6, C var7
   ) {
      int ☃ = ☃.func_181545_F() / 2 + 1;

      for(int ☃x = 0; ☃x < ☃.field_202476_a; ++☃x) {
         int ☃xx = ☃.nextInt(16);
         int ☃xxx = ☃ - 5 + ☃.nextInt(10);
         int ☃xxxx = ☃.nextInt(16);
         ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃xx, ☃xxx, ☃xxxx), ☃);
      }

      return true;
   }
}
