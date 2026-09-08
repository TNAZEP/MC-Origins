package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class LiquidsFeature extends Feature<LiquidsConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, LiquidsConfig var5) {
      if (!Block.func_196252_e(☃.func_180495_p(☃.func_177984_a()).func_177230_c())) {
         return false;
      } else if (!Block.func_196252_e(☃.func_180495_p(☃.func_177977_b()).func_177230_c())) {
         return false;
      } else {
         IBlockState ☃ = ☃.func_180495_p(☃);
         if (!☃.func_196958_f() && !Block.func_196252_e(☃.func_177230_c())) {
            return false;
         } else {
            int ☃ = 0;
            int ☃x = 0;
            if (Block.func_196252_e(☃.func_180495_p(☃.func_177976_e()).func_177230_c())) {
               ++☃x;
            }

            if (Block.func_196252_e(☃.func_180495_p(☃.func_177974_f()).func_177230_c())) {
               ++☃x;
            }

            if (Block.func_196252_e(☃.func_180495_p(☃.func_177978_c()).func_177230_c())) {
               ++☃x;
            }

            if (Block.func_196252_e(☃.func_180495_p(☃.func_177968_d()).func_177230_c())) {
               ++☃x;
            }

            int ☃ = 0;
            if (☃.func_175623_d(☃.func_177976_e())) {
               ++☃;
            }

            if (☃.func_175623_d(☃.func_177974_f())) {
               ++☃;
            }

            if (☃.func_175623_d(☃.func_177978_c())) {
               ++☃;
            }

            if (☃.func_175623_d(☃.func_177968_d())) {
               ++☃;
            }

            if (☃x == 3 && ☃ == 1) {
               ☃.func_180501_a(☃, ☃.field_202459_a.func_207188_f().func_206883_i(), 2);
               ☃.func_205219_F_().func_205360_a(☃, ☃.field_202459_a, 0);
               ++☃;
            }

            return ☃ > 0;
         }
      }
   }
}
