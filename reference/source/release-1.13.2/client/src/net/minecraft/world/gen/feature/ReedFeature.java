package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class ReedFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < 20; ++☃x) {
         BlockPos ☃xx = ☃.func_177982_a(☃.nextInt(4) - ☃.nextInt(4), 0, ☃.nextInt(4) - ☃.nextInt(4));
         if (☃.func_175623_d(☃xx)) {
            BlockPos ☃xxx = ☃xx.func_177977_b();
            if (☃.func_204610_c(☃xxx.func_177976_e()).func_206884_a(FluidTags.field_206959_a)
               || ☃.func_204610_c(☃xxx.func_177974_f()).func_206884_a(FluidTags.field_206959_a)
               || ☃.func_204610_c(☃xxx.func_177978_c()).func_206884_a(FluidTags.field_206959_a)
               || ☃.func_204610_c(☃xxx.func_177968_d()).func_206884_a(FluidTags.field_206959_a)) {
               int ☃xxxx = 2 + ☃.nextInt(☃.nextInt(3) + 1);

               for(int ☃xxxxx = 0; ☃xxxxx < ☃xxxx; ++☃xxxxx) {
                  if (Blocks.field_196608_cF.func_176223_P().func_196955_c(☃, ☃xx)) {
                     ☃.func_180501_a(☃xx.func_177981_b(☃xxxxx), Blocks.field_196608_cF.func_176223_P(), 2);
                     ++☃;
                  }
               }
            }
         }
      }

      return ☃ > 0;
   }
}
