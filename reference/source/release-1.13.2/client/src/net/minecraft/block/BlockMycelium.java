package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Particles;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMycelium extends BlockDirtSnowySpreadable {
   public BlockMycelium(Block.Properties var1) {
      super(☃);
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      super.func_180655_c(☃, ☃, ☃, ☃);
      if (☃.nextInt(10) == 0) {
         ☃.func_195594_a(
            Particles.field_197596_G,
            (double)((float)☃.func_177958_n() + ☃.nextFloat()),
            (double)((float)☃.func_177956_o() + 1.1F),
            (double)((float)☃.func_177952_p() + ☃.nextFloat()),
            0.0,
            0.0,
            0.0
         );
      }
   }
}
