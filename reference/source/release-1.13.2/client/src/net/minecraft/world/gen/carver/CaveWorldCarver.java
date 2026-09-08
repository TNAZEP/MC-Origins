package net.minecraft.world.gen.carver;

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

public class CaveWorldCarver extends WorldCarver<ProbabilityConfig> {
   public boolean func_212246_a(IBlockReader var1, Random var2, int var3, int var4, ProbabilityConfig var5) {
      return ☃.nextFloat() <= ☃.field_203622_a;
   }

   public boolean func_202522_a(IWorld var1, Random var2, int var3, int var4, int var5, int var6, BitSet var7, ProbabilityConfig var8) {
      int ☃ = (this.func_202520_b() * 2 - 1) * 16;
      int ☃x = ☃.nextInt(☃.nextInt(☃.nextInt(15) + 1) + 1);

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         double ☃xxx = (double)(☃ * 16 + ☃.nextInt(16));
         double ☃xxxx = (double)☃.nextInt(☃.nextInt(120) + 8);
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
            float ☃xxxxx = (☃.nextFloat() - 0.5F) / 4.0F;
            double ☃xxxxxx = 1.0;
            float ☃xxxxxxx = ☃.nextFloat() * 2.0F + ☃.nextFloat();
            if (☃.nextInt(10) == 0) {
               ☃xxxxxxx *= ☃.nextFloat() * ☃.nextFloat() * 3.0F + 1.0F;
            }

            int ☃xxxx = ☃ - ☃.nextInt(☃ / 4);
            int ☃xxxxx = 0;
            this.func_202533_a(☃, ☃.nextLong(), ☃, ☃, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxxx, ☃xxxx, ☃xxxxx, 0, ☃xxxx, 1.0, ☃);
         }
      }

      return true;
   }

   protected void func_203627_a(IWorld var1, long var2, int var4, int var5, double var6, double var8, double var10, float var12, double var13, BitSet var15) {
      double ☃ = 1.5 + (double)(MathHelper.func_76126_a((float) (Math.PI / 2)) * ☃);
      double ☃x = ☃ * ☃;
      this.func_202516_a(☃, ☃, ☃, ☃, ☃ + 1.0, ☃, ☃, ☃, ☃x, ☃);
   }

   protected void func_202533_a(
      IWorld var1,
      long var2,
      int var4,
      int var5,
      double var6,
      double var8,
      double var10,
      float var12,
      float var13,
      float var14,
      int var15,
      int var16,
      double var17,
      BitSet var19
   ) {
      Random ☃ = new Random(☃);
      int ☃x = ☃.nextInt(☃ / 2) + ☃ / 4;
      boolean ☃xx = ☃.nextInt(6) == 0;
      float ☃xxx = 0.0F;
      float ☃xxxx = 0.0F;

      for(int ☃xxxxx = ☃; ☃xxxxx < ☃; ++☃xxxxx) {
         double ☃xxxxxx = 1.5 + (double)(MathHelper.func_76126_a((float) Math.PI * (float)☃xxxxx / (float)☃) * ☃);
         double ☃xxxxxxx = ☃xxxxxx * ☃;
         float ☃xxxxxxxx = MathHelper.func_76134_b(☃);
         ☃ += (double)(MathHelper.func_76134_b(☃) * ☃xxxxxxxx);
         ☃ += (double)MathHelper.func_76126_a(☃);
         ☃ += (double)(MathHelper.func_76126_a(☃) * ☃xxxxxxxx);
         ☃ *= ☃xx ? 0.92F : 0.7F;
         ☃ += ☃xxxx * 0.1F;
         ☃ += ☃xxx * 0.1F;
         ☃xxxx *= 0.9F;
         ☃xxx *= 0.75F;
         ☃xxxx += (☃.nextFloat() - ☃.nextFloat()) * ☃.nextFloat() * 2.0F;
         ☃xxx += (☃.nextFloat() - ☃.nextFloat()) * ☃.nextFloat() * 4.0F;
         if (☃xxxxx == ☃x && ☃ > 1.0F) {
            this.func_202533_a(☃, ☃.nextLong(), ☃, ☃, ☃, ☃, ☃, ☃.nextFloat() * 0.5F + 0.5F, ☃ - (float) (Math.PI / 2), ☃ / 3.0F, ☃xxxxx, ☃, 1.0, ☃);
            this.func_202533_a(☃, ☃.nextLong(), ☃, ☃, ☃, ☃, ☃, ☃.nextFloat() * 0.5F + 0.5F, ☃ + (float) (Math.PI / 2), ☃ / 3.0F, ☃xxxxx, ☃, 1.0, ☃);
            return;
         }

         if (☃.nextInt(4) != 0) {
            if (!this.func_202515_a(☃, ☃, ☃, ☃, ☃xxxxx, ☃, ☃)) {
               return;
            }

            this.func_202516_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃xxxxxx, ☃xxxxxxx, ☃);
         }
      }
   }

   @Override
   protected boolean func_202516_a(IWorld var1, long var2, int var4, int var5, double var6, double var8, double var10, double var12, double var14, BitSet var16) {
      double ☃ = (double)(☃ * 16 + 8);
      double ☃x = (double)(☃ * 16 + 8);
      if (!(☃ < ☃ - 16.0 - ☃ * 2.0) && !(☃ < ☃x - 16.0 - ☃ * 2.0) && !(☃ > ☃ + 16.0 + ☃ * 2.0) && !(☃ > ☃x + 16.0 + ☃ * 2.0)) {
         int ☃xx = Math.max(MathHelper.func_76128_c(☃ - ☃) - ☃ * 16 - 1, 0);
         int ☃xxx = Math.min(MathHelper.func_76128_c(☃ + ☃) - ☃ * 16 + 1, 16);
         int ☃xxxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - 1, 1);
         int ☃xxxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) + 1, 248);
         int ☃xxxxxx = Math.max(MathHelper.func_76128_c(☃ - ☃) - ☃ * 16 - 1, 0);
         int ☃xxxxxxx = Math.min(MathHelper.func_76128_c(☃ + ☃) - ☃ * 16 + 1, 16);
         if (this.func_202524_a(☃, ☃, ☃, ☃xx, ☃xxx, ☃xxxx, ☃xxxxx, ☃xxxxxx, ☃xxxxxxx)) {
            return false;
         } else {
            boolean ☃xx = false;
            BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos ☃xxxx = new BlockPos.MutableBlockPos();
            BlockPos.MutableBlockPos ☃xxxxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxxxx = ☃xx; ☃xxxxxx < ☃xxx; ++☃xxxxxx) {
               int ☃xxxxxxx = ☃xxxxxx + ☃ * 16;
               double ☃xxxxxxxx = ((double)☃xxxxxxx + 0.5 - ☃) / ☃;

               for(int ☃xxxxxxxxx = ☃xxxxxx; ☃xxxxxxxxx < ☃xxxxxxx; ++☃xxxxxxxxx) {
                  int ☃xxxxxxxxxx = ☃xxxxxxxxx + ☃ * 16;
                  double ☃xxxxxxxxxxx = ((double)☃xxxxxxxxxx + 0.5 - ☃) / ☃;
                  if (!(☃xxxxxxxx * ☃xxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx >= 1.0)) {
                     boolean ☃xxxxxxxxxxxx = false;

                     for(int ☃xxxxxxxxxxxxx = ☃xxxxx; ☃xxxxxxxxxxxxx > ☃xxxx; --☃xxxxxxxxxxxxx) {
                        double ☃xxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxx - 0.5 - ☃) / ☃;
                        if (!(☃xxxxxxxxxxxxxx <= -0.7) && !(☃xxxxxxxx * ☃xxxxxxxx + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx >= 1.0)) {
                           int ☃xxxxxxxxxxxxxxx = ☃xxxxxx | ☃xxxxxxxxx << 4 | ☃xxxxxxxxxxxxx << 8;
                           if (!☃.get(☃xxxxxxxxxxxxxxx)) {
                              ☃.set(☃xxxxxxxxxxxxxxx);
                              ☃xxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxx);
                              IBlockState ☃xxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxx);
                              IBlockState ☃xxxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxx.func_189533_g(☃xxx).func_189536_c(EnumFacing.UP));
                              if (☃xxxxxxxxxxxxxxxx.func_177230_c() == Blocks.field_196658_i || ☃xxxxxxxxxxxxxxxx.func_177230_c() == Blocks.field_150391_bh) {
                                 ☃xxxxxxxxxxxx = true;
                              }

                              if (this.func_202517_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx)) {
                                 if (☃xxxxxxxxxxxxx < 11) {
                                    ☃.func_180501_a(☃xxx, field_202529_e.func_206883_i(), 2);
                                 } else {
                                    ☃.func_180501_a(☃xxx, field_202526_b, 2);
                                    if (☃xxxxxxxxxxxx) {
                                       ☃xxxxx.func_189533_g(☃xxx).func_189536_c(EnumFacing.DOWN);
                                       if (☃.func_180495_p(☃xxxxx).func_177230_c() == Blocks.field_150346_d) {
                                          IBlockState ☃xxxxxxxxxxxxxxxx = ☃.func_180494_b(☃xxx).func_203944_q().func_204108_a();
                                          ☃.func_180501_a(☃xxxxx, ☃xxxxxxxxxxxxxxxx, 2);
                                       }
                                    }
                                 }

                                 ☃xx = true;
                              }
                           }
                        }
                     }
                  }
               }
            }

            return ☃xx;
         }
      } else {
         return false;
      }
   }
}
