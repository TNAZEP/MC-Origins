package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockSlime extends BlockBreakable {
   public BlockSlime(Block.Properties var1) {
      super(☃);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public void func_180658_a(World var1, BlockPos var2, Entity var3, float var4) {
      if (☃.func_70093_af()) {
         super.func_180658_a(☃, ☃, ☃, ☃);
      } else {
         ☃.func_180430_e(☃, 0.0F);
      }
   }

   @Override
   public void func_176216_a(IBlockReader var1, Entity var2) {
      if (☃.func_70093_af()) {
         super.func_176216_a(☃, ☃);
      } else if (☃.field_70181_x < 0.0) {
         ☃.field_70181_x = -☃.field_70181_x;
         if (!(☃ instanceof EntityLivingBase)) {
            ☃.field_70181_x *= 0.8;
         }
      }
   }

   @Override
   public void func_176199_a(World var1, BlockPos var2, Entity var3) {
      if (Math.abs(☃.field_70181_x) < 0.1 && !☃.func_70093_af()) {
         double ☃ = 0.4 + Math.abs(☃.field_70181_x) * 0.2;
         ☃.field_70159_w *= ☃;
         ☃.field_70179_y *= ☃;
      }

      super.func_176199_a(☃, ☃, ☃);
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return 0;
   }
}
