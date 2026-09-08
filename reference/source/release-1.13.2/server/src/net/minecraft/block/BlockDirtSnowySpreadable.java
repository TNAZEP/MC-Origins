package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class BlockDirtSnowySpreadable extends BlockDirtSnowy {
   protected BlockDirtSnowySpreadable(Block.Properties var1) {
      super(☃);
   }

   private static boolean func_196383_a(IWorldReaderBase var0, BlockPos var1) {
      BlockPos ☃ = ☃.func_177984_a();
      return ☃.func_201696_r(☃) >= 4 || ☃.func_180495_p(☃).func_200016_a(☃, ☃) < ☃.func_201572_C();
   }

   private static boolean func_196384_b(IWorldReaderBase var0, BlockPos var1) {
      BlockPos ☃ = ☃.func_177984_a();
      return ☃.func_201696_r(☃) >= 4
         && ☃.func_180495_p(☃).func_200016_a(☃, ☃) < ☃.func_201572_C()
         && !☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         if (!func_196383_a(☃, ☃)) {
            ☃.func_175656_a(☃, Blocks.field_150346_d.func_176223_P());
         } else {
            if (☃.func_201696_r(☃.func_177984_a()) >= 9) {
               for(int ☃ = 0; ☃ < 4; ++☃) {
                  BlockPos ☃x = ☃.func_177982_a(☃.nextInt(3) - 1, ☃.nextInt(5) - 3, ☃.nextInt(3) - 1);
                  if (!☃.func_195588_v(☃x)) {
                     return;
                  }

                  if (☃.func_180495_p(☃x).func_177230_c() == Blocks.field_150346_d && func_196384_b(☃, ☃x)) {
                     ☃.func_175656_a(☃x, this.func_176223_P());
                  }
               }
            }
         }
      }
   }
}
