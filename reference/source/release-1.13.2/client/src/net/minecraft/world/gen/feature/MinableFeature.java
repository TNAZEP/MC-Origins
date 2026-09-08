package net.minecraft.world.gen.feature;

import java.util.BitSet;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class MinableFeature extends Feature<MinableConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, MinableConfig var5) {
      float ☃ = ☃.nextFloat() * (float) Math.PI;
      float ☃x = (float)☃.field_202443_c / 8.0F;
      int ☃xx = MathHelper.func_76123_f(((float)☃.field_202443_c / 16.0F * 2.0F + 1.0F) / 2.0F);
      double ☃xxx = (double)((float)☃.func_177958_n() + MathHelper.func_76126_a(☃) * ☃x);
      double ☃xxxx = (double)((float)☃.func_177958_n() - MathHelper.func_76126_a(☃) * ☃x);
      double ☃xxxxx = (double)((float)☃.func_177952_p() + MathHelper.func_76134_b(☃) * ☃x);
      double ☃xxxxxx = (double)((float)☃.func_177952_p() - MathHelper.func_76134_b(☃) * ☃x);
      int ☃xxxxxxx = 2;
      double ☃xxxxxxxx = (double)(☃.func_177956_o() + ☃.nextInt(3) - 2);
      double ☃xxxxxxxxx = (double)(☃.func_177956_o() + ☃.nextInt(3) - 2);
      int ☃xxxxxxxxxx = ☃.func_177958_n() - MathHelper.func_76123_f(☃x) - ☃xx;
      int ☃xxxxxxxxxxx = ☃.func_177956_o() - 2 - ☃xx;
      int ☃xxxxxxxxxxxx = ☃.func_177952_p() - MathHelper.func_76123_f(☃x) - ☃xx;
      int ☃xxxxxxxxxxxxx = 2 * (MathHelper.func_76123_f(☃x) + ☃xx);
      int ☃xxxxxxxxxxxxxx = 2 * (2 + ☃xx);

      for(int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxx; ☃xxxxxxxxxxxxxxx <= ☃xxxxxxxxxx + ☃xxxxxxxxxxxxx; ++☃xxxxxxxxxxxxxxx) {
         for(int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx; ☃xxxxxxxxxxxxxxxx <= ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx; ++☃xxxxxxxxxxxxxxxx) {
            if (☃xxxxxxxxxxx <= ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR_WG, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx)) {
               return this.func_207803_a(
                  ☃, ☃, ☃, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx
               );
            }
         }
      }

      return false;
   }

   protected boolean func_207803_a(
      IWorld var1,
      Random var2,
      MinableConfig var3,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      int ☃ = 0;
      BitSet ☃x = new BitSet(☃ * ☃ * ☃);
      BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();
      double[] ☃xxx = new double[☃.field_202443_c * 4];

      for(int ☃xxxx = 0; ☃xxxx < ☃.field_202443_c; ++☃xxxx) {
         float ☃xxxxx = (float)☃xxxx / (float)☃.field_202443_c;
         double ☃xxxxxx = ☃ + (☃ - ☃) * (double)☃xxxxx;
         double ☃xxxxxxx = ☃ + (☃ - ☃) * (double)☃xxxxx;
         double ☃xxxxxxxx = ☃ + (☃ - ☃) * (double)☃xxxxx;
         double ☃xxxxxxxxx = ☃.nextDouble() * (double)☃.field_202443_c / 16.0;
         double ☃xxxxxxxxxx = ((double)(MathHelper.func_76126_a((float) Math.PI * ☃xxxxx) + 1.0F) * ☃xxxxxxxxx + 1.0) / 2.0;
         ☃xxx[☃xxxx * 4 + 0] = ☃xxxxxx;
         ☃xxx[☃xxxx * 4 + 1] = ☃xxxxxxx;
         ☃xxx[☃xxxx * 4 + 2] = ☃xxxxxxxx;
         ☃xxx[☃xxxx * 4 + 3] = ☃xxxxxxxxxx;
      }

      for(int ☃xxxx = 0; ☃xxxx < ☃.field_202443_c - 1; ++☃xxxx) {
         if (!(☃xxx[☃xxxx * 4 + 3] <= 0.0)) {
            for(int ☃xxxxx = ☃xxxx + 1; ☃xxxxx < ☃.field_202443_c; ++☃xxxxx) {
               if (!(☃xxx[☃xxxxx * 4 + 3] <= 0.0)) {
                  double ☃xxxxxx = ☃xxx[☃xxxx * 4 + 0] - ☃xxx[☃xxxxx * 4 + 0];
                  double ☃xxxxxxx = ☃xxx[☃xxxx * 4 + 1] - ☃xxx[☃xxxxx * 4 + 1];
                  double ☃xxxxxxxx = ☃xxx[☃xxxx * 4 + 2] - ☃xxx[☃xxxxx * 4 + 2];
                  double ☃xxxxxxxxx = ☃xxx[☃xxxx * 4 + 3] - ☃xxx[☃xxxxx * 4 + 3];
                  if (☃xxxxxxxxx * ☃xxxxxxxxx > ☃xxxxxx * ☃xxxxxx + ☃xxxxxxx * ☃xxxxxxx + ☃xxxxxxxx * ☃xxxxxxxx) {
                     if (☃xxxxxxxxx > 0.0) {
                        ☃xxx[☃xxxxx * 4 + 3] = -1.0;
                     } else {
                        ☃xxx[☃xxxx * 4 + 3] = -1.0;
                     }
                  }
               }
            }
         }
      }

      for(int ☃xxxx = 0; ☃xxxx < ☃.field_202443_c; ++☃xxxx) {
         double ☃xxxxx = ☃xxx[☃xxxx * 4 + 3];
         if (!(☃xxxxx < 0.0)) {
            double ☃xxxxxx = ☃xxx[☃xxxx * 4 + 0];
            double ☃xxxxxxx = ☃xxx[☃xxxx * 4 + 1];
            double ☃xxxxxxxx = ☃xxx[☃xxxx * 4 + 2];
            int ☃xxxxxxxxx = Math.max(MathHelper.func_76128_c(☃xxxxxx - ☃xxxxx), ☃);
            int ☃xxxxxxxxxx = Math.max(MathHelper.func_76128_c(☃xxxxxxx - ☃xxxxx), ☃);
            int ☃xxxxxxxxxxx = Math.max(MathHelper.func_76128_c(☃xxxxxxxx - ☃xxxxx), ☃);
            int ☃xxxxxxxxxxxx = Math.max(MathHelper.func_76128_c(☃xxxxxx + ☃xxxxx), ☃xxxxxxxxx);
            int ☃xxxxxxxxxxxxx = Math.max(MathHelper.func_76128_c(☃xxxxxxx + ☃xxxxx), ☃xxxxxxxxxx);
            int ☃xxxxxxxxxxxxxx = Math.max(MathHelper.func_76128_c(☃xxxxxxxx + ☃xxxxx), ☃xxxxxxxxxxx);

            for(int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxx; ☃xxxxxxxxxxxxxxx <= ☃xxxxxxxxxxxx; ++☃xxxxxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxx + 0.5 - ☃xxxxxx) / ☃xxxxx;
               if (☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx < 1.0) {
                  for(int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxx; ☃xxxxxxxxxxxxxxxxx <= ☃xxxxxxxxxxxxx; ++☃xxxxxxxxxxxxxxxxx) {
                     double ☃xxxxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxxxx + 0.5 - ☃xxxxxxx) / ☃xxxxx;
                     if (☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxx < 1.0) {
                        for(int ☃xxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx; ☃xxxxxxxxxxxxxxxxxxx <= ☃xxxxxxxxxxxxxx; ++☃xxxxxxxxxxxxxxxxxxx) {
                           double ☃xxxxxxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxxxxxx + 0.5 - ☃xxxxxxxx) / ☃xxxxx;
                           if (☃xxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxx
                                 + ☃xxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxx
                                 + ☃xxxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxxx
                              < 1.0) {
                              int ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx - ☃ + (☃xxxxxxxxxxxxxxxxx - ☃) * ☃ + (☃xxxxxxxxxxxxxxxxxxx - ☃) * ☃ * ☃;
                              if (!☃x.get(☃xxxxxxxxxxxxxxxxxxxxx)) {
                                 ☃x.set(☃xxxxxxxxxxxxxxxxxxxxx);
                                 ☃xx.func_181079_c(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxx);
                                 if (☃.field_202442_b.test(☃.func_180495_p(☃xx))) {
                                    ☃.func_180501_a(☃xx, ☃.field_202444_d, 2);
                                    ++☃;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return ☃ > 0;
   }
}
