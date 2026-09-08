package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class NetherSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
   private static final IBlockState field_205554_c = Blocks.field_201941_jj.func_176223_P();
   private static final IBlockState field_205555_d = Blocks.field_150424_aL.func_176223_P();
   private static final IBlockState field_205556_e = Blocks.field_150351_n.func_176223_P();
   private static final IBlockState field_205557_f = Blocks.field_150425_aM.func_176223_P();
   protected long field_205552_a;
   protected NoiseGeneratorOctaves field_205553_b;

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
      int ☃ = ☃ + 1;
      int ☃x = ☃ & 15;
      int ☃xx = ☃ & 15;
      double ☃xxx = 0.03125;
      boolean ☃xxxx = this.field_205553_b.func_205563_a((double)☃ * 0.03125, (double)☃ * 0.03125, 0.0) + ☃.nextDouble() * 0.2 > 0.0;
      boolean ☃xxxxx = this.field_205553_b.func_205563_a((double)☃ * 0.03125, 109.0, (double)☃ * 0.03125) + ☃.nextDouble() * 0.2 > 0.0;
      int ☃xxxxxx = (int)(☃ / 3.0 + 3.0 + ☃.nextDouble() * 0.25);
      BlockPos.MutableBlockPos ☃xxxxxxx = new BlockPos.MutableBlockPos();
      int ☃xxxxxxxx = -1;
      IBlockState ☃xxxxxxxxx = field_205555_d;
      IBlockState ☃xxxxxxxxxx = field_205555_d;

      for(int ☃xxxxxxxxxxx = 127; ☃xxxxxxxxxxx >= 0; --☃xxxxxxxxxxx) {
         ☃xxxxxxx.func_181079_c(☃x, ☃xxxxxxxxxxx, ☃xx);
         IBlockState ☃xxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxx);
         if (☃xxxxxxxxxxxx.func_177230_c() != null && !☃xxxxxxxxxxxx.func_196958_f()) {
            if (☃xxxxxxxxxxxx.func_177230_c() == ☃.func_177230_c()) {
               if (☃xxxxxxxx == -1) {
                  if (☃xxxxxx <= 0) {
                     ☃xxxxxxxxx = field_205554_c;
                     ☃xxxxxxxxxx = field_205555_d;
                  } else if (☃xxxxxxxxxxx >= ☃ - 4 && ☃xxxxxxxxxxx <= ☃ + 1) {
                     ☃xxxxxxxxx = field_205555_d;
                     ☃xxxxxxxxxx = field_205555_d;
                     if (☃xxxxx) {
                        ☃xxxxxxxxx = field_205556_e;
                        ☃xxxxxxxxxx = field_205555_d;
                     }

                     if (☃xxxx) {
                        ☃xxxxxxxxx = field_205557_f;
                        ☃xxxxxxxxxx = field_205557_f;
                     }
                  }

                  if (☃xxxxxxxxxxx < ☃ && (☃xxxxxxxxx == null || ☃xxxxxxxxx.func_196958_f())) {
                     ☃xxxxxxxxx = ☃;
                  }

                  ☃xxxxxxxx = ☃xxxxxx;
                  if (☃xxxxxxxxxxx >= ☃ - 1) {
                     ☃.func_177436_a(☃xxxxxxx, ☃xxxxxxxxx, false);
                  } else {
                     ☃.func_177436_a(☃xxxxxxx, ☃xxxxxxxxxx, false);
                  }
               } else if (☃xxxxxxxx > 0) {
                  --☃xxxxxxxx;
                  ☃.func_177436_a(☃xxxxxxx, ☃xxxxxxxxxx, false);
               }
            }
         } else {
            ☃xxxxxxxx = -1;
         }
      }
   }

   @Override
   public void func_205548_a(long var1) {
      if (this.field_205552_a != ☃ || this.field_205553_b == null) {
         this.field_205553_b = new NoiseGeneratorOctaves(new SharedSeedRandom(☃), 4);
      }

      this.field_205552_a = ☃;
   }
}
