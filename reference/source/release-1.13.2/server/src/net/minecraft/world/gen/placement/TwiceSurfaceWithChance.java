package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TwiceSurfaceWithChance extends BasePlacement<ChanceConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, ChanceConfig var5, Feature<C> var6, C var7
   ) {
      if (☃.nextFloat() < 1.0F / (float)☃.field_202477_a) {
         int ☃ = ☃.nextInt(16);
         int ☃x = ☃.nextInt(16);
         int ☃xx = ☃.func_205770_a(Heightmap.Type.MOTION_BLOCKING, ☃.func_177982_a(☃, 0, ☃x)).func_177956_o() * 2;
         if (☃xx <= 0) {
            return false;
         }

         int ☃ = ☃.nextInt(☃xx);
         ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃, ☃, ☃x), ☃);
      }

      return true;
   }
}
