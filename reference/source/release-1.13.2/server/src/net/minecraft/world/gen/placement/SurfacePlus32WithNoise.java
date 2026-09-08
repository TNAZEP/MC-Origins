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

public class SurfacePlus32WithNoise extends BasePlacement<NoiseDependant> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoiseDependant var5, Feature<C> var6, C var7
   ) {
      double ☃ = Biome.field_180281_af.func_151601_a((double)☃.func_177958_n() / 200.0, (double)☃.func_177952_p() / 200.0);
      int ☃x = ☃ < ☃.field_202473_a ? ☃.field_202474_b : ☃.field_202475_c;

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         int ☃xxx = ☃.nextInt(16);
         int ☃xxxx = ☃.nextInt(16);
         int ☃xxxxx = ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃xxx, 0, ☃xxxx)).func_177956_o() + 32;
         if (☃xxxxx > 0) {
            int ☃xxxxxx = ☃.nextInt(☃xxxxx);
            ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃xxx, ☃xxxxxx, ☃xxxx), ☃);
         }
      }

      return true;
   }
}
