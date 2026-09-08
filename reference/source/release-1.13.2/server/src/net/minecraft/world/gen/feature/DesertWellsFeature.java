package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class DesertWellsFeature extends Feature<NoFeatureConfig> {
   private static final BlockStateMatcher field_175913_a = BlockStateMatcher.func_177638_a(Blocks.field_150354_m);
   private final IBlockState field_175911_b = Blocks.field_196640_bx.func_176223_P();
   private final IBlockState field_175912_c = Blocks.field_150322_A.func_176223_P();
   private final IBlockState field_175910_d = Blocks.field_150355_j.func_176223_P();

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      ☃ = ☃.func_177984_a();

      while(☃.func_175623_d(☃) && ☃.func_177956_o() > 2) {
         ☃ = ☃.func_177977_b();
      }

      if (!field_175913_a.test(☃.func_180495_p(☃))) {
         return false;
      } else {
         for(int ☃ = -2; ☃ <= 2; ++☃) {
            for(int ☃x = -2; ☃x <= 2; ++☃x) {
               if (☃.func_175623_d(☃.func_177982_a(☃, -1, ☃x)) && ☃.func_175623_d(☃.func_177982_a(☃, -2, ☃x))) {
                  return false;
               }
            }
         }

         for(int ☃ = -1; ☃ <= 0; ++☃) {
            for(int ☃x = -2; ☃x <= 2; ++☃x) {
               for(int ☃xx = -2; ☃xx <= 2; ++☃xx) {
                  ☃.func_180501_a(☃.func_177982_a(☃x, ☃, ☃xx), this.field_175912_c, 2);
               }
            }
         }

         ☃.func_180501_a(☃, this.field_175910_d, 2);

         for(EnumFacing ☃ : EnumFacing.Plane.HORIZONTAL) {
            ☃.func_180501_a(☃.func_177972_a(☃), this.field_175910_d, 2);
         }

         for(int ☃ = -2; ☃ <= 2; ++☃) {
            for(int ☃x = -2; ☃x <= 2; ++☃x) {
               if (☃ == -2 || ☃ == 2 || ☃x == -2 || ☃x == 2) {
                  ☃.func_180501_a(☃.func_177982_a(☃, 1, ☃x), this.field_175912_c, 2);
               }
            }
         }

         ☃.func_180501_a(☃.func_177982_a(2, 1, 0), this.field_175911_b, 2);
         ☃.func_180501_a(☃.func_177982_a(-2, 1, 0), this.field_175911_b, 2);
         ☃.func_180501_a(☃.func_177982_a(0, 1, 2), this.field_175911_b, 2);
         ☃.func_180501_a(☃.func_177982_a(0, 1, -2), this.field_175911_b, 2);

         for(int ☃ = -1; ☃ <= 1; ++☃) {
            for(int ☃x = -1; ☃x <= 1; ++☃x) {
               if (☃ == 0 && ☃x == 0) {
                  ☃.func_180501_a(☃.func_177982_a(☃, 4, ☃x), this.field_175912_c, 2);
               } else {
                  ☃.func_180501_a(☃.func_177982_a(☃, 4, ☃x), this.field_175911_b, 2);
               }
            }
         }

         for(int ☃ = 1; ☃ <= 3; ++☃) {
            ☃.func_180501_a(☃.func_177982_a(-1, ☃, -1), this.field_175912_c, 2);
            ☃.func_180501_a(☃.func_177982_a(-1, ☃, 1), this.field_175912_c, 2);
            ☃.func_180501_a(☃.func_177982_a(1, ☃, -1), this.field_175912_c, 2);
            ☃.func_180501_a(☃.func_177982_a(1, ☃, 1), this.field_175912_c, 2);
         }

         return true;
      }
   }
}
