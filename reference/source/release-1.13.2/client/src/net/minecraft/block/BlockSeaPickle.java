package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockSeaPickle extends BlockBush implements IGrowable, IBucketPickupHandler, ILiquidContainer {
   public static final IntegerProperty field_204902_a = BlockStateProperties.field_208135_aj;
   public static final BooleanProperty field_204903_b = BlockStateProperties.field_208198_y;
   protected static final VoxelShape field_204904_c = Block.func_208617_a(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
   protected static final VoxelShape field_204905_t = Block.func_208617_a(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
   protected static final VoxelShape field_204906_u = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
   protected static final VoxelShape field_204907_v = Block.func_208617_a(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

   protected BlockSeaPickle(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_204902_a, Integer.valueOf(1)).func_206870_a(field_204903_b, Boolean.valueOf(true))
      );
   }

   @Override
   public int func_149750_m(IBlockState var1) {
      return this.func_204901_j(☃) ? 0 : super.func_149750_m(☃) + 3 * ☃.func_177229_b(field_204902_a);
   }

   @Nullable
   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockState ☃ = ☃.func_195991_k().func_180495_p(☃.func_195995_a());
      if (☃.func_177230_c() == this) {
         return ☃.func_206870_a(field_204902_a, Integer.valueOf(Math.min(4, ☃.func_177229_b(field_204902_a) + 1)));
      } else {
         IFluidState ☃ = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
         boolean ☃x = ☃.func_206884_a(FluidTags.field_206959_a) && ☃.func_206882_g() == 8;
         return super.func_196258_a(☃).func_206870_a(field_204903_b, Boolean.valueOf(☃x));
      }
   }

   private boolean func_204901_j(IBlockState var1) {
      return !☃.func_177229_b(field_204903_b);
   }

   @Override
   protected boolean func_200014_a_(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return !☃.func_196952_d(☃, ☃).func_212434_a(EnumFacing.UP).func_197766_b();
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      BlockPos ☃ = ☃.func_177977_b();
      return this.func_200014_a_(☃.func_180495_p(☃), ☃, ☃);
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (!☃.func_196955_c(☃, ☃)) {
         return Blocks.field_150350_a.func_176223_P();
      } else {
         if (☃.func_177229_b(field_204903_b)) {
            ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
         }

         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      }
   }

   @Override
   public boolean func_196253_a(IBlockState var1, BlockItemUseContext var2) {
      return ☃.func_195996_i().func_77973_b() == this.func_199767_j() && ☃.func_177229_b(field_204902_a) < 4 ? true : super.func_196253_a(☃, ☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      switch(☃.func_177229_b(field_204902_a)) {
         case 1:
         default:
            return field_204904_c;
         case 2:
            return field_204905_t;
         case 3:
            return field_204906_u;
         case 4:
            return field_204907_v;
      }
   }

   @Override
   public Fluid func_204508_a(IWorld var1, BlockPos var2, IBlockState var3) {
      if (☃.func_177229_b(field_204903_b)) {
         ☃.func_180501_a(☃, ☃.func_206870_a(field_204903_b, Boolean.valueOf(false)), 3);
         return Fluids.field_204546_a;
      } else {
         return Fluids.field_204541_a;
      }
   }

   @Override
   public IFluidState func_204507_t(IBlockState var1) {
      return ☃.func_177229_b(field_204903_b) ? Fluids.field_204546_a.func_207204_a(false) : super.func_204507_t(☃);
   }

   @Override
   public boolean func_204510_a(IBlockReader var1, BlockPos var2, IBlockState var3, Fluid var4) {
      return !☃.func_177229_b(field_204903_b) && ☃ == Fluids.field_204546_a;
   }

   @Override
   public boolean func_204509_a(IWorld var1, BlockPos var2, IBlockState var3, IFluidState var4) {
      if (!☃.func_177229_b(field_204903_b) && ☃.func_206886_c() == Fluids.field_204546_a) {
         if (!☃.func_201670_d()) {
            ☃.func_180501_a(☃, ☃.func_206870_a(field_204903_b, Boolean.valueOf(true)), 3);
            ☃.func_205219_F_().func_205360_a(☃, ☃.func_206886_c(), ☃.func_206886_c().func_205569_a(☃));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_204902_a, field_204903_b);
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return ☃.func_177229_b(field_204902_a);
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
   public void func_176474_b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      if (!this.func_204901_j(☃) && ☃.func_180495_p(☃.func_177977_b()).func_203425_a(BlockTags.field_205598_B)) {
         int ☃ = 5;
         int ☃x = 1;
         int ☃xx = 2;
         int ☃xxx = 0;
         int ☃xxxx = ☃.func_177958_n() - 2;
         int ☃xxxxx = 0;

         for(int ☃xxxxxx = 0; ☃xxxxxx < 5; ++☃xxxxxx) {
            for(int ☃xxxxxxx = 0; ☃xxxxxxx < ☃x; ++☃xxxxxxx) {
               int ☃xxxxxxxx = 2 + ☃.func_177956_o() - 1;

               for(int ☃xxxxxxxxx = ☃xxxxxxxx - 2; ☃xxxxxxxxx < ☃xxxxxxxx; ++☃xxxxxxxxx) {
                  BlockPos ☃xxxxxxxxxx = new BlockPos(☃xxxx + ☃xxxxxx, ☃xxxxxxxxx, ☃.func_177952_p() - ☃xxxxx + ☃xxxxxxx);
                  if (☃xxxxxxxxxx != ☃ && ☃.nextInt(6) == 0 && ☃.func_180495_p(☃xxxxxxxxxx).func_177230_c() == Blocks.field_150355_j) {
                     IBlockState ☃xxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxx.func_177977_b());
                     if (☃xxxxxxxxxxx.func_203425_a(BlockTags.field_205598_B)) {
                        ☃.func_180501_a(☃xxxxxxxxxx, Blocks.field_204913_jW.func_176223_P().func_206870_a(field_204902_a, Integer.valueOf(☃.nextInt(4) + 1)), 3);
                     }
                  }
               }
            }

            if (☃xxx < 2) {
               ☃x += 2;
               ++☃xxxxx;
            } else {
               ☃x -= 2;
               --☃xxxxx;
            }

            ++☃xxx;
         }

         ☃.func_180501_a(☃, ☃.func_206870_a(field_204902_a, Integer.valueOf(4)), 2);
      }
   }
}
