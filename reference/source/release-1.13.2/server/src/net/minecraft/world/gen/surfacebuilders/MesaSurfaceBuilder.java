package net.minecraft.world.gen.surfacebuilders;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class MesaSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
   private static final IBlockState field_202620_f = Blocks.field_196777_fo.func_176223_P();
   private static final IBlockState field_202621_g = Blocks.field_196778_fp.func_176223_P();
   private static final IBlockState field_202622_h = Blocks.field_150405_ch.func_176223_P();
   private static final IBlockState field_202623_i = Blocks.field_196783_fs.func_176223_P();
   private static final IBlockState field_202624_j = Blocks.field_196719_fA.func_176223_P();
   private static final IBlockState field_202625_k = Blocks.field_196721_fC.func_176223_P();
   private static final IBlockState field_202626_l = Blocks.field_196791_fw.func_176223_P();
   protected IBlockState[] field_202615_a;
   protected long field_202616_b;
   protected NoiseGeneratorPerlin field_202617_c;
   protected NoiseGeneratorPerlin field_202618_d;
   protected NoiseGeneratorPerlin field_202619_e;

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
      IBlockState ☃xx = field_202620_f;
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
                     ☃xx = field_202620_f;
                     ☃xxx = ☃.func_203944_q().func_204109_b();
                  }

                  if (☃xxxxxxxxxx < ☃ && (☃xx == null || ☃xx.func_196958_f())) {
                     ☃xx = ☃;
                  }

                  ☃xxxxxx = ☃xxxx + Math.max(0, ☃xxxxxxxxxx - ☃);
                  if (☃xxxxxxxxxx >= ☃ - 1) {
                     if (☃xxxxxxxxxx <= ☃ + 3 + ☃xxxx) {
                        ☃.func_177436_a(☃xxxxxxxxx, ☃.func_203944_q().func_204108_a(), false);
                        ☃xxxxxxx = true;
                     } else {
                        IBlockState ☃xxxxxxxxxxx;
                        if (☃xxxxxxxxxx < 64 || ☃xxxxxxxxxx > 127) {
                           ☃xxxxxxxxxxx = field_202621_g;
                        } else if (☃xxxxx) {
                           ☃xxxxxxxxxxx = field_202622_h;
                        } else {
                           ☃xxxxxxxxxxx = this.func_202614_a(☃, ☃xxxxxxxxxx, ☃);
                        }

                        ☃.func_177436_a(☃xxxxxxxxx, ☃xxxxxxxxxxx, false);
                     }
                  } else {
                     ☃.func_177436_a(☃xxxxxxxxx, ☃xxx, false);
                     Block ☃xxxxxxxxxxx = ☃xxx.func_177230_c();
                     if (☃xxxxxxxxxxx == Blocks.field_196777_fo
                        || ☃xxxxxxxxxxx == Blocks.field_196778_fp
                        || ☃xxxxxxxxxxx == Blocks.field_196780_fq
                        || ☃xxxxxxxxxxx == Blocks.field_196782_fr
                        || ☃xxxxxxxxxxx == Blocks.field_196783_fs
                        || ☃xxxxxxxxxxx == Blocks.field_196785_ft
                        || ☃xxxxxxxxxxx == Blocks.field_196787_fu
                        || ☃xxxxxxxxxxx == Blocks.field_196789_fv
                        || ☃xxxxxxxxxxx == Blocks.field_196791_fw
                        || ☃xxxxxxxxxxx == Blocks.field_196793_fx
                        || ☃xxxxxxxxxxx == Blocks.field_196795_fy
                        || ☃xxxxxxxxxxx == Blocks.field_196797_fz
                        || ☃xxxxxxxxxxx == Blocks.field_196719_fA
                        || ☃xxxxxxxxxxx == Blocks.field_196720_fB
                        || ☃xxxxxxxxxxx == Blocks.field_196721_fC
                        || ☃xxxxxxxxxxx == Blocks.field_196722_fD) {
                        ☃.func_177436_a(☃xxxxxxxxx, field_202621_g, false);
                     }
                  }
               } else if (☃xxxxxx > 0) {
                  --☃xxxxxx;
                  if (☃xxxxxxx) {
                     ☃.func_177436_a(☃xxxxxxxxx, field_202621_g, false);
                  } else {
                     ☃.func_177436_a(☃xxxxxxxxx, this.func_202614_a(☃, ☃xxxxxxxxxx, ☃), false);
                  }
               }

               ++☃xxxxxxxx;
            }
         }
      }
   }

   @Override
   public void func_205548_a(long var1) {
      if (this.field_202616_b != ☃ || this.field_202615_a == null) {
         this.func_202613_a(☃);
      }

      if (this.field_202616_b != ☃ || this.field_202617_c == null || this.field_202618_d == null) {
         Random ☃ = new SharedSeedRandom(☃);
         this.field_202617_c = new NoiseGeneratorPerlin(☃, 4);
         this.field_202618_d = new NoiseGeneratorPerlin(☃, 1);
      }

      this.field_202616_b = ☃;
   }

   protected void func_202613_a(long var1) {
      this.field_202615_a = new IBlockState[64];
      Arrays.fill(this.field_202615_a, field_202622_h);
      Random ☃ = new SharedSeedRandom(☃);
      this.field_202619_e = new NoiseGeneratorPerlin(☃, 1);

      for(int ☃x = 0; ☃x < 64; ++☃x) {
         ☃x += ☃.nextInt(5) + 1;
         if (☃x < 64) {
            this.field_202615_a[☃x] = field_202621_g;
         }
      }

      int ☃x = ☃.nextInt(4) + 2;

      for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
         int ☃xxx = ☃.nextInt(3) + 1;
         int ☃xxxx = ☃.nextInt(64);

         for(int ☃xxxxx = 0; ☃xxxx + ☃xxxxx < 64 && ☃xxxxx < ☃xxx; ++☃xxxxx) {
            this.field_202615_a[☃xxxx + ☃xxxxx] = field_202623_i;
         }
      }

      int ☃xx = ☃.nextInt(4) + 2;

      for(int ☃xxx = 0; ☃xxx < ☃xx; ++☃xxx) {
         int ☃xxxx = ☃.nextInt(3) + 2;
         int ☃xxxxx = ☃.nextInt(64);

         for(int ☃xxxxxx = 0; ☃xxxxx + ☃xxxxxx < 64 && ☃xxxxxx < ☃xxxx; ++☃xxxxxx) {
            this.field_202615_a[☃xxxxx + ☃xxxxxx] = field_202624_j;
         }
      }

      int ☃xxx = ☃.nextInt(4) + 2;

      for(int ☃xxxx = 0; ☃xxxx < ☃xxx; ++☃xxxx) {
         int ☃xxxxx = ☃.nextInt(3) + 1;
         int ☃xxxxxx = ☃.nextInt(64);

         for(int ☃xxxxxxx = 0; ☃xxxxxx + ☃xxxxxxx < 64 && ☃xxxxxxx < ☃xxxxx; ++☃xxxxxxx) {
            this.field_202615_a[☃xxxxxx + ☃xxxxxxx] = field_202625_k;
         }
      }

      int ☃xxxx = ☃.nextInt(3) + 3;
      int ☃xxxxx = 0;

      for(int ☃xxxxxx = 0; ☃xxxxxx < ☃xxxx; ++☃xxxxxx) {
         int ☃xxxxxxx = 1;
         ☃xxxxx += ☃.nextInt(16) + 4;

         for(int ☃xxxxxxxx = 0; ☃xxxxx + ☃xxxxxxxx < 64 && ☃xxxxxxxx < 1; ++☃xxxxxxxx) {
            this.field_202615_a[☃xxxxx + ☃xxxxxxxx] = field_202620_f;
            if (☃xxxxx + ☃xxxxxxxx > 1 && ☃.nextBoolean()) {
               this.field_202615_a[☃xxxxx + ☃xxxxxxxx - 1] = field_202626_l;
            }

            if (☃xxxxx + ☃xxxxxxxx < 63 && ☃.nextBoolean()) {
               this.field_202615_a[☃xxxxx + ☃xxxxxxxx + 1] = field_202626_l;
            }
         }
      }
   }

   protected IBlockState func_202614_a(int var1, int var2, int var3) {
      int ☃ = (int)Math.round(this.field_202619_e.func_151601_a((double)☃ / 512.0, (double)☃ / 512.0) * 2.0);
      return this.field_202615_a[(☃ + ☃ + 64) % 64];
   }
}
