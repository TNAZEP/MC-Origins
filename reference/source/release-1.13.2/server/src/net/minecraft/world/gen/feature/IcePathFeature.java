package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class IcePathFeature extends Feature<FeatureRadiusConfig> {
   private final Block field_150555_a = Blocks.field_150403_cj;

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, FeatureRadiusConfig var5) {
      while(☃.func_175623_d(☃) && ☃.func_177956_o() > 2) {
         ☃ = ☃.func_177977_b();
      }

      if (☃.func_180495_p(☃).func_177230_c() != Blocks.field_196604_cC) {
         return false;
      } else {
         int ☃ = ☃.nextInt(☃.field_202436_a) + 2;
         int ☃x = 1;

         for(int ☃xx = ☃.func_177958_n() - ☃; ☃xx <= ☃.func_177958_n() + ☃; ++☃xx) {
            for(int ☃xxx = ☃.func_177952_p() - ☃; ☃xxx <= ☃.func_177952_p() + ☃; ++☃xxx) {
               int ☃xxxx = ☃xx - ☃.func_177958_n();
               int ☃xxxxx = ☃xxx - ☃.func_177952_p();
               if (☃xxxx * ☃xxxx + ☃xxxxx * ☃xxxxx <= ☃ * ☃) {
                  for(int ☃xxxxxx = ☃.func_177956_o() - 1; ☃xxxxxx <= ☃.func_177956_o() + 1; ++☃xxxxxx) {
                     BlockPos ☃xxxxxxx = new BlockPos(☃xx, ☃xxxxxx, ☃xxx);
                     Block ☃xxxxxxxx = ☃.func_180495_p(☃xxxxxxx).func_177230_c();
                     if (Block.func_196245_f(☃xxxxxxxx) || ☃xxxxxxxx == Blocks.field_196604_cC || ☃xxxxxxxx == Blocks.field_150432_aD) {
                        ☃.func_180501_a(☃xxxxxxx, this.field_150555_a.func_176223_P(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
