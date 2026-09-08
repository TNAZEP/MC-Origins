package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockCocoa extends BlockHorizontal implements IGrowable {
   public static final IntegerProperty field_176501_a = BlockStateProperties.field_208167_T;
   protected static final VoxelShape[] field_185535_b = new VoxelShape[]{
      Block.func_208617_a(11.0, 7.0, 6.0, 15.0, 12.0, 10.0),
      Block.func_208617_a(9.0, 5.0, 5.0, 15.0, 12.0, 11.0),
      Block.func_208617_a(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)
   };
   protected static final VoxelShape[] field_185536_c = new VoxelShape[]{
      Block.func_208617_a(1.0, 7.0, 6.0, 5.0, 12.0, 10.0),
      Block.func_208617_a(1.0, 5.0, 5.0, 7.0, 12.0, 11.0),
      Block.func_208617_a(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)
   };
   protected static final VoxelShape[] field_185537_d = new VoxelShape[]{
      Block.func_208617_a(6.0, 7.0, 1.0, 10.0, 12.0, 5.0),
      Block.func_208617_a(5.0, 5.0, 1.0, 11.0, 12.0, 7.0),
      Block.func_208617_a(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)
   };
   protected static final VoxelShape[] field_185538_e = new VoxelShape[]{
      Block.func_208617_a(6.0, 7.0, 11.0, 10.0, 12.0, 15.0),
      Block.func_208617_a(5.0, 5.0, 9.0, 11.0, 12.0, 15.0),
      Block.func_208617_a(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)
   };

   public BlockCocoa(Block.Properties var1) {
      super(☃);
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_185512_D, EnumFacing.NORTH).func_206870_a(field_176501_a, Integer.valueOf(0)));
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (☃.field_73012_v.nextInt(5) == 0) {
         int ☃ = ☃.func_177229_b(field_176501_a);
         if (☃ < 2) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_176501_a, Integer.valueOf(☃ + 1)), 2);
         }
      }
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      Block ☃ = ☃.func_180495_p(☃.func_177972_a(☃.func_177229_b(field_185512_D))).func_177230_c();
      return ☃.func_203417_a(BlockTags.field_203289_r);
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      int ☃ = ☃.func_177229_b(field_176501_a);
      switch((EnumFacing)☃.func_177229_b(field_185512_D)) {
         case SOUTH:
            return field_185538_e[☃];
         case NORTH:
         default:
            return field_185537_d[☃];
         case WEST:
            return field_185536_c[☃];
         case EAST:
            return field_185535_b[☃];
      }
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = this.func_176223_P();
      IWorldReaderBase ☃x = ☃.func_195991_k();
      BlockPos ☃xx = ☃.func_195995_a();

      for(EnumFacing ☃xxx : ☃.func_196009_e()) {
         if (☃xxx.func_176740_k().func_176722_c()) {
            ☃ = ☃.func_206870_a(field_185512_D, ☃xxx);
            if (☃.func_196955_c(☃x, ☃xx)) {
               return ☃;
            }
         }
      }

      return null;
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == ☃.func_177229_b(field_185512_D) && !☃.func_196955_c(☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      int ☃ = ☃.func_177229_b(field_176501_a);
      int ☃x = 1;
      if (☃ >= 2) {
         ☃x = 3;
      }

      for(int ☃ = 0; ☃ < ☃x; ++☃) {
         func_180635_a(☃, ☃, new ItemStack(Items.field_196130_bo));
      }
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      return new ItemStack(Items.field_196130_bo);
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return ☃.func_177229_b(field_176501_a) < 2;
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      ☃.func_180501_a(☃, ☃.func_206870_a(field_176501_a, Integer.valueOf(☃.func_177229_b(field_176501_a) + 1)), 2);
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_185512_D, field_176501_a);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }
}
