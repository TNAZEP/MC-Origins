package net.minecraft.block;

import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockLog extends BlockRotatedPillar {
   private final MaterialColor field_196504_b;

   public BlockLog(MaterialColor var1, Block.Properties var2) {
      super(☃);
      this.field_196504_b = ☃;
   }

   @Override
   public MaterialColor func_180659_g(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177229_b(field_176298_M) == EnumFacing.Axis.Y ? this.field_196504_b : this.field_181083_K;
   }
}
