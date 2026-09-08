package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class BlockLadder extends Block implements IBucketPickupHandler, ILiquidContainer {
   public static final DirectionProperty field_176382_a = BlockHorizontal.field_185512_D;
   public static final BooleanProperty field_204612_b = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_185687_b = Block.func_208617_a(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
   protected static final VoxelShape field_185688_c = Block.func_208617_a(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape field_185689_d = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape field_185690_e = Block.func_208617_a(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

   protected BlockLadder(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176382_a, EnumFacing.NORTH).func_206870_a(field_204612_b, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch((EnumFacing)☃.func_177229_b(field_176382_a)) {
         case NORTH:
            return field_185690_e;
         case SOUTH:
            return field_185689_d;
         case WEST:
            return field_185688_c;
         case EAST:
         default:
            return field_185687_b;
      }
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   private boolean func_196471_a(IBlockReader var1, BlockPos var2, EnumFacing var3) {
      IBlockState ☃ = ☃.func_180495_p(☃);
      boolean ☃x = func_193382_c(☃.func_177230_c());
      return !☃x && ☃.func_193401_d(☃, ☃, ☃) == BlockFaceShape.SOLID && !☃.func_185897_m();
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      EnumFacing ☃ = ☃.func_177229_b(field_176382_a);
      return this.func_196471_a(☃, ☃.func_177972_a(☃.func_176734_d()), ☃);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_176734_d() == ☃.func_177229_b(field_176382_a) && !☃.func_196955_c(☃, ☃)) {
         return Blocks.field_150350_a.func_176223_P();
      } else {
         if (☃.func_177229_b(field_204612_b)) {
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         }

         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      if (!☃.func_196012_c()) {
         IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a().func_177972_a(☃.func_196000_l().func_176734_d()));
         if (☃.func_177230_c() == this && ☃.func_177229_b(field_176382_a) == ☃.func_196000_l()) {
            return null;
         }
      }

      IBlockState ☃ = this.func_176223_P();
      IWorldReaderBase ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a();
      IFluidState ☃xxx = ☃.func_195991_k().func_204610_c(☃.func_195995_a());

      for(EnumFacing ☃xxxx : ☃.func_196009_e()) {
         if (☃xxxx.func_176740_k().func_176722_c()) {
            ☃ = ☃.func_206870_a(field_176382_a, ☃xxxx.func_176734_d());
            if (☃.func_196955_c(☃x, ☃xx)) {
               return ☃.func_206870_a(field_204612_b, Boolean.valueOf(☃xxx.func_206886_c() == Fluids.field_204546_a));
            }
         }
      }

      return null;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176382_a, ☃.func_185831_a(☃.func_177229_b(field_176382_a)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_185907_a(☃.func_185800_a(☃.func_177229_b(field_176382_a)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176382_a, field_204612_b);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204612_b)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204612_b, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204612_b) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204612_b) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204612_b) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204612_b, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, ☃.func_206886_c(), ☃.func_206886_c().func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }
}
