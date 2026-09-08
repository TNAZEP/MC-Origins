package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CoralTreeFeature extends CoralFeature {
   @Override
   protected boolean func_204623_a(IWorld var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos(☃);
      int ☃x = ☃.nextInt(3) + 1;

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         if (!this.func_204624_b(☃, ☃, ☃, ☃)) {
            return true;
         }

         ☃.func_189536_c(EnumFacing.UP);
      }

      BlockPos ☃xx = ☃.func_185334_h();
      int ☃xxx = ☃.nextInt(3) + 2;
      List<EnumFacing> ☃xxxx = Lists.<EnumFacing>newArrayList(EnumFacing.Plane.HORIZONTAL);
      Collections.shuffle(☃xxxx, ☃);

      for(EnumFacing ☃xxxxx : ☃xxxx.subList(0, ☃xxx)) {
         ☃.func_189533_g(☃xx);
         ☃.func_189536_c(☃xxxxx);
         int ☃xxxxxx = ☃.nextInt(5) + 2;
         int ☃xxxxxxx = 0;

         for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < ☃xxxxxx && this.func_204624_b(☃, ☃, ☃, ☃); ++☃xxxxxxxx) {
            ++☃xxxxxxx;
            ☃.func_189536_c(EnumFacing.UP);
            if (☃xxxxxxxx == 0 || ☃xxxxxxx >= 2 && ☃.nextFloat() < 0.25F) {
               ☃.func_189536_c(☃xxxxx);
               ☃xxxxxxx = 0;
            }
         }
      }

      return true;
   }
}
