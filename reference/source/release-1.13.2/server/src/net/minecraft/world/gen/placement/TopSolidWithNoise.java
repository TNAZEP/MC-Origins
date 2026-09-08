package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TopSolidWithNoise extends BasePlacement<TopSolidWithNoiseConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, TopSolidWithNoiseConfig var5, Feature<C> var6, C var7
   ) {
      double ☃ = Biome.field_180281_af.func_151601_a((double)☃.func_177958_n() / ☃.field_204631_b, (double)☃.func_177952_p() / ☃.field_204631_b);
      int ☃x = (int)Math.ceil(☃ * (double)☃.field_204630_a);

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         int ☃xxx = ☃.nextInt(16);
         int ☃xxxx = ☃.nextInt(16);
         int ☃xxxxx = ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR_WG, ☃.func_177958_n() + ☃xxx, ☃.func_177952_p() + ☃xxxx);
         ☃.func_212245_a(☃, ☃, ☃, new BlockPos(☃.func_177958_n() + ☃xxx, ☃xxxxx, ☃.func_177952_p() + ☃xxxx), ☃);
      }

      return false;
   }
}
