package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockGrassPath extends Block {
   protected static final VoxelShape field_196453_a = BlockFarmland.field_196432_b;

   protected BlockGrassPath(Block.Properties var1) {
      super(☃);
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_201572_C();
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return !this.func_176223_P().func_196955_c(☃.func_195991_k(), ☃.func_195995_a())
         ? Block.func_199601_a(this.func_176223_P(), Blocks.field_150346_d.func_176223_P(), ☃.func_195991_k(), ☃.func_195995_a())
         : super.func_196258_a(☃);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃ == EnumFacing.UP && !☃.func_196955_c(☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      BlockFarmland.func_199610_d(☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177984_a());
      return !☃.func_185904_a().func_76220_a() || ☃.func_177230_c() instanceof BlockFenceGate;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196453_a;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Blocks.field_150346_d;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
