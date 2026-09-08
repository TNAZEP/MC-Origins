package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import java.util.BitSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class WorldCarver<C extends IFeatureConfig> implements IWorldCarver<C> {
   protected static final IBlockState field_202525_a = Blocks.field_150350_a.func_176223_P();
   protected static final IBlockState field_202526_b = Blocks.field_201941_jj.func_176223_P();
   protected static final IFluidState field_202527_c = Fluids.field_204546_a.func_207188_f();
   protected static final IFluidState field_202529_e = Fluids.field_204547_b.func_207188_f();
   protected Set<Block> field_202531_g = ImmutableSet.of(
      Blocks.field_150348_b,
      Blocks.field_196650_c,
      Blocks.field_196654_e,
      Blocks.field_196656_g,
      Blocks.field_150346_d,
      Blocks.field_196660_k,
      Blocks.field_196661_l,
      Blocks.field_196658_i,
      Blocks.field_150405_ch,
      Blocks.field_196777_fo,
      Blocks.field_196778_fp,
      Blocks.field_196780_fq,
      Blocks.field_196782_fr,
      Blocks.field_196783_fs,
      Blocks.field_196785_ft,
      Blocks.field_196787_fu,
      Blocks.field_196789_fv,
      Blocks.field_196791_fw,
      Blocks.field_196793_fx,
      Blocks.field_196795_fy,
      Blocks.field_196797_fz,
      Blocks.field_196719_fA,
      Blocks.field_196720_fB,
      Blocks.field_196721_fC,
      Blocks.field_196722_fD,
      Blocks.field_150322_A,
      Blocks.field_180395_cM,
      Blocks.field_150391_bh,
      Blocks.field_150433_aE,
      Blocks.field_150403_cj
   );
   protected Set<Fluid> field_204634_f = ImmutableSet.of(Fluids.field_204546_a);

   public int func_202520_b() {
      return 4;
   }

   protected abstract boolean func_202516_a(
      IWorld var1, long var2, int var4, int var5, double var6, double var8, double var10, double var12, double var14, BitSet var16
   );

   protected boolean func_202519_b(IBlockState var1) {
      return this.field_202531_g.contains(☃.func_177230_c());
   }

   protected boolean func_202517_a(IBlockState var1, IBlockState var2) {
      Block ☃ = ☃.func_177230_c();
      return this.func_202519_b(☃) || (☃ == Blocks.field_150354_m || ☃ == Blocks.field_150351_n) && !☃.func_204520_s().func_206884_a(FluidTags.field_206959_a);
   }

   protected boolean func_202524_a(IWorldReaderBase var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();

      for(int ☃x = ☃; ☃x < ☃; ++☃x) {
         for(int ☃xx = ☃; ☃xx < ☃; ++☃xx) {
            for(int ☃xxx = ☃ - 1; ☃xxx <= ☃ + 1; ++☃xxx) {
               if (this.field_204634_f.contains(☃.func_204610_c(☃.func_181079_c(☃x + ☃ * 16, ☃xxx, ☃xx + ☃ * 16)).func_206886_c())) {
                  return true;
               }

               if (☃xxx != ☃ + 1 && !this.func_202514_a(☃, ☃, ☃, ☃, ☃x, ☃xx)) {
                  ☃xxx = ☃;
               }
            }
         }
      }

      return false;
   }

   private boolean func_202514_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      return ☃ == ☃ || ☃ == ☃ - 1 || ☃ == ☃ || ☃ == ☃ - 1;
   }

   protected boolean func_202515_a(int var1, int var2, double var3, double var5, int var7, int var8, float var9) {
      double ☃ = (double)(☃ * 16 + 8);
      double ☃x = (double)(☃ * 16 + 8);
      double ☃xx = ☃ - ☃;
      double ☃xxx = ☃ - ☃x;
      double ☃xxxx = (double)(☃ - ☃);
      double ☃xxxxx = (double)(☃ + 2.0F + 16.0F);
      return ☃xx * ☃xx + ☃xxx * ☃xxx - ☃xxxx * ☃xxxx <= ☃xxxxx * ☃xxxxx;
   }
}
