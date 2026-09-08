package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;

public class MegaJungleFeature extends HugeTreesFeature<NoFeatureConfig> {
   public MegaJungleFeature(boolean var1, int var2, int var3, IBlockState var4, IBlockState var5) {
      super(☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      int ☃ = this.func_150533_a(☃);
      if (!this.func_203427_a(☃, ☃, ☃)) {
         return false;
      } else {
         this.func_202408_c(☃, ☃.func_177981_b(☃), 2);

         for(int ☃ = ☃.func_177956_o() + ☃ - 2 - ☃.nextInt(4); ☃ > ☃.func_177956_o() + ☃ / 2; ☃ -= 2 + ☃.nextInt(4)) {
            float ☃x = ☃.nextFloat() * (float) (Math.PI * 2);
            int ☃xx = ☃.func_177958_n() + (int)(0.5F + MathHelper.func_76134_b(☃x) * 4.0F);
            int ☃xxx = ☃.func_177952_p() + (int)(0.5F + MathHelper.func_76126_a(☃x) * 4.0F);

            for(int ☃xxxx = 0; ☃xxxx < 5; ++☃xxxx) {
               ☃xx = ☃.func_177958_n() + (int)(1.5F + MathHelper.func_76134_b(☃x) * (float)☃xxxx);
               ☃xxx = ☃.func_177952_p() + (int)(1.5F + MathHelper.func_76126_a(☃x) * (float)☃xxxx);
               this.func_208520_a(☃, ☃, new BlockPos(☃xx, ☃ - 3 + ☃xxxx / 2, ☃xxx), this.field_76520_b);
            }

            int ☃xxxx = 1 + ☃.nextInt(2);
            int ☃xxxxx = ☃;

            for(int ☃xxxxxx = ☃ - ☃xxxx; ☃xxxxxx <= ☃xxxxx; ++☃xxxxxx) {
               int ☃xxxxxxx = ☃xxxxxx - ☃xxxxx;
               this.func_175928_b(☃, new BlockPos(☃xx, ☃xxxxxx, ☃xxx), 1 - ☃xxxxxxx);
            }
         }

         for(int ☃ = 0; ☃ < ☃; ++☃) {
            BlockPos ☃x = ☃.func_177981_b(☃);
            if (this.func_150523_a(☃.func_180495_p(☃x).func_177230_c())) {
               this.func_208520_a(☃, ☃, ☃x, this.field_76520_b);
               if (☃ > 0) {
                  this.func_202407_a(☃, ☃, ☃x.func_177976_e(), BlockVine.field_176278_M);
                  this.func_202407_a(☃, ☃, ☃x.func_177978_c(), BlockVine.field_176279_N);
               }
            }

            if (☃ < ☃ - 1) {
               BlockPos ☃x = ☃x.func_177974_f();
               if (this.func_150523_a(☃.func_180495_p(☃x).func_177230_c())) {
                  this.func_208520_a(☃, ☃, ☃x, this.field_76520_b);
                  if (☃ > 0) {
                     this.func_202407_a(☃, ☃, ☃x.func_177974_f(), BlockVine.field_176280_O);
                     this.func_202407_a(☃, ☃, ☃x.func_177978_c(), BlockVine.field_176279_N);
                  }
               }

               BlockPos ☃x = ☃x.func_177968_d().func_177974_f();
               if (this.func_150523_a(☃.func_180495_p(☃x).func_177230_c())) {
                  this.func_208520_a(☃, ☃, ☃x, this.field_76520_b);
                  if (☃ > 0) {
                     this.func_202407_a(☃, ☃, ☃x.func_177974_f(), BlockVine.field_176280_O);
                     this.func_202407_a(☃, ☃, ☃x.func_177968_d(), BlockVine.field_176273_b);
                  }
               }

               BlockPos ☃x = ☃x.func_177968_d();
               if (this.func_150523_a(☃.func_180495_p(☃x).func_177230_c())) {
                  this.func_208520_a(☃, ☃, ☃x, this.field_76520_b);
                  if (☃ > 0) {
                     this.func_202407_a(☃, ☃, ☃x.func_177976_e(), BlockVine.field_176278_M);
                     this.func_202407_a(☃, ☃, ☃x.func_177968_d(), BlockVine.field_176273_b);
                  }
               }
            }
         }

         return true;
      }
   }

   private void func_202407_a(IWorld var1, Random var2, BlockPos var3, BooleanProperty var4) {
      if (☃.nextInt(3) > 0 && ☃.func_175623_d(☃)) {
         this.func_202278_a(☃, ☃, Blocks.field_150395_bd.func_176223_P().func_206870_a(☃, Boolean.valueOf(true)));
      }
   }

   private void func_202408_c(IWorld var1, BlockPos var2, int var3) {
      int ☃ = 2;

      for(int ☃x = -2; ☃x <= 0; ++☃x) {
         this.func_175925_a(☃, ☃.func_177981_b(☃x), ☃ + 1 - ☃x);
      }
   }
}
