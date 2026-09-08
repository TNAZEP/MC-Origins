package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SavannaTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181643_a = Blocks.field_196621_O.func_176223_P();
   private static final IBlockState field_181644_b = Blocks.field_196572_aa.func_176223_P();

   public SavannaTreeFeature(boolean var1) {
      super(☃);
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = ☃.nextInt(3) + ☃.nextInt(3) + 5;
      boolean ☃x = true;
      if (☃.func_177956_o() >= 1 && ☃.func_177956_o() + ☃ + 1 <= 256) {
         for(int ☃xx = ☃.func_177956_o(); ☃xx <= ☃.func_177956_o() + 1 + ☃; ++☃xx) {
            int ☃xxx = 1;
            if (☃xx == ☃.func_177956_o()) {
               ☃xxx = 0;
            }

            if (☃xx >= ☃.func_177956_o() + 1 + ☃ - 2) {
               ☃xxx = 2;
            }

            BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

            for(int ☃xxxx = ☃.func_177958_n() - ☃xxx; ☃xxxx <= ☃.func_177958_n() + ☃xxx && ☃x; ++☃xxxx) {
               for(int ☃xxxxx = ☃.func_177952_p() - ☃xxx; ☃xxxxx <= ☃.func_177952_p() + ☃xxx && ☃x; ++☃xxxxx) {
                  if (☃xx < 0 || ☃xx >= 256) {
                     ☃x = false;
                  } else if (!this.func_150523_a(☃.func_180495_p(☃xxx.func_181079_c(☃xxxx, ☃xx, ☃xxxxx)).func_177230_c())) {
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
               EnumFacing ☃xxx = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
               int ☃xxxx = ☃ - ☃.nextInt(4) - 1;
               int ☃xxxxx = 3 - ☃.nextInt(3);
               int ☃xxxxxx = ☃.func_177958_n();
               int ☃xxxxxxx = ☃.func_177952_p();
               int ☃xxxxxxxx = 0;

               for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃; ++☃xxxxxxxxx) {
                  int ☃xxxxxxxxxx = ☃.func_177956_o() + ☃xxxxxxxxx;
                  if (☃xxxxxxxxx >= ☃xxxx && ☃xxxxx > 0) {
                     ☃xxxxxx += ☃xxx.func_82601_c();
                     ☃xxxxxxx += ☃xxx.func_82599_e();
                     --☃xxxxx;
                  }

                  BlockPos ☃xxxxxxxxxx = new BlockPos(☃xxxxxx, ☃xxxxxxxxxx, ☃xxxxxxx);
                  IBlockState ☃xxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxx);
                  if (☃xxxxxxxxxxx.func_196958_f() || ☃xxxxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                     this.func_208532_a(☃, ☃, ☃xxxxxxxxxx);
                     ☃xxxxxxxx = ☃xxxxxxxxxx;
                  }
               }

               BlockPos ☃xxxxxxxxx = new BlockPos(☃xxxxxx, ☃xxxxxxxx, ☃xxxxxxx);

               for(int ☃xxxxxxxxxx = -3; ☃xxxxxxxxxx <= 3; ++☃xxxxxxxxxx) {
                  for(int ☃xxxxxxxxxxx = -3; ☃xxxxxxxxxxx <= 3; ++☃xxxxxxxxxxx) {
                     if (Math.abs(☃xxxxxxxxxx) != 3 || Math.abs(☃xxxxxxxxxxx) != 3) {
                        this.func_175924_b(☃, ☃xxxxxxxxx.func_177982_a(☃xxxxxxxxxx, 0, ☃xxxxxxxxxxx));
                     }
                  }
               }

               ☃xxxxxxxxx = ☃xxxxxxxxx.func_177984_a();

               for(int ☃xxxxxxxxxx = -1; ☃xxxxxxxxxx <= 1; ++☃xxxxxxxxxx) {
                  for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx <= 1; ++☃xxxxxxxxxxx) {
                     this.func_175924_b(☃, ☃xxxxxxxxx.func_177982_a(☃xxxxxxxxxx, 0, ☃xxxxxxxxxxx));
                  }
               }

               this.func_175924_b(☃, ☃xxxxxxxxx.func_177965_g(2));
               this.func_175924_b(☃, ☃xxxxxxxxx.func_177985_f(2));
               this.func_175924_b(☃, ☃xxxxxxxxx.func_177970_e(2));
               this.func_175924_b(☃, ☃xxxxxxxxx.func_177964_d(2));
               ☃xxxxxx = ☃.func_177958_n();
               ☃xxxxxxx = ☃.func_177952_p();
               EnumFacing ☃xxxxxxxxxx = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
               if (☃xxxxxxxxxx != ☃xxx) {
                  int ☃xxxxxxxxxxx = ☃xxxx - ☃.nextInt(2) - 1;
                  int ☃xxxxxxxxxxxx = 1 + ☃.nextInt(3);
                  ☃xxxxxxxx = 0;

                  for(int ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxx; ☃xxxxxxxxxxxxx < ☃ && ☃xxxxxxxxxxxx > 0; --☃xxxxxxxxxxxx) {
                     if (☃xxxxxxxxxxxxx >= 1) {
                        int ☃xxxxxxxxxxxxxx = ☃.func_177956_o() + ☃xxxxxxxxxxxxx;
                        ☃xxxxxx += ☃xxxxxxxxxx.func_82601_c();
                        ☃xxxxxxx += ☃xxxxxxxxxx.func_82599_e();
                        BlockPos ☃xxxxxxxxxxxxxxx = new BlockPos(☃xxxxxx, ☃xxxxxxxxxxxxxx, ☃xxxxxxx);
                        IBlockState ☃xxxxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxxxxx);
                        if (☃xxxxxxxxxxxxxxxx.func_196958_f() || ☃xxxxxxxxxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                           this.func_208532_a(☃, ☃, ☃xxxxxxxxxxxxxxx);
                           ☃xxxxxxxx = ☃xxxxxxxxxxxxxx;
                        }
                     }

                     ++☃xxxxxxxxxxxxx;
                  }

                  if (☃xxxxxxxx > 0) {
                     BlockPos ☃xxxxxxxxxxxxx = new BlockPos(☃xxxxxx, ☃xxxxxxxx, ☃xxxxxxx);

                     for(int ☃xxxxxxxxxxxxxx = -2; ☃xxxxxxxxxxxxxx <= 2; ++☃xxxxxxxxxxxxxx) {
                        for(int ☃xxxxxxxxxxxxxxx = -2; ☃xxxxxxxxxxxxxxx <= 2; ++☃xxxxxxxxxxxxxxx) {
                           if (Math.abs(☃xxxxxxxxxxxxxx) != 2 || Math.abs(☃xxxxxxxxxxxxxxx) != 2) {
                              this.func_175924_b(☃, ☃xxxxxxxxxxxxx.func_177982_a(☃xxxxxxxxxxxxxx, 0, ☃xxxxxxxxxxxxxxx));
                           }
                        }
                     }

                     ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_177984_a();

                     for(int ☃xxxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxxx <= 1; ++☃xxxxxxxxxxxxxx) {
                        for(int ☃xxxxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxxxx <= 1; ++☃xxxxxxxxxxxxxxx) {
                           this.func_175924_b(☃, ☃xxxxxxxxxxxxx.func_177982_a(☃xxxxxxxxxxxxxx, 0, ☃xxxxxxxxxxxxxxx));
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

   private void func_208532_a(Set<BlockPos> var1, IWorld var2, BlockPos var3) {
      this.func_208520_a(☃, ☃, ☃, field_181643_a);
   }

   private void func_175924_b(IWorld var1, BlockPos var2) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      if (☃.func_196958_f() || ☃.func_203425_a(BlockTags.field_206952_E)) {
         this.func_202278_a(☃, ☃, field_181644_b);
      }
   }
}
