package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class TallGrassFeature extends Feature<TallGrassConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, TallGrassConfig var5) {
      for(IBlockState ☃ = ☃.func_180495_p(☃); (☃.func_196958_f() || ☃.func_203425_a(BlockTags.field_206952_E)) && ☃.func_177956_o() > 0; ☃ = ☃.func_180495_p(☃)) {
         ☃ = ☃.func_177977_b();
      }

      int ☃ = 0;

      for(int ☃x = 0; ☃x < 128; ++☃x) {
         BlockPos ☃xx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xx) && ☃.field_202460_a.func_196955_c(☃, ☃xx)) {
            ☃.func_180501_a(☃xx, ☃.field_202460_a, 2);
            ++☃;
         }
      }

      return ☃ > 0;
   }
}
