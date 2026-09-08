package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BlockWithContextFeature extends Feature<BlockWithContextConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, BlockWithContextConfig var5) {
      if (☃.field_206925_b.contains(☃.func_180495_p(☃.func_177977_b()))
         && ☃.field_206926_c.contains(☃.func_180495_p(☃))
         && ☃.field_206927_d.contains(☃.func_180495_p(☃.func_177984_a()))) {
         ☃.func_180501_a(☃, ☃.field_206924_a, 2);
         return true;
      } else {
         return false;
      }
   }
}
