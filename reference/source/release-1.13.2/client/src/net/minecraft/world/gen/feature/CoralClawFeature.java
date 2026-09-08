package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class CoralClawFeature extends CoralFeature {
   @Override
   protected boolean func_204623_a(IWorld var1, Random var2, BlockPos var3, IBlockState var4) {
      if (!this.func_204624_b(☃, ☃, ☃, ☃)) {
         return false;
      } else {
         EnumFacing ☃ = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
         int ☃x = ☃.nextInt(2) + 2;
         List<EnumFacing> ☃xx = Lists.<EnumFacing>newArrayList(☃, ☃.func_176746_e(), ☃.func_176735_f());
         Collections.shuffle(☃xx, ☃);

         for(EnumFacing ☃xxx : ☃xx.subList(0, ☃x)) {
            BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos(☃);
            int ☃xxxxxxx = ☃.nextInt(2) + 1;
            ☃xxxxxx.func_189536_c(☃xxx);
            int ☃xxxx;
            EnumFacing ☃xxxxx;
            if (☃xxx == ☃) {
               ☃xxxxx = ☃;
               ☃xxxx = ☃.nextInt(3) + 2;
            } else {
               ☃xxxxxx.func_189536_c(EnumFacing.UP);
               EnumFacing[] ☃xxxx = new EnumFacing[]{☃xxx, EnumFacing.UP};
               ☃xxxxx = ☃xxxx[☃.nextInt(☃xxxx.length)];
               ☃xxxx = ☃.nextInt(3) + 3;
            }

            for(int ☃xxxx = 0; ☃xxxx < ☃xxxxxxx && this.func_204624_b(☃, ☃, ☃xxxxxx, ☃); ++☃xxxx) {
               ☃xxxxxx.func_189536_c(☃xxxxx);
            }

            ☃xxxxxx.func_189536_c(☃xxxxx.func_176734_d());
            ☃xxxxxx.func_189536_c(EnumFacing.UP);

            for(int ☃xxxx = 0; ☃xxxx < ☃xxxx; ++☃xxxx) {
               ☃xxxxxx.func_189536_c(☃);
               if (!this.func_204624_b(☃, ☃, ☃xxxxxx, ☃)) {
                  break;
               }

               if (☃.nextFloat() < 0.25F) {
                  ☃xxxxxx.func_189536_c(EnumFacing.UP);
               }
            }
         }

         return true;
      }
   }
}
