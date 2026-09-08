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

public class CanyonWorldCarver extends WorldCarver<ProbabilityConfig> {
   private final float[] field_202536_i = new float[1024];

   public boolean func_212246_a(IBlockReader var1, Random var2, int var3, int var4, ProbabilityConfig var5) {
      return ☃.nextFloat() <= ☃.field_203622_a;
   }

   public boolean func_202522_a(IWorld var1, Random var2, int var3, int var4, int var5, int var6, BitSet var7, ProbabilityConfig var8) {
      int ☃ = (this.func_202520_b() * 2 - 1) * 16;
      double ☃x = (double)(☃ * 16 + ☃.nextInt(16));
      double ☃xx = (double)(☃.nextInt(☃.nextInt(40) + 8) + 20);
      double ☃xxx = (double)(☃ * 16 + ☃.nextInt(16));
      float ☃xxxx = ☃.nextFloat() * (float) (Math.PI * 2);
      float ☃xxxxx = (☃.nextFloat() - 0.5F) * 2.0F / 8.0F;
      double ☃xxxxxx = 3.0;
      float ☃xxxxxxx = (☃.nextFloat() * 2.0F + ☃.nextFloat()) * 2.0F;
      int ☃xxxxxxxx = ☃ - ☃.nextInt(☃ / 4);
      int ☃xxxxxxxxx = 0;
      this.func_202535_a(☃, ☃.nextLong(), ☃, ☃, ☃x, ☃xx, ☃xxx, ☃xxxxxxx, ☃xxxx, ☃xxxxx, 0, ☃xxxxxxxx, 3.0, ☃);
      return true;
   }

   private void func_202535_a(
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
      float ☃x = 1.0F;

      for(int ☃xx = 0; ☃xx < 256; ++☃xx) {
         if (☃xx == 0 || ☃.nextInt(3) == 0) {
            ☃x = 1.0F + ☃.nextFloat() * ☃.nextFloat();
         }

         this.field_202536_i[☃xx] = ☃x * ☃x;
      }

      float ☃xx = 0.0F;
      float ☃xxx = 0.0F;

      for(int ☃xxxx = ☃; ☃xxxx < ☃; ++☃xxxx) {
         double ☃xxxxx = 1.5 + (double)(MathHelper.func_76126_a((float)☃xxxx * (float) Math.PI / (float)☃) * ☃);
         double ☃xxxxxx = ☃xxxxx * ☃;
         ☃xxxxx *= (double)☃.nextFloat() * 0.25 + 0.75;
         ☃xxxxxx *= (double)☃.nextFloat() * 0.25 + 0.75;
         float ☃xxxxxxx = MathHelper.func_76134_b(☃);
         float ☃xxxxxxxx = MathHelper.func_76126_a(☃);
         ☃ += (double)(MathHelper.func_76134_b(☃) * ☃xxxxxxx);
         ☃ += (double)☃xxxxxxxx;
         ☃ += (double)(MathHelper.func_76126_a(☃) * ☃xxxxxxx);
         ☃ *= 0.7F;
         ☃ += ☃xxx * 0.05F;
         ☃ += ☃xx * 0.05F;
         ☃xxx *= 0.8F;
         ☃xx *= 0.5F;
         ☃xxx += (☃.nextFloat() - ☃.nextFloat()) * ☃.nextFloat() * 2.0F;
         ☃xx += (☃.nextFloat() - ☃.nextFloat()) * ☃.nextFloat() * 4.0F;
         if (☃.nextInt(4) != 0) {
            if (!this.func_202515_a(☃, ☃, ☃, ☃, ☃xxxx, ☃, ☃)) {
               return;
            }

            this.func_202516_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃xxxxx, ☃xxxxxx, ☃);
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
         } else if (☃xx <= ☃xxx && ☃xxxx <= ☃xxxxx && ☃xxxxxx <= ☃xxxxxxx) {
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
                  if (☃xxxxxxxx * ☃xxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx < 1.0) {
                     boolean ☃xxxxxxxxxxxx = false;

                     for(int ☃xxxxxxxxxxxxx = ☃xxxxx; ☃xxxxxxxxxxxxx > ☃xxxx; --☃xxxxxxxxxxxxx) {
                        double ☃xxxxxxxxxxxxxx = ((double)(☃xxxxxxxxxxxxx - 1) + 0.5 - ☃) / ☃;
                        if ((☃xxxxxxxx * ☃xxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxx) * (double)this.field_202536_i[☃xxxxxxxxxxxxx - 1]
                              + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx / 6.0
                           < 1.0) {
                           int ☃xxxxxxxxxxxxxxx = ☃xxxxxx | ☃xxxxxxxxx << 4 | ☃xxxxxxxxxxxxx << 8;
                           if (!☃.get(☃xxxxxxxxxxxxxxx)) {
                              ☃.set(☃xxxxxxxxxxxxxxx);
                              ☃xxx.func_181079_c(☃xxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxx);
                              IBlockState ☃xxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxx);
                              ☃xxxx.func_189533_g(☃xxx).func_189536_c(EnumFacing.UP);
                              ☃xxxxx.func_189533_g(☃xxx).func_189536_c(EnumFacing.DOWN);
                              IBlockState ☃xxxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxx);
                              if (☃xxxxxxxxxxxxxxxx.func_177230_c() == Blocks.field_196658_i || ☃xxxxxxxxxxxxxxxx.func_177230_c() == Blocks.field_150391_bh) {
                                 ☃xxxxxxxxxxxx = true;
                              }

                              if (this.func_202517_a(☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx)) {
                                 if (☃xxxxxxxxxxxxx - 1 < 10) {
                                    ☃.func_180501_a(☃xxx, field_202529_e.func_206883_i(), 2);
                                 } else {
                                    ☃.func_180501_a(☃xxx, field_202526_b, 2);
                                    if (☃xxxxxxxxxxxx && ☃.func_180495_p(☃xxxxx).func_177230_c() == Blocks.field_150346_d) {
                                       ☃.func_180501_a(☃xxxxx, ☃.func_180494_b(☃xxx).func_203944_q().func_204108_a(), 2);
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
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
