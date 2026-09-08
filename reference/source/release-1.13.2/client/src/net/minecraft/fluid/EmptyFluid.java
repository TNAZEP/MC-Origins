package net.minecraft.fluid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReaderBase;

public class EmptyFluid extends Fluid {
   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.SOLID;
   }

   @Override
   public Item func_204524_b() {
      return Items.field_190931_a;
   }

   @Override
   public boolean func_211757_a(IFluidState var1, Fluid var2, EnumFacing var3) {
      return true;
   }

   @Override
   public Vec3d func_205564_a(IWorldReaderBase var1, BlockPos var2, IFluidState var3) {
      return Vec3d.field_186680_a;
   }

   @Override
   public int func_205569_a(IWorldReaderBase var1) {
      return 0;
   }

   @Override
   protected boolean func_204538_c() {
      return true;
   }

   @Override
   protected float func_210195_d() {
      return 0.0F;
   }

   @Override
   public float func_207181_a(IFluidState var1) {
      return 0.0F;
   }

   @Override
   protected IBlockState func_204527_a(IFluidState var1) {
      return Blocks.field_150350_a.func_176223_P();
   }

   @Override
   public boolean func_207193_c(IFluidState var1) {
      return false;
   }

   @Override
   public int func_207192_d(IFluidState var1) {
      return 0;
   }
}
