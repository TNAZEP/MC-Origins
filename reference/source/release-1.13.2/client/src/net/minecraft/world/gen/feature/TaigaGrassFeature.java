package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class TaigaGrassFeature extends Feature<NoFeatureConfig> {
   public IBlockState func_202388_a(Random var1) {
      return ☃.nextInt(5) > 0 ? Blocks.field_196554_aH.func_176223_P() : Blocks.field_150349_c.func_176223_P();
   }

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      IBlockState ☃ = this.func_202388_a(☃);

      for(IBlockState ☃x = ☃.func_180495_p(☃);
         (☃x.func_196958_f() || ☃x.func_203425_a(BlockTags.field_206952_E)) && ☃.func_177956_o() > 0;
         ☃x = ☃.func_180495_p(☃)
      ) {
         ☃ = ☃.func_177977_b();
      }

      int ☃x = 0;

      for(int ☃xx = 0; ☃xx < 128; ++☃xx) {
         BlockPos ☃xxx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xxx) && ☃.func_196955_c(☃, ☃xxx)) {
            ☃.func_180501_a(☃xxx, ☃, 2);
            ++☃x;
         }
      }

      return ☃x > 0;
   }
}
