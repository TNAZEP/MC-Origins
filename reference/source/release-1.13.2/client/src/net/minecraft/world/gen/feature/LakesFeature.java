package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class LakesFeature extends Feature<LakesConfig> {
   private static final IBlockState field_205188_a = Blocks.field_201941_jj.func_176223_P();

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, LakesConfig var5) {
      while(☃.func_177956_o() > 5 && ☃.func_175623_d(☃)) {
         ☃ = ☃.func_177977_b();
      }

      if (☃.func_177956_o() <= 4) {
         return false;
      } else {
         ☃ = ☃.func_177979_c(4);
         boolean[] ☃ = new boolean[2048];
         int ☃x = ☃.nextInt(4) + 4;

         for(int ☃xx = 0; ☃xx < ☃x; ++☃xx) {
            double ☃xxx = ☃.nextDouble() * 6.0 + 3.0;
            double ☃xxxx = ☃.nextDouble() * 4.0 + 2.0;
            double ☃xxxxx = ☃.nextDouble() * 6.0 + 3.0;
            double ☃xxxxxx = ☃.nextDouble() * (16.0 - ☃xxx - 2.0) + 1.0 + ☃xxx / 2.0;
            double ☃xxxxxxx = ☃.nextDouble() * (8.0 - ☃xxxx - 4.0) + 2.0 + ☃xxxx / 2.0;
            double ☃xxxxxxxx = ☃.nextDouble() * (16.0 - ☃xxxxx - 2.0) + 1.0 + ☃xxxxx / 2.0;

            for(int ☃xxxxxxxxx = 1; ☃xxxxxxxxx < 15; ++☃xxxxxxxxx) {
               for(int ☃xxxxxxxxxx = 1; ☃xxxxxxxxxx < 15; ++☃xxxxxxxxxx) {
                  for(int ☃xxxxxxxxxxx = 1; ☃xxxxxxxxxxx < 7; ++☃xxxxxxxxxxx) {
                     double ☃xxxxxxxxxxxx = ((double)☃xxxxxxxxx - ☃xxxxxx) / (☃xxx / 2.0);
                     double ☃xxxxxxxxxxxxx = ((double)☃xxxxxxxxxxx - ☃xxxxxxx) / (☃xxxx / 2.0);
                     double ☃xxxxxxxxxxxxxx = ((double)☃xxxxxxxxxx - ☃xxxxxxxx) / (☃xxxxx / 2.0);
                     double ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx * ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx;
                     if (☃xxxxxxxxxxxxxxx < 1.0) {
                        ☃[(☃xxxxxxxxx * 16 + ☃xxxxxxxxxx) * 8 + ☃xxxxxxxxxxx] = true;
                     }
                  }
               }
            }
         }

         for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
               for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
                  boolean ☃xxxxx = !☃[(☃xx * 16 + ☃xxx) * 8 + ☃xxxx]
                     && (
                        ☃xx < 15 && ☃[((☃xx + 1) * 16 + ☃xxx) * 8 + ☃xxxx]
                           || ☃xx > 0 && ☃[((☃xx - 1) * 16 + ☃xxx) * 8 + ☃xxxx]
                           || ☃xxx < 15 && ☃[(☃xx * 16 + ☃xxx + 1) * 8 + ☃xxxx]
                           || ☃xxx > 0 && ☃[(☃xx * 16 + (☃xxx - 1)) * 8 + ☃xxxx]
                           || ☃xxxx < 7 && ☃[(☃xx * 16 + ☃xxx) * 8 + ☃xxxx + 1]
                           || ☃xxxx > 0 && ☃[(☃xx * 16 + ☃xxx) * 8 + (☃xxxx - 1)]
                     );
                  if (☃xxxxx) {
                     Material ☃xxxxxx = ☃.func_180495_p(☃.func_177982_a(☃xx, ☃xxxx, ☃xxx)).func_185904_a();
                     if (☃xxxx >= 4 && ☃xxxxxx.func_76224_d()) {
                        return false;
                     }

                     if (☃xxxx < 4 && !☃xxxxxx.func_76220_a() && ☃.func_180495_p(☃.func_177982_a(☃xx, ☃xxxx, ☃xxx)).func_177230_c() != ☃.field_202438_a) {
                        return false;
                     }
                  }
               }
            }
         }

         for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
               for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
                  if (☃[(☃xx * 16 + ☃xxx) * 8 + ☃xxxx]) {
                     ☃.func_180501_a(☃.func_177982_a(☃xx, ☃xxxx, ☃xxx), ☃xxxx >= 4 ? field_205188_a : ☃.field_202438_a.func_176223_P(), 2);
                  }
               }
            }
         }

         for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
               for(int ☃xxxx = 4; ☃xxxx < 8; ++☃xxxx) {
                  if (☃[(☃xx * 16 + ☃xxx) * 8 + ☃xxxx]) {
                     BlockPos ☃xxxxx = ☃.func_177982_a(☃xx, ☃xxxx - 1, ☃xxx);
                     if (Block.func_196245_f(☃.func_180495_p(☃xxxxx).func_177230_c())
                        && ☃.func_175642_b(EnumLightType.SKY, ☃.func_177982_a(☃xx, ☃xxxx, ☃xxx)) > 0) {
                        Biome ☃xxxxxx = ☃.func_180494_b(☃xxxxx);
                        if (☃xxxxxx.func_203944_q().func_204108_a().func_177230_c() == Blocks.field_150391_bh) {
                           ☃.func_180501_a(☃xxxxx, Blocks.field_150391_bh.func_176223_P(), 2);
                        } else {
                           ☃.func_180501_a(☃xxxxx, Blocks.field_196658_i.func_176223_P(), 2);
                        }
                     }
                  }
               }
            }
         }

         if (☃.field_202438_a.func_176223_P().func_185904_a() == Material.field_151587_i) {
            for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
               for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
                  for(int ☃xxxx = 0; ☃xxxx < 8; ++☃xxxx) {
                     boolean ☃xxxxx = !☃[(☃xx * 16 + ☃xxx) * 8 + ☃xxxx]
                        && (
                           ☃xx < 15 && ☃[((☃xx + 1) * 16 + ☃xxx) * 8 + ☃xxxx]
                              || ☃xx > 0 && ☃[((☃xx - 1) * 16 + ☃xxx) * 8 + ☃xxxx]
                              || ☃xxx < 15 && ☃[(☃xx * 16 + ☃xxx + 1) * 8 + ☃xxxx]
                              || ☃xxx > 0 && ☃[(☃xx * 16 + (☃xxx - 1)) * 8 + ☃xxxx]
                              || ☃xxxx < 7 && ☃[(☃xx * 16 + ☃xxx) * 8 + ☃xxxx + 1]
                              || ☃xxxx > 0 && ☃[(☃xx * 16 + ☃xxx) * 8 + (☃xxxx - 1)]
                        );
                     if (☃xxxxx && (☃xxxx < 4 || ☃.nextInt(2) != 0) && ☃.func_180495_p(☃.func_177982_a(☃xx, ☃xxxx, ☃xxx)).func_185904_a().func_76220_a()) {
                        ☃.func_180501_a(☃.func_177982_a(☃xx, ☃xxxx, ☃xxx), Blocks.field_150348_b.func_176223_P(), 2);
                     }
                  }
               }
            }
         }

         if (☃.field_202438_a.func_176223_P().func_185904_a() == Material.field_151586_h) {
            for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
               for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
                  int ☃xxxx = 4;
                  BlockPos ☃xxxxx = ☃.func_177982_a(☃xx, 4, ☃xxx);
                  if (☃.func_180494_b(☃xxxxx).func_201854_a(☃, ☃xxxxx, false)) {
                     ☃.func_180501_a(☃xxxxx, Blocks.field_150432_aD.func_176223_P(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
