package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockConcretePowder extends BlockFalling {
   private final IBlockState field_200294_a;

   public BlockConcretePowder(Block var1, Block.Properties var2) {
      super(☃);
      this.field_200294_a = ☃.func_176223_P();
   }

   @Override
   public void func_176502_a_(World var1, BlockPos var2, IBlockState var3, IBlockState var4) {
      if (func_212566_x(☃)) {
         ☃.func_180501_a(☃, this.field_200294_a, 3);
      }
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      return !func_212566_x(☃.func_180495_p(☃x)) && !func_196441_b(☃, ☃x) ? super.func_196258_a(☃) : this.field_200294_a;
   }

   private static boolean func_196441_b(IBlockReader var0, BlockPos var1) {
      boolean ☃ = false;
      BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos(☃);

      for(EnumFacing ☃xx : EnumFacing.values()) {
         IBlockState ☃xxx = ☃.func_180495_p(☃x);
         if (☃xx != EnumFacing.DOWN || func_212566_x(☃xxx)) {
            ☃x.func_189533_g(☃).func_189536_c(☃xx);
            ☃xxx = ☃.func_180495_p(☃x);
            if (func_212566_x(☃xxx) && !Block.func_208061_a(☃xxx.func_196952_d(☃, ☃), ☃xx.func_176734_d())) {
               ☃ = true;
               break;
            }
         }
      }

      return ☃;
   }

   private static boolean func_212566_x(IBlockState var0) {
      return ☃.func_204520_s().func_206884_a(FluidTags.field_206959_a);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return func_196441_b(☃, ☃) ? this.field_200294_a : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }
}
