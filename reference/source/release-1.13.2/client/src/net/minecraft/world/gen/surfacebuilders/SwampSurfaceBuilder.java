package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class SwampSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
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
      double ☃ = Biome.field_180281_af.func_151601_a((double)☃ * 0.25, (double)☃ * 0.25);
      if (☃ > 0.0) {
         int ☃x = ☃ & 15;
         int ☃xx = ☃ & 15;
         BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

         for(int ☃xxxx = ☃; ☃xxxx >= 0; --☃xxxx) {
            ☃xxx.func_181079_c(☃x, ☃xxxx, ☃xx);
            if (!☃.func_180495_p(☃xxx).func_196958_f()) {
               if (☃xxxx == 62 && ☃.func_180495_p(☃xxx).func_177230_c() != ☃.func_177230_c()) {
                  ☃.func_177436_a(☃xxx, ☃, false);
                  if (☃ < 0.12) {
                     ☃.func_177436_a(☃xxx.func_196234_d(0, 1, 0), Blocks.field_196651_dG.func_176223_P(), false);
                  }
               }
               break;
            }
         }
      }

      Biome.field_203955_aj.func_205610_a_(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃);
   }
}
