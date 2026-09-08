package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ChanceRange extends BasePlacement<ChanceRangeConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, ChanceRangeConfig var5, Feature<C> var6, C var7
   ) {
      if (☃.nextFloat() < ☃.field_202488_a) {
         int ☃ = ☃.nextInt(16);
         int ☃x = ☃.nextInt(☃.field_202491_d - ☃.field_202489_b) + ☃.field_202490_c;
         int ☃xx = ☃.nextInt(16);
         ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃, ☃x, ☃xx), ☃);
      }

      return true;
   }
}
