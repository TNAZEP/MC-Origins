package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class BlockFlower extends BlockBush {
   protected static final VoxelShape field_196398_a = Block.func_208617_a(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);

   public BlockFlower(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      Vec3d ☃ = ☃.func_191059_e(☃, ☃);
      return field_196398_a.func_197751_a(☃.field_72450_a, ☃.field_72448_b, ☃.field_72449_c);
   }

   @Override
   public Block.EnumOffsetType func_176218_Q() {
      return Block.EnumOffsetType.XZ;
   }
}
