package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import java.util.BitSet;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class UnderwaterCanyonWorldCarver extends CanyonWorldCarver {
   private final float[] field_203628_i = new float[1024];

   public UnderwaterCanyonWorldCarver() {
      this.field_202531_g = ImmutableSet.of(
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
         Blocks.field_150354_m,
         Blocks.field_150351_n,
         Blocks.field_150355_j,
         Blocks.field_150353_l,
         Blocks.field_150343_Z,
         Blocks.field_150350_a,
         Blocks.field_201941_jj
      );
   }

   @Override
   public boolean func_212246_a(IBlockReader var1, Random var2, int var3, int var4, ProbabilityConfig var5) {
      return ☃.nextFloat() <= ☃.field_203622_a;
   }

   @Override
   protected boolean func_202516_a(IWorld var1, long var2, int var4, int var5, double var6, double var8, double var10, double var12, double var14, BitSet var16) {
      Random ☃ = new Random(☃ + (long)☃ + (long)☃);
      double ☃x = (double)(☃ * 16 + 8);
      double ☃xx = (double)(☃ * 16 + 8);
      if (!(☃ < ☃x - 16.0 - ☃ * 2.0) && !(☃ < ☃xx - 16.0 - ☃ * 2.0) && !(☃ > ☃x + 16.0 + ☃ * 2.0) && !(☃ > ☃xx + 16.0 + ☃ * 2.0)) {
         int ☃xxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - ☃ * 16 - 1, 0);
         int ☃xxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) - ☃ * 16 + 1, 16);
         int ☃xxxxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - 1, 1);
         int ☃xxxxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) + 1, 248);
         int ☃xxxxxxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - ☃ * 16 - 1, 0);
         int ☃xxxxxxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) - ☃ * 16 + 1, 16);
         if (☃xxx <= ☃xxxx && ☃xxxxx <= ☃xxxxxx && ☃xxxxxxx <= ☃xxxxxxxx) {
            boolean ☃xxxxxxxxx = false;
            BlockPos.MutableBlockPos ☃xxxxxxxxxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxxxxxxxxx = ☃xxx; ☃xxxxxxxxxxx < ☃xxxx; ++☃xxxxxxxxxxx) {
               int ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx + ☃ * 16;
               double ☃xxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxx + 0.5 - ☃) / ☃;

               for(int ☃xxxxxxxxxxxxxx = ☃xxxxxxx; ☃xxxxxxxxxxxxxx < ☃xxxxxxxx; ++☃xxxxxxxxxxxxxx) {
                  int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx + ☃ * 16;
                  double ☃xxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxx + 0.5 - ☃) / ☃;
                  if (☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx < 1.0) {
                     for(int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxxxxxxxxxx > ☃xxxxx; --☃xxxxxxxxxxxxxxxxx) {
                        double ☃xxxxxxxxxxxxxxxxxx = ((double)(☃xxxxxxxxxxxxxxxxx - 1) + 0.5 - ☃) / ☃;
                        if ((☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx) * (double)this.field_203628_i[☃xxxxxxxxxxxxxxxxx - 1]
                                 + ☃xxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxx / 6.0
                              < 1.0
                           && ☃xxxxxxxxxxxxxxxxx < ☃.func_181545_F()) {
                           int ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx | ☃xxxxxxxxxxxxxx << 4 | ☃xxxxxxxxxxxxxxxxx << 8;
                           if (!☃.get(☃xxxxxxxxxxxxxxxxxxx)) {
                              ☃.set(☃xxxxxxxxxxxxxxxxxxx);
                              ☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx);
                              IBlockState ☃xxxxxxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxx);
                              if (this.func_202519_b(☃xxxxxxxxxxxxxxxxxxxx)) {
                                 if (☃xxxxxxxxxxxxxxxxx == 10) {
                                    float ☃xxxxxxxxxxxxxxxxxxxxx = ☃.nextFloat();
                                    if ((double)☃xxxxxxxxxxxxxxxxxxxxx < 0.25) {
                                       ☃.func_180501_a(☃xxxxxxxxxx, Blocks.field_196814_hQ.func_176223_P(), 2);
                                       ☃.func_205220_G_().func_205360_a(☃xxxxxxxxxx, Blocks.field_196814_hQ, 0);
                                       ☃xxxxxxxxx = true;
                                    } else {
                                       ☃.func_180501_a(☃xxxxxxxxxx, Blocks.field_150343_Z.func_176223_P(), 2);
                                       ☃xxxxxxxxx = true;
                                    }
                                 } else if (☃xxxxxxxxxxxxxxxxx < 10) {
                                    ☃.func_180501_a(☃xxxxxxxxxx, Blocks.field_150353_l.func_176223_P(), 2);
                                 } else {
                                    boolean ☃xxxxxxxxxxxxxxxxxxxxx = false;

                                    for(EnumFacing ☃xxxxxxxxxxxxxxxxxxxxxx : EnumFacing.Plane.HORIZONTAL) {
                                       IBlockState ☃xxxxxxxxxxxxxxxxxxxxxxx = ☃.func_180495_p(
                                          ☃xxxxxxxxxx.func_181079_c(
                                             ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxx.func_82601_c(),
                                             ☃xxxxxxxxxxxxxxxxx,
                                             ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxxxx.func_82599_e()
                                          )
                                       );
                                       if (☃xxxxxxxxxxxxxxxxxxxxxxx.func_196958_f()) {
                                          ☃.func_180501_a(☃xxxxxxxxxx, field_202527_c.func_206883_i(), 2);
                                          ☃.func_205219_F_().func_205360_a(☃xxxxxxxxxx, field_202527_c.func_206886_c(), 0);
                                          ☃xxxxxxxxx = true;
                                          ☃xxxxxxxxxxxxxxxxxxxxx = true;
                                          break;
                                       }
                                    }

                                    ☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx);
                                    if (!☃xxxxxxxxxxxxxxxxxxxxx) {
                                       ☃.func_180501_a(☃xxxxxxxxxx, field_202527_c.func_206883_i(), 2);
                                       ☃xxxxxxxxx = true;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            return ☃xxxxxxxxx;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
