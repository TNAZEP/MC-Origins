package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public abstract class AbstractTree {
   @Nullable
   protected abstract AbstractTreeFeature<NoFeatureConfig> func_196936_b(Random var1);

   public boolean func_196935_a(IWorld var1, BlockPos var2, IBlockState var3, Random var4) {
      AbstractTreeFeature<NoFeatureConfig> ☃ = this.func_196936_b(☃);
      if (☃ == null) {
         return false;
      } else {
         ☃.func_180501_a(☃, Blocks.field_150350_a.func_176223_P(), 4);
         if (☃.func_212245_a(☃, ☃.func_72863_F().func_201711_g(), ☃, ☃, IFeatureConfig.field_202429_e)) {
            return true;
         } else {
            ☃.func_180501_a(☃, ☃, 4);
            return false;
         }
      }
   }
}
