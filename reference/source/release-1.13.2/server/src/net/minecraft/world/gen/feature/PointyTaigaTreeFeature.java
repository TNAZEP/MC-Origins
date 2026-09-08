package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class PointyTaigaTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181636_a = Blocks.field_196618_L.func_176223_P();
   private static final IBlockState field_181637_b = Blocks.field_196645_X.func_176223_P();

   public PointyTaigaTreeFeature() {
      super(false);
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = ☃.nextInt(5) + 7;
      int ☃x = ☃ - ☃.nextInt(2) - 3;
      int ☃xx = ☃ - ☃x;
      int ☃xxx = 1 + ☃.nextInt(☃xx + 1);
      if (☃.func_177956_o() >= 1 && ☃.func_177956_o() + ☃ + 1 <= 256) {
         boolean ☃xxxx = true;

         for(int ☃xxxxx = ☃.func_177956_o(); ☃xxxxx <= ☃.func_177956_o() + 1 + ☃ && ☃xxxx; ++☃xxxxx) {
            int ☃xxxxxx = 1;
            if (☃xxxxx - ☃.func_177956_o() < ☃x) {
               ☃xxxxxx = 0;
            } else {
               ☃xxxxxx = ☃xxx;
            }

            BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxxxxx = ☃.func_177958_n() - ☃xxxxxx; ☃xxxxxxx <= ☃.func_177958_n() + ☃xxxxxx && ☃xxxx; ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = ☃.func_177952_p() - ☃xxxxxx; ☃xxxxxxxx <= ☃.func_177952_p() + ☃xxxxxx && ☃xxxx; ++☃xxxxxxxx) {
                  if (☃xxxxx < 0 || ☃xxxxx >= 256) {
                     ☃xxxx = false;
                  } else if (!this.func_150523_a(☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxx, ☃xxxxxxxx)).func_177230_c())) {
                     ☃xxxx = false;
                  }
               }
            }
         }

         if (!☃xxxx) {
            return false;
         } else {
            Block ☃xxxxx = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
            if ((☃xxxxx == Blocks.field_196658_i || Block.func_196245_f(☃xxxxx)) && ☃.func_177956_o() < 256 - ☃ - 1) {
               this.func_175921_a(☃, ☃.func_177977_b());
               int ☃xxxxxx = 0;

               for(int ☃xxxxxxx = ☃.func_177956_o() + ☃; ☃xxxxxxx >= ☃.func_177956_o() + ☃x; --☃xxxxxxx) {
                  for(int ☃xxxxxxxx = ☃.func_177958_n() - ☃xxxxxx; ☃xxxxxxxx <= ☃.func_177958_n() + ☃xxxxxx; ++☃xxxxxxxx) {
                     int ☃xxxxxxxxx = ☃xxxxxxxx - ☃.func_177958_n();

                     for(int ☃xxxxxxxxxx = ☃.func_177952_p() - ☃xxxxxx; ☃xxxxxxxxxx <= ☃.func_177952_p() + ☃xxxxxx; ++☃xxxxxxxxxx) {
                        int ☃xxxxxxxxxxx = ☃xxxxxxxxxx - ☃.func_177952_p();
                        if (Math.abs(☃xxxxxxxxx) != ☃xxxxxx || Math.abs(☃xxxxxxxxxxx) != ☃xxxxxx || ☃xxxxxx <= 0) {
                           BlockPos ☃xxxxxxxxxxxx = new BlockPos(☃xxxxxxxx, ☃xxxxxxx, ☃xxxxxxxxxx);
                           if (!☃.func_180495_p(☃xxxxxxxxxxxx).func_200015_d(☃, ☃xxxxxxxxxxxx)) {
                              this.func_202278_a(☃, ☃xxxxxxxxxxxx, field_181637_b);
                           }
                        }
                     }
                  }

                  if (☃xxxxxx >= 1 && ☃xxxxxxx == ☃.func_177956_o() + ☃x + 1) {
                     --☃xxxxxx;
                  } else if (☃xxxxxx < ☃xxx) {
                     ++☃xxxxxx;
                  }
               }

               for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃ - 1; ++☃xxxxxxx) {
                  IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃.func_177981_b(☃xxxxxxx));
                  if (☃xxxxxxxx.func_196958_f() || ☃xxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                     this.func_208520_a(☃, ☃, ☃.func_177981_b(☃xxxxxxx), field_181636_a);
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }
}
