package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class MelonFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      for(int ☃ = 0; ☃ < 64; ++☃) {
         BlockPos ☃x = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         IBlockState ☃xx = Blocks.field_150440_ba.func_176223_P();
         if (☃.func_180495_p(☃x.func_177977_b()).func_177230_c() == Blocks.field_196658_i) {
            ☃.func_180501_a(☃x, ☃xx, 2);
         }
      }

      return true;
   }
}
