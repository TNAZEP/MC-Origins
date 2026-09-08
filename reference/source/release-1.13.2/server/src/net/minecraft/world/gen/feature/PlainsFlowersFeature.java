package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class PlainsFlowersFeature extends AbstractFlowersFeature {
   @Override
   public IBlockState func_202355_a(Random var1, BlockPos var2) {
      double ☃ = Biome.field_180281_af.func_151601_a((double)☃.func_177958_n() / 200.0, (double)☃.func_177952_p() / 200.0);
      if (☃ < -0.8) {
         int ☃x = ☃.nextInt(4);
         switch(☃x) {
            case 0:
               return Blocks.field_196613_bi.func_176223_P();
            case 1:
               return Blocks.field_196612_bh.func_176223_P();
            case 2:
               return Blocks.field_196615_bk.func_176223_P();
            case 3:
            default:
               return Blocks.field_196614_bj.func_176223_P();
         }
      } else if (☃.nextInt(3) > 0) {
         int ☃ = ☃.nextInt(3);
         if (☃ == 0) {
            return Blocks.field_196606_bd.func_176223_P();
         } else {
            return ☃ == 1 ? Blocks.field_196610_bg.func_176223_P() : Blocks.field_196616_bl.func_176223_P();
         }
      } else {
         return Blocks.field_196605_bc.func_176223_P();
      }
   }
}
