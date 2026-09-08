package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public class MegaPineTree extends HugeTreesFeature<NoFeatureConfig> {
   private static final IBlockState field_181633_e = Blocks.field_196618_L.func_176223_P();
   private static final IBlockState field_181634_f = Blocks.field_196645_X.func_176223_P();
   private static final IBlockState field_181635_g = Blocks.field_196661_l.func_176223_P();
   private final boolean field_150542_e;

   public MegaPineTree(boolean var1, boolean var2) {
      super(☃, 13, 15, field_181633_e, field_181634_f);
      this.field_150542_e = ☃;
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = this.func_150533_a(☃);
      if (!this.func_203427_a(☃, ☃, ☃)) {
         return false;
      } else {
         this.func_150541_c(☃, ☃.func_177958_n(), ☃.func_177952_p(), ☃.func_177956_o() + ☃, 0, ☃);

         for(int ☃ = 0; ☃ < ☃; ++☃) {
            IBlockState ☃x = ☃.func_180495_p(☃.func_177981_b(☃));
            if (☃x.func_196958_f() || ☃x.func_203425_a(BlockTags.field_206952_E)) {
               this.func_208520_a(☃, ☃, ☃.func_177981_b(☃), this.field_76520_b);
            }

            if (☃ < ☃ - 1) {
               ☃x = ☃.func_180495_p(☃.func_177982_a(1, ☃, 0));
               if (☃x.func_196958_f() || ☃x.func_203425_a(BlockTags.field_206952_E)) {
                  this.func_208520_a(☃, ☃, ☃.func_177982_a(1, ☃, 0), this.field_76520_b);
               }

               ☃x = ☃.func_180495_p(☃.func_177982_a(1, ☃, 1));
               if (☃x.func_196958_f() || ☃x.func_203425_a(BlockTags.field_206952_E)) {
                  this.func_208520_a(☃, ☃, ☃.func_177982_a(1, ☃, 1), this.field_76520_b);
               }

               ☃x = ☃.func_180495_p(☃.func_177982_a(0, ☃, 1));
               if (☃x.func_196958_f() || ☃x.func_203425_a(BlockTags.field_206952_E)) {
                  this.func_208520_a(☃, ☃, ☃.func_177982_a(0, ☃, 1), this.field_76520_b);
               }
            }
         }

         this.func_180711_a(☃, ☃, ☃);
         return true;
      }
   }

   private void func_150541_c(IWorld var1, int var2, int var3, int var4, int var5, Random var6) {
      int ☃ = ☃.nextInt(5) + (this.field_150542_e ? this.field_76522_a : 3);
      int ☃x = 0;

      for(int ☃xx = ☃ - ☃; ☃xx <= ☃; ++☃xx) {
         int ☃xxx = ☃ - ☃xx;
         int ☃xxxx = ☃ + MathHelper.func_76141_d((float)☃xxx / (float)☃ * 3.5F);
         this.func_175925_a(☃, new BlockPos(☃, ☃xx, ☃), ☃xxxx + (☃xxx > 0 && ☃xxxx == ☃x && (☃xx & 1) == 0 ? 1 : 0));
         ☃x = ☃xxxx;
      }
   }

   public void func_180711_a(IWorld var1, Random var2, BlockPos var3) {
      this.func_175933_b(☃, ☃.func_177976_e().func_177978_c());
      this.func_175933_b(☃, ☃.func_177965_g(2).func_177978_c());
      this.func_175933_b(☃, ☃.func_177976_e().func_177970_e(2));
      this.func_175933_b(☃, ☃.func_177965_g(2).func_177970_e(2));

      for(int ☃ = 0; ☃ < 5; ++☃) {
         int ☃x = ☃.nextInt(64);
         int ☃xx = ☃x % 8;
         int ☃xxx = ☃x / 8;
         if (☃xx == 0 || ☃xx == 7 || ☃xxx == 0 || ☃xxx == 7) {
            this.func_175933_b(☃, ☃.func_177982_a(-3 + ☃xx, 0, -3 + ☃xxx));
         }
      }
   }

   private void func_175933_b(IWorld var1, BlockPos var2) {
      for(int ☃ = -2; ☃ <= 2; ++☃) {
         for(int ☃x = -2; ☃x <= 2; ++☃x) {
            if (Math.abs(☃) != 2 || Math.abs(☃x) != 2) {
               this.func_175934_c(☃, ☃.func_177982_a(☃, 0, ☃x));
            }
         }
      }
   }

   private void func_175934_c(IWorld var1, BlockPos var2) {
      for(int ☃ = 2; ☃ >= -3; --☃) {
         BlockPos ☃x = ☃.func_177981_b(☃);
         IBlockState ☃xx = ☃.func_180495_p(☃x);
         Block ☃xxx = ☃xx.func_177230_c();
         if (☃xxx == Blocks.field_196658_i || Block.func_196245_f(☃xxx)) {
            this.func_202278_a(☃, ☃x, field_181635_g);
            break;
         }

         if (!☃xx.func_196958_f() && ☃ < 0) {
            break;
         }
      }
   }
}
