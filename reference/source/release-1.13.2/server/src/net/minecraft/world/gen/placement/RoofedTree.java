package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class RoofedTree extends BasePlacement<NoPlacementConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoPlacementConfig var5, Feature<C> var6, C var7
   ) {
      for(int ☃ = 0; ☃ < 4; ++☃) {
         for(int ☃x = 0; ☃x < 4; ++☃x) {
            int ☃xx = ☃ * 4 + 1 + ☃.nextInt(3);
            int ☃xxx = ☃x * 4 + 1 + ☃.nextInt(3);
            ☃.func_212245_a(☃, ☃, ☃, ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃xx, 0, ☃xxx)), ☃);
         }
      }

      return true;
   }
}
