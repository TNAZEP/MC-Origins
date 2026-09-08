package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Particles;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWetSponge extends Block {
   protected BlockWetSponge(Block.Properties var1) {
      super(☃);
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      EnumFacing ☃ = EnumFacing.func_176741_a(☃);
      if (☃ != EnumFacing.UP && !☃.func_180495_p(☃.func_177972_a(☃)).func_185896_q()) {
         double ☃x = (double)☃.func_177958_n();
         double ☃xx = (double)☃.func_177956_o();
         double ☃xxx = (double)☃.func_177952_p();
         if (☃ == EnumFacing.DOWN) {
            ☃xx -= 0.05;
            ☃x += ☃.nextDouble();
            ☃xxx += ☃.nextDouble();
         } else {
            ☃xx += ☃.nextDouble() * 0.8;
            if (☃.func_176740_k() == EnumFacing.Axis.X) {
               ☃xxx += ☃.nextDouble();
               if (☃ == EnumFacing.EAST) {
                  ++☃x;
               } else {
                  ☃x += 0.05;
               }
            } else {
               ☃x += ☃.nextDouble();
               if (☃ == EnumFacing.SOUTH) {
                  ++☃xxx;
               } else {
                  ☃xxx += 0.05;
               }
            }
         }

         ☃.func_195594_a(Particles.field_197618_k, ☃x, ☃xx, ☃xxx, 0.0, 0.0, 0.0);
      }
   }
}
