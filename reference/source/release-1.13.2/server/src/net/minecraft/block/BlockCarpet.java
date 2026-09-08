package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class BlockCarpet extends Block {
   protected static final VoxelShape field_196548_a = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private final EnumDyeColor field_196549_b;

   protected BlockCarpet(EnumDyeColor var1, Block.Properties var2) {
      super(☃);
      this.field_196549_b = ☃;
   }

   public EnumDyeColor func_196547_d() {
      return this.field_196549_b;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196548_a;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return !☃.func_175623_d(☃.func_177977_b());
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
   }
}
