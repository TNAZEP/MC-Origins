package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDirtSnowy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class IceAndSnowFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos ☃x = new BlockPos.MutableBlockPos();

      for(int ☃xx = 0; ☃xx < 16; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < 16; ++☃xxx) {
            int ☃xxxx = ☃.func_177958_n() + ☃xx;
            int ☃xxxxx = ☃.func_177952_p() + ☃xxx;
            int ☃xxxxxx = ☃.func_201676_a(Heightmap.Type.MOTION_BLOCKING, ☃xxxx, ☃xxxxx);
            ☃.func_181079_c(☃xxxx, ☃xxxxxx, ☃xxxxx);
            ☃x.func_189533_g(☃).func_189534_c(EnumFacing.DOWN, 1);
            Biome ☃xxxxxxx = ☃.func_180494_b(☃);
            if (☃xxxxxxx.func_201854_a(☃, ☃x, false)) {
               ☃.func_180501_a(☃x, Blocks.field_150432_aD.func_176223_P(), 2);
            }

            if (☃xxxxxxx.func_201850_b(☃, ☃)) {
               ☃.func_180501_a(☃, Blocks.field_150433_aE.func_176223_P(), 2);
               IBlockState ☃xxxx = ☃.func_180495_p(☃x);
               if (☃xxxx.func_196959_b(BlockDirtSnowy.field_196382_a)) {
                  ☃.func_180501_a(☃x, ☃xxxx.func_206870_a(BlockDirtSnowy.field_196382_a, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}
