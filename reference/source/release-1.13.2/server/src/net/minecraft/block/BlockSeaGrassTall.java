package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockSeaGrassTall extends BlockShearableDoublePlant implements ILiquidContainer {
   public static final EnumProperty<DoubleBlockHalf> field_208065_c = BlockShearableDoublePlant.field_208063_b;
   protected static final VoxelShape field_207799_b = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   public BlockSeaGrassTall(Block var1, Block.Properties var2) {
      super(☃, ☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_207799_b;
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return Block.func_208061_a(☃.func_196952_d(☃, ☃), EnumFacing.UP) && ☃.func_177230_c() != Blocks.field_196814_hQ;
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return new ItemStack(Blocks.field_203198_aQ);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = super.func_196258_a(☃);
      if (☃ != null) {
         IFluidState ☃x = ☃.func_195991_k().func_204610_c(☃.func_195995_a().func_177984_a());
         if (☃x.func_206884_a(FluidTags.field_206959_a) && ☃x.func_206882_g() == 8) {
            return ☃;
         }
      }

      return null;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      if (☃.func_177229_b(field_208065_c) == DoubleBlockHalf.UPPER) {
         IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
         return ☃.func_177230_c() == this && ☃.func_177229_b(field_208065_c) == DoubleBlockHalf.LOWER;
      } else {
         IFluidState ☃ = ☃.func_204610_c(☃);
         return super.func_196260_a(☃, ☃, ☃) && ☃.func_206884_a(FluidTags.field_206959_a) && ☃.func_206882_g() == 8;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return Fluids.field_204546_a.func_207204_a(false);
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
