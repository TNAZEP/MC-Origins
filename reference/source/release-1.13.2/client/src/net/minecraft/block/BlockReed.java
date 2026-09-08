package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
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

public class BlockReed extends Block {
   public static final IntegerProperty field_176355_a = BlockStateProperties.field_208171_X;
   protected static final VoxelShape field_196503_b = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   protected BlockReed(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176355_a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196503_b;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.func_196955_c(☃, ☃) && ☃.func_175623_d(☃.func_177984_a())) {
         int ☃ = 1;

         while(☃.func_180495_p(☃.func_177979_c(☃)).func_177230_c() == this) {
            ++☃;
         }

         if (☃ < 3) {
            int ☃x = ☃.func_177229_b(field_176355_a);
            if (☃x == 15) {
               ☃.func_175656_a(☃.func_177984_a(), this.func_176223_P());
               ☃.func_180501_a(☃, ☃.func_206870_a(field_176355_a, Integer.valueOf(0)), 4);
            } else {
               ☃.func_180501_a(☃, ☃.func_206870_a(field_176355_a, Integer.valueOf(☃x + 1)), 4);
            }
         }
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      Block ☃ = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
      if (☃ == this) {
         return true;
      } else {
         if (☃ == Blocks.field_196658_i
            || ☃ == Blocks.field_150346_d
            || ☃ == Blocks.field_196660_k
            || ☃ == Blocks.field_196661_l
            || ☃ == Blocks.field_150354_m
            || ☃ == Blocks.field_196611_F) {
            BlockPos ☃ = ☃.func_177977_b();

            for(EnumFacing ☃x : EnumFacing.Plane.HORIZONTAL) {
               IBlockState ☃xx = ☃.func_180495_p(☃.func_177972_a(☃x));
               IFluidState ☃xxx = ☃.func_204610_c(☃.func_177972_a(☃x));
               if (☃xxx.func_206884_a(FluidTags.field_206959_a) || ☃xx.func_177230_c() == Blocks.field_185778_de) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176355_a);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
