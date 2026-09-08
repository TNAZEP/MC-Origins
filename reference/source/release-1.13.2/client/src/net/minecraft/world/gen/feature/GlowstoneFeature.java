package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class GlowstoneFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      if (!☃.func_175623_d(☃)) {
         return false;
      } else if (☃.func_180495_p(☃.func_177984_a()).func_177230_c() != Blocks.field_150424_aL) {
         return false;
      } else {
         ☃.func_180501_a(☃, Blocks.field_150426_aN.func_176223_P(), 2);

         for(int ☃ = 0; ☃ < 1500; ++☃) {
            BlockPos ☃x = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), -☃.nextInt(12), ☃.nextInt(8) - ☃.nextInt(8));
            if (☃.func_180495_p(☃x).func_196958_f()) {
               int ☃xx = 0;

               for(EnumFacing ☃xxx : EnumFacing.values()) {
                  if (☃.func_180495_p(☃x.func_177972_a(☃xxx)).func_177230_c() == Blocks.field_150426_aN) {
                     ++☃xx;
                  }

                  if (☃xx > 1) {
                     break;
                  }
               }

               if (☃xx == 1) {
                  ☃.func_180501_a(☃x, Blocks.field_150426_aN.func_176223_P(), 2);
               }
            }
         }

         return true;
      }
   }
}
