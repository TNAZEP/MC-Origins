package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockChorusFlower;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class ChorusPlantFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      if (☃.func_175623_d(☃.func_177984_a()) && ☃.func_180495_p(☃).func_177230_c() == Blocks.field_150377_bs) {
         BlockChorusFlower.func_185603_a(☃, ☃.func_177984_a(), ☃, 8);
         return true;
      } else {
         return false;
      }
   }
}
