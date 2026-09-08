package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class HellLavaFeature extends Feature<HellLavaConfig> {
   private static final IBlockState field_205173_a = Blocks.field_150424_aL.func_176223_P();

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, HellLavaConfig var5) {
      if (☃.func_180495_p(☃.func_177984_a()) != field_205173_a) {
         return false;
      } else if (!☃.func_180495_p(☃).func_196958_f() && ☃.func_180495_p(☃) != field_205173_a) {
         return false;
      } else {
         int ☃ = 0;
         if (☃.func_180495_p(☃.func_177976_e()) == field_205173_a) {
            ++☃;
         }

         if (☃.func_180495_p(☃.func_177974_f()) == field_205173_a) {
            ++☃;
         }

         if (☃.func_180495_p(☃.func_177978_c()) == field_205173_a) {
            ++☃;
         }

         if (☃.func_180495_p(☃.func_177968_d()) == field_205173_a) {
            ++☃;
         }

         if (☃.func_180495_p(☃.func_177977_b()) == field_205173_a) {
            ++☃;
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

         if (☃.func_175623_d(☃.func_177977_b())) {
            ++☃;
         }

         if (!☃.field_202437_a && ☃ == 4 && ☃ == 1 || ☃ == 5) {
            ☃.func_180501_a(☃, Blocks.field_150353_l.func_176223_P(), 2);
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204547_b, 0);
         }

         return true;
      }
   }
}
