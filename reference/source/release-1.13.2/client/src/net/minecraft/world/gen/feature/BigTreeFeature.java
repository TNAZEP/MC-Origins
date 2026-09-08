package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public class BigTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState field_208530_a = Blocks.field_196617_K.func_176223_P();
   private static final IBlockState field_208531_b = Blocks.field_196642_W.func_176223_P();

   public BigTreeFeature(boolean var1) {
      super(☃);
   }

   private void func_208529_a(IWorld var1, BlockPos var2, float var3) {
      int ☃ = (int)((double)☃ + 0.618);

      for(int ☃x = -☃; ☃x <= ☃; ++☃x) {
         for(int ☃xx = -☃; ☃xx <= ☃; ++☃xx) {
            if (Math.pow((double)Math.abs(☃x) + 0.5, 2.0) + Math.pow((double)Math.abs(☃xx) + 0.5, 2.0) <= (double)(☃ * ☃)) {
               BlockPos ☃xxx = ☃.func_177982_a(☃x, 0, ☃xx);
               IBlockState ☃xxxx = ☃.func_180495_p(☃xxx);
               if (☃xxxx.func_196958_f() || ☃xxxx.func_185904_a() == Material.field_151584_j) {
                  this.func_202278_a(☃, ☃xxx, field_208531_b);
               }
            }
         }
      }
   }

   private float func_208527_a(int var1, int var2) {
      if ((float)☃ < (float)☃ * 0.3F) {
         return -1.0F;
      } else {
         float ☃ = (float)☃ / 2.0F;
         float ☃x = ☃ - (float)☃;
         float ☃xx = MathHelper.func_76129_c(☃ * ☃ - ☃x * ☃x);
         if (☃x == 0.0F) {
            ☃xx = ☃;
         } else if (Math.abs(☃x) >= ☃) {
            return 0.0F;
         }

         return ☃xx * 0.5F;
      }
   }

   private float func_76495_b(int var1) {
      if (☃ < 0 || ☃ >= 5) {
         return -1.0F;
      } else {
         return ☃ != 0 && ☃ != 4 ? 3.0F : 2.0F;
      }
   }

   private void func_202393_b(IWorld var1, BlockPos var2) {
      for(int ☃ = 0; ☃ < 5; ++☃) {
         this.func_208529_a(☃, ☃.func_177981_b(☃), this.func_76495_b(☃));
      }
   }

   private int func_208523_a(Set<BlockPos> var1, IWorld var2, BlockPos var3, BlockPos var4, boolean var5) {
      if (!☃ && Objects.equals(☃, ☃)) {
         return -1;
      } else {
         BlockPos ☃ = ☃.func_177982_a(-☃.func_177958_n(), -☃.func_177956_o(), -☃.func_177952_p());
         int ☃x = this.func_175935_b(☃);
         float ☃xx = (float)☃.func_177958_n() / (float)☃x;
         float ☃xxx = (float)☃.func_177956_o() / (float)☃x;
         float ☃xxxx = (float)☃.func_177952_p() / (float)☃x;

         for(int ☃xxxxx = 0; ☃xxxxx <= ☃x; ++☃xxxxx) {
            BlockPos ☃xxxxxx = ☃.func_177963_a(
               (double)(0.5F + (float)☃xxxxx * ☃xx), (double)(0.5F + (float)☃xxxxx * ☃xxx), (double)(0.5F + (float)☃xxxxx * ☃xxxx)
            );
            if (☃) {
               this.func_208520_a(☃, ☃, ☃xxxxxx, field_208530_a.func_206870_a(BlockLog.field_176298_M, this.func_197170_b(☃, ☃xxxxxx)));
            } else if (!this.func_150523_a(☃.func_180495_p(☃xxxxxx).func_177230_c())) {
               return ☃xxxxx;
            }
         }

         return -1;
      }
   }

   private int func_175935_b(BlockPos var1) {
      int ☃ = MathHelper.func_76130_a(☃.func_177958_n());
      int ☃x = MathHelper.func_76130_a(☃.func_177956_o());
      int ☃xx = MathHelper.func_76130_a(☃.func_177952_p());
      if (☃xx > ☃ && ☃xx > ☃x) {
         return ☃xx;
      } else {
         return ☃x > ☃ ? ☃x : ☃;
      }
   }

   private EnumFacing.Axis func_197170_b(BlockPos var1, BlockPos var2) {
      EnumFacing.Axis ☃ = EnumFacing.Axis.Y;
      int ☃x = Math.abs(☃.func_177958_n() - ☃.func_177958_n());
      int ☃xx = Math.abs(☃.func_177952_p() - ☃.func_177952_p());
      int ☃xxx = Math.max(☃x, ☃xx);
      if (☃xxx > 0) {
         if (☃x == ☃xxx) {
            ☃ = EnumFacing.Axis.X;
         } else if (☃xx == ☃xxx) {
            ☃ = EnumFacing.Axis.Z;
         }
      }

      return ☃;
   }

   private void func_208525_a(IWorld var1, int var2, BlockPos var3, List<BigTreeFeature.FoliageCoordinates> var4) {
      for(BigTreeFeature.FoliageCoordinates ☃ : ☃) {
         if (this.func_208522_b(☃, ☃.func_177999_q() - ☃.func_177956_o())) {
            this.func_202393_b(☃, ☃);
         }
      }
   }

   private boolean func_208522_b(int var1, int var2) {
      return (double)☃ >= (double)☃ * 0.2;
   }

   private void func_208526_a(Set<BlockPos> var1, IWorld var2, BlockPos var3, int var4) {
      this.func_208523_a(☃, ☃, ☃, ☃.func_177981_b(☃), true);
   }

   private void func_208524_a(Set<BlockPos> var1, IWorld var2, int var3, BlockPos var4, List<BigTreeFeature.FoliageCoordinates> var5) {
      for(BigTreeFeature.FoliageCoordinates ☃ : ☃) {
         int ☃x = ☃.func_177999_q();
         BlockPos ☃xx = new BlockPos(☃.func_177958_n(), ☃x, ☃.func_177952_p());
         if (!☃xx.equals(☃) && this.func_208522_b(☃, ☃x - ☃.func_177956_o())) {
            this.func_208523_a(☃, ☃, ☃xx, ☃, true);
         }
      }
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      Random ☃ = new Random(☃.nextLong());
      int ☃x = this.func_208528_b(☃, ☃, ☃, 5 + ☃.nextInt(12));
      if (☃x == -1) {
         return false;
      } else {
         this.func_175921_a(☃, ☃.func_177977_b());
         int ☃ = (int)((double)☃x * 0.618);
         if (☃ >= ☃x) {
            ☃ = ☃x - 1;
         }

         double ☃ = 1.0;
         int ☃x = (int)(1.382 + Math.pow(1.0 * (double)☃x / 13.0, 2.0));
         if (☃x < 1) {
            ☃x = 1;
         }

         int ☃ = ☃.func_177956_o() + ☃;
         int ☃x = ☃x - 5;
         List<BigTreeFeature.FoliageCoordinates> ☃xx = Lists.<BigTreeFeature.FoliageCoordinates>newArrayList();
         ☃xx.add(new BigTreeFeature.FoliageCoordinates(☃.func_177981_b(☃x), ☃));

         for(; ☃x >= 0; --☃x) {
            float ☃xxx = this.func_208527_a(☃x, ☃x);
            if (!(☃xxx < 0.0F)) {
               for(int ☃xxxx = 0; ☃xxxx < ☃x; ++☃xxxx) {
                  double ☃xxxxx = 1.0;
                  double ☃xxxxxx = 1.0 * (double)☃xxx * ((double)☃.nextFloat() + 0.328);
                  double ☃xxxxxxx = (double)(☃.nextFloat() * 2.0F) * Math.PI;
                  double ☃xxxxxxxx = ☃xxxxxx * Math.sin(☃xxxxxxx) + 0.5;
                  double ☃xxxxxxxxx = ☃xxxxxx * Math.cos(☃xxxxxxx) + 0.5;
                  BlockPos ☃xxxxxxxxxx = ☃.func_177963_a(☃xxxxxxxx, (double)(☃x - 1), ☃xxxxxxxxx);
                  BlockPos ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_177981_b(5);
                  if (this.func_208523_a(☃, ☃, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, false) == -1) {
                     int ☃xxxxxxxxxxxx = ☃.func_177958_n() - ☃xxxxxxxxxx.func_177958_n();
                     int ☃xxxxxxxxxxxxx = ☃.func_177952_p() - ☃xxxxxxxxxx.func_177952_p();
                     double ☃xxxxxxxxxxxxxx = (double)☃xxxxxxxxxx.func_177956_o()
                        - Math.sqrt((double)(☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx)) * 0.381;
                     int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx > (double)☃ ? ☃ : (int)☃xxxxxxxxxxxxxx;
                     BlockPos ☃xxxxxxxxxxxxxxxx = new BlockPos(☃.func_177958_n(), ☃xxxxxxxxxxxxxxx, ☃.func_177952_p());
                     if (this.func_208523_a(☃, ☃, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxx, false) == -1) {
                        ☃xx.add(new BigTreeFeature.FoliageCoordinates(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxx.func_177956_o()));
                     }
                  }
               }
            }
         }

         this.func_208525_a(☃, ☃x, ☃, ☃xx);
         this.func_208526_a(☃, ☃, ☃, ☃);
         this.func_208524_a(☃, ☃, ☃x, ☃, ☃xx);
         return true;
      }
   }

   private int func_208528_b(Set<BlockPos> var1, IWorld var2, BlockPos var3, int var4) {
      Block ☃ = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
      if (!Block.func_196245_f(☃) && ☃ != Blocks.field_196658_i && ☃ != Blocks.field_150458_ak) {
         return -1;
      } else {
         int ☃ = this.func_208523_a(☃, ☃, ☃, ☃.func_177981_b(☃ - 1), false);
         if (☃ == -1) {
            return ☃;
         } else {
            return ☃ < 6 ? -1 : ☃;
         }
      }
   }

   static class FoliageCoordinates extends BlockPos {
      private final int field_178000_b;

      public FoliageCoordinates(BlockPos var1, int var2) {
         super(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
         this.field_178000_b = ☃;
      }

      public int func_177999_q() {
         return this.field_178000_b;
      }
   }
}
