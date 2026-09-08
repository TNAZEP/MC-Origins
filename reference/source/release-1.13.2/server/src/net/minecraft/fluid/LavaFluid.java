package net.minecraft.fluid;

import java.util.Random;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public abstract class LavaFluid extends FlowingFluid {
   @Override
   public Fluid func_210197_e() {
      return Fluids.field_207213_d;
   }

   @Override
   public Fluid func_210198_f() {
      return Fluids.field_204547_b;
   }

   @Override
   public Item func_204524_b() {
      return Items.field_151129_at;
   }

   @Override
   public void func_207186_b(World var1, BlockPos var2, IFluidState var3, Random var4) {
      if (☃.func_82736_K().func_82766_b("doFireTick")) {
         int ☃ = ☃.nextInt(3);
         if (☃ > 0) {
            BlockPos ☃x = ☃;

            for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
               ☃x = ☃x.func_177982_a(☃.nextInt(3) - 1, 1, ☃.nextInt(3) - 1);
               if (!☃.func_195588_v(☃x)) {
                  return;
               }

               IBlockState ☃xxx = ☃.func_180495_p(☃x);
               if (☃xxx.func_196958_f()) {
                  if (this.func_176369_e(☃, ☃x)) {
                     ☃.func_175656_a(☃x, Blocks.field_150480_ab.func_176223_P());
                     return;
                  }
               } else if (☃xxx.func_185904_a().func_76230_c()) {
                  return;
               }
            }
         } else {
            for(int ☃ = 0; ☃ < 3; ++☃) {
               BlockPos ☃x = ☃.func_177982_a(☃.nextInt(3) - 1, 0, ☃.nextInt(3) - 1);
               if (!☃.func_195588_v(☃x)) {
                  return;
               }

               if (☃.func_175623_d(☃x.func_177984_a()) && this.func_176368_m(☃, ☃x)) {
                  ☃.func_175656_a(☃x.func_177984_a(), Blocks.field_150480_ab.func_176223_P());
               }
            }
         }
      }
   }

   private boolean func_176369_e(IWorldReaderBase var1, BlockPos var2) {
      for(EnumFacing ☃ : EnumFacing.values()) {
         if (this.func_176368_m(☃, ☃.func_177972_a(☃))) {
            return true;
         }
      }

      return false;
   }

   private boolean func_176368_m(IWorldReaderBase var1, BlockPos var2) {
      return ☃.func_177956_o() >= 0 && ☃.func_177956_o() < 256 && !☃.func_175667_e(☃) ? false : ☃.func_180495_p(☃).func_185904_a().func_76217_h();
   }

   @Override
   protected void func_205580_a(IWorld var1, BlockPos var2, IBlockState var3) {
      this.func_205581_a(☃, ☃);
   }

   @Override
   public int func_185698_b(IWorldReaderBase var1) {
      return ☃.func_201675_m().func_177500_n() ? 4 : 2;
   }

   @Override
   public IBlockState func_204527_a(IFluidState var1) {
      return Blocks.field_150353_l.func_176223_P().func_206870_a(BlockFlowingFluid.field_176367_b, Integer.valueOf(func_207205_e(☃)));
   }

   @Override
   public boolean func_207187_a(Fluid var1) {
      return ☃ == Fluids.field_204547_b || ☃ == Fluids.field_207213_d;
   }

   @Override
   public int func_204528_b(IWorldReaderBase var1) {
      return ☃.func_201675_m().func_177500_n() ? 1 : 2;
   }

   @Override
   public boolean func_211757_a(IFluidState var1, Fluid var2, EnumFacing var3) {
      return ☃.func_206885_f() >= 0.44444445F && ☃.func_207185_a(FluidTags.field_206959_a);
   }

   @Override
   public int func_205569_a(IWorldReaderBase var1) {
      return ☃.func_201675_m().func_177495_o() ? 10 : 30;
   }

   @Override
   public int func_205578_a(World var1, IFluidState var2, IFluidState var3) {
      int ☃ = this.func_205569_a(☃);
      if (!☃.func_206888_e()
         && !☃.func_206888_e()
         && !☃.func_177229_b(field_207209_a)
         && !☃.func_177229_b(field_207209_a)
         && ☃.func_206885_f() > ☃.func_206885_f()
         && ☃.func_201674_k().nextInt(4) != 0) {
         ☃ *= 4;
      }

      return ☃;
   }

   protected void func_205581_a(IWorld var1, BlockPos var2) {
      double ☃ = (double)☃.func_177958_n();
      double ☃x = (double)☃.func_177956_o();
      double ☃xx = (double)☃.func_177952_p();
      ☃.func_184133_a(
         null, ☃, SoundEvents.field_187659_cY, SoundCategory.BLOCKS, 0.5F, 2.6F + (☃.func_201674_k().nextFloat() - ☃.func_201674_k().nextFloat()) * 0.8F
      );

      for(int ☃xxx = 0; ☃xxx < 8; ++☃xxx) {
         ☃.func_195594_a(Particles.field_197594_E, ☃ + Math.random(), ☃x + 1.2, ☃xx + Math.random(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected boolean func_205579_d() {
      return false;
   }

   @Override
   protected void func_205574_a(IWorld var1, BlockPos var2, IBlockState var3, EnumFacing var4, IFluidState var5) {
      if (☃ == EnumFacing.DOWN) {
         IFluidState ☃ = ☃.func_204610_c(☃);
         if (this.func_207185_a(FluidTags.field_206960_b) && ☃.func_206884_a(FluidTags.field_206959_a)) {
            if (☃.func_177230_c() instanceof BlockFlowingFluid) {
               ☃.func_180501_a(☃, Blocks.field_150348_b.func_176223_P(), 3);
            }

            this.func_205581_a(☃, ☃);
            return;
         }
      }

      super.func_205574_a(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   protected boolean func_207196_h() {
      return true;
   }

   @Override
   protected float func_210195_d() {
      return 100.0F;
   }

   public static class Flowing extends LavaFluid {
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

   public static class Source extends LavaFluid {
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
