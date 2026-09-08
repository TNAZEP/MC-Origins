package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockNetherWart extends BlockBush {
   public static final IntegerProperty field_176486_a = BlockStateProperties.field_208168_U;
   private static final VoxelShape[] field_196399_b = new VoxelShape[]{
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 11.0, 16.0),
      Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
   };

   protected BlockNetherWart(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176486_a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196399_b[☃.func_177229_b(field_176486_a)];
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177230_c() == Blocks.field_150425_aM;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      int ☃ = ☃.func_177229_b(field_176486_a);
      if (☃ < 3 && ☃.nextInt(10) == 0) {
         ☃ = ☃.func_206870_a(field_176486_a, Integer.valueOf(☃ + 1));
         ☃.func_180501_a(☃, ☃, 2);
      }

      super.func_196267_b(☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      if (!☃.field_72995_K) {
         int ☃ = 1;
         if (☃.func_177229_b(field_176486_a) >= 3) {
            ☃ = 2 + ☃.field_73012_v.nextInt(3);
            if (☃ > 0) {
               ☃ += ☃.field_73012_v.nextInt(☃ + 1);
            }
         }

         for(int ☃ = 0; ☃ < ☃; ++☃) {
            func_180635_a(☃, ☃, new ItemStack(Items.field_151075_bm));
         }
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return new ItemStack(Items.field_151075_bm);
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176486_a);
   }
}
