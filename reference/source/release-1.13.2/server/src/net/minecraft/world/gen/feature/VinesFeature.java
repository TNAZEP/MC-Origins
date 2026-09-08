package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class VinesFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos(☃);

      for(int ☃x = ☃.func_177956_o(); ☃x < 256; ++☃x) {
         ☃.func_189533_g(☃);
         ☃.func_196234_d(☃.nextInt(4) - ☃.nextInt(4), 0, ☃.nextInt(4) - ☃.nextInt(4));
         ☃.func_185336_p(☃x);
         if (☃.func_175623_d(☃)) {
            for(EnumFacing ☃xx : EnumFacing.Plane.HORIZONTAL) {
               IBlockState ☃xxx = Blocks.field_150395_bd.func_176223_P().func_206870_a(BlockVine.func_176267_a(☃xx), Boolean.valueOf(true));
               if (☃xxx.func_196955_c(☃, ☃)) {
                  ☃.func_180501_a(☃, ☃xxx, 2);
                  break;
               }
            }
         }
      }

      return true;
   }
}
