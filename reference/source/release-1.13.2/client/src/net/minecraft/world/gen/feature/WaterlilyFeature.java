package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class WaterlilyFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      BlockPos ☃;
      for(BlockPos ☃ = ☃; ☃.func_177956_o() > 0; ☃ = ☃) {
         ☃ = ☃.func_177977_b();
         if (!☃.func_175623_d(☃)) {
            break;
         }
      }

      for(int ☃ = 0; ☃ < 10; ++☃) {
         BlockPos ☃x = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         IBlockState ☃xx = Blocks.field_196651_dG.func_176223_P();
         if (☃.func_175623_d(☃x) && ☃xx.func_196955_c(☃, ☃x)) {
            ☃.func_180501_a(☃x, ☃xx, 2);
         }
      }

      return true;
   }
}
