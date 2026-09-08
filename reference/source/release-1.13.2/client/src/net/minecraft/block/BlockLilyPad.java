package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockLilyPad extends BlockBush {
   protected static final VoxelShape field_185523_a = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);

   protected BlockLilyPad(Block.Properties var1) {
      super(☃);
   }

   @Override
   public void func_196262_a(IBlockState var1, World var2, BlockPos var3, Entity var4) {
      super.func_196262_a(☃, ☃, ☃, ☃);
      if (☃ instanceof EntityBoat) {
         ☃.func_175655_b(new BlockPos(☃), true);
      }
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_185523_a;
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      IFluidState ☃ = ☃.func_204610_c(☃);
      return ☃.func_206886_c() == Fluids.field_204546_a || ☃.func_185904_a() == Material.field_151588_w;
   }
}
