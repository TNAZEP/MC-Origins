package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class IceSpikeFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      while(☃.func_175623_d(☃) && ☃.func_177956_o() > 2) {
         ☃ = ☃.func_177977_b();
      }

      if (☃.func_180495_p(☃).func_177230_c() != Blocks.field_196604_cC) {
         return false;
      } else {
         ☃ = ☃.func_177981_b(☃.nextInt(4));
         int ☃ = ☃.nextInt(4) + 7;
         int ☃x = ☃ / 4 + ☃.nextInt(2);
         if (☃x > 1 && ☃.nextInt(60) == 0) {
            ☃ = ☃.func_177981_b(10 + ☃.nextInt(30));
         }

         for(int ☃ = 0; ☃ < ☃; ++☃) {
            float ☃x = (1.0F - (float)☃ / (float)☃) * (float)☃x;
            int ☃xx = MathHelper.func_76123_f(☃x);

            for(int ☃xxx = -☃xx; ☃xxx <= ☃xx; ++☃xxx) {
               float ☃xxxx = (float)MathHelper.func_76130_a(☃xxx) - 0.25F;

               for(int ☃xxxxx = -☃xx; ☃xxxxx <= ☃xx; ++☃xxxxx) {
                  float ☃xxxxxx = (float)MathHelper.func_76130_a(☃xxxxx) - 0.25F;
                  if ((☃xxx == 0 && ☃xxxxx == 0 || !(☃xxxx * ☃xxxx + ☃xxxxxx * ☃xxxxxx > ☃x * ☃x))
                     && (☃xxx != -☃xx && ☃xxx != ☃xx && ☃xxxxx != -☃xx && ☃xxxxx != ☃xx || !(☃.nextFloat() > 0.75F))) {
                     IBlockState ☃xxxxxxx = ☃.func_180495_p(☃.func_177982_a(☃xxx, ☃, ☃xxxxx));
                     Block ☃xxxxxxxx = ☃xxxxxxx.func_177230_c();
                     if (☃xxxxxxx.func_196958_f()
                        || Block.func_196245_f(☃xxxxxxxx)
                        || ☃xxxxxxxx == Blocks.field_196604_cC
                        || ☃xxxxxxxx == Blocks.field_150432_aD) {
                        this.func_202278_a(☃, ☃.func_177982_a(☃xxx, ☃, ☃xxxxx), Blocks.field_150403_cj.func_176223_P());
                     }

                     if (☃ != 0 && ☃xx > 1) {
                        ☃xxxxxxx = ☃.func_180495_p(☃.func_177982_a(☃xxx, -☃, ☃xxxxx));
                        ☃xxxxxxxx = ☃xxxxxxx.func_177230_c();
                        if (☃xxxxxxx.func_196958_f()
                           || Block.func_196245_f(☃xxxxxxxx)
                           || ☃xxxxxxxx == Blocks.field_196604_cC
                           || ☃xxxxxxxx == Blocks.field_150432_aD) {
                           this.func_202278_a(☃, ☃.func_177982_a(☃xxx, -☃, ☃xxxxx), Blocks.field_150403_cj.func_176223_P());
                        }
                     }
                  }
               }
            }
         }

         int ☃ = ☃x - 1;
         if (☃ < 0) {
            ☃ = 0;
         } else if (☃ > 1) {
            ☃ = 1;
         }

         for(int ☃ = -☃; ☃ <= ☃; ++☃) {
            for(int ☃x = -☃; ☃x <= ☃; ++☃x) {
               BlockPos ☃xx = ☃.func_177982_a(☃, -1, ☃x);
               int ☃xxx = 50;
               if (Math.abs(☃) == 1 && Math.abs(☃x) == 1) {
                  ☃xxx = ☃.nextInt(5);
               }

               while(☃xx.func_177956_o() > 50) {
                  IBlockState ☃xx = ☃.func_180495_p(☃xx);
                  Block ☃xxx = ☃xx.func_177230_c();
                  if (!☃xx.func_196958_f()
                     && !Block.func_196245_f(☃xxx)
                     && ☃xxx != Blocks.field_196604_cC
                     && ☃xxx != Blocks.field_150432_aD
                     && ☃xxx != Blocks.field_150403_cj) {
                     break;
                  }

                  this.func_202278_a(☃, ☃xx, Blocks.field_150403_cj.func_176223_P());
                  ☃xx = ☃xx.func_177977_b();
                  if (--☃xxx <= 0) {
                     ☃xx = ☃xx.func_177979_c(☃.nextInt(5) + 1);
                     ☃xxx = ☃.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
