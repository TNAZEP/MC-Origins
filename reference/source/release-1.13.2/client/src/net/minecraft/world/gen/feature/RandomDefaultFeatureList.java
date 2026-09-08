package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class RandomDefaultFeatureList extends Feature<RandomFeatureListConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, RandomFeatureListConfig var5) {
      int ☃ = ☃.nextInt(5) - 3 + ☃.field_202456_c;

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         int ☃xx = ☃.nextInt(☃.field_202454_a.length);
         this.func_202361_a(☃.field_202454_a[☃xx], ☃.field_202455_b[☃xx], ☃, ☃, ☃, ☃);
      }

      return true;
   }

   <FC extends IFeatureConfig> boolean func_202361_a(
      Feature<FC> var1, IFeatureConfig var2, IWorld var3, IChunkGenerator<? extends IChunkGenSettings> var4, Random var5, BlockPos var6
   ) {
      return ☃.func_212245_a(☃, ☃, ☃, ☃, (FC)☃);
   }
}
