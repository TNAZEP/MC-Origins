package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class FrozenOceanSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
   protected static final IBlockState field_205192_a = Blocks.field_150403_cj.func_176223_P();
   protected static final IBlockState field_205193_b = Blocks.field_196604_cC.func_176223_P();
   private static final IBlockState field_205195_d = Blocks.field_150350_a.func_176223_P();
   private static final IBlockState field_205196_e = Blocks.field_150351_n.func_176223_P();
   private static final IBlockState field_205197_f = Blocks.field_150432_aD.func_176223_P();
   private NoiseGeneratorPerlin field_205199_h;
   private NoiseGeneratorPerlin field_205200_i;
   private long field_205201_j;

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
      double ☃x = 0.0;
      BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();
      float ☃xxx = ☃.func_180626_a(☃xx.func_181079_c(☃, 63, ☃));
      double ☃xxxx = Math.min(Math.abs(☃), this.field_205199_h.func_151601_a((double)☃ * 0.1, (double)☃ * 0.1));
      if (☃xxxx > 1.8) {
         double ☃xxxxx = 0.09765625;
         double ☃xxxxxx = Math.abs(this.field_205200_i.func_151601_a((double)☃ * 0.09765625, (double)☃ * 0.09765625));
         ☃ = ☃xxxx * ☃xxxx * 1.2;
         double ☃xxxxxxx = Math.ceil(☃xxxxxx * 40.0) + 14.0;
         if (☃ > ☃xxxxxxx) {
            ☃ = ☃xxxxxxx;
         }

         if (☃xxx > 0.1F) {
            ☃ -= 2.0;
         }

         if (☃ > 2.0) {
            ☃x = (double)☃ - ☃ - 7.0;
            ☃ += (double)☃;
         } else {
            ☃ = 0.0;
         }
      }

      int ☃ = ☃ & 15;
      int ☃x = ☃ & 15;
      IBlockState ☃xx = ☃.func_203944_q().func_204109_b();
      IBlockState ☃xxx = ☃.func_203944_q().func_204108_a();
      int ☃xxxx = (int)(☃ / 3.0 + 3.0 + ☃.nextDouble() * 0.25);
      int ☃xxxxx = -1;
      int ☃xxxxxx = 0;
      int ☃xxxxxxx = 2 + ☃.nextInt(4);
      int ☃xxxxxxxx = ☃ + 18 + ☃.nextInt(10);

      for(int ☃xxxxxxxxx = Math.max(☃, (int)☃ + 1); ☃xxxxxxxxx >= 0; --☃xxxxxxxxx) {
         ☃xx.func_181079_c(☃, ☃xxxxxxxxx, ☃x);
         if (☃.func_180495_p(☃xx).func_196958_f() && ☃xxxxxxxxx < (int)☃ && ☃.nextDouble() > 0.01) {
            ☃.func_177436_a(☃xx, field_205192_a, false);
         } else if (☃.func_180495_p(☃xx).func_185904_a() == Material.field_151586_h
            && ☃xxxxxxxxx > (int)☃x
            && ☃xxxxxxxxx < ☃
            && ☃x != 0.0
            && ☃.nextDouble() > 0.15) {
            ☃.func_177436_a(☃xx, field_205192_a, false);
         }

         IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xx);
         if (☃xxxxxxxxxx.func_196958_f()) {
            ☃xxxxx = -1;
         } else if (☃xxxxxxxxxx.func_177230_c() == ☃.func_177230_c()) {
            if (☃xxxxx == -1) {
               if (☃xxxx <= 0) {
                  ☃xxx = field_205195_d;
                  ☃xx = ☃;
               } else if (☃xxxxxxxxx >= ☃ - 4 && ☃xxxxxxxxx <= ☃ + 1) {
                  ☃xxx = ☃.func_203944_q().func_204108_a();
                  ☃xx = ☃.func_203944_q().func_204109_b();
               }

               if (☃xxxxxxxxx < ☃ && (☃xxx == null || ☃xxx.func_196958_f())) {
                  if (☃.func_180626_a(☃xx.func_181079_c(☃, ☃xxxxxxxxx, ☃)) < 0.15F) {
                     ☃xxx = field_205197_f;
                  } else {
                     ☃xxx = ☃;
                  }
               }

               ☃xxxxx = ☃xxxx;
               if (☃xxxxxxxxx >= ☃ - 1) {
                  ☃.func_177436_a(☃xx, ☃xxx, false);
               } else if (☃xxxxxxxxx < ☃ - 7 - ☃xxxx) {
                  ☃xxx = field_205195_d;
                  ☃xx = ☃;
                  ☃.func_177436_a(☃xx, field_205196_e, false);
               } else {
                  ☃.func_177436_a(☃xx, ☃xx, false);
               }
            } else if (☃xxxxx > 0) {
               --☃xxxxx;
               ☃.func_177436_a(☃xx, ☃xx, false);
               if (☃xxxxx == 0 && ☃xx.func_177230_c() == Blocks.field_150354_m && ☃xxxx > 1) {
                  ☃xxxxx = ☃.nextInt(4) + Math.max(0, ☃xxxxxxxxx - 63);
                  ☃xx = ☃xx.func_177230_c() == Blocks.field_196611_F ? Blocks.field_180395_cM.func_176223_P() : Blocks.field_150322_A.func_176223_P();
               }
            }
         } else if (☃xxxxxxxxxx.func_177230_c() == Blocks.field_150403_cj && ☃xxxxxx <= ☃xxxxxxx && ☃xxxxxxxxx > ☃xxxxxxxx) {
            ☃.func_177436_a(☃xx, field_205193_b, false);
            ++☃xxxxxx;
         }
      }
   }

   @Override
   public void func_205548_a(long var1) {
      if (this.field_205201_j != ☃ || this.field_205199_h == null || this.field_205200_i == null) {
         Random ☃ = new SharedSeedRandom(☃);
         this.field_205199_h = new NoiseGeneratorPerlin(☃, 4);
         this.field_205200_i = new NoiseGeneratorPerlin(☃, 1);
      }

      this.field_205201_j = ☃;
   }
}
