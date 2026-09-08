package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockChorusPlant extends BlockSixWay {
   protected BlockChorusPlant(Block.Properties var1) {
      super(0.3125F, ☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_196488_a, Boolean.valueOf(false))
            .func_206870_a(field_196490_b, Boolean.valueOf(false))
            .func_206870_a(field_196492_c, Boolean.valueOf(false))
            .func_206870_a(field_196495_y, Boolean.valueOf(false))
            .func_206870_a(field_196496_z, Boolean.valueOf(false))
            .func_206870_a(field_196489_A, Boolean.valueOf(false))
      );
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      return this.func_196497_a(☃.func_195991_k(), ☃.func_195995_a());
   }

   public IBlockState func_196497_a(IBlockReader var1, BlockPos var2) {
      Block ☃ = ☃.func_180495_p(☃.func_177977_b()).func_177230_c();
      Block ☃x = ☃.func_180495_p(☃.func_177984_a()).func_177230_c();
      Block ☃xx = ☃.func_180495_p(☃.func_177978_c()).func_177230_c();
      Block ☃xxx = ☃.func_180495_p(☃.func_177974_f()).func_177230_c();
      Block ☃xxxx = ☃.func_180495_p(☃.func_177968_d()).func_177230_c();
      Block ☃xxxxx = ☃.func_180495_p(☃.func_177976_e()).func_177230_c();
      return this.func_176223_P()
         .func_206870_a(field_196489_A, Boolean.valueOf(☃ == this || ☃ == Blocks.field_185766_cS || ☃ == Blocks.field_150377_bs))
         .func_206870_a(field_196496_z, Boolean.valueOf(☃x == this || ☃x == Blocks.field_185766_cS))
         .func_206870_a(field_196488_a, Boolean.valueOf(☃xx == this || ☃xx == Blocks.field_185766_cS))
         .func_206870_a(field_196490_b, Boolean.valueOf(☃xxx == this || ☃xxx == Blocks.field_185766_cS))
         .func_206870_a(field_196492_c, Boolean.valueOf(☃xxxx == this || ☃xxxx == Blocks.field_185766_cS))
         .func_206870_a(field_196495_y, Boolean.valueOf(☃xxxxx == this || ☃xxxxx == Blocks.field_185766_cS));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (!☃.func_196955_c(☃, ☃)) {
         ☃.func_205220_G_().func_205360_a(☃, this, 1);
         return super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
      } else {
         Block ☃ = ☃.func_177230_c();
         boolean ☃x = ☃ == this || ☃ == Blocks.field_185766_cS || ☃ == EnumFacing.DOWN && ☃ == Blocks.field_150377_bs;
         return ☃.func_206870_a((IProperty)field_196491_B.get(☃), Boolean.valueOf(☃x));
      }
   }

   @Override
   public void func_196267_b(IBlockState var1, World var2, BlockPos var3, Random var4) {
      if (!☃.func_196955_c(☃, ☃)) {
         ☃.func_175655_b(☃, true);
      }
   }

   @Override
   public IItemProvider func_199769_a(IBlockState var1, World var2, BlockPos var3, int var4) {
      return Items.field_185161_cS;
   }

   @Override
   public int func_196264_a(IBlockState var1, Random var2) {
      return ☃.nextInt(2);
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      IBlockState ☃ = ☃.func_180495_p(☃.func_177977_b());
      boolean ☃x = !☃.func_180495_p(☃.func_177984_a()).func_196958_f() && !☃.func_196958_f();

      for(EnumFacing ☃xx : EnumFacing.Plane.HORIZONTAL) {
         BlockPos ☃xxx = ☃.func_177972_a(☃xx);
         Block ☃xxxx = ☃.func_180495_p(☃xxx).func_177230_c();
         if (☃xxxx == this) {
            if (☃x) {
               return false;
            }

            Block ☃xxxxx = ☃.func_180495_p(☃xxx.func_177977_b()).func_177230_c();
            if (☃xxxxx == this || ☃xxxxx == Blocks.field_150377_bs) {
               return true;
            }
         }
      }

      Block ☃xx = ☃.func_177230_c();
      return ☃xx == this || ☃xx == Blocks.field_150377_bs;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196488_a, field_196490_b, field_196492_c, field_196495_y, field_196496_z, field_196489_A);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
