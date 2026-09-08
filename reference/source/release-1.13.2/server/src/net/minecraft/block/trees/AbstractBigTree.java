package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class AbstractBigTree extends AbstractTree {
   @Override
   public boolean func_196935_a(IWorld var1, BlockPos var2, IBlockState var3, Random var4) {
      for(int ☃ = 0; ☃ >= -1; --☃) {
         for(int ☃x = 0; ☃x >= -1; --☃x) {
            if (func_196937_a(☃, ☃, ☃, ☃, ☃x)) {
               return this.func_196939_a(☃, ☃, ☃, ☃, ☃, ☃x);
            }
         }
      }

      return super.func_196935_a(☃, ☃, ☃, ☃);
   }

   @Nullable
   protected abstract AbstractTreeFeature<NoFeatureConfig> func_196938_a(Random var1);

   public boolean func_196939_a(IWorld var1, BlockPos var2, IBlockState var3, Random var4, int var5, int var6) {
      AbstractTreeFeature<NoFeatureConfig> ☃ = this.func_196938_a(☃);
      if (☃ == null) {
         return false;
      } else {
         IBlockState ☃ = Blocks.field_150350_a.func_176223_P();
         ☃.func_180501_a(☃.func_177982_a(☃, 0, ☃), ☃, 4);
         ☃.func_180501_a(☃.func_177982_a(☃ + 1, 0, ☃), ☃, 4);
         ☃.func_180501_a(☃.func_177982_a(☃, 0, ☃ + 1), ☃, 4);
         ☃.func_180501_a(☃.func_177982_a(☃ + 1, 0, ☃ + 1), ☃, 4);
         if (☃.func_212245_a(☃, ☃.func_72863_F().func_201711_g(), ☃, ☃.func_177982_a(☃, 0, ☃), IFeatureConfig.field_202429_e)) {
            return true;
         } else {
            ☃.func_180501_a(☃.func_177982_a(☃, 0, ☃), ☃, 4);
            ☃.func_180501_a(☃.func_177982_a(☃ + 1, 0, ☃), ☃, 4);
            ☃.func_180501_a(☃.func_177982_a(☃, 0, ☃ + 1), ☃, 4);
            ☃.func_180501_a(☃.func_177982_a(☃ + 1, 0, ☃ + 1), ☃, 4);
            return false;
         }
      }
   }

   public static boolean func_196937_a(IBlockState var0, IBlockReader var1, BlockPos var2, int var3, int var4) {
      Block ☃ = ☃.func_177230_c();
      return ☃ == ☃.func_180495_p(☃.func_177982_a(☃, 0, ☃)).func_177230_c()
         && ☃ == ☃.func_180495_p(☃.func_177982_a(☃ + 1, 0, ☃)).func_177230_c()
         && ☃ == ☃.func_180495_p(☃.func_177982_a(☃, 0, ☃ + 1)).func_177230_c()
         && ☃ == ☃.func_180495_p(☃.func_177982_a(☃ + 1, 0, ☃ + 1)).func_177230_c();
   }
}
