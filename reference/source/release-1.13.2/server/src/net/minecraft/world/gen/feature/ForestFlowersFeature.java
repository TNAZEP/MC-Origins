package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;

public class ForestFlowersFeature extends AbstractFlowersFeature {
   private static final Block[] field_202356_a = new Block[]{
      Blocks.field_196605_bc,
      Blocks.field_196606_bd,
      Blocks.field_196607_be,
      Blocks.field_196609_bf,
      Blocks.field_196610_bg,
      Blocks.field_196612_bh,
      Blocks.field_196613_bi,
      Blocks.field_196614_bj,
      Blocks.field_196615_bk,
      Blocks.field_196616_bl
   };

   @Override
   public IBlockState func_202355_a(Random var1, BlockPos var2) {
      double ☃ = MathHelper.func_151237_a(
         (1.0 + Biome.field_180281_af.func_151601_a((double)☃.func_177958_n() / 48.0, (double)☃.func_177952_p() / 48.0)) / 2.0, 0.0, 0.9999
      );
      Block ☃x = field_202356_a[(int)(☃ * (double)field_202356_a.length)];
      return ☃x == Blocks.field_196607_be ? Blocks.field_196606_bd.func_176223_P() : ☃x.func_176223_P();
   }
}
