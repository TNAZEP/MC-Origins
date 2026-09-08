package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class PumpkinFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      int ☃ = 0;
      IBlockState ☃x = Blocks.field_150423_aK.func_176223_P();

      for(int ☃xx = 0; ☃xx < 64; ++☃xx) {
         BlockPos ☃xxx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xxx) && ☃.func_180495_p(☃xxx.func_177977_b()).func_177230_c() == Blocks.field_196658_i) {
            ☃.func_180501_a(☃xxx, ☃x, 2);
            ++☃;
         }
      }

      return ☃ > 0;
   }
}
