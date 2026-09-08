package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class VoidStartPlatformFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      BlockPos ☃ = ☃.func_175694_M();
      int ☃x = 16;
      double ☃xx = ☃.func_177951_i(☃.func_177982_a(8, ☃.func_177956_o(), 8));
      if (☃xx > 1024.0) {
         return true;
      } else {
         BlockPos ☃ = new BlockPos(☃.func_177958_n() - 16, Math.max(☃.func_177956_o(), 4) - 1, ☃.func_177952_p() - 16);
         BlockPos ☃x = new BlockPos(☃.func_177958_n() + 16, Math.max(☃.func_177956_o(), 4) - 1, ☃.func_177952_p() + 16);
         BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos(☃);

         for(int ☃xxx = ☃.func_177952_p(); ☃xxx < ☃.func_177952_p() + 16; ++☃xxx) {
            for(int ☃xxxx = ☃.func_177958_n(); ☃xxxx < ☃.func_177958_n() + 16; ++☃xxxx) {
               if (☃xxx >= ☃.func_177952_p() && ☃xxx <= ☃x.func_177952_p() && ☃xxxx >= ☃.func_177958_n() && ☃xxxx <= ☃x.func_177958_n()) {
                  ☃xx.func_181079_c(☃xxxx, ☃xx.func_177956_o(), ☃xxx);
                  if (☃.func_177958_n() == ☃xxxx && ☃.func_177952_p() == ☃xxx) {
                     ☃.func_180501_a(☃xx, Blocks.field_150347_e.func_176223_P(), 2);
                  } else {
                     ☃.func_180501_a(☃xx, Blocks.field_150348_b.func_176223_P(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
