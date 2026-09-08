package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockCoralPlantDead extends BlockCoralPlantBase {
   protected static final VoxelShape field_212561_a = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 15.0, 14.0);

   protected BlockCoralPlantDead(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_212561_a;
   }
}
