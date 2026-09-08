package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class DoublePlantFeature extends Feature<DoublePlantConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, DoublePlantConfig var5) {
      boolean ☃ = false;

      for(int ☃x = 0; ☃x < 64; ++☃x) {
         BlockPos ☃xx = ☃.func_177982_a(☃.nextInt(8) - ☃.nextInt(8), ☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(8) - ☃.nextInt(8));
         if (☃.func_175623_d(☃xx) && ☃xx.func_177956_o() < 254 && ☃.field_202435_a.func_196955_c(☃, ☃xx)) {
            ((BlockDoublePlant)☃.field_202435_a.func_177230_c()).func_196390_a(☃, ☃xx, 2);
            ☃ = true;
         }
      }

      return ☃;
   }
}
