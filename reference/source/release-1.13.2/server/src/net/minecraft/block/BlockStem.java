package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
   public static final IntegerProperty field_176484_a = BlockStateProperties.field_208170_W;
   protected static final VoxelShape[] field_196388_b = new VoxelShape[]{
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 2.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 4.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 6.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 12.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0),
      Block.func_208617_a(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)
   };
   private final BlockStemGrown field_149877_a;

   protected BlockStem(BlockStemGrown var1, Block.Properties var2) {
      super(☃);
      this.field_149877_a = ☃;
      this.func_180632_j(this.field_176227_L.func_177621_b().func_206870_a(field_176484_a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196388_b[☃.func_177229_b(field_176484_a)];
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return ☃.func_177230_c() == Blocks.field_150458_ak;
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      super.func_196267_b(☃, ☃, ☃, ☃);
      if (☃.func_201669_a(☃.func_177984_a(), 0) >= 9) {
         float ☃ = BlockCrops.func_180672_a(this, ☃, ☃);
         if (☃.nextInt((int)(25.0F / ☃) + 1) == 0) {
            int ☃x = ☃.func_177229_b(field_176484_a);
            if (☃x < 7) {
               ☃ = ☃.func_206870_a(field_176484_a, Integer.valueOf(☃x + 1));
               ☃.func_180501_a(☃, ☃, 2);
            } else {
               EnumFacing ☃x = EnumFacing.Plane.HORIZONTAL.func_179518_a(☃);
               BlockPos ☃xx = ☃.func_177972_a(☃x);
               Block ☃xxx = ☃.func_180495_p(☃xx.func_177977_b()).func_177230_c();
               if (☃.func_180495_p(☃xx).func_196958_f()
                  && (
                     ☃xxx == Blocks.field_150458_ak
                        || ☃xxx == Blocks.field_150346_d
                        || ☃xxx == Blocks.field_196660_k
                        || ☃xxx == Blocks.field_196661_l
                        || ☃xxx == Blocks.field_196658_i
                  )) {
                  ☃.func_175656_a(☃xx, this.field_149877_a.func_176223_P());
                  ☃.func_175656_a(☃, this.field_149877_a.func_196523_e().func_176223_P().func_206870_a(BlockHorizontal.field_185512_D, ☃x));
               }
            }
         }
      }
   }

   @Override
   public void func_196255_a(IBlockState var1, World var2, BlockPos var3, float var4, int var5) {
      super.func_196255_a(☃, ☃, ☃, ☃, ☃);
      if (!☃.field_72995_K) {
         Item ☃ = this.func_176481_j();
         if (☃ != null) {
            int ☃x = ☃.func_177229_b(field_176484_a);

            for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
               if (☃.field_73012_v.nextInt(15) <= ☃x) {
                  func_180635_a(☃, ☃, new ItemStack(☃));
               }
            }
         }
      }
   }

   @Nullable
   protected Item func_176481_j() {
      if (this.field_149877_a == Blocks.field_150423_aK) {
         return Items.field_151080_bb;
      } else {
         return this.field_149877_a == Blocks.field_150440_ba ? Items.field_151081_bc : null;
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_190931_a;
   }

   @Override
   public ItemStack func_185473_a(IBlockReader var1, BlockPos var2, IBlockState var3) {
      Item ☃ = this.func_176481_j();
      return ☃ == null ? ItemStack.field_190927_a : new ItemStack(☃);
   }

   @Override
   public boolean func_176473_a(IBlockReader var1, BlockPos var2, IBlockState var3, boolean var4) {
      return ☃.func_177229_b(field_176484_a) != 7;
   }

   @Override
   public boolean func_180670_a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      int ☃ = Math.min(7, ☃.func_177229_b(field_176484_a) + MathHelper.func_76136_a(☃.field_73012_v, 2, 5));
      IBlockState ☃x = ☃.func_206870_a(field_176484_a, Integer.valueOf(☃));
      ☃.func_180501_a(☃, ☃x, 2);
      if (☃ == 7) {
         ☃x.func_196940_a(☃, ☃, ☃.field_73012_v);
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176484_a);
   }

   public BlockStemGrown func_208486_d() {
      return this.field_149877_a;
   }
}
