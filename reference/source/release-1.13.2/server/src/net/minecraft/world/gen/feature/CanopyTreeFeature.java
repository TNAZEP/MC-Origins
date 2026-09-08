package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class CanopyTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_181640_a = Blocks.field_196623_P.func_176223_P();
   private static final IBlockState field_181641_b = Blocks.field_196574_ab.func_176223_P();

   public CanopyTreeFeature(boolean var1) {
      super(☃);
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = ☃.nextInt(3) + ☃.nextInt(2) + 6;
      int ☃x = ☃.func_177958_n();
      int ☃xx = ☃.func_177956_o();
      int ☃xxx = ☃.func_177952_p();
      if (☃xx >= 1 && ☃xx + ☃ + 1 < 256) {
         BlockPos ☃xxxx = ☃.func_177977_b();
         Block ☃xxxxx = ☃.func_180495_p(☃xxxx).func_177230_c();
         if (☃xxxxx != Blocks.field_196658_i && !Block.func_196245_f(☃xxxxx)) {
            return false;
         } else if (!this.func_181638_a(☃, ☃, ☃)) {
            return false;
         } else {
            this.func_175921_a(☃, ☃xxxx);
            this.func_175921_a(☃, ☃xxxx.func_177974_f());
            this.func_175921_a(☃, ☃xxxx.func_177968_d());
            this.func_175921_a(☃, ☃xxxx.func_177968_d().func_177974_f());
            EnumFacing ☃xxxx = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
            int ☃xxxxx = ☃ - ☃.nextInt(4);
            int ☃xxxxxx = 2 - ☃.nextInt(3);
            int ☃xxxxxxx = ☃x;
            int ☃xxxxxxxx = ☃xxx;
            int ☃xxxxxxxxx = ☃xx + ☃ - 1;

            for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃; ++☃xxxxxxxxxx) {
               if (☃xxxxxxxxxx >= ☃xxxxx && ☃xxxxxx > 0) {
                  ☃xxxxxxx += ☃xxxx.func_82601_c();
                  ☃xxxxxxxx += ☃xxxx.func_82599_e();
                  --☃xxxxxx;
               }

               int ☃xxxxxxxxxxx = ☃xx + ☃xxxxxxxxxx;
               BlockPos ☃xxxxxxxxxxxx = new BlockPos(☃xxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxx);
               IBlockState ☃xxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxx);
               if (☃xxxxxxxxxxxxx.func_196958_f() || ☃xxxxxxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                  this.func_208533_a(☃, ☃, ☃xxxxxxxxxxxx);
                  this.func_208533_a(☃, ☃, ☃xxxxxxxxxxxx.func_177974_f());
                  this.func_208533_a(☃, ☃, ☃xxxxxxxxxxxx.func_177968_d());
                  this.func_208533_a(☃, ☃, ☃xxxxxxxxxxxx.func_177974_f().func_177968_d());
               }
            }

            for(int ☃xxxxxxxxxx = -2; ☃xxxxxxxxxx <= 0; ++☃xxxxxxxxxx) {
               for(int ☃xxxxxxxxxxx = -2; ☃xxxxxxxxxxx <= 0; ++☃xxxxxxxxxxx) {
                  int ☃xxxxxxxxxxxx = -1;
                  this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx, ☃xxxxxxxxx + ☃xxxxxxxxxxxx, ☃xxxxxxxx + ☃xxxxxxxxxxx);
                  this.func_202414_a(☃, 1 + ☃xxxxxxx - ☃xxxxxxxxxx, ☃xxxxxxxxx + ☃xxxxxxxxxxxx, ☃xxxxxxxx + ☃xxxxxxxxxxx);
                  this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx, ☃xxxxxxxxx + ☃xxxxxxxxxxxx, 1 + ☃xxxxxxxx - ☃xxxxxxxxxxx);
                  this.func_202414_a(☃, 1 + ☃xxxxxxx - ☃xxxxxxxxxx, ☃xxxxxxxxx + ☃xxxxxxxxxxxx, 1 + ☃xxxxxxxx - ☃xxxxxxxxxxx);
                  if ((☃xxxxxxxxxx > -2 || ☃xxxxxxxxxxx > -1) && (☃xxxxxxxxxx != -1 || ☃xxxxxxxxxxx != -2)) {
                     int var29 = 1;
                     this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx, ☃xxxxxxxxx + var29, ☃xxxxxxxx + ☃xxxxxxxxxxx);
                     this.func_202414_a(☃, 1 + ☃xxxxxxx - ☃xxxxxxxxxx, ☃xxxxxxxxx + var29, ☃xxxxxxxx + ☃xxxxxxxxxxx);
                     this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx, ☃xxxxxxxxx + var29, 1 + ☃xxxxxxxx - ☃xxxxxxxxxxx);
                     this.func_202414_a(☃, 1 + ☃xxxxxxx - ☃xxxxxxxxxx, ☃xxxxxxxxx + var29, 1 + ☃xxxxxxxx - ☃xxxxxxxxxxx);
                  }
               }
            }

            if (☃.nextBoolean()) {
               this.func_202414_a(☃, ☃xxxxxxx, ☃xxxxxxxxx + 2, ☃xxxxxxxx);
               this.func_202414_a(☃, ☃xxxxxxx + 1, ☃xxxxxxxxx + 2, ☃xxxxxxxx);
               this.func_202414_a(☃, ☃xxxxxxx + 1, ☃xxxxxxxxx + 2, ☃xxxxxxxx + 1);
               this.func_202414_a(☃, ☃xxxxxxx, ☃xxxxxxxxx + 2, ☃xxxxxxxx + 1);
            }

            for(int ☃xxxxxxxxxx = -3; ☃xxxxxxxxxx <= 4; ++☃xxxxxxxxxx) {
               for(int ☃xxxxxxxxxxx = -3; ☃xxxxxxxxxxx <= 4; ++☃xxxxxxxxxxx) {
                  if ((☃xxxxxxxxxx != -3 || ☃xxxxxxxxxxx != -3)
                     && (☃xxxxxxxxxx != -3 || ☃xxxxxxxxxxx != 4)
                     && (☃xxxxxxxxxx != 4 || ☃xxxxxxxxxxx != -3)
                     && (☃xxxxxxxxxx != 4 || ☃xxxxxxxxxxx != 4)
                     && (Math.abs(☃xxxxxxxxxx) < 3 || Math.abs(☃xxxxxxxxxxx) < 3)) {
                     this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxx + ☃xxxxxxxxxxx);
                  }
               }
            }

            for(int ☃xxxxxxxxxx = -1; ☃xxxxxxxxxx <= 2; ++☃xxxxxxxxxx) {
               for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx <= 2; ++☃xxxxxxxxxxx) {
                  if ((☃xxxxxxxxxx < 0 || ☃xxxxxxxxxx > 1 || ☃xxxxxxxxxxx < 0 || ☃xxxxxxxxxxx > 1) && ☃.nextInt(3) <= 0) {
                     int ☃xxxxxxxxxxxx = ☃.nextInt(3) + 2;

                     for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < ☃xxxxxxxxxxxx; ++☃xxxxxxxxxxxxx) {
                        this.func_208533_a(☃, ☃, new BlockPos(☃x + ☃xxxxxxxxxx, ☃xxxxxxxxx - ☃xxxxxxxxxxxxx - 1, ☃xxx + ☃xxxxxxxxxxx));
                     }

                     for(int ☃xxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxx <= 1; ++☃xxxxxxxxxxxxx) {
                        for(int ☃xxxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxxx <= 1; ++☃xxxxxxxxxxxxxx) {
                           this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx + ☃xxxxxxxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxx + ☃xxxxxxxxxxx + ☃xxxxxxxxxxxxxx);
                        }
                     }

                     for(int ☃xxxxxxxxxxxxx = -2; ☃xxxxxxxxxxxxx <= 2; ++☃xxxxxxxxxxxxx) {
                        for(int ☃xxxxxxxxxxxxxx = -2; ☃xxxxxxxxxxxxxx <= 2; ++☃xxxxxxxxxxxxxx) {
                           if (Math.abs(☃xxxxxxxxxxxxx) != 2 || Math.abs(☃xxxxxxxxxxxxxx) != 2) {
                              this.func_202414_a(☃, ☃xxxxxxx + ☃xxxxxxxxxx + ☃xxxxxxxxxxxxx, ☃xxxxxxxxx - 1, ☃xxxxxxxx + ☃xxxxxxxxxxx + ☃xxxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean func_181638_a(IBlockReader var1, BlockPos var2, int var3) {
      int ☃ = ☃.func_177958_n();
      int ☃x = ☃.func_177956_o();
      int ☃xx = ☃.func_177952_p();
      BlockPos.MutableBlockPos ☃xxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxx = 0; ☃xxxx <= ☃ + 1; ++☃xxxx) {
         int ☃xxxxx = 1;
         if (☃xxxx == 0) {
            ☃xxxxx = 0;
         }

         if (☃xxxx >= ☃ - 1) {
            ☃xxxxx = 2;
         }

         for(int ☃xxxxx = -☃xxxxx; ☃xxxxx <= ☃xxxxx; ++☃xxxxx) {
            for(int ☃xxxxxx = -☃xxxxx; ☃xxxxxx <= ☃xxxxx; ++☃xxxxxx) {
               if (!this.func_150523_a(☃.func_180495_p(☃xxx.func_181079_c(☃ + ☃xxxxx, ☃x + ☃xxxx, ☃xx + ☃xxxxxx)).func_177230_c())) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private void func_208533_a(Set<BlockPos> var1, IWorld var2, BlockPos var3) {
      if (this.func_150523_a(☃.func_180495_p(☃).func_177230_c())) {
         this.func_208520_a(☃, ☃, ☃, field_181640_a);
      }
   }

   private void func_202414_a(IWorld var1, int var2, int var3, int var4) {
      BlockPos ☃ = new BlockPos(☃, ☃, ☃);
      if (☃.func_180495_p(☃).func_196958_f()) {
         this.func_202278_a(☃, ☃, field_181641_b);
      }
   }
}
