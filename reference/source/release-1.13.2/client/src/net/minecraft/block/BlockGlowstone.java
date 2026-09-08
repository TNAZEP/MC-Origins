package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockGlowstone extends Block {
   public BlockGlowstone(Block.Properties var1) {
      super(☃);
   }

   @Override
   public int func_196251_a(IBlockState var1, int var2, World var3, BlockPos var4, Random var5) {
      return MathHelper.func_76125_a(this.func_196264_a(☃, ☃) + ☃.nextInt(☃ + 1), 1, 4);
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 2 + ☃.nextInt(3);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_151114_aO;
   }
}
