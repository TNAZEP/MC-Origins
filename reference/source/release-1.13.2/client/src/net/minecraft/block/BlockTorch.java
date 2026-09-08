package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Particles;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockTorch extends Block {
   protected static final VoxelShape field_196526_y = Block.func_208617_a(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

   protected BlockTorch(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196526_y;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == EnumFacing.DOWN && !this.func_196260_a(☃, ☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      Block ☃x = ☃.func_177230_c();
      boolean ☃xx = ☃x instanceof BlockFence
         || ☃x instanceof BlockStainedGlass
         || ☃x == Blocks.field_150359_w
         || ☃x == Blocks.field_150463_bK
         || ☃x == Blocks.field_196723_eg
         || ☃.func_185896_q();
      return ☃xx && ☃x != Blocks.field_185775_db;
   }

   @Override
   public void func_180655_c(IBlockState var1, World var2, BlockPos var3, Random var4) {
      double ☃ = (double)☃.func_177958_n() + 0.5;
      double ☃x = (double)☃.func_177956_o() + 0.7;
      double ☃xx = (double)☃.func_177952_p() + 0.5;
      ☃.func_195594_a(Particles.field_197601_L, ☃, ☃x, ☃xx, 0.0, 0.0, 0.0);
      ☃.func_195594_a(Particles.field_197631_x, ☃, ☃x, ☃xx, 0.0, 0.0, 0.0);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
