package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TopSolid extends BasePlacement<FrequencyConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, FrequencyConfig var5, Feature<C> var6, C var7
   ) {
      for(int ☃ = 0; ☃ < ☃.field_202476_a; ++☃) {
         int ☃x = ☃.nextInt(16) + ☃.func_177958_n();
         int ☃xx = ☃.nextInt(16) + ☃.func_177952_p();
         ☃.func_212245_a(☃, ☃, ☃, new BlockPos(☃x, ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR_WG, ☃x, ☃xx), ☃xx), ☃);
      }

      return true;
   }
}
