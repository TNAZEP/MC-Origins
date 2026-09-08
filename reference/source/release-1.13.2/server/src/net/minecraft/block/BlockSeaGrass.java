package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.stats.StatList;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockSeaGrass extends BlockBush implements IGrowable, ILiquidContainer {
   protected static final VoxelShape field_207798_a = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

   protected BlockSeaGrass(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_207798_a;
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return Block.func_208061_a(☃.func_196952_d(☃, ☃), EnumFacing.UP) && ☃.func_177230_c() != Blocks.field_196814_hQ;
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IFluidState ☃ = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      return ☃.func_206884_a(FluidTags.field_206959_a) && ☃.func_206882_g() == 8 ? super.func_196258_a(☃) : null;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      IBlockState ☃ = super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      if (!☃.func_196958_f()) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return ☃;
   }

   @Override
   public void func_180657_a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, @Nullable TileEntity var5, ItemStack var6) {
      if (!☃.field_72995_K && ☃.func_77973_b() == Items.field_151097_aZ) {
         ☃.func_71029_a(StatList.field_188065_ae.func_199076_b(this));
         ☃.func_71020_j(0.005F);
         func_180635_a(☃, ☃, new ItemStack(this));
      } else {
         super.func_180657_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return Fluids.field_204546_a.func_207204_a(false);
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      IBlockState ☃ = Blocks.field_203199_aR.func_176223_P();
      IBlockState ☃x = ☃.func_206870_a(BlockSeaGrassTall.field_208065_c, DoubleBlockHalf.UPPER);
      BlockPos ☃xx = ☃.func_177984_a();
      if (☃.func_180495_p(☃xx).func_177230_c() == Blocks.field_150355_j) {
         ☃.func_180501_a(☃, ☃, 2);
         ☃.func_180501_a(☃xx, ☃x, 2);
      }
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return false;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      return false;
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return Blocks.field_150355_j.func_176223_P().func_200016_a(☃, ☃);
   }
}
