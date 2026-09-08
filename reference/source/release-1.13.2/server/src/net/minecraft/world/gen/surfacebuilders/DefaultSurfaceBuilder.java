package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class DefaultSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
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
      this.func_206967_a(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃.func_204108_a(), ☃.func_204109_b(), ☃.func_204110_c(), ☃);
   }

   protected void func_206967_a(
      Random var1,
      IChunk var2,
      Biome var3,
      int var4,
      int var5,
      int var6,
      double var7,
      IBlockState var9,
      IBlockState var10,
      IBlockState var11,
      IBlockState var12,
      IBlockState var13,
      int var14
   ) {
      IBlockState ☃ = ☃;
      IBlockState ☃x = ☃;
      BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();
      int ☃xxx = -1;
      int ☃xxxx = (int)(☃ / 3.0 + 3.0 + ☃.nextDouble() * 0.25);
      int ☃xxxxx = ☃ & 15;
      int ☃xxxxxx = ☃ & 15;

      for(int ☃xxxxxxx = ☃; ☃xxxxxxx >= 0; --☃xxxxxxx) {
         ☃xx.func_181079_c(☃xxxxx, ☃xxxxxxx, ☃xxxxxx);
         IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃xx);
         if (☃xxxxxxxx.func_196958_f()) {
            ☃xxx = -1;
         } else if (☃xxxxxxxx.func_177230_c() == ☃.func_177230_c()) {
            if (☃xxx == -1) {
               if (☃xxxx <= 0) {
                  ☃ = Blocks.field_150350_a.func_176223_P();
                  ☃x = ☃;
               } else if (☃xxxxxxx >= ☃ - 4 && ☃xxxxxxx <= ☃ + 1) {
                  ☃ = ☃;
                  ☃x = ☃;
               }

               if (☃xxxxxxx < ☃ && (☃ == null || ☃.func_196958_f())) {
                  if (☃.func_180626_a(☃xx.func_181079_c(☃, ☃xxxxxxx, ☃)) < 0.15F) {
                     ☃ = Blocks.field_150432_aD.func_176223_P();
                  } else {
                     ☃ = ☃;
                  }

                  ☃xx.func_181079_c(☃xxxxx, ☃xxxxxxx, ☃xxxxxx);
               }

               ☃xxx = ☃xxxx;
               if (☃xxxxxxx >= ☃ - 1) {
                  ☃.func_177436_a(☃xx, ☃, false);
               } else if (☃xxxxxxx < ☃ - 7 - ☃xxxx) {
                  ☃ = Blocks.field_150350_a.func_176223_P();
                  ☃x = ☃;
                  ☃.func_177436_a(☃xx, ☃, false);
               } else {
                  ☃.func_177436_a(☃xx, ☃x, false);
               }
            } else if (☃xxx > 0) {
               --☃xxx;
               ☃.func_177436_a(☃xx, ☃x, false);
               if (☃xxx == 0 && ☃x.func_177230_c() == Blocks.field_150354_m && ☃xxxx > 1) {
                  ☃xxx = ☃.nextInt(4) + Math.max(0, ☃xxxxxxx - 63);
                  ☃x = ☃x.func_177230_c() == Blocks.field_196611_F ? Blocks.field_180395_cM.func_176223_P() : Blocks.field_150322_A.func_176223_P();
               }
            }
         }
      }
   }
}
