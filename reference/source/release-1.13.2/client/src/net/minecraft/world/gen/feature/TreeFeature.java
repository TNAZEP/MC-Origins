package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class TreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181653_a = Blocks.field_196617_K.func_176223_P();
   private static final IBlockState field_181654_b = Blocks.field_196642_W.func_176223_P();
   protected final int field_76533_a;
   private final boolean field_76531_b;
   private final IBlockState field_76532_c;
   private final IBlockState field_76530_d;

   public TreeFeature(boolean var1) {
      this(☃, 4, field_181653_a, field_181654_b, false);
   }

   public TreeFeature(boolean var1, int var2, IBlockState var3, IBlockState var4, boolean var5) {
      super(☃);
      this.field_76533_a = ☃;
      this.field_76532_c = ☃;
      this.field_76530_d = ☃;
      this.field_76531_b = ☃;
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = this.func_208534_a(☃);
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
            if ((☃xx == Blocks.field_196658_i || Block.func_196245_f(☃xx) || ☃xx == Blocks.field_150458_ak) && ☃.func_177956_o() < 256 - ☃ - 1) {
               this.func_175921_a(☃, ☃.func_177977_b());
               int ☃xxx = 3;
               int ☃xxxx = 0;

               for(int ☃xxxxx = ☃.func_177956_o() - 3 + ☃; ☃xxxxx <= ☃.func_177956_o() + ☃; ++☃xxxxx) {
                  int ☃xxxxxx = ☃xxxxx - (☃.func_177956_o() + ☃);
                  int ☃xxxxxxx = 1 - ☃xxxxxx / 2;

                  for(int ☃xxxxxxxx = ☃.func_177958_n() - ☃xxxxxxx; ☃xxxxxxxx <= ☃.func_177958_n() + ☃xxxxxxx; ++☃xxxxxxxx) {
                     int ☃xxxxxxxxx = ☃xxxxxxxx - ☃.func_177958_n();

                     for(int ☃xxxxxxxxxx = ☃.func_177952_p() - ☃xxxxxxx; ☃xxxxxxxxxx <= ☃.func_177952_p() + ☃xxxxxxx; ++☃xxxxxxxxxx) {
                        int ☃xxxxxxxxxxx = ☃xxxxxxxxxx - ☃.func_177952_p();
                        if (Math.abs(☃xxxxxxxxx) != ☃xxxxxxx || Math.abs(☃xxxxxxxxxxx) != ☃xxxxxxx || ☃.nextInt(2) != 0 && ☃xxxxxx != 0) {
                           BlockPos ☃xxxxxxxxxxxx = new BlockPos(☃xxxxxxxx, ☃xxxxx, ☃xxxxxxxxxx);
                           IBlockState ☃xxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxx);
                           Material ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx.func_185904_a();
                           if (☃xxxxxxxxxxxxx.func_196958_f()
                              || ☃xxxxxxxxxxxxx.func_203425_a(BlockTags.field_206952_E)
                              || ☃xxxxxxxxxxxxxx == Material.field_151582_l) {
                              this.func_202278_a(☃, ☃xxxxxxxxxxxx, this.field_76530_d);
                           }
                        }
                     }
                  }
               }

               for(int ☃xxxxx = 0; ☃xxxxx < ☃; ++☃xxxxx) {
                  IBlockState ☃xxxxxx = ☃.func_180495_p(☃.func_177981_b(☃xxxxx));
                  Material ☃xxxxxxx = ☃xxxxxx.func_185904_a();
                  if (☃xxxxxx.func_196958_f() || ☃xxxxxx.func_203425_a(BlockTags.field_206952_E) || ☃xxxxxxx == Material.field_151582_l) {
                     this.func_208520_a(☃, ☃, ☃.func_177981_b(☃xxxxx), this.field_76532_c);
                     if (this.field_76531_b && ☃xxxxx > 0) {
                        if (☃.nextInt(3) > 0 && ☃.func_175623_d(☃.func_177982_a(-1, ☃xxxxx, 0))) {
                           this.func_181651_a(☃, ☃.func_177982_a(-1, ☃xxxxx, 0), BlockVine.field_176278_M);
                        }

                        if (☃.nextInt(3) > 0 && ☃.func_175623_d(☃.func_177982_a(1, ☃xxxxx, 0))) {
                           this.func_181651_a(☃, ☃.func_177982_a(1, ☃xxxxx, 0), BlockVine.field_176280_O);
                        }

                        if (☃.nextInt(3) > 0 && ☃.func_175623_d(☃.func_177982_a(0, ☃xxxxx, -1))) {
                           this.func_181651_a(☃, ☃.func_177982_a(0, ☃xxxxx, -1), BlockVine.field_176279_N);
                        }

                        if (☃.nextInt(3) > 0 && ☃.func_175623_d(☃.func_177982_a(0, ☃xxxxx, 1))) {
                           this.func_181651_a(☃, ☃.func_177982_a(0, ☃xxxxx, 1), BlockVine.field_176273_b);
                        }
                     }
                  }
               }

               if (this.field_76531_b) {
                  for(int ☃xxxxx = ☃.func_177956_o() - 3 + ☃; ☃xxxxx <= ☃.func_177956_o() + ☃; ++☃xxxxx) {
                     int ☃xxxxxx = ☃xxxxx - (☃.func_177956_o() + ☃);
                     int ☃xxxxxxx = 2 - ☃xxxxxx / 2;
                     BlockPos.MutableBlockPos ☃xxxxxxxx = new BlockPos.MutableBlockPos();

                     for(int ☃xxxxxxxxx = ☃.func_177958_n() - ☃xxxxxxx; ☃xxxxxxxxx <= ☃.func_177958_n() + ☃xxxxxxx; ++☃xxxxxxxxx) {
                        for(int ☃xxxxxxxxxx = ☃.func_177952_p() - ☃xxxxxxx; ☃xxxxxxxxxx <= ☃.func_177952_p() + ☃xxxxxxx; ++☃xxxxxxxxxx) {
                           ☃xxxxxxxx.func_181079_c(☃xxxxxxxxx, ☃xxxxx, ☃xxxxxxxxxx);
                           if (☃.func_180495_p(☃xxxxxxxx).func_203425_a(BlockTags.field_206952_E)) {
                              BlockPos ☃xxxxxxxxxxx = ☃xxxxxxxx.func_177976_e();
                              BlockPos ☃xxxxxxxxxxxx = ☃xxxxxxxx.func_177974_f();
                              BlockPos ☃xxxxxxxxxxxxx = ☃xxxxxxxx.func_177978_c();
                              BlockPos ☃xxxxxxxxxxxxxx = ☃xxxxxxxx.func_177968_d();
                              if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxxx).func_196958_f()) {
                                 this.func_181650_b(☃, ☃xxxxxxxxxxx, BlockVine.field_176278_M);
                              }

                              if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxxxx).func_196958_f()) {
                                 this.func_181650_b(☃, ☃xxxxxxxxxxxx, BlockVine.field_176280_O);
                              }

                              if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxxxxx).func_196958_f()) {
                                 this.func_181650_b(☃, ☃xxxxxxxxxxxxx, BlockVine.field_176279_N);
                              }

                              if (☃.nextInt(4) == 0 && ☃.func_180495_p(☃xxxxxxxxxxxxxx).func_196958_f()) {
                                 this.func_181650_b(☃, ☃xxxxxxxxxxxxxx, BlockVine.field_176273_b);
                              }
                           }
                        }
                     }
                  }

                  if (☃.nextInt(5) == 0 && ☃ > 5) {
                     for(int ☃xxxxx = 0; ☃xxxxx < 2; ++☃xxxxx) {
                        for(EnumFacing ☃xxxxxx : EnumFacing.Plane.HORIZONTAL) {
                           if (☃.nextInt(4 - ☃xxxxx) == 0) {
                              EnumFacing ☃xxxxxxx = ☃xxxxxx.func_176734_d();
                              this.func_181652_a(☃, ☃.nextInt(3), ☃.func_177982_a(☃xxxxxxx.func_82601_c(), ☃ - 5 + ☃xxxxx, ☃xxxxxxx.func_82599_e()), ☃xxxxxx);
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

   protected int func_208534_a(Random var1) {
      return this.field_76533_a + ☃.nextInt(3);
   }

   private void func_181652_a(IWorld var1, int var2, BlockPos var3, EnumFacing var4) {
      this.func_202278_a(
         ☃, ☃, Blocks.field_150375_by.func_176223_P().func_206870_a(BlockCocoa.field_176501_a, Integer.valueOf(☃)).func_206870_a(BlockCocoa.field_185512_D, ☃)
      );
   }

   private void func_181651_a(IWorld var1, BlockPos var2, BooleanProperty var3) {
      this.func_202278_a(☃, ☃, Blocks.field_150395_bd.func_176223_P().func_206870_a(☃, Boolean.valueOf(true)));
   }

   private void func_181650_b(IWorld var1, BlockPos var2, BooleanProperty var3) {
      this.func_181651_a(☃, ☃, ☃);
      int ☃ = 4;

      for(BlockPos var5 = ☃.func_177977_b(); ☃.func_180495_p(var5).func_196958_f() && ☃ > 0; --☃) {
         this.func_181651_a(☃, var5, ☃);
         var5 = var5.func_177977_b();
      }
   }
}
