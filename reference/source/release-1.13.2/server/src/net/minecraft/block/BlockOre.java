package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockOre extends Block {
   public BlockOre(Block.Properties var1) {
      super(☃);
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      if (this == Blocks.field_150365_q) {
         return Items.field_151044_h;
      } else if (this == Blocks.field_150482_ag) {
         return Items.field_151045_i;
      } else if (this == Blocks.field_150369_x) {
         return Items.field_196128_bn;
      } else if (this == Blocks.field_150412_bA) {
         return Items.field_151166_bC;
      } else {
         return (IItemProvider)(this == Blocks.field_196766_fg ? Items.field_151128_bU : this);
      }
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return this == Blocks.field_150369_x ? 4 + ☃.nextInt(5) : 1;
   }

   @Override
   public int func_196251_a(IBlockState var1, int var2, World var3, BlockPos var4, Random var5) {
      if (☃ > 0 && this != this.func_199769_a((IBlockState)this.func_176194_O().func_177619_a().iterator().next(), ☃, ☃, ☃)) {
         int ☃ = ☃.nextInt(☃ + 2) - 1;
         if (☃ < 0) {
            ☃ = 0;
         }

         return this.func_196264_a(☃, ☃) * (☃ + 1);
      } else {
         return this.func_196264_a(☃, ☃);
      }
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      super.func_196255_a(☃, ☃, ☃, ☃, ☃);
      if (this.func_199769_a(☃, ☃, ☃, ☃) != this) {
         int ☃ = 0;
         if (this == Blocks.field_150365_q) {
            ☃ = MathHelper.func_76136_a(☃.field_73012_v, 0, 2);
         } else if (this == Blocks.field_150482_ag) {
            ☃ = MathHelper.func_76136_a(☃.field_73012_v, 3, 7);
         } else if (this == Blocks.field_150412_bA) {
            ☃ = MathHelper.func_76136_a(☃.field_73012_v, 3, 7);
         } else if (this == Blocks.field_150369_x) {
            ☃ = MathHelper.func_76136_a(☃.field_73012_v, 2, 5);
         } else if (this == Blocks.field_196766_fg) {
            ☃ = MathHelper.func_76136_a(☃.field_73012_v, 2, 5);
         }

         this.func_180637_b(☃, ☃, ☃);
      }
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return new ItemStack(this);
   }
}
