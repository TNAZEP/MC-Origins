package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockSlab extends Block implements IBucketPickupHandler, ILiquidContainer {
   public static final EnumProperty<SlabType> field_196505_a = BlockStateProperties.field_208145_at;
   public static final BooleanProperty field_204512_b = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_196506_b = Block.func_208617_a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape field_196507_c = Block.func_208617_a(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);

   public BlockSlab(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.func_176223_P().func_206870_a(field_196505_a, SlabType.BOTTOM).func_206870_a(field_204512_b, Boolean.valueOf(false)));
   }

   @Override
   public int func_200011_d(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_201572_C();
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196505_a, field_204512_b);
   }

   @Override
   protected boolean func_149700_E() {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      SlabType ☃ = ☃.func_177229_b(field_196505_a);
      switch(☃) {
         case DOUBLE:
            return VoxelShapes.func_197868_b();
         case TOP:
            return field_196507_c;
         default:
            return field_196506_b;
      }
   }

   @Override
   public boolean func_185481_k(IBlockState var1) {
      return ☃.func_177229_b(field_196505_a) == SlabType.DOUBLE || ☃.func_177229_b(field_196505_a) == SlabType.TOP;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      SlabType ☃ = ☃.func_177229_b(field_196505_a);
      if (☃ == SlabType.DOUBLE) {
         return BlockFaceShape.SOLID;
      } else if (☃ == EnumFacing.UP && ☃ == SlabType.TOP) {
         return BlockFaceShape.SOLID;
      } else {
         return ☃ == EnumFacing.DOWN && ☃ == SlabType.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      if (☃.func_177230_c() == this) {
         return ☃.func_206870_a(field_196505_a, SlabType.DOUBLE).func_206870_a(field_204512_b, Boolean.valueOf(false));
      } else {
         IFluidState ☃ = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
         IBlockState ☃x = this.func_176223_P()
            .func_206870_a(field_196505_a, SlabType.BOTTOM)
            .func_206870_a(field_204512_b, Boolean.valueOf(☃.func_206886_c() == Fluids.field_204546_a));
         EnumFacing ☃xx = ☃.func_196000_l();
         return ☃xx != EnumFacing.DOWN && (☃xx == EnumFacing.UP || !((double)☃.func_195993_n() > 0.5)) ? ☃x : ☃x.func_206870_a(field_196505_a, SlabType.TOP);
      }
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return ☃.func_177229_b(field_196505_a) == SlabType.DOUBLE ? 2 : 1;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return ☃.func_177229_b(field_196505_a) == SlabType.DOUBLE;
   }

   @Override
   public boolean func_196253_a(IBlockState var1, BlockItemUseContext var2) {
      ItemStack ☃ = ☃.func_195996_i();
      SlabType ☃x = ☃.func_177229_b(field_196505_a);
      if (☃x == SlabType.DOUBLE || ☃.func_77973_b() != this.func_199767_j()) {
         return false;
      } else if (☃.func_196012_c()) {
         boolean ☃ = (double)☃.func_195993_n() > 0.5;
         EnumFacing ☃x = ☃.func_196000_l();
         if (☃x == SlabType.BOTTOM) {
            return ☃x == EnumFacing.UP || ☃ && ☃x.func_176740_k().func_176722_c();
         } else {
            return ☃x == EnumFacing.DOWN || !☃ && ☃x.func_176740_k().func_176722_c();
         }
      } else {
         return true;
      }
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204512_b)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204512_b, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204512_b) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return ☃.func_177229_b(field_196505_a) != SlabType.DOUBLE && !☃.func_177229_b(field_204512_b) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (☃.func_177229_b(field_196505_a) != SlabType.DOUBLE && !☃.func_177229_b(field_204512_b) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204512_b, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, ☃.func_206886_c(), ☃.func_206886_c().func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204512_b)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      switch(☃) {
         case LAND:
            return ☃.func_177229_b(field_196505_a) == SlabType.BOTTOM;
         case WATER:
            return ☃.func_204610_c(☃).func_206884_a(FluidTags.field_206959_a);
         case AIR:
            return false;
         default:
            return false;
      }
   }
}
