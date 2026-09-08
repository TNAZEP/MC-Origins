package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public class BlockPane extends BlockFourWay {
   protected BlockPane(Block.Properties var1) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, ☃);
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_196409_a, Boolean.valueOf(false))
            .func_206870_a(field_196411_b, Boolean.valueOf(false))
            .func_206870_a(field_196413_c, Boolean.valueOf(false))
            .func_206870_a(field_196414_y, Boolean.valueOf(false))
            .func_206870_a(field_204514_u, Boolean.valueOf(false))
      );
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IBlockReader ☃ = ☃.func_195991_k();
      BlockPos ☃x = ☃.func_195995_a();
      IFluidState ☃xx = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      BlockPos ☃xxx = ☃x.func_177978_c();
      BlockPos ☃xxxx = ☃x.func_177968_d();
      BlockPos ☃xxxxx = ☃x.func_177976_e();
      BlockPos ☃xxxxxx = ☃x.func_177974_f();
      IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxx);
      IBlockState ☃xxxxxxxx = ☃.func_180495_p(☃xxxx);
      IBlockState ☃xxxxxxxxx = ☃.func_180495_p(☃xxxxx);
      IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxxxx);
      return this.func_176223_P()
         .func_206870_a(field_196409_a, Boolean.valueOf(this.func_196417_a(☃xxxxxxx, ☃xxxxxxx.func_193401_d(☃, ☃xxx, EnumFacing.SOUTH))))
         .func_206870_a(field_196413_c, Boolean.valueOf(this.func_196417_a(☃xxxxxxxx, ☃xxxxxxxx.func_193401_d(☃, ☃xxxx, EnumFacing.NORTH))))
         .func_206870_a(field_196414_y, Boolean.valueOf(this.func_196417_a(☃xxxxxxxxx, ☃xxxxxxxxx.func_193401_d(☃, ☃xxxxx, EnumFacing.EAST))))
         .func_206870_a(field_196411_b, Boolean.valueOf(this.func_196417_a(☃xxxxxxxxxx, ☃xxxxxxxxxx.func_193401_d(☃, ☃xxxxxx, EnumFacing.WEST))))
         .func_206870_a(field_204514_u, Boolean.valueOf(☃xx.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      if (☃.func_177229_b(field_204514_u)) {
         ☃.func_205219_F_().func_205360_a(☃, Fluids.field_204546_a, Fluids.field_204546_a.func_205569_a(☃));
      }

      return ☃.func_176740_k().func_176722_c()
         ? ☃.func_206870_a((IProperty)field_196415_z.get(☃), Boolean.valueOf(this.func_196417_a(☃, ☃.func_193401_d(☃, ☃, ☃.func_176734_d()))))
         : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public boolean func_200122_a(IBlockState var1, IBlockState var2, EnumFacing var3) {
      if (☃.func_177230_c() == this) {
         if (!☃.func_176740_k().func_176722_c()) {
            return true;
         }

         if (☃.func_177229_b((IProperty)field_196415_z.get(☃)) && ☃.func_177229_b((IProperty)field_196415_z.get(☃.func_176734_d()))) {
            return true;
         }
      }

      return super.func_200122_a(☃, ☃, ☃);
   }

   public final boolean func_196417_a(IBlockState var1, BlockFaceShape var2) {
      Block ☃ = ☃.func_177230_c();
      return !func_196418_h(☃) && ☃ == BlockFaceShape.SOLID || ☃ == BlockFaceShape.MIDDLE_POLE_THIN;
   }

   public static boolean func_196418_h(Block var0) {
      return ☃ instanceof BlockShulkerBox
         || ☃ instanceof BlockLeaves
         || ☃ == Blocks.field_150461_bJ
         || ☃ == Blocks.field_150383_bp
         || ☃ == Blocks.field_150426_aN
         || ☃ == Blocks.field_150432_aD
         || ☃ == Blocks.field_180398_cJ
         || ☃ == Blocks.field_150331_J
         || ☃ == Blocks.field_150320_F
         || ☃ == Blocks.field_150332_K
         || ☃ == Blocks.field_150440_ba
         || ☃ == Blocks.field_150423_aK
         || ☃ == Blocks.field_196625_cS
         || ☃ == Blocks.field_196628_cT
         || ☃ == Blocks.field_180401_cv;
   }

   @Override
   protected boolean func_149700_E() {
      return true;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.CUTOUT_MIPPED;
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_196409_a, field_196411_b, field_196414_y, field_196413_c, field_204514_u);
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return ☃ != EnumFacing.UP && ☃ != EnumFacing.DOWN ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.CENTER_SMALL;
   }
}
