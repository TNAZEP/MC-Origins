package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class AbstractFlowersFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      IBlockState ☃ = this.func_202355_a(☃, ☃);
      int ☃x = 0;

      for(int ☃xx = 0; ☃xx < 64; ++☃xx) {
         BlockPos ☃xxx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xxx) && ☃xxx.func_177956_o() < 255 && ☃.func_196955_c(☃, ☃xxx)) {
            ☃.func_180501_a(☃xxx, ☃, 2);
            ++☃x;
         }
      }

      return ☃x > 0;
   }

   public abstract IBlockState func_202355_a(Random var1, BlockPos var2);
}
