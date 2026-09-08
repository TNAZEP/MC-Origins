package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class EndIsland extends BasePlacement<NoPlacementConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoPlacementConfig var5, Feature<C> var6, C var7
   ) {
      boolean ☃ = false;
      if (☃.nextInt(14) == 0) {
         ☃ |= ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃.nextInt(16), 55 + ☃.nextInt(16), ☃.nextInt(16)), ☃);
         if (☃.nextInt(4) == 0) {
            ☃ |= ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃.nextInt(16), 55 + ☃.nextInt(16), ☃.nextInt(16)), ☃);
         }
      }

      return ☃;
   }
}
