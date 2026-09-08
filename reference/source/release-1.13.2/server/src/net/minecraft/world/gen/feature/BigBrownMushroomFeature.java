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

public class BigBrownMushroomFeature extends Feature<NoFeatureConfig> {
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

            for(int ☃xx = 0; ☃xx <= 1 + ☃; ++☃xx) {
               int ☃xxx = ☃xx <= 3 ? 0 : 3;

               for(int ☃xxxx = -☃xxx; ☃xxxx <= ☃xxx; ++☃xxxx) {
                  for(int ☃xxxxx = -☃xxx; ☃xxxxx <= ☃xxx; ++☃xxxxx) {
                     IBlockState ☃xxxxxx = ☃.func_180495_p(☃x.func_189533_g(☃).func_196234_d(☃xxxx, ☃xx, ☃xxxxx));
                     if (!☃xxxxxx.func_196958_f() && !☃xxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                        return false;
                     }
                  }
               }
            }

            IBlockState ☃xx = Blocks.field_150420_aW
               .func_176223_P()
               .func_206870_a(BlockHugeMushroom.field_196465_z, Boolean.valueOf(true))
               .func_206870_a(BlockHugeMushroom.field_196460_A, Boolean.valueOf(false));
            int ☃xxx = 3;

            for(int ☃xxxx = -3; ☃xxxx <= 3; ++☃xxxx) {
               for(int ☃xxxxx = -3; ☃xxxxx <= 3; ++☃xxxxx) {
                  boolean ☃xxxxxx = ☃xxxx == -3;
                  boolean ☃xxxxxxx = ☃xxxx == 3;
                  boolean ☃xxxxxxxx = ☃xxxxx == -3;
                  boolean ☃xxxxxxxxx = ☃xxxxx == 3;
                  boolean ☃xxxxxxxxxx = ☃xxxxxx || ☃xxxxxxx;
                  boolean ☃xxxxxxxxxxx = ☃xxxxxxxx || ☃xxxxxxxxx;
                  if (!☃xxxxxxxxxx || !☃xxxxxxxxxxx) {
                     ☃x.func_189533_g(☃).func_196234_d(☃xxxx, ☃, ☃xxxxx);
                     if (!☃.func_180495_p(☃x).func_200015_d(☃, ☃x)) {
                        boolean ☃xxxxxxxxxxxx = ☃xxxxxx || ☃xxxxxxxxxxx && ☃xxxx == -2;
                        boolean ☃xxxxxxxxxxxxx = ☃xxxxxxx || ☃xxxxxxxxxxx && ☃xxxx == 2;
                        boolean ☃xxxxxxxxxxxxxx = ☃xxxxxxxx || ☃xxxxxxxxxx && ☃xxxxx == -2;
                        boolean ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxx || ☃xxxxxxxxxx && ☃xxxxx == 2;
                        this.func_202278_a(
                           ☃,
                           ☃x,
                           ☃xx.func_206870_a(BlockHugeMushroom.field_196464_y, Boolean.valueOf(☃xxxxxxxxxxxx))
                              .func_206870_a(BlockHugeMushroom.field_196461_b, Boolean.valueOf(☃xxxxxxxxxxxxx))
                              .func_206870_a(BlockHugeMushroom.field_196459_a, Boolean.valueOf(☃xxxxxxxxxxxxxx))
                              .func_206870_a(BlockHugeMushroom.field_196463_c, Boolean.valueOf(☃xxxxxxxxxxxxxxx))
                        );
                     }
                  }
               }
            }

            IBlockState ☃xxxx = Blocks.field_196706_do
               .func_176223_P()
               .func_206870_a(BlockHugeMushroom.field_196465_z, Boolean.valueOf(false))
               .func_206870_a(BlockHugeMushroom.field_196460_A, Boolean.valueOf(false));

            for(int ☃xxxxx = 0; ☃xxxxx < ☃; ++☃xxxxx) {
               ☃x.func_189533_g(☃).func_189534_c(EnumFacing.UP, ☃xxxxx);
               if (!☃.func_180495_p(☃x).func_200015_d(☃, ☃x)) {
                  this.func_202278_a(☃, ☃x, ☃xxxx);
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }
}
