package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class TwoFeatureChoiceFeature extends Feature<TwoFeatureChoiceConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, TwoFeatureChoiceConfig var5) {
      boolean ☃ = ☃.nextBoolean();
      return ☃ ? this.func_202360_a(☃.field_202445_a, ☃.field_202446_b, ☃, ☃, ☃, ☃) : this.func_202360_a(☃.field_202447_c, ☃.field_202448_d, ☃, ☃, ☃, ☃);
   }

   <FC extends IFeatureConfig> boolean func_202360_a(
      Feature<FC> var1, IFeatureConfig var2, IWorld var3, IChunkGenerator<? extends IChunkGenSettings> var4, Random var5, BlockPos var6
   ) {
      return ☃.func_212245_a(☃, ☃, ☃, ☃, (FC)☃);
   }
}
