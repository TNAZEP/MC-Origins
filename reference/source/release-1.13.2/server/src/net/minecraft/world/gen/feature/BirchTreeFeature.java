package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class BirchTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181629_a = Blocks.field_196619_M.func_176223_P();
   private static final IBlockState field_181630_b = Blocks.field_196647_Y.func_176223_P();
   private final boolean field_150531_a;

   public BirchTreeFeature(boolean var1, boolean var2) {
      super(☃);
      this.field_150531_a = ☃;
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = ☃.nextInt(3) + 5;
      if (this.field_150531_a) {
         ☃ += ☃.nextInt(7);
      }

      boolean ☃ = true;
      if (☃.func_177956_o() >= 1 && ☃.func_177956_o() + ☃ + 1 <= 256) {
         for(int ☃x = ☃.func_177956_o(); ☃x <= ☃.func_177956_o() + 1 + ☃; ++☃x) {
            int ☃xx = 1;
            if (☃x == ☃.func_177956_o()) {
               ☃xx = 0;
            }

            if (☃x >= ☃.func_177956_o() + 1 + ☃ - 2) {
               ☃xx = 2;
            }

            BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();

            for(int ☃xxx = ☃.func_177958_n() - ☃xx; ☃xxx <= ☃.func_177958_n() + ☃xx && ☃; ++☃xxx) {
               for(int ☃xxxx = ☃.func_177952_p() - ☃xx; ☃xxxx <= ☃.func_177952_p() + ☃xx && ☃; ++☃xxxx) {
                  if (☃x < 0 || ☃x >= 256) {
                     ☃ = false;
                  } else if (!this.func_150523_a(☃.func_180495_p(☃xx.func_181079_c(☃xxx, ☃x, ☃xxxx)).func_177230_c())) {
                     ☃ = false;
                  }
               }
            }
         }

         if (!☃) {
            return false;
         } else {
            Block ☃x = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
            if ((☃x == Blocks.field_196658_i || Block.func_196245_f(☃x) || ☃x == Blocks.field_150458_ak) && ☃.func_177956_o() < 256 - ☃ - 1) {
               this.func_175921_a(☃, ☃.func_177977_b());

               for(int ☃xx = ☃.func_177956_o() - 3 + ☃; ☃xx <= ☃.func_177956_o() + ☃; ++☃xx) {
                  int ☃xxx = ☃xx - (☃.func_177956_o() + ☃);
                  int ☃xxxx = 1 - ☃xxx / 2;

                  for(int ☃xxxxx = ☃.func_177958_n() - ☃xxxx; ☃xxxxx <= ☃.func_177958_n() + ☃xxxx; ++☃xxxxx) {
                     int ☃xxxxxx = ☃xxxxx - ☃.func_177958_n();

                     for(int ☃xxxxxxx = ☃.func_177952_p() - ☃xxxx; ☃xxxxxxx <= ☃.func_177952_p() + ☃xxxx; ++☃xxxxxxx) {
                        int ☃xxxxxxxx = ☃xxxxxxx - ☃.func_177952_p();
                        if (Math.abs(☃xxxxxx) != ☃xxxx || Math.abs(☃xxxxxxxx) != ☃xxxx || ☃.nextInt(2) != 0 && ☃xxx != 0) {
                           BlockPos ☃xxxxxxxxx = new BlockPos(☃xxxxx, ☃xx, ☃xxxxxxx);
                           IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxx);
                           if (☃xxxxxxxxxx.func_196958_f() || ☃xxxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                              this.func_202278_a(☃, ☃xxxxxxxxx, field_181630_b);
                           }
                        }
                     }
                  }
               }

               for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
                  IBlockState ☃xxx = ☃.func_180495_p(☃.func_177981_b(☃xx));
                  if (☃xxx.func_196958_f() || ☃xxx.func_203425_a(BlockTags.field_206952_E)) {
                     this.func_208520_a(☃, ☃, ☃.func_177981_b(☃xx), field_181629_a);
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
