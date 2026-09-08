package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class AtSurfaceWithChanceMultiple extends BasePlacement<HeightWithChanceConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, HeightWithChanceConfig var5, Feature<C> var6, C var7
   ) {
      for(int ☃ = 0; ☃ < ☃.field_202481_a; ++☃) {
         if (☃.nextFloat() < ☃.field_202482_b) {
            int ☃x = ☃.nextInt(16);
            int ☃xx = ☃.nextInt(16);
            BlockPos ☃xxx = ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃x, 0, ☃xx));
            ☃.func_212245_a(☃, ☃, ☃, ☃xxx, ☃);
         }
      }

      return true;
   }
}
