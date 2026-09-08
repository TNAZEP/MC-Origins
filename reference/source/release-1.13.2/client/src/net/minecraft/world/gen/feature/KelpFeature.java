package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockKelpTop;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class KelpFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      int ☃ = 0;
      int ☃x = ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR, ☃.func_177958_n(), ☃.func_177952_p());
      BlockPos ☃xx = new BlockPos(☃.func_177958_n(), ☃x, ☃.func_177952_p());
      if (☃.func_180495_p(☃xx).func_177230_c() == Blocks.field_150355_j) {
         IBlockState ☃xxx = Blocks.field_203214_jx.func_176223_P();
         IBlockState ☃xxxx = Blocks.field_203215_jy.func_176223_P();
         int ☃xxxxx = 1 + ☃.nextInt(10);

         for(int ☃xxxxxx = 0; ☃xxxxxx <= ☃xxxxx; ++☃xxxxxx) {
            if (☃.func_180495_p(☃xx).func_177230_c() == Blocks.field_150355_j
               && ☃.func_180495_p(☃xx.func_177984_a()).func_177230_c() == Blocks.field_150355_j
               && ☃xxxx.func_196955_c(☃, ☃xx)) {
               if (☃xxxxxx == ☃xxxxx) {
                  ☃.func_180501_a(☃xx, ☃xxx.func_206870_a(BlockKelpTop.field_203163_a, Integer.valueOf(☃.nextInt(23))), 2);
                  ++☃;
               } else {
                  ☃.func_180501_a(☃xx, ☃xxxx, 2);
               }
            } else if (☃xxxxxx > 0) {
               BlockPos ☃xxxxxxx = ☃xx.func_177977_b();
               if (☃xxx.func_196955_c(☃, ☃xxxxxxx) && ☃.func_180495_p(☃xxxxxxx.func_177977_b()).func_177230_c() != Blocks.field_203214_jx) {
                  ☃.func_180501_a(☃xxxxxxx, ☃xxx.func_206870_a(BlockKelpTop.field_203163_a, Integer.valueOf(☃.nextInt(23))), 2);
                  ++☃;
               }
               break;
            }

            ☃xx = ☃xx.func_177984_a();
         }
      }

      return ☃ > 0;
   }
}
