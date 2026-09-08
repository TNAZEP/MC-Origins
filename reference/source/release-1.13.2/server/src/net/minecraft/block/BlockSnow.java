package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.World;

public class BlockSnow extends Block {
   protected BlockSnow(Block.Properties var1) {
      super(☃);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_151126_ay;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 4;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_175642_b(EnumLightType.BLOCK, ☃) > 11) {
         ☃.func_196949_c(☃, ☃, 0);
         ☃.func_175698_g(☃);
      }
   }
}
