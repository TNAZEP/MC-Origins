package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class MesaForestSurfaceBuilder extends MesaSurfaceBuilder {
   private static final IBlockState field_202627_f = Blocks.field_196777_fo.func_176223_P();
   private static final IBlockState field_202628_g = Blocks.field_196778_fp.func_176223_P();
   private static final IBlockState field_202629_h = Blocks.field_150405_ch.func_176223_P();

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
      int ☃ = ☃ & 15;
      int ☃x = ☃ & 15;
      IBlockState ☃xx = field_202627_f;
      IBlockState ☃xxx = ☃.func_203944_q().func_204109_b();
      int ☃xxxx = (int)(☃ / 3.0 + 3.0 + ☃.nextDouble() * 0.25);
      boolean ☃xxxxx = Math.cos(☃ / 3.0 * Math.PI) > 0.0;
      int ☃xxxxxx = -1;
      boolean ☃xxxxxxx = false;
      int ☃xxxxxxxx = 0;
      BlockPos.MutableBlockPos ☃xxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxxxxx = ☃; ☃xxxxxxxxxx >= 0; --☃xxxxxxxxxx) {
         if (☃xxxxxxxx < 15) {
            ☃xxxxxxxxx.func_181079_c(☃, ☃xxxxxxxxxx, ☃x);
            IBlockState ☃xxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxx);
            if (☃xxxxxxxxxxx.func_196958_f()) {
               ☃xxxxxx = -1;
            } else if (☃xxxxxxxxxxx.func_177230_c() == ☃.func_177230_c()) {
               if (☃xxxxxx == -1) {
                  ☃xxxxxxx = false;
                  if (☃xxxx <= 0) {
                     ☃xx = Blocks.field_150350_a.func_176223_P();
                     ☃xxx = ☃;
                  } else if (☃xxxxxxxxxx >= ☃ - 4 && ☃xxxxxxxxxx <= ☃ + 1) {
                     ☃xx = field_202627_f;
                     ☃xxx = ☃.func_203944_q().func_204109_b();
                  }

                  if (☃xxxxxxxxxx < ☃ && (☃xx == null || ☃xx.func_196958_f())) {
                     ☃xx = ☃;
                  }

                  ☃xxxxxx = ☃xxxx + Math.max(0, ☃xxxxxxxxxx - ☃);
                  if (☃xxxxxxxxxx < ☃ - 1) {
                     ☃.func_177436_a(☃xxxxxxxxx, ☃xxx, false);
                     if (☃xxx.func_177230_c() == field_202627_f) {
                        ☃.func_177436_a(☃xxxxxxxxx, field_202628_g, false);
                     }
                  } else if (☃xxxxxxxxxx > 86 + ☃xxxx * 2) {
                     if (☃xxxxx) {
                        ☃.func_177436_a(☃xxxxxxxxx, Blocks.field_196660_k.func_176223_P(), false);
                     } else {
                        ☃.func_177436_a(☃xxxxxxxxx, Blocks.field_196658_i.func_176223_P(), false);
                     }
                  } else if (☃xxxxxxxxxx <= ☃ + 3 + ☃xxxx) {
                     ☃.func_177436_a(☃xxxxxxxxx, ☃.func_203944_q().func_204108_a(), false);
                     ☃xxxxxxx = true;
                  } else {
                     IBlockState ☃xxxxxxxxxxx;
                     if (☃xxxxxxxxxx < 64 || ☃xxxxxxxxxx > 127) {
                        ☃xxxxxxxxxxx = field_202628_g;
                     } else if (☃xxxxx) {
                        ☃xxxxxxxxxxx = field_202629_h;
                     } else {
                        ☃xxxxxxxxxxx = this.func_202614_a(☃, ☃xxxxxxxxxx, ☃);
                     }

                     ☃.func_177436_a(☃xxxxxxxxx, ☃xxxxxxxxxxx, false);
                  }
               } else if (☃xxxxxx > 0) {
                  --☃xxxxxx;
                  if (☃xxxxxxx) {
                     ☃.func_177436_a(☃xxxxxxxxx, field_202628_g, false);
                  } else {
                     ☃.func_177436_a(☃xxxxxxxxx, this.func_202614_a(☃, ☃xxxxxxxxxx, ☃), false);
                  }
               }

               ++☃xxxxxxxx;
            }
         }
      }
   }
}
