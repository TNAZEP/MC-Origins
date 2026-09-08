package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class DeadBushFeature extends Feature<NoFeatureConfig> {
   private static final BlockDeadBush field_197166_a = (BlockDeadBush)Blocks.field_196555_aI;

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      for(IBlockState ☃ = ☃.func_180495_p(☃); (☃.func_196958_f() || ☃.func_203425_a(BlockTags.field_206952_E)) && ☃.func_177956_o() > 0; ☃ = ☃.func_180495_p(☃)) {
         ☃ = ☃.func_177977_b();
      }

      IBlockState ☃ = field_197166_a.func_176223_P();

      for(int ☃x = 0; ☃x < 4; ++☃x) {
         BlockPos ☃xx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xx) && ☃.func_196955_c(☃, ☃xx)) {
            ☃.func_180501_a(☃xx, ☃, 2);
         }
      }

      return true;
   }
}
