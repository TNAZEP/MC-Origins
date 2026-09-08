package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public class BlueIceFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      if (☃.func_177956_o() > ☃.func_181545_F() - 1) {
         return false;
      } else if (☃.func_180495_p(☃).func_177230_c() != Blocks.field_150355_j && ☃.func_180495_p(☃.func_177977_b()).func_177230_c() != Blocks.field_150355_j) {
         return false;
      } else {
         boolean ☃ = false;

         for(EnumFacing ☃x : EnumFacing.values()) {
            if (☃x != EnumFacing.DOWN && ☃.func_180495_p(☃.func_177972_a(☃x)).func_177230_c() == Blocks.field_150403_cj) {
               ☃ = true;
               break;
            }
         }

         if (!☃) {
            return false;
         } else {
            ☃.func_180501_a(☃, Blocks.field_205164_gk.func_176223_P(), 2);

            for(int ☃x = 0; ☃x < 200; ++☃x) {
               int ☃xx = ☃.nextInt(5) - ☃.nextInt(6);
               int ☃xxx = 3;
               if (☃xx < 2) {
                  ☃xxx += ☃xx / 2;
               }

               if (☃xxx >= 1) {
                  BlockPos ☃xx = ☃.func_177982_a(☃.nextInt(☃xxx) - ☃.nextInt(☃xxx), ☃xx, ☃.nextInt(☃xxx) - ☃.nextInt(☃xxx));
                  IBlockState ☃xxx = ☃.func_180495_p(☃xx);
                  Block ☃xxxx = ☃xxx.func_177230_c();
                  if (☃xxx.func_185904_a() == Material.field_151579_a
                     || ☃xxxx == Blocks.field_150355_j
                     || ☃xxxx == Blocks.field_150403_cj
                     || ☃xxxx == Blocks.field_150432_aD) {
                     for(EnumFacing ☃xxxxx : EnumFacing.values()) {
                        Block ☃xxxxxx = ☃.func_180495_p(☃xx.func_177972_a(☃xxxxx)).func_177230_c();
                        if (☃xxxxxx == Blocks.field_205164_gk) {
                           ☃.func_180501_a(☃xx, Blocks.field_205164_gk.func_176223_P(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
