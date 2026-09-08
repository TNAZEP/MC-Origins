package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class CactusFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      for(int ☃ = 0; ☃ < 10; ++☃) {
         BlockPos ☃x = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃x)) {
            int ☃xx = 1 + ☃.nextInt(☃.nextInt(3) + 1);

            for(int ☃xxx = 0; ☃xxx < ☃xx; ++☃xxx) {
               if (Blocks.field_150434_aF.func_176223_P().func_196955_c(☃, ☃x)) {
                  ☃.func_180501_a(☃x.func_177981_b(☃xxx), Blocks.field_150434_aF.func_176223_P(), 2);
               }
            }
         }
      }

      return true;
   }
}
