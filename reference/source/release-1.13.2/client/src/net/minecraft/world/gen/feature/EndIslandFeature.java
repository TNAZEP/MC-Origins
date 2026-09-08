package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class EndIslandFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      float ☃ = (float)(☃.nextInt(3) + 4);

      for(int ☃x = 0; ☃ > 0.5F; --☃x) {
         for(int ☃xx = MathHelper.func_76141_d(-☃); ☃xx <= MathHelper.func_76123_f(☃); ++☃xx) {
            for(int ☃xxx = MathHelper.func_76141_d(-☃); ☃xxx <= MathHelper.func_76123_f(☃); ++☃xxx) {
               if ((float)(☃xx * ☃xx + ☃xxx * ☃xxx) <= (☃ + 1.0F) * (☃ + 1.0F)) {
                  this.func_202278_a(☃, ☃.func_177982_a(☃xx, ☃x, ☃xxx), Blocks.field_150377_bs.func_176223_P());
               }
            }
         }

         ☃ = (float)((double)☃ - ((double)☃.nextInt(2) + 0.5));
      }

      return true;
   }
}
