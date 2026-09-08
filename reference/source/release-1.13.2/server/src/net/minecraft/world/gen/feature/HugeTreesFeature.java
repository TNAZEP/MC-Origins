package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public abstract class HugeTreesFeature<T extends IFeatureConfig> extends AbstractTreeFeature<T> {
   protected final int field_76522_a;
   protected final IBlockState field_76520_b;
   protected final IBlockState field_76521_c;
   protected int field_150538_d;

   public HugeTreesFeature(boolean var1, int var2, int var3, IBlockState var4, IBlockState var5) {
      super(☃);
      this.field_76522_a = ☃;
      this.field_150538_d = ☃;
      this.field_76520_b = ☃;
      this.field_76521_c = ☃;
   }

   protected int func_150533_a(Random var1) {
      int ☃ = ☃.nextInt(3) + this.field_76522_a;
      if (this.field_150538_d > 1) {
         ☃ += ☃.nextInt(this.field_150538_d);
      }

      return ☃;
   }

   private boolean func_175926_c(IBlockReader var1, BlockPos var2, int var3) {
      boolean ☃ = true;
      if (☃.func_177956_o() >= 1 && ☃.func_177956_o() + ☃ + 1 <= 256) {
         for(int ☃x = 0; ☃x <= 1 + ☃; ++☃x) {
            int ☃xx = 2;
            if (☃x == 0) {
               ☃xx = 1;
            } else if (☃x >= 1 + ☃ - 2) {
               ☃xx = 2;
            }

            for(int ☃xx = -☃xx; ☃xx <= ☃xx && ☃; ++☃xx) {
               for(int ☃xxx = -☃xx; ☃xxx <= ☃xx && ☃; ++☃xxx) {
                  if (☃.func_177956_o() + ☃x < 0
                     || ☃.func_177956_o() + ☃x >= 256
                     || !this.func_150523_a(☃.func_180495_p(☃.func_177982_a(☃xx, ☃x, ☃xxx)).func_177230_c())) {
                     ☃ = false;
                  }
               }
            }
         }

         return ☃;
      } else {
         return false;
      }
   }

   private boolean func_202405_b(IWorld var1, BlockPos var2) {
      BlockPos ☃ = ☃.func_177977_b();
      Block ☃x = ☃.func_180495_p(☃).func_177230_c();
      if ((☃x == Blocks.field_196658_i || Block.func_196245_f(☃x)) && ☃.func_177956_o() >= 2) {
         this.func_175921_a(☃, ☃);
         this.func_175921_a(☃, ☃.func_177974_f());
         this.func_175921_a(☃, ☃.func_177968_d());
         this.func_175921_a(☃, ☃.func_177968_d().func_177974_f());
         return true;
      } else {
         return false;
      }
   }

   protected boolean func_203427_a(IWorld var1, BlockPos var2, int var3) {
      return this.func_175926_c(☃, ☃, ☃) && this.func_202405_b(☃, ☃);
   }

   protected void func_175925_a(IWorld var1, BlockPos var2, int var3) {
      int ☃ = ☃ * ☃;

      for(int ☃x = -☃; ☃x <= ☃ + 1; ++☃x) {
         for(int ☃xx = -☃; ☃xx <= ☃ + 1; ++☃xx) {
            int ☃xxx = Math.min(Math.abs(☃x), Math.abs(☃x - 1));
            int ☃xxxx = Math.min(Math.abs(☃xx), Math.abs(☃xx - 1));
            if (☃xxx + ☃xxxx < 7 && ☃xxx * ☃xxx + ☃xxxx * ☃xxxx <= ☃) {
               BlockPos ☃xxxxx = ☃.func_177982_a(☃x, 0, ☃xx);
               IBlockState ☃xxxxxx = ☃.func_180495_p(☃xxxxx);
               if (☃xxxxxx.func_196958_f() || ☃xxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                  this.func_202278_a(☃, ☃xxxxx, this.field_76521_c);
               }
            }
         }
      }
   }

   protected void func_175928_b(IWorld var1, BlockPos var2, int var3) {
      int ☃ = ☃ * ☃;

      for(int ☃x = -☃; ☃x <= ☃; ++☃x) {
         for(int ☃xx = -☃; ☃xx <= ☃; ++☃xx) {
            if (☃x * ☃x + ☃xx * ☃xx <= ☃) {
               BlockPos ☃xxx = ☃.func_177982_a(☃x, 0, ☃xx);
               IBlockState ☃xxxx = ☃.func_180495_p(☃xxx);
               if (☃xxxx.func_196958_f() || ☃xxxx.func_203425_a(BlockTags.field_206952_E)) {
                  this.func_202278_a(☃, ☃xxx, this.field_76521_c);
               }
            }
         }
      }
   }
}
