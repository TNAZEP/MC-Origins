package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class NetherGlowstone extends BasePlacement<FrequencyConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, FrequencyConfig var5, Feature<C> var6, C var7
   ) {
      for(int ☃ = 0; ☃ < ☃.nextInt(☃.nextInt(☃.field_202476_a) + 1); ++☃) {
         int ☃x = ☃.nextInt(16);
         int ☃xx = ☃.nextInt(120) + 4;
         int ☃xxx = ☃.nextInt(16);
         ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃x, ☃xx, ☃xxx), ☃);
      }

      return true;
   }
}
