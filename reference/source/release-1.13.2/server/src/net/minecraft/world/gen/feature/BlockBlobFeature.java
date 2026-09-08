package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BlockBlobFeature extends Feature<BlockBlobConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, BlockBlobConfig var5) {
      for(; ☃.func_177956_o() > 3; ☃ = ☃.func_177977_b()) {
         if (!☃.func_175623_d(☃.func_177977_b())) {
            Block ☃ = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
            if (☃ == Blocks.field_196658_i || Block.func_196245_f(☃) || Block.func_196252_e(☃)) {
               break;
            }
         }
      }

      if (☃.func_177956_o() <= 3) {
         return false;
      } else {
         int ☃ = ☃.field_202464_b;

         for(int ☃x = 0; ☃ >= 0 && ☃x < 3; ++☃x) {
            int ☃xx = ☃ + ☃.nextInt(2);
            int ☃xxx = ☃ + ☃.nextInt(2);
            int ☃xxxx = ☃ + ☃.nextInt(2);
            float ☃xxxxx = (float)(☃xx + ☃xxx + ☃xxxx) * 0.333F + 0.5F;

            for(BlockPos ☃xxxxxx : BlockPos.func_177980_a(☃.func_177982_a(-☃xx, -☃xxx, -☃xxxx), ☃.func_177982_a(☃xx, ☃xxx, ☃xxxx))) {
               if (☃xxxxxx.func_177951_i(☃) <= (double)(☃xxxxx * ☃xxxxx)) {
                  ☃.func_180501_a(☃xxxxxx, ☃.field_202463_a.func_176223_P(), 4);
               }
            }

            ☃ = ☃.func_177982_a(-(☃ + 1) + ☃.nextInt(2 + ☃ * 2), 0 - ☃.nextInt(2), -(☃ + 1) + ☃.nextInt(2 + ☃ * 2));
         }

         return true;
      }
   }
}
