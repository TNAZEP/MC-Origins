package net.minecraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHay extends BlockRotatedPillar {
   public BlockHay(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176298_M, EnumFacing.Axis.Y));
   }

   @Override
   public void func_180658_a(World var1, BlockPos var2, Entity var3, float var4) {
      ☃.func_180430_e(☃, 0.2F);
   }
}
