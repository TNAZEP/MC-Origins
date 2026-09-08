package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class RandomFeatureWithConfigFeature extends Feature<RandomFeatureWithConfigConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, RandomFeatureWithConfigConfig var5) {
      int ☃ = ☃.nextInt(☃.field_204628_a.length);
      return this.func_204627_a(☃.field_204628_a[☃], ☃.field_204629_b[☃], ☃, ☃, ☃, ☃);
   }

   <FC extends IFeatureConfig> boolean func_204627_a(
      Feature<FC> var1, IFeatureConfig var2, IWorld var3, IChunkGenerator<? extends IChunkGenSettings> var4, Random var5, BlockPos var6
   ) {
      return ☃.func_212245_a(☃, ☃, ☃, ☃, (FC)☃);
   }
}
