package net.minecraft.fluid;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class WaterFluid extends FlowingFluid {
   @Override
   public Fluid func_210197_e() {
      return Fluids.field_207212_b;
   }

   @Override
   public Fluid func_210198_f() {
      return Fluids.field_204546_a;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public Item func_204524_b() {
      return Items.field_151131_as;
   }

   @Override
   public void func_204522_a(World var1, BlockPos var2, IFluidState var3, Random var4) {
      if (!☃.func_206889_d() && !☃.func_177229_b(field_207209_a)) {
         if (☃.nextInt(64) == 0) {
            ☃.func_184134_a(
               (double)☃.func_177958_n() + 0.5,
               (double)☃.func_177956_o() + 0.5,
               (double)☃.func_177952_p() + 0.5,
               SoundEvents.field_187917_gq,
               SoundCategory.BLOCKS,
               ☃.nextFloat() * 0.25F + 0.75F,
               ☃.nextFloat() + 0.5F,
               false
            );
         }
      } else if (☃.nextInt(10) == 0) {
         ☃.func_195594_a(
            Particles.field_197605_P,
            (double)((float)☃.func_177958_n() + ☃.nextFloat()),
            (double)((float)☃.func_177956_o() + ☃.nextFloat()),
            (double)((float)☃.func_177952_p() + ☃.nextFloat()),
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Nullable
   @Override
   public IParticleData func_204521_c() {
      return Particles.field_197618_k;
   }

   @Override
   protected boolean func_205579_d() {
      return true;
   }

   @Override
   protected void func_205580_a(IWorld var1, BlockPos var2, IBlockState var3) {
      ☃.func_196949_c(☃.func_201672_e(), ☃, 0);
   }

   @Override
   public int func_185698_b(IWorldReaderBase var1) {
      return 4;
   }

   @Override
   public IBlockState func_204527_a(IFluidState var1) {
      return Blocks.field_150355_j.func_176223_P().func_206870_a(BlockFlowingFluid.field_176367_b, Integer.valueOf(func_207205_e(☃)));
   }

   @Override
   public boolean func_207187_a(Fluid var1) {
      return ☃ == Fluids.field_204546_a || ☃ == Fluids.field_207212_b;
   }

   @Override
   public int func_204528_b(IWorldReaderBase var1) {
      return 1;
   }

   @Override
   public int func_205569_a(IWorldReaderBase var1) {
      return 5;
   }

   @Override
   public boolean func_211757_a(IFluidState var1, Fluid var2, EnumFacing var3) {
      return ☃ == EnumFacing.DOWN && !☃.func_207185_a(FluidTags.field_206959_a);
   }

   @Override
   protected float func_210195_d() {
      return 100.0F;
   }

   public static class Flowing extends WaterFluid {
      @Override
      protected void func_207184_a(StateContainer.Builder<Fluid, IFluidState> var1) {
         super.func_207184_a(☃);
         ☃.func_206894_a(field_207210_b);
      }

      @Override
      public int func_207192_d(IFluidState var1) {
         return ☃.func_177229_b(field_207210_b);
      }

      @Override
      public boolean func_207193_c(IFluidState var1) {
         return false;
      }
   }

   public static class Source extends WaterFluid {
      @Override
      public int func_207192_d(IFluidState var1) {
         return 8;
      }

      @Override
      public boolean func_207193_c(IFluidState var1) {
         return true;
      }
   }
}
