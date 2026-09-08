package net.minecraft.world.gen.placement;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class LakeLava extends BasePlacement<LakeChanceConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, LakeChanceConfig var5, Feature<C> var6, C var7
   ) {
      if (☃.nextInt(☃.field_202486_a / 10) == 0) {
         int ☃ = ☃.nextInt(16);
         int ☃x = ☃.nextInt(☃.nextInt(☃.func_207511_e() - 8) + 8);
         int ☃xx = ☃.nextInt(16);
         if (☃x < ☃.func_181545_F() || ☃.nextInt(☃.field_202486_a / 8) == 0) {
            ☃.func_212245_a(☃, ☃, ☃, ☃.func_177982_a(☃, ☃x, ☃xx), ☃);
         }
      }

      return true;
   }
}
