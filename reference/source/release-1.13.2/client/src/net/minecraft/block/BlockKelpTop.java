package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockKelpTop extends Block implements ILiquidContainer {
   public static final IntegerProperty field_203163_a = BlockStateProperties.field_208172_Y;
   protected static final VoxelShape field_207797_b = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

   protected BlockKelpTop(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_203163_a, Integer.valueOf(0)));
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_207797_b;
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IFluidState ☃ = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      return ☃.func_206884_a(FluidTags.field_206959_a) && ☃.func_206882_g() == 8 ? this.func_209906_a(☃.func_195991_k()) : null;
   }

   public IBlockState func_209906_a(IWorld var1) {
      return this.func_176223_P().func_206870_a(field_203163_a, Integer.valueOf(☃.func_201674_k().nextInt(25)));
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return Fluids.field_204546_a.func_207204_a(false);
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.func_196955_c(☃, ☃)) {
         ☃.func_175655_b(☃, true);
      } else {
         BlockPos ☃ = ☃.func_177984_a();
         IBlockState ☃x = ☃.func_180495_p(☃);
         if (☃x.func_177230_c() == Blocks.field_150355_j && ☃.func_177229_b(field_203163_a) < 25 && ☃.nextDouble() < 0.14) {
            ☃.func_175656_a(☃, ☃.func_177231_a(field_203163_a));
         }
      }
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      BlockPos ☃ = ☃.func_177977_b();
      IBlockState ☃x = ☃.func_180495_p(☃);
      Block ☃xx = ☃x.func_177230_c();
      if (☃xx == Blocks.field_196814_hQ) {
         return false;
      } else {
         return ☃xx == this || ☃xx == Blocks.field_203215_jy || Block.func_208061_a(☃x.func_196952_d(☃, ☃), EnumFacing.UP);
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (!☃.func_196955_c(☃, ☃)) {
         if (☃ == EnumFacing.DOWN) {
            return Blocks.field_150350_a.func_176223_P();
         }

         ☃.func_205220_G_().func_205360_a(☃, this, 1);
      }

      if (☃ == EnumFacing.UP && ☃.func_177230_c() == this) {
         return Blocks.field_203215_jy.func_176223_P();
      } else {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_203163_a);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return false;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      return false;
   }
}
