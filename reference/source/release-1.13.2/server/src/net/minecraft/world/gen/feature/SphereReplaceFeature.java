package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class SphereReplaceFeature extends Feature<SphereReplaceConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, SphereReplaceConfig var5) {
      if (!☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a)) {
         return false;
      } else {
         int ☃ = 0;
         int ☃x = ☃.nextInt(☃.field_202432_b - 2) + 2;

         for(int ☃xx = ☃.func_177958_n() - ☃x; ☃xx <= ☃.func_177958_n() + ☃x; ++☃xx) {
            for(int ☃xxx = ☃.func_177952_p() - ☃x; ☃xxx <= ☃.func_177952_p() + ☃x; ++☃xxx) {
               int ☃xxxx = ☃xx - ☃.func_177958_n();
               int ☃xxxxx = ☃xxx - ☃.func_177952_p();
               if (☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx <= ☃x * ☃x) {
                  for(int ☃xxxxxx = ☃.func_177956_o() - ☃.field_202433_c; ☃xxxxxx <= ☃.func_177956_o() + ☃.field_202433_c; ++☃xxxxxx) {
                     BlockPos ☃xxxxxxx = new BlockPos(☃xx, ☃xxxxxx, ☃xxx);
                     Block ☃xxxxxxxx = ☃.func_180495_p(☃xxxxxxx).func_177230_c();
                     if (☃.field_202434_d.contains(☃xxxxxxxx)) {
                        ☃.func_180501_a(☃xxxxxxx, ☃.field_202431_a.func_176223_P(), 2);
                        ++☃;
                     }
                  }
               }
            }
         }

         return ☃ > 0;
      }
   }
}
