package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class MesaBryceSurfaceBuilder extends MesaSurfaceBuilder {
   private static final IBlockState field_202634_f = Blocks.field_196777_fo.func_176223_P();
   private static final IBlockState field_202635_g = Blocks.field_196778_fp.func_176223_P();
   private static final IBlockState field_202636_h = Blocks.field_150405_ch.func_176223_P();

   @Override
   public void func_205610_a_(
      Random var1,
      IChunk var2,
      Biome var3,
      int var4,
      int var5,
      int var6,
      double var7,
      IBlockState var9,
      IBlockState var10,
      int var11,
      long var12,
      SurfaceBuilderConfig var14
   ) {
      double ☃ = 0.0;
      double ☃x = Math.min(Math.abs(☃), this.field_202617_c.func_151601_a((double)☃ * 0.25, (double)☃ * 0.25));
      if (☃x > 0.0) {
         double ☃xx = 0.001953125;
         double ☃xxx = Math.abs(this.field_202618_d.func_151601_a((double)☃ * 0.001953125, (double)☃ * 0.001953125));
         ☃ = ☃x * ☃x * 2.5;
         double ☃xxxx = Math.ceil(☃xxx * 50.0) + 14.0;
         if (☃ > ☃xxxx) {
            ☃ = ☃xxxx;
         }

         ☃ += 64.0;
      }

      int ☃ = ☃ & 15;
      int ☃x = ☃ & 15;
      IBlockState ☃xx = field_202634_f;
      IBlockState ☃xxx = ☃.func_203944_q().func_204109_b();
      int ☃xxxx = (int)(☃ / 3.0 + 3.0 + ☃.nextDouble() * 0.25);
      boolean ☃xxxxx = Math.cos(☃ / 3.0 * Math.PI) > 0.0;
      int ☃xxxxxx = -1;
      boolean ☃xxxxxxx = false;
      BlockPos.MutableBlockPos ☃xxxxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxxxx = Math.max(☃, (int)☃ + 1); ☃xxxxxxxxx >= 0; --☃xxxxxxxxx) {
         ☃xxxxxxxx.func_181079_c(☃, ☃xxxxxxxxx, ☃x);
         if (☃.func_180495_p(☃xxxxxxxx).func_196958_f() && ☃xxxxxxxxx < (int)☃) {
            ☃.func_177436_a(☃xxxxxxxx, ☃, false);
         }

         IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
         if (☃xxxxxxxxxx.func_196958_f()) {
            ☃xxxxxx = -1;
         } else if (☃xxxxxxxxxx.func_177230_c() == ☃.func_177230_c()) {
            if (☃xxxxxx == -1) {
               ☃xxxxxxx = false;
               if (☃xxxx <= 0) {
                  ☃xx = Blocks.field_150350_a.func_176223_P();
                  ☃xxx = ☃;
               } else if (☃xxxxxxxxx >= ☃ - 4 && ☃xxxxxxxxx <= ☃ + 1) {
                  ☃xx = field_202634_f;
                  ☃xxx = ☃.func_203944_q().func_204109_b();
               }

               if (☃xxxxxxxxx < ☃ && (☃xx == null || ☃xx.func_196958_f())) {
                  ☃xx = ☃;
               }

               ☃xxxxxx = ☃xxxx + Math.max(0, ☃xxxxxxxxx - ☃);
               if (☃xxxxxxxxx >= ☃ - 1) {
                  if (☃xxxxxxxxx > ☃ + 3 + ☃xxxx) {
                     IBlockState ☃xxxxxxxxxx;
                     if (☃xxxxxxxxx < 64 || ☃xxxxxxxxx > 127) {
                        ☃xxxxxxxxxx = field_202635_g;
                     } else if (☃xxxxx) {
                        ☃xxxxxxxxxx = field_202636_h;
                     } else {
                        ☃xxxxxxxxxx = this.func_202614_a(☃, ☃xxxxxxxxx, ☃);
                     }

                     ☃.func_177436_a(☃xxxxxxxx, ☃xxxxxxxxxx, false);
                  } else {
                     ☃.func_177436_a(☃xxxxxxxx, ☃.func_203944_q().func_204108_a(), false);
                     ☃xxxxxxx = true;
                  }
               } else {
                  ☃.func_177436_a(☃xxxxxxxx, ☃xxx, false);
                  Block ☃xxxxxxxxxx = ☃xxx.func_177230_c();
                  if (☃xxxxxxxxxx == Blocks.field_196777_fo
                     || ☃xxxxxxxxxx == Blocks.field_196778_fp
                     || ☃xxxxxxxxxx == Blocks.field_196780_fq
                     || ☃xxxxxxxxxx == Blocks.field_196782_fr
                     || ☃xxxxxxxxxx == Blocks.field_196783_fs
                     || ☃xxxxxxxxxx == Blocks.field_196785_ft
                     || ☃xxxxxxxxxx == Blocks.field_196787_fu
                     || ☃xxxxxxxxxx == Blocks.field_196789_fv
                     || ☃xxxxxxxxxx == Blocks.field_196791_fw
                     || ☃xxxxxxxxxx == Blocks.field_196793_fx
                     || ☃xxxxxxxxxx == Blocks.field_196795_fy
                     || ☃xxxxxxxxxx == Blocks.field_196797_fz
                     || ☃xxxxxxxxxx == Blocks.field_196719_fA
                     || ☃xxxxxxxxxx == Blocks.field_196720_fB
                     || ☃xxxxxxxxxx == Blocks.field_196721_fC
                     || ☃xxxxxxxxxx == Blocks.field_196722_fD) {
                     ☃.func_177436_a(☃xxxxxxxx, field_202635_g, false);
                  }
               }
            } else if (☃xxxxxx > 0) {
               --☃xxxxxx;
               if (☃xxxxxxx) {
                  ☃.func_177436_a(☃xxxxxxxx, field_202635_g, false);
               } else {
                  ☃.func_177436_a(☃xxxxxxxx, this.func_202614_a(☃, ☃xxxxxxxxx, ☃), false);
               }
            }
         }
      }
   }
}
