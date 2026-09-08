package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class AtSurfaceWithExtra extends BasePlacement<AtSurfaceWithExtraConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, AtSurfaceWithExtraConfig var5, Feature<C> var6, C var7
   ) {
      int ☃ = ☃.field_202478_a;
      if (☃.nextFloat() < ☃.field_202479_b) {
         ☃ += ☃.field_202480_c;
      }

      for(int ☃ = 0; ☃ < ☃; ++☃) {
         int ☃x = ☃.nextInt(16);
         int ☃xx = ☃.nextInt(16);
         ☃.func_212245_a(☃, ☃, ☃, ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃x, 0, ☃xx)), ☃);
      }

      return true;
   }
}
