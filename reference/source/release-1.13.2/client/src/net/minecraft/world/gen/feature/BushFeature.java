package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BushFeature extends Feature<BushConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, BushConfig var5) {
      int ☃ = 0;
      IBlockState ☃x = ☃.field_202430_a.func_176223_P();

      for(int ☃xx = 0; ☃xx < 64; ++☃xx) {
         BlockPos ☃xxx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xxx) && (!☃.func_201675_m().func_177495_o() || ☃xxx.func_177956_o() < 255) && ☃x.func_196955_c(☃, ☃xxx)) {
            ☃.func_180501_a(☃xxx, ☃x, 2);
            ++☃;
         }
      }

      return ☃ > 0;
   }
}
