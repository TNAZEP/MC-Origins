package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.CompositeFlowerFeature;

public class BlockGrass extends BlockDirtSnowySpreadable implements IGrowable {
   public BlockGrass(Block.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return ☃.func_180495_p(☃.func_177984_a()).func_196958_f();
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockPos ☃ = ☃.func_177984_a();
      IBlockState ☃x = Blocks.field_150349_c.func_176223_P();

      label48:
      for(int ☃xx = 0; ☃xx < 128; ++☃xx) {
         BlockPos ☃xxx = ☃;

         for(int ☃xxxx = 0; ☃xxxx < ☃xx / 16; ++☃xxxx) {
            ☃xxx = ☃xxx.func_177982_a(☃.nextInt(3) - 1, (☃.nextInt(3) - 1) * ☃.nextInt(3) / 2, ☃.nextInt(3) - 1);
            if (☃.func_180495_p(☃xxx.func_177977_b()).func_177230_c() != this || ☃.func_180495_p(☃xxx).func_185898_k()) {
               continue label48;
            }
         }

         IBlockState ☃xxxx = ☃.func_180495_p(☃xxx);
         if (☃xxxx.func_177230_c() == ☃x.func_177230_c() && ☃.nextInt(10) == 0) {
            ((IGrowable)☃x.func_177230_c()).func_176474_b(☃, ☃, ☃xxx, ☃xxxx);
         }

         if (☃xxxx.func_196958_f()) {
            IBlockState ☃xxxx;
            if (☃.nextInt(8) == 0) {
               List<CompositeFlowerFeature<?>> ☃xxxxx = ☃.func_180494_b(☃xxx).func_201853_g();
               if (☃xxxxx.isEmpty()) {
                  continue;
               }

               ☃xxxx = ((CompositeFlowerFeature)☃xxxxx.get(0)).func_202354_a(☃, ☃xxx);
            } else {
               ☃xxxx = ☃x;
            }

            if (☃xxxx.func_196955_c(☃, ☃xxx)) {
               ☃.func_180501_a(☃xxx, ☃xxxx, 3);
            }
         }
      }
   }

   @Override
   public boolean func_200124_e(IBlockState var1) {
      return true;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }
}
