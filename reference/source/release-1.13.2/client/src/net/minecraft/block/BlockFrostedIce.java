package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockFrostedIce extends BlockIce {
   public static final IntegerProperty field_185682_a = BlockStateProperties.field_208168_U;

   public BlockFrostedIce(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_185682_a, Integer.valueOf(0)));
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if ((☃.nextInt(3) == 0 || this.func_196456_a(☃, ☃, 4))
         && ☃.func_201696_r(☃) > 11 - ☃.func_177229_b(field_185682_a) - ☃.func_200016_a(☃, ☃)
         && this.func_196455_e(☃, ☃, ☃)) {
         try (BlockPos.PooledMutableBlockPos ☃ = BlockPos.PooledMutableBlockPos.func_185346_s()) {
            for(EnumFacing ☃x : EnumFacing.values()) {
               ☃.func_189533_g(☃).func_189536_c(☃x);
               IBlockState ☃xx = ☃.func_180495_p(☃);
               if (☃xx.func_177230_c() == this && !this.func_196455_e(☃xx, ☃, ☃)) {
                  ☃.func_205220_G_().func_205360_a(☃, this, MathHelper.func_76136_a(☃, 20, 40));
               }
            }
         }
      } else {
         ☃.func_205220_G_().func_205360_a(☃, this, MathHelper.func_76136_a(☃, 20, 40));
      }
   }

   private boolean func_196455_e(IBlockState var1, World var2, BlockPos var3) {
      int ☃ = ☃.func_177229_b(field_185682_a);
      if (☃ < 3) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_185682_a, Integer.valueOf(☃ + 1)), 2);
         return false;
      } else {
         this.func_196454_d(☃, ☃, ☃);
         return true;
      }
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (☃ == this && this.func_196456_a(☃, ☃, 2)) {
         this.func_196454_d(☃, ☃, ☃);
      }

      super.func_189540_a(☃, ☃, ☃, ☃, ☃);
   }

   private boolean func_196456_a(IBlockReader var1, BlockPos var2, int var3) {
      int ☃ = 0;

      try (BlockPos.PooledMutableBlockPos ☃x = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         for(EnumFacing ☃xx : EnumFacing.values()) {
            ☃x.func_189533_g(☃).func_189536_c(☃xx);
            if (☃.func_180495_p(☃x).func_177230_c() == this) {
               if (++☃ >= ☃) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185682_a);
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return ItemStack.field_190927_a;
   }
}
