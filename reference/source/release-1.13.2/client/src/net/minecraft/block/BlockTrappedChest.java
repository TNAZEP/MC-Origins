package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityTrappedChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public class BlockTrappedChest extends BlockChest {
   public BlockTrappedChest(Block.Properties var1) {
      super(☃);
   }

   @Override
   public TileEntity func_196283_a_(IBlockReader var1) {
      return new TileEntityTrappedChest();
   }

   @Override
   protected Stat<ResourceLocation> func_196310_d() {
      return StatList.field_199092_j.func_199076_b(StatList.field_188089_W);
   }

   @Override
   public boolean func_149744_f(IBlockState var1) {
      return true;
   }

   @Override
   public int func_180656_a(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return MathHelper.func_76125_a(TileEntityChest.func_195481_a(☃, ☃), 0, 15);
   }

   @Override
   public int func_176211_b(IBlockState var1, IBlockReader var2, BlockPos var3, EnumFacing var4) {
      return ☃ == EnumFacing.UP ? ☃.func_185911_a(☃, ☃, ☃) : 0;
   }
}
