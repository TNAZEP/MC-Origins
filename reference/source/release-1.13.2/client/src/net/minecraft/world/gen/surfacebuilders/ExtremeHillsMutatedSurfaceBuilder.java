package net.minecraft.world.gen.surfacebuilders;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public class ExtremeHillsMutatedSurfaceBuilder implements ISurfaceBuilder<SurfaceBuilderConfig> {
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
      if (☃ < -1.0 || ☃ > 2.0) {
         Biome.field_203955_aj.func_205610_a_(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, Biome.field_203947_ab);
      } else if (☃ > 1.0) {
         Biome.field_203955_aj.func_205610_a_(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, Biome.field_203946_aa);
      } else {
         Biome.field_203955_aj.func_205610_a_(☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, ☃, Biome.field_203961_Z);
      }
   }
}
