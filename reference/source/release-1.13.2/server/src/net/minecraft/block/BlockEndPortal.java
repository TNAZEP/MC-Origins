package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class BlockEndPortal extends BlockContainer {
   protected static final VoxelShape field_196323_a = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);

   protected BlockEndPortal(Block.Properties var1) {
      super(☃);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityEndPortal();
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196323_a;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return 0;
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      if (!☃.field_72995_K
         && !☃.func_184218_aH()
         && !☃.func_184207_aI()
         && ☃.func_184222_aU()
         && VoxelShapes.func_197879_c(
            VoxelShapes.func_197881_a(☃.func_174813_aQ().func_72317_d((double)(-☃.func_177958_n()), (double)(-☃.func_177956_o()), (double)(-☃.func_177952_p()))),
            ☃.func_196954_c(☃, ☃),
            IBooleanFunction.AND
         )) {
         ☃.func_212321_a(DimensionType.THE_END);
      }
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return ItemStack.field_190927_a;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
