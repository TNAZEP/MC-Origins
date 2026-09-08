package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ChorusPlant extends BasePlacement<NoPlacementConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoPlacementConfig var5, Feature<C> var6, C var7
   ) {
      boolean ☃ = false;
      int ☃x = ☃.nextInt(5);

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         int ☃xxx = ☃.nextInt(16);
         int ☃xxxx = ☃.nextInt(16);
         int ☃xxxxx = ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃xxx, 0, ☃xxxx)).func_177956_o();
         if (☃xxxxx > 0) {
            int ☃xxxxxx = ☃xxxxx - 1;
            ☃ |= ☃.func_212245_a(☃, ☃, ☃, new BlockPos(☃.func_177958_n() + ☃xxx, ☃xxxxxx, ☃.func_177952_p() + ☃xxxx), ☃);
         }
      }

      return ☃;
   }
}
