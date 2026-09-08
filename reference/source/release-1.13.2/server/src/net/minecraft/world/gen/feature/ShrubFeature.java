package net.minecraft.world.gen.feature;

import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class ShrubFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private final IBlockState field_150528_a;
   private final IBlockState field_150527_b;

   public ShrubFeature(IBlockState var1, IBlockState var2) {
      super(false);
      this.field_150527_b = ☃;
      this.field_150528_a = ☃;
   }

   @Override
   public boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4) {
      for(IBlockState ☃ = ☃.func_180495_p(☃); (☃.func_196958_f() || ☃.func_203425_a(BlockTags.field_206952_E)) && ☃.func_177956_o() > 0; ☃ = ☃.func_180495_p(☃)) {
         ☃ = ☃.func_177977_b();
      }

      Block ☃ = ☃.func_180495_p(☃).func_177230_c();
      if (Block.func_196245_f(☃) || ☃ == Blocks.field_196658_i) {
         ☃ = ☃.func_177984_a();
         this.func_208520_a(☃, ☃, ☃, this.field_150527_b);

         for(int ☃x = ☃.func_177956_o(); ☃x <= ☃.func_177956_o() + 2; ++☃x) {
            int ☃xx = ☃x - ☃.func_177956_o();
            int ☃xxx = 2 - ☃xx;

            for(int ☃xxxx = ☃.func_177958_n() - ☃xxx; ☃xxxx <= ☃.func_177958_n() + ☃xxx; ++☃xxxx) {
               int ☃xxxxx = ☃xxxx - ☃.func_177958_n();

               for(int ☃xxxxxx = ☃.func_177952_p() - ☃xxx; ☃xxxxxx <= ☃.func_177952_p() + ☃xxx; ++☃xxxxxx) {
                  int ☃xxxxxxx = ☃xxxxxx - ☃.func_177952_p();
                  if (Math.abs(☃xxxxx) != ☃xxx || Math.abs(☃xxxxxxx) != ☃xxx || ☃.nextInt(2) != 0) {
                     BlockPos ☃xxxxxxxx = new BlockPos(☃xxxx, ☃x, ☃xxxxxx);
                     IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
                     if (☃xxxxxxxxx.func_196958_f() || ☃xxxxxxxxx.func_203425_a(BlockTags.field_206952_E)) {
                        this.func_202278_a(☃, ☃xxxxxxxx, this.field_150528_a);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
