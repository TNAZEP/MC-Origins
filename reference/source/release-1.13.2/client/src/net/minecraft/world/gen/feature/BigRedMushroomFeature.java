package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BigRedMushroomFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      int ☃ = ☃.nextInt(3) + 4;
      if (☃.nextInt(12) == 0) {
         ☃ *= 2;
      }

      int ☃ = ☃.func_177956_o();
      if (☃ >= 1 && ☃ + ☃ + 1 < 256) {
         Block ☃x = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
         if (!Block.func_196245_f(☃x) && ☃x != Blocks.field_196658_i && ☃x != Blocks.field_150391_bh) {
            return false;
         } else {
            BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos();

            for(int ☃xx = 0; ☃xx <= ☃; ++☃xx) {
               int ☃xxx = 0;
               if (☃xx < ☃ && ☃xx >= ☃ - 3) {
                  ☃xxx = 2;
               } else if (☃xx == ☃) {
                  ☃xxx = 1;
               }

               for(int ☃xxx = -☃xxx; ☃xxx <= ☃xxx; ++☃xxx) {
                  for(int ☃xxxx = -☃xxx; ☃xxxx <= ☃xxx; ++☃xxxx) {
                     IBlockState ☃xxxxx = ☃.func_180495_p(☃x.func_189533_g(☃).func_196234_d(☃xxx, ☃xx, ☃xxxx));
                     if (!☃xxxxx.func_196958_f() && !☃xxxxx.func_203425_a(BlockTags.field_206952_E)) {
                        return false;
                     }
                  }
               }
            }

            IBlockState ☃xx = Blocks.field_150419_aX.func_176223_P().func_206870_a(BlockHugeMushroom.field_196460_A, Boolean.valueOf(false));

            for(int ☃xxx = ☃ - 3; ☃xxx <= ☃; ++☃xxx) {
               int ☃xxxx = ☃xxx < ☃ ? 2 : 1;
               int ☃xxxxx = 0;

               for(int ☃xxxxxx = -☃xxxx; ☃xxxxxx <= ☃xxxx; ++☃xxxxxx) {
                  for(int ☃xxxxxxx = -☃xxxx; ☃xxxxxxx <= ☃xxxx; ++☃xxxxxxx) {
                     boolean ☃xxxxxxxx = ☃xxxxxx == -☃xxxx;
                     boolean ☃xxxxxxxxx = ☃xxxxxx == ☃xxxx;
                     boolean ☃xxxxxxxxxx = ☃xxxxxxx == -☃xxxx;
                     boolean ☃xxxxxxxxxxx = ☃xxxxxxx == ☃xxxx;
                     boolean ☃xxxxxxxxxxxx = ☃xxxxxxxx || ☃xxxxxxxxx;
                     boolean ☃xxxxxxxxxxxxx = ☃xxxxxxxxxx || ☃xxxxxxxxxxx;
                     if (☃xxx >= ☃ || ☃xxxxxxxxxxxx != ☃xxxxxxxxxxxxx) {
                        ☃x.func_189533_g(☃).func_196234_d(☃xxxxxx, ☃xxx, ☃xxxxxxx);
                        if (!☃.func_180495_p(☃x).func_200015_d(☃, ☃x)) {
                           this.func_202278_a(
                              ☃,
                              ☃x,
                              ☃xx.func_206870_a(BlockHugeMushroom.field_196465_z, Boolean.valueOf(☃xxx >= ☃ - 1))
                                 .func_206870_a(BlockHugeMushroom.field_196464_y, Boolean.valueOf(☃xxxxxx < 0))
                                 .func_206870_a(BlockHugeMushroom.field_196461_b, Boolean.valueOf(☃xxxxxx > 0))
                                 .func_206870_a(BlockHugeMushroom.field_196459_a, Boolean.valueOf(☃xxxxxxx < 0))
                                 .func_206870_a(BlockHugeMushroom.field_196463_c, Boolean.valueOf(☃xxxxxxx > 0))
                           );
                        }
                     }
                  }
               }
            }

            IBlockState ☃xxx = Blocks.field_196706_do
               .func_176223_P()
               .func_206870_a(BlockHugeMushroom.field_196465_z, Boolean.valueOf(false))
               .func_206870_a(BlockHugeMushroom.field_196460_A, Boolean.valueOf(false));

            for(int ☃xxxx = 0; ☃xxxx < ☃; ++☃xxxx) {
               ☃x.func_189533_g(☃).func_189534_c(EnumFacing.UP, ☃xxxx);
               if (!☃.func_180495_p(☃x).func_200015_d(☃, ☃x)) {
                  this.func_202278_a(☃, ☃x, ☃xxx);
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
