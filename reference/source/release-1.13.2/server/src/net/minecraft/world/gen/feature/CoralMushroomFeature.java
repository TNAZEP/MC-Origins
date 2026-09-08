package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CoralMushroomFeature extends CoralFeature {
   @Override
   protected boolean func_204623_a(IWorld var1, Random var2, BlockPos var3, IBlockState var4) {
      int ☃ = ☃.nextInt(3) + 3;
      int ☃x = ☃.nextInt(3) + 3;
      int ☃xx = ☃.nextInt(3) + 3;
      int ☃xxx = ☃.nextInt(3) + 1;
      BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos(☃);

      for(int ☃xxxxx = 0; ☃xxxxx <= ☃x; ++☃xxxxx) {
         for(int ☃xxxxxx = 0; ☃xxxxxx <= ☃; ++☃xxxxxx) {
            for(int ☃xxxxxxx = 0; ☃xxxxxxx <= ☃xx; ++☃xxxxxxx) {
               ☃xxxx.func_181079_c(☃xxxxx + ☃.func_177958_n(), ☃xxxxxx + ☃.func_177956_o(), ☃xxxxxxx + ☃.func_177952_p());
               ☃xxxx.func_189534_c(EnumFacing.DOWN, ☃xxx);
               if ((☃xxxxx != 0 && ☃xxxxx != ☃x || ☃xxxxxx != 0 && ☃xxxxxx != ☃)
                  && (☃xxxxxxx != 0 && ☃xxxxxxx != ☃xx || ☃xxxxxx != 0 && ☃xxxxxx != ☃)
                  && (☃xxxxx != 0 && ☃xxxxx != ☃x || ☃xxxxxxx != 0 && ☃xxxxxxx != ☃xx)
                  && (☃xxxxx == 0 || ☃xxxxx == ☃x || ☃xxxxxx == 0 || ☃xxxxxx == ☃ || ☃xxxxxxx == 0 || ☃xxxxxxx == ☃xx)
                  && !(☃.nextFloat() < 0.1F)
                  && !this.func_204624_b(☃, ☃, ☃xxxx, ☃)) {
               }
            }
         }
      }

      return true;
   }
}
