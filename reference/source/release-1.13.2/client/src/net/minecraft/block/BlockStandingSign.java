package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReaderBase;

public class BlockStandingSign extends BlockSign {
   public static final IntegerProperty field_176413_a = BlockStateProperties.field_208138_am;

   public BlockStandingSign(Block.Properties var1) {
      super(☃);
      this.func_180632_j(
         this.field_176227_L.func_177621_b().func_206870_a(field_176413_a, Integer.valueOf(0)).func_206870_a(field_204613_a, Boolean.valueOf(false))
      );
   }

   @Override
   public boolean func_196260_a(IBlockState var1, IWorldReaderBase var2, BlockPos var3) {
      return ☃.func_180495_p(☃.func_177977_b()).func_185904_a().func_76220_a();
   }

   @Override
   public IBlockState func_196258_a(BlockItemUseContext var1) {
      IFluidState ☃ = ☃.func_195991_k().func_204610_c(☃.func_195995_a());
      return this.func_176223_P()
         .func_206870_a(field_176413_a, Integer.valueOf(MathHelper.func_76128_c((double)((180.0F + ☃.func_195990_h()) * 16.0F / 360.0F) + 0.5) & 15))
         .func_206870_a(field_204613_a, Boolean.valueOf(☃.func_206886_c() == Fluids.field_204546_a));
   }

   @Override
   public IBlockState func_196271_a(IBlockState var1, EnumFacing var2, IBlockState var3, IWorld var4, BlockPos var5, BlockPos var6) {
      return ☃ == EnumFacing.DOWN && !this.func_196260_a(☃, ☃, ☃) ? Blocks.field_150350_a.func_176223_P() : super.func_196271_a(☃, ☃, ☃, ☃, ☃, ☃);
   }

   @Override
   public IBlockState func_185499_a(IBlockState var1, Rotation var2) {
      return ☃.func_206870_a(field_176413_a, Integer.valueOf(☃.func_185833_a(☃.func_177229_b(field_176413_a), 16)));
   }

   @Override
   public IBlockState func_185471_a(IBlockState var1, Mirror var2) {
      return ☃.func_206870_a(field_176413_a, Integer.valueOf(☃.func_185802_a(☃.func_177229_b(field_176413_a), 16)));
   }

   @Override
   protected void func_206840_a(StateContainer.Builder<Block, IBlockState> var1) {
      ☃.func_206894_a(field_176413_a, field_204613_a);
   }
}
