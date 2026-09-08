package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SwampTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181648_a = Blocks.field_196617_K.func_176223_P();
   private static final IBlockState field_181649_b = Blocks.field_196642_W.func_176223_P();

   public SwampTreeFeature() {
      super(false);
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = ☃.nextInt(4) + 5;

      while(☃.func_204610_c(☃.func_177977_b()).func_206884_a(FluidTags.field_206959_a)) {
         ☃ = ☃.func_177977_b();
      }

      boolean ☃x = true;
      if (☃.func_177956_o() >= 1 && ☃.func_177956_o() + ☃ + 1 <= 256) {
         for(int ☃xx = ☃.func_177956_o(); ☃xx <= ☃.func_177956_o() + 1 + ☃; ++☃xx) {
            int ☃xxx = 1;
            if (☃xx == ☃.func_177956_o()) {
               ☃xxx = 0;
            }

            if (☃xx >= ☃.func_177956_o() + 1 + ☃ - 2) {
               ☃xxx = 3;
            }

            BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxx = ☃.func_177958_n() - ☃xxx; ☃xxxx <= ☃.func_177958_n() + ☃xxx && ☃x; ++☃xxxx) {
               for(int ☃xxxxx = ☃.func_177952_p() - ☃xxx; ☃xxxxx <= ☃.func_177952_p() + ☃xxx && ☃x; ++☃xxxxx) {
                  if (☃xx >= 0 && ☃xx < 256) {
                     IBlockState ☃xxxxxx = ☃.func_180495_p(☃xxx.func_181079_c(☃xxxx, ☃xx, ☃xxxxx));
                     Block ☃xxxxxxx = ☃xxxxxx.func_177230_c();
                     if (!☃xxxxxx.func_196958_f() && !☃xxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                        if (☃xxxxxxx == Blocks.field_150355_j) {
                           if (☃xx > ☃.func_177956_o()) {
                              ☃x = false;
                           }
                        } else {
                           ☃x = false;
                        }
                     }
                  } else {
                     ☃x = false;
                  }
               }
            }
         }

         if (!☃x) {
            return false;
         } else {
            Block ☃xx = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
            if ((☃xx == Blocks.field_196658_i || Block.func_196245_f(☃xx)) && ☃.func_177956_o() < 256 - ☃ - 1) {
               this.func_175921_a(☃, ☃.func_177977_b());

               for(int ☃xxx = ☃.func_177956_o() - 3 + ☃; ☃xxx <= ☃.func_177956_o() + ☃; ++☃xxx) {
                  int ☃xxxx = ☃xxx - (☃.func_177956_o() + ☃);
                  int ☃xxxxx = 2 - ☃xxxx / 2;

                  for(int ☃xxxxxx = ☃.func_177958_n() - ☃xxxxx; ☃xxxxxx <= ☃.func_177958_n() + ☃xxxxx; ++☃xxxxxx) {
                     int ☃xxxxxxx = ☃xxxxxx - ☃.func_177958_n();

                     for(int ☃xxxxxxxx = ☃.func_177952_p() - ☃xxxxx; ☃xxxxxxxx <= ☃.func_177952_p() + ☃xxxxx; ++☃xxxxxxxx) {
                        int ☃xxxxxxxxx = ☃xxxxxxxx - ☃.func_177952_p();
                        if (Math.abs(☃xxxxxxx) != ☃xxxxx || Math.abs(☃xxxxxxxxx) != ☃xxxxx || ☃.nextInt(2) != 0 && ☃xxxx != 0) {
                           BlockPos ☃xxxxxxxxxx = new BlockPos(☃xxxxxx, ☃xxx, ☃xxxxxxxx);
                           if (!☃.func_180495_p(☃xxxxxxxxxx).func_200015_d(☃, ☃xxxxxxxxxx)) {
                              this.func_202278_a(☃, ☃xxxxxxxxxx, field_181649_b);
                           }
                        }
                     }
                  }
               }

               for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
                  IBlockState ☃xxxx = ☃.func_180495_p(☃.func_177981_b(☃xxx));
                  Block ☃xxxxx = ☃xxxx.func_177230_c();
                  if (☃xxxx.func_196958_f() || ☃xxxx.func_203425_a(BlockTags.field_206952_E) || ☃xxxxx == Blocks.field_150355_j) {
                     this.func_208520_a(☃, ☃, ☃.func_177981_b(☃xxx), field_181648_a);
                  }
               }

               for(int ☃xxx = ☃.func_177956_o() - 3 + ☃; ☃xxx <= ☃.func_177956_o() + ☃; ++☃xxx) {
                  int ☃xxxx = ☃xxx - (☃.func_177956_o() + ☃);
                  int ☃xxxxx = 2 - ☃xxxx / 2;
                  BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

                  for(int ☃xxxxxxx = ☃.func_177958_n() - ☃xxxxx; ☃xxxxxxx <= ☃.func_177958_n() + ☃xxxxx; ++☃xxxxxxx) {
                     for(int ☃xxxxxxxx = ☃.func_177952_p() - ☃xxxxx; ☃xxxxxxxx <= ☃.func_177952_p() + ☃xxxxx; ++☃xxxxxxxx) {
                        ☃xxxxxx.func_181079_c(☃xxxxxxx, ☃xxx, ☃xxxxxxxx);
                        if (☃.func_180495_p(☃xxxxxx).func_203425_a(BlockTags.field_206952_E)) {
                           BlockPos ☃xxxxxxxxx = ☃xxxxxx.func_177976_e();
                           BlockPos ☃xxxxxxxxxx = ☃xxxxxx.func_177974_f();
                           BlockPos ☃xxxxxxxxxxx = ☃xxxxxx.func_177978_c();
                           BlockPos ☃xxxxxxxxxxxx = ☃xxxxxx.func_177968_d();
                           if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxx).func_196958_f()) {
                              this.func_181647_a(☃, ☃xxxxxxxxx, BlockVine.field_176278_M);
                           }

                           if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxx).func_196958_f()) {
                              this.func_181647_a(☃, ☃xxxxxxxxxx, BlockVine.field_176280_O);
                           }

                           if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxxx).func_196958_f()) {
                              this.func_181647_a(☃, ☃xxxxxxxxxxx, BlockVine.field_176279_N);
                           }

                           if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxxxx).func_196958_f()) {
                              this.func_181647_a(☃, ☃xxxxxxxxxxxx, BlockVine.field_176273_b);
                           }
                        }
                     }
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

   private void func_181647_a(IWorld var1, BlockPos var2, BooleanProperty var3) {
      IBlockState ☃ = Blocks.field_150395_bd.func_176223_P().func_206870_a(☃, Boolean.valueOf(true));
      this.func_202278_a(☃, ☃, ☃);
      int ☃x = 4;

      for(BlockPos var6 = ☃.func_177977_b(); ☃.func_180495_p(var6).func_196958_f() && ☃x > 0; --☃x) {
         this.func_202278_a(☃, var6, ☃);
         var6 = var6.func_177977_b();
      }
   }
}
