package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class IcebergFeature extends Feature<IcebergConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, IcebergConfig var5) {
      ☃ = new BlockPos(☃.func_177958_n(), ☃.func_181545_F(), ☃.func_177952_p());
      boolean ☃ = ☃.nextDouble() > 0.7;
      IBlockState ☃x = ☃.field_205191_a;
      double ☃xx = ☃.nextDouble() * 2.0 * Math.PI;
      int ☃xxx = 11 - ☃.nextInt(5);
      int ☃xxxx = 3 + ☃.nextInt(3);
      boolean ☃xxxxx = ☃.nextDouble() > 0.7;
      int ☃xxxxxx = 11;
      int ☃xxxxxxx = ☃xxxxx ? ☃.nextInt(6) + 6 : ☃.nextInt(15) + 3;
      if (!☃xxxxx && ☃.nextDouble() > 0.9) {
         ☃xxxxxxx += ☃.nextInt(19) + 7;
      }

      int ☃ = Math.min(☃xxxxxxx + ☃.nextInt(11), 18);
      int ☃x = Math.min(☃xxxxxxx + ☃.nextInt(7) - ☃.nextInt(5), 11);
      int ☃xx = ☃xxxxx ? ☃xxx : 11;

      for(int ☃xxx = -☃xx; ☃xxx < ☃xx; ++☃xxx) {
         for(int ☃xxxx = -☃xx; ☃xxxx < ☃xx; ++☃xxxx) {
            for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxxxxx; ++☃xxxxx) {
               int ☃xxxxxx = ☃xxxxx ? this.func_205178_b(☃xxxxx, ☃xxxxxxx, ☃x) : this.func_205183_a(☃, ☃xxxxx, ☃xxxxxxx, ☃x);
               if (☃xxxxx || ☃xxx < ☃xxxxxx) {
                  this.func_205181_a(☃, ☃, ☃, ☃xxxxxxx, ☃xxx, ☃xxxxx, ☃xxxx, ☃xxxxxx, ☃xx, ☃xxxxx, ☃xxxx, ☃xx, ☃, ☃x);
               }
            }
         }
      }

      this.func_205186_a(☃, ☃, ☃x, ☃xxxxxxx, ☃xxxxx, ☃xxx);

      for(int ☃xxx = -☃xx; ☃xxx < ☃xx; ++☃xxx) {
         for(int ☃xxxx = -☃xx; ☃xxxx < ☃xx; ++☃xxxx) {
            for(int ☃xxxxx = -1; ☃xxxxx > -☃; --☃xxxxx) {
               int ☃xxxxxx = ☃xxxxx ? MathHelper.func_76123_f((float)☃xx * (1.0F - (float)Math.pow((double)☃xxxxx, 2.0) / ((float)☃ * 8.0F))) : ☃xx;
               int ☃xxxxxxx = this.func_205187_b(☃, -☃xxxxx, ☃, ☃x);
               if (☃xxx < ☃xxxxxxx) {
                  this.func_205181_a(☃, ☃, ☃, ☃, ☃xxx, ☃xxxxx, ☃xxxx, ☃xxxxxxx, ☃xxxxxx, ☃xxxxx, ☃xxxx, ☃xx, ☃, ☃x);
               }
            }
         }
      }

      boolean ☃xxx = ☃xxxxx ? ☃.nextDouble() > 0.1 : ☃.nextDouble() > 0.7;
      if (☃xxx) {
         this.func_205184_a(☃, ☃, ☃x, ☃xxxxxxx, ☃, ☃xxxxx, ☃xxx, ☃xx, ☃xxxx);
      }

      return true;
   }

   private void func_205184_a(Random var1, IWorld var2, int var3, int var4, BlockPos var5, boolean var6, int var7, double var8, int var10) {
      int ☃ = ☃.nextBoolean() ? -1 : 1;
      int ☃x = ☃.nextBoolean() ? -1 : 1;
      int ☃xx = ☃.nextInt(Math.max(☃ / 2 - 2, 1));
      if (☃.nextBoolean()) {
         ☃xx = ☃ / 2 + 1 - ☃.nextInt(Math.max(☃ - ☃ / 2 - 1, 1));
      }

      int ☃ = ☃.nextInt(Math.max(☃ / 2 - 2, 1));
      if (☃.nextBoolean()) {
         ☃ = ☃ / 2 + 1 - ☃.nextInt(Math.max(☃ - ☃ / 2 - 1, 1));
      }

      if (☃) {
         ☃xx = ☃ = ☃.nextInt(Math.max(☃ - 5, 1));
      }

      BlockPos ☃ = new BlockPos(0, 0, 0).func_177982_a(☃ * ☃xx, 0, ☃x * ☃);
      double ☃x = ☃ ? ☃ + (Math.PI / 2) : ☃.nextDouble() * 2.0 * Math.PI;

      for(int ☃xx = 0; ☃xx < ☃ - 3; ++☃xx) {
         int ☃xxx = this.func_205183_a(☃, ☃xx, ☃, ☃);
         this.func_205174_a(☃xxx, ☃xx, ☃, ☃, false, ☃x, ☃, ☃, ☃);
      }

      for(int ☃xx = -1; ☃xx > -☃ + ☃.nextInt(5); --☃xx) {
         int ☃xxx = this.func_205187_b(☃, -☃xx, ☃, ☃);
         this.func_205174_a(☃xxx, ☃xx, ☃, ☃, true, ☃x, ☃, ☃, ☃);
      }
   }

   private void func_205174_a(int var1, int var2, BlockPos var3, IWorld var4, boolean var5, double var6, BlockPos var8, int var9, int var10) {
      int ☃ = ☃ + 1 + ☃ / 3;
      int ☃x = Math.min(☃ - 3, 3) + ☃ / 2 - 1;

      for(int ☃xx = -☃; ☃xx < ☃; ++☃xx) {
         for(int ☃xxx = -☃; ☃xxx < ☃; ++☃xxx) {
            double ☃xxxx = this.func_205180_a(☃xx, ☃xxx, ☃, ☃, ☃x, ☃);
            if (☃xxxx < 0.0) {
               BlockPos ☃xxxxx = ☃.func_177982_a(☃xx, ☃, ☃xxx);
               Block ☃xxxxxx = ☃.func_180495_p(☃xxxxx).func_177230_c();
               if (this.func_205179_a(☃xxxxxx) || ☃xxxxxx == Blocks.field_196604_cC) {
                  if (☃) {
                     this.func_202278_a(☃, ☃xxxxx, Blocks.field_150355_j.func_176223_P());
                  } else {
                     this.func_202278_a(☃, ☃xxxxx, Blocks.field_150350_a.func_176223_P());
                     this.func_205185_a(☃, ☃xxxxx);
                  }
               }
            }
         }
      }
   }

   private void func_205185_a(IWorld var1, BlockPos var2) {
      if (☃.func_180495_p(☃.func_177984_a()).func_177230_c() == Blocks.field_150433_aE) {
         this.func_202278_a(☃, ☃.func_177984_a(), Blocks.field_150350_a.func_176223_P());
      }
   }

   private void func_205181_a(
      IWorld var1,
      Random var2,
      BlockPos var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      boolean var10,
      int var11,
      double var12,
      boolean var14,
      IBlockState var15
   ) {
      BlockPos ☃ = new BlockPos(0, 0, 0);
      double ☃x = ☃ ? this.func_205180_a(☃, ☃, ☃, ☃, this.func_205176_a(☃, ☃, ☃), ☃) : this.func_205177_a(☃, ☃, ☃, ☃, ☃);
      if (☃x < 0.0) {
         BlockPos ☃xx = ☃.func_177982_a(☃, ☃, ☃);
         double ☃xxx = ☃ ? -0.5 : (double)(-6 - ☃.nextInt(3));
         if (☃x > ☃xxx && ☃.nextDouble() > 0.9) {
            return;
         }

         this.func_205175_a(☃xx, ☃, ☃, ☃ - ☃, ☃, ☃, ☃, ☃);
      }
   }

   private void func_205175_a(BlockPos var1, IWorld var2, Random var3, int var4, int var5, boolean var6, boolean var7, IBlockState var8) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      Block ☃x = ☃.func_177230_c();
      if (☃.func_185904_a() == Material.field_151579_a || ☃x == Blocks.field_196604_cC || ☃x == Blocks.field_150432_aD || ☃x == Blocks.field_150355_j) {
         boolean ☃xx = !☃ || ☃.nextDouble() > 0.05;
         int ☃xxx = ☃ ? 3 : 2;
         if (☃ && ☃x != Blocks.field_150355_j && (double)☃ <= (double)☃.nextInt(Math.max(1, ☃ / ☃xxx)) + (double)☃ * 0.6 && ☃xx) {
            this.func_202278_a(☃, ☃, Blocks.field_196604_cC.func_176223_P());
         } else {
            this.func_202278_a(☃, ☃, ☃);
         }
      }
   }

   private int func_205176_a(int var1, int var2, int var3) {
      int ☃ = ☃;
      if (☃ > 0 && ☃ - ☃ <= 3) {
         ☃ = ☃ - (4 - (☃ - ☃));
      }

      return ☃;
   }

   private double func_205177_a(int var1, int var2, BlockPos var3, int var4, Random var5) {
      float ☃ = 10.0F * MathHelper.func_76131_a(☃.nextFloat(), 0.2F, 0.8F) / (float)☃;
      return (double)☃ + Math.pow((double)(☃ - ☃.func_177958_n()), 2.0) + Math.pow((double)(☃ - ☃.func_177952_p()), 2.0) - Math.pow((double)☃, 2.0);
   }

   private double func_205180_a(int var1, int var2, BlockPos var3, int var4, int var5, double var6) {
      return Math.pow(((double)(☃ - ☃.func_177958_n()) * Math.cos(☃) - (double)(☃ - ☃.func_177952_p()) * Math.sin(☃)) / (double)☃, 2.0)
         + Math.pow(((double)(☃ - ☃.func_177958_n()) * Math.sin(☃) + (double)(☃ - ☃.func_177952_p()) * Math.cos(☃)) / (double)☃, 2.0)
         - 1.0;
   }

   private int func_205183_a(Random var1, int var2, int var3, int var4) {
      float ☃ = 3.5F - ☃.nextFloat();
      float ☃x = (1.0F - (float)Math.pow((double)☃, 2.0) / ((float)☃ * ☃)) * (float)☃;
      if (☃ > 15 + ☃.nextInt(5)) {
         int ☃xx = ☃ < 3 + ☃.nextInt(6) ? ☃ / 2 : ☃;
         ☃x = (1.0F - (float)☃xx / ((float)☃ * ☃ * 0.4F)) * (float)☃;
      }

      return MathHelper.func_76123_f(☃x / 2.0F);
   }

   private int func_205178_b(int var1, int var2, int var3) {
      float ☃ = 1.0F;
      float ☃x = (1.0F - (float)Math.pow((double)☃, 2.0) / ((float)☃ * 1.0F)) * (float)☃;
      return MathHelper.func_76123_f(☃x / 2.0F);
   }

   private int func_205187_b(Random var1, int var2, int var3, int var4) {
      float ☃ = 1.0F + ☃.nextFloat() / 2.0F;
      float ☃x = (1.0F - (float)☃ / ((float)☃ * ☃)) * (float)☃;
      return MathHelper.func_76123_f(☃x / 2.0F);
   }

   private boolean func_205179_a(Block var1) {
      return ☃ == Blocks.field_150403_cj || ☃ == Blocks.field_196604_cC || ☃ == Blocks.field_205164_gk;
   }

   private boolean func_205182_b(IBlockReader var1, BlockPos var2) {
      return ☃.func_180495_p(☃.func_177977_b()).func_185904_a() == Material.field_151579_a;
   }

   private void func_205186_a(IWorld var1, BlockPos var2, int var3, int var4, boolean var5, int var6) {
      int ☃ = ☃ ? ☃ : ☃ / 2;

      for(int ☃x = -☃; ☃x <= ☃; ++☃x) {
         for(int ☃xx = -☃; ☃xx <= ☃; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx <= ☃; ++☃xxx) {
               BlockPos ☃xxxx = ☃.func_177982_a(☃x, ☃xxx, ☃xx);
               Block ☃xxxxx = ☃.func_180495_p(☃xxxx).func_177230_c();
               if (this.func_205179_a(☃xxxxx) || ☃xxxxx == Blocks.field_150433_aE) {
                  if (this.func_205182_b(☃, ☃xxxx)) {
                     this.func_202278_a(☃, ☃xxxx, Blocks.field_150350_a.func_176223_P());
                     this.func_202278_a(☃, ☃xxxx.func_177984_a(), Blocks.field_150350_a.func_176223_P());
                  } else if (this.func_205179_a(☃xxxxx)) {
                     Block[] ☃xxxxxx = new Block[]{
                        ☃.func_180495_p(☃xxxx.func_177976_e()).func_177230_c(),
                        ☃.func_180495_p(☃xxxx.func_177974_f()).func_177230_c(),
                        ☃.func_180495_p(☃xxxx.func_177978_c()).func_177230_c(),
                        ☃.func_180495_p(☃xxxx.func_177968_d()).func_177230_c()
                     };
                     int ☃xxxxxxx = 0;

                     for(Block ☃xxxxxxxx : ☃xxxxxx) {
                        if (!this.func_205179_a(☃xxxxxxxx)) {
                           ++☃xxxxxxx;
                        }
                     }

                     if (☃xxxxxxx >= 3) {
                        this.func_202278_a(☃, ☃xxxx, Blocks.field_150350_a.func_176223_P());
                     }
                  }
               }
            }
         }
      }
   }
}
