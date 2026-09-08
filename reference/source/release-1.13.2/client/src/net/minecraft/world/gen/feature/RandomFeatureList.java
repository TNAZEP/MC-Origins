package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class RandomFeatureList extends Feature<RandomDefaultFeatureListConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, RandomDefaultFeatureListConfig var5) {
      for(int ☃ = 0; ☃ < ☃.field_202449_a.length; ++☃) {
         if (☃.nextFloat() < ☃.field_202451_c[☃]) {
            return this.func_202362_a(☃.field_202449_a[☃], ☃.field_202450_b[☃], ☃, ☃, ☃, ☃);
         }
      }

      return this.func_202362_a(☃.field_202452_d, ☃.field_202453_f, ☃, ☃, ☃, ☃);
   }

   <FC extends IFeatureConfig> boolean func_202362_a(
      Feature<FC> var1, IFeatureConfig var2, IWorld var3, IChunkGenerator<? extends IChunkGenSettings> var4, Random var5, BlockPos var6
   ) {
      return ☃.func_212245_a(☃, ☃, ☃, ☃, (FC)☃);
   }
}
