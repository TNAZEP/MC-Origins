package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockSeaGrassTall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class SeaGrassFeature extends Feature<SeaGrassConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, SeaGrassConfig var5) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < ☃.field_203237_a; ++☃x) {
         int ☃xx = ☃.nextInt(8) - ☃.nextInt(8);
         int ☃xxx = ☃.nextInt(8) - ☃.nextInt(8);
         int ☃xxxx = ☃.func_201676_a(Heightmap.Type.OCEAN_FLOOR, ☃.func_177958_n() + ☃xx, ☃.func_177952_p() + ☃xxx);
         BlockPos ☃xxxxx = new BlockPos(☃.func_177958_n() + ☃xx, ☃xxxx, ☃.func_177952_p() + ☃xxx);
         if (☃.func_180495_p(☃xxxxx).func_177230_c() == Blocks.field_150355_j) {
            boolean ☃xxxxxx = ☃.nextDouble() < ☃.field_203238_b;
            IBlockState ☃xxxxxxx = ☃xxxxxx ? Blocks.field_203199_aR.func_176223_P() : Blocks.field_203198_aQ.func_176223_P();
            if (☃xxxxxxx.func_196955_c(☃, ☃xxxxx)) {
               if (☃xxxxxx) {
                  IBlockState ☃xxxxxxxx = ☃xxxxxxx.func_206870_a(BlockSeaGrassTall.field_208065_c, DoubleBlockHalf.UPPER);
                  BlockPos ☃xxxxxxxxx = ☃xxxxx.func_177984_a();
                  if (☃.func_180495_p(☃xxxxxxxxx).func_177230_c() == Blocks.field_150355_j) {
                     ☃.func_180501_a(☃xxxxx, ☃xxxxxxx, 2);
                     ☃.func_180501_a(☃xxxxxxxxx, ☃xxxxxxxx, 2);
                  }
               } else {
                  ☃.func_180501_a(☃xxxxx, ☃xxxxxxx, 2);
               }

               ++☃;
            }
         }
      }

      return ☃ > 0;
   }
}
