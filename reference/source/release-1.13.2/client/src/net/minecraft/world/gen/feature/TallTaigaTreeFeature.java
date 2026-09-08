package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class TallTaigaTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181645_a = Blocks.field_196618_L.func_176223_P();
   private static final IBlockState field_181646_b = Blocks.field_196645_X.func_176223_P();

   public TallTaigaTreeFeature(boolean var1) {
      super(☃);
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = ☃.nextInt(4) + 6;
      int ☃x = 1 + ☃.nextInt(2);
      int ☃xx = ☃ - ☃x;
      int ☃xxx = 2 + ☃.nextInt(2);
      boolean ☃xxxx = true;
      if (☃.func_177956_o() >= 1 && ☃.func_177956_o() + ☃ + 1 <= 256) {
         for(int ☃xxxxx = ☃.func_177956_o(); ☃xxxxx <= ☃.func_177956_o() + 1 + ☃ && ☃xxxx; ++☃xxxxx) {
            int ☃xxxxxx;
            if (☃xxxxx - ☃.func_177956_o() < ☃x) {
               ☃xxxxxx = 0;
            } else {
               ☃xxxxxx = ☃xxx;
            }

            BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxxxxx = ☃.func_177958_n() - ☃xxxxxx; ☃xxxxxxx <= ☃.func_177958_n() + ☃xxxxxx && ☃xxxx; ++☃xxxxxxx) {
               for(int ☃xxxxxxxx = ☃.func_177952_p() - ☃xxxxxx; ☃xxxxxxxx <= ☃.func_177952_p() + ☃xxxxxx && ☃xxxx; ++☃xxxxxxxx) {
                  if (☃xxxxx >= 0 && ☃xxxxx < 256) {
                     IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxxxx, ☃xxxxxxxx));
                     if (!☃xxxxxxxxx.func_196958_f() && !☃xxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                        ☃xxxx = false;
                     }
                  } else {
                     ☃xxxx = false;
                  }
               }
            }
         }

         if (!☃xxxx) {
            return false;
         } else {
            Block ☃xxxxx = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
            if ((☃xxxxx == Blocks.field_196658_i || Block.func_196245_f(☃xxxxx) || ☃xxxxx == Blocks.field_150458_ak) && ☃.func_177956_o() < 256 - ☃ - 1) {
               this.func_175921_a(☃, ☃.func_177977_b());
               int ☃xxxxxx = ☃.nextInt(2);
               int ☃xxxxxxx = 1;
               int ☃xxxxxxxx = 0;

               for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx <= ☃xx; ++☃xxxxxxxxx) {
                  int ☃xxxxxxxxxx = ☃.func_177956_o() + ☃ - ☃xxxxxxxxx;

                  for(int ☃xxxxxxxxxxx = ☃.func_177958_n() - ☃xxxxxx; ☃xxxxxxxxxxx <= ☃.func_177958_n() + ☃xxxxxx; ++☃xxxxxxxxxxx) {
                     int ☃xxxxxxxxxxxx = ☃xxxxxxxxxxx - ☃.func_177958_n();

                     for(int ☃xxxxxxxxxxxxx = ☃.func_177952_p() - ☃xxxxxx; ☃xxxxxxxxxxxxx <= ☃.func_177952_p() + ☃xxxxxx; ++☃xxxxxxxxxxxxx) {
                        int ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx - ☃.func_177952_p();
                        if (Math.abs(☃xxxxxxxxxxxx) != ☃xxxxxx || Math.abs(☃xxxxxxxxxxxxxx) != ☃xxxxxx || ☃xxxxxx <= 0) {
                           BlockPos ☃xxxxxxxxxxxxxxx = new BlockPos(☃xxxxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxxxx);
                           if (!☃.func_180495_p(☃xxxxxxxxxxxxxxx).func_200015_d(☃, ☃xxxxxxxxxxxxxxx)) {
                              this.func_202278_a(☃, ☃xxxxxxxxxxxxxxx, field_181646_b);
                           }
                        }
                     }
                  }

                  if (☃xxxxxx >= ☃xxxxxxx) {
                     ☃xxxxxx = ☃xxxxxxxx;
                     ☃xxxxxxxx = 1;
                     if (++☃xxxxxxx > ☃xxx) {
                        ☃xxxxxxx = ☃xxx;
                     }
                  } else {
                     ++☃xxxxxx;
                  }
               }

               int ☃xxxxxxxxx = ☃.nextInt(3);

               for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃ - ☃xxxxxxxxx; ++☃xxxxxxxxxx) {
                  IBlockState ☃xxxxxxxxxxx = ☃.func_180495_p(☃.func_177981_b(☃xxxxxxxxxx));
                  if (☃xxxxxxxxxxx.func_196958_f() || ☃xxxxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                     this.func_208520_a(☃, ☃, ☃.func_177981_b(☃xxxxxxxxxx), field_181645_a);
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
