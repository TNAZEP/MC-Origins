package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import java.util.BitSet;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class NetherCaveWorldCarver extends CaveWorldCarver {
   public NetherCaveWorldCarver() {
      this.field_202531_g = ImmutableSet.of(
         Blocks.field_150348_b,
         Blocks.field_196650_c,
         Blocks.field_196654_e,
         Blocks.field_196656_g,
         Blocks.field_150346_d,
         Blocks.field_196660_k,
         Blocks.field_196661_l,
         Blocks.field_196658_i,
         Blocks.field_150424_aL
      );
      this.field_204634_f = ImmutableSet.of(Fluids.field_204547_b, Fluids.field_204546_a);
   }

   @Override
   public boolean func_212246_a(IBlockReader var1, Random var2, int var3, int var4, ProbabilityConfig var5) {
      return ☃.nextFloat() <= ☃.field_203622_a;
   }

   @Override
   public boolean func_202522_a(IWorld var1, Random var2, int var3, int var4, int var5, int var6, BitSet var7, ProbabilityConfig var8) {
      int ☃ = (this.func_202520_b() * 2 - 1) * 16;
      int ☃x = ☃.nextInt(☃.nextInt(☃.nextInt(10) + 1) + 1);

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         double ☃xxx = (double)(☃ * 16 + ☃.nextInt(16));
         double ☃xxxx = (double)☃.nextInt(128);
         double ☃xxxxx = (double)(☃ * 16 + ☃.nextInt(16));
         int ☃xxxxxx = 1;
         if (☃.nextInt(4) == 0) {
            double ☃xxxxxxx = 0.5;
            float ☃xxxxxxxx = 1.0F + ☃.nextFloat() * 6.0F;
            this.func_203627_a(☃, ☃.nextLong(), ☃, ☃, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxxxx, 0.5, ☃);
            ☃xxxxxx += ☃.nextInt(4);
         }

         for(int ☃xxx = 0; ☃xxx < ☃xxxxxx; ++☃xxx) {
            float ☃xxxx = ☃.nextFloat() * (float) (Math.PI * 2);
            float ☃xxxxx = (☃.nextFloat() - 0.5F) * 2.0F / 8.0F;
            double ☃xxxxxx = 5.0;
            float ☃xxxxxxx = (☃.nextFloat() * 2.0F + ☃.nextFloat()) * 2.0F;
            int ☃xxxxxxxx = ☃ - ☃.nextInt(☃ / 4);
            int ☃xxxxxxxxx = 0;
            this.func_202533_a(☃, ☃.nextLong(), ☃, ☃, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxxx, ☃xxxx, ☃xxxxx, 0, ☃xxxxxxxx, 5.0, ☃);
         }
      }

      return true;
   }

   @Override
   protected boolean func_202516_a(IWorld var1, long var2, int var4, int var5, double var6, double var8, double var10, double var12, double var14, BitSet var16) {
      double ☃ = (double)(☃ * 16 + 8);
      double ☃x = (double)(☃ * 16 + 8);
      if (!(☃ < ☃ - 16.0 - ☃ * 2.0) && !(☃ < ☃x - 16.0 - ☃ * 2.0) && !(☃ > ☃ + 16.0 + ☃ * 2.0) && !(☃ > ☃x + 16.0 + ☃ * 2.0)) {
         int ☃xx = Math.max(MathHelper.func_76128_c(☃ - ☃) - ☃ * 16 - 1, 0);
         int ☃xxx = Math.min(MathHelper.func_76128_c(☃ + ☃) - ☃ * 16 + 1, 16);
         int ☃xxxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - 1, 1);
         int ☃xxxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) + 1, 120);
         int ☃xxxxxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - ☃ * 16 - 1, 0);
         int ☃xxxxxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) - ☃ * 16 + 1, 16);
         if (this.func_202524_a(☃, ☃, ☃, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx)) {
            return false;
         } else if (☃xx <= ☃xxx && ☃xxxx <= ☃xxxxx && ☃xxxxxx <= ☃xxxxxxx) {
            boolean ☃xx = false;

            for(int ☃xxx = ☃xx; ☃xxx < ☃xxx; ++☃xxx) {
               int ☃xxxx = ☃xxx + ☃ * 16;
               double ☃xxxxx = ((double)☃xxxx + 0.5 - ☃) / ☃;

               for(int ☃xxxxxx = ☃xxxxxx; ☃xxxxxx < ☃xxxxxxx; ++☃xxxxxx) {
                  int ☃xxxxxxx = ☃xxxxxx + ☃ * 16;
                  double ☃xxxxxxxx = ((double)☃xxxxxxx + 0.5 - ☃) / ☃;

                  for(int ☃xxxxxxxxx = ☃xxxxx; ☃xxxxxxxxx > ☃xxxx; --☃xxxxxxxxx) {
                     double ☃xxxxxxxxxx = ((double)(☃xxxxxxxxx - 1) + 0.5 - ☃) / ☃;
                     if (☃xxxxxxxxxx > -0.7 && ☃xxxxx * ☃xxxxx + ☃xxxxxxxxxx * ☃xxxxxxxxxx + ☃xxxxxxxx * ☃xxxxxxxx < 1.0) {
                        int ☃xxxxxxxxxxx = ☃xxx | ☃xxxxxx << 4 | ☃xxxxxxxxx << 8;
                        if (!☃.get(☃xxxxxxxxxxx)) {
                           ☃.set(☃xxxxxxxxxxx);
                           if (this.func_202519_b(☃.func_180495_p(new BlockPos(☃xxxx, ☃xxxxxxxxx, ☃xxxxxxx)))) {
                              if (☃xxxxxxxxx <= 31) {
                                 ☃.func_180501_a(new BlockPos(☃xxxx, ☃xxxxxxxxx, ☃xxxxxxx), field_202529_e.func_206883_i(), 2);
                              } else {
                                 ☃.func_180501_a(new BlockPos(☃xxxx, ☃xxxxxxxxx, ☃xxxxxxx), field_202526_b, 2);
                              }

                              ☃xx = true;
                           }
                        }
                     }
                  }
               }
            }

            return ☃xx;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
