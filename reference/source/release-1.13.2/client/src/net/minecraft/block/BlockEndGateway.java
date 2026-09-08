package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndGateway;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockEndGateway extends BlockContainer {
   protected BlockEndGateway(Block.Properties var1) {
      super(☃);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityEndGateway();
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      TileEntity ☃ = ☃.func_175625_s(☃);
      if (☃ instanceof TileEntityEndGateway) {
         int ☃x = ((TileEntityEndGateway)☃).func_195493_h();

         for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
            double ☃xxx = (double)((float)☃.func_177958_n() + ☃.nextFloat());
            double ☃xxxx = (double)((float)☃.func_177956_o() + ☃.nextFloat());
            double ☃xxxxx = (double)((float)☃.func_177952_p() + ☃.nextFloat());
            double ☃xxxxxx = ((double)☃.nextFloat() - 0.5) * 0.5;
            double ☃xxxxxxx = ((double)☃.nextFloat() - 0.5) * 0.5;
            double ☃xxxxxxxx = ((double)☃.nextFloat() - 0.5) * 0.5;
            int ☃xxxxxxxxx = ☃.nextInt(2) * 2 - 1;
            if (☃.nextBoolean()) {
               ☃xxxxx = (double)☃.func_177952_p() + 0.5 + 0.25 * (double)☃xxxxxxxxx;
               ☃xxxxxxxx = (double)(☃.nextFloat() * 2.0F * (float)☃xxxxxxxxx);
            } else {
               ☃xxx = (double)☃.func_177958_n() + 0.5 + 0.25 * (double)☃xxxxxxxxx;
               ☃xxxxxx = (double)(☃.nextFloat() * 2.0F * (float)☃xxxxxxxxx);
            }

            ☃.func_195594_a(Particles.field_197599_J, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxx);
         }
      }
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return ItemStack.field_190927_a;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
