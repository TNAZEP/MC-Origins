package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRedstoneLamp extends Block {
   public static final BooleanProperty field_196502_a = BlockRedstoneTorch.field_196528_a;

   public BlockRedstoneLamp(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.func_176223_P().func_206870_a(field_196502_a, Boolean.valueOf(false)));
   }

   @Override
   public int func_149750_m(IBlockState var1) {
      return ☃.func_177229_b(field_196502_a) ? super.func_149750_m(☃) : 0;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      super.func_196259_b(☃, ☃, ☃, ☃);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_176223_P().func_206870_a(field_196502_a, Boolean.valueOf(☃.func_195991_k().func_175640_z(☃.func_195995_a())));
   }

   @Override
   public void func_189540_a(IBlockState var1, World var2, BlockPos var3, Block var4, BlockPos var5) {
      if (!☃.field_72995_K) {
         boolean ☃ = ☃.func_177229_b(field_196502_a);
         if (☃ != ☃.func_175640_z(☃)) {
            if (☃) {
               ☃.func_205220_G_().func_205360_a(☃, this, 4);
            } else {
               ☃.func_180501_a(☃, ☃.func_177231_a(field_196502_a), 2);
            }
         }
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.field_72995_K) {
         if (☃.func_177229_b(field_196502_a) && !☃.func_175640_z(☃)) {
            ☃.func_180501_a(☃, ☃.func_177231_a(field_196502_a), 2);
         }
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196502_a);
   }
}
