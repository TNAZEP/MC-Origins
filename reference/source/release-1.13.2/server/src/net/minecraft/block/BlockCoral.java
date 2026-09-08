package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockCoral extends Block {
   private final Block field_204403_a;

   public BlockCoral(Block var1, Block.Properties var2) {
      super(☃);
      this.field_204403_a = ☃;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!this.func_203943_a(☃, ☃)) {
         ☃.func_180501_a(☃, this.field_204403_a.func_176223_P(), 2);
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (!this.func_203943_a(☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 60 + ☃.func_201674_k().nextInt(40));
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   protected boolean func_203943_a(IBlockReader var1, BlockPos var2) {
      for(EnumFacing ☃ : EnumFacing.values()) {
         IFluidState ☃x = ☃.func_204610_c(☃.func_177972_a(☃));
         if (☃x.func_206884_a(FluidTags.field_206959_a)) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      if (!this.func_203943_a(☃.func_195991_k(), ☃.func_195995_a())) {
         ☃.func_195991_k().func_205220_G_().func_205360_a(☃.func_195995_a(), this, 60 + ☃.func_195991_k().func_201674_k().nextInt(40));
      }

      return this.func_176223_P();
   }

   @Override
   protected boolean func_149700_E() {
      return true;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return this.field_204403_a;
   }
}
