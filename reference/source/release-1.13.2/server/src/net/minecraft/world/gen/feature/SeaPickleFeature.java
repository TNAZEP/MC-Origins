package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockSeaPickle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.placement.CountConfig;

public class SeaPickleFeature extends Feature<CountConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<?> var2, Random var3, BlockPos var4, CountConfig var5) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < ☃.field_204915_a; ++☃x) {
         int ☃xx = ☃.nextInt(8) - ☃.nextInt(8);
         int ☃xxx = ☃.nextInt(8) - ☃.nextInt(8);
         int ☃xxxx = ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR, ☃.func_177958_n() + ☃xx, ☃.func_177952_p() + ☃xxx);
         BlockPos ☃xxxxx = new BlockPos(☃.func_177958_n() + ☃xx, ☃xxxx, ☃.func_177952_p() + ☃xxx);
         IBlockState ☃xxxxxx = Blocks.field_204913_jW.func_176223_P().func_206870_a(BlockSeaPickle.field_204902_a, Integer.valueOf(☃.nextInt(4) + 1));
         if (☃.func_180495_p(☃xxxxx).func_177230_c() == Blocks.field_150355_j && ☃xxxxxx.func_196955_c(☃, ☃xxxxx)) {
            ☃.func_180501_a(☃xxxxx, ☃xxxxxx, 2);
            ++☃;
         }
      }

      return ☃ > 0;
   }
}
